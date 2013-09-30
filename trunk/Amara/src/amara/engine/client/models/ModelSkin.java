/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.models;


import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.Util;
import amara.engine.JMonkeyUtil;
import amara.engine.client.MaterialFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.Attribute;

/**
 *
 * @author Carl
 */
public class ModelSkin{
    
    public ModelSkin(String fileResourcePath){
        this.fileResourceURL = Util.getResourceURL(fileResourcePath);
        loadFile();
    }
    private static final String[] FILE_EXTENSIONS = new String[]{"j3o", "mesh.xml", "blend"};
    private URL fileResourceURL;
    private Element rootElement;
    private Element modelElement;
    private Element positionElement;
    private Element materialElement;
    private String name;
    private float modelScale;
    private float materialAmbient;
    
    private void loadFile(){
        try{
            Document document = new SAXBuilder().build(fileResourceURL);
            rootElement = document.getRootElement();
            name = rootElement.getAttributeValue("name");
            modelElement = rootElement.getChild("model");
            positionElement = modelElement.getChild("position");
            materialElement = modelElement.getChild("material");
            modelScale = getAttributeValue(modelElement, "scale", 1);
            materialAmbient = getAttributeValue(materialElement, "ambient", 0.15f);
        }catch(Exception ex){
            System.out.println("Error while loading object skin '" + fileResourceURL + "'");
        }
    }
    
    private boolean getAttributeValue(Element element, String attributeName, boolean defaultValue){
        return (getAttributeValue(element, attributeName, (defaultValue?1:0)) == 1);
    }
    
    private float getAttributeValue(Element element, String attributeName, float defaultValue){
        if(element != null){
            Attribute attribute = element.getAttribute(attributeName);
            if(attribute != null){
                try{
                    return attribute.getFloatValue();
                }catch(Exception ex){
                }
            }
        }
        return defaultValue;
    }
    
    public Spatial loadSpatial(){
        Spatial spatial = loadModel();
        loadMaterial(spatial);
        loadPosition(spatial);
        applyGeometryInformation(spatial);
        return spatial;
    }
    
    private Spatial loadModel(){
        String modelPath = getModelFilePath();
        Spatial spatial = MaterialFactory.getAssetManager().loadModel(modelPath);
        spatial.setLocalScale(modelScale);
        return spatial;
    }
    
    private String getModelFilePath(){
        for(int i=0;i<FILE_EXTENSIONS.length;i++){
            String modelFilePath = getModelFilePath(FILE_EXTENSIONS[i]);
            if(Util.existsResource("/" + modelFilePath)){
                return modelFilePath;
            }
        }
        return null;
    }
    
    private String getModelFilePath(String fileExtension){
        return "Models/" + name + "/" + name + "." + fileExtension;
    }
    
    private void loadMaterial(Spatial spatial){
        if(materialElement != null){
            List<Element> materialElements = materialElement.getChildren();
            for(int i=0;i<materialElements.size();i++){
                Element currentMaterialElement = materialElements.get(i);
                String materialDefintion = currentMaterialElement.getText();
                Material material = null;
                if(currentMaterialElement.getName().equals("color")){
                    float[] colorComponents = Util.parseToFloatArray(materialDefintion.split(","));
                    ColorRGBA colorRGBA = new ColorRGBA(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                    material = MaterialFactory.generateLightingMaterial(colorRGBA);
                }
                else if(currentMaterialElement.getName().equals("texture")){
                    String textureFilePath = getResourcesFilePath() + currentMaterialElement.getText();
                    material = MaterialFactory.generateUnshadedMaterial(textureFilePath);
                }
                if(material != null){
                    String filter = currentMaterialElement.getAttributeValue("filter", "bilinear");
                    if(filter.equals("nearest")){
                        MaterialFactory.setFilter_Nearest(material);
                    }
                    try{
                        int childIndex = currentMaterialElement.getAttribute("index").getIntValue();
                        Geometry child = (Geometry) JMonkeyUtil.getChild(spatial, childIndex);
                        child.setMaterial(material);
                    }catch(Exception ex){
                        System.out.println("Error while reading material for object '" + name + "'");
                    }
                }
            }
        }
    }
    
    private String getResourcesFilePath(){
        return "Models/" + name + "/resources/";
    }
    
    private void loadPosition(Spatial spatial){
        if(positionElement != null){
            Element locationElement = positionElement.getChild("location");
            if(locationElement != null){
                float[] location = Util.parseToFloatArray(locationElement.getText().split(","));
                spatial.setLocalTranslation(location[0], location[1], location[2]);
            }
            Element directionElement = positionElement.getChild("direction");
            if(directionElement != null){
                float[] direction = Util.parseToFloatArray(directionElement.getText().split(","));
                JMonkeyUtil.setLocalRotation(spatial, new Vector3f(direction[0], direction[1], direction[2]));
            }
        }
    }
    
    private void applyGeometryInformation(Spatial spatial){
        LinkedList<Geometry> geometryChilds = JMonkeyUtil.getAllGeometryChilds(spatial);
        for(int i=0;i<geometryChilds.size();i++){
            Geometry geometry = geometryChilds.get(i);
            Material material = geometry.getMaterial();
            MaterialFactory.generateAmbientColor(material, materialAmbient);
            material.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
            RigidBodyControl rigidBodyControl = geometry.getControl(RigidBodyControl.class);
            if(rigidBodyControl != null){
                rigidBodyControl.getCollisionShape().setScale(new Vector3f(modelScale, modelScale, modelScale));
            }
            geometry.setUserData("layer", 2);
        }
    }
    
    public String getIconFilePath(){
        String iconFilePath = "Models/" + name + "/icon.jpg";
        if(!Util.existsResource("/" + iconFilePath)){
            iconFilePath = "Interface/images/icon_unknown.jpg";
        }
        return iconFilePath;
    }

    public String getName(){
        return name;
    }

    public float getModelScale(){
        return modelScale;
    }
    
    public float getMaterialAmbient(){
        return materialAmbient;
    }
}
