/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.attributes;

/**
 *
 * @author Carl
 */
public class AttributeBonus{

    public AttributeBonus(){
        
    }
    float flatMaximumHealth = 0;
    float flatAttackDamage = 0;
    float flatAbilityPower = 0;
    float percentageAttackSpeed = 0;

    public void addFlatMaximumHealth(float value){
        flatMaximumHealth += value;
    }
    
    public float getFlatMaximumHealth(){
        return flatMaximumHealth;
    }

    public void addFlatAbilityPower(float value){
        flatAbilityPower += value;
    }

    public float getFlatAbilityPower(){
        return flatAbilityPower;
    }

    public void addFlatAttackDamage(float value){
        flatAttackDamage += value;
    }

    public float getFlatAttackDamage(){
        return flatAttackDamage;
    }

    public void addPercentageAttackSpeed(float value){
        percentageAttackSpeed += value;
    }

    public float getPercentageAttackSpeed(){
        return percentageAttackSpeed;
    }
}
