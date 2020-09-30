package amara.applications.ingame.entitysystem.templates;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.items.IsSellableComponent;
import amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent;
import amara.applications.ingame.entitysystem.templates.items.RandomItemVisualisation;
import amara.libraries.entitysystem.EntityWorld;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EntityContentGenerator {

    private static final int ATTRIBUTES_COUNT = 11;

    public static int generateRandomItem(EntityWorld entityWorld) {
        int attributesCount = generateRandomAttributesCount();
        int[] attributeIndices = generateRandomAttributeIndices(attributesCount);
        int totalStats = 10 + generateWeightedValue(0.97f);
        HashMap<Integer, Integer> attributeStats = new HashMap<>();
        for (int i = 0; i < totalStats; i++) {
            int attributeIndexIndex = (int) (Math.random() * attributesCount);
            int attributeIndex = attributeIndices[attributeIndexIndex];
            int currentAttributeStat = attributeStats.computeIfAbsent(attributeIndex, ai -> 0);
            attributeStats.put(attributeIndex, currentAttributeStat + 1);
        }
        return createItemEntity(entityWorld, attributeStats);
    }

    private static int generateRandomAttributesCount() {
        int attributesCount = 1;
        if (Math.random() < 0.9) {
            attributesCount++;
            while ((Math.random() < 0.33) && (attributesCount < ATTRIBUTES_COUNT)) {
                attributesCount++;
            }
        }
        return attributesCount;
    }

    private static int[] generateRandomAttributeIndices(int attributesCount) {
        LinkedList<Integer> remainingAttributeIndices = new LinkedList<>();
        for (int i = 0; i < ATTRIBUTES_COUNT; i++) {
            remainingAttributeIndices.add(i);
        }
        int[] attributeIndices = new int[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            int attributeIndexIndex = (int) (Math.random() * remainingAttributeIndices.size());
            Integer attributeIndex = remainingAttributeIndices.remove(attributeIndexIndex);
            attributeIndices[i] = attributeIndex;
        }
        return attributeIndices;
    }

    private static int createItemEntity(EntityWorld entityWorld, HashMap<Integer, Integer> attributeStats) {
        int itemEntity = entityWorld.createEntity();
        int totalStats = 0;
        int highestAttributeIndex = -1;
        int highestAttributeStat = -1;
        for (Map.Entry<Integer, Integer> entry : attributeStats.entrySet()) {
            int attributeIndex = entry.getKey();
            int attributeStat = entry.getValue();
            Object bonusAttributeComponent = null;
            switch (attributeIndex) {
                case 0: bonusAttributeComponent = new BonusFlatAbilityPowerComponent(attributeStat); break;
                case 1: bonusAttributeComponent = new BonusFlatAttackDamageComponent(attributeStat); break;
                case 2: bonusAttributeComponent = new BonusFlatArmorComponent(attributeStat); break;
                case 3: bonusAttributeComponent = new BonusFlatMagicResistanceComponent(attributeStat); break;
                case 4: bonusAttributeComponent = new BonusFlatMaximumHealthComponent(attributeStat); break;
                case 5: bonusAttributeComponent = new BonusFlatMaximumManaComponent(attributeStat); break;
                case 6: bonusAttributeComponent = new BonusPercentageAttackSpeedComponent(0.01f * attributeStat); break;
                case 7: bonusAttributeComponent = new BonusPercentageCooldownSpeedComponent(0.01f * attributeStat); break;
                case 8: bonusAttributeComponent = new BonusPercentageCriticalChanceComponent(0.01f * attributeStat); break;
                case 9: bonusAttributeComponent = new BonusPercentageLifestealComponent(0.01f * attributeStat); break;
                case 10: bonusAttributeComponent = new BonusPercentageWalkSpeedComponent(0.01f * attributeStat); break;
            }
            entityWorld.setComponent(itemEntity, bonusAttributeComponent);
            totalStats += attributeStat;
            if (attributeStat > highestAttributeStat) {
                highestAttributeIndex = attributeIndex;
                highestAttributeStat = attributeStat;
            }
        }
        String itemVisualisation = RandomItemVisualisation.getRandomItemVisualisation(highestAttributeIndex);
        String itemName = RandomItemVisualisation.getRandomItemName(itemVisualisation);
        entityWorld.setComponent(itemEntity, new ItemVisualisationComponent(itemVisualisation));
        entityWorld.setComponent(itemEntity, new NameComponent(itemName));
        entityWorld.setComponent(itemEntity, new IsSellableComponent(totalStats));
        return itemEntity;
    }

    public static int generateWeightedValue(float rate) {
        int value = 0;
        while (Math.random() < rate) {
            value++;
        }
        return value;
    }
}
