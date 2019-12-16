/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.models;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

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
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Carl
 */
public class ModelSkin{

    private ModelSkin(String filePath){
        loadFile(filePath);
    }
    private static ConcurrentHashMap<String, ModelSkin> cachedSkins = new ConcurrentHashMap<>();
    private static final String[] FILE_EXTENSIONS = new String[]{"j3o", "mesh.xml", "blend"};
    private Element modelElement;
    private Element positionElement;
    private Element materialElement;
    private Element modifiersElement;
    private String name;
    private float modelNormScale;
    private Vector3f modelScale;
    private String rigType;
    private float materialAmbient;
    private LinkedList<ModelModifier> modelModifiers = new LinkedList<>();

    public static ModelSkin get(String filePath) {
        return cachedSkins.computeIfAbsent(filePath, fp -> new ModelSkin(filePath));
    }

    private void loadFile(String filePath) {
        try {
            Document document = new SAXBuilder().build(new File(FileAssets.ROOT + filePath));
            Element rootElement = document.getRootElement();
            name = rootElement.getAttributeValue("name");
            modelElement = rootElement.getChild("model");
            positionElement = modelElement.getChild("position");
            materialElement = modelElement.getChild("material");
            modifiersElement = modelElement.getChild("modifiers");
            modelNormScale = getAttributeValue(modelElement, "normScale", 1);
            modelScale = getAttributeValue(modelElement, "scale", Vector3f.UNIT_XYZ);
            rigType = modelElement.getAttributeValue("rigType");
            materialAmbient = getAttributeValue(materialElement, "ambient", 0.15f);
        } catch (JDOMException | IOException ex) {
            System.out.println("Error while loading object skin '" + filePath + "': " + ex.getMessage());
        }
    }

    private boolean getAttributeValue(Element element, String attributeName, boolean defaultValue) {
        return (getAttributeValue(element, attributeName, (defaultValue?1:0)) == 1);
    }

    private float getAttributeValue(Element element, String attributeName, float defaultValue) {
        if (element != null) {
            Attribute attribute = element.getAttribute(attributeName);
            if(attribute != null){
                try {
                    return attribute.getFloatValue();
                } catch (DataConversionException ex) {
                }
            }
        }
        return defaultValue;
    }

