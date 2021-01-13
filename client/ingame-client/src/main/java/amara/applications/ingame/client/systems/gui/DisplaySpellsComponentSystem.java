package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

public abstract class DisplaySpellsComponentSystem<T> extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplaySpellsComponentSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD, Class<T> componentClass) {
        super(playerAppState, screenController_HUD);
        this.componentClass = componentClass;
    }
    private Class<T> componentClass;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class, componentClass);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, SpellsComponent.class));
        SpellsComponent spellsComponent = entityWorld.getComponent(characterEntity, SpellsComponent.class);
        if (spellsComponent != null) {
            checkCurrentSpells(observer, spellsComponent.getSpellsEntities());
        }
    }

    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent) {
        if (spellsComponent != null) {
            int[] spells = spellsComponent.getSpellsEntities();
            for (int i = 0; i < 4; i++) {
                if (i < spells.length) {
                    T component = entityWorld.getComponent(spells[i], componentClass);
                    if (component != null) {
                        show(i, component);
                    } else {
                        hide(i);
                    }
                } else {
                    hide(i);
                }
            }
        }
    }

    private void checkCurrentSpells(ComponentMapObserver observer, int[] spells) {
        for (int i = 0; i < 4; i++) {
            if (i < spells.length) {
                checkChangedComponent(observer.getNew().getComponent(spells[i], componentClass), i);
                checkChangedComponent(observer.getChanged().getComponent(spells[i], componentClass), i);
                checkRemovedComponent(observer.getRemoved().getComponent(spells[i], componentClass), i);
            }
        }
    }

    private void checkChangedComponent(T component, int spellIndex) {
        if (component != null) {
            show(spellIndex, component);
        }
    }

    private void checkRemovedComponent(T component, int spellIndex){
        if (component != null){
            hide(spellIndex);
        }
    }

    protected abstract void show(int spellIndex, T component);

    protected abstract void hide(int spellIndex);
}
