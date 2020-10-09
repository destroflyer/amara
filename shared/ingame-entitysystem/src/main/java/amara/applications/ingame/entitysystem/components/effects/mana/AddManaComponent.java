package amara.applications.ingame.entitysystem.components.effects.mana;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class AddManaComponent {

    public AddManaComponent() {

    }

    public AddManaComponent(String expression) {
        this.expression = expression;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String expression;

    public String getExpression() {
        return expression;
    }
}
