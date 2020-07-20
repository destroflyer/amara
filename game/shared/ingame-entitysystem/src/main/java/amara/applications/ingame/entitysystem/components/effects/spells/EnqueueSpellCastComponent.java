package amara.applications.ingame.entitysystem.components.effects.spells;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class EnqueueSpellCastComponent {

    public EnqueueSpellCastComponent() {

    }

    public EnqueueSpellCastComponent(int spellEntity, int targetEntity) {
        this.spellEntity = spellEntity;
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getSpellEntity() {
        return spellEntity;
    }

    public int getTargetEntity() {
        return targetEntity;
    }
}
