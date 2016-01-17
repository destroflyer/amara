/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core;

import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.UIManager;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class Launcher_Core{
    
    public static void initialize(){
        try{
            FileOutputStream logFileOutputStream = new FileOutputStream("./log.txt");
            System.setOut(new PrintStream(new MultipleOutputStream(System.out, logFileOutputStream)));
            System.setErr(new PrintStream(new MultipleOutputStream(System.err, logFileOutputStream)));
        }catch(Exception ex){
        }
        FileAssets.readRootFile();
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex){
        }
    }
}
