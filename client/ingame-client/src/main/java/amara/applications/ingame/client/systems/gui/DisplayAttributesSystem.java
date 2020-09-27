package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.core.Util;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

import java.util.function.Consumer;
import java.util.function.Function;

public class DisplayAttributesSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayAttributesSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    private final static String NON_EXISTING_ATTRIBUTE_TEXT = "-";
    private int lastInspectedEntity = -1;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        updateAttributes(entityWorld, "player", characterEntity, false);
        int inspectedEntity = getInspectedEntity();
        updateAttributes(entityWorld, "inspection", inspectedEntity, (inspectedEntity != lastInspectedEntity));
        lastInspectedEntity = inspectedEntity;
    }

    private void updateAttributes(EntityWorld entityWorld, String uiPrefix, int entity, boolean forceUpdate) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            HealthComponent.class,
            MaximumHealthComponent.class,
            ManaComponent.class,
            MaximumManaComponent.class,
            AttackDamageComponent.class,
            AbilityPowerComponent.class,
            AttackSpeedComponent.class,
            CooldownSpeedComponent.class,
            ArmorComponent.class,
            MagicResistanceComponent.class,
            WalkSpeedComponent.class
        );
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, AttackDamageComponent.class, AttackDamageComponent::getValue, 0, text -> screenController.setAttributeValue_AttackDamage(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, AbilityPowerComponent.class, AbilityPowerComponent::getValue, 0, text -> screenController.setAttributeValue_AbilityPower(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, AttackSpeedComponent.class, AttackSpeedComponent::getValue, 2, text -> screenController.setAttributeValue_AttackSpeed(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, CooldownSpeedComponent.class, CooldownSpeedComponent::getValue, 2, text -> screenController.setAttributeValue_CooldownSpeed(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, ArmorComponent.class, ArmorComponent::getValue, 0, text -> screenController.setAttributeValue_Armor(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, MagicResistanceComponent.class, MagicResistanceComponent::getValue, 0, text -> screenController.setAttributeValue_MagicResistance(uiPrefix, text));
        updateAttributeValue(entityWorld, observer, entity, forceUpdate, WalkSpeedComponent.class, WalkSpeedComponent::getValue, 2, text -> screenController.setAttributeValue_WalkSpeed(uiPrefix, text));
        if ("player".equals(uiPrefix)) {
            updateResourceBar(entityWorld, observer, entity, forceUpdate, HealthComponent.class, MaximumHealthComponent.class, HealthComponent::getValue, MaximumHealthComponent::getValue, portion -> screenController.setPlayer_ResourceBar_Health_Width(portion), text -> screenController.setPlayer_ResourceBar_Health_Text(text));
            updateResourceBar(entityWorld, observer, entity, forceUpdate, ManaComponent.class, MaximumManaComponent.class, ManaComponent::getValue, MaximumManaComponent::getValue, portion -> screenController.setPlayer_ResourceBar_Mana_Width(portion), text -> screenController.setPlayer_ResourceBar_Mana_Text(text));
        }
    }

    private <T> void updateAttributeValue(EntityWorld entityWorld, ComponentMapObserver observer, int entity, boolean forceUpdate, Class<T> componentClass, Function<T, Float> getValue, int decimals, Consumer<String> displayValueText) {
        if (forceUpdate ? entityWorld.hasComponent(entity, componentClass) : (observer.getNew().hasComponent(entity, componentClass) || observer.getChanged().hasComponent(entity, componentClass))) {
            T component = entityWorld.getComponent(entity, componentClass);
            Float value = getValue.apply(component);
            String valueText;
            if (decimals == 0) {
                valueText = "" + value.intValue();
            } else {
                valueText = "" + Util.round(value, decimals);
            }
            displayValueText.accept(valueText);
        } else if (forceUpdate || observer.getRemoved().hasComponent(entity, componentClass)) {
            displayValueText.accept(NON_EXISTING_ATTRIBUTE_TEXT);
        }
    }

    private <C, M> void updateResourceBar(EntityWorld entityWorld, ComponentMapObserver observer, int entity, boolean forceUpdate, Class<C> currentComponentClass, Class<M> maximumComponentClass, Function<C, Float> getCurrentValue, Function<M, Float> getMaximumValue, Consumer<Float> setWidthPortion, Consumer<String> setText) {
        boolean shouldUpdateExistingValues = entityWorld.hasAllComponents(entity, currentComponentClass, maximumComponentClass);
        if (!forceUpdate) {
            shouldUpdateExistingValues &= (
                    observer.getNew().hasAnyComponent(entity, currentComponentClass, maximumComponentClass)
                 || observer.getChanged().hasAnyComponent(entity, currentComponentClass, maximumComponentClass)
            );
        }
        if (shouldUpdateExistingValues) {
            float currentValue = getCurrentValue.apply(entityWorld.getComponent(entity, currentComponentClass));
            float maximumValue = getMaximumValue.apply(entityWorld.getComponent(entity, maximumComponentClass));
            String valueText = (((int) currentValue) + " / " + ((int) maximumValue));
            float valuePortion = (currentValue / maximumValue);
            setWidthPortion.accept(valuePortion);
            setText.accept(valueText);
        } else  if (forceUpdate || observer.getRemoved().hasAnyComponent(entity, currentComponentClass, maximumComponentClass)) {
            setWidthPortion.accept(0f);
            setText.accept(NON_EXISTING_ATTRIBUTE_TEXT);
        }
    }
}
