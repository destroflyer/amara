/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui;

import amara.core.Util;

/**
 *
 * @author Carl
 */
public class GUIUtil{
    
    public static String getValueText_Signed(float value){
        value = Util.compensateFloatRoundingErrors(value);
        String signText = "";
        if(value >= 0){
            signText += "+";
        }
        return (signText + getValueText(value));
    }
    
    public static String getValueText(float value){
        int valueInt = (int) value;
        if(value == valueInt){
            return ("" + valueInt);
        }
        else{
            return ("" + value);
        }
    }
    
    public static String getFormattedTime(float time){
        int seconds = (int) time;
        int hours = (int) (seconds / (60 * 60));
        seconds -= (hours * (60 * 60));
        int minutes = (int) (seconds / 60);
        seconds -= (minutes * 60);
        String text = "";
        if(hours > 0){
            text += (hours + ":");
        }
        if(minutes < 10){
            text += "0";
        }
        text += (minutes + ":");
        if(seconds < 10){
            text += "0";
        }
        text += seconds;
        return text;
    }
}
