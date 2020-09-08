package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.RiseUpControl;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;

public class ModelModifier_Dosaz_Tombstone extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        registeredModel.getNode().addControl(new RiseUpControl(0.2f));
    }
}
