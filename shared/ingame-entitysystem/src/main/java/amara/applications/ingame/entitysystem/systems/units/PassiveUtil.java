package amara.applications.ingame.entitysystem.systems.units;

import java.util.LinkedList;

import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.general.UniqueComponent;
import amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent;
import amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;

public class PassiveUtil {

    private static LinkedList<Integer> tmpPassiveEntities_Current = new LinkedList<>();
    private static LinkedList<Integer> tmpPassiveEntities_Changed = new LinkedList<>();

    public static void addPassives(EntityWorld entityWorld, int targetEntity, int... passiveEntities) {
        triggerPassives(entityWorld, targetEntity, passiveEntities, true);
    }

    public static void removePassives(EntityWorld entityWorld, int targetEntity, int... passiveEntities) {
        triggerPassives(entityWorld, targetEntity, passiveEntities, false);
    }

    private static void triggerPassives(EntityWorld entityWorld, int targetEntity, int[] passiveEntities, boolean addedOrRemoved) {
        tmpPassiveEntities_Current.clear();
        tmpPassiveEntities_Changed.clear();
        CurrentPassivesComponent currentPassivesComponent = entityWorld.getComponent(targetEntity, CurrentPassivesComponent.class);
        if (currentPassivesComponent != null) {
            for (int passiveEntity : currentPassivesComponent.getPassiveEntities()) {
                tmpPassiveEntities_Current.add(passiveEntity);
            }
        }
        for (int passiveEntity : passiveEntities) {
            boolean hasChanged = true;
            int uniquePassiveEntity = passiveEntity;
            if (entityWorld.hasComponent(passiveEntity, UniqueComponent.class)) {
                NameComponent nameComponent = entityWorld.getComponent(passiveEntity, NameComponent.class);
                if (nameComponent != null) {
                    int uniquePassivesCount = 0;
                    for (int i = (tmpPassiveEntities_Current.size() - 1); i >= 0; i--) {
                        int tmpPassiveEntity = tmpPassiveEntities_Current.get(i);
                        if (entityWorld.hasComponent(tmpPassiveEntity, UniqueComponent.class)) {
                            NameComponent tmpNameComponent = entityWorld.getComponent(tmpPassiveEntity, NameComponent.class);
                            if ((tmpNameComponent != null) && nameComponent.getName().equals(tmpNameComponent.getName())) {
                                if (uniquePassivesCount == 0) {
                                    uniquePassiveEntity = tmpPassiveEntity;
                                }
                                uniquePassivesCount++;
                            }
                        }
                    }
                    if ((addedOrRemoved && (uniquePassivesCount > 0))
                    || ((!addedOrRemoved) && (uniquePassivesCount != 1))) {
                        hasChanged = false;
                    }
                }
            }
            if (addedOrRemoved) {
                tmpPassiveEntities_Current.add(passiveEntity);
            } else {
                tmpPassiveEntities_Current.remove((Integer) uniquePassiveEntity);
            }
            if (hasChanged) {
                tmpPassiveEntities_Changed.add(uniquePassiveEntity);
            }
        }
        entityWorld.setComponent(targetEntity, new CurrentPassivesComponent(Util.convertToArray_Integer(tmpPassiveEntities_Current)));
        for (int passiveEntity : tmpPassiveEntities_Changed) {
            int[] effectTriggersEntities = null;
            if (addedOrRemoved) {
                PassiveAddedEffectTriggersComponent passiveAddedEffectTriggersComponent = entityWorld.getComponent(passiveEntity, PassiveAddedEffectTriggersComponent.class);
                if (passiveAddedEffectTriggersComponent != null) {
                    effectTriggersEntities = passiveAddedEffectTriggersComponent.getEffectTriggerEntities();
                }
            } else {
                PassiveRemovedEffectTriggersComponent passiveRemovedEffectTriggersComponent = entityWorld.getComponent(passiveEntity, PassiveRemovedEffectTriggersComponent.class);
                if (passiveRemovedEffectTriggersComponent != null) {
                    effectTriggersEntities = passiveRemovedEffectTriggersComponent.getEffectTriggerEntities();
                }
            }
            if (effectTriggersEntities != null) {
                for (int effectTriggerEntity : effectTriggersEntities) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
