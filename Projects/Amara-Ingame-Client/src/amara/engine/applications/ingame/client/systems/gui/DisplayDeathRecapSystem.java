/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.core.Util;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class DisplayDeathRecapSystem extends GUIDisplaySystem{

    public DisplayDeathRecapSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        if(observer.getRemoved().hasComponent(selectedEntity, IsAliveComponent.class)){
            String text = "[No damage history existing]";
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(selectedEntity, DamageHistoryComponent.class);
            if(damageHistoryComponent != null){
                float totalDamage = 0;
                for(int i=0;i<damageHistoryComponent.getEntries().length;i++){
                    totalDamage += damageHistoryComponent.getEntries()[i].getDamage();
                }
                text = ((int) totalDamage) + " total damage over " + Util.round(damageHistoryComponent.getLastDamageTime() - damageHistoryComponent.getFirstDamageTime(), 3) + " seconds\n";
                for(int i=0;i<damageHistoryComponent.getEntries().length;i++){
                    DamageHistoryComponent.DamageHistoryEntry entry = damageHistoryComponent.getEntries()[i];
                    String sourceName = null;
                    if(entry.getSourceEntity() != -1){
                        NameComponent nameComponent = entityWorld.getComponent(entry.getSourceEntity(), NameComponent.class);
                        if(nameComponent != null){
                            sourceName = nameComponent.getName();
                        }
                        else{
                            sourceName = "[Unnamed unit]";
                        }
                    }
                    String sourceSpellName = null;
                    if(entry.getSourceSpellEntity() != -1){
                        NameComponent nameComponent = entityWorld.getComponent(entry.getSourceSpellEntity(), NameComponent.class);
                        if(nameComponent != null){
                            sourceSpellName = nameComponent.getName();
                        }
                        else{
                            sourceSpellName = "[Unnamed spell]";
                        }
                    }
                    text += "\n";
                    if(sourceName != null){
                        text += sourceName + " -> ";
                    }
                    if(sourceSpellName != null){
                        text += sourceSpellName + " -> ";
                    }
                    text += ((int) entry.getDamage()) + " " + entry.getDamageType().name().toLowerCase();
                }
            }
            screenController_HUD.setDeathLayersVisible(true);
            screenController_HUD.setDeathRecapText(text);
            screenController_HUD.setDeathRecapVisible(false);
        }
        else if(observer.getNew().hasComponent(selectedEntity, IsAliveComponent.class)){
            screenController_HUD.setDeathLayersVisible(false);
        }
    }
}
