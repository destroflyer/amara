package amara.applications.ingame.client.systems.visualisation;

import amara.core.settings.Settings;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.ingame.appstates.IngameMouseCursorAppState;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import amara.libraries.entitysystem.*;

import java.util.function.Supplier;

public class MarkHoveredUnitsSystem implements EntitySystem {

    public MarkHoveredUnitsSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem, IngameMouseCursorAppState ingameMouseCursorAppState, Supplier<Integer> cursorHoveredEntitySupplier, AssetManager assetManager) {
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
        this.ingameMouseCursorAppState = ingameMouseCursorAppState;
        this.cursorHoveredEntitySupplier = cursorHoveredEntitySupplier;
        this.assetManager = assetManager;
    }
    private final static String NODE_NAME_MARKER = "hoveredMarker";
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;
    private IngameMouseCursorAppState ingameMouseCursorAppState;
    private Supplier<Integer> cursorHoveredEntitySupplier;
    private AssetManager assetManager;
    private ColorRGBA colorAllies = new ColorRGBA(0.1f, 1, 0.1f, 1);
    private ColorRGBA colorEnemies = new ColorRGBA(1, 0.1f, 0.1f, 1);
    private int markedEntity = -1;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        int cursorHoveredEntity = cursorHoveredEntitySupplier.get();
        if (cursorHoveredEntity != markedEntity) {
            if (markedEntity != -1) {
                removeMarker(markedEntity);
            }
            if (cursorHoveredEntity != -1) {
                addMarker(entityWorld, cursorHoveredEntity);
            }
            markedEntity = cursorHoveredEntity;
        }
    }

    private void removeMarker(int entity) {
        ingameMouseCursorAppState.setCursor_Default();
        Node node = entitySceneMap.requestNode(entity);
        ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
        // Model can be already removed if entity died while being hovered (PlayerAppState is executed before LocalEntitySystemAppState)
        if (modelObject != null) {
            Spatial marker = modelObject.getChild(NODE_NAME_MARKER);
            modelObject.unregisterModel(marker);
        }
    }

    private void addMarker(EntityWorld entityWorld, int entity) {
        boolean isAllied = playerTeamSystem.isAllied(entityWorld, entity);
        if (isAllied) {
            ingameMouseCursorAppState.setCursor_Default();
        } else {
            ingameMouseCursorAppState.setCursor_Enemy();
        }
        float hoverOutlineThickness = Settings.getFloat("hover_outline_thickness");
        if (hoverOutlineThickness > 0) {
            Node node = entitySceneMap.requestNode(entity);
            ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
            // Model can be already removed if entity died while being started to be hovered (PlayerAppState is executed before LocalEntitySystemAppState)
            if (modelObject != null) {
                RegisteredModel clonedModel = modelObject.loadAndRegisterModel();
                clonedModel.getNode().setName(NODE_NAME_MARKER);
                for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel.getNode())) {
                    Material material = new Material(assetManager, "Shaders/cartoonedge/matdefs/cartoonedge.j3md");
                    material.setColor("EdgesColor", (isAllied ? colorAllies : colorEnemies));
                    material.setFloat("EdgeSize", hoverOutlineThickness / geometry.getWorldScale().getY());
                    geometry.setMaterial(material);
                }
                JMonkeyUtil.setHardwareSkinningPreferred(clonedModel.getNode(), false);
            }
        }
    }
}
