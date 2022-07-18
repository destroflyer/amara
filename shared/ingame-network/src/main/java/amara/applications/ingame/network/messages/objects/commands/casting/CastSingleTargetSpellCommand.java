package amara.applications.ingame.network.messages.objects.commands.casting;

import com.jme3.network.serializing.Serializable;
import amara.applications.ingame.network.messages.objects.commands.Command;

@Serializable
public class CastSingleTargetSpellCommand extends Command {

    public CastSingleTargetSpellCommand() {

    }

    public CastSingleTargetSpellCommand(SpellIndex spellIndex, int targetEntity) {
        this.spellIndex = spellIndex;
        this.targetEntity = targetEntity;
    }
    private SpellIndex spellIndex;
    private int targetEntity;

    public SpellIndex getSpellIndex() {
        return spellIndex;
    }

    public int getTargetEntity() {
        return targetEntity;
    }
}
