/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

/**
 *
 * @author Carl
 */
public class MMOGame extends Game {

    public MMOGame(String mapName) {
        super(mapName);
        start();
    }

    @Override
    public int getTeamsCount() {
        return 2;
    }
}
