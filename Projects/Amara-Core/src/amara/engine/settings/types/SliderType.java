/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.settings.types;

import amara.engine.settings.SettingType;
import amara.engine.settings.Settings;

/**
 *
 * @author Carl
 */
public class SliderType extends SettingType{

    public SliderType(float defaultValue, float minimum, float maximum, float stepSize, float buttonStepSize){
        this.defaultValue = defaultValue;
        this.minimum = minimum;
        this.maximum = maximum;
        this.stepSize = stepSize;
        this.buttonStepSize = buttonStepSize;
    }
    private float defaultValue;
    private float minimum;
    private float maximum;
    private float stepSize;
    private float buttonStepSize;

    @Override
    public String getDefaultValue(){
        return Settings.toString(defaultValue);
    }

    public float getMinimum(){
        return minimum;
    }

    public float getMaximum(){
        return maximum;
    }

    public float getStepSize(){
        return stepSize;
    }

    public float getButtonStepSize(){
        return buttonStepSize;
    }
}
