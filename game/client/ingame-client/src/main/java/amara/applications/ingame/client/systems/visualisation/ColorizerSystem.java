/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.client.systems.visualisation.colors.ColorizingInfo;
import amara.applications.ingame.client.systems.visualisation.colors.MaterialColorizer;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class ColorizerSystem implements EntitySystem {

    public ColorizerSystem(EntitySceneMap entitySceneMap) {
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;
    private HashMap<Integer, ColorizingInfo> entityColorizingInfo = new HashMap<>();
    private LinkedList<Integer> changedEntities = new LinkedList<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : changedEntities) {
            updateMaterial(entity);
        }
        changedEntities.clear();
    }

    private void updateMaterial(int entity) {
        ColorizingInfo colorizingInfo = getColorizingInfo(entity);
        boolean isColorized = (colorizingInfo.getColorizers().size() > 0);
        ColorRGBA color = null;
        if (isColorized) {
            color = getColor(colorizingInfo.getColorizers());
        } else {
            entityColorizingInfo.remove(entity);
        }
        Node node = entitySceneMap.requestNode(entity);
        for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(node)) {
            // Update color
            boolean wasModified = false;
            Material material = geometry.getMaterial();
            if (MaterialFactory.DEFINITION_NAME_LIGHTING.equals(material.getMaterialDef().getAssetName())) {
                if (isColorized) {
                    material.setBoolean("UseMaterialColors", true);
                    material.setColor("Diffuse", color);
                } else {
                    material.clearParam("UseMaterialColors");
                    material.clearParam("Diffuse");
                }
                wasModified = true;
            } else if (MaterialFactory.DEFINITION_NAME_UNSHADED.equals(material.getMaterialDef().getAssetName())) {
                if (isColorized) {
                    material.setColor("Color", color);
                } else {
                    material.clearParam("Color");
                }
                wasModified = true;
            }
            // Update transparency
            if (wasModified) {
                RenderQueue.Bucket queueBucket = colorizingInfo.getQueueBucket(geometry);
                if (queueBucket == RenderQueue.Bucket.Opaque) {
                    boolean isTransparent = (isColorized && (color.getAlpha() < 1));
                    MaterialFactory.setTransparent(material, isTransparent);
                    if (isTransparent) {
                        queueBucket = RenderQueue.Bucket.Transparent;
                    }
                }
                geometry.setQueueBucket(queueBucket);
            }
        }
    }

    private ColorRGBA getColor(LinkedList<MaterialColorizer> colorizers) {
        ColorRGBA color = ColorRGBA.White.clone();
        for (MaterialColorizer colorizer : colorizers) {
            colorizer.modifyColor(color);
        }
        return color;
    }

    public void addColorizer(int entity, MaterialColorizer colorizer) {
        ColorizingInfo colorizingInfo = entityColorizingInfo.computeIfAbsent(entity, e -> {
            ColorizingInfo newColorizingInfo = new ColorizingInfo();
            Node node = entitySceneMap.requestNode(entity);
            for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(node)) {
                newColorizingInfo.putQueueBucket(geometry, geometry.getQueueBucket());
            }
            return newColorizingInfo;
        });
        colorizingInfo.addColorizer(colorizer);
        changedEntities.add(entity);
    }

    public void removeColorizer(int entity, Class<? extends MaterialColorizer> colorizerClass) {
        LinkedList<MaterialColorizer> colorizers = getColorizingInfo(entity).getColorizers();
        colorizers.stream()
            .filter(colorizer -> colorizerClass.isAssignableFrom(colorizerClass))
            .findAny()
            .ifPresent(colorizer -> {
                colorizers.remove(colorizer);
                changedEntities.add(entity);
            });
    }

    private ColorizingInfo getColorizingInfo(int entity) {
        return entityColorizingInfo.get(entity);
    }
}
