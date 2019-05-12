/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.loginscreens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import amara.applications.master.client.launcher.OggClip;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class LoginScreen_Classic extends SwingLoginScreen{

    private BufferedImage backgroundImage;
    private OggClip backgroundMusic;

    @Override
    public void initialize(){
        super.initialize();
        backgroundImage = FileAssets.getImage("Interface/client/login/classic/background.jpg", width, height);
        backgroundMusic = new OggClip(FileAssets.ROOT + "Sounds/music/login/classic/background.ogg");
        backgroundMusic.setGain(0.75f);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(backgroundMusic.stopped()){
            backgroundMusic.play();
        }
    }
    
    @Override
    public void paint(Graphics2D graphics){
        graphics.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void close(){
        super.close();
        backgroundMusic.stop();
    }
}
