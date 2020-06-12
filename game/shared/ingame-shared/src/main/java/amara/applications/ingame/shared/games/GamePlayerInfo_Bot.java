/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.master.network.messages.objects.BotType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Carl
 */
@AllArgsConstructor
@Getter
public class GamePlayerInfo_Bot extends GamePlayerInfo {
    private BotType botType;
    private String name;
}
