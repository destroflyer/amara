/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.settings;

import amara.engine.settings.types.*;

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
                        new CategorizedSetting("0", "Spell #1", new KeyType()),
                        new CategorizedSetting("1", "Spell #2", new KeyType()),
                        new CategorizedSetting("2", "Spell #3", new KeyType()),
                        new CategorizedSetting("3", "Spell #4", new KeyType())
                    }
                ),
                new SettingsCategory(
                    "navigation", "Navigation",
                    new Setting[]{
                        new CategorizedSetting("select", "Select", new MouseButtonType()),
                        new CategorizedSetting("walk_attack", "Walk / Attack", new MouseButtonType())
                    }
                ),
                new SettingsCategory(
                    "camera", "Camera",
                    new Setting[]{
                        new CategorizedSetting("movement_speed", "Movement Speed", new SliderType(20, 1, 100, 1, 5)),
                        new CategorizedSetting("movement_cursor_border", "Cursor Border Size", new SliderType(90, 0, 200, 1, 5))
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
                        new Setting("fullscreen", "Fullscreen", new BooleanType(false)),
                        new Setting("vsync", "VSync", new BooleanType(false))
                    }
                ),
                new SettingsCategory(
                    "effects", "Effects",
                    new Setting[]{
                        new Setting("hardware_skinning", "Hardware Skinning", new BooleanType(false)),
                        new Setting("terrain_quality", "Terrain Quality", new SliderType(1, 0, 4, 1, 1)),
                        new Setting("shadow_quality", "Shadow Quality", new SliderType(2, 0, 4, 1, 1)),
                        new Setting("fog_of_war_update_interval", "F.o.W. Update Interval", new SliderType(0.05f, 0, 1, 0.01f, 0.05f))
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
