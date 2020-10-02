package amara.libraries.entitysystem.templates;

import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public class EntityTemplate{

    private String name;
    private Map<String, String> parameters;

    public int getIntegerParameter(String key) {
        return Integer.parseInt(getStringParameter(key));
    }

    public String getStringParameter(String key) {
        return parameters.get(key);
    }

    public String getText() {
        String text = name + "(";
        int i = 0;
        for (Map.Entry<String, String> parameterEntry : parameters.entrySet()) {
            if (i > 0) {
                text += ",";
            }
            text += parameterEntry.getKey() + "=" + parameterEntry.getValue();
            i++;
        }
        text += ")";
        return text;
    }

    // Static Utility

    private static ArrayList<EntityTemplate_Loader> loaders = new ArrayList<>();

    public static void addLoader(EntityTemplate_Loader loader) {
        loaders.add(loader);
    }

    public static EntityWrapper createFromTemplate(EntityWorld entityWorld, String... templateNames) {
        int entity = entityWorld.createEntity();
        loadTemplates(entityWorld, entity, templateNames);
        return entityWorld.getWrapped(entity);
    }

    public static void loadTemplates(EntityWorld entityWorld, int entity, String... templateNames) {
        for (String templateName : templateNames) {
            loadTemplate(entityWorld, entity, templateName);
        }
    }

    public static void loadTemplate(EntityWorld entityWorld, int entity, String template) {
        loadTemplate(entityWorld, entity, parseTemplate(template));
    }

    public static EntityTemplate parseTemplate(String template) {
        return parseTemplate(template, Function.identity(), Function.identity(), Function.identity());
    }

    public static EntityTemplate parseTemplate(String template, Function<String, String> getImplicitParameterName, Function<String, String> modifyParameterName, Function<String, String> modifyParameterValue) {
        String name = template;
        HashMap<String, String> parameters = new HashMap<>();
        while (name.matches("(.*)\\((.*)\\)")) {
            int bracketStart = name.indexOf("(");
            int bracketEnd = name.indexOf(")");
            String[] parameterTexts = name.substring(bracketStart + 1, bracketEnd).split(",");
            name = name.substring(0, bracketStart) + name.substring(bracketEnd + 1);
            for (String parameterText : parameterTexts) {
                String parameterName;
                String parameterValue;
                if (parameterText.contains("=")) {
                    String[] parameterParts = parameterText.split("=");
                    parameterName = modifyParameterName.apply(parameterParts[0]);
                    parameterValue = modifyParameterValue.apply(parameterParts[1]);
                } else {
                    parameterName = getImplicitParameterName.apply(parameterText);
                    parameterValue = modifyParameterValue.apply(parameterText);
                }
                parameters.put(parameterName, parameterValue);
            }
        }
        return new EntityTemplate(name, parameters);
    }

    public static void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate entityTemplate) {
        for (EntityTemplate_Loader loader : loaders) {
            loader.loadTemplate(entityWorld, entity, entityTemplate);
        }
    }
}
