/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateUpgradeSpellsPanelSystem extends GUIDisplaySystem{

    public UpdateUpgradeSpellsPanelSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    private boolean isUpdateRequired;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        isUpdateRequired = false;
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class, SpellsUpgradePointsComponent.class);
        checkChangedComponent(observer.getNew().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedComponent(observer.getChanged().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedComponent(observer.getNew().getComponent(selectedEntity, SpellsUpgradePointsComponent.class));
        checkChangedComponent(observer.getChanged().getComponent(selectedEntity, SpellsUpgradePointsComponent.class));
        if(isUpdateRequired){
            updateUpgradeSpellsPanel(entityWorld, selectedEntity);
        }
    }
    
    private void checkChangedComponent(Object changedComponent){
        if(changedComponent != null){
            isUpdateRequired = true;
        }
    }
    
    private void updateUpgradeSpellsPanel(EntityWorld entityWorld, int selectedEntity){
        SpellsComponent spellsComponent = entityWorld.getComponent(selectedEntity, SpellsComponent.class);
        SpellsUpgradePointsComponent spellsUpgradePointsComponent = entityWorld.getComponent(selectedEntity, SpellsUpgradePointsComponent.class);
        if((spellsComponent != null) && (spellsUpgradePointsComponent != null)){
            boolean showLayer = false;
            boolean showButton;
            boolean learnOrUpgrade = false;
            for(int i=0;i<4;i++){
                showButton = false;
                if(SpellUtil.canUpgradeSpell(entityWorld, selectedEntity, i)){
                    showButton = true;
                    learnOrUpgrade = false;
                }
                else if(SpellUtil.canLearnSpell(entityWorld, selectedEntity, i)){
                    showButton = true;
                    learnOrUpgrade = true;
                }
                screenController_HUD.setUpgradeSpellsButtonVisible(i, showButton);
                if(showButton){
                    screenController_HUD.setUpgradeSpellsButtonImage(i, "Interface/hud/" + (learnOrUpgrade?"learn":"upgrade") + "_spell_button.png");
                    showLayer = true;
                }
            }
            screenController_HUD.setUpgradeSpellsLayerVisible(showLayer);
        }
    }
}
