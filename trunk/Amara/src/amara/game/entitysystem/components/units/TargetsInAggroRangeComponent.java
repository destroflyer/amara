/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Philipp
 */
public class TargetsInAggroRangeComponent
{
    private int[] targets;

    public TargetsInAggroRangeComponent(int[] targets) {
        this.targets = targets;
    }

    public int[] getTargets() {
        return targets;
    }
}
