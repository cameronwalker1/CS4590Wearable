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
    AudificationSteve saqib;
    VisualGraph vgax, vgay, vgaz;

    float ax, ay, az;
    boolean rateToggle;
    public void settings() {fullScreen(P2D);}

    public void setup() {
        vgax = new VisualGraph(this,100);
        vgay = new VisualGraph(this,100);
        vgaz = new VisualGraph(this,100);

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
        saqib = new AudificationSteve(this, listener);
    }

    public void draw() {
        background(0);
        text("X: " + ax + "\nY: " + ay + "\nZ: " + az, 0, 0, width, height);

        text("Song Pos: " + file.percent(),0,500,width,height);
        text("playrate: " + (1 + (float) 0.1 * abs( ax)),0,700,width,height);

        stroke(255);


        vgax.updateData(listener.getX());
        vgay.updateData(listener.getY());
        vgaz.updateData(listener.getZ());

        vgax.draw(0, 1000, 1000, 50);
        vgay.draw(0, 1250, 1000, 50);
        vgaz.draw(0, 1500, 1000, 50);

        saqib.step();

    }

    public class AccelerometerListener implements SensorEventListener {
        private float ax;
        private float ay;
        private float az;
        public void onSensorChanged(SensorEvent event) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
            //file.rate(1 + (float) 0.1 * abs( ax));
        }

        public float getX() {
            return ax;
        }
        public float getY() {
            return ay;
        }
        public float getZ() {
            return az;
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