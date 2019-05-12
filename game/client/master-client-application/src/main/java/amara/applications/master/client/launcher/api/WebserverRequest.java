/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Carl
 */
public class WebserverRequest{
    
    public WebserverRequest(String path){
        this(path, null);
    }
    
    public WebserverRequest(String path, String query){
        try{
            URL url = WebserverInfo.getWebserverRequestURL(path, query);
            urlConnection = url.openConnection();
            /*if(Session.getID() != null){
                urlConnection.setRequestProperty("Cookie", "amara_session=" + Session.getID());
            }*/
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private URLConnection urlConnection;
    protected HashMap<String, String> postData = new HashMap<String, String>();
    private String responseContent;
    
    public void send(){
        try{
            //Set POST-Data
            String postDataText = getPostDataText();
            if(!postDataText.isEmpty()){
                urlConnection.setDoOutput(true);
                OutputStreamWriter urlConnectionPostDataWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                urlConnectionPostDataWriter.write(postDataText);
                urlConnectionPostDataWriter.flush();
                urlConnectionPostDataWriter.close();
            }
            //Receive response
            responseContent = "";
            BufferedReader responseInputReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String responseLine;
            int lineIndex = 0;
            while((responseLine = responseInputReader.readLine()) != null){
                if(lineIndex != 0){
                    responseContent += "\n";
                }
                responseContent += responseLine;
                lineIndex++;
            }
            responseInputReader.close();
            //Read cookies
            String headerName = null;
            for(int i=1;((headerName = urlConnection.getHeaderFieldKey(i)) != null);i++){
                if(headerName.equals("Set-Cookie")){                  
                    String cookie = urlConnection.getHeaderField(i);
                    cookie = cookie.substring(0, cookie.indexOf(";"));
                    String cookieName = cookie.substring(0, cookie.indexOf("="));
                    String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    if(cookieName.equals("amara_session")){
                        //Session.setID(cookieValue);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private String getPostDataText(){
        String data = "";
        try{
            Iterator<String> postDataKeyIterator = postData.keySet().iterator();
            int i = 0;
            while(postDataKeyIterator.hasNext()){
                String postDataKey = postDataKeyIterator.next();
                String postDataValue = postData.get(postDataKey);
                if(i != 0){
                    data += "&";
                }
                data += URLEncoder.encode(postDataKey, "UTF-8") + "=" + URLEncoder.encode(postDataValue, "UTF-8");
                i++;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    public String getResponseContent(){
        return responseContent;
    }
    
    public Document getResponseXMLContent(){
        try{
            Document document = new SAXBuilder().build(new StringReader(responseContent));
            return document;
        }catch(Exception ex){
            System.out.println("Error while loading XML response '" + responseContent + "'");
        }
        return null;
    }
}
