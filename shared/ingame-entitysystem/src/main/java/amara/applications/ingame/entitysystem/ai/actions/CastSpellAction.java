package amara.applications.ingame.entitysystem.ai.actions;

import amara.ingame.ai.Action;

public class CastSpellAction extends Action {

    public CastSpellAction(int spellEntity, int targetEntity) {
        this.spellEntity = spellEntity;
        this.targetEntity = targetEntity;
    }
    private int spellEntity;
    private int targetEntity;

    public int getSpellEntity() {
        return spellEntity;
    }

    public int getTargetEntity() {
        return targetEntity;
    }
}
