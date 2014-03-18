/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client;

import java.io.BufferedInputStream;
import amara.Util;
import javazoom.jl.player.Player;

/**
 *
 * @author carl, lesasch
 */
public class MP3Player{

    public MP3Player(){
        
    }
    private Player player;
    private BufferedInputStream songIputStream;
    private boolean playLoop;

    public void play(final String songResourcePath){
        play(songResourcePath, false);
    }

    public void play(final String songResourcePath, boolean playLoop){
        this.playLoop = playLoop;
        new Thread(new Runnable(){

            public void run(){
                do{
                    songIputStream = new BufferedInputStream(Util.getResourceInputStrean(songResourcePath));
                    try{
                        player = new Player(songIputStream);
                        player.play();
                        songIputStream.close();
                    }catch(Exception ex){
                    }
                    if(player != null){
                        player.close();
                        player = null;
                    }
                    songIputStream = null;
                }while(isPlayLoop());
            }
        }).start();
    }

    public void stop(){
        if(player != null){
            player.close();
            player = null;
        }
    }

    public boolean isPlaying(){
        return ((player != null) && (!player.isComplete()));
    }

    public boolean isPlayLoop(){
        return playLoop;
    }

    public void setPlayLoop(boolean playLoop){
        this.playLoop = playLoop;
    }
}
