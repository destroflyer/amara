package amara.applications.ingame.entitysystem.systems.units;

import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceActionIndexComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent;
import amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.applications.ingame.entitysystem.systems.game.UpdateGameTimeSystem;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class UpdateDamageHistorySystem implements EntitySystem {

    private static final int RESET_TIME = 10;
    private Set<Integer> entitiesWithInactiveDamageHistory;
    private HashMap<Integer, LinkedList<DamageHistoryComponent.DamageHistoryEntry>> damageEntriesMap = new HashMap<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        entitiesWithInactiveDamageHistory = entityWorld.getEntitiesWithAny(DamageHistoryComponent.class);
        damageEntriesMap.clear();
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)) {
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.PHYSICAL);
        }
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)) {
            onDamageTaken(entityWorld, effectImpactEntity, DamageHistoryComponent.DamageType.MAGIC);
        }
        for (int entity : damageEntriesMap.keySet()) {
            updateDamageHistory(entityWorld, entity);
        }
        for (int entity : entitiesWithInactiveDamageHistory) {
            if (entityWorld.hasComponent(entity, IsAliveComponent.class)) {
                DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(entity, DamageHistoryComponent.class);
                float timeSinceLastDamage = (UpdateGameTimeSystem.getGameTime(entityWorld) - damageHistoryComponent.getLastDamageTime());
                if (timeSinceLastDamage >= RESET_TIME) {
                    entityWorld.removeComponent(entity, DamageHistoryComponent.class);
                }
            }
        }
    }

    private void onDamageTaken(EntityWorld entityWorld, int effectImpactEntity, DamageHistoryComponent.DamageType damageType) {
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
        float damage = switch (damageType) {
            case PHYSICAL -> entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class).getValue();
            case MAGIC -> entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class).getValue();
        };
        int sourceActionIndex = entityWorld.getComponent(effectImpactEntity, EffectSourceActionIndexComponent.class).getIndex();
        int sourceEntity = -1;
        String sourceName = "[Unnamed unit]";
        EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
        if (effectSourceComponent != null) {
            sourceEntity = effectSourceComponent.getSourceEntity();
            NameComponent nameComponent = entityWorld.getComponent(sourceEntity, NameComponent.class);
            if (nameComponent != null) {
                sourceName = nameComponent.getName();
            }
        }
        int sourceSpellEntity = -1;
        String sourceSpellName = "[Unnamed spell]";
        EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
        if (effectSourceSpellComponent != null) {
            sourceSpellEntity = effectSourceSpellComponent.getSpellEntity();
            NameComponent nameComponent = entityWorld.getComponent(sourceSpellEntity, NameComponent.class);
            if (nameComponent != null) {
                sourceSpellName = nameComponent.getName();
            }
        }
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> damageEntries = damageEntriesMap.computeIfAbsent(targetEntity, t -> new LinkedList<>());
        Iterator<DamageHistoryComponent.DamageHistoryEntry> iterator = damageEntries.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            DamageHistoryComponent.DamageHistoryEntry entry = iterator.next();
            if (damage > entry.getDamage()) {
                break;
            }
            index++;
        }
        damageEntries.add(index, new DamageHistoryComponent.DamageHistoryEntry(damageType, damage, sourceActionIndex, sourceEntity, sourceName, sourceSpellEntity, sourceSpellName));
    }

    private void updateDamageHistory(EntityWorld entityWorld, int targetEntity) {
        DamageHistoryComponent.DamageHistoryEntry[] oldEntries = new DamageHistoryComponent.DamageHistoryEntry[0];
        float gameTime = UpdateGameTimeSystem.getGameTime(entityWorld);
        float firstDamageTime = gameTime;
        DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(targetEntity, DamageHistoryComponent.class);
        if (damageHistoryComponent != null) {
            oldEntries = damageHistoryComponent.getEntries();
            firstDamageTime = damageHistoryComponent.getFirstDamageTime();
        }
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> damageEntries = damageEntriesMap.get(targetEntity);
        LinkedList<DamageHistoryComponent.DamageHistoryEntry> appliedEntries = damageEntries;
        float health = entityWorld.getComponent(targetEntity, HealthComponent.class).getValue();
        if (health < 1) {
            appliedEntries = new LinkedList<>();
            // If an entity had exactly 1 health, the parts can add up to 0.999999 due to floating point errors, so damageEntries can be empty when popping
            while ((health < 1) && (damageEntries.size() > 0)) {
                DamageHistoryComponent.DamageHistoryEntry entry = damageEntries.pop();
                health += entry.getDamage();
                appliedEntries.add(entry);
            }
        }
        DamageHistoryComponent.DamageHistoryEntry[] newEntries = new DamageHistoryComponent.DamageHistoryEntry[oldEntries.length + appliedEntries.size()];
        for (int i = 0; i < oldEntries.length; i++) {
            newEntries[i] = oldEntries[i];
        }
        for (int i = 0; i < appliedEntries.size(); i++) {
            newEntries[oldEntries.length + i] = appliedEntries.get(i);
        }
        entityWorld.setComponent(targetEntity, new DamageHistoryComponent(newEntries, firstDamageTime, gameTime));
        entitiesWithInactiveDamageHistory.remove(targetEntity);
    }
}
