package amara.applications.ingame.client.gui.generators;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;

public class DeathRecapGenerator extends ElementGenerator {

    private String text;

    public void setData(String text) {
        this.text = text;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new ScrollPanelBuilder(id){{
            set("horizontal", "false");
            style("nifty-scrollpanel");

            panel(new PanelBuilder(){{
                childLayoutCenter();
                padding("10px");

                text(new TextBuilder("death_recap_text") {{
                    width("100%");
                    text(text);
                    textHAlignLeft();
                    font("Interface/fonts/Verdana_14.fnt");
                }});
            }});
        }};
    }
}
