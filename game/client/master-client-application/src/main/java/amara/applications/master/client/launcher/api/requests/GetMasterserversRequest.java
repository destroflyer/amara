/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.api.requests;

import java.util.List;
import amara.applications.master.client.launcher.api.objects.Masterserver;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

/**
 *
 * @author Carl
 */
public class GetMasterserversRequest extends APIRequest{
    
    public GetMasterserversRequest(){
        super("mode=getMasterservers");
    }
    private Masterserver[] masterservers;

    @Override
    public void send(){
        super.send();
        Element newsNode = getResponseXMLContent().getRootElement();
        List<Element> serverElements = newsNode.getChildren();
        masterservers = new Masterserver[serverElements.size()];
        for(int i=0;i<serverElements.size();i++){
            Element serverElement = serverElements.get(i);
            try{
                String ip = serverElement.getAttributeValue("ip");
                int port = serverElement.getAttribute("port").getIntValue();
                masterservers[i] = new Masterserver(ip, port);
            }catch(DataConversionException ex){
                ex.printStackTrace();
            }
        }
    }

    public Masterserver[] getMasterservers(){
        return masterservers;
    }
}
