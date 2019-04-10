package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;

public class VisualGraph {
    float[] data;
    PApplet a;

    // default constructor with 50 data points
    public VisualGraph(PApplet a) {
        this.a = a;
        data = new float[50];
    }

    //constructor with number of values to store
    public VisualGraph(PApplet a, int n) {
        this.a = a;
        data = new float[n];
    }

    // update first node and shift the rest // there is a way to make this faster. with mods and head index.
    public void updateData(float d) {
        for(int i = 0; i<data.length - 1; i++){
            data[data.length-1 -i] = data[data.length-2-i];
        }
        data[0] = d;
    }

    //calls other draw calls later to make visual generalize later
    public void draw(){
        a.strokeWeight(4);
        a.color(0);

        for(int i = 0; i<data.length - 1; i++){
            a.line(i*10, data[i] * 50 + 1000, (i+1)*10, data[i+1]*50 +1000);
        }
    }

    // height is just the scale factor of the amplitude
    public void draw(float x, float y, float w, float h){
        a.strokeWeight(4);
        a.color(0);

        float spacing = w / data.length;

        for(int i = 0; i<data.length - 1; i++){
            a.line(i * spacing + x, data[i] * h + y, (i+1)* spacing + x, data[i+1]*h +y);
        }
    }

}