    private Vector3f getAttributeValue(Element element, String attributeName, Vector3f defaultValue) {
        if (element != null) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                String[] coordinates = attribute.getValue().split(",");
                if (coordinates.length == 3) {
                    float x = Float.parseFloat(coordinates[0]);
                    float y = Float.parseFloat(coordinates[1]);
                    float z = Float.parseFloat(coordinates[2]);
                    return new Vector3f(x, y, z);
                } else {
                    try {
                        float value = attribute.getFloatValue();
                        return new Vector3f(value, value, value);
                    } catch(DataConversionException ex) {
                    }
                }
            }
        }
        return defaultValue;
    }

    public Spatial loadSpatial() {
        Spatial spatial = loadModel();
        loadMaterial(spatial);
        loadPosition(spatial);
        loadModifiers();
        applyGeometryInformation(spatial);
        return spatial;
    }

    private Spatial loadModel() {
        String modelPath = getModelFilePath();
        Spatial spatial = MaterialFactory.getAssetManager().loadModel(modelPath);
        spatial.setLocalScale(modelScale.mult(modelNormScale));
        if (getAttributeValue(modelElement, "generateTangents", false)) {
            TangentBinormalGenerator.generate(spatial);
        }
        return spatial;
    }

    private String getModelFilePath() {
        for (String FILE_EXTENSION : FILE_EXTENSIONS) {
            String modelFilePath = getModelFilePath(FILE_EXTENSION);
            if (FileAssets.exists(modelFilePath)) {
                return modelFilePath;
            }
        }
        return null;
    }

    private String getModelFilePath(String fileExtension) {
        return "Models/" + name + "/" + name + "." + fileExtension;
    }

    private void loadMaterial(Spatial spatial) {
        if (materialElement != null) {
            List<Element> materialElements = materialElement.getChildren();
            for (int i = 0; i < materialElements.size(); i++){
                Element currentMaterialElement = materialElements.get(i);
                String sourceName = currentMaterialElement.getAttributeValue("source", name);
                String materialDefintion = currentMaterialElement.getText();
                Material material = null;
                if (currentMaterialElement.getName().equals("color")) {
                    float[] colorComponents = Util.parseToFloatArray(materialDefintion.split(","));
                    ColorRGBA colorRGBA = new ColorRGBA(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                    material = MaterialFactory.generateLightingMaterial(colorRGBA);
                } else if(currentMaterialElement.getName().equals("texture")) {
                    String textureFilePath = (getResourcesFilePath(sourceName) + currentMaterialElement.getText());
                    material = MaterialFactory.generateLightingMaterial(textureFilePath);
                    //[jME 3.1 SNAPSHOT] Hardware skinning currently doesn't seem to support normal maps correctly
                    if (!Settings.getBoolean("hardware_skinning")) {
                        tryLoadTexture(material, "NormalMap", currentMaterialElement.getAttributeValue("normalMap"), sourceName);
                    }
                    tryLoadTexture(material, "AlphaMap", currentMaterialElement.getAttributeValue("alphaMap"), sourceName);
                    tryLoadTexture(material, "SpecularMap", currentMaterialElement.getAttributeValue("specularMap"), sourceName);
                    tryLoadTexture(material, "GlowMap", currentMaterialElement.getAttributeValue("glowMap"), sourceName);
                }
                if (material != null) {
                    String filter = currentMaterialElement.getAttributeValue("filter", "bilinear");
                    if (filter.equals("nearest")) {
                        MaterialFactory.setFilter_Nearest(material);
                    }
                    try {
                        Geometry child = (Geometry) JMonkeyUtil.getChild(spatial, i);
                        if (getAttributeValue(currentMaterialElement, "alpha", false)) {
                            child.setQueueBucket(RenderQueue.Bucket.Transparent);
                            MaterialFactory.setTransparent(material, true);
                        }
                        child.setMaterial(material);
                    } catch (Exception ex) {
                        System.out.println("Error while reading material for object '" + name + "': " + ex.getMessage());
                    }
                }
            }
        }
    }
   
    private void tryLoadTexture(Material material, String materialParameter, String textureName, String sourceName) {
        if (textureName != null) {
            Texture texture = MaterialFactory.loadTexture(getResourcesFilePath(sourceName) + textureName);
            material.setTexture(materialParameter, texture);
        }
    }
   
    private String getResourcesFilePath(String sourceName) {
        return "Models/" + sourceName + "/resources/";
    }

    private void loadPosition(Spatial spatial) {
        if (positionElement != null) {
            Element locationElement = positionElement.getChild("location");
            if (locationElement != null) {
                float[] location = Util.parseToFloatArray(locationElement.getText().split(","));
                spatial.setLocalTranslation(location[0], location[1], location[2]);
            }
            Element directionElement = positionElement.getChild("direction");
            if (directionElement != null) {
                float[] direction = Util.parseToFloatArray(directionElement.getText().split(","));
                JMonkeyUtil.setLocalRotation(spatial, new Vector3f(direction[0], direction[1], direction[2]));
            }
            Element rotationElement = positionElement.getChild("rotation");
            if (rotationElement != null) {
                float[] rotation = Util.parseToFloatArray(rotationElement.getText().split(","));
                spatial.rotate(new Quaternion(rotation[0], rotation[1], rotation[2], rotation[3]));
            }
        }
    }

    private void loadModifiers() {
        modelModifiers.clear();
        if (modifiersElement != null){
            for (Object childObject : modifiersElement.getChildren("modifier")) {
                Element modifierElement = (Element) childObject;
                ModelModifier modelModifier = Util.createObjectByClassName(modifierElement.getText(), ModelModifier.class);
                if (modelModifier != null) {
                    modelModifiers.add(modelModifier);
                }
            }
        }
    }

    public LinkedList<ModelModifier> getModelModifiers() {
        return modelModifiers;
    }

    private void applyGeometryInformation(Spatial spatial) {
        LinkedList<Geometry> geometryChilds = JMonkeyUtil.getAllGeometryChilds(spatial);
        for (Geometry geometry : geometryChilds) {
            Material material = geometry.getMaterial();
            MaterialFactory.generateAmbientColor(material, materialAmbient);
            material.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
            geometry.setUserData("layer", 6);
        }
    }

    public String getName() {
        return name;
    }

    public Vector3f getModelScale() {
        return modelScale;
    }

    public String getRigType() {
        return rigType;
    }
}
