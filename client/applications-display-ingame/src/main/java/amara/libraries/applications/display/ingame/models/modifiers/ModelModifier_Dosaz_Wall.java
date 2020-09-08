package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.ingame.models.util.RiseUpControl;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelSkin;
import amara.libraries.applications.display.models.RegisteredModel;
import amara.libraries.physics.util2d.PointUtil;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class ModelModifier_Dosaz_Wall extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Vector2f[] circlePoints = PointUtil.getCirclePoints(6, 64);
        for (int i = 3; i < 29; i += 2){
            Node node = ModelSkin.get("Models/3dsa_fantasy_forest_animal_bones/skin.xml").load();
            node.setLocalTranslation(circlePoints[i].getY(), 0, circlePoints[i].getX());
            node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
            node.setLocalScale(2, 8, 2);
            registeredModel.getNode().attachChild(node);
        }
        registeredModel.getNode().addControl(new RiseUpControl(0.318f));
    }
}
