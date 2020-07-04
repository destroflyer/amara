package amara.applications.ingame.client.gui.generators;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;

public abstract class ElementGenerator {

    private int index;

    public void update(Nifty nifty, String idPrefix, String parentId) {
        Element parentElement = getElementById(nifty, parentId);
        Element existingElement = getElementById(nifty, getId(idPrefix));
        if (existingElement != null) {
            existingElement.markForRemoval();
        }
        index++;
        ElementBuilder elementBuilder = generate(nifty, getId(idPrefix));
        elementBuilder.build(nifty, nifty.getCurrentScreen(), parentElement);
    }

    private String getId(String idPrefix) {
        return idPrefix + "_" + index;
    }

    public abstract ElementBuilder generate(Nifty nifty, String id);

    protected Element getElementById(Nifty nifty, String id) {
        return nifty.getCurrentScreen().findElementById(id);
    }
}
