/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher;

import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.UIManager;
import amara.MultipleOutputStream;
import amara.engine.files.FileAssets;
import amara.engine.network.*;
import amara.game.entitysystem.CustomGameTemplates;

/**
 *
 * @author Carl
 */
public class LauncherUtil{
    
    public static void initProgramProperties(){
        try{
            FileOutputStream logFileOutputStream = new FileOutputStream("./log.txt");
            System.setOut(new PrintStream(new MultipleOutputStream(System.out, logFileOutputStream)));
            System.setErr(new PrintStream(new MultipleOutputStream(System.err, logFileOutputStream)));
        }catch(Exception ex){
        }
        FileAssets.readRootFile();
        MessagesSerializer_Protocol.registerClasses();
        MessagesSerializer_Game.registerClasses();
        CustomGameTemplates.registerLoader();
        //LookAndFeel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex){
        }
    }
}
