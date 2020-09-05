package amara.applications.ingame.entitysystem.ai.bots;

import amara.applications.ingame.entitysystem.ai.goals.BuyItemGoal;
import amara.applications.ingame.entitysystem.ai.goals.UseItemActiveGoal;
import amara.applications.ingame.entitysystem.ai.goals.VegasPutUnitsOnBoardGoal;

public class EasyVegasBot extends Bot{

    public EasyVegasBot(){
        decisionMaking.addGoal(new BuyItemGoal());
        decisionMaking.addGoal(new UseItemActiveGoal());
        decisionMaking.addGoal(new VegasPutUnitsOnBoardGoal());
    }
}
