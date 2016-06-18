/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.models;


import java.io.File;
import java.util.List;
import java.util.LinkedList;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;
import amara.core.Util;
import amara.core.files.FileAssets;
import amara.core.settings.Settings;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.Attribute;
import org.jdom.DataConversionException;

/**
 *
 * @author Carl
 */
public class ModelSkin{
   
    public ModelSkin(String skinPath){
        this.filePath = skinPath;
        loadFile();
    }
    private static final String[] FILE_EXTENSIONS = new String[]{"j3o", "mesh.xml", "blend"};
    private String filePath;
    private Element rootElement;
    private Element modelElement;
    private Element positionElement;
    private Element materialElement;
    private Element modifiersElement;
    private String name;
    private float modelNormScale;
    private Vector3f modelScale;
    private String rigType;
    private float materialAmbient;
    private LinkedList<ModelModifier> modelModifiers = new LinkedList<ModelModifier>();
   
    private void loadFile(){
        try{
            Document document = new SAXBuilder().build(new File(FileAssets.ROOT + filePath));
            rootElement = document.getRootElement();
            name = rootElement.getAttributeValue("name");
            modelElement = rootElement.getChild("model");
            positionElement = modelElement.getChild("position");
            materialElement = modelElement.getChild("material");
            modifiersElement = modelElement.getChild("modifiers");
            modelNormScale = getAttributeValue(modelElement, "normScale", 1);
            modelScale = getAttributeValue(modelElement, "scale", Vector3f.UNIT_XYZ);
            rigType = modelElement.getAttributeValue("rigType");
            materialAmbient = getAttributeValue(materialElement, "ambient", 0.15f);
        }catch(Exception ex){
            System.out.println("Error while loading object skin '" + filePath + "'");
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
                }catch(DataConversionException ex){
                }
            }
        }
        return defaultValue;
    }
   
    private Vector3f getAttributeValue(Element element, String attributeName, Vector3f defaultValue){
        if(element != null){
            Attribute attribute = element.getAttribute(attributeName);
            if(attribute != null){
                String[] coordinates = attribute.getValue().split(",");
                if(coordinates.length == 3){
                    float x = Float.parseFloat(coordinates[0]);
                    float y = Float.parseFloat(coordinates[1]);
                    float z = Float.parseFloat(coordinates[2]);
                    return new Vector3f(x, y, z);
                }
                else{
                    try{
                        float value = attribute.getFloatValue();
                        return new Vector3f(value, value, value);
                    }catch(DataConversionException ex){
                    }
                }
            }
        }
        return defaultValue;
    }
   
    public Spatial loadSpatial(){
        Spatial spatial = loadModel();
        loadMaterial(spatial);
        loadPosition(spatial);
        loadModifiers();
        applyGeometryInformation(spatial);
        return spatial;
    }
   
    private Spatial loadModel(){
        String modelPath = getModelFilePath();
        Spatial spatial = MaterialFactory.getAssetManager().loadModel(modelPath);
        spatial.setLocalScale(modelScale.mult(modelNormScale));
        if(getAttributeValue(modelElement, "generateTangents", false)){
            TangentBinormalGenerator.generate(spatial);
        }
        return spatial;
    }
   
    private String getModelFilePath(){
        for(int i=0;i<FILE_EXTENSIONS.length;i++){
            String modelFilePath = getModelFilePath(FILE_EXTENSIONS[i]);
            if(FileAssets.exists(modelFilePath)){
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
                String sourceName = currentMaterialElement.getAttributeValue("source", name);
                String materialDefintion = currentMaterialElement.getText();
                Material material = null;
                if(currentMaterialElement.getName().equals("color")){
                    float[] colorComponents = Util.parseToFloatArray(materialDefintion.split(","));
                    ColorRGBA colorRGBA = new ColorRGBA(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                    material = MaterialFactory.generateLightingMaterial(colorRGBA);
                }
                else if(currentMaterialElement.getName().equals("texture")){
                    String textureFilePath = (getResourcesFilePath(sourceName) + currentMaterialElement.getText());
                    material = MaterialFactory.generateLightingMaterial(textureFilePath);
                    //[jME 3.1 SNAPSHOT] Hardware skinning currently doesn't seem to support normal maps correctly
                    if(!Settings.getBoolean("hardware_skinning")){
                        loadTexture(material, "NormalMap", currentMaterialElement.getAttributeValue("normalMap"), sourceName);
                    }
                    loadTexture(material, "AlphaMap", currentMaterialElement.getAttributeValue("alphaMap"), sourceName);
                    loadTexture(material, "SpecularMap", currentMaterialElement.getAttributeValue("specularMap"), sourceName);
                    loadTexture(material, "GlowMap", currentMaterialElement.getAttributeValue("glowMap"), sourceName);
                }
                if(material != null){
                    String filter = currentMaterialElement.getAttributeValue("filter", "bilinear");
                    if(filter.equals("nearest")){
                        MaterialFactory.setFilter_Nearest(material);
                    }
                    try{
                        Geometry child = (Geometry) JMonkeyUtil.getChild(spatial, i);
                        if(getAttributeValue(currentMaterialElement, "alpha", false)){
                            child.setQueueBucket(RenderQueue.Bucket.Transparent);
                            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                        }
                        child.setMaterial(material);
                    }catch(Exception ex){
                        System.out.println("Error while reading material for object '" + name + "'");
                    }
                }
            }
        }
    }
   
    private void loadTexture(Material material, String materialParameter, String textureName, String sourceName){
        if(textureName != null){
            Texture texture = MaterialFactory.loadTexture(getResourcesFilePath(sourceName) + textureName);
            material.setTexture(materialParameter, texture);
        }
    }
   
    private String getResourcesFilePath(String sourceName){
        return "Models/" + sourceName + "/resources/";
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
            Element rotationElement = positionElement.getChild("rotation");
            if(rotationElement != null){
                float[] rotation = Util.parseToFloatArray(rotationElement.getText().split(","));
                spatial.rotate(new Quaternion(rotation[0], rotation[1], rotation[2], rotation[3]));
            }
        }
    }
   
    private void loadModifiers(){
        modelModifiers.clear();
        if(modifiersElement != null){
            for(Object childObject : modifiersElement.getChildren("modifier")){
                Element modifierElement = (Element) childObject;
                ModelModifier modelModifier = Util.createObjectByClassName(modifierElement.getText(), ModelModifier.class);
                if(modelModifier != null){
                    modelModifiers.add(modelModifier);
                }
            }
        }
    }

    public LinkedList<ModelModifier> getModelModifiers(){
        return modelModifiers;
    }
   
    private void applyGeometryInformation(Spatial spatial){
        LinkedList<Geometry> geometryChilds = JMonkeyUtil.getAllGeometryChilds(spatial);
        //Vector3f scaleVector = modelScale.mult(modelNormScale);
        for(int i=0;i<geometryChilds.size();i++){
            Geometry geometry = geometryChilds.get(i);
            Material material = geometry.getMaterial();
            MaterialFactory.generateAmbientColor(material, materialAmbient);
            material.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
            //We don't need bullet physics in Amara yet
            /*RigidBodyControl rigidBodyControl = geometry.getControl(RigidBodyControl.class);
            if(rigidBodyControl != null){
                rigidBodyControl.getCollisionShape().setScale(scaleVector);
            }*/
            geometry.setUserData("layer", 3);
        }
    }
   
    public String getIconFilePath(){
        String iconFilePath = "Models/" + name + "/icon.jpg";
        if(!FileAssets.exists(iconFilePath)){
            iconFilePath = "Interface/images/icon_unknown.jpg";
        }
        return iconFilePath;
    }

    public String getName(){
        return name;
    }

    public Vector3f getModelScale(){
        return modelScale;
    }

    public float getModelNormScale(){
        return modelNormScale;
    }

    public String getRigType(){
        return rigType;
    }
   
    public float getMaterialAmbient(){
        return materialAmbient;
    }
}
