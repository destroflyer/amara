package amara.applications.ingame.client.gui.generators;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

public abstract class ElementGenerator {

    protected ElementGenerator(String idPrefix) {
        this.idPrefix = idPrefix;
    }
    private String idPrefix;
    private int index;

    public void update(Nifty nifty, String parentId) {
        Element parentElement = getElementById(nifty, parentId);
        Element existingElement = getElementById(nifty, getId());
        if (existingElement != null) {
            existingElement.markForRemoval();
        }
        index++;
        ElementBuilder elementBuilder = generate(nifty, getId());
        elementBuilder.build(nifty, nifty.getCurrentScreen(), parentElement);
    }

    public String getId() {
        return idPrefix + "_" + index;
    }

    protected abstract ElementBuilder generate(Nifty nifty, String id);

    protected TextRenderer getTextRenderer(Nifty nifty, String id) {
        Element element = getElementById(nifty, id);
        return ((element != null) ? element.getRenderer(TextRenderer.class) : null);
    }

    protected Element getElementById(Nifty nifty, String id) {
        return nifty.getCurrentScreen().findElementById(id);
    }
}
