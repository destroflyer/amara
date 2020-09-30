package amara.applications.ingame.entitysystem.ai.bots;

import amara.applications.ingame.entitysystem.ai.goals.*;

public class EasyBot extends Bot {

    public EasyBot() {
        decisionMaking.addGoal(new WalkToEnemyNexusGoal());
        decisionMaking.addGoal(new LastHitGoal());
    }
}
