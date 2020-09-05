/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.filters;

import amara.applications.ingame.shared.maps.MapFilter;

/**
 *
 * @author Carl
 */
public class MapFilter_SSAO extends MapFilter {

    public MapFilter_SSAO(float sampleRadius, float intensity, float scale, float bias) {
        this.sampleRadius = sampleRadius;
        this.intensity = intensity;
        this.scale = scale;
        this.bias = bias;
    }
    private float sampleRadius;
    private float intensity;
    private float scale;
    private float bias;

    public float getSampleRadius() {
        return sampleRadius;
    }

    public float getIntensity() {
        return intensity;
    }

    public float getScale() {
        return scale;
    }

    public float getBias() {
        return bias;
    }
}
