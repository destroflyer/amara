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
    private float flatHealthRegeneration = 0;
    private float flatAttackDamage = 0;
    private float flatAbilityPower = 0;
    private float flatAttackSpeed = 0;
    private float percentageAttackSpeed = 0;
    private float percentageCooldownSpeed = 0;
    private float flatArmor = 0;
    private float flatMagicResistance = 0;
    private float flatWalkSpeed = 0;
    private float percentageWalkSpeed = 1;
    private float percentageCriticalChance = 0;
    private float percentageLifesteal = 0;

    public void addFlatMaximumHealth(float value){
        flatMaximumHealth += value;
    }
    
    public float getFlatMaximumHealth(){
        return flatMaximumHealth;
    }
    
    public void addFlatHealthRegeneration(float value){
        flatHealthRegeneration += value;
    }

    public float getFlatHealthRegeneration(){
        return flatHealthRegeneration;
    }

    public void addFlatAttackDamage(float value){
        flatAttackDamage += value;
    }

    public float getFlatAttackDamage(){
        return flatAttackDamage;
    }

    public void addFlatAbilityPower(float value){
        flatAbilityPower += value;
    }

    public float getFlatAbilityPower(){
        return flatAbilityPower;
    }
    
    public void addFlatAttackSpeed(float value){
        flatAttackSpeed += value;
    }

    public float getFlatAttackSpeed(){
        return flatAttackSpeed;
    }
    
    public void addPercentageAttackSpeed(float value){
        percentageAttackSpeed += value;
    }

    public float getPercentageAttackSpeed(){
        return percentageAttackSpeed;
    }

    public void addPercentageCooldownSpeed(float value){
        percentageCooldownSpeed += value;
    }

    public float getPercentageCooldownSpeed(){
        return percentageCooldownSpeed;
    }

    public void addFlatArmor(float value){
        flatArmor += value;
    }

    public float getFlatArmor(){
        return flatArmor;
    }

    public void addFlatMagicResistance(float value){
        flatMagicResistance += value;
    }

    public float getFlatMagicResistance(){
        return flatMagicResistance;
    }
    
    public void addFlatWalkSpeed(float value){
        flatWalkSpeed += value;
    }

    public float getFlatWalkSpeed(){
        return flatWalkSpeed;
    }
    
    public void multiplicatePercentageWalkSpeed(float value){
        percentageWalkSpeed *= (1 + value);
    }

    public float getPercentageWalkSpeed(){
        return percentageWalkSpeed;
    }

    public float getPercentageCriticalChance(){
        return percentageCriticalChance;
    }

    public void addPercentageCriticalChance(float value){
        percentageCriticalChance += value;
    }

    public float getPercentageLifesteal(){
        return percentageLifesteal;
    }
    
    public void addPercentageLifesteal(float value){
        percentageLifesteal += value;
    }
}
