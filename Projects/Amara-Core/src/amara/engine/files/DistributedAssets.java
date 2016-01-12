/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.files;

import java.io.File;

/**
 *
 * @author Carl
 */
public class DistributedAssets{
    
    public static void main(String[] args){
        cleanup(new File("[root]/update/assets/"));
    }
    
    private static final String[] REMOVED_DIRECTORY_NAMES = new String[]{
        "animations"
    };
    private static final String[] REMOVED_FILES_EXTENSIONS = new String[]{
        ".blend",".mesh.xml",".skeleton.xml"
    };
    
    public static void cleanup(File file){
        File[] files = file.listFiles();
        boolean removeFile = false;
        if(files != null){
            for(String directoryName : REMOVED_DIRECTORY_NAMES){
                if(file.getName().equals(directoryName)){
                    removeFile = true;
                    break;
                }
            }
            if(!removeFile){
                for(File subFile : files){
                    cleanup(subFile);
                }
            }
        }
        else{
            for(String fileExtension : REMOVED_FILES_EXTENSIONS){
                if(file.getPath().toLowerCase().endsWith(fileExtension)){
                    removeFile = true;
                }
            }
        }
        if(removeFile){
            if(FileManager.deleteFile(file)){
                System.out.println("Deleted '" + file.getPath() + "'.");
            }
            else{
                System.err.println("Couldn't delete '" + file.getPath() + "'.");
            }
        }
    }
}
