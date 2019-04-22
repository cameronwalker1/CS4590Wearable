package cs4590finalproject.cs4590wearable;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import controlP5.Button;
import controlP5.RadioButton;
import cs4590finalproject.cs4590wearable.Sketch.AccelerometerListener;

import controlP5.ControlP5;
import processing.core.PApplet;
import processing.sound.BandPass;
import processing.sound.HighPass;
import processing.sound.LowPass;
import processing.sound.Reverb;
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

    LowPass LP;
    Reverb RV;
    HighPass HP;
    BandPass BP;


    SoundFile currentInstrument;
    public static RadioButton switchInstrument;

    public AudificationSteve(PApplet a, AccelerometerListener listener) {
        LP = new LowPass(a);
        RV = new Reverb(a);
        HP = new HighPass(a);
        BP = new BandPass(a);

        this.a = a;
        nanoTime = System.nanoTime();
        file = new SoundFile(a, "beat1.wav");
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

        currentInstrument = cymbal;

    }

    //step function is called. might need to add thing for time.
    public void step() {
        long deltaTime = System.nanoTime() - nanoTime;
        count += deltaTime;
        nanoTime = System.nanoTime();
        if (count > 1000000000L) {

            LP.stop();
            RV.stop();
            HP.stop();
            BP.stop();


            count = 0;
            double threshold = Math.sqrt(Math.pow(listener.getX(), 2) + Math.pow(listener.getY(), 2)
                    + Math.pow(listener.getZ(), 2)) * 10;
            if (threshold > 20 && threshold <40 ) {
                LP.process(currentInstrument, 800);
            } else if (threshold > 40 && threshold < 60) {
                RV.process(currentInstrument);
            } else if (threshold > 60 && threshold < 100) {
                HP.process(currentInstrument, 800);
            } else if (threshold >= 100){
                BP.process(currentInstrument,600, 1000);
            }

            if (threshold > 20) {
                currentInstrument.play();
            }
        }
    }

    public void switchInstrument(int a) {

        if (a == 1) {
            currentInstrument = hihatHit;
        } else if (a == 2) {

            currentInstrument = kickHit;
        } else if(a == 3) {

            currentInstrument = singleClap;
        } else if(a == 4) {

            currentInstrument = singleTambourineHit;
        }else if(a == 5) {

            currentInstrument = snareDrumSingleHit;
        }else if(a == 6) {

            currentInstrument = tambourineLoop;
        } else {

            currentInstrument = cymbal;
        }
    }
}
