/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class UpdateFile{

    public UpdateFile(){
        
    }

    public UpdateFile(String filePath, String checksum_MD5, long size){
        this.filePath = filePath;
        this.checksum_MD5 = checksum_MD5;
        this.size = size;
    }
    private String filePath;
    private String checksum_MD5;
    private long size;

    public String getFilePath(){
        return filePath;
    }

    public String getChecksum_MD5(){
        return checksum_MD5;
    }

    public long getSize(){
        return size;
    }
}
