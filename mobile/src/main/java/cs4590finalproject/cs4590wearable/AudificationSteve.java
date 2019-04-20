package cs4590finalproject.cs4590wearable;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import cs4590finalproject.cs4590wearable.Sketch.AccelerometerListener;
import java.util.HashMap;

import controlP5.ControlP5;
import processing.core.PApplet;
import processing.sound.SoundFile;

// so the way this one will work is we will play sounds sounds when thresholds happen on the phone in a loop
public class AudificationSteve {
    private HashMap<Double, String> hashMap;
    SensorManager manager;
    Sensor sensor;
    Sketch.AccelerometerListener listener;
    PApplet a;
    SoundFile file;
    ControlP5 cp5;
    Context context;
    int count = 0;


    public AudificationSteve(PApplet a, AccelerometerListener listener) {
        this.a = a;

        file = new SoundFile(a, "darksamus.wav");
        file.loop();
        // Create the audiosample based on the data, set framerate to play 200 oscillations/second
        cp5 = new ControlP5(a);
        context = a.getActivity();
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//      listener = (new Sketch()).new AccelerometerListener();
        this.listener = listener;
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        a.textFont(a.createFont("SansSerif", 30 * a.displayDensity));
    }

    //step function is called. might need to add thing for time.
    public void step(){
        if (count > 1000) return;
        count++;
        double threshold = Math.pow(listener.getX(), 2) + Math.pow(listener.getY(), 2)
                + Math.pow(listener.getZ(), 2) * 100;
        if (threshold > 0 && threshold < 20) {
            file = new SoundFile(a, "cymbalHit.wav");
            file.play();
        } else if (threshold > 20 && threshold < 40) {
            file = new SoundFile(a, "cowbell.wav");
            file.play();
        } else if (threshold > 40 && threshold < 60) {
            file = new SoundFile(a, "hihatHit.wav");
            file.play();
        } else if (threshold > 60 && threshold < 80) {
            file = new SoundFile(a, "kickHit.wav");
            file.play();
        } else if (threshold > 80 && threshold < 100) {
            file = new SoundFile(a, "singleClap.wav");
            file.play();
        } else if (threshold > 100 && threshold < 120) {
            file = new SoundFile(a, "SingleTambourineHit.wav");
            file.play();
        } else if (threshold > 120 && threshold < 140) {
            file = new SoundFile(a, "snareDrumSingleHit.wav");
            file.play();
        } else {
            file = new SoundFile(a, "tambourineLoop.wav");
            file.play();
        }
    }
}
