package amara.applications.ingame.client.systems.gui.deathrecap;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.systems.gui.GUIDisplaySystem;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.core.Util;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

import java.util.Collection;
import java.util.HashMap;

public class DisplayDeathRecapSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayDeathRecapSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        if (observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)) {
            String text = "[No damage history existing]";
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(characterEntity, DamageHistoryComponent.class);
            if (damageHistoryComponent != null) {
                float totalDamage = 0;
                for (DamageHistoryComponent.DamageHistoryEntry entry : damageHistoryComponent.getEntries()) {
                    totalDamage += entry.getDamage();
                }
                text = ((int) totalDamage) + " total damage over " + Util.round(damageHistoryComponent.getLastDamageTime() - damageHistoryComponent.getFirstDamageTime(), 3) + " seconds";
                Collection<DeathRecapUnit> units = getDeathRecapUnits(entityWorld, damageHistoryComponent.getEntries());
                for (DeathRecapUnit unit : units) {
                    text += "\n\n" + unit.getName() + ":";
                    for (DeathRecapSpell spell : unit.getSpells()) {
                        text += "\n" + spell.getCount() + "x " + spell.getName() + " -> ";
                        if (spell.getTotalPhysicalDamage() > 0) {
                            text += ((int) spell.getTotalPhysicalDamage()) + " physical";
                        }
                        if (spell.getTotalMagicDamage() > 0) {
                            if (spell.getTotalPhysicalDamage() > 0) {
                                text += ", ";
                            }
                            text += ((int) spell.getTotalMagicDamage()) + " magic";
                        }
                    }
                }
            }
            screenController.setDeathLayersVisible(true);
            screenController.setDeathRecapText(text);
            screenController.setDeathRecapVisible(false);
        } else if (observer.getNew().hasComponent(characterEntity, IsAliveComponent.class)) {
            screenController.setDeathLayersVisible(false);
        }
    }

    private Collection<DeathRecapUnit> getDeathRecapUnits(EntityWorld entityWorld, DamageHistoryComponent.DamageHistoryEntry[] damageHistoryEntries) {
        HashMap<Integer, DeathRecapUnit> units = new HashMap<>();
        HashMap<Integer, DeathRecapSpell> spells = new HashMap<>();
        for (DamageHistoryComponent.DamageHistoryEntry entry : damageHistoryEntries) {
            DeathRecapUnit unit = units.computeIfAbsent(entry.getSourceEntity(), sourceEntity -> {
                NameComponent nameComponent = entityWorld.getComponent(sourceEntity, NameComponent.class);
                String name = ((nameComponent != null) ? nameComponent.getName() : "[Unnamed unit]");
                return new DeathRecapUnit(name);
            });
            DeathRecapSpell spell = spells.computeIfAbsent(entry.getSourceSpellEntity(), sourceSpellEntity -> {
                NameComponent nameComponent = entityWorld.getComponent(sourceSpellEntity, NameComponent.class);
                String name = ((nameComponent != null) ? nameComponent.getName() : "[Unnamed spell]");
                DeathRecapSpell newSpell = new DeathRecapSpell(name);
                unit.addSpell(newSpell);
                return newSpell;
            });
            switch (entry.getDamageType()) {
                case PHYSICAL:
                    spell.addPhysicalDamage(entry.getDamage());
                    break;
                case MAGIC:
                    spell.addMagicDamage(entry.getDamage());
                    break;
            }
        }
        return units.values();
    }
}
