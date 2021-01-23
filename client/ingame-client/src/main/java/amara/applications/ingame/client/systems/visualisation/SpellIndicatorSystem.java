package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent;
import amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent;
import amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.LinkedList;
import java.util.function.Supplier;

public class SpellIndicatorSystem implements EntitySystem {

    public SpellIndicatorSystem(Supplier<Integer> playerEntity, EntitySceneMap entitySceneMap, AssetManager assetManager) {
        this.playerEntity = playerEntity;
        this.entitySceneMap = entitySceneMap;
        this.assetManager = assetManager;
    }
    private Supplier<Integer> playerEntity;
    private EntitySceneMap entitySceneMap;
    private AssetManager assetManager;
    private int currentSpellEntity = -1;
    private LinkedList<Spatial> currentIndicators = new LinkedList<>();
    private boolean updateIndicator;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds ){
        if (updateIndicator) {
            SpellIndicatorComponent spellIndicatorComponent = entityWorld.getComponent(currentSpellEntity, SpellIndicatorComponent.class);
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity.get(), PlayerCharacterComponent.class);
            if ((spellIndicatorComponent != null) && (playerCharacterComponent != null)) {
                Node node = entitySceneMap.requestNode(playerCharacterComponent.getEntity());
                CircularIndicatorComponent circularIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), CircularIndicatorComponent.class);
                if (circularIndicatorComponent != null) {
                    float diameter = (2 * circularIndicatorComponent.getRadius());
                    float x = (circularIndicatorComponent.getX() - circularIndicatorComponent.getRadius());
                    float y = (circularIndicatorComponent.getY() + circularIndicatorComponent.getRadius());
                    currentIndicators.add(GroundTextures.create(assetManager, "Textures/spell_indicators/circular.png", x, y, diameter, diameter, RenderState.BlendMode.AlphaAdditive));
                }
                LinearIndicatorComponent linearIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), LinearIndicatorComponent.class);
                if (linearIndicatorComponent != null) {
                    float width = linearIndicatorComponent.getWidth();
                    float heightTarget = Math.min(5, linearIndicatorComponent.getHeight());
                    float heightBase = (linearIndicatorComponent.getHeight() - heightTarget);
                    float x = (linearIndicatorComponent.getX() - (linearIndicatorComponent.getWidth() / 2));
                    float yBase = (linearIndicatorComponent.getY() + heightBase);
                    float yTarget = (linearIndicatorComponent.getY() + heightBase + heightTarget);
                    Geometry indicatorBase = GroundTextures.create(assetManager, "Textures/spell_indicators/linear_base.png", x, yBase, width, heightBase, RenderState.BlendMode.AlphaAdditive);
                    Geometry indicatorTarget = GroundTextures.create(assetManager, "Textures/spell_indicators/linear_target.png", x, yTarget, width, heightTarget, RenderState.BlendMode.AlphaAdditive);
                    // Ensure that the textures connect seamless (The textures have a high enough resolution that it still looks ok)
                    MaterialFactory.setFilter_Nearest(indicatorBase.getMaterial());
                    MaterialFactory.setFilter_Nearest(indicatorTarget.getMaterial());
                    currentIndicators.add(indicatorBase);
                    currentIndicators.add(indicatorTarget);
                }
                for (Spatial indicator : currentIndicators) {
                    node.attachChild(indicator);
                }
            }
            updateIndicator = false;
        }
    }

    public void showIndicator(int spellEntity) {
        hideIndicator();
        currentSpellEntity = spellEntity;
        updateIndicator = true;
    }

    public void hideIndicator() {
        if (currentSpellEntity != -1) {
            currentSpellEntity = -1;
            for (Spatial indicator : currentIndicators) {
                indicator.removeFromParent();
            }
            currentIndicators.clear();
            updateIndicator = false;
        }
    }
}
