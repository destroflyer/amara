package amara.libraries.entitysystem.templates;

import amara.libraries.entitysystem.EntityWorld;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class XMLTemplateReader {

    public XMLTemplateReader(XMLTemplateManager templateManager, EntityTemplateReader entityTemplateReader) {
        this.templateManager = templateManager;
        this.entityTemplateReader = entityTemplateReader;
    }
    private XMLTemplateManager templateManager;
    private EntityTemplateReader entityTemplateReader;
    private Stack<String> currentDirectories = new Stack<>();
    private Stack<HashMap<String, Integer>> cachedEntities = new Stack<>();
    private Stack<HashMap<String, String>> cachedValues = new Stack<>();

    public void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate template, Document document) {
        Element templateElement = document.getRootElement();
        String currentDirectory = "";
        String[] directories = template.getName().split("/");
        for (int i = 0; i < (directories.length - 1); i++) {
            currentDirectory += directories[i] + "/";
        }
        currentDirectories.push(currentDirectory);
        HashMap<String, Integer> entities = new HashMap<>(10);
        cachedEntities.push(entities);
        HashMap<String, String> values = new HashMap<>();
        Element valuesElement = templateElement.getChild("values");
        if (valuesElement != null) {
            for (Element valueElement : valuesElement.getChildren()) {
                values.put(valueElement.getName(), valueElement.getText());
                // Save the unmodified default value so it can be exported and accessed by parent
                values.put("_" + valueElement.getName(), valueElement.getText());
            }
        }
        values.putAll(template.getInput());
        cachedValues.push(values);
        boolean isFirstEntity = true;
        for (Element entityElement : templateElement.getChildren("entity")) {
            if (isFirstEntity) {
                String id = entityElement.getAttributeValue("id");
                if (id != null) {
                    cachedEntities.lastElement().put(id, entity);
                }
                loadEntity(entityWorld, entity, entityElement);
            } else {
                createAndLoadEntity(entityWorld, entityElement);
            }
            isFirstEntity = false;
        }
        // Export
        if (cachedValues.size() > 1) {
            HashMap<String, String> parentTemplateValues = cachedValues.get(cachedValues.size() - 2);
            for (Entry<String, String> output : template.getOutput().entrySet()) {
                String name = output.getKey();
                String value = parseValue(entityWorld, output.getValue());
                parentTemplateValues.put(name, value);
            }
        }
        currentDirectories.pop();
        cachedEntities.pop();
        cachedValues.pop();
    }

    public int createAndLoadEntity(EntityWorld entityWorld, Element entityElement) {
        if ((!isElementEnabled(entityWorld, entityElement)) || entityElement.getName().equals("empty")) {
            return -1;
        }
        Integer entity = null;
        String id = entityElement.getAttributeValue("id");
        if (id != null) {
            entity = cachedEntities.lastElement().get(id);
        }
        if (entity == null) {
            entity = createEntity(entityWorld, id);
        }
        loadEntity(entityWorld, entity, entityElement);
        return entity;
    }

    private int createEntity(EntityWorld entityWorld, String id) {
        int entity = entityWorld.createEntity();
        if (id != null) {
            cachedEntities.lastElement().put(id, entity);
        }
        return entity;
    }

    private void loadEntity(EntityWorld entityWorld, int entity, Element entityElement) {
        String templateXMLText = entityElement.getAttributeValue("template");
        if (templateXMLText != null) {
            entityTemplateReader.loadTemplate(entityWorld, entity, parseTemplate(entityWorld, templateXMLText));
        }
        for (Element componentElement : entityElement.getChildren()) {
            if (isElementEnabled(entityWorld, componentElement)) {
                Object component = templateManager.constructComponent(this, entityWorld, componentElement);
                entityWorld.setComponent(entity, component);
            }
        }
    }

    public String parseTemplateText(EntityWorld entityWorld, String templateXMLText) {
        return parseTemplate(entityWorld, templateXMLText).getText();
    }

    public EntityTemplate parseTemplate(EntityWorld entityWorld, String templateXMLText) {
        String template = templateXMLText;
        if (template.startsWith("./")) {
            template = currentDirectories.lastElement() + template.substring(2);
        }
        return EntityTemplate.parseTemplate(template, text -> {
            if (text.startsWith("#")) {
                return text.substring(1);
            } else if (text.startsWith("[") && text.endsWith("]")) {
                return text.substring(1, text.length() - 1);
            }
            return text;
        }, key -> parseValue(entityWorld, key), value -> parseValue(entityWorld, value));
    }

    private boolean isElementEnabled(EntityWorld entityWorld, Element element) {
        String ifCondition = element.getAttributeValue("if");
        return ((ifCondition == null) || parseValueBoolean(entityWorld, ifCondition));
    }

    private boolean parseValueBoolean(EntityWorld entityWorld, String text) {
        String valueText = text;
        boolean inverted = false;
        if (valueText.startsWith("!")) {
            valueText = valueText.substring(1);
            inverted = true;
        }
        String value = parseValue(entityWorld, valueText);
        boolean isTruthy = ((!value.isEmpty()) && (!value.equals("false")) && (!value.equals("0")));
        return (isTruthy != inverted);
    }

    public String parseValue(EntityWorld entityWorld, String text) {
        if (text.startsWith("#")) {
            String entityId = text.substring(1);
            Integer entity = cachedEntities.lastElement().get(entityId);
            if (entity == null) {
                entity = createEntity(entityWorld, entityId);
            }
            return entity.toString();
        }
        HashMap<String, String> values = cachedValues.lastElement();
        for (Entry<String, String> valueEntry : values.entrySet()) {
            text = text.replaceAll("\\[" + valueEntry.getKey() + "\\]", valueEntry.getValue());
        }
        return text;
    }
}
