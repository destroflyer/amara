package amara.libraries.entitysystem.templates;

import amara.core.files.FileAssets;
import amara.libraries.entitysystem.EntityWorld;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.HashMap;

public class XMLTemplateManager {

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

    public <T> void registerComponent(XMLComponentConstructor<T> xmlComponentConstructor) {
        xmlComponentConstructors.put(xmlComponentConstructor.getElementName(), xmlComponentConstructor);
    }

    public void loadTemplate(XMLTemplateReader templateReader, EntityWorld entityWorld, int entity, EntityTemplate entityTemplate) {
        String templateFilePath = (filePath + entityTemplate.getName() + ".xml");
        Document document = getDocument(templateFilePath);
        if (document != null) {
            templateReader.loadTemplate(entityWorld, entity, entityTemplate, document);
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

    public <T> T constructComponent(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element) {
        XMLComponentConstructor<T> xmlComponentConstructor = (XMLComponentConstructor<T>) xmlComponentConstructors.get(element.getName());
        if (xmlComponentConstructor != null) {
            return xmlComponentConstructor.construct(templateReader, entityWorld, element);
        }
        throw new IllegalArgumentException("Unregistered component '" + element.getName() + "'");
    }
}
