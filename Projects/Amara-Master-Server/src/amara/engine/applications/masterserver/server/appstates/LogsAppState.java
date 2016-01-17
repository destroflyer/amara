/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import amara.core.files.FileManager;
import amara.engine.applications.*;

/**
 *
 * @author Carl
 */
public class LogsAppState extends ServerBaseAppState{
    
    private static final String DIRECTORY = "./logs/";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
    public enum Type{
        Info,
        Crash,
        Warning
    }
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        FileManager.createDirectoryIfNotExists(DIRECTORY);
    }
    
    public void writeLog(Type type, String text){
        String filePath = (DIRECTORY + type.name() + " " + dateFormat.format(new Date()) + ".txt");
        FileManager.putFileContent(filePath, text);
    }
    
    public static String printStackTrace(Exception exception){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
