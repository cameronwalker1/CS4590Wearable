package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import controlP5.*;
import processing.sound.*;

public class Sketch extends PApplet {
    SoundFile file;
    ControlP5 cp5;
    Context context;
    SensorManager manager;
    Sensor sensor;
    AccelerometerListener listener;

    VisualGraph vg1;

    float[] axs, ays, azs;
    float[] vxs, vys, vzs;
    float ax, ay, az;
    boolean rateToggle;
    public void settings() {fullScreen(P2D);}

    public void setup() {
        vg1 = new VisualGraph(100);

        axs = new float[100];
        ays = new float[100];
        azs = new float[100];

        vxs = new float[100];
        vys = new float[100];
        vzs = new float[100];

        file = new SoundFile(this, "darksamus.wav");
        file.loop();
        // Create the audiosample based on the data, set framerate to play 200 oscillations/second
        cp5 = new ControlP5(this);
//        cp5.addButton("Playback")
//                .setCaptionLabel("Toggle Playback")
//                .setPosition(600, 600)
//                .setSize(400, 400);
        context = getActivity();
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        listener = new AccelerometerListener();
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        textFont(createFont("SansSerif", 30 * displayDensity));
    }

    public void draw() {
        background(0);
        text("X: " + ax + "\nY: " + ay + "\nZ: " + az, 0, 0, width, height);

        text("Song Pos: " + file.percent(),0,500,width,height);
        text("playrate: " + (1 + (float) 0.1 * abs( ax)),0,700,width,height);

        stroke(255);
//        line(0,0,100,100);
        //shift all data over one starting from the back
//        for(int i = 0; i<axs.length - 1; i++){
//            axs[axs.length-1 -i] = axs[axs.length-2-i];
//        }
//        axs[0] = ax; //add new data in front
//
        vg1.updateData(ax);


        for(int i = 0; i<ays.length - 1; i++){
            ays[ays.length-1 -i] = ays[ays.length-2-i];
        }
        ays[0] = ay; //add new data in front

        for(int i = 0; i<azs.length - 1; i++){
            azs[azs.length-1 -i] = azs[azs.length-2-i];
        }
        azs[0] = az; //add new data in front



//        for(int i = 0; i<vxs.length - 1; i++){
//            vxs[vxs.length-1 -i] = vxs[vxs.length-2-i];
//        }
//        vxs[0] = vxs[1] + axs[0];
//
//        for(int i = 0; i<vys.length - 1; i++){
//            vys[vys.length-1 -i] = vys[vys.length-2-i];
//        }
//        vys[0] = vys[1] + ays[0];
//
//        for(int i = 0; i<vzs.length - 1; i++){
//            vzs[vzs.length-1 -i] = vzs[vzs.length-2-i];
//        }
//        vzs[0] = vzs[1] + azs[0];


        strokeWeight(4);
        color(0);

        vg1.customDraw();
        //below draws the ax time line
//        for(int i = 0; i<axs.length - 1; i++){
//            line(i*10, axs[i] * 50 + 1000, (i+1)*10, axs[i+1]*50 +1000);
//        }

        //below draws the ax time line
        for(int i = 0; i<ays.length - 1; i++){
            line(i*10, ays[i] * 50 + 1200, (i+1)*10, ays[i+1]*50 +1200);
        }

        //below draws the ax time line
        for(int i = 0; i<azs.length - 1; i++){
            line(i*10, azs[i] * 50 + 1400, (i+1)*10, azs[i+1]*50 +1400);
        }


        //velocities
//
//        for(int i = 0; i<vxs.length - 1; i++){
//            line(i*10, vxs[i] * 50 + 1600, (i+1)*10, vxs[i+1]*50 +1600);
//        }
//
//        //below draws the ax time line
//        for(int i = 0; i<vys.length - 1; i++){
//            line(i*10, vys[i] * 50 + 1800, (i+1)*10, vys[i+1]*50 +1800);
//        }
//
//        //below draws the ax time line
//        for(int i = 0; i<vzs.length - 1; i++){
//            line(i*10, vzs[i] * 50 + 2000, (i+1)*10, vzs[i+1]*50 +2000);
//        }

    }
    public class AccelerometerListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
//            if (rateToggle)
            file.rate(1 + (float) 0.1 * abs( ax));

        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }
    public void onResume() {
        super.onResume();
        if (manager != null) {
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void onPause() {
        super.onPause();
        if (manager != null) {
            manager.unregisterListener(listener);
        }
    }
}