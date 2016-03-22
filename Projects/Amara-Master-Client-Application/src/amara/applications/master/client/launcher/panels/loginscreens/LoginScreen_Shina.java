/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.loginscreens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import amara.applications.master.client.launcher.OggClip;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class LoginScreen_Shina extends SwingLoginScreen{

    public LoginScreen_Shina(){
        loopDuration = 40;
    }
    private BufferedImage backgroundImage;
    private OggClip backgroundMusic;
    private KeyframeAnimation overlayAlphaAnimation = new KeyframeAnimation(
        new Keyframe(0, 1),
        new Keyframe(0.5f, 1),
        new Keyframe(0.6f, 0),
        new Keyframe(1.1f, 1),
        new Keyframe(2, 1),
        new Keyframe(2.2f, 0.2f),
        new Keyframe(2.6f, 1),
        new Keyframe(3.4f, 1),
        new Keyframe(5, 0),
        new Keyframe(37.5f, 0),
        new Keyframe(39.5f, 1),
        new Keyframe(loopDuration, 1)
    );

    @Override
    public void initialize(){
        super.initialize();
        backgroundImage = FileAssets.getImage("Interface/client/login/shina/background.png", width, height);
        backgroundMusic = new OggClip(FileAssets.ROOT + "Sounds/music/login/shina/background.ogg");
        backgroundMusic.setGain(0.8f);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(backgroundMusic.stopped() && (time > 0.5f)){
            backgroundMusic.play();
        }
    }
    
    @Override
    public void paint(Graphics2D graphics){
        graphics.drawImage(backgroundImage, 0, 0, null);
        graphics.setColor(new Color(0.015f, 0.015f, 0.015f, overlayAlphaAnimation.getValue(time)));
        graphics.fillRect(0, 0, width, height);
    }

    @Override
    public void close(){
        super.close();
        backgroundMusic.stop();
    }
}
