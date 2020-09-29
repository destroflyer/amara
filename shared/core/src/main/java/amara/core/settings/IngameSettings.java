package amara.core.settings;

import com.jme3.input.KeyInput;
import amara.core.input.events.MouseClickEvent;
import amara.core.settings.types.*;

public class IngameSettings extends SettingsCategory {

    public IngameSettings(){
        super(null, "Ingame Settings", new SettingsCategory[] {
            new SettingsCategory("controls_topdown", "Controls (top-down)", new SettingsCategory[] {
                new CategorizedSettingsCategory(
                    "spells", "Spells",
                    new Setting[] {
                        new CategorizedSetting("0", "Spell #1", new KeyType(KeyInput.KEY_Q)),
                        new CategorizedSetting("1", "Spell #2", new KeyType(KeyInput.KEY_W)),
                        new CategorizedSetting("2", "Spell #3", new KeyType(KeyInput.KEY_E)),
                        new CategorizedSetting("3", "Spell #4", new KeyType(KeyInput.KEY_R)),
                        new CategorizedSetting("map_0", "MapSpell #1", new KeyType(KeyInput.KEY_D)),
                        new CategorizedSetting("map_1", "MapSpell #2", new KeyType(KeyInput.KEY_F)),
                        new CategorizedSetting("map_2", "MapSpell #3", new KeyType(KeyInput.KEY_B))
                    }
                ),
                new CategorizedSettingsCategory(
                    "navigation", "Navigation",
                    new Setting[] {
                        new CategorizedSetting("select", "Select", new MouseButtonType(MouseClickEvent.Button.Left)),
                        new CategorizedSetting("walk_attack", "Walk / Attack", new MouseButtonType(MouseClickEvent.Button.Right)),
                        new CategorizedSetting("stop", "Stop", new KeyType(KeyInput.KEY_S))
                    }
                ),
                new SettingsCategory(
                    "camera", "Camera",
                    new Setting[]{
                        new CategorizedSetting("movement_speed", "Movement Speed", new SliderType(50, 1, 100, 1, 5)),
                        new CategorizedSetting("movement_cursor_border", "Cursor Border Size", new SliderType(5, 0, 150, 1, 5)),
                        new CategorizedSetting("move_to_player_screen_extension", "Move to Player Extension", new SliderType(100, 0, 1000, 10, 50))
                    }
                ),
                new CategorizedSettingsCategory(
                    "items", "Items",
                    new Setting[]{
                        new CategorizedSetting("0", "Item #1", new KeyType(KeyInput.KEY_1)),
                        new CategorizedSetting("1", "Item #2", new KeyType(KeyInput.KEY_2)),
                        new CategorizedSetting("2", "Item #3", new KeyType(KeyInput.KEY_3)),
                        new CategorizedSetting("3", "Item #4", new KeyType(KeyInput.KEY_4)),
                        new CategorizedSetting("4", "Item #5", new KeyType(KeyInput.KEY_5)),
                        new CategorizedSetting("5", "Item #6", new KeyType(KeyInput.KEY_6))
                    }
                ),
                new CategorizedSettingsCategory(
                    "interface", "Interface",
                    new Setting[]{
                        new CategorizedSetting("scoreboard", "Scoreboard", new KeyType(KeyInput.KEY_TAB)),
                        new CategorizedSetting("shop", "Shop", new KeyType(KeyInput.KEY_P)),
                        new CategorizedSetting("bag", "Bag", new KeyType(KeyInput.KEY_I)),
                        new CategorizedSetting("menu", "Menu", new KeyType(KeyInput.KEY_ESCAPE)),
                    }
                ),
                new CategorizedSettingsCategory(
                    "reactions", "Reactions",
                    new Setting[]{
                        new CategorizedSetting("0", "Kappa", new KeyType(KeyInput.KEY_F1)),
                        new CategorizedSetting("1", "PogChamp", new KeyType(KeyInput.KEY_F2)),
                        new CategorizedSetting("2", "Kreygasm", new KeyType(KeyInput.KEY_F3)),
                        new CategorizedSetting("3", "BibleThump", new KeyType(KeyInput.KEY_F4))
                    }
                )
            }),
            new SettingsCategory("controls_3rdperson", "Controls (3rd person)", new SettingsCategory[] {
                new CategorizedSettingsCategory(
                    "spells", "Spells",
                    new Setting[] {
                        new CategorizedSetting("0", "Spell #1", new KeyType(KeyInput.KEY_1)),
                        new CategorizedSetting("1", "Spell #2", new KeyType(KeyInput.KEY_2)),
                        new CategorizedSetting("2", "Spell #3", new KeyType(KeyInput.KEY_3)),
                        new CategorizedSetting("3", "Spell #4", new KeyType(KeyInput.KEY_4)),
                        new CategorizedSetting("map_0", "MapSpell #1", new KeyType(KeyInput.KEY_F)),
                        new CategorizedSetting("map_1", "MapSpell #2", new KeyType(KeyInput.KEY_G)),
                        new CategorizedSetting("map_2", "MapSpell #3", new KeyType(KeyInput.KEY_H))
                    }
                ),
                new CategorizedSettingsCategory(
                    "navigation", "Navigation",
                    new Setting[] {
                        new CategorizedSetting("forward", "Forward", new KeyType(KeyInput.KEY_W)),
                        new CategorizedSetting("left", "Left", new KeyType(KeyInput.KEY_A)),
                        new CategorizedSetting("backward", "Backward", new KeyType(KeyInput.KEY_S)),
                        new CategorizedSetting("right", "Right", new KeyType(KeyInput.KEY_D)),
                        new CategorizedSetting("select", "Select", new MouseButtonType(MouseClickEvent.Button.Left)),
                        new CategorizedSetting("attack", "Attack", new MouseButtonType(MouseClickEvent.Button.Right))
                    }
                ),
                new CategorizedSettingsCategory(
                    "items", "Items",
                    new Setting[]{
                        new CategorizedSetting("0", "Item #1", new KeyType(KeyInput.KEY_Y)),
                        new CategorizedSetting("1", "Item #2", new KeyType(KeyInput.KEY_X)),
                        new CategorizedSetting("2", "Item #3", new KeyType(KeyInput.KEY_C)),
                        new CategorizedSetting("3", "Item #4", new KeyType(KeyInput.KEY_V)),
                        new CategorizedSetting("4", "Item #5", new KeyType(KeyInput.KEY_B)),
                        new CategorizedSetting("5", "Item #6", new KeyType(KeyInput.KEY_N))
                    }
                ),
                new CategorizedSettingsCategory(
                    "interface", "Interface",
                    new Setting[]{
                        new CategorizedSetting("scoreboard", "Scoreboard", new KeyType(KeyInput.KEY_TAB)),
                        new CategorizedSetting("shop", "Shop", new KeyType(KeyInput.KEY_P)),
                        new CategorizedSetting("bag", "Bag", new KeyType(KeyInput.KEY_I)),
                        new CategorizedSetting("menu", "Menu", new KeyType(KeyInput.KEY_ESCAPE))
                    }
                ),
                new CategorizedSettingsCategory(
                    "reactions", "Reactions",
                    new Setting[]{
                        new CategorizedSetting("0", "Kappa", new KeyType(KeyInput.KEY_F1)),
                        new CategorizedSetting("1", "PogChamp", new KeyType(KeyInput.KEY_F2)),
                        new CategorizedSetting("2", "Kreygasm", new KeyType(KeyInput.KEY_F3)),
                        new CategorizedSetting("3", "BibleThump", new KeyType(KeyInput.KEY_F4))
                    }
                )
            }),
            new SettingsCategory("graphics", "Graphics", new SettingsCategory[]{
                new SettingsCategory(
                    "video", "Video",
                    new Setting[]{
                        new Setting("resolution_width", "Resolution (Width)", new SliderType(1280, 800, 1920, 1, 10)),
                        new Setting("resolution_height", "Resolution (Height)", new SliderType(720, 600, 1080, 1, 10)),
                        new Setting("antialiasing", "Antialiasing", new SliderType(0, 0, 32, 1, 1)),
                        new Setting("fullscreen", "Fullscreen", new BooleanType(true)),
                        new Setting("vsync", "VSync", new BooleanType(true))
                    }
                ),
                new SettingsCategory(
                    "effects", "Effects",
                    new Setting[]{
                        new Setting("hardware_skinning", "Hardware Skinning", new BooleanType(true)),
                        new Setting("terrain_quality", "Terrain Quality", new SliderType(0, 0, 4, 1, 1)),
                        new Setting("terrain_triplanar_mapping", "Terrain Tri-Planar Mapping", new BooleanType(true)),
                        new Setting("ssao", "SSAO", new BooleanType(true)),
                        new Setting("shadow_quality", "Shadow Quality", new SliderType(2, 0, 4, 1, 1)),
                        new Setting("fog_of_war_resolution", "F.o.W. Resolution", new SliderType(1, 0.5f, 10, 0.1f, 0.5f)),
                        new Setting("fog_of_war_update_interval", "F.o.W. Update Interval", new SliderType(0.02f, 0, 1, 0.005f, 0.01f)),
                        new Setting("minimap_update_interval", "Minimap Update Interval", new SliderType(0.04f, 0, 1, 0.005f, 0.01f)),
                        new Setting("hover_outline_thickness", "Hover outline thickness", new SliderType(0.1f, 0, 0.5f, 0.005f, 0.05f))
                    }
                ),
            }),
            new SettingsCategory("sound", "Sound", new SettingsCategory[]{
                new SettingsCategory(
                    "mix", "Mix",
                    new Setting[]{
                        new Setting("audio_volume", "Audio Volume", new SliderType(0.07f, 0, 1, 0.01f, 0.05f))
                    }
                )
            })
        });
    }
}
