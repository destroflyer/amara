/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.windowed;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import amara.core.*;

/**
 *
 * @author Carl
 */
public class FrameUtil{
    
    public static void initFrameSpecials(JFrame frame){
        initWindowSpecials(frame);
        frame.setTitle(GameInfo.getClientTitle());
    }

    public static void initDialogSpecials(JDialog dialog){
        initWindowSpecials(dialog);
        dialog.setTitle(GameInfo.getClientTitle());
    }

    private static void initWindowSpecials(Window window){
        window.setIconImage(GameInfo.ICON_128);
    }
    
    public static void centerFrame(Window window){
        Dimension screenSize = Util.getScreenResolution();
        window.setLocation((int) ((screenSize.getWidth() / 2) - (window.getWidth() / 2)), (int) ((screenSize.getHeight() / 2) - (window.getHeight() / 2)));
    }
    
    public static void setContainerChildsEnabled(Container container, boolean enabled){
        for(int i=0;i<container.getComponentCount();i++){
            Component component = container.getComponent(i);
            component.setEnabled(enabled);
        }
    }
    
    public static Window getWindow(Component windowComponent){
        while(windowComponent.getParent() != null){
            windowComponent = windowComponent.getParent();
            if(windowComponent instanceof Window){
                Window window = (Window) windowComponent;
                return window;
            }
        }
        return null;
    }
    
    public static Point getLocationIn(JComponent component, Container parent){
        int x = 0;
        int y = 0;
        Container container = component;
        do{
            x += container.getX();
            y += container.getY();
            container = container.getParent();
        }while(container != parent);
        return new Point(x, y);
    }
    
    public enum MessageType{
        INFORMATION,
        WARNING,
        ERROR
    }
    public static void showMessageDialog(Component parent, String message, MessageType type){
        int messageType = -1;
        switch(type){
            case INFORMATION:
                messageType = JOptionPane.INFORMATION_MESSAGE;
                break;
            
            case WARNING:
                messageType = JOptionPane.WARNING_MESSAGE;
                break;
            
            case ERROR:
                messageType = JOptionPane.ERROR_MESSAGE;
                break;
        }
        JOptionPane.showMessageDialog(parent, message, GameInfo.NAME, messageType);
    }
    
    public static String showInputDialog(Component parent, String message){
        return JOptionPane.showInputDialog(parent, message, GameInfo.NAME, JOptionPane.QUESTION_MESSAGE);
    }
}
