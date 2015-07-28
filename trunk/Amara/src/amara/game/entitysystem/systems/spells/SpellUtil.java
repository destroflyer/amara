/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SpellUtil{
    
    public static final int SPELL_POINTS_COST_LEARN = 1;
    public static final int SPELL_POINTS_COST_UPGRADE = 2;
    
    public static void learnSpell(EntityWorld entityWorld, int entity, int spellIndex){
        int spellsUpgradePoints = entityWorld.getComponent(entity, SpellsUpgradePointsComponent.class).getUpgradePoints();
        if(spellsUpgradePoints >= SPELL_POINTS_COST_LEARN){
            LearnableSpellsComponent learnableSpellsComponent = entityWorld.getComponent(entity, LearnableSpellsComponent.class);
            if((learnableSpellsComponent != null) && (spellIndex < learnableSpellsComponent.getSpellsEntities().length)){
                int spellEntity = learnableSpellsComponent.getSpellsEntities()[spellIndex];
                setSpell(entityWorld, entity, spellIndex, spellEntity);
                entityWorld.setComponent(entity, new SpellsUpgradePointsComponent(spellsUpgradePoints - SPELL_POINTS_COST_LEARN));
            }
        }
    }
    
    public static void upgradeSpell(EntityWorld entityWorld, int entity, int spellIndex, int upgradeIndex){
        int spellsUpgradePoints = entityWorld.getComponent(entity, SpellsUpgradePointsComponent.class).getUpgradePoints();
        if(spellsUpgradePoints >= SPELL_POINTS_COST_UPGRADE){
            int[] spells = entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities();
            if(spellIndex < spells.length){
                SpellUpgradesComponent spellUpgradesComponent = entityWorld.getComponent(spells[spellIndex], SpellUpgradesComponent.class);
                if((spellUpgradesComponent != null) && (upgradeIndex < spellUpgradesComponent.getSpellsEntities().length)){
                    int upgradedSpellEntity = spellUpgradesComponent.getSpellsEntities()[upgradeIndex];
                    setSpell(entityWorld, entity, spellIndex, upgradedSpellEntity);
                    entityWorld.setComponent(entity, new SpellsUpgradePointsComponent(spellsUpgradePoints - SPELL_POINTS_COST_UPGRADE));
                }
            }
        }
    }
    
    public static void setSpell(EntityWorld entityWorld, int entity, int spellIndex, int spellEntity){
        int[] oldSpells = new int[0];
        int newSpellsCount = 1;
        SpellsComponent spellsComponent = entityWorld.getComponent(entity, SpellsComponent.class);
        if(spellsComponent != null){
            oldSpells = spellsComponent.getSpellsEntities();
            newSpellsCount = oldSpells.length;
        }
        if(newSpellsCount <= spellIndex){
            newSpellsCount = (spellIndex + 1);
        }
        int[] newSpells = new int[newSpellsCount];
        for(int i=0;i<newSpells.length;i++){
            if(i == spellIndex){
                newSpells[i] = spellEntity;
            }
            else if(i < oldSpells.length){
                newSpells[i] = oldSpells[i];
            }
            else{
                newSpells[i] = -1;
            }
        }
        entityWorld.setComponent(entity, new SpellsComponent(newSpells));
        entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
    }
}
