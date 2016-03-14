/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.settings;

import com.jme3.input.KeyInput;
import amara.core.input.events.MouseClickEvent;
import amara.core.settings.types.*;

/**
 *
 * @author Carl
 */
public class IngameSettings extends SettingsCategory{

    public IngameSettings(){
        super(null, "Ingame Settings", new SettingsCategory[]{
            new SettingsCategory("controls", "Controls", new SettingsCategory[]{
                new CategorizedSettingsCategory(
                    "spells", "Spells",
                    new Setting[]{
                        new CategorizedSetting("0", "Spell #1", new KeyType(KeyInput.KEY_Q)),
                        new CategorizedSetting("1", "Spell #2", new KeyType(KeyInput.KEY_W)),
                        new CategorizedSetting("2", "Spell #3", new KeyType(KeyInput.KEY_E)),
                        new CategorizedSetting("3", "Spell #4", new KeyType(KeyInput.KEY_R)),
                        new CategorizedSetting("backport", "Backport", new KeyType(KeyInput.KEY_B)),
                        new CategorizedSetting("player_0", "PlayerSpell #1", new KeyType(KeyInput.KEY_D)),
                        new CategorizedSetting("player_1", "PlayerSpell #2", new KeyType(KeyInput.KEY_F))
                    }
                ),
                new CategorizedSettingsCategory(
                    "navigation", "Navigation",
                    new Setting[]{
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
                        new Setting("frame_rate", "Frame Rate", new SliderType(60, 15, 120, 1, 1)),
                        new Setting("antialiasing", "Antialiasing", new SliderType(0, 0, 32, 1, 1)),
                        new Setting("fullscreen", "Fullscreen", new BooleanType(true)),
                        new Setting("vsync", "VSync", new BooleanType(true))
                    }
                ),
                new SettingsCategory(
                    "effects", "Effects",
                    new Setting[]{
                        new Setting("hardware_skinning", "Hardware Skinning", new BooleanType(true)),
                        new Setting("terrain_quality", "Terrain Quality", new SliderType(1, 0, 4, 1, 1)),
                        new Setting("shadow_quality", "Shadow Quality", new SliderType(2, 0, 4, 1, 1)),
                        new Setting("fog_of_war_update_interval", "F.o.W. Update Interval", new SliderType(0.05f, 0, 1, 0.01f, 0.05f)),
                        new Setting("minimap_update_interval", "Minimap Update Interval", new SliderType(0.05f, 0, 1, 0.01f, 0.05f))
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
            }),
            new SettingsCategory("other", "Other", new SettingsCategory[0])
        });
    }
}
