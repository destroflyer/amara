package amara.libraries.entitysystem.templates;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import amara.core.files.FileAssets;
import amara.libraries.entitysystem.EntityWorld;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLTemplateManager{

    public static XMLTemplateManager getInstance() {
        if (instance == null) {
            instance = new XMLTemplateManager("Templates/");
        }
        return instance;
    }

    private XMLTemplateManager(String filePath) {
        this.filePath = filePath;
    }
    private static XMLTemplateManager instance;
    private String filePath;
    private HashMap<String, XMLComponentConstructor<?>> xmlComponentConstructors = new HashMap<>();
    private HashMap<String, Document> cachedDocuments = new HashMap<>();
    private Stack<String> currentDirectories = new Stack<>();
    private Stack<HashMap<String, Integer>> cachedEntities = new Stack<>();
    private Stack<HashMap<String, String>> cachedValues = new Stack<>();

    public <T> void registerComponent(XMLComponentConstructor<T> xmlComponentConstructor){
        xmlComponentConstructor.setXmlTemplateManager(this);
        xmlComponentConstructors.put(xmlComponentConstructor.getElementName(), xmlComponentConstructor);
    }

    public void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate entityTemplate) {
        String templateFilePath = (filePath + entityTemplate.getName() + ".xml");
        Document document = getDocument(templateFilePath);
        if (document != null) {
            loadTemplate(entityWorld, entity, entityTemplate, document);
        }
    }

    private Document getDocument(String filePath) {
        Document document = cachedDocuments.get(filePath);
        if ((document == null) && FileAssets.exists(filePath)){
            try {
                InputStream inputStream = FileAssets.getInputStream(filePath);
                document = new SAXBuilder().build(inputStream);
            } catch (Exception ex) {
                System.err.println("Error while loading template file '" + filePath + "'.");
            }
            cachedDocuments.put(filePath, document);
        }
        return document;
    }

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
        for (Map.Entry<String, String> parameterEntry : template.getInput().entrySet()) {
            values.put(parameterEntry.getKey(), parameterEntry.getValue());
        }
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
            for (Map.Entry<String, String> output : template.getOutput().entrySet()) {
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
            EntityTemplate.loadTemplate(entityWorld, entity, parseTemplate(entityWorld, templateXMLText));
        }
        for (Element componentElement : entityElement.getChildren()) {
            if (isElementEnabled(entityWorld, componentElement)) {
                Object component = constructComponent(entityWorld, componentElement);
                if (component != null) {
                    entityWorld.setComponent(entity, component);
                }
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

    private <T> T constructComponent(EntityWorld entityWorld, Element element) {
        XMLComponentConstructor<T> xmlComponentConstructor = (XMLComponentConstructor<T>) xmlComponentConstructors.get(element.getName());
        if (xmlComponentConstructor != null) {
            return xmlComponentConstructor.construct(entityWorld, element);
        }
        System.err.println("Unregistered component '" + element.getName() + "'");
        return null;
    }
}
