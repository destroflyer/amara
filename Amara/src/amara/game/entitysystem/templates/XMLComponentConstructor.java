/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.game.entitysystem.templates;

import java.util.List;
import amara.game.entitysystem.EntityWorld;
import org.jdom.Element;

/**
 *
 * @author Carl
 */
public abstract class XMLComponentConstructor<T>{

    public XMLComponentConstructor(String elementName){
        this.elementName = elementName;
    }
    private String elementName;
    private XMLTemplateManager xmlTemplateManager;
    private EntityWorld entityWorld;
    protected Element element;
    
    public void prepare(XMLTemplateManager xmlTemplateManager, EntityWorld entityWorld, Element element){
        this.xmlTemplateManager = xmlTemplateManager;
        this.entityWorld = entityWorld;
        this.element = element;
    }
    
    public abstract T construct();

    protected int createChildEntity(int index){
        List children = element.getChildren();
        if(children.size() > 0){
            return xmlTemplateManager.createEntity(entityWorld, (Element) children.get(index));
        }
        else if(index == 0){
            return xmlTemplateManager.getCachedEntities().get(element.getText().substring(1));
        }
        return -1;
    }

    protected int[] createChildEntities(int offset){
        int[] childEntities = new int[element.getChildren().size()];
        for(int i=offset;i<element.getChildren().size();i++){
            Element childElement = (Element) element.getChildren().get(i);
            childEntities[i] = xmlTemplateManager.createEntity(entityWorld, childElement);
        }
        return childEntities;
    }
    
    public String getElementName(){
        return elementName;
    }
}
