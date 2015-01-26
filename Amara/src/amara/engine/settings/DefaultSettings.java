/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.settings;

import java.awt.Dimension;

/**
 *
 * @author Carl
 */
public class DefaultSettings{
    
    public static final Dimension[] RESOLUTIONS = new Dimension[]{
        new Dimension(320, 240),
        new Dimension(400, 240),
        new Dimension(640, 800),
        new Dimension(800, 480),
        new Dimension(800, 600),
        new Dimension(854, 480),
        new Dimension(1024, 600),
        new Dimension(1024, 768),
        new Dimension(1152, 864),
        new Dimension(1280, 720),
        new Dimension(1280, 768),
        new Dimension(1280, 800),
        new Dimension(1280, 1024),
        new Dimension(1360, 768),
        new Dimension(1360, 1024),
        new Dimension(1366, 768),
        new Dimension(1400, 1050),
        new Dimension(1440, 900),
        new Dimension(1600, 900),
        new Dimension(1600, 1200),
        new Dimension(1680, 1050),
        new Dimension(1920, 1080),
        new Dimension(1920, 1200),
        new Dimension(2048, 1152),
        new Dimension(2048, 1536),
        new Dimension(2560, 1440),
        new Dimension(2560, 1600),
        new Dimension(3200, 2400),
        new Dimension(3840, 2400),
        new Dimension(4096, 3072),
        new Dimension(5120, 3200),
        new Dimension(6400, 4800),
        new Dimension(7680, 4800)
    };
    public static int[] ANTIALIASING_SAMPLES = new int[]{
        0, 2, 4, 8, 16, 32
    };
}
