/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class UpgradeSpellCommand extends Command{

    public UpgradeSpellCommand(){
        
    }

    public UpgradeSpellCommand(int spellIndex, int upgradeIndex){
        this.spellIndex = spellIndex;
        this.upgradeIndex = upgradeIndex;
    }
    private int spellIndex;
    private int upgradeIndex;

    public int getSpellIndex(){
        return spellIndex;
    }

    public int getUpgradeIndex(){
        return upgradeIndex;
    }
}
