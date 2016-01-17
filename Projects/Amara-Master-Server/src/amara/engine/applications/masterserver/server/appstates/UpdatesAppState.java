/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import java.io.File;
import java.util.LinkedList;
import amara.core.files.FileManager;
import amara.engine.applications.*;
import amara.engine.applications.masterserver.server.network.backends.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.NetworkServerAppState;
import amara.engine.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class UpdatesAppState extends ServerBaseAppState{

    public UpdatesAppState(){
        
    }
    private String updateFilesDirectory;
    private UpdateFile[] updateFiles;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        updateFilesDirectory = FileManager.getFileContent("./update.ini");
        initializeUpdateFiles();
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new SendUpdateFilesBackend(this));
    }

    private void initializeUpdateFiles(){
        LinkedList<UpdateFile> filesList = new LinkedList<UpdateFile>();
        File parentDirectory = new File(updateFilesDirectory);
        addUpdateFiles(parentDirectory, parentDirectory, filesList);
        updateFiles = filesList.toArray(new UpdateFile[filesList.size()]);
    }
    
    private void addUpdateFiles(File parentDirectory, File file, LinkedList<UpdateFile> filesList){
        if(file.isDirectory()){
            if(file != parentDirectory){
                filesList.add(new UpdateFile(prepareUpdateFilePath(file) + "/", null, 0));
            }
            File[] subFiles = file.listFiles();
            for(File subFile : subFiles){
                addUpdateFiles(parentDirectory, subFile, filesList);
            }
        }
        else{
            filesList.add(new UpdateFile(prepareUpdateFilePath(file), FileManager.getFileChecksum_MD5(file), file.length()));
        }
    }
    
    private String prepareUpdateFilePath(File file){
        String filePath = ("./" + file.getPath().substring(updateFilesDirectory.length()));
        filePath = filePath.replace("\\", "/");
        return filePath;
    }

    public String getUpdateFilesDirectory(){
        return updateFilesDirectory;
    }

    public UpdateFile[] getUpdateFiles(){
        return updateFiles;
    }
}
