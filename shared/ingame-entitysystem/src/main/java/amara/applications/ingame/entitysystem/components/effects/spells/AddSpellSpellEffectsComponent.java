package amara.applications.ingame.entitysystem.components.effects.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AddSpellSpellEffectsComponent {

    public AddSpellSpellEffectsComponent() {

    }

    public AddSpellSpellEffectsComponent(int[] spellEffectEntities, boolean setSourcesToSpells) {
        this.spellEffectEntities = spellEffectEntities;
        this.setSourcesToSpells = setSourcesToSpells;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] spellEffectEntities;
    private boolean setSourcesToSpells;

    public int[] getSpellEffectEntities() {
        return spellEffectEntities;
    }

    public boolean isSetSourcesToSpells() {
        return setSourcesToSpells;
    }
}
