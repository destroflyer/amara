package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.client.gui.GUIItems;
import amara.applications.ingame.client.gui.GUIUtil;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.components.items.IsSellableComponent;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;

public class BagPanelGenerator extends ElementGenerator {

    private int BAG_ITEMS_ROWS = 10;
    private int BAG_ITEMS_PER_ROW = 10;
    private int BAG_ITEMS_ROW_HEIGHT = 70;

    private EntityWorld entityWorld;
    private int[] bagItemEntities;

    public void setData(EntityWorld entityWorld, int[] bagItemEntities) {
        this.entityWorld = entityWorld;
        this.bagItemEntities = bagItemEntities;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new ScrollPanelBuilder(id){{
            set("height", "100%");
            set("horizontal", "false");
            set("style", "nifty-scrollpanel");

            panel(new PanelBuilder(){{
                childLayoutVertical();
                height(((BAG_ITEMS_ROWS * BAG_ITEMS_ROW_HEIGHT) + 10) + "px");
                padding("10px");

                for (int i = 0; i < BAG_ITEMS_ROWS; i++) {
                    int rowIndex = i;
                    panel(new PanelBuilder(){{
                        childLayoutHorizontal();
                        height(BAG_ITEMS_ROW_HEIGHT + "px");

                        for (int r = 0; r < BAG_ITEMS_PER_ROW; r++) {
                            int itemIndex = ((rowIndex * BAG_ITEMS_PER_ROW) + r);
                            int _itemEntity = -1;
                            if (itemIndex < bagItemEntities.length) {
                                _itemEntity = bagItemEntities[itemIndex];
                            }
                            int itemEntity = _itemEntity;
                            panel(new PanelBuilder(){{
                                childLayoutVertical();
                                width("55px");

                                image(new ImageBuilder(){{
                                    String imageFilePath = GUIItems.getImageFilePath(entityWorld, bagItemEntities, itemIndex);
                                    filename(imageFilePath);
                                    width("45px");
                                    height("45px");

                                    if (itemEntity != -1) {
                                        String description = ItemDescription.generate_NameAndDescription(entityWorld, itemEntity, GUIItems.DESCRIPTION_LINE_LENGTH);
                                        onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", description));
                                    }
                                }});
                                IsSellableComponent isSellableComponent = entityWorld.getComponent(itemEntity, IsSellableComponent.class);
                                if (isSellableComponent != null) {
                                    panel(new PanelBuilder(){{
                                        height("2px");
                                    }});
                                    panel(new PanelBuilder(){{
                                        childLayoutHorizontal();
                                        height("32px");

                                        panel(new PanelBuilder(){{
                                            width("2px");
                                        }});
                                        image(new ImageBuilder(){{
                                            filename("Interface/hud/gold.png");
                                            width("15px");
                                            height("15px");
                                        }});
                                        panel(new PanelBuilder(){{
                                            width("1px");
                                        }});
                                        text(new TextBuilder(){{
                                            text(GUIUtil.getValueText(isSellableComponent.getGold()));
                                            font("Interface/fonts/Verdana_12.fnt");
                                        }});
                                        panel(new PanelBuilder());
                                    }});
                                }
                            }});
                        }
                    }});
                }
                panel(new PanelBuilder());
            }});
        }};
    }
}
