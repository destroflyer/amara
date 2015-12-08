package amara.game.entitysystem.templates;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.synchronizing.BitstreamClassManager;
import amara.game.entitysystem.synchronizing.ComponentSerializer;
import amara.game.entitysystem.synchronizing.FieldSerializer;
import amara.game.entitysystem.synchronizing.fieldserializers.*;

/**GENERATED**/
public class ComponentsRegistrator{

    public static void registerComponents(){
        XMLTemplateManager xmlTemplateManager = XMLTemplateManager.getInstance();
        BitstreamClassManager bitstreamClassManager = BitstreamClassManager.getInstance();
        FieldSerializer componentFieldSerializer_Entity = new FieldSerializer_Integer(32);
        FieldSerializer componentFieldSerializer_Timer = new FieldSerializer_Float(20, 8);
        FieldSerializer componentFieldSerializer_Distance = new FieldSerializer_Float(20, 8);
        FieldSerializer componentFieldSerializer_Attribute = new FieldSerializer_Float(20, 8);
        FieldSerializer componentFieldSerializer_Stacks = new FieldSerializer_Integer(16);
        //attributes
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.AbilityPowerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.AbilityPowerComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AbilityPowerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AbilityPowerComponent>("abilityPower"){

            @Override
            public amara.game.entitysystem.components.attributes.AbilityPowerComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.AbilityPowerComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.ArmorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.ArmorComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.ArmorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.ArmorComponent>("armor"){

            @Override
            public amara.game.entitysystem.components.attributes.ArmorComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.ArmorComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.AttackDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.AttackDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AttackDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AttackDamageComponent>("attackDamage"){

            @Override
            public amara.game.entitysystem.components.attributes.AttackDamageComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.AttackDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.AttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.AttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.AttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.AttackSpeedComponent>("attackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.AttackSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.AttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent>("bonusFlatAbilityPower"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatAbilityPowerComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatArmorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatArmorComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatArmorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatArmorComponent>("bonusFlatArmor"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatArmorComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatArmorComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent>("bonusFlatAttackDamage"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatAttackDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent>("bonusFlatAttackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatAttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent>("bonusFlatHealthRegeneration"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent>("bonusFlatMagicResistance"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatMagicResistanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent>("bonusFlatMaximumHealth"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatMaximumHealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent>("bonusFlatWalkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusFlatWalkSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent>("bonusPercentageAttackSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent>("bonusPercentageCooldownSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent>("bonusPercentageCriticalChance"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent>("bonusPercentageLifesteal"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusPercentageLifestealComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent>("bonusPercentageWalkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.CooldownSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.CooldownSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.CooldownSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.CooldownSpeedComponent>("cooldownSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.CooldownSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.CooldownSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.CriticalChanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.CriticalChanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.CriticalChanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.CriticalChanceComponent>("criticalChance"){

            @Override
            public amara.game.entitysystem.components.attributes.CriticalChanceComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.CriticalChanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.HealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.HealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.HealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.HealthComponent>("health"){

            @Override
            public amara.game.entitysystem.components.attributes.HealthComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.HealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.HealthRegenerationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.HealthRegenerationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.HealthRegenerationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.HealthRegenerationComponent>("healthRegeneration"){

            @Override
            public amara.game.entitysystem.components.attributes.HealthRegenerationComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.HealthRegenerationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.LifestealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.LifestealComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.LifestealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.LifestealComponent>("lifesteal"){

            @Override
            public amara.game.entitysystem.components.attributes.LifestealComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.LifestealComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.MagicResistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.MagicResistanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.MagicResistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.MagicResistanceComponent>("magicResistance"){

            @Override
            public amara.game.entitysystem.components.attributes.MagicResistanceComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.MagicResistanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.MaximumHealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.MaximumHealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.MaximumHealthComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.MaximumHealthComponent>("maximumHealth"){

            @Override
            public amara.game.entitysystem.components.attributes.MaximumHealthComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.MaximumHealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent>("requestUpdateAttributes"){

            @Override
            public amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent construct(){
                return new amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.attributes.WalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.attributes.WalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.attributes.WalkSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.attributes.WalkSpeedComponent>("walkSpeed"){

            @Override
            public amara.game.entitysystem.components.attributes.WalkSpeedComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.attributes.WalkSpeedComponent(value);
            }
        });
        //audio
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.AudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioComponent>("audio"){

            @Override
            public amara.game.entitysystem.components.audio.AudioComponent construct(){
                String audioPath = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.audio.AudioComponent(audioPath);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.AudioLoopComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioLoopComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioLoopComponent>("audioLoop"){

            @Override
            public amara.game.entitysystem.components.audio.AudioLoopComponent construct(){
                return new amara.game.entitysystem.components.audio.AudioLoopComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.AudioSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.audio.AudioSourceComponent.class.getDeclaredField("entity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioSourceComponent>("audioSource"){

            @Override
            public amara.game.entitysystem.components.audio.AudioSourceComponent construct(){
                int entity = createChildEntity(0, "entity");
                return new amara.game.entitysystem.components.audio.AudioSourceComponent(entity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class.getDeclaredField("audioEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class.getDeclaredField("delay"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioSuccessorComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioSuccessorComponent>("audioSuccessor"){

            @Override
            public amara.game.entitysystem.components.audio.AudioSuccessorComponent construct(){
                int audioEntity = createChildEntity(0, "audioEntity");
                float delay = 0;
                String delayText = element.getAttributeValue("delay");
                if((delayText != null) && (delayText.length() > 0)){
                    delay = Float.parseFloat(xmlTemplateManager.parseValue(delayText));
                }
                return new amara.game.entitysystem.components.audio.AudioSuccessorComponent(audioEntity, delay);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.AudioVolumeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.AudioVolumeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.AudioVolumeComponent>("audioVolume"){

            @Override
            public amara.game.entitysystem.components.audio.AudioVolumeComponent construct(){
                float volume = 0;
                String volumeText = element.getText();
                if((volumeText != null) && (volumeText.length() > 0)){
                    volume = Float.parseFloat(xmlTemplateManager.parseValue(volumeText));
                }
                return new amara.game.entitysystem.components.audio.AudioVolumeComponent(volume);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.StartPlayingAudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.StartPlayingAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.StartPlayingAudioComponent>("startPlayingAudio"){

            @Override
            public amara.game.entitysystem.components.audio.StartPlayingAudioComponent construct(){
                return new amara.game.entitysystem.components.audio.StartPlayingAudioComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.audio.StopPlayingAudioComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.audio.StopPlayingAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.audio.StopPlayingAudioComponent>("stopPlayingAudio"){

            @Override
            public amara.game.entitysystem.components.audio.StopPlayingAudioComponent construct(){
                return new amara.game.entitysystem.components.audio.StopPlayingAudioComponent();
            }
        });
        //buffs
        //areas
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.areas.AreaBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.areas.AreaBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaBuffComponent>("areaBuff"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaBuffComponent(buffEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent>("areaBuffTargetRules"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.areas.AreaOriginComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.areas.AreaOriginComponent.class.getDeclaredField("originEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaOriginComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaOriginComponent>("areaOrigin"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaOriginComponent construct(){
                int originEntity = createChildEntity(0, "originEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaOriginComponent(originEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.areas.AreaSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.areas.AreaSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.areas.AreaSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.areas.AreaSourceComponent>("areaSource"){

            @Override
            public amara.game.entitysystem.components.buffs.areas.AreaSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.buffs.areas.AreaSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.BuffStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.BuffStacksComponent.class.getDeclaredField("stacksEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.BuffStacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.BuffStacksComponent>("buffStacks"){

            @Override
            public amara.game.entitysystem.components.buffs.BuffStacksComponent construct(){
                int stacksEntity = createChildEntity(0, "stacksEntity");
                return new amara.game.entitysystem.components.buffs.BuffStacksComponent(stacksEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.ContinuousAttributesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.ContinuousAttributesComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.ContinuousAttributesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.ContinuousAttributesComponent>("continuousAttributes"){

            @Override
            public amara.game.entitysystem.components.buffs.ContinuousAttributesComponent construct(){
                int bonusAttributesEntity = createChildEntity(0, "bonusAttributesEntity");
                return new amara.game.entitysystem.components.buffs.ContinuousAttributesComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent>("continuousAttributesPerStack"){

            @Override
            public amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent construct(){
                int bonusAttributesEntity = createChildEntity(0, "bonusAttributesEntity");
                return new amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.KeepOnDeathComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.KeepOnDeathComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.KeepOnDeathComponent>("keepOnDeath"){

            @Override
            public amara.game.entitysystem.components.buffs.KeepOnDeathComponent construct(){
                return new amara.game.entitysystem.components.buffs.KeepOnDeathComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent>("onBuffRemoveEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class.getDeclaredField("interval"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.RepeatingEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.RepeatingEffectComponent>("repeatingEffect"){

            @Override
            public amara.game.entitysystem.components.buffs.RepeatingEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                float interval = 0;
                String intervalText = element.getAttributeValue("interval");
                if((intervalText != null) && (intervalText.length() > 0)){
                    interval = Float.parseFloat(xmlTemplateManager.parseValue(intervalText));
                }
                return new amara.game.entitysystem.components.buffs.RepeatingEffectComponent(effectEntity, interval);
            }
        });
        //stacks
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent>("maximumStacks"){

            @Override
            public amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent construct(){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(stacksText));
                }
                return new amara.game.entitysystem.components.buffs.stacks.MaximumStacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.stacks.StacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.stacks.StacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.stacks.StacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.stacks.StacksComponent>("stacks"){

            @Override
            public amara.game.entitysystem.components.buffs.stacks.StacksComponent construct(){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(stacksText));
                }
                return new amara.game.entitysystem.components.buffs.stacks.StacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent>("stacksRefreshment"){

            @Override
            public amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.buffs.stacks.StacksRefreshmentComponent(duration);
            }
        });
        //status
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.ActiveBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.ActiveBuffComponent>("activeBuff"){

            @Override
            public amara.game.entitysystem.components.buffs.status.ActiveBuffComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.buffs.status.ActiveBuffComponent(targetEntity, buffEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent>("buffVisualisation"){

            @Override
            public amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.buffs.status.BuffVisualisationComponent(name);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent>("remainingBuffDuration"){

            @Override
            public amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent>("removeFromTarget"){

            @Override
            public amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent construct(){
                return new amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent>("timeSinceLastRepeatingEffect"){

            @Override
            public amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent(duration);
            }
        });
        //camps
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampHealthResetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampHealthResetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampHealthResetComponent>("campHealthReset"){

            @Override
            public amara.game.entitysystem.components.camps.CampHealthResetComponent construct(){
                return new amara.game.entitysystem.components.camps.CampHealthResetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent>("campMaximumAggroDistance"){

            @Override
            public amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent construct(){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(distanceText));
                }
                return new amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent>("campRemainingRespawnDuration"){

            @Override
            public amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampRespawnDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.camps.CampRespawnDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampRespawnDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampRespawnDurationComponent>("campRespawnDuration"){

            @Override
            public amara.game.entitysystem.components.camps.CampRespawnDurationComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.camps.CampRespawnDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampSpawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampSpawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampSpawnComponent>("campSpawn"){

            @Override
            public amara.game.entitysystem.components.camps.CampSpawnComponent construct(){
                return new amara.game.entitysystem.components.camps.CampSpawnComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampSpawnInformationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.camps.CampSpawnInformationComponent.class.getDeclaredField("spawnInformationEntites"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampSpawnInformationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampSpawnInformationComponent>("campSpawnInformation"){

            @Override
            public amara.game.entitysystem.components.camps.CampSpawnInformationComponent construct(){
                int[] spawnInformationEntities = createChildEntities(0, "spawnInformationEntities");
                return new amara.game.entitysystem.components.camps.CampSpawnInformationComponent(spawnInformationEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.camps.CampUnionAggroComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.camps.CampUnionAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.camps.CampUnionAggroComponent>("campUnionAggro"){

            @Override
            public amara.game.entitysystem.components.camps.CampUnionAggroComponent construct(){
                return new amara.game.entitysystem.components.camps.CampUnionAggroComponent();
            }
        });
        //effects
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.AffectedTargetsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.AffectedTargetsComponent.class.getDeclaredField("targetEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.AffectedTargetsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.AffectedTargetsComponent>("affectedTargets"){

            @Override
            public amara.game.entitysystem.components.effects.AffectedTargetsComponent construct(){
                int[] targetEntities = createChildEntities(0, "targetEntities");
                return new amara.game.entitysystem.components.effects.AffectedTargetsComponent(targetEntities);
            }
        });
        //aggro
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent>("drawTeamAggro"){

            @Override
            public amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent construct(){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(rangeText));
                }
                return new amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent(range);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.ApplyEffectImpactComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.ApplyEffectImpactComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.ApplyEffectImpactComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.ApplyEffectImpactComponent>("applyEffectImpact"){

            @Override
            public amara.game.entitysystem.components.effects.ApplyEffectImpactComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.ApplyEffectImpactComponent(targetEntity);
            }
        });
        //audio
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.audio.PlayAudioComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.audio.PlayAudioComponent.class.getDeclaredField("audioEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.audio.PlayAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.audio.PlayAudioComponent>("playAudio"){

            @Override
            public amara.game.entitysystem.components.effects.audio.PlayAudioComponent construct(){
                int[] audioEntities = createChildEntities(0, "audioEntities");
                return new amara.game.entitysystem.components.effects.audio.PlayAudioComponent(audioEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.audio.StopAudioComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.audio.StopAudioComponent.class.getDeclaredField("audioEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.audio.StopAudioComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.audio.StopAudioComponent>("stopAudio"){

            @Override
            public amara.game.entitysystem.components.effects.audio.StopAudioComponent construct(){
                int[] audioEntities = createChildEntities(0, "audioEntities");
                return new amara.game.entitysystem.components.effects.audio.StopAudioComponent(audioEntities);
            }
        });
        //buffs
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.AddBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.AddBuffComponent>("addBuff"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.AddBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.buffs.AddBuffComponent(buffEntity, duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent>("addNewBuff"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent construct(){
                String templateExpression = xmlTemplateManager.parseValue(element.getAttributeValue("templateExpression"));
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.buffs.AddNewBuffComponent(templateExpression, duration);
            }
        });
        //areas
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class.getDeclaredField("buffAreaEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent>("addBuffArea"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent construct(){
                int buffAreaEntity = createChildEntity(0, "buffAreaEntity");
                return new amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent(buffAreaEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class.getDeclaredField("buffAreaEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent>("removeBuffArea"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent construct(){
                int buffAreaEntity = createChildEntity(0, "buffAreaEntity");
                return new amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent(buffAreaEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent>("removeBuff"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent(buffEntity);
            }
        });
        //stacks
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent>("addStacks"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent construct(){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(stacksText));
                }
                return new amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent>("clearStacks"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent construct(){
                return new amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent>("removeStacks"){

            @Override
            public amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent construct(){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(stacksText));
                }
                return new amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent(stacks);
            }
        });
        //casts
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent>("effectCastSource"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent>("effectCastSourceSpell"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent(spellEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent>("effectCastTarget"){

            @Override
            public amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent(targetEntity);
            }
        });
        //crowdcontrol
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent>("addBinding"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent>("addBindingImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class.getDeclaredField("knockupEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent>("addKnockup"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent construct(){
                int knockupEntity = createChildEntity(0, "knockupEntity");
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent(knockupEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent>("addKnockupImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent>("addSilence"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent>("addSilenceImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent>("addStun"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent>("addStunImmune"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent(duration);
            }
        });
        //knockup
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent>("knockupDuration"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class.getDeclaredField("height"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent>("knockupHeight"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent construct(){
                float height = 0;
                String heightText = element.getText();
                if((heightText != null) && (heightText.length() > 0)){
                    height = Float.parseFloat(xmlTemplateManager.parseValue(heightText));
                }
                return new amara.game.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent(height);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent>("removeBinding"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent>("removeKnockup"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent>("removeSilence"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent>("removeStun"){

            @Override
            public amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent construct(){
                return new amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.CustomEffectValuesComponent.class);
        //damage
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent>("addTargetability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent>("addVulnerability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.CanCritComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.CanCritComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.CanCritComponent>("canCrit"){

            @Override
            public amara.game.entitysystem.components.effects.damage.CanCritComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.CanCritComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.MagicDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.MagicDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.MagicDamageComponent>("magicDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.MagicDamageComponent construct(){
                String expression = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.effects.damage.MagicDamageComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent>("physicalDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent construct(){
                String expression = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent>("removeTargetability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent>("removeVulnerability"){

            @Override
            public amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent construct(){
                return new amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent>("resultingMagicDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent>("resultingPhysicalDamage"){

            @Override
            public amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent(value);
            }
        });
        //game
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.game.PlayCinematicComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.game.PlayCinematicComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.game.PlayCinematicComponent>("playCinematic"){

            @Override
            public amara.game.entitysystem.components.effects.game.PlayCinematicComponent construct(){
                String cinematicClassName = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.effects.game.PlayCinematicComponent(cinematicClassName);
            }
        });
        //general
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.general.AddComponentsComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent>("addEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.general.RemoveComponentsComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent>("removeEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.general.RemoveEntityComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.general.RemoveEntityComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.general.RemoveEntityComponent>("removeEntity"){

            @Override
            public amara.game.entitysystem.components.effects.general.RemoveEntityComponent construct(){
                return new amara.game.entitysystem.components.effects.general.RemoveEntityComponent();
            }
        });
        //heals
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.heals.HealComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.heals.HealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.heals.HealComponent>("heal"){

            @Override
            public amara.game.entitysystem.components.effects.heals.HealComponent construct(){
                String expression = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.effects.heals.HealComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.heals.ResultingHealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.heals.ResultingHealComponent.class.getDeclaredField("value"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.heals.ResultingHealComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.heals.ResultingHealComponent>("resultingHeal"){

            @Override
            public amara.game.entitysystem.components.effects.heals.ResultingHealComponent construct(){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(valueText));
                }
                return new amara.game.entitysystem.components.effects.heals.ResultingHealComponent(value);
            }
        });
        //movement
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.movement.MoveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.movement.MoveComponent.class.getDeclaredField("movementEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.MoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.MoveComponent>("move"){

            @Override
            public amara.game.entitysystem.components.effects.movement.MoveComponent construct(){
                int movementEntity = createChildEntity(0, "movementEntity");
                return new amara.game.entitysystem.components.effects.movement.MoveComponent(movementEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.movement.StopComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.StopComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.StopComponent>("stop"){

            @Override
            public amara.game.entitysystem.components.effects.movement.StopComponent construct(){
                return new amara.game.entitysystem.components.effects.movement.StopComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.movement.TeleportComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.movement.TeleportComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.movement.TeleportComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.movement.TeleportComponent>("teleport"){

            @Override
            public amara.game.entitysystem.components.effects.movement.TeleportComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.effects.movement.TeleportComponent(targetEntity);
            }
        });
        //physics
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent>("activateHitbox"){

            @Override
            public amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent construct(){
                return new amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent>("deactivateHitbox"){

            @Override
            public amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent construct(){
                return new amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.PrepareEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.PrepareEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.PrepareEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.PrepareEffectComponent>("prepareEffect"){

            @Override
            public amara.game.entitysystem.components.effects.PrepareEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                return new amara.game.entitysystem.components.effects.PrepareEffectComponent(effectEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.RemainingEffectDelayComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.RemainingEffectDelayComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.RemainingEffectDelayComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.RemainingEffectDelayComponent>("remainingEffectDelay"){

            @Override
            public amara.game.entitysystem.components.effects.RemainingEffectDelayComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.effects.RemainingEffectDelayComponent(duration);
            }
        });
        //spawns
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spawns.SpawnComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.spawns.SpawnComponent.class.getDeclaredField("spawnInformationEntites"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spawns.SpawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spawns.SpawnComponent>("spawn"){

            @Override
            public amara.game.entitysystem.components.effects.spawns.SpawnComponent construct(){
                int[] spawnInformationEntities = createChildEntities(0, "spawnInformationEntities");
                return new amara.game.entitysystem.components.effects.spawns.SpawnComponent(spawnInformationEntities);
            }
        });
        //spells
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class.getDeclaredField("spellEffectEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent>("addAutoAttackSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent construct(){
                int[] spellEffectEntities = createChildEntities(0, "spellEffectEntities");
                return new amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent(spellEffectEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class.getDeclaredField("spellEffectEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent>("removeSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent construct(){
                int[] spellEffectEntities = createChildEntities(0, "spellEffectEntities");
                return new amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent(spellEffectEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent>("replaceSpellWithExistingSpell"){

            @Override
            public amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent construct(){
                int spellIndex = 0;
                String spellIndexText = element.getAttributeValue("spellIndex");
                if((spellIndexText != null) && (spellIndexText.length() > 0)){
                    spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(spellIndexText));
                }
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent(spellIndex, spellEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent>("replaceSpellWithNewSpell"){

            @Override
            public amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent construct(){
                int spellIndex = 0;
                String spellIndexText = element.getAttributeValue("spellIndex");
                if((spellIndexText != null) && (spellIndexText.length() > 0)){
                    spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(spellIndexText));
                }
                String newSpellTemplate = xmlTemplateManager.parseTemplate(element.getAttributeValue("newSpellTemplate"));
                return new amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent(spellIndex, newSpellTemplate);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent>("triggerSpellEffects"){

            @Override
            public amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent(spellEntity);
            }
        });
        //visuals
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent>("playAnimation"){

            @Override
            public amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.effects.visuals.StopAnimationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.effects.visuals.StopAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.effects.visuals.StopAnimationComponent>("stopAnimation"){

            @Override
            public amara.game.entitysystem.components.effects.visuals.StopAnimationComponent construct(){
                return new amara.game.entitysystem.components.effects.visuals.StopAnimationComponent();
            }
        });
        //game
        bitstreamClassManager.register(amara.game.entitysystem.components.game.CinematicComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.CinematicComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.CinematicComponent>("cinematic"){

            @Override
            public amara.game.entitysystem.components.game.CinematicComponent construct(){
                String cinematicClassName = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.game.CinematicComponent(cinematicClassName);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.game.GameSpeedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.GameSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.GameSpeedComponent>("gameSpeed"){

            @Override
            public amara.game.entitysystem.components.game.GameSpeedComponent construct(){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(speedText));
                }
                return new amara.game.entitysystem.components.game.GameSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.game.GameTimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.game.GameTimeComponent.class.getDeclaredField("time"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.game.GameTimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.game.GameTimeComponent>("gameTime"){

            @Override
            public amara.game.entitysystem.components.game.GameTimeComponent construct(){
                float time = 0;
                String timeText = element.getText();
                if((timeText != null) && (timeText.length() > 0)){
                    time = Float.parseFloat(xmlTemplateManager.parseValue(timeText));
                }
                return new amara.game.entitysystem.components.game.GameTimeComponent(time);
            }
        });
        //general
        bitstreamClassManager.register(amara.game.entitysystem.components.general.DescriptionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.general.DescriptionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.general.DescriptionComponent>("description"){

            @Override
            public amara.game.entitysystem.components.general.DescriptionComponent construct(){
                String description = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.general.DescriptionComponent(description);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.general.NameComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.general.NameComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.general.NameComponent>("name"){

            @Override
            public amara.game.entitysystem.components.general.NameComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.general.NameComponent(name);
            }
        });
        //input
        bitstreamClassManager.register(amara.game.entitysystem.components.input.CastSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.input.CastSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.input.CastSpellComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.input.CastSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.input.CastSpellComponent>("castSpell"){

            @Override
            public amara.game.entitysystem.components.input.CastSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.input.CastSpellComponent(spellEntity, targetEntity);
            }
        });
        //items
        bitstreamClassManager.register(amara.game.entitysystem.components.items.InventoryComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.items.InventoryComponent.class.getDeclaredField("itemEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.InventoryComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.InventoryComponent>("inventory"){

            @Override
            public amara.game.entitysystem.components.items.InventoryComponent construct(){
                int[] itemEntities = createChildEntities(0, "itemEntities");
                return new amara.game.entitysystem.components.items.InventoryComponent(itemEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.items.IsSellableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.IsSellableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.IsSellableComponent>("isSellable"){

            @Override
            public amara.game.entitysystem.components.items.IsSellableComponent construct(){
                int gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Integer.parseInt(xmlTemplateManager.parseValue(goldText));
                }
                return new amara.game.entitysystem.components.items.IsSellableComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.items.ItemActiveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.items.ItemActiveComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemActiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemActiveComponent>("itemActive"){

            @Override
            public amara.game.entitysystem.components.items.ItemActiveComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.items.ItemActiveComponent(spellEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.items.ItemIDComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemIDComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemIDComponent>("itemID"){

            @Override
            public amara.game.entitysystem.components.items.ItemIDComponent construct(){
                String id = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.items.ItemIDComponent(id);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.items.ItemPassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.items.ItemPassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemPassivesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemPassivesComponent>("itemPassives"){

            @Override
            public amara.game.entitysystem.components.items.ItemPassivesComponent construct(){
                int[] passiveEntities = createChildEntities(0, "passiveEntities");
                return new amara.game.entitysystem.components.items.ItemPassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.items.ItemRecipeComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.items.ItemRecipeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.items.ItemRecipeComponent>("itemRecipe"){

            @Override
            public amara.game.entitysystem.components.items.ItemRecipeComponent construct(){
                int gold = 0;
                String goldText = element.getAttributeValue("gold");
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Integer.parseInt(xmlTemplateManager.parseValue(goldText));
                }
                String[] itemIDs = new String[0];
                String itemIDsText = element.getAttributeValue("itemIDs");
                if(itemIDsText != null){
                    itemIDs = itemIDsText.split(",");
                    for(int i=0;i<itemIDs.length;i++){
                        itemIDs[i] = xmlTemplateManager.parseValue(itemIDs[i]);
                    }
                }
                return new amara.game.entitysystem.components.items.ItemRecipeComponent(gold, itemIDs);
            }
        });
        //maps
        bitstreamClassManager.register(amara.game.entitysystem.components.maps.MapObjectiveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.maps.MapObjectiveComponent.class.getDeclaredField("objectiveEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.MapObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.MapObjectiveComponent>("mapObjective"){

            @Override
            public amara.game.entitysystem.components.maps.MapObjectiveComponent construct(){
                int objectiveEntity = createChildEntity(0, "objectiveEntity");
                return new amara.game.entitysystem.components.maps.MapObjectiveComponent(objectiveEntity);
            }
        });
        //playerdeathrules
        bitstreamClassManager.register(amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent>("respawnPlayers"){

            @Override
            public amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent construct(){
                return new amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class.getDeclaredField("initialDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class.getDeclaredField("deltaDurationPerTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent>("respawnTimer"){

            @Override
            public amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent construct(){
                float initialDuration = 0;
                String initialDurationText = element.getAttributeValue("initialDuration");
                if((initialDurationText != null) && (initialDurationText.length() > 0)){
                    initialDuration = Float.parseFloat(xmlTemplateManager.parseValue(initialDurationText));
                }
                float deltaDurationPerTime = 0;
                String deltaDurationPerTimeText = element.getAttributeValue("deltaDurationPerTime");
                if((deltaDurationPerTimeText != null) && (deltaDurationPerTimeText.length() > 0)){
                    deltaDurationPerTime = Float.parseFloat(xmlTemplateManager.parseValue(deltaDurationPerTimeText));
                }
                return new amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent(initialDuration, deltaDurationPerTime);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.maps.PlayerDeathRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.maps.PlayerDeathRulesComponent.class.getDeclaredField("rulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.maps.PlayerDeathRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.maps.PlayerDeathRulesComponent>("playerDeathRules"){

            @Override
            public amara.game.entitysystem.components.maps.PlayerDeathRulesComponent construct(){
                int rulesEntity = createChildEntity(0, "rulesEntity");
                return new amara.game.entitysystem.components.maps.PlayerDeathRulesComponent(rulesEntity);
            }
        });
        //movements
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.DisplacementComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.DisplacementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.DisplacementComponent>("displacement"){

            @Override
            public amara.game.entitysystem.components.movements.DisplacementComponent construct(){
                return new amara.game.entitysystem.components.movements.DisplacementComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.DistanceLimitComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.DistanceLimitComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.DistanceLimitComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.DistanceLimitComponent>("distanceLimit"){

            @Override
            public amara.game.entitysystem.components.movements.DistanceLimitComponent construct(){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(distanceText));
                }
                return new amara.game.entitysystem.components.movements.DistanceLimitComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovedDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.MovedDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovedDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovedDistanceComponent>("movedDistance"){

            @Override
            public amara.game.entitysystem.components.movements.MovedDistanceComponent construct(){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(distanceText));
                }
                return new amara.game.entitysystem.components.movements.MovedDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.MovementAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementAnimationComponent>("movementAnimation"){

            @Override
            public amara.game.entitysystem.components.movements.MovementAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.movements.MovementAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementDirectionComponent.class);
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
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementIsCancelableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementIsCancelableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementIsCancelableComponent>("movementIsCancelable"){

            @Override
            public amara.game.entitysystem.components.movements.MovementIsCancelableComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementIsCancelableComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent>("movementLocalAvoidance"){

            @Override
            public amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementPathfindingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementPathfindingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementPathfindingComponent>("movementPathfinding"){

            @Override
            public amara.game.entitysystem.components.movements.MovementPathfindingComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementPathfindingComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.MovementSpeedComponent.class.getDeclaredField("speed"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementSpeedComponent>("movementSpeed"){

            @Override
            public amara.game.entitysystem.components.movements.MovementSpeedComponent construct(){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(speedText));
                }
                return new amara.game.entitysystem.components.movements.MovementSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.MovementTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetComponent>("movementTarget"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.movements.MovementTargetComponent(targetEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementTargetReachedComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetReachedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetReachedComponent>("movementTargetReached"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetReachedComponent construct(){
                return new amara.game.entitysystem.components.movements.MovementTargetReachedComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent>("movementTargetSufficientDistance"){

            @Override
            public amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent construct(){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(distanceText));
                }
                return new amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.movements.WalkMovementComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.movements.WalkMovementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.movements.WalkMovementComponent>("walkMovement"){

            @Override
            public amara.game.entitysystem.components.movements.WalkMovementComponent construct(){
                return new amara.game.entitysystem.components.movements.WalkMovementComponent();
            }
        });
        //objectives
        bitstreamClassManager.register(amara.game.entitysystem.components.objectives.FinishedObjectiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.FinishedObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.FinishedObjectiveComponent>("finishedObjective"){

            @Override
            public amara.game.entitysystem.components.objectives.FinishedObjectiveComponent construct(){
                return new amara.game.entitysystem.components.objectives.FinishedObjectiveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.objectives.MissingEntitiesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.objectives.MissingEntitiesComponent.class.getDeclaredField("entities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.MissingEntitiesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.MissingEntitiesComponent>("missingEntities"){

            @Override
            public amara.game.entitysystem.components.objectives.MissingEntitiesComponent construct(){
                int[] entities = createChildEntities(0, "entities");
                return new amara.game.entitysystem.components.objectives.MissingEntitiesComponent(entities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.objectives.OpenObjectiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.OpenObjectiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.OpenObjectiveComponent>("openObjective"){

            @Override
            public amara.game.entitysystem.components.objectives.OpenObjectiveComponent construct(){
                return new amara.game.entitysystem.components.objectives.OpenObjectiveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.objectives.OrObjectivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.objectives.OrObjectivesComponent.class.getDeclaredField("objectiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.objectives.OrObjectivesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.objectives.OrObjectivesComponent>("orObjectives"){

            @Override
            public amara.game.entitysystem.components.objectives.OrObjectivesComponent construct(){
                int[] objectiveEntities = createChildEntities(0, "objectiveEntities");
                return new amara.game.entitysystem.components.objectives.OrObjectivesComponent(objectiveEntities);
            }
        });
        //physics
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.CollisionGroupComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.DirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.DirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.DirectionComponent>("direction"){

            @Override
            public amara.game.entitysystem.components.physics.DirectionComponent construct(){
                float radian = 0;
                String radianText = element.getText();
                if((radianText != null) && (radianText.length() > 0)){
                    radian = Float.parseFloat(xmlTemplateManager.parseValue(radianText));
                }
                return new amara.game.entitysystem.components.physics.DirectionComponent(radian);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.HitboxActiveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.HitboxActiveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.HitboxActiveComponent>("hitboxActive"){

            @Override
            public amara.game.entitysystem.components.physics.HitboxActiveComponent construct(){
                return new amara.game.entitysystem.components.physics.HitboxActiveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.HitboxComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.IntersectionPushComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.IntersectionPushComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.IntersectionPushComponent>("intersectionPush"){

            @Override
            public amara.game.entitysystem.components.physics.IntersectionPushComponent construct(){
                return new amara.game.entitysystem.components.physics.IntersectionPushComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.PositionComponent.class);
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
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent>("removeOnMapLeave"){

            @Override
            public amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent construct(){
                return new amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.physics.ScaleComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.physics.ScaleComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.physics.ScaleComponent>("scale"){

            @Override
            public amara.game.entitysystem.components.physics.ScaleComponent construct(){
                float scale = 0;
                String scaleText = element.getText();
                if((scaleText != null) && (scaleText.length() > 0)){
                    scale = Float.parseFloat(xmlTemplateManager.parseValue(scaleText));
                }
                return new amara.game.entitysystem.components.physics.ScaleComponent(scale);
            }
        });
        //players
        bitstreamClassManager.register(amara.game.entitysystem.components.players.ClientComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.ClientComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.ClientComponent>("client"){

            @Override
            public amara.game.entitysystem.components.players.ClientComponent construct(){
                int clientID = 0;
                String clientIDText = element.getText();
                if((clientIDText != null) && (clientIDText.length() > 0)){
                    clientID = Integer.parseInt(xmlTemplateManager.parseValue(clientIDText));
                }
                return new amara.game.entitysystem.components.players.ClientComponent(clientID);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.players.PlayerIndexComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.PlayerIndexComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.PlayerIndexComponent>("playerIndex"){

            @Override
            public amara.game.entitysystem.components.players.PlayerIndexComponent construct(){
                int index = 0;
                String indexText = element.getText();
                if((indexText != null) && (indexText.length() > 0)){
                    index = Integer.parseInt(xmlTemplateManager.parseValue(indexText));
                }
                return new amara.game.entitysystem.components.players.PlayerIndexComponent(index);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.players.RespawnComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.RespawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.RespawnComponent>("respawn"){

            @Override
            public amara.game.entitysystem.components.players.RespawnComponent construct(){
                return new amara.game.entitysystem.components.players.RespawnComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.players.SelectedUnitComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.players.SelectedUnitComponent.class.getDeclaredField("entity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.SelectedUnitComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.SelectedUnitComponent>("selectedUnit"){

            @Override
            public amara.game.entitysystem.components.players.SelectedUnitComponent construct(){
                int entity = createChildEntity(0, "entity");
                return new amara.game.entitysystem.components.players.SelectedUnitComponent(entity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.players.WaitingToRespawnComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.players.WaitingToRespawnComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.players.WaitingToRespawnComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.players.WaitingToRespawnComponent>("waitingToRespawn"){

            @Override
            public amara.game.entitysystem.components.players.WaitingToRespawnComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.players.WaitingToRespawnComponent(remainingDuration);
            }
        });
        //shop
        bitstreamClassManager.register(amara.game.entitysystem.components.shop.ShopRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.shop.ShopRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.shop.ShopRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.shop.ShopRangeComponent>("shopRange"){

            @Override
            public amara.game.entitysystem.components.shop.ShopRangeComponent construct(){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(rangeText));
                }
                return new amara.game.entitysystem.components.shop.ShopRangeComponent(range);
            }
        });
        //spawns
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.RelativeSpawnPositionComponent.class);
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
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent>("spawnAttackMove"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent construct(){
                return new amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent>("spawnMovementAnimation"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent.class.getDeclaredField("speed"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent>("spawnMovementSpeed"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent construct(){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(speedText));
                }
                return new amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent>("spawnMoveToTarget"){

            @Override
            public amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent construct(){
                return new amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spawns.SpawnTemplateComponent.class);
        //spells
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class.getDeclaredField("casterEntityID"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class.getDeclaredField("castedSpellEntityID"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.ApplyCastedSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.ApplyCastedSpellComponent>("applyCastedSpell"){

            @Override
            public amara.game.entitysystem.components.spells.ApplyCastedSpellComponent construct(){
                int casterEntityID = 0;
                String casterEntityIDText = element.getAttributeValue("casterEntityID");
                if((casterEntityIDText != null) && (casterEntityIDText.length() > 0)){
                    casterEntityID = Integer.parseInt(xmlTemplateManager.parseValue(casterEntityIDText));
                }
                int castedSpellEntityID = 0;
                String castedSpellEntityIDText = element.getAttributeValue("castedSpellEntityID");
                if((castedSpellEntityIDText != null) && (castedSpellEntityIDText.length() > 0)){
                    castedSpellEntityID = Integer.parseInt(xmlTemplateManager.parseValue(castedSpellEntityIDText));
                }
                return new amara.game.entitysystem.components.spells.ApplyCastedSpellComponent(casterEntityID, castedSpellEntityID);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.BaseCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.BaseCooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.BaseCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.BaseCooldownComponent>("baseCooldown"){

            @Override
            public amara.game.entitysystem.components.spells.BaseCooldownComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.spells.BaseCooldownComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.CastAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastAnimationComponent>("castAnimation"){

            @Override
            public amara.game.entitysystem.components.spells.CastAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.spells.CastAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastCancelableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastCancelableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastCancelableComponent>("castCancelable"){

            @Override
            public amara.game.entitysystem.components.spells.CastCancelableComponent construct(){
                return new amara.game.entitysystem.components.spells.CastCancelableComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastCancelActionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastCancelActionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastCancelActionComponent>("castCancelAction"){

            @Override
            public amara.game.entitysystem.components.spells.CastCancelActionComponent construct(){
                return new amara.game.entitysystem.components.spells.CastCancelActionComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.CastDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastDurationComponent>("castDuration"){

            @Override
            public amara.game.entitysystem.components.spells.CastDurationComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.spells.CastDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastTurnToTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CastTurnToTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CastTurnToTargetComponent>("castTurnToTarget"){

            @Override
            public amara.game.entitysystem.components.spells.CastTurnToTargetComponent construct(){
                return new amara.game.entitysystem.components.spells.CastTurnToTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CastTypeComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.CooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.CooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.CooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.CooldownComponent>("cooldown"){

            @Override
            public amara.game.entitysystem.components.spells.CooldownComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.spells.CooldownComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.InstantEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.InstantEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.InstantEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.InstantEffectTriggersComponent>("instantEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.spells.InstantEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.spells.InstantEffectTriggersComponent(effectTriggerEntities);
            }
        });
        //placeholders
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent>("sourceMovementDirection"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent construct(){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(angle_DegreesText));
                }
                return new amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent>("targetedMovementDirection"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent construct(){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(angle_DegreesText));
                }
                return new amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent>("targetedMovementTarget"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent>("teleportToTargetPosition"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent>("triggerCastedSpellEffects"){

            @Override
            public amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent construct(){
                return new amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.RangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.RangeComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.RangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.RangeComponent>("range"){

            @Override
            public amara.game.entitysystem.components.spells.RangeComponent construct(){
                float distange = 0;
                String distangeText = element.getText();
                if((distangeText != null) && (distangeText.length() > 0)){
                    distange = Float.parseFloat(xmlTemplateManager.parseValue(distangeText));
                }
                return new amara.game.entitysystem.components.spells.RangeComponent(distange);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.RemainingCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.RemainingCooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.RemainingCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.RemainingCooldownComponent>("remainingCooldown"){

            @Override
            public amara.game.entitysystem.components.spells.RemainingCooldownComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.spells.RemainingCooldownComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.SpellTargetRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.SpellTargetRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.SpellTargetRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.SpellTargetRulesComponent>("spellTargetRules"){

            @Override
            public amara.game.entitysystem.components.spells.SpellTargetRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.spells.SpellTargetRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.SpellUpgradesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.SpellUpgradesComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.SpellUpgradesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.SpellUpgradesComponent>("spellUpgrades"){

            @Override
            public amara.game.entitysystem.components.spells.SpellUpgradesComponent construct(){
                int[] spellsEntities = createChildEntities(0, "spellsEntities");
                return new amara.game.entitysystem.components.spells.SpellUpgradesComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.SpellVisualisationComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.SpellVisualisationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.SpellVisualisationComponent>("spellVisualisation"){

            @Override
            public amara.game.entitysystem.components.spells.SpellVisualisationComponent construct(){
                String name = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.spells.SpellVisualisationComponent(name);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.StopAfterCastingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.StopAfterCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.StopAfterCastingComponent>("stopAfterCasting"){

            @Override
            public amara.game.entitysystem.components.spells.StopAfterCastingComponent construct(){
                return new amara.game.entitysystem.components.spells.StopAfterCastingComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.StopBeforeCastingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.StopBeforeCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.StopBeforeCastingComponent>("stopBeforeCasting"){

            @Override
            public amara.game.entitysystem.components.spells.StopBeforeCastingComponent construct(){
                return new amara.game.entitysystem.components.spells.StopBeforeCastingComponent();
            }
        });
        //triggers
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent>("castedEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.spells.triggers.CastedSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.spells.triggers.CastedSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.spells.triggers.CastedSpellComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.spells.triggers.CastedSpellComponent>("castedSpell"){

            @Override
            public amara.game.entitysystem.components.spells.triggers.CastedSpellComponent construct(){
                int spellEntity = createChildEntity(0, "spellEntity");
                return new amara.game.entitysystem.components.spells.triggers.CastedSpellComponent(spellEntity);
            }
        });
        //targets
        bitstreamClassManager.register(amara.game.entitysystem.components.targets.AcceptAlliesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.AcceptAlliesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.AcceptAlliesComponent>("acceptAllies"){

            @Override
            public amara.game.entitysystem.components.targets.AcceptAlliesComponent construct(){
                return new amara.game.entitysystem.components.targets.AcceptAlliesComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.targets.AcceptEnemiesComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.AcceptEnemiesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.AcceptEnemiesComponent>("acceptEnemies"){

            @Override
            public amara.game.entitysystem.components.targets.AcceptEnemiesComponent construct(){
                return new amara.game.entitysystem.components.targets.AcceptEnemiesComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.targets.RequireProjectileComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.targets.RequireProjectileComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.targets.RequireProjectileComponent>("requireProjectile"){

            @Override
            public amara.game.entitysystem.components.targets.RequireProjectileComponent construct(){
                return new amara.game.entitysystem.components.targets.RequireProjectileComponent();
            }
        });
        //units
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AggroResetTimerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AggroResetTimerComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AggroResetTimerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AggroResetTimerComponent>("aggroResetTimer"){

            @Override
            public amara.game.entitysystem.components.units.AggroResetTimerComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.units.AggroResetTimerComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AggroTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AggroTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AggroTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AggroTargetComponent>("aggroTarget"){

            @Override
            public amara.game.entitysystem.components.units.AggroTargetComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.units.AggroTargetComponent(targetEntity);
            }
        });
        //animations
        bitstreamClassManager.register(amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent>("autoAttackAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.animations.DeathAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.animations.DeathAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.DeathAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.DeathAnimationComponent>("deathAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.DeathAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.DeathAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.animations.IdleAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.animations.IdleAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.IdleAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.IdleAnimationComponent>("idleAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.IdleAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.IdleAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.animations.WalkAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.animations.WalkAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.animations.WalkAnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.animations.WalkAnimationComponent>("walkAnimation"){

            @Override
            public amara.game.entitysystem.components.units.animations.WalkAnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.units.animations.WalkAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AttackMoveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AttackMoveComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AttackMoveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AttackMoveComponent>("attackMove"){

            @Override
            public amara.game.entitysystem.components.units.AttackMoveComponent construct(){
                int targetEntity = createChildEntity(0, "targetEntity");
                return new amara.game.entitysystem.components.units.AttackMoveComponent(targetEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AttributesPerLevelComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AttributesPerLevelComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AttributesPerLevelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AttributesPerLevelComponent>("attributesPerLevel"){

            @Override
            public amara.game.entitysystem.components.units.AttributesPerLevelComponent construct(){
                int bonusAttributesEntity = createChildEntity(0, "bonusAttributesEntity");
                return new amara.game.entitysystem.components.units.AttributesPerLevelComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AutoAggroComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AutoAggroComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AutoAggroComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AutoAggroComponent>("autoAggro"){

            @Override
            public amara.game.entitysystem.components.units.AutoAggroComponent construct(){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(rangeText));
                }
                return new amara.game.entitysystem.components.units.AutoAggroComponent(range);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.AutoAttackComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.AutoAttackComponent.class.getDeclaredField("autoAttackEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.AutoAttackComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.AutoAttackComponent>("autoAttack"){

            @Override
            public amara.game.entitysystem.components.units.AutoAttackComponent construct(){
                int autoAttackEntity = createChildEntity(0, "autoAttackEntity");
                return new amara.game.entitysystem.components.units.AutoAttackComponent(autoAttackEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.BaseAttributesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.BaseAttributesComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.BaseAttributesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.BaseAttributesComponent>("baseAttributes"){

            @Override
            public amara.game.entitysystem.components.units.BaseAttributesComponent construct(){
                int bonusAttributesEntity = createChildEntity(0, "bonusAttributesEntity");
                return new amara.game.entitysystem.components.units.BaseAttributesComponent(bonusAttributesEntity);
            }
        });
        //bounties
        bitstreamClassManager.register(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.bounties.BountyBuffComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.bounties.BountyBuffComponent>("bountyBuff"){

            @Override
            public amara.game.entitysystem.components.units.bounties.BountyBuffComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.units.bounties.BountyBuffComponent(buffEntity, duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.bounties.BountyExperienceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.bounties.BountyExperienceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.bounties.BountyExperienceComponent>("bountyExperience"){

            @Override
            public amara.game.entitysystem.components.units.bounties.BountyExperienceComponent construct(){
                int experience = 0;
                String experienceText = element.getText();
                if((experienceText != null) && (experienceText.length() > 0)){
                    experience = Integer.parseInt(xmlTemplateManager.parseValue(experienceText));
                }
                return new amara.game.entitysystem.components.units.bounties.BountyExperienceComponent(experience);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.bounties.BountyGoldComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.bounties.BountyGoldComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.bounties.BountyGoldComponent>("bountyGold"){

            @Override
            public amara.game.entitysystem.components.units.bounties.BountyGoldComponent construct(){
                int gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Integer.parseInt(xmlTemplateManager.parseValue(goldText));
                }
                return new amara.game.entitysystem.components.units.bounties.BountyGoldComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.BountyComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.BountyComponent.class.getDeclaredField("bountyEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.BountyComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.BountyComponent>("bounty"){

            @Override
            public amara.game.entitysystem.components.units.BountyComponent construct(){
                int bountyEntity = createChildEntity(0, "bountyEntity");
                return new amara.game.entitysystem.components.units.BountyComponent(bountyEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.CampComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.CampComponent.class.getDeclaredField("campEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
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
        bitstreamClassManager.register(amara.game.entitysystem.components.units.CampResetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CampResetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CampResetComponent>("campReset"){

            @Override
            public amara.game.entitysystem.components.units.CampResetComponent construct(){
                return new amara.game.entitysystem.components.units.CampResetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent.class);
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
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent>("isBinded"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsBindedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent>("isBindedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsBindedImmuneComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class.getDeclaredField("knockupEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent>("isKnockuped"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent construct(){
                int knockupEntity = createChildEntity(0, "knockupEntity");
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedComponent(knockupEntity, remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent>("isKnockupedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsKnockupedImmuneComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent>("isSilenced"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsSilencedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent>("isSilencedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsSilencedImmuneComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent>("isStunned"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsStunnedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent>("isStunnedImmune"){

            @Override
            public amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.crowdcontrol.IsStunnedImmuneComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent.class.getDeclaredField("effectCastEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent>("currentActionEffectCasts"){

            @Override
            public amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent construct(){
                int[] effectCastEntities = createChildEntities(0, "effectCastEntities");
                return new amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent(effectCastEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.DamageHistoryComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.DamageHistoryComponent.class.getDeclaredField("firstDamageTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.DamageHistoryComponent.class.getDeclaredField("lastDamageTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        //effecttriggers
        //targets
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent>("buffTargetsTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent construct(){
                int buffEntity = createChildEntity(0, "buffEntity");
                return new amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent(buffEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent>("casterTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class.getDeclaredField("targetEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent>("customTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent construct(){
                int[] targetEntities = createChildEntities(0, "targetEntities");
                return new amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent(targetEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent>("sourceTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent>("targetTarget"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent>("triggerDelay"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent>("triggeredEffect"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent construct(){
                int effectEntity = createChildEntity(0, "effectEntity");
                return new amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent(effectEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent>("triggerOnCancel"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent>("triggerOnce"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent();
            }
        });
        //triggers
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent>("castingFinishedTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent>("collisionTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent>("damageTakenTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent construct(){
                boolean physicalDamage = false;
                String physicalDamageText = element.getAttributeValue("physicalDamage");
                if((physicalDamageText != null) && (physicalDamageText.length() > 0)){
                    physicalDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(physicalDamageText));
                }
                boolean magicDamage = false;
                String magicDamageText = element.getAttributeValue("magicDamage");
                if((magicDamageText != null) && (magicDamageText.length() > 0)){
                    magicDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(magicDamageText));
                }
                boolean trueDamage = false;
                String trueDamageText = element.getAttributeValue("trueDamage");
                if((trueDamageText != null) && (trueDamageText.length() > 0)){
                    trueDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(trueDamageText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent(physicalDamage, magicDamage, trueDamage);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent>("deathTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent>("instantTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class.getDeclaredField("intervalDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent>("repeatingTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent construct(){
                float intervalDuration = 0;
                String intervalDurationText = element.getText();
                if((intervalDurationText != null) && (intervalDurationText.length() > 0)){
                    intervalDuration = Float.parseFloat(xmlTemplateManager.parseValue(intervalDurationText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent(intervalDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent>("repeatingTriggerCounter"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent construct(){
                int counter = 0;
                String counterText = element.getText();
                if((counterText != null) && (counterText.length() > 0)){
                    counter = Integer.parseInt(xmlTemplateManager.parseValue(counterText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent(counter);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent>("stacksReachedTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent construct(){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(stacksText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent>("targetReachedTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent>("timeSinceLastRepeatTrigger"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent>("triggerSource"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent construct(){
                int sourceEntity = createChildEntity(0, "sourceEntity");
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent>("triggerTemporary"){

            @Override
            public amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent construct(){
                return new amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.ExperienceComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.ExperienceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.ExperienceComponent>("experience"){

            @Override
            public amara.game.entitysystem.components.units.ExperienceComponent construct(){
                int experience = 0;
                String experienceText = element.getText();
                if((experienceText != null) && (experienceText.length() > 0)){
                    experience = Integer.parseInt(xmlTemplateManager.parseValue(experienceText));
                }
                return new amara.game.entitysystem.components.units.ExperienceComponent(experience);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.GoldComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.GoldComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.GoldComponent>("gold"){

            @Override
            public amara.game.entitysystem.components.units.GoldComponent construct(){
                int gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Integer.parseInt(xmlTemplateManager.parseValue(goldText));
                }
                return new amara.game.entitysystem.components.units.GoldComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.HealthBarStyleComponent.class);
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IntersectionRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.IntersectionRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IntersectionRulesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IntersectionRulesComponent>("intersectionRules"){

            @Override
            public amara.game.entitysystem.components.units.IntersectionRulesComponent construct(){
                int targetRulesEntity = createChildEntity(0, "targetRulesEntity");
                return new amara.game.entitysystem.components.units.IntersectionRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsAliveComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsAliveComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsAliveComponent>("isAlive"){

            @Override
            public amara.game.entitysystem.components.units.IsAliveComponent construct(){
                return new amara.game.entitysystem.components.units.IsAliveComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsCastingComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.IsCastingComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsCastingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsCastingComponent>("isCasting"){

            @Override
            public amara.game.entitysystem.components.units.IsCastingComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                boolean isCancelable = false;
                String isCancelableText = element.getAttributeValue("isCancelable");
                if((isCancelableText != null) && (isCancelableText.length() > 0)){
                    isCancelable = Boolean.parseBoolean(xmlTemplateManager.parseValue(isCancelableText));
                }
                return new amara.game.entitysystem.components.units.IsCastingComponent(remainingDuration, isCancelable);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsHoveredComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsHoveredComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsHoveredComponent>("isHovered"){

            @Override
            public amara.game.entitysystem.components.units.IsHoveredComponent construct(){
                return new amara.game.entitysystem.components.units.IsHoveredComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsProjectileComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsProjectileComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsProjectileComponent>("isProjectile"){

            @Override
            public amara.game.entitysystem.components.units.IsProjectileComponent construct(){
                return new amara.game.entitysystem.components.units.IsProjectileComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsTargetableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsTargetableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsTargetableComponent>("isTargetable"){

            @Override
            public amara.game.entitysystem.components.units.IsTargetableComponent construct(){
                return new amara.game.entitysystem.components.units.IsTargetableComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsVulnerableComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsVulnerableComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsVulnerableComponent>("isVulnerable"){

            @Override
            public amara.game.entitysystem.components.units.IsVulnerableComponent construct(){
                return new amara.game.entitysystem.components.units.IsVulnerableComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent>("isWalkingToAggroTarget"){

            @Override
            public amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent construct(){
                return new amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.LearnableSpellsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.LearnableSpellsComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.LearnableSpellsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.LearnableSpellsComponent>("learnableSpells"){

            @Override
            public amara.game.entitysystem.components.units.LearnableSpellsComponent construct(){
                int[] spellsEntities = createChildEntities(0, "spellsEntities");
                return new amara.game.entitysystem.components.units.LearnableSpellsComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.LevelComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.LevelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.LevelComponent>("level"){

            @Override
            public amara.game.entitysystem.components.units.LevelComponent construct(){
                int level = 0;
                String levelText = element.getText();
                if((levelText != null) && (levelText.length() > 0)){
                    level = Integer.parseInt(xmlTemplateManager.parseValue(levelText));
                }
                return new amara.game.entitysystem.components.units.LevelComponent(level);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.LifetimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.LifetimeComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.LifetimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.LifetimeComponent>("lifetime"){

            @Override
            public amara.game.entitysystem.components.units.LifetimeComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.LifetimeComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent>("localAvoidanceWalk"){

            @Override
            public amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent construct(){
                return new amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.MaximumAggroRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.MaximumAggroRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.MaximumAggroRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.MaximumAggroRangeComponent>("maximumAggroRange"){

            @Override
            public amara.game.entitysystem.components.units.MaximumAggroRangeComponent construct(){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(rangeText));
                }
                return new amara.game.entitysystem.components.units.MaximumAggroRangeComponent(range);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.MovementComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.MovementComponent.class.getDeclaredField("movementEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.MovementComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.MovementComponent>("movement"){

            @Override
            public amara.game.entitysystem.components.units.MovementComponent construct(){
                int movementEntity = createChildEntity(0, "movementEntity");
                return new amara.game.entitysystem.components.units.MovementComponent(movementEntity);
            }
        });
        //passives
        bitstreamClassManager.register(amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent>("passiveAddedEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent>("passiveRemovedEffectTriggers"){

            @Override
            public amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent construct(){
                int[] effectTriggerEntities = createChildEntities(0, "effectTriggerEntities");
                return new amara.game.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.PassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.PassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.PassivesComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.PassivesComponent>("passives"){

            @Override
            public amara.game.entitysystem.components.units.PassivesComponent construct(){
                int[] passiveEntities = createChildEntities(0, "passiveEntities");
                return new amara.game.entitysystem.components.units.PassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.ReactionComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.ReactionComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.ReactionComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.ReactionComponent>("reaction"){

            @Override
            public amara.game.entitysystem.components.units.ReactionComponent construct(){
                String reaction = xmlTemplateManager.parseValue(element.getAttributeValue("reaction"));
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.ReactionComponent(reaction, remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent>("remainingAggroResetDuration"){

            @Override
            public amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent construct(){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(remainingDurationText));
                }
                return new amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent.class.getDeclaredField("cooldowns"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent>("setNewTargetSpellsOnCooldown"){

            @Override
            public amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent construct(){
                String[] spellIndicesParts = element.getAttributeValue("spellIndices").split(",");
                int[] spellIndices = new int[spellIndicesParts.length];
                for(int i=0;i<spellIndices.length;i++){
                    spellIndices[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getAttributeValue("spellIndices")));
                }
                String[] cooldownsParts = element.getAttributeValue("cooldowns").split(",");
                float[] cooldowns = new float[cooldownsParts.length];
                for(int i=0;i<cooldowns.length;i++){
                    cooldowns[i] = Float.parseFloat(xmlTemplateManager.parseValue(element.getAttributeValue("cooldowns")));
                }
                return new amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent(spellIndices, cooldowns);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.SightRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.SightRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SightRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SightRangeComponent>("sightRange"){

            @Override
            public amara.game.entitysystem.components.units.SightRangeComponent construct(){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(rangeText));
                }
                return new amara.game.entitysystem.components.units.SightRangeComponent(range);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.SpellsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.SpellsComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SpellsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SpellsComponent>("spells"){

            @Override
            public amara.game.entitysystem.components.units.SpellsComponent construct(){
                int[] spellsEntities = createChildEntities(0, "spellsEntities");
                return new amara.game.entitysystem.components.units.SpellsComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.SpellsUpgradePointsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.SpellsUpgradePointsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.SpellsUpgradePointsComponent>("spellsUpgradePoints"){

            @Override
            public amara.game.entitysystem.components.units.SpellsUpgradePointsComponent construct(){
                int upgradePoints = 0;
                String upgradePointsText = element.getText();
                if((upgradePointsText != null) && (upgradePointsText.length() > 0)){
                    upgradePoints = Integer.parseInt(xmlTemplateManager.parseValue(upgradePointsText));
                }
                return new amara.game.entitysystem.components.units.SpellsUpgradePointsComponent(upgradePoints);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.TargetsInAggroRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.TargetsInAggroRangeComponent.class.getDeclaredField("targetEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.TargetsInAggroRangeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.TargetsInAggroRangeComponent>("targetsInAggroRange"){

            @Override
            public amara.game.entitysystem.components.units.TargetsInAggroRangeComponent construct(){
                String[] targetEntitiesParts = element.getText().split(",");
                int[] targetEntities = new int[targetEntitiesParts.length];
                for(int i=0;i<targetEntities.length;i++){
                    targetEntities[i] = Integer.parseInt(xmlTemplateManager.parseValue(element.getText()));
                }
                return new amara.game.entitysystem.components.units.TargetsInAggroRangeComponent(targetEntities);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.TeamComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.TeamComponent.class.getDeclaredField("teamEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.TeamComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.TeamComponent>("team"){

            @Override
            public amara.game.entitysystem.components.units.TeamComponent construct(){
                int teamEntity = createChildEntity(0, "teamEntity");
                return new amara.game.entitysystem.components.units.TeamComponent(teamEntity);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.units.WalkStepDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.units.WalkStepDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.units.WalkStepDistanceComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.units.WalkStepDistanceComponent>("walkStepDistance"){

            @Override
            public amara.game.entitysystem.components.units.WalkStepDistanceComponent construct(){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(distanceText));
                }
                return new amara.game.entitysystem.components.units.WalkStepDistanceComponent(distance);
            }
        });
        //visuals
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.AnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.visuals.AnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.AnimationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.AnimationComponent>("animation"){

            @Override
            public amara.game.entitysystem.components.visuals.AnimationComponent construct(){
                int animationEntity = createChildEntity(0, "animationEntity");
                return new amara.game.entitysystem.components.visuals.AnimationComponent(animationEntity);
            }
        });
        //animations
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent>("freezeAfterPlaying"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent construct(){
                return new amara.game.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent();
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.animations.LoopDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.visuals.animations.LoopDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.LoopDurationComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.LoopDurationComponent>("loopDuration"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.LoopDurationComponent construct(){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(durationText));
                }
                return new amara.game.entitysystem.components.visuals.animations.LoopDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class.getDeclaredField("passedTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent>("passedLoopTime"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent construct(){
                float passedTime = 0;
                String passedTimeText = element.getText();
                if((passedTimeText != null) && (passedTimeText.length() > 0)){
                    passedTime = Float.parseFloat(xmlTemplateManager.parseValue(passedTimeText));
                }
                return new amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent(passedTime);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent>("remainingLoops"){

            @Override
            public amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent construct(){
                int loopsCount = 0;
                String loopsCountText = element.getText();
                if((loopsCountText != null) && (loopsCountText.length() > 0)){
                    loopsCount = Integer.parseInt(xmlTemplateManager.parseValue(loopsCountText));
                }
                return new amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent(loopsCount);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.ModelComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.ModelComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.ModelComponent>("model"){

            @Override
            public amara.game.entitysystem.components.visuals.ModelComponent construct(){
                String modelSkinPath = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.visuals.ModelComponent(modelSkinPath);
            }
        });
        bitstreamClassManager.register(amara.game.entitysystem.components.visuals.TitleComponent.class);
        xmlTemplateManager.registerComponent(amara.game.entitysystem.components.visuals.TitleComponent.class, new XMLComponentConstructor<amara.game.entitysystem.components.visuals.TitleComponent>("title"){

            @Override
            public amara.game.entitysystem.components.visuals.TitleComponent construct(){
                String title = xmlTemplateManager.parseValue(element.getText());
                return new amara.game.entitysystem.components.visuals.TitleComponent(title);
            }
        });
    }
}