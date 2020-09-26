package amara.applications.ingame.server.entitysystem.systems.objectives;

import amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent;
import amara.applications.ingame.entitysystem.components.objectives.FinishedObjectiveComponent;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.server.IngameServerApplication;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CheckMapObjectiveSystem implements EntitySystem {

    public CheckMapObjectiveSystem(Map map, IngameServerApplication mainApplication) {
        this.map = map;
        this.mainApplication = mainApplication;
    }
    private Map map;
    private IngameServerApplication mainApplication;
    private boolean isFinished = false;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (!isFinished) {
            MapObjectiveComponent mapObjectiveComponent = entityWorld.getComponent(map.getEntity(), MapObjectiveComponent.class);
            if (mapObjectiveComponent != null) {
                if (entityWorld.hasComponent(mapObjectiveComponent.getObjectiveEntity(), FinishedObjectiveComponent.class)) {
                    isFinished = true;
                    mainApplication.onGameOver();
                }
            }
        }
    }
}
