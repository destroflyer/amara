package amara.game.entitysystem.templates;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializer;

/**GENERATED**/
public class ComponentsRegistrator{

    public static void registerComponents(){
        XMLTemplateManager xmlTemplateManager = XMLTemplateManager.getInstance();
        //attributes
        Serializer.registerClass(amara.game.entitysystem.components.attributes.AbilityPowerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AbilityPowerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AbilityPowerComponent>("abilityPower"){

            @Override
            public amara.game.entitysystem.components.attributes.AbilityPowerComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.AbilityPowerComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.ArmorComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.ArmorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.ArmorComponent>("armor"){

            @Override
            public amara.game.entitysystem.components.attributes.ArmorComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.ArmorComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.AttackDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AttackDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AttackDamageComponent>("attackDamage"){

            @Override
            public amara.game.entitysystem.components.attributes.AttackDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.AttackDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.AttackSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AttackSpeedComponent>("attackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.AttackSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.AttackSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseAbilityPowerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseAbilityPowerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseAbilityPowerComponent>("baseAbilityPower"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseAbilityPowerComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseAbilityPowerComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseArmorComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseArmorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseArmorComponent>("baseArmor"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseArmorComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseArmorComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseAttackDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseAttackDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseAttackDamageComponent>("baseAttackDamage"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseAttackDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseAttackDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseAttackSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseAttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseAttackSpeedComponent>("baseAttackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseAttackSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseAttackSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseHealthRegenerationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseHealthRegenerationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseHealthRegenerationComponent>("baseHealthRegeneration"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseHealthRegenerationComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseHealthRegenerationComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseMagicResistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseMagicResistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseMagicResistanceComponent>("baseMagicResistance"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseMagicResistanceComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseMagicResistanceComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseMaximumHealthComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseMaximumHealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseMaximumHealthComponent>("baseMaximumHealth"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseMaximumHealthComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseMaximumHealthComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BaseWalkSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BaseWalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BaseWalkSpeedComponent>("baseWalkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BaseWalkSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BaseWalkSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent>("bonusFlatAbilityPower"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatArmorComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatArmorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatArmorComponent>("bonusFlatArmor"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatArmorComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatArmorComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent>("bonusFlatAttackDamage"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent>("bonusFlatHealthRegeneration"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent>("bonusFlatMagicResistance"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent>("bonusFlatMaximumHealth"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent>("bonusFlatWalkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent>("bonusPercentageAttackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent>("bonusPercentageCooldownSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent>("bonusPercentageWalkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.CooldownSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.CooldownSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.CooldownSpeedComponent>("cooldownSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.CooldownSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.CooldownSpeedComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.HealthComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.HealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.HealthComponent>("health"){

            @Override
            public amara.game.entitysystem.components.attributes.HealthComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.HealthComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.HealthRegenerationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.HealthRegenerationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.HealthRegenerationComponent>("healthRegeneration"){

            @Override
            public amara.game.entitysystem.components.attributes.HealthRegenerationComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.HealthRegenerationComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.MagicResistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.MagicResistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.MagicResistanceComponent>("magicResistance"){

            @Override
            public amara.game.entitysystem.components.attributes.MagicResistanceComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.MagicResistanceComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.MaximumHealthComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.MaximumHealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.MaximumHealthComponent>("maximumHealth"){

            @Override
            public amara.game.entitysystem.components.attributes.MaximumHealthComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.MaximumHealthComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent>("requestUpdateAttributes"){

            @Override
            public amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent construct(){
                return new amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.attributes.WalkSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.WalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.WalkSpeedComponent>("walkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.WalkSpeedComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.attributes.WalkSpeedComponent(value);
            }
        });
        //audio
        Serializer.registerClass(amara.game.entitysystem.components.audio.AudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioComponent>("audio"){

            @Override
            public amara.game.entitysystem.components.audio.AudioComponent construct(){
                String audioPath = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.audio.AudioComponent(audioPath);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.AudioLoopComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioLoopComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioLoopComponent>("audioLoop"){

            @Override
            public amara.game.entitysystem.components.audio.AudioLoopComponent construct(){
                return new amara.game.entitysystem.components.audio.AudioLoopComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.AudioSourceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioSourceComponent>("audioSource"){

            @Override
            public amara.game.entitysystem.components.audio.AudioSourceComponent construct(){
                int entity = createChildEntity(0, "entity");
                return new amara.game.entitysystem.components.audio.AudioSourceComponent(entity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioSuccessorComponent>("audioSuccessor"){

            @Override
            public amara.game.entitysystem.components.audio.AudioSuccessorComponent construct(){
                int audioEntity = createChildEntity(0, "audioEntity");
                float delay = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("delay")));
                return new amara.game.entitysystem.components.audio.AudioSuccessorComponent(audioEntity, delay);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.AudioVolumeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioVolumeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioVolumeComponent>("audioVolume"){

            @Override
            public amara.game.entitysystem.components.audio.AudioVolumeComponent construct(){
                float volume = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.audio.AudioVolumeComponent(volume);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.IsAudioPausedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.IsAudioPausedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.IsAudioPausedComponent>("isAudioPaused"){

            @Override
            public amara.game.entitysystem.components.audio.IsAudioPausedComponent construct(){
                return new amara.game.entitysystem.components.audio.IsAudioPausedComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.audio.IsAudioPlayingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.IsAudioPlayingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.IsAudioPlayingComponent>("isAudioPlaying"){

            @Override
            public amara.game.entitysystem.components.audio.IsAudioPlayingComponent construct(){
                return new amara.game.entitysystem.components.audio.IsAudioPlayingComponent();
            }
        });
        //buffs
        //areas
        Serializer.registerClass(amara.game.entitysystem.components.buffs.areas.AreaBuffComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaBuffComponent>("areaBuff"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaBuffComponent(buffEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent>("areaBuffTargetRules"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent(targetRulesEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.areas.AreaOriginComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaOriginComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaOriginComponent>("areaOrigin"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaOriginComponent construct(){
                int originEntity = createChildEntity(0, "originEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaOriginComponent(originEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.areas.AreaSourceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaSourceComponent>("areaSource"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaSourceComponent(sourceEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.ContinuousEffectComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.ContinuousEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.ContinuousEffectComponent>("continuousEffect"){

            @Override
            public amara.game.entitysystem.components.buffs.ContinuousEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                return new amara.game.entitysystem.components.buffs.ContinuousEffectComponent(effectEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.KeepOnDeathComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.KeepOnDeathComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.KeepOnDeathComponent>("keepOnDeath"){

            @Override
            public amara.game.entitysystem.components.buffs.KeepOnDeathComponent construct(){
                return new amara.game.entitysystem.components.buffs.KeepOnDeathComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent>("onBuffRemoveEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.RepeatingEffectComponent>("repeatingEffect"){

            @Override
            public amara.game.entitysystem.components.buffs.RepeatingEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                float interval = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("interval")));
                return new amara.game.entitysystem.components.buffs.RepeatingEffectComponent(effectEntity, interval);
            }
        });
        //status
        Serializer.registerClass(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.ActiveBuffComponent>("activeBuff"){

            @Override
            public amara.game.entitysystem.components.buffs.status.ActiveBuffComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.buffs.status.ActiveBuffComponent(targetEntity, buffEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent>("buffVisualisation"){

            @Override
            public amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent(name);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent>("remainingBuffDuration"){

            @Override
            public amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent>("removeFromTarget"){

            @Override
            public amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent construct(){
                return new amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent>("timeSinceLastRepeatingEffect"){

            @Override
            public amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent(duration);
            }
        });
        //camps
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampHealthResetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampHealthResetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampHealthResetComponent>("campHealthReset"){

            @Override
            public amara.game.entitysystem.components.camps.CampHealthResetComponent construct(){
                return new amara.game.entitysystem.components.camps.CampHealthResetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent>("campMaximumAggroDistance"){

            @Override
            public amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent construct(){
                float distance = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent(distance);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent>("campRemainingRespawnDuration"){

            @Override
            public amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampRespawnDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampRespawnDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampRespawnDurationComponent>("campRespawnDuration"){

            @Override
            public amara.game.entitysystem.components.camps.CampRespawnDurationComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.camps.CampRespawnDurationComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampSpawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampSpawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampSpawnComponent>("campSpawn"){

            @Override
            public amara.game.entitysystem.components.camps.CampSpawnComponent construct(){
                return new amara.game.entitysystem.components.camps.CampSpawnComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampSpawnInformationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampSpawnInformationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampSpawnInformationComponent>("campSpawnInformation"){

            @Override
            public amara.game.entitysystem.components.camps.CampSpawnInformationComponent construct(){
                int[] spawnInformationEntities = createChildEntities(0, "spawnInformationEntities");
                return new amara.game.entitysystem.components.camps.CampSpawnInformationComponent(spawnInformationEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.camps.CampUnionAggroComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampUnionAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampUnionAggroComponent>("campUnionAggro"){

            @Override
            public amara.game.entitysystem.components.camps.CampUnionAggroComponent construct(){
                return new amara.game.entitysystem.components.camps.CampUnionAggroComponent();
            }
        });
        //effects
        Serializer.registerClass(amara.game.entitysystem.components.effects.AffectedTargetsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.AffectedTargetsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.AffectedTargetsComponent>("affectedTargets"){

            @Override
            public amara.game.entitysystem.components.effects.AffectedTargetsComponent construct(){
                int[] targetEntities = createChildEntities(0, "targetEntities");
                return new amara.game.entitysystem.components.effects.AffectedTargetsComponent(targetEntities);
            }
        });
        //aggro
        Serializer.registerClass(amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent>("drawTeamAggro"){

            @Override
            public amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent construct(){
                float range = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent(range);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.ApplyEffectImpactComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.ApplyEffectImpactComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.ApplyEffectImpactComponent>("applyEffectImpact"){

            @Override
            public amara.game.entitysystem.components.effects.ApplyEffectImpactComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.ApplyEffectImpactComponent(targetEntity);
            }
        });
        //audio
        Serializer.registerClass(amara.game.entitysystem.components.effects.audio.PauseAudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.audio.PauseAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.audio.PauseAudioComponent>("pauseAudio"){

            @Override
            public amara.game.entitysystem.components.effects.audio.PauseAudioComponent construct(){
                int[] audioEntities = createChildEntities(0, "audioEntities");
                return new amara.game.entitysystem.components.effects.audio.PauseAudioComponent(audioEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.audio.PlayAudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.audio.PlayAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.audio.PlayAudioComponent>("playAudio"){

            @Override
            public amara.game.entitysystem.components.effects.audio.PlayAudioComponent construct(){
                int[] audioEntities = createChildEntities(0, "audioEntities");
                return new amara.game.entitysystem.components.effects.audio.PlayAudioComponent(audioEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.audio.StopAudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.audio.StopAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.audio.StopAudioComponent>("stopAudio"){

            @Override
            public amara.game.entitysystem.components.effects.audio.StopAudioComponent construct(){
                int[] audioEntities = createChildEntities(0, "audioEntities");
                return new amara.game.entitysystem.components.effects.audio.StopAudioComponent(audioEntities);
            }
        });
        //buffs
        Serializer.registerClass(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.AddBuffComponent>("addBuff"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.AddBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("duration")));
                return new amara.game.entitysystem.components.effects.buffs.AddBuffComponent(buffEntity, duration);
            }
        });
        //areas
        Serializer.registerClass(amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent>("addBuffArea"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent construct(){
                int buffAreaEntity = createChildEntity(0, "buffAreaEntity");
                return new amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent(buffAreaEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent>("removeBuffArea"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent construct(){
                int buffAreaEntity = createChildEntity(0, "buffAreaEntity");
                return new amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent(buffAreaEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent>("removeBuff"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent(buffEntity);
            }
        });
        //casts
        Serializer.registerClass(amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent>("effectCastSource"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent(sourceEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent>("effectCastSourceSpell"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent(spellEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent>("effectCastTarget"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent(targetEntity);
            }
        });
        //crowdcontrol
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent>("addBinding"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent>("addBindingImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent>("addKnockup"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent construct(){
                int knockupEntity = createChildEntity(0, "knockupEntity");
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent(knockupEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent>("addKnockupImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent>("addSilence"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent>("addSilenceImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent>("addStun"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent>("addStunImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent(duration);
            }
        });
        //knockup
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent>("knockupDuration"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent>("knockupHeight"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent construct(){
                float height = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent(height);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent>("removeBinding"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent>("removeKnockup"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent>("removeSilence"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent>("removeStun"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent();
            }
        });
        //damage
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent>("addTargetability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent>("addVulnerability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.FlatMagicDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.FlatMagicDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.FlatMagicDamageComponent>("flatMagicDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.FlatMagicDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.FlatMagicDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.FlatPhysicalDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.FlatPhysicalDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.FlatPhysicalDamageComponent>("flatPhysicalDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.FlatPhysicalDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.FlatPhysicalDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.MagicDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.MagicDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.MagicDamageComponent>("magicDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.MagicDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.MagicDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent>("physicalDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent>("removeTargetability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent>("removeVulnerability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.ScalingAbilityPowerMagicDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.ScalingAbilityPowerMagicDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.ScalingAbilityPowerMagicDamageComponent>("scalingAbilityPowerMagicDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.ScalingAbilityPowerMagicDamageComponent construct(){
                float ratio = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.ScalingAbilityPowerMagicDamageComponent(ratio);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.damage.ScalingAttackDamagePhysicalDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.ScalingAttackDamagePhysicalDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.ScalingAttackDamagePhysicalDamageComponent>("scalingAttackDamagePhysicalDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.ScalingAttackDamagePhysicalDamageComponent construct(){
                float ratio = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.damage.ScalingAttackDamagePhysicalDamageComponent(ratio);
            }
        });
        //game
        Serializer.registerClass(amara.game.entitysystem.components.effects.game.PlayCinematicComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.game.PlayCinematicComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.game.PlayCinematicComponent>("playCinematic"){

            @Override
            public amara.game.entitysystem.components.effects.game.PlayCinematicComponent construct(){
                String cinematicClassName = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.effects.game.PlayCinematicComponent(cinematicClassName);
            }
        });
        //general
        Serializer.registerClass(amara.game.entitysystem.components.effects.general.AddComponentsComponent.class);
        Serializer.registerClass(amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent>("addEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent(effectTriggerEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.general.RemoveComponentsComponent.class);
        Serializer.registerClass(amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent>("removeEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.general.RemoveEntityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.RemoveEntityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.RemoveEntityComponent>("removeEntity"){

            @Override
            public amara.game.entitysystem.components.effects.general.RemoveEntityComponent construct(){
                return new amara.game.entitysystem.components.effects.general.RemoveEntityComponent();
            }
        });
        //heals
        Serializer.registerClass(amara.game.entitysystem.components.effects.heals.FlatHealComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.heals.FlatHealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.heals.FlatHealComponent>("flatHeal"){

            @Override
            public amara.game.entitysystem.components.effects.heals.FlatHealComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.heals.FlatHealComponent(value);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.heals.HealComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.heals.HealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.heals.HealComponent>("heal"){

            @Override
            public amara.game.entitysystem.components.effects.heals.HealComponent construct(){
                float value = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.heals.HealComponent(value);
            }
        });
        //movement
        Serializer.registerClass(amara.game.entitysystem.components.effects.movement.MoveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.MoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.MoveComponent>("move"){

            @Override
            public amara.game.entitysystem.components.effects.movement.MoveComponent construct(){
                int movementEntity = createChildEntity(0, "movementEntity");
                return new amara.game.entitysystem.components.effects.movement.MoveComponent(movementEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.movement.StopComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.StopComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.StopComponent>("stop"){

            @Override
            public amara.game.entitysystem.components.effects.movement.StopComponent construct(){
                return new amara.game.entitysystem.components.effects.movement.StopComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.movement.TeleportComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.TeleportComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.TeleportComponent>("teleport"){

            @Override
            public amara.game.entitysystem.components.effects.movement.TeleportComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.movement.TeleportComponent(targetEntity);
            }
        });
        //physics
        Serializer.registerClass(amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent>("activateHitbox"){

            @Override
            public amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent construct(){
                return new amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent>("deactivateHitbox"){

            @Override
            public amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent construct(){
                return new amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.PrepareEffectComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.PrepareEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.PrepareEffectComponent>("prepareEffect"){

            @Override
            public amara.game.entitysystem.components.effects.PrepareEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                return new amara.game.entitysystem.components.effects.PrepareEffectComponent(effectEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.RemainingEffectDelayComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.RemainingEffectDelayComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.RemainingEffectDelayComponent>("remainingEffectDelay"){

            @Override
            public amara.game.entitysystem.components.effects.RemainingEffectDelayComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.effects.RemainingEffectDelayComponent(duration);
            }
        });
        //spawns
        Serializer.registerClass(amara.game.entitysystem.components.effects.spawns.SpawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spawns.SpawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spawns.SpawnComponent>("spawn"){

            @Override
            public amara.game.entitysystem.components.effects.spawns.SpawnComponent construct(){
                int[] spawnInformationEntities = createChildEntities(0, "spawnInformationEntities");
                return new amara.game.entitysystem.components.effects.spawns.SpawnComponent(spawnInformationEntities);
            }
        });
        //spells
        Serializer.registerClass(amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent>("addAutoAttackSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent construct(){
                int[] spellEffectEntities = createChildEntities(0, "spellEffectEntities");
                return new amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent(spellEffectEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent>("removeSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent construct(){
                int[] spellEffectEntities = createChildEntities(0, "spellEffectEntities");
                return new amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent(spellEffectEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent>("replaceSpellWithExistingSpell"){

            @Override
            public amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent construct(){
                int spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("spellIndex")));
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent(spellIndex, spellEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent>("replaceSpellWithNewSpell"){

            @Override
            public amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent construct(){
                int spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("spellIndex")));
                String newSpellTemplate = xmlTemplateManager.parseTemplate(element.getAttributeValue("newSpellTemplate"));
                return new amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent(spellIndex, newSpellTemplate);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent>("triggerSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent(spellEntity);
            }
        });
        //visuals
        Serializer.registerClass(amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent>("playAnimation"){

            @Override
            public amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.effects.visuals.StopAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.visuals.StopAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.visuals.StopAnimationComponent>("stopAnimation"){

            @Override
            public amara.game.entitysystem.components.effects.visuals.StopAnimationComponent construct(){
                return new amara.game.entitysystem.components.effects.visuals.StopAnimationComponent();
            }
        });
        //game
        Serializer.registerClass(amara.game.entitysystem.components.game.CinematicComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.CinematicComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.CinematicComponent>("cinematic"){

            @Override
            public amara.game.entitysystem.components.game.CinematicComponent construct(){
                String cinematicClassName = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.game.CinematicComponent(cinematicClassName);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.game.GameSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.GameSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.GameSpeedComponent>("gameSpeed"){

            @Override
            public amara.game.entitysystem.components.game.GameSpeedComponent construct(){
                float speed = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.game.GameSpeedComponent(speed);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.game.GameTimeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.GameTimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.GameTimeComponent>("gameTime"){

            @Override
            public amara.game.entitysystem.components.game.GameTimeComponent construct(){
                float time = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.game.GameTimeComponent(time);
            }
        });
        //general
        Serializer.registerClass(amara.game.entitysystem.components.general.DescriptionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.general.DescriptionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.general.DescriptionComponent>("description"){

            @Override
            public amara.game.entitysystem.components.general.DescriptionComponent construct(){
                String description = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.general.DescriptionComponent(description);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.general.NameComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.general.NameComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.general.NameComponent>("name"){

            @Override
            public amara.game.entitysystem.components.general.NameComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.general.NameComponent(name);
            }
        });
        //input
        Serializer.registerClass(amara.game.entitysystem.components.input.CastSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.input.CastSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.input.CastSpellComponent>("castSpell"){

            @Override
            public amara.game.entitysystem.components.input.CastSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.input.CastSpellComponent(spellEntity, targetEntity);
            }
        });
        //items
        Serializer.registerClass(amara.game.entitysystem.components.items.InventoryComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.InventoryComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.InventoryComponent>("inventory"){

            @Override
            public amara.game.entitysystem.components.items.InventoryComponent construct(){
                int[] itemEntities = createChildEntities(0, "itemEntities");
                return new amara.game.entitysystem.components.items.InventoryComponent(itemEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.items.IsSellableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.IsSellableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.IsSellableComponent>("isSellable"){

            @Override
            public amara.game.entitysystem.components.items.IsSellableComponent construct(){
                int gold = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.items.IsSellableComponent(gold);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.items.ItemActiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemActiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemActiveComponent>("itemActive"){

            @Override
            public amara.game.entitysystem.components.items.ItemActiveComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.items.ItemActiveComponent(spellEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.items.ItemIDComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemIDComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemIDComponent>("itemID"){

            @Override
            public amara.game.entitysystem.components.items.ItemIDComponent construct(){
                String id = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.items.ItemIDComponent(id);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.items.ItemRecipeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemRecipeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemRecipeComponent>("itemRecipe"){

            @Override
            public amara.game.entitysystem.components.items.ItemRecipeComponent construct(){
                int gold = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("gold")));
                String[] itemIDs = element.getAttributeValue("itemIDs").split(",");
                for(int i=0;i<itemIDs.length;i++){
                    itemIDs[i] = xmlTemplateManager.parseValue(itemIDs[i]);
                }
                return new amara.game.entitysystem.components.items.ItemRecipeComponent(gold, itemIDs);
            }
        });
        //maps
        Serializer.registerClass(amara.game.entitysystem.components.maps.MapObjectiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.MapObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.MapObjectiveComponent>("mapObjective"){

            @Override
            public amara.game.entitysystem.components.maps.MapObjectiveComponent construct(){
                int objectiveEntity = createChildEntity(0, "objectiveEntity");
                return new amara.game.entitysystem.components.maps.MapObjectiveComponent(objectiveEntity);
            }
        });
        //playerdeathrules
        Serializer.registerClass(amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent>("respawnPlayers"){

            @Override
            public amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent construct(){
                return new amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent>("respawnTimer"){

            @Override
            public amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent construct(){
                float initialDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("initialDuration")));
                float deltaDurationPerTime = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("deltaDurationPerTime")));
                return new amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent(initialDuration, deltaDurationPerTime);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.maps.PlayerDeathRulesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.PlayerDeathRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.PlayerDeathRulesComponent>("playerDeathRules"){

            @Override
            public amara.game.entitysystem.components.maps.PlayerDeathRulesComponent construct(){
                int rulesEntity = createChildEntity(0, "rulesEntity");
                return new amara.game.entitysystem.components.maps.PlayerDeathRulesComponent(rulesEntity);
            }
        });
        //movements
        Serializer.registerClass(amara.game.entitysystem.components.movements.DisplacementComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.DisplacementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.DisplacementComponent>("displacement"){

            @Override
            public amara.game.entitysystem.components.movements.DisplacementComponent construct(){
                return new amara.game.entitysystem.components.movements.DisplacementComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.DistanceLimitComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.DistanceLimitComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.DistanceLimitComponent>("distanceLimit"){

            @Override
            public amara.game.entitysystem.components.movements.DistanceLimitComponent construct(){
                float distance = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.movements.DistanceLimitComponent(distance);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovedDistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovedDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovedDistanceComponent>("movedDistance"){

            @Override
            public amara.game.entitysystem.components.movements.MovedDistanceComponent construct(){
                float distance = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.movements.MovedDistanceComponent(distance);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementAnimationComponent>("movementAnimation"){

            @Override
            public amara.game.entitysystem.components.movements.MovementAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.movements.MovementAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementDirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementDirectionComponent>("movementDirection"){

            @Override
            public amara.game.entitysystem.components.movements.MovementDirectionComponent construct(){
                String[] directionCoordinates = element.getText().split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.game.entitysystem.components.movements.MovementDirectionComponent(direction);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementIsCancelableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementIsCancelableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementIsCancelableComponent>("movementIsCancelable"){

            @Override
            public amara.game.entitysystem.components.movements.MovementIsCancelableComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementIsCancelableComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementSpeedComponent>("movementSpeed"){

            @Override
            public amara.game.entitysystem.components.movements.MovementSpeedComponent construct(){
                float speed = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.movements.MovementSpeedComponent(speed);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetComponent>("movementTarget"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.movements.MovementTargetComponent(targetEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementTargetReachedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetReachedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetReachedComponent>("movementTargetReached"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetReachedComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementTargetReachedComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent>("movementTargetSufficientDistance"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent construct(){
                float distance = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent(distance);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.movements.WalkMovementComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.WalkMovementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.WalkMovementComponent>("walkMovement"){

            @Override
            public amara.game.entitysystem.components.movements.WalkMovementComponent construct(){
                return new amara.game.entitysystem.components.movements.WalkMovementComponent();
            }
        });
        //objectives
        Serializer.registerClass(amara.game.entitysystem.components.objectives.FinishedObjectiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.FinishedObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.FinishedObjectiveComponent>("finishedObjective"){

            @Override
            public amara.game.entitysystem.components.objectives.FinishedObjectiveComponent construct(){
                return new amara.game.entitysystem.components.objectives.FinishedObjectiveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.objectives.MissingEntitiesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.MissingEntitiesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.MissingEntitiesComponent>("missingEntities"){

            @Override
            public amara.game.entitysystem.components.objectives.MissingEntitiesComponent construct(){
                int[] entities = createChildEntities(0, "entities");
                return new amara.game.entitysystem.components.objectives.MissingEntitiesComponent(entities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.objectives.OpenObjectiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.OpenObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.OpenObjectiveComponent>("openObjective"){

            @Override
            public amara.game.entitysystem.components.objectives.OpenObjectiveComponent construct(){
                return new amara.game.entitysystem.components.objectives.OpenObjectiveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.objectives.OrObjectivesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.OrObjectivesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.OrObjectivesComponent>("orObjectives"){

            @Override
            public amara.game.entitysystem.components.objectives.OrObjectivesComponent construct(){
                int[] objectiveEntities = createChildEntities(0, "objectiveEntities");
                return new amara.game.entitysystem.components.objectives.OrObjectivesComponent(objectiveEntities);
            }
        });
        //physics
        Serializer.registerClass(amara.game.entitysystem.components.physics.CollisionGroupComponent.class);
        Serializer.registerClass(amara.game.entitysystem.components.physics.DirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.DirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.DirectionComponent>("direction"){

            @Override
            public amara.game.entitysystem.components.physics.DirectionComponent construct(){
                float radian = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.physics.DirectionComponent(radian);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.physics.HitboxActiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.HitboxActiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.HitboxActiveComponent>("hitboxActive"){

            @Override
            public amara.game.entitysystem.components.physics.HitboxActiveComponent construct(){
                return new amara.game.entitysystem.components.physics.HitboxActiveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.physics.HitboxComponent.class);
        Serializer.registerClass(amara.game.entitysystem.components.physics.IntersectionPushComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.IntersectionPushComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.IntersectionPushComponent>("intersectionPush"){

            @Override
            public amara.game.entitysystem.components.physics.IntersectionPushComponent construct(){
                return new amara.game.entitysystem.components.physics.IntersectionPushComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.physics.PositionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.PositionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.PositionComponent>("position"){

            @Override
            public amara.game.entitysystem.components.physics.PositionComponent construct(){
                String[] positionCoordinates = element.getText().split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                return new amara.game.entitysystem.components.physics.PositionComponent(position);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent>("removeOnMapLeave"){

            @Override
            public amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent construct(){
                return new amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.physics.ScaleComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.ScaleComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.ScaleComponent>("scale"){

            @Override
            public amara.game.entitysystem.components.physics.ScaleComponent construct(){
                float scale = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.physics.ScaleComponent(scale);
            }
        });
        //players
        Serializer.registerClass(amara.game.entitysystem.components.players.ClientComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.ClientComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.ClientComponent>("client"){

            @Override
            public amara.game.entitysystem.components.players.ClientComponent construct(){
                int clientID = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.players.ClientComponent(clientID);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.players.PlayerIndexComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.PlayerIndexComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.PlayerIndexComponent>("playerIndex"){

            @Override
            public amara.game.entitysystem.components.players.PlayerIndexComponent construct(){
                int index = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.players.PlayerIndexComponent(index);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.players.RespawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.RespawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.RespawnComponent>("respawn"){

            @Override
            public amara.game.entitysystem.components.players.RespawnComponent construct(){
                return new amara.game.entitysystem.components.players.RespawnComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.players.SelectedUnitComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.SelectedUnitComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.SelectedUnitComponent>("selectedUnit"){

            @Override
            public amara.game.entitysystem.components.players.SelectedUnitComponent construct(){
                int entity = createChildEntity(0, "entity");
                return new amara.game.entitysystem.components.players.SelectedUnitComponent(entity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.players.WaitingToRespawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.WaitingToRespawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.WaitingToRespawnComponent>("waitingToRespawn"){

            @Override
            public amara.game.entitysystem.components.players.WaitingToRespawnComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.players.WaitingToRespawnComponent(remainingDuration);
            }
        });
        //shop
        Serializer.registerClass(amara.game.entitysystem.components.shop.ShopRangeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.shop.ShopRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.shop.ShopRangeComponent>("shopRange"){

            @Override
            public amara.game.entitysystem.components.shop.ShopRangeComponent construct(){
                float range = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.shop.ShopRangeComponent(range);
            }
        });
        //spawns
        Serializer.registerClass(amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent>("relativeSpawnPosition"){

            @Override
            public amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent construct(){
                String[] positionCoordinates = element.getText().split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                return new amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent(position);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent>("spawnAttackMove"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent construct(){
                return new amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent>("spawnMovementAnimation"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent>("spawnMovementSpeed"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent construct(){
                float speed = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent(speed);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent>("spawnMoveToTarget"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent construct(){
                return new amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spawns.SpawnTemplateComponent.class);
        //spells
        Serializer.registerClass(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.ApplyCastedSpellComponent>("applyCastedSpell"){

            @Override
            public amara.game.entitysystem.components.spells.ApplyCastedSpellComponent construct(){
                int casterEntityID = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("casterEntityID")));
                int castedSpellEntityID = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("castedSpellEntityID")));
                return new amara.game.entitysystem.components.spells.ApplyCastedSpellComponent(casterEntityID, castedSpellEntityID);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.BaseCooldownComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.BaseCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.BaseCooldownComponent>("baseCooldown"){

            @Override
            public amara.game.entitysystem.components.spells.BaseCooldownComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spells.BaseCooldownComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastAnimationComponent>("castAnimation"){

            @Override
            public amara.game.entitysystem.components.spells.CastAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.spells.CastAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastCancelableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastCancelableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastCancelableComponent>("castCancelable"){

            @Override
            public amara.game.entitysystem.components.spells.CastCancelableComponent construct(){
                return new amara.game.entitysystem.components.spells.CastCancelableComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastCancelActionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastCancelActionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastCancelActionComponent>("castCancelAction"){

            @Override
            public amara.game.entitysystem.components.spells.CastCancelActionComponent construct(){
                return new amara.game.entitysystem.components.spells.CastCancelActionComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastDurationComponent>("castDuration"){

            @Override
            public amara.game.entitysystem.components.spells.CastDurationComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spells.CastDurationComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastTurnToTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastTurnToTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastTurnToTargetComponent>("castTurnToTarget"){

            @Override
            public amara.game.entitysystem.components.spells.CastTurnToTargetComponent construct(){
                return new amara.game.entitysystem.components.spells.CastTurnToTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.CastTypeComponent.class);
        Serializer.registerClass(amara.game.entitysystem.components.spells.CooldownComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CooldownComponent>("cooldown"){

            @Override
            public amara.game.entitysystem.components.spells.CooldownComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spells.CooldownComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.InstantEffectTriggersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.InstantEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.InstantEffectTriggersComponent>("instantEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.spells.InstantEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.spells.InstantEffectTriggersComponent(effectTriggerEntities);
            }
        });
        //placeholders
        Serializer.registerClass(amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent>("sourceMovementDirection"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent>("targetedMovementDirection"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent>("targetedMovementTarget"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent>("teleportToTargetPosition"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent>("triggerCastedSpellEffects"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.RangeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.RangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.RangeComponent>("range"){

            @Override
            public amara.game.entitysystem.components.spells.RangeComponent construct(){
                float distange = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spells.RangeComponent(distange);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.RemainingCooldownComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.RemainingCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.RemainingCooldownComponent>("remainingCooldown"){

            @Override
            public amara.game.entitysystem.components.spells.RemainingCooldownComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.spells.RemainingCooldownComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.SpellTargetRulesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.SpellTargetRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.SpellTargetRulesComponent>("spellTargetRules"){

            @Override
            public amara.game.entitysystem.components.spells.SpellTargetRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.spells.SpellTargetRulesComponent(targetRulesEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.SpellVisualisationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.SpellVisualisationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.SpellVisualisationComponent>("spellVisualisation"){

            @Override
            public amara.game.entitysystem.components.spells.SpellVisualisationComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.spells.SpellVisualisationComponent(name);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.StopAfterCastingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.StopAfterCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.StopAfterCastingComponent>("stopAfterCasting"){

            @Override
            public amara.game.entitysystem.components.spells.StopAfterCastingComponent construct(){
                return new amara.game.entitysystem.components.spells.StopAfterCastingComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.StopBeforeCastingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.StopBeforeCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.StopBeforeCastingComponent>("stopBeforeCasting"){

            @Override
            public amara.game.entitysystem.components.spells.StopBeforeCastingComponent construct(){
                return new amara.game.entitysystem.components.spells.StopBeforeCastingComponent();
            }
        });
        //triggers
        Serializer.registerClass(amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent>("castedEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.spells.triggers.CastedSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.triggers.CastedSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.triggers.CastedSpellComponent>("castedSpell"){

            @Override
            public amara.game.entitysystem.components.spells.triggers.CastedSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.spells.triggers.CastedSpellComponent(spellEntity);
            }
        });
        //targets
        Serializer.registerClass(amara.game.entitysystem.components.targets.AcceptAlliesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.AcceptAlliesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.AcceptAlliesComponent>("acceptAllies"){

            @Override
            public amara.game.entitysystem.components.targets.AcceptAlliesComponent construct(){
                return new amara.game.entitysystem.components.targets.AcceptAlliesComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.targets.AcceptEnemiesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.AcceptEnemiesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.AcceptEnemiesComponent>("acceptEnemies"){

            @Override
            public amara.game.entitysystem.components.targets.AcceptEnemiesComponent construct(){
                return new amara.game.entitysystem.components.targets.AcceptEnemiesComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.targets.RequireProjectileComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.RequireProjectileComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.RequireProjectileComponent>("requireProjectile"){

            @Override
            public amara.game.entitysystem.components.targets.RequireProjectileComponent construct(){
                return new amara.game.entitysystem.components.targets.RequireProjectileComponent();
            }
        });
        //units
        Serializer.registerClass(amara.game.entitysystem.components.units.AggroTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AggroTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AggroTargetComponent>("aggroTarget"){

            @Override
            public amara.game.entitysystem.components.units.AggroTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.units.AggroTargetComponent(targetEntity);
            }
        });
        //animations
        Serializer.registerClass(amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent>("autoAttackAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.animations.DeathAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.DeathAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.DeathAnimationComponent>("deathAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.DeathAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.DeathAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.animations.IdleAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.IdleAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.IdleAnimationComponent>("idleAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.IdleAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.IdleAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.animations.WalkAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.WalkAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.WalkAnimationComponent>("walkAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.WalkAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.WalkAnimationComponent(animationEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.AttackMoveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AttackMoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AttackMoveComponent>("attackMove"){

            @Override
            public amara.game.entitysystem.components.units.AttackMoveComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.units.AttackMoveComponent(targetEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.AutoAggroComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AutoAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AutoAggroComponent>("autoAggro"){

            @Override
            public amara.game.entitysystem.components.units.AutoAggroComponent construct(){
                float range = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.AutoAggroComponent(range);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.AutoAttackComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AutoAttackComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AutoAttackComponent>("autoAttack"){

            @Override
            public amara.game.entitysystem.components.units.AutoAttackComponent construct(){
                int autoAttackEntity = createChildEntity(0, "autoAttackEntity");
                return new amara.game.entitysystem.components.units.AutoAttackComponent(autoAttackEntity);
            }
        });
        //bounties
        Serializer.registerClass(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.bounties.BountyBuffComponent>("bountyBuff"){

            @Override
            public amara.game.entitysystem.components.units.bounties.BountyBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("duration")));
                return new amara.game.entitysystem.components.units.bounties.BountyBuffComponent(buffEntity, duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.bounties.BountyGoldComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.bounties.BountyGoldComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.bounties.BountyGoldComponent>("bountyGold"){

            @Override
            public amara.game.entitysystem.components.units.bounties.BountyGoldComponent construct(){
                int gold = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.bounties.BountyGoldComponent(gold);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.BountyComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.BountyComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.BountyComponent>("bounty"){

            @Override
            public amara.game.entitysystem.components.units.BountyComponent construct(){
                int bountyEntity = createChildEntity(0, "bountyEntity");
                return new amara.game.entitysystem.components.units.BountyComponent(bountyEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.CampComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CampComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CampComponent>("camp"){

            @Override
            public amara.game.entitysystem.components.units.CampComponent construct(){
                int campEntity = createChildEntity(0, "campEntity");
                String[] positionCoordinates = element.getAttributeValue("position").split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                String[] directionCoordinates = element.getAttributeValue("direction").split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.game.entitysystem.components.units.CampComponent(campEntity, position, direction);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.CampResetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CampResetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CampResetComponent>("campReset"){

            @Override
            public amara.game.entitysystem.components.units.CampResetComponent construct(){
                return new amara.game.entitysystem.components.units.CampResetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent>("castSpellOnCooldownWhileAttacking"){

            @Override
            public amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent construct(){
                String[] spellIndicesParts = element.getText().split(",");
                int[] spellIndices = new int[spellIndicesParts.length];
                for(int i=0;i<spellIndices.length;i++){
                    spellIndices[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                }
                return new amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent(spellIndices);
            }
        });
        //crowdcontrol
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent>("isBinded"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent>("isBindedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent>("isKnockuped"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent construct(){
                int knockupEntity = createChildEntity(0, "knockupEntity");
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("remainingDuration")));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent(knockupEntity, remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent>("isKnockupedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent>("isSilenced"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent>("isSilencedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent>("isStunned"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent>("isStunnedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent>("currentActionEffectCasts"){

            @Override
            public amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent construct(){
                int[] effectCastEntities = createChildEntities(0, "effectCastEntities");
                return new amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent(effectCastEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.DamageHistoryComponent.class);
        //effecttriggers
        //targets
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent>("casterTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent>("customTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent construct(){
                int[] targetEntities = createChildEntities(0, "targetEntities");
                return new amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent(targetEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent>("sourceTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent>("targetTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent>("triggerDelay"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent>("triggeredEffect"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                return new amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent(effectEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent>("triggerOnCancel"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent>("triggerOnce"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent();
            }
        });
        //triggers
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent>("castingFinishedTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent>("collisionTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent>("deathTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent>("instantTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent>("repeatingTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent construct(){
                float intervalDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent(intervalDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent>("repeatingTriggerCounter"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent construct(){
                int counter = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent(counter);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent>("targetReachedTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent>("timeSinceLastRepeatTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent>("triggerSource"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent(sourceEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent>("triggerTemporary"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.GoldComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.GoldComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.GoldComponent>("gold"){

            @Override
            public amara.game.entitysystem.components.units.GoldComponent construct(){
                int gold = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.GoldComponent(gold);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IntersectionRulesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IntersectionRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IntersectionRulesComponent>("intersectionRules"){

            @Override
            public amara.game.entitysystem.components.units.IntersectionRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.units.IntersectionRulesComponent(targetRulesEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsAliveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsAliveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsAliveComponent>("isAlive"){

            @Override
            public amara.game.entitysystem.components.units.IsAliveComponent construct(){
                return new amara.game.entitysystem.components.units.IsAliveComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsCastingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsCastingComponent>("isCasting"){

            @Override
            public amara.game.entitysystem.components.units.IsCastingComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("remainingDuration")));
                boolean isCancelable = Boolean.parseBoolean(xmlTemplateManager.parseValue(element.getAttributeValue("isCancelable")));
                return new amara.game.entitysystem.components.units.IsCastingComponent(remainingDuration, isCancelable);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsProjectileComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsProjectileComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsProjectileComponent>("isProjectile"){

            @Override
            public amara.game.entitysystem.components.units.IsProjectileComponent construct(){
                return new amara.game.entitysystem.components.units.IsProjectileComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsTargetableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsTargetableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsTargetableComponent>("isTargetable"){

            @Override
            public amara.game.entitysystem.components.units.IsTargetableComponent construct(){
                return new amara.game.entitysystem.components.units.IsTargetableComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsVulnerableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsVulnerableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsVulnerableComponent>("isVulnerable"){

            @Override
            public amara.game.entitysystem.components.units.IsVulnerableComponent construct(){
                return new amara.game.entitysystem.components.units.IsVulnerableComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent>("isWalkingToAggroTarget"){

            @Override
            public amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent construct(){
                return new amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.LifetimeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.LifetimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.LifetimeComponent>("lifetime"){

            @Override
            public amara.game.entitysystem.components.units.LifetimeComponent construct(){
                float remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.LifetimeComponent(remainingDuration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.MaximumAggroRangeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.MaximumAggroRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.MaximumAggroRangeComponent>("maximumAggroRange"){

            @Override
            public amara.game.entitysystem.components.units.MaximumAggroRangeComponent construct(){
                float range = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.MaximumAggroRangeComponent(range);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.MovementComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.MovementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.MovementComponent>("movement"){

            @Override
            public amara.game.entitysystem.components.units.MovementComponent construct(){
                int movementEntity = createChildEntity(0, "movementEntity");
                return new amara.game.entitysystem.components.units.MovementComponent(movementEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent>("setNewTargetSpellsOnCooldown"){

            @Override
            public amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent construct(){
                String[] spellIndicesParts = element.getAttributeValue("spellIndices").split(",");
                int[] spellIndices = new int[spellIndicesParts.length];
                for(int i=0;i<spellIndices.length;i++){
                    spellIndices[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("spellIndices")));
                }
                String[] cooldownsParts = element.getAttributeValue("cooldowns").split(",");
                int[] cooldowns = new int[cooldownsParts.length];
                for(int i=0;i<cooldowns.length;i++){
                    cooldowns[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("cooldowns")));
                }
                return new amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent(spellIndices, cooldowns);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.SpellsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SpellsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SpellsComponent>("spells"){

            @Override
            public amara.game.entitysystem.components.units.SpellsComponent construct(){
                int[] spellsEntities = createChildEntities(0, "spellsEntities");
                return new amara.game.entitysystem.components.units.SpellsComponent(spellsEntities);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.TargetsInAggroRangeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.TargetsInAggroRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.TargetsInAggroRangeComponent>("targetsInAggroRange"){

            @Override
            public amara.game.entitysystem.components.units.TargetsInAggroRangeComponent construct(){
                String[] targetsParts = element.getText().split(",");
                int[] targets = new int[targetsParts.length];
                for(int i=0;i<targets.length;i++){
                    targets[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                }
                return new amara.game.entitysystem.components.units.TargetsInAggroRangeComponent(targets);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.TeamComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.TeamComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.TeamComponent>("team"){

            @Override
            public amara.game.entitysystem.components.units.TeamComponent construct(){
                int teamEntity = createChildEntity(0, "teamEntity");
                return new amara.game.entitysystem.components.units.TeamComponent(teamEntity);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.units.WalkStepDistanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.WalkStepDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.WalkStepDistanceComponent>("walkStepDistance"){

            @Override
            public amara.game.entitysystem.components.units.WalkStepDistanceComponent construct(){
                float distance = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.units.WalkStepDistanceComponent(distance);
            }
        });
        //visuals
        Serializer.registerClass(amara.game.entitysystem.components.visuals.AnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.AnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.AnimationComponent>("animation"){

            @Override
            public amara.game.entitysystem.components.visuals.AnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.visuals.AnimationComponent(animationEntity);
            }
        });
        //animations
        Serializer.registerClass(amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent>("freezeAfterPlaying"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent construct(){
                return new amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent();
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.visuals.animations.LoopDurationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.LoopDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.LoopDurationComponent>("loopDuration"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.LoopDurationComponent construct(){
                float duration = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.visuals.animations.LoopDurationComponent(duration);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent>("passedLoopTime"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent construct(){
                float passedTime = Float.parseFloat(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent(passedTime);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent>("remainingLoops"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent construct(){
                int loopsCount = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                return new amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent(loopsCount);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.visuals.ModelComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.ModelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.ModelComponent>("model"){

            @Override
            public amara.game.entitysystem.components.visuals.ModelComponent construct(){
                String modelSkinPath = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.visuals.ModelComponent(modelSkinPath);
            }
        });
        Serializer.registerClass(amara.game.entitysystem.components.visuals.TitleComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.TitleComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.TitleComponent>("title"){

            @Override
            public amara.game.entitysystem.components.visuals.TitleComponent construct(){
                String title = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.visuals.TitleComponent(title);
            }
        });
    }
}