package amara.applications.ingame.entitysystem.templates;

import com.jme3.math.Vector2f;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.entitysystem.synchronizing.fieldserializers.*;
import amara.libraries.entitysystem.templates.*;
import org.jdom2.Element;

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
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.AbilityPowerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.AbilityPowerComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.AbilityPowerComponent>("abilityPower"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.AbilityPowerComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.AbilityPowerComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.ArmorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.ArmorComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.ArmorComponent>("armor"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.ArmorComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.ArmorComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.AttackDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.AttackDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.AttackDamageComponent>("attackDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.AttackDamageComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.AttackDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.AttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.AttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.AttackSpeedComponent>("attackSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.AttackSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.AttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAbilityPowerComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatAbilityPowerComponent>("bonusFlatAbilityPower"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatAbilityPowerComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatAbilityPowerComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatArmorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatArmorComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatArmorComponent>("bonusFlatArmor"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatArmorComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatArmorComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent>("bonusFlatAttackDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent>("bonusFlatAttackSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatGoldPerSecondComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatGoldPerSecondComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatGoldPerSecondComponent>("bonusFlatGoldPerSecond"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatGoldPerSecondComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatGoldPerSecondComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent>("bonusFlatHealthRegeneration"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatHealthRegenerationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatMagicResistanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatMagicResistanceComponent>("bonusFlatMagicResistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatMagicResistanceComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatMagicResistanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent>("bonusFlatMaximumHealth"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent>("bonusFlatWalkSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent>("bonusPercentageAttackSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageAttackSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent>("bonusPercentageCooldownSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCooldownSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent>("bonusPercentageCriticalChance"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageIncomingDamageAmplificationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageIncomingDamageAmplificationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageIncomingDamageAmplificationComponent>("bonusPercentageIncomingDamageAmplification"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageIncomingDamageAmplificationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageIncomingDamageAmplificationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageLifestealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageLifestealComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageLifestealComponent>("bonusPercentageLifesteal"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageLifestealComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageLifestealComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageOutgoingDamageAmplificationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageOutgoingDamageAmplificationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageOutgoingDamageAmplificationComponent>("bonusPercentageOutgoingDamageAmplification"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageOutgoingDamageAmplificationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageOutgoingDamageAmplificationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent>("bonusPercentageWalkSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.BonusPercentageWalkSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.CooldownSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.CooldownSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.CooldownSpeedComponent>("cooldownSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.CooldownSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.CooldownSpeedComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.CriticalChanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.CriticalChanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.CriticalChanceComponent>("criticalChance"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.CriticalChanceComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.CriticalChanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.DisableGoldPerSecondComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.DisableGoldPerSecondComponent>("disableGoldPerSecond"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.DisableGoldPerSecondComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.attributes.DisableGoldPerSecondComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.GoldPerSecondComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.GoldPerSecondComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.GoldPerSecondComponent>("goldPerSecond"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.GoldPerSecondComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.GoldPerSecondComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.HealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.HealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.HealthComponent>("health"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.HealthComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.HealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent>("healthRegeneration"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.IncomingDamageAmplificationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.IncomingDamageAmplificationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.IncomingDamageAmplificationComponent>("incomingDamageAmplification"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.IncomingDamageAmplificationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.IncomingDamageAmplificationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.LifestealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.LifestealComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.LifestealComponent>("lifesteal"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.LifestealComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.LifestealComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.MagicResistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.MagicResistanceComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.MagicResistanceComponent>("magicResistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.MagicResistanceComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.MagicResistanceComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent>("maximumHealth"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.OutgoingDamageAmplificationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.OutgoingDamageAmplificationComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.OutgoingDamageAmplificationComponent>("outgoingDamageAmplification"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.OutgoingDamageAmplificationComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.OutgoingDamageAmplificationComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent>("requestUpdateAttributes"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.attributes.WalkSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.attributes.WalkSpeedComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.attributes.WalkSpeedComponent>("walkSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.attributes.WalkSpeedComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.attributes.WalkSpeedComponent(value);
            }
        });
        //audio
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioComponent>("audio"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioComponent construct(EntityWorld entityWorld, Element element){
                String audioPath = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.audio.AudioComponent(audioPath);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioGlobalComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioGlobalComponent>("audioGlobal"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioGlobalComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.audio.AudioGlobalComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioLoopComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioLoopComponent>("audioLoop"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioLoopComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.audio.AudioLoopComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioRemoveAfterPlayingComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioRemoveAfterPlayingComponent>("audioRemoveAfterPlaying"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioRemoveAfterPlayingComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.audio.AudioRemoveAfterPlayingComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.audio.AudioSourceComponent.class.getDeclaredField("entity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioSourceComponent>("audioSource"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioSourceComponent construct(EntityWorld entityWorld, Element element){
                int entity = createChildEntity(entityWorld, element, 0, "entity");
                return new amara.applications.ingame.entitysystem.components.audio.AudioSourceComponent(entity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent.class.getDeclaredField("audioEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent.class.getDeclaredField("delay"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent>("audioSuccessor"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent construct(EntityWorld entityWorld, Element element){
                int audioEntity = createChildEntity(entityWorld, element, 0, "audioEntity");
                float delay = 0;
                String delayText = element.getAttributeValue("delay");
                if((delayText != null) && (delayText.length() > 0)){
                    delay = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, delayText));
                }
                return new amara.applications.ingame.entitysystem.components.audio.AudioSuccessorComponent(audioEntity, delay);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.AudioVolumeComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.AudioVolumeComponent>("audioVolume"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.AudioVolumeComponent construct(EntityWorld entityWorld, Element element){
                float volume = 0;
                String volumeText = element.getText();
                if((volumeText != null) && (volumeText.length() > 0)){
                    volume = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, volumeText));
                }
                return new amara.applications.ingame.entitysystem.components.audio.AudioVolumeComponent(volume);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.StartPlayingAudioComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.StartPlayingAudioComponent>("startPlayingAudio"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.StartPlayingAudioComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.audio.StartPlayingAudioComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.audio.StopPlayingAudioComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.audio.StopPlayingAudioComponent>("stopPlayingAudio"){

            @Override
            public amara.applications.ingame.entitysystem.components.audio.StopPlayingAudioComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.audio.StopPlayingAudioComponent();
            }
        });
        //buffs
        //areas
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffComponent>("areaBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffComponent construct(EntityWorld entityWorld, Element element){
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffComponent(buffEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent>("areaBuffTargetRules"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent construct(EntityWorld entityWorld, Element element){
                int targetRulesEntity = createChildEntity(entityWorld, element, 0, "targetRulesEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.areas.AreaSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.areas.AreaSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.areas.AreaSourceComponent>("areaSource"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.areas.AreaSourceComponent construct(EntityWorld entityWorld, Element element){
                int sourceEntity = createChildEntity(entityWorld, element, 0, "sourceEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.areas.AreaSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent.class.getDeclaredField("stacksEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent>("buffStacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent construct(EntityWorld entityWorld, Element element){
                int stacksEntity = createChildEntity(entityWorld, element, 0, "stacksEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.BuffStacksComponent(stacksEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent>("continuousAttributes"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent construct(EntityWorld entityWorld, Element element){
                int bonusAttributesEntity = createChildEntity(entityWorld, element, 0, "bonusAttributesEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent>("continuousAttributesPerStack"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent construct(EntityWorld entityWorld, Element element){
                int bonusAttributesEntity = createChildEntity(entityWorld, element, 0, "bonusAttributesEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesPerStackComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.KeepOnDeathComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.KeepOnDeathComponent>("keepOnDeath"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.KeepOnDeathComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.buffs.KeepOnDeathComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent>("onBuffRemoveEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent.class.getDeclaredField("interval"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent>("repeatingEffect"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent construct(EntityWorld entityWorld, Element element){
                int effectEntity = createChildEntity(entityWorld, element, 0, "effectEntity");
                float interval = 0;
                String intervalText = element.getAttributeValue("interval");
                if((intervalText != null) && (intervalText.length() > 0)){
                    interval = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, intervalText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.RepeatingEffectComponent(effectEntity, interval);
            }
        });
        //stacks
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.stacks.MaximumStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.stacks.MaximumStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.stacks.MaximumStacksComponent>("maximumStacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.stacks.MaximumStacksComponent construct(EntityWorld entityWorld, Element element){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, stacksText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.stacks.MaximumStacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent>("stacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent construct(EntityWorld entityWorld, Element element){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, stacksText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.stacks.StacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.stacks.StacksRefreshmentComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.stacks.StacksRefreshmentComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.stacks.StacksRefreshmentComponent>("stacksRefreshment"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.stacks.StacksRefreshmentComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.stacks.StacksRefreshmentComponent(duration);
            }
        });
        //status
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent>("activeBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                return new amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent(targetEntity, buffEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.status.BuffVisualisationComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.status.BuffVisualisationComponent>("buffVisualisation"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.status.BuffVisualisationComponent construct(EntityWorld entityWorld, Element element){
                String name = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.buffs.status.BuffVisualisationComponent(name);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.status.RemainingBuffDurationComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.status.RemainingBuffDurationComponent>("remainingBuffDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.status.RemainingBuffDurationComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.status.RemainingBuffDurationComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.status.RemoveFromTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.status.RemoveFromTargetComponent>("removeFromTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.status.RemoveFromTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.buffs.status.RemoveFromTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent>("timeSinceLastRepeatingEffect"){

            @Override
            public amara.applications.ingame.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent(duration);
            }
        });
        //camps
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampHealthResetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampHealthResetComponent>("campHealthReset"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampHealthResetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.camps.CampHealthResetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampInCombatComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampInCombatComponent>("campInCombat"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampInCombatComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.camps.CampInCombatComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.camps.CampMaximumAggroDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampMaximumAggroDistanceComponent>("campMaximumAggroDistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampMaximumAggroDistanceComponent construct(EntityWorld entityWorld, Element element){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distanceText));
                }
                return new amara.applications.ingame.entitysystem.components.camps.CampMaximumAggroDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.camps.CampRemainingRespawnDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampRemainingRespawnDurationComponent>("campRemainingRespawnDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampRemainingRespawnDurationComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.camps.CampRemainingRespawnDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampRespawnDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.camps.CampRespawnDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampRespawnDurationComponent>("campRespawnDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampRespawnDurationComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.camps.CampRespawnDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampSpawnComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampSpawnComponent>("campSpawn"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampSpawnComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.camps.CampSpawnComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampSpawnInformationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.camps.CampSpawnInformationComponent.class.getDeclaredField("spawnInformationEntites"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampSpawnInformationComponent>("campSpawnInformation"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampSpawnInformationComponent construct(EntityWorld entityWorld, Element element){
                int[] spawnInformationEntities = createChildEntities(entityWorld, element, 0, "spawnInformationEntities");
                return new amara.applications.ingame.entitysystem.components.camps.CampSpawnInformationComponent(spawnInformationEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.camps.CampUnionAggroComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.camps.CampUnionAggroComponent>("campUnionAggro"){

            @Override
            public amara.applications.ingame.entitysystem.components.camps.CampUnionAggroComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.camps.CampUnionAggroComponent();
            }
        });
        //conditions
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent.class.getDeclaredField("buffEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent>("hasBuffCondition"){

            @Override
            public amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent construct(EntityWorld entityWorld, Element element){
                int[] buffEntities = createChildEntities(entityWorld, element, 0, "buffEntities");
                return new amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent(buffEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.conditions.HasHealthPortionConditionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.conditions.HasHealthPortionConditionComponent>("hasHealthPortionCondition"){

            @Override
            public amara.applications.ingame.entitysystem.components.conditions.HasHealthPortionConditionComponent construct(EntityWorld entityWorld, Element element){
                float portion = 0;
                String portionText = element.getAttributeValue("portion");
                if((portionText != null) && (portionText.length() > 0)){
                    portion = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, portionText));
                }
                boolean lessOrMore = false;
                String lessOrMoreText = element.getAttributeValue("lessOrMore");
                if((lessOrMoreText != null) && (lessOrMoreText.length() > 0)){
                    lessOrMore = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, lessOrMoreText));
                }
                boolean allowEqual = false;
                String allowEqualText = element.getAttributeValue("allowEqual");
                if((allowEqualText != null) && (allowEqualText.length() > 0)){
                    allowEqual = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, allowEqualText));
                }
                return new amara.applications.ingame.entitysystem.components.conditions.HasHealthPortionConditionComponent(portion, lessOrMore, allowEqual);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.conditions.NameAmountConditionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.conditions.NameAmountConditionComponent>("nameAmountCondition"){

            @Override
            public amara.applications.ingame.entitysystem.components.conditions.NameAmountConditionComponent construct(EntityWorld entityWorld, Element element){
                int maximum = 0;
                String maximumText = element.getText();
                if((maximumText != null) && (maximumText.length() > 0)){
                    maximum = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, maximumText));
                }
                return new amara.applications.ingame.entitysystem.components.conditions.NameAmountConditionComponent(maximum);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent.class.getDeclaredField("conditionEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent>("orConditions"){

            @Override
            public amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent construct(EntityWorld entityWorld, Element element){
                int[] conditionEntities = createChildEntities(entityWorld, element, 0, "conditionEntities");
                return new amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent(conditionEntities);
            }
        });
        //effects
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent.class.getDeclaredField("targetEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent>("affectedTargets"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent construct(EntityWorld entityWorld, Element element){
                int[] targetEntities = createChildEntities(entityWorld, element, 0, "targetEntities");
                return new amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent(targetEntities);
            }
        });
        //aggro
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.aggro.DrawTeamAggroComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.aggro.DrawTeamAggroComponent>("drawTeamAggro"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.aggro.DrawTeamAggroComponent construct(EntityWorld entityWorld, Element element){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, rangeText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.aggro.DrawTeamAggroComponent(range);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent>("applyEffectImpact"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent(targetEntity);
            }
        });
        //audio
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.audio.PlayAudioComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.audio.PlayAudioComponent.class.getDeclaredField("audioEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.audio.PlayAudioComponent>("playAudio"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.audio.PlayAudioComponent construct(EntityWorld entityWorld, Element element){
                boolean clone = false;
                String cloneText = element.getAttributeValue("clone");
                if((cloneText != null) && (cloneText.length() > 0)){
                    clone = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, cloneText));
                }
                int[] audioEntities = createChildEntities(entityWorld, element, 0, "audioEntities");
                return new amara.applications.ingame.entitysystem.components.effects.audio.PlayAudioComponent(clone, audioEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.audio.StopAudioComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.audio.StopAudioComponent.class.getDeclaredField("audioEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.audio.StopAudioComponent>("stopAudio"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.audio.StopAudioComponent construct(EntityWorld entityWorld, Element element){
                int[] audioEntities = createChildEntities(entityWorld, element, 0, "audioEntities");
                return new amara.applications.ingame.entitysystem.components.effects.audio.StopAudioComponent(audioEntities);
            }
        });
        //buffs
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent>("addBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent construct(EntityWorld entityWorld, Element element){
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent(buffEntity, duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.AddNewBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.AddNewBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.AddNewBuffComponent>("addNewBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.AddNewBuffComponent construct(EntityWorld entityWorld, Element element){
                String templateExpression = xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("templateExpression"));
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.buffs.AddNewBuffComponent(templateExpression, duration);
            }
        });
        //areas
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent.class.getDeclaredField("buffAreaEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent>("addBuffArea"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent construct(EntityWorld entityWorld, Element element){
                int buffAreaEntity = createChildEntity(entityWorld, element, 0, "buffAreaEntity");
                return new amara.applications.ingame.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent(buffAreaEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent.class.getDeclaredField("buffAreaEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent>("removeBuffArea"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent construct(EntityWorld entityWorld, Element element){
                int buffAreaEntity = createChildEntity(entityWorld, element, 0, "buffAreaEntity");
                return new amara.applications.ingame.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent(buffAreaEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent>("removeBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent construct(EntityWorld entityWorld, Element element){
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                return new amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent(buffEntity);
            }
        });
        //stacks
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent>("addStacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent construct(EntityWorld entityWorld, Element element){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, stacksText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.stacks.ClearStacksComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.stacks.ClearStacksComponent>("clearStacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.stacks.ClearStacksComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.buffs.stacks.ClearStacksComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent>("removeStacks"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent construct(EntityWorld entityWorld, Element element){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, stacksText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent(stacks);
            }
        });
        //casts
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent>("effectCastTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.effects.casts.EffectCastTargetComponent(targetEntity);
            }
        });
        //crowdcontrol
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindableComponent>("addBindable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindingComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindingComponent>("addBinding"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindingComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddBindingComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupableComponent>("addKnockupable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupComponent.class.getDeclaredField("knockupEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupComponent>("addKnockup"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupComponent construct(EntityWorld entityWorld, Element element){
                int knockupEntity = createChildEntity(entityWorld, element, 0, "knockupEntity");
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddKnockupComponent(knockupEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilencableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilencableComponent>("addSilencable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilencableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilencableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilenceComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilenceComponent>("addSilence"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilenceComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddSilenceComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunComponent>("addStun"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunnableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunnableComponent>("addStunnable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunnableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.AddStunnableComponent();
            }
        });
        //knockup
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent>("knockupDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent.class.getDeclaredField("height"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent>("knockupHeight"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent construct(EntityWorld entityWorld, Element element){
                float height = 0;
                String heightText = element.getText();
                if((heightText != null) && (heightText.length() > 0)){
                    height = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, heightText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.KnockupHeightComponent(height);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindableComponent>("removeBindable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent>("removeBinding"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupableComponent>("removeKnockupable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent>("removeKnockup"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilencableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilencableComponent>("removeSilencable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilencableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilencableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent>("removeSilence"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunComponent>("removeStun"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunnableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunnableComponent>("removeStunnable"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunnableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.crowdcontrol.RemoveStunnableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.CustomEffectValuesComponent.class);
        //damage
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.AddTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.AddTargetabilityComponent>("addTargetability"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.AddTargetabilityComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.damage.AddTargetabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.AddVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.AddVulnerabilityComponent>("addVulnerability"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.AddVulnerabilityComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.damage.AddVulnerabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.CanCritComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.CanCritComponent>("canCrit"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.CanCritComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.damage.CanCritComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.MagicDamageComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.MagicDamageComponent>("magicDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.MagicDamageComponent construct(EntityWorld entityWorld, Element element){
                String expression = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.damage.MagicDamageComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.PhysicalDamageComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.PhysicalDamageComponent>("physicalDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.PhysicalDamageComponent construct(EntityWorld entityWorld, Element element){
                String expression = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.damage.PhysicalDamageComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.RemoveTargetabilityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.RemoveTargetabilityComponent>("removeTargetability"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.RemoveTargetabilityComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.damage.RemoveTargetabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.RemoveVulnerabilityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.RemoveVulnerabilityComponent>("removeVulnerability"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.RemoveVulnerabilityComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.damage.RemoveVulnerabilityComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent>("resultingMagicDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.damage.ResultingMagicDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent>("resultingPhysicalDamage"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent(value);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent>("effectSource"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent construct(EntityWorld entityWorld, Element element){
                int sourceEntity = createChildEntity(entityWorld, element, 0, "sourceEntity");
                return new amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent>("effectSourceSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent construct(EntityWorld entityWorld, Element element){
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                return new amara.applications.ingame.entitysystem.components.effects.EffectSourceSpellComponent(spellEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent>("finishObjective"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent();
            }
        });
        //game
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.game.PlayCinematicComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.game.PlayCinematicComponent>("playCinematic"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.game.PlayCinematicComponent construct(EntityWorld entityWorld, Element element){
                String cinematicClassName = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.game.PlayCinematicComponent(cinematicClassName);
            }
        });
        //general
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.general.AddComponentsComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent>("addEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.general.RemoveComponentsComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent>("removeEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.general.RemoveEntityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.general.RemoveEntityComponent>("removeEntity"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.general.RemoveEntityComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.general.RemoveEntityComponent();
            }
        });
        //heals
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.heals.HealComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.heals.HealComponent>("heal"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.heals.HealComponent construct(EntityWorld entityWorld, Element element){
                String expression = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.heals.HealComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.heals.ResultingHealComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.heals.ResultingHealComponent.class.getDeclaredField("value"), componentFieldSerializer_Attribute);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.heals.ResultingHealComponent>("resultingHeal"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.heals.ResultingHealComponent construct(EntityWorld entityWorld, Element element){
                float value = 0;
                String valueText = element.getText();
                if((valueText != null) && (valueText.length() > 0)){
                    value = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, valueText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.heals.ResultingHealComponent(value);
            }
        });
        //movement
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.movement.MoveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.movement.MoveComponent.class.getDeclaredField("movementEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.movement.MoveComponent>("move"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.movement.MoveComponent construct(EntityWorld entityWorld, Element element){
                int movementEntity = createChildEntity(entityWorld, element, 0, "movementEntity");
                return new amara.applications.ingame.entitysystem.components.effects.movement.MoveComponent(movementEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.movement.StopComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.movement.StopComponent>("stop"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.movement.StopComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.movement.StopComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent>("teleport"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.effects.movement.TeleportComponent(targetEntity);
            }
        });
        //physics
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.ActivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.physics.ActivateHitboxComponent>("activateHitbox"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.physics.ActivateHitboxComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.physics.ActivateHitboxComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.AddCollisionGroupsComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.AddIntersectionPushComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.physics.AddIntersectionPushComponent>("addIntersectionPush"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.physics.AddIntersectionPushComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.physics.AddIntersectionPushComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.DeactivateHitboxComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.physics.DeactivateHitboxComponent>("deactivateHitbox"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.physics.DeactivateHitboxComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.physics.DeactivateHitboxComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.RemoveCollisionGroupsComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.physics.RemoveIntersectionPushComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.physics.RemoveIntersectionPushComponent>("removeIntersectionPush"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.physics.RemoveIntersectionPushComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.physics.RemoveIntersectionPushComponent();
            }
        });
        //players
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.players.DisplayPlayerAnnouncementComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.players.DisplayPlayerAnnouncementComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.players.DisplayPlayerAnnouncementComponent>("displayPlayerAnnouncement"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.players.DisplayPlayerAnnouncementComponent construct(EntityWorld entityWorld, Element element){
                String expression = xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("expression"));
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.players.DisplayPlayerAnnouncementComponent(expression, remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.players.ResultingPlayerAnnouncementComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.players.ResultingPlayerAnnouncementComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.players.ResultingPlayerAnnouncementComponent>("resultingPlayerAnnouncement"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.players.ResultingPlayerAnnouncementComponent construct(EntityWorld entityWorld, Element element){
                String text = xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("text"));
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.players.ResultingPlayerAnnouncementComponent(text, remainingDuration);
            }
        });
        //popups
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.popups.AddPopupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.popups.AddPopupComponent>("addPopup"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.popups.AddPopupComponent construct(EntityWorld entityWorld, Element element){
                String expression = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.popups.AddPopupComponent(expression);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.popups.RemovePopupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.popups.RemovePopupComponent>("removePopup"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.popups.RemovePopupComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.popups.RemovePopupComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.popups.ResultingPopupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.popups.ResultingPopupComponent>("resultingPopup"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.popups.ResultingPopupComponent construct(EntityWorld entityWorld, Element element){
                String text = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.effects.popups.ResultingPopupComponent(text);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent>("prepareEffect"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent construct(EntityWorld entityWorld, Element element){
                int effectEntity = createChildEntity(entityWorld, element, 0, "effectEntity");
                return new amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent(effectEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent>("remainingEffectDelay"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent(duration);
            }
        });
        //spawns
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent.class.getDeclaredField("spawnInformationEntites"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent>("spawn"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent construct(EntityWorld entityWorld, Element element){
                int[] spawnInformationEntities = createChildEntities(entityWorld, element, 0, "spawnInformationEntities");
                return new amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent(spawnInformationEntities);
            }
        });
        //spells
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent.class.getDeclaredField("spellEffectEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent>("addAutoAttackSpellEffects"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellEffectEntities = createChildEntities(entityWorld, element, 0, "spellEffectEntities");
                return new amara.applications.ingame.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent(spellEffectEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.AddSpellsSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spells.AddSpellsSpellEffectsComponent.class.getDeclaredField("spellEffectEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.AddSpellsSpellEffectsComponent>("addSpellsSpellEffects"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.AddSpellsSpellEffectsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellEffectEntities = createChildEntities(entityWorld, element, 0, "spellEffectEntities");
                boolean setSourcesToSpells = false;
                String setSourcesToSpellsText = element.getAttributeValue("setSourcesToSpells");
                if((setSourcesToSpellsText != null) && (setSourcesToSpellsText.length() > 0)){
                    setSourcesToSpells = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, setSourcesToSpellsText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.spells.AddSpellsSpellEffectsComponent(spellEffectEntities, setSourcesToSpells);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent.class.getDeclaredField("spellEffectEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent>("removeSpellEffects"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellEffectEntities = createChildEntities(entityWorld, element, 0, "spellEffectEntities");
                return new amara.applications.ingame.entitysystem.components.effects.spells.RemoveSpellEffectsComponent(spellEffectEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent>("replaceSpellWithExistingSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent construct(EntityWorld entityWorld, Element element){
                int spellIndex = 0;
                String spellIndexText = element.getAttributeValue("spellIndex");
                if((spellIndexText != null) && (spellIndexText.length() > 0)){
                    spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, spellIndexText));
                }
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                return new amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent(spellIndex, spellEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent>("replaceSpellWithNewSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent construct(EntityWorld entityWorld, Element element){
                int spellIndex = 0;
                String spellIndexText = element.getAttributeValue("spellIndex");
                if((spellIndexText != null) && (spellIndexText.length() > 0)){
                    spellIndex = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, spellIndexText));
                }
                String newSpellTemplate = xmlTemplateManager.parseTemplate(entityWorld, element.getAttributeValue("newSpellTemplate"));
                return new amara.applications.ingame.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent(spellIndex, newSpellTemplate);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.spells.TriggerSpellEffectsComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.spells.TriggerSpellEffectsComponent>("triggerSpellEffects"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.spells.TriggerSpellEffectsComponent construct(EntityWorld entityWorld, Element element){
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                return new amara.applications.ingame.entitysystem.components.effects.spells.TriggerSpellEffectsComponent(spellEntity);
            }
        });
        //units
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.units.AddGoldComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.units.AddGoldComponent>("addGold"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.units.AddGoldComponent construct(EntityWorld entityWorld, Element element){
                float gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, goldText));
                }
                return new amara.applications.ingame.entitysystem.components.effects.units.AddGoldComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.units.CancelActionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.units.CancelActionComponent>("cancelAction"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.units.CancelActionComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.units.CancelActionComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent>("respawn"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent();
            }
        });
        //vision
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.vision.AddStealthComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.vision.AddStealthComponent>("addStealth"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.vision.AddStealthComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.vision.AddStealthComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.vision.RemoveStealthComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.vision.RemoveStealthComponent>("removeStealth"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.vision.RemoveStealthComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.vision.RemoveStealthComponent();
            }
        });
        //visuals
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.visuals.PlayAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.effects.visuals.PlayAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.visuals.PlayAnimationComponent>("playAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.visuals.PlayAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.effects.visuals.PlayAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.effects.visuals.StopAnimationComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.effects.visuals.StopAnimationComponent>("stopAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.effects.visuals.StopAnimationComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.effects.visuals.StopAnimationComponent();
            }
        });
        //game
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.game.CinematicComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.game.CinematicComponent>("cinematic"){

            @Override
            public amara.applications.ingame.entitysystem.components.game.CinematicComponent construct(EntityWorld entityWorld, Element element){
                String cinematicClassName = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.game.CinematicComponent(cinematicClassName);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.game.GameSpeedComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.game.GameSpeedComponent>("gameSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.game.GameSpeedComponent construct(EntityWorld entityWorld, Element element){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, speedText));
                }
                return new amara.applications.ingame.entitysystem.components.game.GameSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.game.GameTimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.game.GameTimeComponent.class.getDeclaredField("time"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.game.GameTimeComponent>("gameTime"){

            @Override
            public amara.applications.ingame.entitysystem.components.game.GameTimeComponent construct(EntityWorld entityWorld, Element element){
                float time = 0;
                String timeText = element.getText();
                if((timeText != null) && (timeText.length() > 0)){
                    time = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, timeText));
                }
                return new amara.applications.ingame.entitysystem.components.game.GameTimeComponent(time);
            }
        });
        //general
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent>("customCleanup"){

            @Override
            public amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.general.DescriptionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.general.DescriptionComponent>("description"){

            @Override
            public amara.applications.ingame.entitysystem.components.general.DescriptionComponent construct(EntityWorld entityWorld, Element element){
                String description = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.general.DescriptionComponent(description);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.general.NameComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.general.NameComponent>("name"){

            @Override
            public amara.applications.ingame.entitysystem.components.general.NameComponent construct(EntityWorld entityWorld, Element element){
                String name = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.general.NameComponent(name);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.general.TemporaryComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.general.TemporaryComponent>("temporary"){

            @Override
            public amara.applications.ingame.entitysystem.components.general.TemporaryComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.general.TemporaryComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.general.UniqueComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.general.UniqueComponent>("unique"){

            @Override
            public amara.applications.ingame.entitysystem.components.general.UniqueComponent construct(EntityWorld entityWorld, Element element){
                String id = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.general.UniqueComponent(id);
            }
        });
        //input
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.input.CastSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.input.CastSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.input.CastSpellComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.input.CastSpellComponent>("castSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.input.CastSpellComponent construct(EntityWorld entityWorld, Element element){
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.input.CastSpellComponent(spellEntity, targetEntity);
            }
        });
        //items
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.BagComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.items.BagComponent.class.getDeclaredField("itemEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.BagComponent>("bag"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.BagComponent construct(EntityWorld entityWorld, Element element){
                int[] itemEntities = createChildEntities(entityWorld, element, 0, "itemEntities");
                return new amara.applications.ingame.entitysystem.components.items.BagComponent(itemEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.InventoryComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.items.InventoryComponent.class.getDeclaredField("itemEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.InventoryComponent>("inventory"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.InventoryComponent construct(EntityWorld entityWorld, Element element){
                int[] itemEntities = createChildEntities(entityWorld, element, 0, "itemEntities");
                return new amara.applications.ingame.entitysystem.components.items.InventoryComponent(itemEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.IsSellableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.IsSellableComponent>("isSellable"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.IsSellableComponent construct(EntityWorld entityWorld, Element element){
                float gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, goldText));
                }
                return new amara.applications.ingame.entitysystem.components.items.IsSellableComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemActiveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.items.ItemActiveComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemActiveComponent>("itemActive"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemActiveComponent construct(EntityWorld entityWorld, Element element){
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                boolean consumable = false;
                String consumableText = element.getAttributeValue("consumable");
                if((consumableText != null) && (consumableText.length() > 0)){
                    consumable = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, consumableText));
                }
                return new amara.applications.ingame.entitysystem.components.items.ItemActiveComponent(spellEntity, consumable);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemCategoriesComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemCategoriesComponent>("itemCategories"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemCategoriesComponent construct(EntityWorld entityWorld, Element element){
                String[] categories = new String[0];
                String categoriesText = element.getText();
                if(categoriesText != null){
                    categories = categoriesText.split(",");
                    for(int i=0;i<categories.length;i++){
                        categories[i] = xmlTemplateManager.parseValue(entityWorld, categories[i]);
                    }
                }
                return new amara.applications.ingame.entitysystem.components.items.ItemCategoriesComponent(categories);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemIDComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemIDComponent>("itemID"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemIDComponent construct(EntityWorld entityWorld, Element element){
                String id = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.items.ItemIDComponent(id);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemPassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.items.ItemPassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemPassivesComponent>("itemPassives"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemPassivesComponent construct(EntityWorld entityWorld, Element element){
                int[] passiveEntities = createChildEntities(entityWorld, element, 0, "passiveEntities");
                return new amara.applications.ingame.entitysystem.components.items.ItemPassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemRecipeComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemRecipeComponent>("itemRecipe"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemRecipeComponent construct(EntityWorld entityWorld, Element element){
                float gold = 0;
                String goldText = element.getAttributeValue("gold");
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, goldText));
                }
                String[] itemIDs = new String[0];
                String itemIDsText = element.getAttributeValue("itemIDs");
                if(itemIDsText != null){
                    itemIDs = itemIDsText.split(",");
                    for(int i=0;i<itemIDs.length;i++){
                        itemIDs[i] = xmlTemplateManager.parseValue(entityWorld, itemIDs[i]);
                    }
                }
                return new amara.applications.ingame.entitysystem.components.items.ItemRecipeComponent(gold, itemIDs);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent>("itemVisualisation"){

            @Override
            public amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent construct(EntityWorld entityWorld, Element element){
                String name = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.items.ItemVisualisationComponent(name);
            }
        });
        //maps
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent.class.getDeclaredField("objectiveEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent>("mapObjective"){

            @Override
            public amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent construct(EntityWorld entityWorld, Element element){
                int objectiveEntity = createChildEntity(entityWorld, element, 0, "objectiveEntity");
                return new amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent(objectiveEntity);
            }
        });
        //playerdeathrules
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent>("respawnPlayers"){

            @Override
            public amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class.getDeclaredField("initialDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent.class.getDeclaredField("deltaDurationPerTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent>("respawnTimer"){

            @Override
            public amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent construct(EntityWorld entityWorld, Element element){
                float initialDuration = 0;
                String initialDurationText = element.getAttributeValue("initialDuration");
                if((initialDurationText != null) && (initialDurationText.length() > 0)){
                    initialDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, initialDurationText));
                }
                float deltaDurationPerTime = 0;
                String deltaDurationPerTimeText = element.getAttributeValue("deltaDurationPerTime");
                if((deltaDurationPerTimeText != null) && (deltaDurationPerTimeText.length() > 0)){
                    deltaDurationPerTime = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, deltaDurationPerTimeText));
                }
                return new amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent(initialDuration, deltaDurationPerTime);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent.class.getDeclaredField("rulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent>("playerDeathRules"){

            @Override
            public amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent construct(EntityWorld entityWorld, Element element){
                int rulesEntity = createChildEntity(entityWorld, element, 0, "rulesEntity");
                return new amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent(rulesEntity);
            }
        });
        //movements
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.DisplacementComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.DisplacementComponent>("displacement"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.DisplacementComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.DisplacementComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.DistanceLimitComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.DistanceLimitComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.DistanceLimitComponent>("distanceLimit"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.DistanceLimitComponent construct(EntityWorld entityWorld, Element element){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distanceText));
                }
                return new amara.applications.ingame.entitysystem.components.movements.DistanceLimitComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovedDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.MovedDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovedDistanceComponent>("movedDistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovedDistanceComponent construct(EntityWorld entityWorld, Element element){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distanceText));
                }
                return new amara.applications.ingame.entitysystem.components.movements.MovedDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent>("movementAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent>("movementDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent construct(EntityWorld entityWorld, Element element){
                String[] directionCoordinates = element.getText().split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent(direction);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementIsCancelableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementIsCancelableComponent>("movementIsCancelable"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementIsCancelableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.MovementIsCancelableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementLocalAvoidanceComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementLocalAvoidanceComponent>("movementLocalAvoidance"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementLocalAvoidanceComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.MovementLocalAvoidanceComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementPathfindingComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementPathfindingComponent>("movementPathfinding"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementPathfindingComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.MovementPathfindingComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent.class.getDeclaredField("speed"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent>("movementSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent construct(EntityWorld entityWorld, Element element){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, speedText));
                }
                return new amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent>("movementTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent(targetEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementTargetReachedComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementTargetReachedComponent>("movementTargetReached"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementTargetReachedComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.MovementTargetReachedComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.movements.MovementTargetSufficientDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementTargetSufficientDistanceComponent>("movementTargetSufficientDistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementTargetSufficientDistanceComponent construct(EntityWorld entityWorld, Element element){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distanceText));
                }
                return new amara.applications.ingame.entitysystem.components.movements.MovementTargetSufficientDistanceComponent(distance);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent>("movementTurnInDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.movements.WalkMovementComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.movements.WalkMovementComponent>("walkMovement"){

            @Override
            public amara.applications.ingame.entitysystem.components.movements.WalkMovementComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.movements.WalkMovementComponent();
            }
        });
        //objectives
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.objectives.FinishedObjectiveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.objectives.FinishedObjectiveComponent>("finishedObjective"){

            @Override
            public amara.applications.ingame.entitysystem.components.objectives.FinishedObjectiveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.objectives.FinishedObjectiveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent.class.getDeclaredField("entities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent>("missingEntities"){

            @Override
            public amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent construct(EntityWorld entityWorld, Element element){
                int[] entities = createChildEntities(entityWorld, element, 0, "entities");
                return new amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent(entities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.objectives.OpenObjectiveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.objectives.OpenObjectiveComponent>("openObjective"){

            @Override
            public amara.applications.ingame.entitysystem.components.objectives.OpenObjectiveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.objectives.OpenObjectiveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.objectives.OrObjectivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.objectives.OrObjectivesComponent.class.getDeclaredField("objectiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.objectives.OrObjectivesComponent>("orObjectives"){

            @Override
            public amara.applications.ingame.entitysystem.components.objectives.OrObjectivesComponent construct(EntityWorld entityWorld, Element element){
                int[] objectiveEntities = createChildEntities(entityWorld, element, 0, "objectiveEntities");
                return new amara.applications.ingame.entitysystem.components.objectives.OrObjectivesComponent(objectiveEntities);
            }
        });
        //physics
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.CollisionGroupComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.DirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.DirectionComponent>("direction"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.DirectionComponent construct(EntityWorld entityWorld, Element element){
                float radian = 0;
                String radianText = element.getText();
                if((radianText != null) && (radianText.length() > 0)){
                    radian = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, radianText));
                }
                return new amara.applications.ingame.entitysystem.components.physics.DirectionComponent(radian);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent>("hitboxActive"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.HitboxComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.IntersectionPushComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.IntersectionPushComponent>("intersectionPush"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.IntersectionPushComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.physics.IntersectionPushComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.PositionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.PositionComponent>("position"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.PositionComponent construct(EntityWorld entityWorld, Element element){
                String[] positionCoordinates = element.getText().split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                return new amara.applications.ingame.entitysystem.components.physics.PositionComponent(position);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.RemoveOnMapLeaveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.RemoveOnMapLeaveComponent>("removeOnMapLeave"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.RemoveOnMapLeaveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.physics.RemoveOnMapLeaveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.ScaleComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.ScaleComponent>("scale"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.ScaleComponent construct(EntityWorld entityWorld, Element element){
                float scale = 0;
                String scaleText = element.getText();
                if((scaleText != null) && (scaleText.length() > 0)){
                    scale = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, scaleText));
                }
                return new amara.applications.ingame.entitysystem.components.physics.ScaleComponent(scale);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.physics.TransformOriginComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.physics.TransformOriginComponent.class.getDeclaredField("originEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.physics.TransformOriginComponent>("transformOrigin"){

            @Override
            public amara.applications.ingame.entitysystem.components.physics.TransformOriginComponent construct(EntityWorld entityWorld, Element element){
                int originEntity = createChildEntity(entityWorld, element, 0, "originEntity");
                return new amara.applications.ingame.entitysystem.components.physics.TransformOriginComponent(originEntity);
            }
        });
        //players
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.ClientComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.ClientComponent>("client"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.ClientComponent construct(EntityWorld entityWorld, Element element){
                int clientId = 0;
                String clientIdText = element.getText();
                if((clientIdText != null) && (clientIdText.length() > 0)){
                    clientId = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, clientIdText));
                }
                return new amara.applications.ingame.entitysystem.components.players.ClientComponent(clientId);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.IsBotComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.IsBotComponent>("isBot"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.IsBotComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.players.IsBotComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementComponent>("playerAnnouncement"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementComponent construct(EntityWorld entityWorld, Element element){
                String text = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementComponent(text);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementRemainingDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementRemainingDurationComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementRemainingDurationComponent>("playerAnnouncementRemainingDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementRemainingDurationComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.players.PlayerAnnouncementRemainingDurationComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent.class.getDeclaredField("entity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent>("playerCharacter"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent construct(EntityWorld entityWorld, Element element){
                int entity = createChildEntity(entityWorld, element, 0, "entity");
                return new amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent(entity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.players.PlayerIndexComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.players.PlayerIndexComponent>("playerIndex"){

            @Override
            public amara.applications.ingame.entitysystem.components.players.PlayerIndexComponent construct(EntityWorld entityWorld, Element element){
                int index = 0;
                String indexText = element.getText();
                if((indexText != null) && (indexText.length() > 0)){
                    index = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, indexText));
                }
                return new amara.applications.ingame.entitysystem.components.players.PlayerIndexComponent(index);
            }
        });
        //shop
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent>("shopItems"){

            @Override
            public amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent construct(EntityWorld entityWorld, Element element){
                String[] itemTemplateNames = new String[0];
                String itemTemplateNamesText = element.getText();
                if(itemTemplateNamesText != null){
                    itemTemplateNames = itemTemplateNamesText.split(",");
                    for(int i=0;i<itemTemplateNames.length;i++){
                        itemTemplateNames[i] = xmlTemplateManager.parseValue(entityWorld, itemTemplateNames[i]);
                    }
                }
                return new amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent(itemTemplateNames);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent>("shopRange"){

            @Override
            public amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent construct(EntityWorld entityWorld, Element element){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, rangeText));
                }
                return new amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent(range);
            }
        });
        //spawns
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnApplyAsRespawnTransformComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnApplyAsRespawnTransformComponent>("spawnApplyAsRespawnTransform"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnApplyAsRespawnTransformComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnApplyAsRespawnTransformComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnAttackMoveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnAttackMoveComponent>("spawnAttackMove"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnAttackMoveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnAttackMoveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnMovementAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spawns.SpawnMovementAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnMovementAnimationComponent>("spawnMovementAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnMovementAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnMovementAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnMovementRelativeDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnMovementRelativeDirectionComponent>("spawnMovementRelativeDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnMovementRelativeDirectionComponent construct(EntityWorld entityWorld, Element element){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, angle_DegreesText));
                }
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnMovementRelativeDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnMovementSpeedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spawns.SpawnMovementSpeedComponent.class.getDeclaredField("speed"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnMovementSpeedComponent>("spawnMovementSpeed"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnMovementSpeedComponent construct(EntityWorld entityWorld, Element element){
                float speed = 0;
                String speedText = element.getText();
                if((speedText != null) && (speedText.length() > 0)){
                    speed = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, speedText));
                }
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnMovementSpeedComponent(speed);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnMoveToTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnMoveToTargetComponent>("spawnMoveToTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnMoveToTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnMoveToTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnRedirectReceivedBountiesComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnRedirectReceivedBountiesComponent>("spawnRedirectReceivedBounties"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnRedirectReceivedBountiesComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnRedirectReceivedBountiesComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnRelativeDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnRelativeDirectionComponent>("spawnRelativeDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnRelativeDirectionComponent construct(EntityWorld entityWorld, Element element){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, angle_DegreesText));
                }
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnRelativeDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnRelativePositionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spawns.SpawnRelativePositionComponent>("spawnRelativePosition"){

            @Override
            public amara.applications.ingame.entitysystem.components.spawns.SpawnRelativePositionComponent construct(EntityWorld entityWorld, Element element){
                String[] positionCoordinates = element.getText().split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                return new amara.applications.ingame.entitysystem.components.spawns.SpawnRelativePositionComponent(position);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spawns.SpawnTemplateComponent.class);
        //specials
        //erika
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.specials.erika.ErikaPassiveEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.specials.erika.ErikaPassiveEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.specials.erika.ErikaPassiveEffectTriggersComponent>("erikaPassiveEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.specials.erika.ErikaPassiveEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.specials.erika.ErikaPassiveEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.specials.erika.TriggerErikaPassiveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.specials.erika.TriggerErikaPassiveComponent>("triggerErikaPassive"){

            @Override
            public amara.applications.ingame.entitysystem.components.specials.erika.TriggerErikaPassiveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.specials.erika.TriggerErikaPassiveComponent();
            }
        });
        //spells
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent.class.getDeclaredField("casterEntityID"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent.class.getDeclaredField("castedSpellEntityID"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent>("applyCastedSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent construct(EntityWorld entityWorld, Element element){
                int casterEntityID = 0;
                String casterEntityIDText = element.getAttributeValue("casterEntityID");
                if((casterEntityIDText != null) && (casterEntityIDText.length() > 0)){
                    casterEntityID = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, casterEntityIDText));
                }
                int castedSpellEntityID = 0;
                String castedSpellEntityIDText = element.getAttributeValue("castedSpellEntityID");
                if((castedSpellEntityIDText != null) && (castedSpellEntityIDText.length() > 0)){
                    castedSpellEntityID = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, castedSpellEntityIDText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.ApplyCastedSpellComponent(casterEntityID, castedSpellEntityID);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.BaseCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.BaseCooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.BaseCooldownComponent>("baseCooldown"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.BaseCooldownComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.BaseCooldownComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent>("castAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.spells.CastAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastCancelableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CastCancelableComponent>("castCancelable"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CastCancelableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.CastCancelableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastCancelActionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CastCancelActionComponent>("castCancelAction"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CastCancelActionComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.CastCancelActionComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.CastDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CastDurationComponent>("castDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CastDurationComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.CastDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastTurnToTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CastTurnToTargetComponent>("castTurnToTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CastTurnToTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.CastTurnToTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CastTypeComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.CooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.CooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.CooldownComponent>("cooldown"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.CooldownComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.CooldownComponent(duration);
            }
        });
        //indicators
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent.class.getDeclaredField("x"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent.class.getDeclaredField("y"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent.class.getDeclaredField("radius"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent>("circularIndicator"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent construct(EntityWorld entityWorld, Element element){
                float x = 0;
                String xText = element.getAttributeValue("x");
                if((xText != null) && (xText.length() > 0)){
                    x = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, xText));
                }
                float y = 0;
                String yText = element.getAttributeValue("y");
                if((yText != null) && (yText.length() > 0)){
                    y = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, yText));
                }
                float radius = 0;
                String radiusText = element.getAttributeValue("radius");
                if((radiusText != null) && (radiusText.length() > 0)){
                    radius = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, radiusText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.indicators.CircularIndicatorComponent(x, y, radius);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent.class.getDeclaredField("x"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent.class.getDeclaredField("y"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent.class.getDeclaredField("width"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent.class.getDeclaredField("height"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent>("linearIndicator"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent construct(EntityWorld entityWorld, Element element){
                float x = 0;
                String xText = element.getAttributeValue("x");
                if((xText != null) && (xText.length() > 0)){
                    x = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, xText));
                }
                float y = 0;
                String yText = element.getAttributeValue("y");
                if((yText != null) && (yText.length() > 0)){
                    y = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, yText));
                }
                float width = 0;
                String widthText = element.getAttributeValue("width");
                if((widthText != null) && (widthText.length() > 0)){
                    width = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, widthText));
                }
                float height = 0;
                String heightText = element.getAttributeValue("height");
                if((heightText != null) && (heightText.length() > 0)){
                    height = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, heightText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.indicators.LinearIndicatorComponent(x, y, width, height);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent>("instantEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.LinkedCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.LinkedCooldownComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.LinkedCooldownComponent>("linkedCooldown"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.LinkedCooldownComponent construct(EntityWorld entityWorld, Element element){
                int sourceEntity = createChildEntity(entityWorld, element, 0, "sourceEntity");
                return new amara.applications.ingame.entitysystem.components.spells.LinkedCooldownComponent(sourceEntity);
            }
        });
        //placeholders
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent>("sourceMovementDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent construct(EntityWorld entityWorld, Element element){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, angle_DegreesText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent>("targetedMovementDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent construct(EntityWorld entityWorld, Element element){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, angle_DegreesText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent>("targetedMovementTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent>("teleportToTargetPosition"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.TowardsSourceMovementDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.TowardsSourceMovementDirectionComponent>("towardsSourceMovementDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.TowardsSourceMovementDirectionComponent construct(EntityWorld entityWorld, Element element){
                float angle_Degrees = 0;
                String angle_DegreesText = element.getText();
                if((angle_DegreesText != null) && (angle_DegreesText.length() > 0)){
                    angle_Degrees = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, angle_DegreesText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.TowardsSourceMovementDirectionComponent(angle_Degrees);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent>("triggerCastedSpellEffects"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.RangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.RangeComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.RangeComponent>("range"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.RangeComponent construct(EntityWorld entityWorld, Element element){
                float distange = 0;
                String distangeText = element.getText();
                if((distangeText != null) && (distangeText.length() > 0)){
                    distange = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distangeText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.RangeComponent(distange);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent>("remainingCooldown"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent.class.getDeclaredField("indicatorEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent>("spellIndicator"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent construct(EntityWorld entityWorld, Element element){
                int indicatorEntity = createChildEntity(entityWorld, element, 0, "indicatorEntity");
                return new amara.applications.ingame.entitysystem.components.spells.SpellIndicatorComponent(indicatorEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent>("spellPassives"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent construct(EntityWorld entityWorld, Element element){
                int[] passiveEntities = createChildEntities(entityWorld, element, 0, "passiveEntities");
                return new amara.applications.ingame.entitysystem.components.spells.SpellPassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellRequiredLevelComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellRequiredLevelComponent>("spellRequiredLevel"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellRequiredLevelComponent construct(EntityWorld entityWorld, Element element){
                int level = 0;
                String levelText = element.getText();
                if((levelText != null) && (levelText.length() > 0)){
                    level = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, levelText));
                }
                return new amara.applications.ingame.entitysystem.components.spells.SpellRequiredLevelComponent(level);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent>("spellTargetRules"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent construct(EntityWorld entityWorld, Element element){
                int targetRulesEntity = createChildEntity(entityWorld, element, 0, "targetRulesEntity");
                return new amara.applications.ingame.entitysystem.components.spells.SpellTargetRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellUpgradesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.SpellUpgradesComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellUpgradesComponent>("spellUpgrades"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellUpgradesComponent construct(EntityWorld entityWorld, Element element){
                int[] spellsEntities = createChildEntities(entityWorld, element, 0, "spellsEntities");
                return new amara.applications.ingame.entitysystem.components.spells.SpellUpgradesComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.SpellVisualisationComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.SpellVisualisationComponent>("spellVisualisation"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.SpellVisualisationComponent construct(EntityWorld entityWorld, Element element){
                String name = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.spells.SpellVisualisationComponent(name);
            }
        });
        //triggers
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent>("castedEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent.class.getDeclaredField("spellEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent>("castedSpell"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent construct(EntityWorld entityWorld, Element element){
                int spellEntity = createChildEntity(entityWorld, element, 0, "spellEntity");
                return new amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent(spellEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent.class.getDeclaredField("spellEffectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent>("spellEffectParent"){

            @Override
            public amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent construct(EntityWorld entityWorld, Element element){
                int spellEffectEntity = createChildEntity(entityWorld, element, 0, "spellEffectEntity");
                return new amara.applications.ingame.entitysystem.components.spells.triggers.SpellEffectParentComponent(spellEffectEntity);
            }
        });
        //targets
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.targets.AcceptAlliesComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.targets.AcceptAlliesComponent>("acceptAllies"){

            @Override
            public amara.applications.ingame.entitysystem.components.targets.AcceptAlliesComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.targets.AcceptAlliesComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.targets.AcceptEnemiesComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.targets.AcceptEnemiesComponent>("acceptEnemies"){

            @Override
            public amara.applications.ingame.entitysystem.components.targets.AcceptEnemiesComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.targets.AcceptEnemiesComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.targets.RequireCharacterComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.targets.RequireCharacterComponent>("requireCharacter"){

            @Override
            public amara.applications.ingame.entitysystem.components.targets.RequireCharacterComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.targets.RequireCharacterComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.targets.RequireProjectileComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.targets.RequireProjectileComponent>("requireProjectile"){

            @Override
            public amara.applications.ingame.entitysystem.components.targets.RequireProjectileComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.targets.RequireProjectileComponent();
            }
        });
        //units
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AggroPriorityComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AggroPriorityComponent>("aggroPriority"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AggroPriorityComponent construct(EntityWorld entityWorld, Element element){
                int priority = 0;
                String priorityText = element.getText();
                if((priorityText != null) && (priorityText.length() > 0)){
                    priority = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, priorityText));
                }
                return new amara.applications.ingame.entitysystem.components.units.AggroPriorityComponent(priority);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AggroResetTimerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AggroResetTimerComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AggroResetTimerComponent>("aggroResetTimer"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AggroResetTimerComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.AggroResetTimerComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AggroTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AggroTargetComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AggroTargetComponent>("aggroTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AggroTargetComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.units.AggroTargetComponent(targetEntity);
            }
        });
        //animations
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent>("autoAttackAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.animations.DeathAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.animations.DeathAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.animations.DeathAnimationComponent>("deathAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.animations.DeathAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.units.animations.DeathAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent>("idleAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent>("walkAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent(animationEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AttackMoveComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AttackMoveComponent.class.getDeclaredField("targetEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AttackMoveComponent>("attackMove"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AttackMoveComponent construct(EntityWorld entityWorld, Element element){
                int targetEntity = createChildEntity(entityWorld, element, 0, "targetEntity");
                return new amara.applications.ingame.entitysystem.components.units.AttackMoveComponent(targetEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AttributesPerLevelComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AttributesPerLevelComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AttributesPerLevelComponent>("attributesPerLevel"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AttributesPerLevelComponent construct(EntityWorld entityWorld, Element element){
                int bonusAttributesEntity = createChildEntity(entityWorld, element, 0, "bonusAttributesEntity");
                return new amara.applications.ingame.entitysystem.components.units.AttributesPerLevelComponent(bonusAttributesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AutoAggroComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AutoAggroComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AutoAggroComponent>("autoAggro"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AutoAggroComponent construct(EntityWorld entityWorld, Element element){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, rangeText));
                }
                return new amara.applications.ingame.entitysystem.components.units.AutoAggroComponent(range);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.AutoAttackComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.AutoAttackComponent.class.getDeclaredField("autoAttackEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.AutoAttackComponent>("autoAttack"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.AutoAttackComponent construct(EntityWorld entityWorld, Element element){
                int autoAttackEntity = createChildEntity(entityWorld, element, 0, "autoAttackEntity");
                return new amara.applications.ingame.entitysystem.components.units.AutoAttackComponent(autoAttackEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent.class.getDeclaredField("bonusAttributesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent>("baseAttributes"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent construct(EntityWorld entityWorld, Element element){
                int bonusAttributesEntity = createChildEntity(entityWorld, element, 0, "bonusAttributesEntity");
                return new amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent(bonusAttributesEntity);
            }
        });
        //bounties
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent>("bountyBuff"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent construct(EntityWorld entityWorld, Element element){
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                float duration = 0;
                String durationText = element.getAttributeValue("duration");
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyBuffComponent(buffEntity, duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyCharacterKillComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyCharacterKillComponent>("bountyCharacterKill"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyCharacterKillComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyCharacterKillComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyCreepScoreComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyCreepScoreComponent>("bountyCreepScore"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyCreepScoreComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyCreepScoreComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyExperienceComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyExperienceComponent>("bountyExperience"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyExperienceComponent construct(EntityWorld entityWorld, Element element){
                int experience = 0;
                String experienceText = element.getText();
                if((experienceText != null) && (experienceText.length() > 0)){
                    experience = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, experienceText));
                }
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyExperienceComponent(experience);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyGoldComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyGoldComponent>("bountyGold"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyGoldComponent construct(EntityWorld entityWorld, Element element){
                float gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, goldText));
                }
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyGoldComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyItemsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.bounties.BountyItemsComponent.class.getDeclaredField("itemEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyItemsComponent>("bountyItems"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyItemsComponent construct(EntityWorld entityWorld, Element element){
                int[] itemEntities = createChildEntities(entityWorld, element, 0, "itemEntities");
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyItemsComponent(itemEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.BountyRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.bounties.BountyRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.BountyRulesComponent>("bountyRules"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.BountyRulesComponent construct(EntityWorld entityWorld, Element element){
                int targetRulesEntity = createChildEntity(entityWorld, element, 0, "targetRulesEntity");
                return new amara.applications.ingame.entitysystem.components.units.bounties.BountyRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.bounties.RedirectReceivedBountiesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.bounties.RedirectReceivedBountiesComponent.class.getDeclaredField("receiverEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.bounties.RedirectReceivedBountiesComponent>("redirectReceivedBounties"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.bounties.RedirectReceivedBountiesComponent construct(EntityWorld entityWorld, Element element){
                int receiverEntity = createChildEntity(entityWorld, element, 0, "receiverEntity");
                return new amara.applications.ingame.entitysystem.components.units.bounties.RedirectReceivedBountiesComponent(receiverEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.BountyComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.BountyComponent.class.getDeclaredField("bountyEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.BountyComponent>("bounty"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.BountyComponent construct(EntityWorld entityWorld, Element element){
                int bountyEntity = createChildEntity(entityWorld, element, 0, "bountyEntity");
                return new amara.applications.ingame.entitysystem.components.units.BountyComponent(bountyEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.CampComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.CampComponent.class.getDeclaredField("campEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.CampComponent>("camp"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.CampComponent construct(EntityWorld entityWorld, Element element){
                int campEntity = createChildEntity(entityWorld, element, 0, "campEntity");
                String[] positionCoordinates = element.getAttributeValue("position").split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                String[] directionCoordinates = element.getAttributeValue("direction").split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.applications.ingame.entitysystem.components.units.CampComponent(campEntity, position, direction);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.CampResetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.CampResetComponent>("campReset"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.CampResetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.CampResetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent>("castSpellOnCooldownWhileAttacking"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent construct(EntityWorld entityWorld, Element element){
                String[] spellIndicesParts = element.getText().split(",");
                int[] spellIndices = new int[spellIndicesParts.length];
                for(int i=0;i<spellIndices.length;i++){
                    spellIndices[i] = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, element.getText()));
                }
                return new amara.applications.ingame.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent(spellIndices);
            }
        });
        //crowdcontrol
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindableComponent>("isBindable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent>("isBinded"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupableComponent>("isKnockupable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class.getDeclaredField("knockupEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent>("isKnockuped"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent construct(EntityWorld entityWorld, Element element){
                int knockupEntity = createChildEntity(entityWorld, element, 0, "knockupEntity");
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent(knockupEntity, remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencableComponent>("isSilencable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencedComponent>("isSilenced"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencedComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnableComponent>("isStunnable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent>("isStunned"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.CurrentActionEffectCastsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.CurrentActionEffectCastsComponent.class.getDeclaredField("effectCastEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.CurrentActionEffectCastsComponent>("currentActionEffectCasts"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.CurrentActionEffectCastsComponent construct(EntityWorld entityWorld, Element element){
                int[] effectCastEntities = createChildEntities(entityWorld, element, 0, "effectCastEntities");
                return new amara.applications.ingame.entitysystem.components.units.CurrentActionEffectCastsComponent(effectCastEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent>("currentPassives"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent construct(EntityWorld entityWorld, Element element){
                int[] passiveEntities = createChildEntities(entityWorld, element, 0, "passiveEntities");
                return new amara.applications.ingame.entitysystem.components.units.CurrentPassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent.class.getDeclaredField("firstDamageTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent.class.getDeclaredField("lastDamageTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        //effecttriggers
        //targets
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent.class.getDeclaredField("buffEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent>("buffTargetsTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent construct(EntityWorld entityWorld, Element element){
                int buffEntity = createChildEntity(entityWorld, element, 0, "buffEntity");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent(buffEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent>("casterTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent.class.getDeclaredField("targetEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent>("customTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent construct(EntityWorld entityWorld, Element element){
                int[] targetEntities = createChildEntities(entityWorld, element, 0, "targetEntities");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent(targetEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent>("sourceTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent>("targetTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TeamTargetComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TeamTargetComponent.class.getDeclaredField("teamEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TeamTargetComponent>("teamTarget"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TeamTargetComponent construct(EntityWorld entityWorld, Element element){
                int teamEntity = createChildEntity(entityWorld, element, 0, "teamEntity");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.TeamTargetComponent(teamEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent.class.getDeclaredField("conditionEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent>("triggerConditions"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent construct(EntityWorld entityWorld, Element element){
                int[] conditionEntities = createChildEntities(entityWorld, element, 0, "conditionEntities");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent(conditionEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent>("triggerDelay"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerDelayComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent.class.getDeclaredField("effectEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent>("triggeredEffect"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent construct(EntityWorld entityWorld, Element element){
                int effectEntity = createChildEntity(entityWorld, element, 0, "effectEntity");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent(effectEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent>("triggerOnCancel"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnceComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnceComponent>("triggerOnce"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnceComponent construct(EntityWorld entityWorld, Element element){
                boolean removeEntity = false;
                String removeEntityText = element.getText();
                if((removeEntityText != null) && (removeEntityText.length() > 0)){
                    removeEntity = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, removeEntityText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerOnceComponent(removeEntity);
            }
        });
        //triggers
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ActionCancelledTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ActionCancelledTriggerComponent>("actionCancelledTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ActionCancelledTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ActionCancelledTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent>("castingFinishedTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent>("collisionTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent>("damageTakenTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent construct(EntityWorld entityWorld, Element element){
                boolean physicalDamage = false;
                String physicalDamageText = element.getAttributeValue("physicalDamage");
                if((physicalDamageText != null) && (physicalDamageText.length() > 0)){
                    physicalDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, physicalDamageText));
                }
                boolean magicDamage = false;
                String magicDamageText = element.getAttributeValue("magicDamage");
                if((magicDamageText != null) && (magicDamageText.length() > 0)){
                    magicDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, magicDamageText));
                }
                boolean trueDamage = false;
                String trueDamageText = element.getAttributeValue("trueDamage");
                if((trueDamageText != null) && (trueDamageText.length() > 0)){
                    trueDamage = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, trueDamageText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DamageTakenTriggerComponent(physicalDamage, magicDamage, trueDamage);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent>("deathTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent>("instantTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ObjectiveFinishedTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ObjectiveFinishedTriggerComponent>("objectiveFinishedTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ObjectiveFinishedTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ObjectiveFinishedTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent.class.getDeclaredField("intervalDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent>("repeatingTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent construct(EntityWorld entityWorld, Element element){
                float intervalDuration = 0;
                String intervalDurationText = element.getText();
                if((intervalDurationText != null) && (intervalDurationText.length() > 0)){
                    intervalDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, intervalDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent(intervalDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent>("repeatingTriggerCounter"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent construct(EntityWorld entityWorld, Element element){
                int counter = 0;
                String counterText = element.getText();
                if((counterText != null) && (counterText.length() > 0)){
                    counter = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, counterText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent(counter);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ShopUsageTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ShopUsageTriggerComponent>("shopUsageTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ShopUsageTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.ShopUsageTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent.class.getDeclaredField("stacks"), componentFieldSerializer_Stacks);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent>("stacksReachedTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent construct(EntityWorld entityWorld, Element element){
                int stacks = 0;
                String stacksText = element.getText();
                if((stacksText != null) && (stacksText.length() > 0)){
                    stacks = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, stacksText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.StacksReachedTriggerComponent(stacks);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent>("targetReachedTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent>("teamDeathTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TeamDeathTriggerComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent>("timeSinceLastRepeatTrigger"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent.class.getDeclaredField("sourceEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent>("triggerSource"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent construct(EntityWorld entityWorld, Element element){
                int sourceEntity = createChildEntity(entityWorld, element, 0, "sourceEntity");
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent(sourceEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent>("triggerTemporary"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.ExperienceComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.ExperienceComponent>("experience"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.ExperienceComponent construct(EntityWorld entityWorld, Element element){
                int experience = 0;
                String experienceText = element.getText();
                if((experienceText != null) && (experienceText.length() > 0)){
                    experience = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, experienceText));
                }
                return new amara.applications.ingame.entitysystem.components.units.ExperienceComponent(experience);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.GoldComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.GoldComponent>("gold"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.GoldComponent construct(EntityWorld entityWorld, Element element){
                float gold = 0;
                String goldText = element.getText();
                if((goldText != null) && (goldText.length() > 0)){
                    gold = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, goldText));
                }
                return new amara.applications.ingame.entitysystem.components.units.GoldComponent(gold);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.HealthBarStyleComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.InCombatComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.InCombatComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.InCombatComponent>("inCombat"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.InCombatComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.InCombatComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.InnateWalkDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.InnateWalkDirectionComponent>("innateWalkDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.InnateWalkDirectionComponent construct(EntityWorld entityWorld, Element element){
                String[] directionCoordinates = element.getText().split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.applications.ingame.entitysystem.components.units.InnateWalkDirectionComponent(direction);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IntersectionRulesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.IntersectionRulesComponent.class.getDeclaredField("targetRulesEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IntersectionRulesComponent>("intersectionRules"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IntersectionRulesComponent construct(EntityWorld entityWorld, Element element){
                int targetRulesEntity = createChildEntity(entityWorld, element, 0, "targetRulesEntity");
                return new amara.applications.ingame.entitysystem.components.units.IntersectionRulesComponent(targetRulesEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsAliveComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsAliveComponent>("isAlive"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsAliveComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsAliveComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsAlwaysVisibleComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsAlwaysVisibleComponent>("isAlwaysVisible"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsAlwaysVisibleComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsAlwaysVisibleComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsCastingComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.IsCastingComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsCastingComponent>("isCasting"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsCastingComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                boolean isCancelable = false;
                String isCancelableText = element.getAttributeValue("isCancelable");
                if((isCancelableText != null) && (isCancelableText.length() > 0)){
                    isCancelable = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, isCancelableText));
                }
                return new amara.applications.ingame.entitysystem.components.units.IsCastingComponent(remainingDuration, isCancelable);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsHiddenAreaComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsHiddenAreaComponent>("isHiddenArea"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsHiddenAreaComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsHiddenAreaComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsHoveredComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsHoveredComponent>("isHovered"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsHoveredComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsHoveredComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsInHiddenAreaComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.IsInHiddenAreaComponent.class.getDeclaredField("hiddenAreaEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsInHiddenAreaComponent>("isInHiddenArea"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsInHiddenAreaComponent construct(EntityWorld entityWorld, Element element){
                int hiddenAreaEntity = createChildEntity(entityWorld, element, 0, "hiddenAreaEntity");
                return new amara.applications.ingame.entitysystem.components.units.IsInHiddenAreaComponent(hiddenAreaEntity);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsRespawnableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsRespawnableComponent>("isRespawnable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsRespawnableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsRespawnableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsStealthedComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsStealthedComponent>("isStealthed"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsStealthedComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsStealthedComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsTargetableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsTargetableComponent>("isTargetable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsTargetableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsTargetableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsVisibleForTeamsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsVisibleForTeamsComponent>("isVisibleForTeams"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsVisibleForTeamsComponent construct(EntityWorld entityWorld, Element element){
                String[] teamVisionsParts = element.getText().split(",");
                boolean[] teamVisions = new boolean[teamVisionsParts.length];
                for(int i=0;i<teamVisions.length;i++){
                    teamVisions[i] = Boolean.parseBoolean(xmlTemplateManager.parseValue(entityWorld, element.getText()));
                }
                return new amara.applications.ingame.entitysystem.components.units.IsVisibleForTeamsComponent(teamVisions);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.IsVulnerableComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.IsVulnerableComponent>("isVulnerable"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.IsVulnerableComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.IsVulnerableComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent>("learnableSpells"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellsEntities = createChildEntities(entityWorld, element, 0, "spellsEntities");
                return new amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.LevelComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.LevelComponent>("level"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.LevelComponent construct(EntityWorld entityWorld, Element element){
                int level = 0;
                String levelText = element.getText();
                if((levelText != null) && (levelText.length() > 0)){
                    level = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, levelText));
                }
                return new amara.applications.ingame.entitysystem.components.units.LevelComponent(level);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.LifetimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.LifetimeComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.LifetimeComponent>("lifetime"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.LifetimeComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.LifetimeComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.LocalAvoidanceWalkComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.LocalAvoidanceWalkComponent>("localAvoidanceWalk"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.LocalAvoidanceWalkComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.LocalAvoidanceWalkComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.MapSpellsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.MapSpellsComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.MapSpellsComponent>("mapSpells"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.MapSpellsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellsEntities = createChildEntities(entityWorld, element, 0, "spellsEntities");
                return new amara.applications.ingame.entitysystem.components.units.MapSpellsComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.MaximumAggroRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.MaximumAggroRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.MaximumAggroRangeComponent>("maximumAggroRange"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.MaximumAggroRangeComponent construct(EntityWorld entityWorld, Element element){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, rangeText));
                }
                return new amara.applications.ingame.entitysystem.components.units.MaximumAggroRangeComponent(range);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.MovementComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.MovementComponent.class.getDeclaredField("movementEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.MovementComponent>("movement"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.MovementComponent construct(EntityWorld entityWorld, Element element){
                int movementEntity = createChildEntity(entityWorld, element, 0, "movementEntity");
                return new amara.applications.ingame.entitysystem.components.units.MovementComponent(movementEntity);
            }
        });
        //passives
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent>("passiveAddedEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent.class.getDeclaredField("effectTriggerEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent>("passiveRemovedEffectTriggers"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent construct(EntityWorld entityWorld, Element element){
                int[] effectTriggerEntities = createChildEntities(entityWorld, element, 0, "effectTriggerEntities");
                return new amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent(effectTriggerEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.PassivesComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.PassivesComponent.class.getDeclaredField("passiveEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.PassivesComponent>("passives"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.PassivesComponent construct(EntityWorld entityWorld, Element element){
                int[] passiveEntities = createChildEntities(entityWorld, element, 0, "passiveEntities");
                return new amara.applications.ingame.entitysystem.components.units.PassivesComponent(passiveEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.PopupComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.PopupComponent>("popup"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.PopupComponent construct(EntityWorld entityWorld, Element element){
                String text = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.units.PopupComponent(text);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.ReactionComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.ReactionComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.ReactionComponent>("reaction"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.ReactionComponent construct(EntityWorld entityWorld, Element element){
                String reaction = xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("reaction"));
                float remainingDuration = 0;
                String remainingDurationText = element.getAttributeValue("remainingDuration");
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.ReactionComponent(reaction, remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.RemainingAggroResetDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.RemainingAggroResetDurationComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.RemainingAggroResetDurationComponent>("remainingAggroResetDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.RemainingAggroResetDurationComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.RemainingAggroResetDurationComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.RespawnDirectionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.RespawnDirectionComponent>("respawnDirection"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.RespawnDirectionComponent construct(EntityWorld entityWorld, Element element){
                String[] directionCoordinates = element.getText().split(",");
                float directionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[0]));
                float directionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, directionCoordinates[1]));
                Vector2f direction = new Vector2f(directionX, directionY);
                return new amara.applications.ingame.entitysystem.components.units.RespawnDirectionComponent(direction);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.RespawnPositionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.RespawnPositionComponent>("respawnPosition"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.RespawnPositionComponent construct(EntityWorld entityWorld, Element element){
                String[] positionCoordinates = element.getText().split(",");
                float positionX = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[0]));
                float positionY = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[1]));
                Vector2f position = new Vector2f(positionX, positionY);
                return new amara.applications.ingame.entitysystem.components.units.RespawnPositionComponent(position);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.ScoreComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.ScoreComponent.class.getDeclaredField("scoreEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.ScoreComponent>("score"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.ScoreComponent construct(EntityWorld entityWorld, Element element){
                int scoreEntity = createChildEntity(entityWorld, element, 0, "scoreEntity");
                return new amara.applications.ingame.entitysystem.components.units.ScoreComponent(scoreEntity);
            }
        });
        //scores
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.scores.CharacterAssistsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.scores.CharacterAssistsComponent>("characterAssists"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.scores.CharacterAssistsComponent construct(EntityWorld entityWorld, Element element){
                int assists = 0;
                String assistsText = element.getText();
                if((assistsText != null) && (assistsText.length() > 0)){
                    assists = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, assistsText));
                }
                return new amara.applications.ingame.entitysystem.components.units.scores.CharacterAssistsComponent(assists);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.scores.CharacterKillsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.scores.CharacterKillsComponent>("characterKills"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.scores.CharacterKillsComponent construct(EntityWorld entityWorld, Element element){
                int kills = 0;
                String killsText = element.getText();
                if((killsText != null) && (killsText.length() > 0)){
                    kills = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, killsText));
                }
                return new amara.applications.ingame.entitysystem.components.units.scores.CharacterKillsComponent(kills);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.scores.CreepScoreComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.scores.CreepScoreComponent>("creepScore"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.scores.CreepScoreComponent construct(EntityWorld entityWorld, Element element){
                int kills = 0;
                String killsText = element.getText();
                if((killsText != null) && (killsText.length() > 0)){
                    kills = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, killsText));
                }
                return new amara.applications.ingame.entitysystem.components.units.scores.CreepScoreComponent(kills);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.scores.DeathsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.scores.DeathsComponent>("deaths"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.scores.DeathsComponent construct(EntityWorld entityWorld, Element element){
                int deaths = 0;
                String deathsText = element.getText();
                if((deathsText != null) && (deathsText.length() > 0)){
                    deaths = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, deathsText));
                }
                return new amara.applications.ingame.entitysystem.components.units.scores.DeathsComponent(deaths);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.SetNewCampCombatSpellsOnCooldownComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.SetNewCampCombatSpellsOnCooldownComponent.class.getDeclaredField("cooldowns"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.SetNewCampCombatSpellsOnCooldownComponent>("setNewCampCombatSpellsOnCooldown"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.SetNewCampCombatSpellsOnCooldownComponent construct(EntityWorld entityWorld, Element element){
                String[] spellIndicesParts = element.getAttributeValue("spellIndices").split(",");
                int[] spellIndices = new int[spellIndicesParts.length];
                for(int i=0;i<spellIndices.length;i++){
                    spellIndices[i] = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("spellIndices")));
                }
                String[] cooldownsParts = element.getAttributeValue("cooldowns").split(",");
                float[] cooldowns = new float[cooldownsParts.length];
                for(int i=0;i<cooldowns.length;i++){
                    cooldowns[i] = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, element.getAttributeValue("cooldowns")));
                }
                return new amara.applications.ingame.entitysystem.components.units.SetNewCampCombatSpellsOnCooldownComponent(spellIndices, cooldowns);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.ShopGoldExpensesComponent.class);
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.SightRangeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.SightRangeComponent.class.getDeclaredField("range"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.SightRangeComponent>("sightRange"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.SightRangeComponent construct(EntityWorld entityWorld, Element element){
                float range = 0;
                String rangeText = element.getText();
                if((rangeText != null) && (rangeText.length() > 0)){
                    range = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, rangeText));
                }
                return new amara.applications.ingame.entitysystem.components.units.SightRangeComponent(range);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.SpellsComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.SpellsComponent.class.getDeclaredField("spellsEntities"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.SpellsComponent>("spells"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.SpellsComponent construct(EntityWorld entityWorld, Element element){
                int[] spellsEntities = createChildEntities(entityWorld, element, 0, "spellsEntities");
                return new amara.applications.ingame.entitysystem.components.units.SpellsComponent(spellsEntities);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent>("spellsUpgradePoints"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent construct(EntityWorld entityWorld, Element element){
                int upgradePoints = 0;
                String upgradePointsText = element.getText();
                if((upgradePointsText != null) && (upgradePointsText.length() > 0)){
                    upgradePoints = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, upgradePointsText));
                }
                return new amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent(upgradePoints);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.TeamComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.TeamComponent.class.getDeclaredField("teamEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.TeamComponent>("team"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.TeamComponent construct(EntityWorld entityWorld, Element element){
                int teamEntity = createChildEntity(entityWorld, element, 0, "teamEntity");
                return new amara.applications.ingame.entitysystem.components.units.TeamComponent(teamEntity);
            }
        });
        //types
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent>("isCharacter"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent>("isMinion"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent>("isMonster"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.types.IsProjectileComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.types.IsProjectileComponent>("isProjectile"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.types.IsProjectileComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.types.IsProjectileComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent>("isStructure"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.WaitingToRespawnComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.WaitingToRespawnComponent.class.getDeclaredField("remainingDuration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.WaitingToRespawnComponent>("waitingToRespawn"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.WaitingToRespawnComponent construct(EntityWorld entityWorld, Element element){
                float remainingDuration = 0;
                String remainingDurationText = element.getText();
                if((remainingDurationText != null) && (remainingDurationText.length() > 0)){
                    remainingDuration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, remainingDurationText));
                }
                return new amara.applications.ingame.entitysystem.components.units.WaitingToRespawnComponent(remainingDuration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.units.WalkStepDistanceComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.units.WalkStepDistanceComponent.class.getDeclaredField("distance"), componentFieldSerializer_Distance);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.units.WalkStepDistanceComponent>("walkStepDistance"){

            @Override
            public amara.applications.ingame.entitysystem.components.units.WalkStepDistanceComponent construct(EntityWorld entityWorld, Element element){
                float distance = 0;
                String distanceText = element.getText();
                if((distanceText != null) && (distanceText.length() > 0)){
                    distance = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, distanceText));
                }
                return new amara.applications.ingame.entitysystem.components.units.WalkStepDistanceComponent(distance);
            }
        });
        //visuals
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.AnimationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.visuals.AnimationComponent.class.getDeclaredField("animationEntity"), componentFieldSerializer_Entity);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.AnimationComponent>("animation"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.AnimationComponent construct(EntityWorld entityWorld, Element element){
                int animationEntity = createChildEntity(entityWorld, element, 0, "animationEntity");
                return new amara.applications.ingame.entitysystem.components.visuals.AnimationComponent(animationEntity);
            }
        });
        //animations
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent>("freezeAfterPlaying"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.visuals.animations.FreezeAfterPlayingComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.animations.LoopDurationComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.visuals.animations.LoopDurationComponent.class.getDeclaredField("duration"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.animations.LoopDurationComponent>("loopDuration"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.animations.LoopDurationComponent construct(EntityWorld entityWorld, Element element){
                float duration = 0;
                String durationText = element.getText();
                if((durationText != null) && (durationText.length() > 0)){
                    duration = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, durationText));
                }
                return new amara.applications.ingame.entitysystem.components.visuals.animations.LoopDurationComponent(duration);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class);
        try{
            ComponentSerializer.registerFieldSerializer(amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent.class.getDeclaredField("passedTime"), componentFieldSerializer_Timer);
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent>("passedLoopTime"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent construct(EntityWorld entityWorld, Element element){
                float passedTime = 0;
                String passedTimeText = element.getText();
                if((passedTimeText != null) && (passedTimeText.length() > 0)){
                    passedTime = Float.parseFloat(xmlTemplateManager.parseValue(entityWorld, passedTimeText));
                }
                return new amara.applications.ingame.entitysystem.components.visuals.animations.PassedLoopTimeComponent(passedTime);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.animations.RemainingLoopsComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.animations.RemainingLoopsComponent>("remainingLoops"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.animations.RemainingLoopsComponent construct(EntityWorld entityWorld, Element element){
                int loopsCount = 0;
                String loopsCountText = element.getText();
                if((loopsCountText != null) && (loopsCountText.length() > 0)){
                    loopsCount = Integer.parseInt(xmlTemplateManager.parseValue(entityWorld, loopsCountText));
                }
                return new amara.applications.ingame.entitysystem.components.visuals.animations.RemainingLoopsComponent(loopsCount);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.animations.RestartClientAnimationComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.animations.RestartClientAnimationComponent>("restartClientAnimation"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.animations.RestartClientAnimationComponent construct(EntityWorld entityWorld, Element element){
                return new amara.applications.ingame.entitysystem.components.visuals.animations.RestartClientAnimationComponent();
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.ModelComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.ModelComponent>("model"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.ModelComponent construct(EntityWorld entityWorld, Element element){
                String modelSkinPath = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.visuals.ModelComponent(modelSkinPath);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.TeamModelComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.TeamModelComponent>("teamModel"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.TeamModelComponent construct(EntityWorld entityWorld, Element element){
                String modelSkinPath = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.visuals.TeamModelComponent(modelSkinPath);
            }
        });
        bitstreamClassManager.register(amara.applications.ingame.entitysystem.components.visuals.TitleComponent.class);
        xmlTemplateManager.registerComponent(new XMLComponentConstructor<amara.applications.ingame.entitysystem.components.visuals.TitleComponent>("title"){

            @Override
            public amara.applications.ingame.entitysystem.components.visuals.TitleComponent construct(EntityWorld entityWorld, Element element){
                String title = xmlTemplateManager.parseValue(entityWorld, element.getText());
                return new amara.applications.ingame.entitysystem.components.visuals.TitleComponent(title);
            }
        });
    }
}