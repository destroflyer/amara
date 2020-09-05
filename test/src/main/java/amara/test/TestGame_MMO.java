/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.master.network.messages.Message_JoinMMOMap;
import amara.applications.master.network.messages.objects.GameSelectionPlayerData;

/**
 *
 * @author Carl
 */
public class TestGame_MMO extends TestGame {

    public static void main(String[] args) {
        String authToken = args[0];
        startServerAndLogin(authToken, networkClient -> {
            networkClient.sendMessage(new Message_JoinMMOMap("astrudan", new GameSelectionPlayerData(17, null)));
        });
    }
}
