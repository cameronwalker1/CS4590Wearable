package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;

public class VisualGraph extends PApplet {
    float[] data;

    // default constructor with 50 data points
    public VisualGraph() {
        data = new float[50];
    }

    //constructor with number of values to store
    public VisualGraph(int n) {
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
    public void customDraw(){
        strokeWeight(4);
        color(0);

        for(int i = 0; i<data.length - 1; i++){
            line(i*10, data[i] * 50 + 1000, (i+1)*10, data[i+1]*50 +1000);
        }
    }
}
