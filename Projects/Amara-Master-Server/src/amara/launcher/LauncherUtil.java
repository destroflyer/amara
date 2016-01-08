/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import com.jme3.animation.SkeletonControl;
import amara.MultipleOutputStream;
import amara.engine.network.*;
import amara.game.entitysystem.CustomGameTemplates;

/**
 *
 * @author Carl
 */
public class LauncherUtil{
    
    public static void initProgramProperties(){
        //ConsoleOutput
        Logger.getLogger("").setLevel(Level.SEVERE);
        Logger.getLogger(SkeletonControl.class.getName()).setLevel(Level.SEVERE);
        try{
            FileOutputStream logFileOutputStream = new FileOutputStream("./log.txt");
            System.setOut(new PrintStream(new MultipleOutputStream(System.out, logFileOutputStream)));
            System.setErr(new PrintStream(new MultipleOutputStream(System.err, logFileOutputStream)));
        }catch(Exception ex){
        }
        //LookAndFeel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex){
        }
        MessagesSerializer_Protocol.registerClasses();
        MessagesSerializer_Game.registerClasses();
        CustomGameTemplates.registerLoader();
    }
}
