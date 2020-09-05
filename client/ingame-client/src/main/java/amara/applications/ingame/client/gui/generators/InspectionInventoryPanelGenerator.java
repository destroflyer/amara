package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.client.gui.GUIItems;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;

public class InspectionInventoryPanelGenerator extends ElementGenerator {

    public InspectionInventoryPanelGenerator() {
        super("inspection__inventory_items");
    }
    private EntityWorld entityWorld;
    private int[] itemEntities;

    public void setData(EntityWorld entityWorld, int[] itemEntities) {
        this.entityWorld = entityWorld;
        this.itemEntities = itemEntities;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new PanelBuilder(id){{
            childLayoutVertical();

            panel(new PanelBuilder(){{
                height("6px");
            }});
            panel(new PanelBuilder(){{
                childLayoutHorizontal();
                height("130px");

                panel(new PanelBuilder(){{
                    width("174px");
                }});
                panel(new PanelBuilder(){{
                    width("132px");
                    childLayoutVertical();

                    panel(new PanelBuilder(){{
                        height("43px");
                        childLayoutHorizontal();

                        panel(getItemBuilder(0));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        panel(getItemBuilder(1));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        panel(getItemBuilder(2));
                    }});
                    panel(new PanelBuilder(){{
                        height("1px");
                        backgroundColor("#999F");
                    }});
                    panel(new PanelBuilder(){{
                        height("43px");
                        childLayoutHorizontal();

                        panel(getItemBuilder(3));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        panel(getItemBuilder(4));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        panel(getItemBuilder(5));
                    }});
                    panel(new PanelBuilder(){{
                        width("131px");
                        backgroundColor("#000D");
                        childLayoutCenter();

                        image(new ImageBuilder(){{
                            filename("Interface/hud/shop_button.png");
                            width("100%");
                            height("100%");
                        }});
                        text(new TextBuilder(){{
                            text("TODO");
                            textHAlignCenter();
                            font("Interface/fonts/Verdana_14.fnt");
                        }});
                    }});
                    panel(new PanelBuilder(){{
                        height("6px");
                    }});
                }});
                panel(new PanelBuilder());
            }});
            panel(new PanelBuilder());
        }};
    }

    private PanelBuilder getItemBuilder(int itemIndex) {
        int itemEntity = EntityArrayUtil.get(itemEntities, itemIndex);
        return new PanelBuilder(){{
            width("43px");
            height("43px");
            childLayoutCenter();

            if (itemEntity != -1) {
                String description = ItemDescription.generate_NameAndDescription(entityWorld, itemEntity, GUIItems.DESCRIPTION_LINE_LENGTH);
                onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", description));
            }

            image(new ImageBuilder(){{
                String imageFilePath = GUIItems.getImageFilePath(entityWorld, itemEntity);
                filename(imageFilePath);
                width("100%");
                height("100%");
            }});
        }};
    }
}
