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
public class ReleaseApplication{
    
    private static final String DIRECTORY_DEVELOPER_PROJECTS = "../";
    private static final String DIRECTORY_RELEASE_SERVER_APPLICATION = "[root]/amara";
    private static final String[] REMOVED_DIRECTORY_NAMES = new String[]{
        "animations"
    };
    private static final String[] REMOVED_FILES_ENDINGS = new String[]{
        "README.txt",
        ".blend",".mesh.xml",".skeleton.xml"
    };
    
    public static void main(String[] args){
        synchronize("Server Application [Executable]", "/Amara-Master-Server-Application/dist/Amara-Master-Server-Application.jar", "/server.jar");
        synchronize("Server Application [Libraries]", "/Amara-Master-Server-Application/dist/lib", "/lib");
        synchronize("Server Application [Key to the City]", "/Amara-Master-Server-Application/workspace/key_to_the_city.ini", "/key_to_the_city.ini");
        synchronize("Client Application [Download] [Executable]", "/Amara-Master-Client-Application/dist/Amara-Master-Client-Application.jar", "/Amara/Amara.jar");
        synchronize("Client Application [Download] [Libraries]", "/Amara-Master-Client-Application/dist/lib", "/Amara/lib");
        synchronize("Client Application [Update] [Executable]", "/Amara-Master-Client-Application/dist/Amara-Master-Client-Application.jar", "/update/Amara.jar");
        synchronize("Client Application [Update] [Libraries]", "/Amara-Master-Client-Application/dist/lib", "/update/lib");
        synchronize("Client Application [Assets]", "/assets", "/update/assets");
        synchronize("Client Application [Test]", "/Amara-Master-Server-Application/workspace/test", "/update/test");
        synchronize("SQL Scripts", "/Amara-Ingame-Server/src/amara/applications/master/server/scripts", "/scripts");
    }
    
    private static void synchronize(String title, String developerDirectory, String releaseDirectory){
        System.out.println("Synchronizing " + title + "...");
        cleanup(DIRECTORY_DEVELOPER_PROJECTS + developerDirectory, DIRECTORY_RELEASE_SERVER_APPLICATION + releaseDirectory, "");
        copy(DIRECTORY_DEVELOPER_PROJECTS + developerDirectory, DIRECTORY_RELEASE_SERVER_APPLICATION + releaseDirectory, "");
    }
    
    private static void cleanup(String developerDirectory, String releaseDirectory, String filePath){
        File releasedFile = new File(releaseDirectory + filePath);
        File developerFile = new File(developerDirectory + filePath);
        boolean existsDeveloperFile = developerFile.exists();
        String[] subFileNames = releasedFile.list();
        boolean shouldBeReleased;
        if(subFileNames != null){
            for(String subFileName : subFileNames){
                cleanup(developerDirectory, releaseDirectory, filePath + "/" + subFileName);
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
    
    private static void copy(String developerDirectory, String releaseDirectory, String filePath){
        boolean copyFile;
        File developerFile = new File(developerDirectory + filePath);
        File releasedFile = new File(releaseDirectory + filePath);
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
                    copy(developerDirectory, releaseDirectory, filePath + "/" + subFileName);
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
        for(String fileExtension : REMOVED_FILES_ENDINGS){
            if(file.getPath().endsWith(fileExtension)){
                return false;
            }
        }
        return true;
    }
}
