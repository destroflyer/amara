
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.BubbleUtil;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;

public class ModelModifier_Bubble_Pink extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        registeredModel.getNode().attachChild(BubbleUtil.createPink());
    }
}
