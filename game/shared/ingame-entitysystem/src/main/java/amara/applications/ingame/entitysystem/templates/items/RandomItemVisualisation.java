package amara.applications.ingame.entitysystem.templates.items;

import amara.applications.ingame.entitysystem.templates.items.names.*;

import java.util.HashMap;

public class RandomItemVisualisation {

    private static final String[][] ATTRIBUTE_ITEM_VISUALISATIONS = {
        // AbilityPower
        { "apprentice_rod", "book_of_wisdom", "egos_ring", "moonlight", "nightkiss", "requiem", "the_untamed", "tiny_scepter" },
        // AttackDamage
        { "arcaneblade", "blinkstrike", "cataclysm", "egos_sword", "greatsword", "hells_scream", "icecold", "new_dawn", "reaper", "reinforced_sword", "rusty_sword", "soulblade" },
        // Armor
        { "burning_armor", "ethers_armor", "heavy_leather_armor", "iron_armor", "iron_bulwark", "leather_armor", "lifebinder" },
        // MagicResistance
        { "arcane_vesture", "arcaneblade", "enchanted_vesture", "green_gem", "mirror_coat", "natures_protector" },
        // MaximumHealth
        { "book_of_vitality", "cotton_armor", "egos_shield", "leather_armor" },
        // AttackSpeed
        { "dagger", "spike_dagger", "swift_bow", "swift_dagger", "thunderbolt", "wooden_bow" },
        // CooldownSpeed
        { "egos_ring", "book_of_wisdom", "soulblade", "spike_dagger" },
        // CriticalChance
        { "blinkstrike", "book_of_extreme_precision", "book_of_precision", "boots_of_intellect" },
        // Lifesteal
        { "book_of_vampirism", "doomblade", "hells_scream", "reaper", "red_gem" },
        // WalkSpeed
        { "boots_of_ferocity", "boots_of_haste", "boots_of_intellect", "boots_of_intellect", "boots_of_iron", "boots_of_intellect", "boots_of_silence", "boots_of_sorcery", "wanderers_treads" },
    };
    private static final NameGenerator_Bow NAME_GENERATOR_BOW = new NameGenerator_Bow();
    private static final NameGenerator_ChestArmor NAME_GENERATOR_CHEST_ARMOR = new NameGenerator_ChestArmor();
    private static final NameGenerator_Dagger NAME_GENERATOR_DAGGER = new NameGenerator_Dagger();
    private static final NameGenerator_FeetArmor NAME_GENERATOR_FEET_ARMOR = new NameGenerator_FeetArmor();
    private static final NameGenerator_Jewelry NAME_GENERATOR_JEWELRY = new NameGenerator_Jewelry();
    private static final NameGenerator_MagicTome NAME_GENERATOR_MAGIC_TOME = new NameGenerator_MagicTome();
    private static final NameGenerator_Shield NAME_GENERATOR_SHIELD = new NameGenerator_Shield();
    private static final NameGenerator_Staff NAME_GENERATOR_STAFF = new NameGenerator_Staff();
    private static final NameGenerator_Sword NAME_GENERATOR_SWORD = new NameGenerator_Sword();
    private static final HashMap<String, NameGenerator> ITEM_VISUALISATIONS_NAME_GENERATORS = new HashMap<>();
    static {
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("apprentice_rod", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("arcane_vesture", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("arcaneblade", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("blinkstrike", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("book_of_extreme_precision", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("book_of_precision", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("book_of_vampirism", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("book_of_vitality", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("book_of_wisdom", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_ferocity", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_haste", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_intellect", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_iron", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_silence", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_sorcery", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("burning_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("cataclysm", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("cotton_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("dagger", NAME_GENERATOR_DAGGER);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("doomblade", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_ring", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_shield", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_sword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("enchanted_vesture", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("ethers_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("greatsword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("green_gem", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("heavy_leather_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("hells_scream", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("icecold", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("iron_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("iron_bulwark", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("leather_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("lifebinder", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("mirror_coat", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("moonlight", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("natures_protector", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("new_dawn", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("nightkiss", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("reaper", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("red_gem", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("reinforced_sword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("requiem", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("rusty_sword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("soulblade", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("spike_dagger", NAME_GENERATOR_DAGGER);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("swift_bow", NAME_GENERATOR_BOW);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("swift_dagger", NAME_GENERATOR_DAGGER);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("the_untamed", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("thunderbolt", NAME_GENERATOR_DAGGER);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("tiny_scepter", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("wanderers_treads", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("wooden_bow", NAME_GENERATOR_BOW);
    }

    public static String getRandomItemVisualisation(int attributeIndex) {
        String[] itemVisualisations = ATTRIBUTE_ITEM_VISUALISATIONS[attributeIndex];
        int itemVisualisationIndex = (int) (Math.random() * itemVisualisations.length);
        return itemVisualisations[itemVisualisationIndex];
    }

    public static String getRandomItemName(String itemVisualisation) {
        NameGenerator nameGenerator = ITEM_VISUALISATIONS_NAME_GENERATORS.get(itemVisualisation);
        return nameGenerator.generateName();
    }
}
