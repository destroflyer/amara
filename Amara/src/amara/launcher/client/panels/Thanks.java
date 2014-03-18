/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

/**
 *
 * @author Carl
 */
public class Thanks{

    public Thanks(String receiver, String description, String iconFilePath){
        this(receiver, description, iconFilePath, "");
    }

    public Thanks(String receiver, String description, String iconFilePath, String url){
        this.receiver = receiver;
        this.description = description;
        this.iconFilePath = iconFilePath;
        this.url = url;
    }
    private String receiver;
    private String description;
    private String iconFilePath;
    private String url;

    public String getReceiver(){
        return receiver;
    }

    public String getDescription(){
        return description;
    }

    public String getIconFilePath(){
        return iconFilePath;
    }
    
    public boolean hasURL(){
        return (!url.isEmpty());
    }

    public String getURL(){
        return url;
    }
}
