package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class DamageHistoryComponent {

    public DamageHistoryComponent() {

    }

    public DamageHistoryComponent(DamageHistoryEntry[] entries, float firstDamageTime, float lastDamageTime) {
        this.entries = entries;
        this.firstDamageTime = firstDamageTime;
        this.lastDamageTime = lastDamageTime;
    }
    public enum DamageType{
        PHYSICAL,
        MAGIC
    }
    private DamageHistoryEntry[] entries;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float firstDamageTime;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float lastDamageTime;

    public DamageHistoryEntry[] getEntries() {
        return entries;
    }

    public float getFirstDamageTime() {
        return firstDamageTime;
    }

    public float getLastDamageTime() {
        return lastDamageTime;
    }

    @Serializable
    public static class DamageHistoryEntry {

        public DamageHistoryEntry() {

        }

        public DamageHistoryEntry(DamageType damageType, float damage, int sourceActionIndex, int sourceEntity, int sourceSpellEntity) {
            this.damageType = damageType;
            this.damage = damage;
            this.sourceActionIndex = sourceActionIndex;
            this.sourceEntity = sourceEntity;
            this.sourceSpellEntity = sourceSpellEntity;
        }
        private DamageType damageType;
        private float damage;
        private int sourceActionIndex;
        private int sourceEntity;
        private int sourceSpellEntity;

        public DamageType getDamageType() {
            return damageType;
        }

        public float getDamage() {
            return damage;
        }

        public int getSourceActionIndex() {
            return sourceActionIndex;
        }

        public int getSourceEntity() {
            return sourceEntity;
        }

        public int getSourceSpellEntity() {
            return sourceSpellEntity;
        }
    }
}
