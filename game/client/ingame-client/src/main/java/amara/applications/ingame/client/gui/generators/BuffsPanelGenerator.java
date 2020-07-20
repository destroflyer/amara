package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.entitysystem.components.buffs.BuffIconComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.general.DescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

public class BuffsPanelGenerator extends ElementGenerator {

    public BuffsPanelGenerator(String idPrefix) {
        super(idPrefix);
    }
    private EntityWorld entityWorld;
    private int[] buffStatusEntities;

    public void setData(EntityWorld entityWorld, int[] buffStatusEntities) {
        this.entityWorld = entityWorld;
        this.buffStatusEntities = buffStatusEntities;
    }

    @Override
    public ElementBuilder generate(Nifty nifty, String id) {
        return new PanelBuilder(id){{
            childLayoutHorizontal();

            for (int _buffStatusEntity : buffStatusEntities) {
                final int buffStatusEntity = _buffStatusEntity;
                int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();

                String description = getBuffDescription(entityWorld, buffEntity);
                if (description != null) {
                    panel(new PanelBuilder() {{
                        width("32px");
                        height("32px");
                        childLayoutOverlay();

                        image(new ImageBuilder() {{
                            filename(getBuffImageFilePath(entityWorld, buffEntity));

                            onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", description));
                        }});

                        text(new TextBuilder(id + "_" + buffStatusEntity + "_stacks") {{
                            textHAlignRight();
                            textVAlignBottom();
                            font("Interface/fonts/Verdana_14.fnt");
                            visible(false);
                        }});
                    }});
                }
            }
        }};
    }

    private String getBuffImageFilePath(EntityWorld entityWorld, int buffEntity) {
        BuffIconComponent buffIconComponent = entityWorld.getComponent(buffEntity, BuffIconComponent.class);
        String imageFileName = ((buffIconComponent != null) ? buffIconComponent.getName() : "spells/unknown");
        return "Interface/hud/" + imageFileName + ".png";
    }

    private String getBuffDescription(EntityWorld entityWorld, int buffEntity) {
        String description = null;
        NameComponent nameComponent = entityWorld.getComponent(buffEntity, NameComponent.class);
        if (nameComponent != null) {
            description = nameComponent.getName();
        }
        DescriptionComponent descriptionComponent = entityWorld.getComponent(buffEntity, DescriptionComponent.class);
        if (descriptionComponent != null) {
            if (description == null) {
                description = "";
            } else {
                description += ": ";
            }
            description += descriptionComponent.getDescription();
        }
        return description;
    }

    public void setBuffStacks(Nifty nifty, int buffStatusEntity, int stacks) {
        TextRenderer textRenderer = getTextRenderer(nifty, getId() + "_" + buffStatusEntity + "_stacks");
        // Element can be null if buff should not be shown
        if (textRenderer != null) {
            textRenderer.setText("" + stacks);
        }
    }

    public void setBuffStacksVisible(Nifty nifty, int buffStatusEntity, boolean visible) {
        Element element = getElementById(nifty, getId() + "_" + buffStatusEntity + "_stacks");
        // Element can be null if buff should not be shown
        if (element != null) {
            element.setVisible(visible);
        }
    }
}
