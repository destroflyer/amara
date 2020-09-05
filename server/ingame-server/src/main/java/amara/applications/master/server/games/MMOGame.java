package amara.applications.master.server.games;

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
