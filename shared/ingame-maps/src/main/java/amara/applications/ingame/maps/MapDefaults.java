package amara.applications.ingame.maps;

class MapDefaults {

    private static final String[] shopItemNames = new String[] {
        "egos_sword","egos_shield","egos_ring",
        "healing_potion",
        "sandals","boots_of_ferocity","enchanted_boots","noble_boots","plated_boots","moccasins","swiftwalkers",
        "broken_sword","waraxe","greatsword","arrowhead","quiver",
        "red_gem","molten_heart","natures_secret","padded_vest","plated_armor","shredded_cape","enchanted_cloak",
        "blue_gem","icy_trinket","shard_of_time","rainbow_gem",
        "forbidden_chapter","runic_blades","solar_diadem","arcane_cape",
        "blood_capsule","hells_scream",
        "cataclysm", "icecold","rapidfire_tower","thunderlord_trident",
        "precision_gloves", "new_dawn", "swift_bow",
        "apprentice_notes","appropriately_sized_wand","the_untamed",
        "silver_scroll","nightkiss",
        "runic_bracers","invincible","requiem",
        "evergreen_bamboo","natures_secret","ethers_armor",
        "iron_bulwark","demon_figurine",
        "glowing_tabard","gladiator_helmet",
        "spiked_flail","silver_plated_pike","soulblade"
    };

    static String[] getShopItemTemplateNames() {
        String[] shopItemTemplateNames = new String[shopItemNames.length];
        for (int i = 0; i < shopItemTemplateNames.length; i++) {
            shopItemTemplateNames[i] = "items/" + shopItemNames[i];
        }
        return shopItemTemplateNames;
    }
}
