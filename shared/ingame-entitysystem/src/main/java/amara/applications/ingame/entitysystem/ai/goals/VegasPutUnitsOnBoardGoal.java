package amara.applications.ingame.entitysystem.ai.goals;

import amara.applications.ingame.entitysystem.ai.actions.CastSpellAction;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.ingame.ai.Action;
import amara.ingame.ai.Goal;
import amara.libraries.entitysystem.EntityWorld;

import java.util.LinkedList;

public class VegasPutUnitsOnBoardGoal extends Goal {

    private int spellEntity;
    private int targetEntity;

    @Override
    public boolean isEnabled(EntityWorld entityWorld, int entity) {
        spellEntity = entityWorld.getComponent(entity, SpellsComponent.class).getSpellsEntities()[0];
        String swapSpellName = entityWorld.getComponent(spellEntity, NameComponent.class).getName();
        int spellTargetRulesEntity = entityWorld.getComponent(spellEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
        if ("Move (Mark)".equals(swapSpellName)) {
            targetEntity = getPreferredTarget(entityWorld, entity, spellTargetRulesEntity, "Unit on bench");
        } else {
            targetEntity = getPreferredTarget(entityWorld, entity, spellTargetRulesEntity, "Free board place");
        }
        return (targetEntity != -1);
    }

    private int getPreferredTarget(EntityWorld entityWorld, int casterEntity, int spellTargetRulesEntity, String buffName) {
        LinkedList<Integer> validTargets = new LinkedList<>();
        for (int targetEntity : entityWorld.getEntitiesWithAll(BuffsComponent.class)) {
            if (TargetUtil.isValidTarget(entityWorld, casterEntity, targetEntity, spellTargetRulesEntity)) {
                int[] buffStatusEntities = entityWorld.getComponent(targetEntity, BuffsComponent.class).getBuffStatusEntities();
                for (int buffStatusEntity : buffStatusEntities) {
                    int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                    NameComponent nameComponent = entityWorld.getComponent(buffEntity, NameComponent.class);
                    if ((nameComponent != null) && buffName.equals(nameComponent.getName())) {
                        validTargets.add(targetEntity);
                        break;
                    }
                }
            }
        }
        if (validTargets.size() > 0) {
            return validTargets.get((int) (Math.random() * validTargets.size()));
        }
        return -1;
    }

    @Override
    public void initialize(EntityWorld entityWorld, int entity) {

    }

    @Override
    public double getValue(EntityWorld entityWorld, int entity) {
        return 2;
    }

    @Override
    public void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions) {
        actions.add(new CastSpellAction(spellEntity, targetEntity));
    }
}
