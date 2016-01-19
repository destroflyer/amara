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
}
