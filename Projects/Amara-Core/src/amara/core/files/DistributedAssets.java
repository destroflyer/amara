/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Carl
 */
public class DistributedAssets{
    
    private static final String DIRECTORY_ASSETS_DEVELOPER = "../assets";
    private static final String DIRECTORY_ASSETS_RELEASE = "[root]/amara/update/assets";
    private static final String[] REMOVED_DIRECTORY_NAMES = new String[]{
        "animations"
    };
    private static final String[] REMOVED_FILES_EXTENSIONS = new String[]{
        ".blend",".mesh.xml",".skeleton.xml"
    };
    
    public static void main(String[] args){
        cleanup("");
        copy("");
    }
    
    public static void cleanup(String filePath){
        File releasedFile = new File(DIRECTORY_ASSETS_RELEASE + filePath);
        File developerFile = new File(DIRECTORY_ASSETS_DEVELOPER + filePath);
        boolean existsDeveloperFile = developerFile.exists();
        String[] subFileNames = releasedFile.list();
        boolean shouldBeReleased;
        if(subFileNames != null){
            for(String subFileName : subFileNames){
                cleanup(filePath + "/" + subFileName);
            }
            shouldBeReleased = shouldBeReleased_Directory(developerFile);
        }
        else{
            shouldBeReleased = shouldBeReleased_File(developerFile);
        }
        if((!existsDeveloperFile) || (!shouldBeReleased)){
            if(releasedFile.delete()){
                System.out.println("Deleted file: " + filePath);
            }
            else{
                System.err.println("Couldn't delete file: " + filePath);
            }
        }
    }
    
    public static void copy(String filePath){
        boolean copyFile;
        File developerFile = new File(DIRECTORY_ASSETS_DEVELOPER + filePath);
        File releasedFile = new File(DIRECTORY_ASSETS_RELEASE + filePath);
        boolean existsReleasedFile = releasedFile.exists();
        String[] subFileNames = developerFile.list();
        if(subFileNames != null){
            copyFile = shouldBeReleased_Directory(developerFile);
            if(copyFile){
                if(!existsReleasedFile){
                    if(releasedFile.mkdir()){
                        System.out.println("Created directory: " + filePath);
                    }
                    else{
                        System.err.println("Couldn't create directory: " + filePath);
                    }
                }
                for(String subFileName : subFileNames){
                    copy(filePath + "/" + subFileName);
                }
            }
        }
        else{
            copyFile = shouldBeReleased_File(developerFile);
            if(copyFile){
                if(releasedFile.exists()){
                    String developerFile_MD5 = FileManager.getFileChecksum_MD5(developerFile);
                    String releasedFile_MD5 = FileManager.getFileChecksum_MD5(releasedFile);
                    if(developerFile_MD5.equals(releasedFile_MD5)){
                        copyFile = false;
                    }
                    else{
                        releasedFile.delete();
                    }
                }
                if(copyFile){
                    try{
                        Files.copy(developerFile.toPath(), releasedFile.toPath());
                        System.out.println("Copied file: " + filePath);
                    }catch(IOException ex){
                        System.err.println("Error while copying file: " + filePath);
                    }
                }
            }
        }
    }
    
    private static boolean shouldBeReleased_Directory(File directory){
        for(String directoryName : REMOVED_DIRECTORY_NAMES){
            if(directory.getName().equals(directoryName)){
                return false;
            }
        }
        return true;
    }
    
    private static boolean shouldBeReleased_File(File file){
        for(String fileExtension : REMOVED_FILES_EXTENSIONS){
            if(file.getPath().toLowerCase().endsWith(fileExtension)){
                return false;
            }
        }
        return true;
    }
}
