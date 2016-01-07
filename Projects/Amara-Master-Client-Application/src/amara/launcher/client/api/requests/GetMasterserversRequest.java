/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.api.requests;

import java.util.List;
import amara.launcher.client.api.objects.Masterserver;
import org.jdom.DataConversionException;
import org.jdom.Element;

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
