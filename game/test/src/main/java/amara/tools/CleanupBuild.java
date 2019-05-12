/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.tools;

import java.io.File;
import amara.core.files.FileManager;

/**
 *
 * @author Carl
 */
public class CleanupBuild{
    
    public static void main(String[] args){
        File projectsDirectory = new File("../../");
        for(File projectDirectory : projectsDirectory.listFiles()){
            if((!projectDirectory.getName().equals("Amara-Master-Server-Application"))
            && (!projectDirectory.getName().equals("Amara-Master-Client-Application"))){
                File distDirectory = new File(projectDirectory.getPath() + "/dist");
                if(distDirectory.exists()){
                    if(!FileManager.deleteFile(distDirectory)){
                        System.err.println("Couldn't delete: " + distDirectory);
                    }
                }
            }
        }
    }
}
