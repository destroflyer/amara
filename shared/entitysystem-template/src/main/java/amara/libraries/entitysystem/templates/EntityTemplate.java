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
public class EntityTemplate {

    private static final String SEPARATOR_INPUT_START = "(";
    private static final String SEPARATOR_INPUT_END = ")";
    private static final String SEPARATOR_OUTPUT_START = "{";
    private static final String SEPARATOR_OUTPUT_END = "}";
    private static final String SEPARATOR_PARAMETERS = ",";
    private static final String SEPARATOR_PARAMETER_NAME_VALUE = "=";

    private String name;
    private Map<String, String> input;
    private Map<String, String> output;

    public int getIntegerInput(String key) {
        return Integer.parseInt(getStringInput(key));
    }

    public String getStringInput(String key) {
        return input.get(key);
    }

    public String getText() {
        String text = name;
        if (input.size() > 0) {
            text += SEPARATOR_INPUT_START + getText(input) + SEPARATOR_INPUT_END;
        }
        if (output.size() > 0) {
            text += SEPARATOR_OUTPUT_START + getText(output) + SEPARATOR_OUTPUT_END;
        }
        return text;
    }

    private static String getText(Map<String, String> values) {
        String text = "";
        int i = 0;
        for (Map.Entry<String, String> parameterEntry : values.entrySet()) {
            if (i > 0) {
                text += SEPARATOR_PARAMETERS;
            }
            text += parameterEntry.getKey() + SEPARATOR_PARAMETER_NAME_VALUE + parameterEntry.getValue();
            i++;
        }
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

    public static EntityTemplate parseTemplate(String template, Function<String, String> getImplicitParameterName, Function<String, String> modifyValueKey, Function<String, String> modifyValueValue) {
        String name = template;
        int inputStart = name.indexOf(SEPARATOR_INPUT_START);
        if (inputStart != -1) {
            name = name.substring(0, inputStart);
        }
        int outputStart = name.indexOf(SEPARATOR_OUTPUT_START);
        if (outputStart != -1) {
            name = name.substring(0, outputStart);
        }
        Map<String, String> input = parseValues(template, SEPARATOR_INPUT_START, SEPARATOR_INPUT_END, getImplicitParameterName, modifyValueKey, modifyValueValue);
        Map<String, String> output = parseValues(template, SEPARATOR_OUTPUT_START, SEPARATOR_OUTPUT_END, getImplicitParameterName, modifyValueKey, Function.identity());
        return new EntityTemplate(name, input, output);
    }

    private static Map<String, String> parseValues(String text, String separatorStart, String separatorEnd, Function<String, String> getImplicitParameterName, Function<String, String> modifyValueKey, Function<String, String> modifyValueValue) {
        HashMap<String, String> values = new HashMap<>();
        while (text.contains(separatorStart)) {
            int indexStart = text.indexOf(separatorStart);
            int indexEnd = text.indexOf(separatorEnd);
            String[] valueTexts = text.substring(indexStart + 1, indexEnd).split(SEPARATOR_PARAMETERS);
            text = text.substring(0, indexStart) + text.substring(indexEnd + 1);
            for (String valueText : valueTexts) {
                String name;
                String value;
                if (valueText.contains(SEPARATOR_PARAMETER_NAME_VALUE)) {
                    String[] parameterParts = valueText.split(SEPARATOR_PARAMETER_NAME_VALUE);
                    name = modifyValueKey.apply(parameterParts[0]);
                    value = modifyValueValue.apply(parameterParts[1]);
                } else {
                    name = getImplicitParameterName.apply(valueText);
                    value = modifyValueValue.apply(valueText);
                }
                values.put(name, value);
            }
        }
        return values;
    }

    public static void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate entityTemplate) {
        for (EntityTemplate_Loader loader : loaders) {
            loader.loadTemplate(entityWorld, entity, entityTemplate);
        }
    }
}
