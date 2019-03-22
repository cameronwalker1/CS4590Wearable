package cs4590finalproject.cs4590wearable;

import processing.core.PApplet;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class Sketch extends PApplet {
    Context context;
    SensorManager manager;
    Sensor sensor;
    AccelerometerListener listener;
    float ax, ay, az;
    public void settings() {
        size(600, 600);
    }

    public void setup() {
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
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
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
    }}