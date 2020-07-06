package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.client.gui.GUIItems;
import amara.applications.ingame.client.gui.objects.ItemDescription;
import amara.applications.ingame.entitysystem.systems.general.EntityArrayUtil;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;

public class PlayerInventoryPanelGenerator extends ElementGenerator {

    public PlayerInventoryPanelGenerator() {
        super("player_inventory_items");
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

            panel(new PanelBuilder());
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

                        control(getItemBuilder(id, 0));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        control(getItemBuilder(id, 1));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        control(getItemBuilder(id, 2));
                    }});
                    panel(new PanelBuilder(){{
                        height("1px");
                        backgroundColor("#999F");
                    }});
                    panel(new PanelBuilder(){{
                        height("43px");
                        childLayoutHorizontal();

                        control(getItemBuilder(id, 3));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        control(getItemBuilder(id, 4));
                        panel(new PanelBuilder(){{
                            width("1px");
                            backgroundColor("#999F");
                        }});
                        control(getItemBuilder(id, 5));
                    }});
                    control(new DroppableBuilder(id + "_shop_button") {{
                        backgroundColor("#000D");
                        width("131px");
                        childLayoutCenter();

                        image(new ImageBuilder(){{
                            filename("Interface/hud/shop_button.png");
                            width("100%");
                            height("100%");
                            onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", "Shop"));
                        }});
                        panel(new PanelBuilder(){{
                            width("100%");
                            height("100%");
                            childLayoutHorizontal();
                            interactOnClick("toggleShopVisible()");

                            panel(new PanelBuilder(){{
                                width("32px");
                            }});
                            panel(new PanelBuilder(){{
                                width("26px");
                                childLayoutVertical();

                                panel(new PanelBuilder(){{
                                    height("5px");
                                }});
                                panel(new PanelBuilder(){{
                                    childLayoutHorizontal();

                                    image(new ImageBuilder(){{
                                        filename("Interface/hud/gold.png");
                                        width("26px");
                                        height("26px");
                                    }});
                                }});
                            }});
                            panel(new PanelBuilder(){{
                                width("7px");
                            }});
                            panel(new PanelBuilder(){{
                                childLayoutVertical();

                                panel(new PanelBuilder(){{
                                    height("9px");
                                }});
                                text(new TextBuilder("player_gold"){{
                                    text("????");
                                    textHAlignLeft();
                                    font("Interface/fonts/Verdana_14.fnt");
                                }});
                            }});
                        }});
                    }});
                    panel(new PanelBuilder(){{
                        height("6px");
                    }});
                }});
                panel(new PanelBuilder());
            }});
        }};
    }

    private ControlBuilder getItemBuilder(String id, int itemIndex) {
        int itemEntity = EntityArrayUtil.get(itemEntities, itemIndex);
        return new DroppableBuilder(id + "_item_droppable_" + itemIndex){{
            width("43px");
            height("43px");
            backgroundImage(GUIItems.getImageFilePath(entityWorld, -1));

            if (itemEntity != -1) {
                String description = ItemDescription.generate_NameAndDescription(entityWorld, itemEntity, GUIItems.DESCRIPTION_LINE_LENGTH);
                onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", description));

                control(new DraggableBuilder(id + "_item_draggable_" + itemIndex){{
                    width("43px");
                    height("43px");
                    childLayoutCenter();

                    image(new ImageBuilder(id + "_item_image_" + itemIndex){{
                        String imageFilePath = GUIItems.getImageFilePath(entityWorld, itemEntity);
                        filename(imageFilePath);
                        width("100%");
                        height("100%");
                    }});
                    panel(new PanelBuilder(id + "_item_cooldown_" + itemIndex){{
                        width("100%");
                        height("100%");
                        backgroundColor("#000C");
                        childLayoutCenter();

                        text(new TextBuilder(id + "_item_cooldown_time_" + itemIndex){{
                            text("???");
                            font("Interface/fonts/Verdana_14.fnt");
                        }});
                    }});
                }});
            }
        }};
    }
}
