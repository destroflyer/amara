package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.client.gui.GUIItems;
import amara.applications.ingame.entitysystem.components.items.ItemIDComponent;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;

import java.util.List;

public class ShopBuildsIntoGenerator extends ElementGenerator {

    public ShopBuildsIntoGenerator() {
        super("builds-into");
    }
    private EntityWorld entityWorld;
    private List<Integer> itemEntities;

    public void setData(EntityWorld entityWorld, List<Integer> itemEntities) {
        this.entityWorld = entityWorld;
        this.itemEntities = itemEntities;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new PanelBuilder(id){{
            childLayoutHorizontal();
            width("100%");
            height("100%");

            int _i = 0;
            for (int itemEntity : itemEntities) {
                final int i = _i;
                String itemId = entityWorld.getComponent(itemEntity, ItemIDComponent.class).getID();
                panel(new PanelBuilder(){{
                    width((i == 0) ? "20px" : "10px");
                }});
                image(new ImageBuilder(id + "_item_" + itemId){{
                    String imageFilePath = GUIItems.getImageFilePath(entityWorld, itemEntity);
                    filename(imageFilePath);
                    width("45px");
                    height("45px");

                    visibleToMouse();
                }});
                _i++;
            }
        }};
    }
}
