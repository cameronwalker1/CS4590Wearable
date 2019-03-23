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
    float ax, ay, az;
    boolean rateToggle;
    public void settings() {fullScreen(P2D);}

    public void setup() {
        file = new SoundFile(this, "techno.wav");
        file.loop();
        // Create the audiosample based on the data, set framerate to play 200 oscillations/second
        cp5 = new ControlP5(this);
//        cp5.addButton("Playback")
//                .setCaptionLabel("Toggle Playback")
//                .setPosition(600, 600)
//                .setSize(600, 600);
        context = getActivity();
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new AccelerometerListener();
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        textFont(createFont("SansSerif", 30 * displayDensity));
    }

    public void draw() {
        background(0);
        text("X: " + ax + "\nY: " + ay + "\nZ: " + az, 0, 0, width, height);
    }
    public class AccelerometerListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
//            if (rateToggle)
            file.rate(abs((int) ax * (int) ay * (int) az) / 100);

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

//    void PlayBack() {
//        rateToggle = !rateToggle;
//    }
}