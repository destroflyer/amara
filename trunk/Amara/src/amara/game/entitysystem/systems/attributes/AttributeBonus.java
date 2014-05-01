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
    private float flatMaximumHealth = 0;
    private float flatAttackDamage = 0;
    private float flatAbilityPower = 0;
    private float percentageAttackSpeed = 0;
    private float flatWalkSpeed = 0;

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
    
    public void addFlatWalkSpeed(float value){
        flatWalkSpeed += value;
    }

    public float getFlatWalkSpeed(){
        return flatWalkSpeed;
    }
}
