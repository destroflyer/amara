/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.audio;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.shared.games.Game;
import amara.core.settings.Settings;
import amara.libraries.applications.display.appstates.AudioAppState;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class AudioSystem implements EntitySystem{
    
    public AudioSystem(AudioAppState audioAppState, IngameCameraAppState ingameCameraAppState){
        this.audioAppState = audioAppState;
        this.ingameCameraAppState = ingameCameraAppState;
    }
    private AudioAppState audioAppState;
    private IngameCameraAppState ingameCameraAppState;
    private HashMap<Integer, AudioNode> audioNodes = new HashMap<Integer, AudioNode>();
    private LinkedList<Integer> queuedAudioEntities = new LinkedList<Integer>();
    private Vector2f tmpAudioLocation = new Vector2f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        increasePlayingAudioProgresses(deltaSeconds);
        ComponentMapObserver observer = entityWorld.requestObserver(this, SendEntityChangesSystem.COMPONENT_EQUALITY_DEFINTION, AudioComponent.class, AudioSourceComponent.class, PositionComponent.class, StartPlayingAudioComponent.class, StopPlayingAudioComponent.class, GameSpeedComponent.class);
        //Data
        for(int entity : observer.getNew().getEntitiesWithAll(AudioComponent.class)){
            load(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AudioComponent.class)){
            remove(entity);
        }
        //Source
        for(int entity : observer.getNew().getEntitiesWithAll(AudioSourceComponent.class)){
            AudioNode audioNode = audioNodes.get(entity);
            int audioSourceEntity = entityWorld.getComponent(entity, AudioSourceComponent.class).getEntity();
            audioNode.setPositional(true);
            audioNode.setRefDistance(9999999);
            audioNode.setMaxDistance(9999999);
            tryUpdateAudioPosition(entity, entityWorld.getComponent(audioSourceEntity, PositionComponent.class));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AudioSourceComponent.class)){
            AudioNode audioNode = audioNodes.get(entity);
            audioNode.setPositional(false);
        }
        //Start
        for(int entity : observer.getNew().getEntitiesWithAll(StartPlayingAudioComponent.class)){
            enqueuePlay(entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(StartPlayingAudioComponent.class)){
            enqueuePlay(entity);
        }
        //Stop
        for(int entity : observer.getNew().getEntitiesWithAll(StopPlayingAudioComponent.class)){
            stop(entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(StopPlayingAudioComponent.class)){
            stop(entity);
        }
        updateAudioPositions(entityWorld, observer);
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Game.ENTITY, GameSpeedComponent.class);
        if(gameSpeedComponent != null){
            for(AudioNode audioNode : audioNodes.values()){
                updateAudioPitch(entityWorld, audioNode);
            }
        }
        playQueuedAudioEntities(entityWorld);
    }
    
    private void load(EntityWorld entityWorld, int audioEntity){
        String audioPath = entityWorld.getComponent(audioEntity, AudioComponent.class).getAudioPath();
        AudioNode audioNode = audioAppState.createAudioNode(audioPath);
        audioNode.setLooping(entityWorld.hasComponent(audioEntity, AudioLoopComponent.class));
        AudioVolumeComponent audioVolumeComponent = entityWorld.getComponent(audioEntity, AudioVolumeComponent.class);
        if(audioVolumeComponent != null){
            audioNode.setVolume(audioNode.getVolume() * audioVolumeComponent.getVolume());
        }
        audioNodes.put(audioEntity, audioNode);
        updateAudioPitch(entityWorld, audioNode);
    }
    
    private void remove(int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        audioAppState.removeAudioNode(audioNode);
    }
    
    private void enqueuePlay(int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        audioNode.setUserData("audio_progress", 0f);
        queuedAudioEntities.add(audioEntity);
    }
    
    private void play(int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        audioNode.stop();
        audioNode.play();
    }
    
    private void stop(int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        audioNode.setUserData("audio_progress", 0f);
        audioNode.stop();
    }
    
    private void increasePlayingAudioProgresses(float deltaSeconds){
        for(AudioNode audioNode : audioNodes.values()){
            if(audioNode.getStatus() == AudioSource.Status.Playing){
                audioNode.setUserData("audio_progress", getProgress(audioNode) + deltaSeconds);
            }
        }
    }
    
    private void playQueuedAudioEntities(EntityWorld entityWorld){
        for(int i=0;i<queuedAudioEntities.size();i++){
            int audioEntity = queuedAudioEntities.get(i);
            boolean isPlayingAllowed = true;
            AudioSuccessorComponent audioSuccessorComponent = entityWorld.getComponent(audioEntity, AudioSuccessorComponent.class);
            if(audioSuccessorComponent != null){
                AudioNode audioNode = audioNodes.get(audioSuccessorComponent.getAudioEntity());
                if(audioNode != null){
                    isPlayingAllowed = (getProgress(audioNode) >= audioSuccessorComponent.getDelay());
                }
            }
            if(isPlayingAllowed){
                play(audioEntity);
                queuedAudioEntities.remove(i);
                i--;
            }
        }
    }
    
    private float getProgress(AudioNode audioNode){
        Float progress = audioNode.getUserData("audio_progress");
        return ((progress != null)?progress:0);
    }
    
    private void updateAudioPositions(EntityWorld entityWorld, ComponentMapObserver observer){
        for(int audioEntity : audioNodes.keySet()){
            boolean updateVolume = ingameCameraAppState.hasMoved();
            AudioSourceComponent audioSourceComponent = entityWorld.getComponent(audioEntity, AudioSourceComponent.class);
            if(audioSourceComponent != null){
                if(tryUpdateAudioPosition(audioEntity, observer.getNew().getComponent(audioSourceComponent.getEntity(), PositionComponent.class))
                || tryUpdateAudioPosition(audioEntity, observer.getChanged().getComponent(audioSourceComponent.getEntity(), PositionComponent.class))){
                    updateVolume = true;
                }
            }
            if(updateVolume){
                updateAudioVolume(entityWorld, audioEntity);
            }
        }
    }
    
    private boolean tryUpdateAudioPosition(int audioEntity, PositionComponent positionComponent){
        if(positionComponent != null){
            Vector2f position = positionComponent.getPosition();
            AudioNode audioNode = audioNodes.get(audioEntity);
            audioNode.setLocalTranslation(position.getX(), 0, position.getY());
            return true;
        }
        return false;
    }
    
    private void updateAudioVolume(EntityWorld entityWorld, int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        float volume = Settings.getFloat("audio_volume");
        AudioVolumeComponent audioVolumeComponent = entityWorld.getComponent(audioEntity, AudioVolumeComponent.class);
        if(audioVolumeComponent != null){
            volume *= audioVolumeComponent.getVolume();
        }
        if(!entityWorld.hasComponent(audioEntity, AudioGlobalComponent.class)){
            tmpAudioLocation.set(audioNode.getLocalTranslation().getX(), audioNode.getLocalTranslation().getZ());
            if(!ingameCameraAppState.isVisible(tmpAudioLocation)){
                volume = 0;
            }
        }
        audioNode.setVolume(volume);
    }
    
    private void updateAudioPitch(EntityWorld entityWorld, AudioNode audioNode){
        float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
        float audioSpeed = Math.max(0.5f, Math.min(gameSpeed, 2));
        audioNode.setPitch(audioSpeed);
    }
}
