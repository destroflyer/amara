package amara.applications.ingame.entitysystem.templates.items;

import amara.applications.ingame.entitysystem.templates.items.names.*;

import java.util.HashMap;

public class RandomItemVisualisation {

    private static final String[][] ATTRIBUTE_ITEM_VISUALISATIONS = {
        // AbilityPower
        { "apprentice_notes", "appropriately_sized_wand", "egos_ring", "forbidden_chapter", "nightkiss", "orb_of_might", "requiem", "shard_of_time", "silver_scroll", "the_untamed" },
        // AttackDamage
        { "blacksmiths_wrath", "broken_sword", "cataclysm", "egos_sword", "greatsword", "hells_scream", "icecold", "new_dawn", "runic_blades", "silver_plated_pike", "soulblade", "spiked_flail", "waraxe" },
        // Armor
        { "egos_shield", "gladiator_helmet", "glowing_tabard", "invincible", "iron_bulwark", "padded_vest", "plated_armor", "runic_bracers", "solar_diadem" },
        // MagicResistance
        { "arcane_cape", "demon_figurine", "enchanted_cloak", "shredded_cape" },
        // MaximumHealth
        { "ethers_armor", "evergreen_bamboo", "molten_heart", "natures_secret", "rainbow_gem", "red_gem" },
        // Mana
        { "blue_gem", "egos_ring", "icy_trinket", "shard_of_time" },
        // AttackSpeed
        { "arrowhead", "quiver", "rapidfire_tower", "swift_bow", "thunderlord_trident" },
        // CooldownSpeed
        { "arcane_cape", "rainbow_gem", "shard_of_time", "solar_diadem" },
        // CriticalChance
        { "new_dawn", "precision_gloves", "swift_bow" },
        // Lifesteal
        { "blood_capsule", "hells_scream" },
        // WalkSpeed
        { "boots_of_ferocity", "enchanted_boots", "moccasins", "noble_boots", "plated_boots", "sandals", "swiftwalkers" },
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
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("apprentice_notes", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("boots_of_ferocity", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("enchanted_boots", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("moccasins", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("noble_boots", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("plated_boots", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("sandals", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("swiftwalkers", NAME_GENERATOR_FEET_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("appropriately_sized_wand", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("arcane_cape", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("arrowhead", NAME_GENERATOR_DAGGER);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("blacksmiths_wrath", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("blood_capsule", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("blue_gem", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("broken_sword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("cataclysm", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("demon_figurine", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_ring", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_shield", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("egos_sword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("enchanted_cloak", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("ethers_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("evergreen_bamboo", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("forbidden_chapter", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("gladiator_helmet", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("glowing_tabard", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("greatsword", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("hells_scream", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("icecold", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("icy_trinket", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("invincible", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("iron_bulwark", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("molten_heart", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("natures_secret", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("new_dawn", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("nightkiss", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("orb_of_might", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("padded_vest", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("plated_armor", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("precision_gloves", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("quiver", NAME_GENERATOR_BOW);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("rainbow_gem", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("rapidfire_tower", NAME_GENERATOR_BOW);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("red_gem", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("requiem", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("runic_blades", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("runic_bracers", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("shard_of_time", NAME_GENERATOR_JEWELRY);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("shredded_cape", NAME_GENERATOR_CHEST_ARMOR);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("silver_plated_pike", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("silver_scroll", NAME_GENERATOR_MAGIC_TOME);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("solar_diadem", NAME_GENERATOR_SHIELD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("soulblade", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("spiked_flail", NAME_GENERATOR_SWORD);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("swift_bow", NAME_GENERATOR_BOW);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("the_untamed", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("thunderlord_trident", NAME_GENERATOR_STAFF);
        ITEM_VISUALISATIONS_NAME_GENERATORS.put("waraxe", NAME_GENERATOR_SWORD);
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
