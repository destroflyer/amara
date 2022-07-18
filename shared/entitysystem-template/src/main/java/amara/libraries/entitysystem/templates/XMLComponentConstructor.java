package amara.libraries.entitysystem.templates;

import java.util.LinkedList;
import java.util.List;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jdom2.Element;

@AllArgsConstructor
public abstract class XMLComponentConstructor<T> {

    @Getter
    private String elementName;

    public abstract T construct(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element);

    protected int[] createChildEntities(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element, int offset, String parameterName) {
        LinkedList<Integer> childEntities = new LinkedList<>();
        List<Element> children = element.getChildren();
        if (children.size() > 0) {
            for (Element child : children) {
                int childEntity = templateReader.createAndLoadEntity(entityWorld, child);
                if (childEntity != -1) {
                    childEntities.add(childEntity);
                }
            }
        } else if (element.getText().length() > 0) {
            String[] textParts = element.getText().split(",");
            for (String textPart : textParts) {
                childEntities.add(parseEntity(templateReader, entityWorld, textPart));
            }
        }
        int parameterIndex = 0;
        String attributeValue;
        while ((attributeValue = element.getAttributeValue(parameterName + parameterIndex)) != null) {
            childEntities.add(parseEntity(templateReader, entityWorld, attributeValue));
            parameterIndex++;
        }
        return Util.convertToArray_Integer(childEntities);
    }

    protected int createChildEntity(XMLTemplateReader templateReader, EntityWorld entityWorld, Element element, int index, String parameterName) {
        List<Element> children = element.getChildren();
        if (children.size() > 0) {
            if (index < children.size()) {
                return templateReader.createAndLoadEntity(entityWorld, children.get(index));
            }
        } else if ((index == 0) && (element.getText().length() > 0)) {
            return parseEntity(templateReader, entityWorld, element.getText());
        }
        return parseEntity(templateReader, entityWorld, element.getAttributeValue(parameterName));
    }

    private int parseEntity(XMLTemplateReader templateReader, EntityWorld entityWorld, String text) {
        return Integer.parseInt(templateReader.parseValue(entityWorld, text));
    }
}
