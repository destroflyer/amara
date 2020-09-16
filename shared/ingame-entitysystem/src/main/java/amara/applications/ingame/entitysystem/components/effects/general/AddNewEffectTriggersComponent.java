package amara.applications.ingame.entitysystem.components.effects.general;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class AddNewEffectTriggersComponent {

    public AddNewEffectTriggersComponent() {

    }

    public AddNewEffectTriggersComponent(String[] effectTriggerTemplates) {
        this.effectTriggerTemplates = effectTriggerTemplates;
    }
    @ComponentField(type=ComponentField.Type.TEMPLATE)
    private String[] effectTriggerTemplates;

    public String[] getEffectTriggerTemplates() {
        return effectTriggerTemplates;
    }
}
