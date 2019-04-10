package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;

public class AudificationSoundsPhill {
    PApplet a;
    float[] data;

    public AudificationSoundsPhill(PApplet a) {
        this.a = a;
        data = new float[50];
    }
    public AudificationSoundsPhill(PApplet a, int n) {
        this.a = a;
        data = new float[n];
    }
}
