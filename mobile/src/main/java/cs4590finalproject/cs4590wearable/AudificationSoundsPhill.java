package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;

/**
 * The goal for my approach is to take the data received from motion data and develop thresholds
 * and play sounds based on the data in different thresholds
 */
public class AudificationSoundsPhill {
    PApplet a;
    float[] data;

    public AudificationSoundsPhill(PApplet a) {
        this.a = a;
        data = new float[50];
    }

}
