/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.audio;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import amara.engine.appstates.AudioAppState;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.games.Game;

/**
 *
 * @author Carl
 */
public class AudioSystem implements EntitySystem{
    
    public AudioSystem(AudioAppState audioAppState){
        this.audioAppState = audioAppState;
    }
    private AudioAppState audioAppState;
    private HashMap<Integer, AudioNode> audioNodes = new HashMap<Integer, AudioNode>();
    private LinkedList<Integer> queuedAudioEntities = new LinkedList<Integer>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        increasePlayingAudioProgresses(deltaSeconds);
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AudioComponent.class, AudioSourceComponent.class, PositionComponent.class, IsAudioPlayingComponent.class, GameSpeedComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AudioComponent.class)){
            load(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AudioComponent.class)){
            remove(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(AudioSourceComponent.class)){
            AudioNode audioNode = audioNodes.get(entity);
            int audioSourceEntity = entityWorld.getComponent(entity, AudioSourceComponent.class).getEntity();
            audioNode.setUserData("audio_source_entity", audioSourceEntity);
            tryUpdateAudioPosition(audioNode, entityWorld.getComponent(audioSourceEntity, PositionComponent.class));
            //audioNode.setPositional(true);
            audioNode.setRefDistance(50);
            audioNode.setMaxDistance(100);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AudioSourceComponent.class)){
            AudioNode audioNode = audioNodes.get(entity);
            audioNode.setUserData("audio_source_entity", null);
            audioNode.setPositional(false);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(IsAudioPlayingComponent.class)){
            enqueuePlay(entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(IsAudioPlayingComponent.class)){
            enqueuePlay(entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAudioPlayingComponent.class)){
            stop(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(IsAudioPausedComponent.class)){
            pause(entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAudioPausedComponent.class)){
            if(entityWorld.hasComponent(entity, IsAudioPlayingComponent.class)){
                play(entity);
            }
        }
        updateAudioPositions(observer);
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Game.ENTITY, GameSpeedComponent.class);
        if(gameSpeedComponent != null){
            for(AudioNode audioNode : audioNodes.values()){
                updateAudioPitch(entityWorld, audioNode);
            }
        }
        observer.reset();
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
    
    private void pause(int audioEntity){
        AudioNode audioNode = audioNodes.get(audioEntity);
        audioNode.pause();
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
    
    private void updateAudioPositions(ComponentMapObserver observer){
        for(AudioNode audioNode : audioNodes.values()){
            Integer audioSourceEntity = audioNode.getUserData("audio_source_entity");
            if(audioSourceEntity != null){
                tryUpdateAudioPosition(audioNode, observer.getNew().getComponent(audioSourceEntity, PositionComponent.class));
                tryUpdateAudioPosition(audioNode, observer.getChanged().getComponent(audioSourceEntity, PositionComponent.class));
            }
        }
    }
    
    private void tryUpdateAudioPosition(AudioNode audioNode, PositionComponent positionComponent){
        if(positionComponent != null){
            audioNode.setLocalTranslation(positionComponent.getPosition().getX(), 0, positionComponent.getPosition().getY());
        }
    }
    
    private void updateAudioPitch(EntityWorld entityWorld, AudioNode audioNode){
        float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
        float audioSpeed = Math.max(0.5f, Math.min(gameSpeed, 2));
        audioNode.setPitch(audioSpeed);
    }
}
