package amara.applications.ingame.client.gui.generators;

import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;

public class ScoreboardGenerator extends ElementGenerator {

    private GameSelectionPlayer[][] teams;

    public void setData(GameSelectionPlayer[][] teams) {
        this.teams = teams;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new PanelBuilder(){{
            childLayoutVertical();

            if (teams != null) {
                int _playerIndex = 0;
                for (int i = 0; i < teams.length; i++) {
                    int teamIndex = i;
                    text(new TextBuilder() {{
                        height("30px");
                        textHAlignLeft();
                        font("Interface/fonts/Verdana_14.fnt");
                        text("Team #" + (teamIndex + 1));
                    }});
                    for (int r = 0; r < teams[i].length; r++) {
                        int playerIndex = _playerIndex;
                        panel(new PanelBuilder("scoreboard_player_" + playerIndex) {{
                            childLayoutHorizontal();
                            height("30px");

                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_name") {{
                                width("200px");
                                height("30px");
                                textHAlignLeft();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("Player #" + (playerIndex + 1));
                            }});
                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_kda") {{
                                width("40px");
                                height("30px");
                                textHAlignCenter();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("K/D/A");
                            }});
                            panel(new PanelBuilder() {{
                                width("20px");
                            }});
                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_creepscore") {{
                                width("40px");
                                height("30px");
                                textHAlignCenter();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("CS");
                            }});
                            panel(new PanelBuilder() {{
                                width("20px");
                            }});
                            for (int i = 0; i < 6; i++) {
                                image(new ImageBuilder("scoreboard_player_" + playerIndex + "_item_" + i + "_image") {{
                                    width("30px");
                                    height("30px");
                                    filename("Interface/hud/items/unknown.png");

                                    onHoverEffect(new HoverEffectBuilder("hint") {{
                                        effectParameter("hintText", "?");
                                    }});
                                }});
                            }
                            panel(new PanelBuilder() {{
                                width("*");
                            }});
                        }});
                        panel(new PanelBuilder() {{
                            height("5px");
                        }});
                        _playerIndex++;
                    }
                }
            } else {
                // TODO: Scoreboard for MMO maps
            }
            panel(new PanelBuilder(){{
                height("*");
            }});
        }};
    }
}
