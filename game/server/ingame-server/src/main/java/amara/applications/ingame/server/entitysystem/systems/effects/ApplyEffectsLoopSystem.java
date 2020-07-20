package amara.applications.ingame.server.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.systems.aggro.*;
import amara.applications.ingame.entitysystem.systems.effects.*;
import amara.applications.ingame.entitysystem.systems.effects.aggro.*;
import amara.applications.ingame.entitysystem.systems.effects.audio.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.stacks.*;
import amara.applications.ingame.entitysystem.systems.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.effects.damage.*;
import amara.applications.ingame.entitysystem.systems.effects.game.*;
import amara.applications.ingame.entitysystem.systems.effects.general.*;
import amara.applications.ingame.entitysystem.systems.effects.heal.*;
import amara.applications.ingame.entitysystem.systems.effects.movement.*;
import amara.applications.ingame.entitysystem.systems.effects.physics.*;
import amara.applications.ingame.entitysystem.systems.effects.players.*;
import amara.applications.ingame.entitysystem.systems.effects.popups.*;
import amara.applications.ingame.entitysystem.systems.effects.spawns.*;
import amara.applications.ingame.entitysystem.systems.effects.spells.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.units.*;
import amara.applications.ingame.entitysystem.systems.effects.vision.*;
import amara.applications.ingame.entitysystem.systems.effects.visuals.*;
import amara.applications.ingame.entitysystem.systems.specials.erika.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.applications.ingame.entitysystem.systems.units.*;
import amara.applications.ingame.server.entitysystem.systems.general.LoopSystem;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyEffectsLoopSystem extends LoopSystem {

    public ApplyEffectsLoopSystem(Map map, CastSpellQueueSystem castSpellQueueSystem) {
        calculateEffectImpactSystem = new CalculateEffectImpactSystem();
        add(calculateEffectImpactSystem);
        add(new ApplyPlayCinematicSystem());
        add(new ApplyDrawTeamAggroSystem());
        add(new ApplyPlayAudioSystem());
        add(new ApplyStopAudioSystem());
        add(new ApplyRemoveBuffsSystem());
        add(new ApplyRemoveBuffAreasSystem());
        add(new ApplyAddBuffsSystem());
        add(new ApplyAddBuffAreasSystem());
        add(new ApplyAddStacksSystem());
        add(new ApplyClearStacksSystem());
        add(new ApplyRemoveStacksSystem());
        add(new ApplyAddBindableSystem());
        add(new ApplyAddBindingSystem());
        add(new ApplyAddKnockupableSystem());
        add(new ApplyAddKnockupSystem());
        add(new ApplyAddSilencableSystem());
        add(new ApplyAddSilenceSystem());
        add(new ApplyAddStunSystem());
        add(new ApplyAddStunnableSystem());
        add(new ApplyRemoveBindableSystem());
        add(new ApplyRemoveBindingSystem());
        add(new ApplyRemoveKnockupableSystem());
        add(new ApplyRemoveKnockupSystem());
        add(new ApplyRemoveSilencableSystem());
        add(new ApplyRemoveSilenceSystem());
        add(new ApplyRemoveStunnableSystem());
        add(new ApplyRemoveStunSystem());
        add(new ApplyAddTargetabilitySystem());
        add(new ApplyRemoveTargetabilitySystem());
        add(new ApplyAddVulnerabilitySystem());
        add(new ApplyRemoveVulnerabilitySystem());
        add(new ApplyPhysicalDamageSystem());
        add(new ApplyMagicDamageSystem());
        add(new ApplyHealSystem());
        add(new ApplyStopSystem());
        add(new ApplyMoveSystem());
        add(new ApplyTeleportSystem());
        add(new ApplyActivateHitboxSystem());
        add(new ApplyAddCollisiongGroupsSystem());
        add(new ApplyAddIntersectionPushSystem());
        add(new ApplyDeactivateHitboxSystem());
        add(new ApplyRemoveCollisiongGroupsSystem());
        add(new ApplyRemoveIntersectionPushSystem());
        add(new ApplyDisplayPlayerAnnouncementsSystem());
        add(new ApplyAddPopupsSystem());
        add(new ApplyRemovePopupsSystem());
        add(new ApplySpawnsSystems());
        add(new ApplyAddAutoAttackSpellEffectsSystem());
        add(new ApplyAddSpellsSpellEffectsSystem());
        add(new ApplyEnqueueSpellCastSystem(castSpellQueueSystem));
        add(new ApplyRemoveSpellEffectsSystem());
        add(new ApplyReplaceSpellsWithExistingSpellsSystem());
        add(new ApplyReplaceSpellsWithNewSpellsSystem());
        add(new ApplyTriggerSpellEffectsSystem());
        add(new ApplyAddGoldSystem());
        add(new ApplyCancelActionsSystem());
        add(new ApplyRespawnSystem(map));
        add(new ApplyAddStealthSystem());
        add(new ApplyRemoveStealthSystem());
        add(new ApplyPlayAnimationsSystem());
        add(new ApplyStopAnimationsSystem());
        add(new ApplyAddComponentsSystem());
        add(new ApplyAddEffectTriggersSystem());
        add(new ApplyFinishObjectivesSystem());
        add(new ApplyRemoveComponentsSystem());
        add(new ApplyRemoveEffectTriggersSystem());
        add(new ApplyRemoveEntitySystem());
        add(new ApplyTriggerErikaPassivesSystem());
        add(new SetInCombatSystem());
        add(new DrawAggroOnDamageSystem());
        add(new ResetAggroTimerOnDamageSystem());
        add(new UpdateDamageHistorySystem());
        add(new TriggerDamageTakenSystem());
        add(new LifestealSystem());
        add(new RemoveAppliedEffectImpactsSystem());
    }
    private CalculateEffectImpactSystem calculateEffectImpactSystem;

    @Override
    protected boolean isFinished(EntityWorld entityWorld) {
        return !calculateEffectImpactSystem.hasApplicableEffectCastEntities(entityWorld);
    }
}
