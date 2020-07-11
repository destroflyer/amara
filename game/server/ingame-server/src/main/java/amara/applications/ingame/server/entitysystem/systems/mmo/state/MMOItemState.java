package amara.applications.ingame.server.entitysystem.systems.mmo.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MMOItemState {
    private String name;
    private String visualisation;
    private Float flatAbilityPower;
    private Float flatAttackDamage;
    private Float flatArmor;
    private Float flatMagicResistance;
    private Float flatMaximumHealth;
    private Float percentageAttackSpeed;
    private Float percentageCooldownSpeed;
    private Float percentageCriticalChance;
    private Float percentageLifesteal;
    private Float percentageWalkSpeed;
    private Float sellableGold;
}
