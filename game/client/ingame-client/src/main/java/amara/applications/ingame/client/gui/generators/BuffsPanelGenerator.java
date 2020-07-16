package amara.applications.ingame.client.gui.generators;

import amara.applications.ingame.entitysystem.components.buffs.BuffIconComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.general.DescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.libraries.entitysystem.EntityWorld;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;

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

            for (int buffStatusEntity : buffStatusEntities) {
                int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();

                String description = getBuffDescription(entityWorld, buffEntity);
                if (description != null) {
                    image(new ImageBuilder() {{
                        width("32px");
                        height("32px");
                        filename(getBuffImageFilePath(entityWorld, buffEntity));

                        onHoverEffect(new HoverEffectBuilder("hint").effectParameter("hintText", description));
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
}
