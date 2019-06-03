/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.libraries.entitysystem.templates;

import java.util.LinkedList;
import java.util.List;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;
import org.jdom2.Element;

/**
 *
 * @author Carl
 */
public abstract class XMLComponentConstructor<T>{

    public XMLComponentConstructor(String elementName){
        this.elementName = elementName;
    }
    private String elementName;
    protected XMLTemplateManager xmlTemplateManager;

    public void setXmlTemplateManager(XMLTemplateManager xmlTemplateManager) {
        this.xmlTemplateManager = xmlTemplateManager;
    }

    public abstract T construct(EntityWorld entityWorld, Element element);

    protected int[] createChildEntities(EntityWorld entityWorld, Element element, int offset, String parameterName){
        LinkedList<Integer> childEntities = new LinkedList<>();
        List children = element.getChildren();
        if(children.size() > 0){
            for(int i=0;i<children.size();i++){
                childEntities.add(xmlTemplateManager.createAndLoadEntity(entityWorld, (Element) children.get(i)));
            }
        }
        else if(element.getText().length() > 0){
            String[] textParts = element.getText().split(",");
            for(String textPart : textParts){
                childEntities.add(parseEntity(entityWorld, textPart));
            }
        }
        int parameterIndex = 0;
        String attributeValue;
        while((attributeValue = element.getAttributeValue(parameterName + parameterIndex)) != null){
            childEntities.add(parseEntity(entityWorld, attributeValue));
            parameterIndex++;
        }
        return Util.convertToArray_Integer(childEntities);
    }

    protected int createChildEntity(EntityWorld entityWorld, Element element, int index, String parameterName){
        List children = element.getChildren();
        if(children.size() > 0){
            if(index < children.size()){
                return xmlTemplateManager.createAndLoadEntity(entityWorld, (Element) children.get(index));
            }
        }
        else if((index == 0) && (element.getText().length() > 0)){
            return parseEntity(entityWorld, element.getText());
        }
        return parseEntity(entityWorld, element.getAttributeValue(parameterName));
    }

    private int parseEntity(EntityWorld entityWorld, String text){
        return Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, text));
    }

    public String getElementName(){
        return elementName;
    }
}
