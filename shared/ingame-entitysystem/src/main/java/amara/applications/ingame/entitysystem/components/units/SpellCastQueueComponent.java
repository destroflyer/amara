package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

@Serializable
public class SpellCastQueueComponent {

    public SpellCastQueueComponent(){

    }

    public SpellCastQueueComponent(SpellCastQueueEntry[] entries){
        this.entries = entries;
    }
    private SpellCastQueueEntry[] entries;

    public SpellCastQueueEntry[] getEntries() {
        return entries;
    }

    @Serializable
    public static class SpellCastQueueEntry {

        public SpellCastQueueEntry() {

        }

        public SpellCastQueueEntry(int spellEntity, int targetEntity) {
            this.spellEntity = spellEntity;
            this.targetEntity = targetEntity;
        }
        private int spellEntity;
        private int targetEntity;

        public int getSpellEntity() {
            return spellEntity;
        }

        public int getTargetEntity() {
            return targetEntity;
        }
    }
}
