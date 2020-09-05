package amara.applications.ingame.maps;

class MapDefaults {

    private static final String[] shopItemNames = new String[]{
        "egos_sword","egos_shield","egos_ring",
        "red_gem",
        "wanderers_treads","boots_of_haste","boots_of_ferocity","boots_of_sorcery","boots_of_intellect","boots_of_iron","boots_of_silence",
        "rusty_sword","reinforced_sword","greatsword","dagger","wooden_bow",
        "leather_armor","heavy_leather_armor","cotton_armor","iron_armor","arcane_vesture","enchanted_vesture",
        "book_of_vampirism","doomblade","hells_scream",
        "cataclysm","icecold","spike_dagger","thunderbolt",
        "book_of_precision","book_of_extreme_precision","blinkstrike","new_dawn",
        "swift_dagger","swift_bow",
        "tiny_scepter","apprentice_rod","the_untamed",
        "book_of_wisdom","moonlight","nightkiss","requiem",
        "book_of_vitality","ethers_armor",
        "iron_bulwark","natures_protector",
        "burning_armor","mirror_coat",
        "reaper","arcaneblade","soulblade"
    };

    static String[] getShopItemTemplateNames() {
        String[] shopItemTemplateNames = new String[shopItemNames.length];
        for (int i = 0; i < shopItemTemplateNames.length; i++) {
            shopItemTemplateNames[i] = "items/" + shopItemNames[i];
        }
        return shopItemTemplateNames;
    }
}
