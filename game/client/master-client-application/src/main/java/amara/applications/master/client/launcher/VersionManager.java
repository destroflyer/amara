/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher;

import java.io.File;
import java.util.List;
import amara.core.files.FileManager;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Carl
 */
public class VersionManager{
    
    private VersionManager(String versionFilePath){
        this.versionFilePath = versionFilePath;
        File versionFile = new File(versionFilePath);
        if(versionFile.exists()){
            try{
                xmlDocument = new SAXBuilder().build(versionFile);
            }catch(Exception ex){
                System.out.println("Error while reading version file: " + ex.toString());
            }
        }
        else{
            Element rootElement = new Element("version");
            Element filesElement = new Element("files");
            rootElement.addContent(filesElement);
            xmlDocument = new Document(rootElement);
            saveFile();
        }
    }
    private static VersionManager instance = new VersionManager("./version.xml");
    private String versionFilePath;
    private Document xmlDocument;
    
    public String getFileChecksumMD5(String filePath){
        Element fileElement = getFileElement(filePath);
        Attribute checksumAttribute = fileElement.getChild("checksum").getAttribute("md5");
        if(checksumAttribute != null){
            return checksumAttribute.getValue();
        }
        return updateFileChecksumMD5(filePath);
    }
    
    public void onFileUpdated(String filePath){
        updateFileChecksumMD5(filePath);
    }
    
    private String updateFileChecksumMD5(String filePath){
        Element fileElement = getFileElement(filePath);
        Element checksumElement = fileElement.getChild("checksum");
        String checksumMD5 = FileManager.getFileChecksum_MD5(new File(filePath));
        checksumElement.setAttribute("md5", checksumMD5);
        saveFile();
        return checksumMD5;
    }
    
    private Element getFileElement(String filePath){
        Element fileElement = null;
        Element filesElement = xmlDocument.getRootElement().getChild("files");
        List<Element> fileElements = filesElement.getChildren();
        for(int i=0;i<fileElements.size();i++){
            Element currentFileElement = fileElements.get(i);
            if(currentFileElement.getAttributeValue("path").equals(filePath)){
                fileElement = currentFileElement;
                break;
            }
        }
        if(fileElement == null){
            fileElement = new Element("file");
            fileElement.setAttribute("path", filePath);
            filesElement.addContent(fileElement);
        }
        Element checksumElement = fileElement.getChild("checksum");
        if(checksumElement == null){
            checksumElement = new Element("checksum");
            fileElement.addContent(checksumElement);
        }
        return fileElement;
    }
    
    private void saveFile(){
        try{
            FileManager.putFileContent(versionFilePath, new XMLOutputter().outputString(xmlDocument));
        }catch(Exception ex){
            System.out.println("Error while saving version file: " + ex.toString());
        }
    }
    
    public static VersionManager getInstance(){
        return instance;
    }
}
