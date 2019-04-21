package cs4590finalproject.cs4590wearable;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import cs4590finalproject.cs4590wearable.Sketch.AccelerometerListener;

import controlP5.ControlP5;
import processing.core.PApplet;
import processing.sound.SoundFile;

// so the way this one will work is we will play sounds sounds when thresholds happen on the phone in a loop
public class AudificationSteve {
    SensorManager manager;
    Sensor sensor;
    Sketch.AccelerometerListener listener;
    PApplet a;
    SoundFile file;
    ControlP5 cp5;
    Context context;
    long count = 0;
    long nanoTime;
    SoundFile cymbal;
    SoundFile cowbell;
    SoundFile hihatHit;
    SoundFile kickHit;
    SoundFile singleClap;
    SoundFile singleTambourineHit;
    SoundFile snareDrumSingleHit;
    SoundFile tambourineLoop;

    public AudificationSteve(PApplet a, AccelerometerListener listener) {
        this.a = a;
        nanoTime = System.nanoTime();
        file = new SoundFile(a, "darksamus.wav");
        file.amp(.05f);
        file.loop();
        // Create the audiosample based on the data, set framerate to play 200 oscillations/second
        cp5 = new ControlP5(a);
        context = a.getActivity();
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.listener = listener;
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        a.textFont(a.createFont("SansSerif", 30 * a.displayDensity));
        cymbal = new SoundFile(a, "cymbalHit.wav");
//        cowbell = new SoundFile(a, "cowbell.wav");
        hihatHit  = new SoundFile(a, "hihatHit.wav");
        kickHit = new SoundFile(a, "kickHit.wav");
        singleClap = new SoundFile(a, "singleClap.wav");
        singleTambourineHit = new SoundFile(a, "SingleTambourineHit.wav");
        snareDrumSingleHit = new SoundFile(a, "snareDrumSingleHit.wav");
        tambourineLoop = new SoundFile(a, "tambourineLoop.wav");
    }

    //step function is called. might need to add thing for time.
    public void step() {
        long deltaTime = System.nanoTime() - nanoTime;
        count += deltaTime;
        nanoTime = System.nanoTime();
        if (count > 1000000000L) {
            count = 0;
            double threshold = Math.sqrt(Math.pow(listener.getX(), 2) + Math.pow(listener.getY(), 2)
                    + Math.pow(listener.getZ(), 2)) * 10;
            if (threshold > 0 && threshold < 20) {
                cymbal.play();
            } else if (threshold > 20 && threshold < 40) {
               hihatHit.play();
            } else if (threshold > 40 && threshold < 60) {
                hihatHit.play();
            } else if (threshold > 60 && threshold < 80) {
               kickHit.play();
            } else if (threshold > 80 && threshold < 100) {
                singleClap.play();
            } else if (threshold > 100 && threshold < 120) {
                singleTambourineHit.play();
            } else if (threshold > 120 && threshold < 140) {
                snareDrumSingleHit.play();
            } else {
                tambourineLoop.play();
            }
        }
    }
}
