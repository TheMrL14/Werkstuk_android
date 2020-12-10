package be.lennert.werkstuk.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Random;

import be.lennert.werkstuk.model.interfaces.ITimerListener;


public class TimerService extends Service {

    public static final String ID = "timer_id";
    public static final String MILLIS = "timer_time";
    public static final String LISTENER= "timer_listener";
    public static final String STATUS= "timer_is_done";
    public static final String TIME= "timer_current_time";
    public static final String TIMER= "timer_object";

    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    private CountDownTimer timer;

    private long millisUntilFinished;
    private final long mCountdownInterval = 1;
    private ITimerListener listener;

    public class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        final LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(this);
        millisUntilFinished = intent.getLongExtra(MILLIS,0);
        final Parcelable[] listener = intent.getParcelableArrayExtra(LISTENER);
        final int id = intent.getIntExtra(ID,0);

        this.timer = new CountDownTimer(millisUntilFinished,mCountdownInterval){
            @Override
            public void onTick(long millis) {
                millisUntilFinished = millis;
                Intent intent = new Intent(LISTENER);
                intent.putExtra(ID, id);
                intent.putExtra(TIME, millisUntilFinished);
                intent.putExtra(STATUS,false);
                broadcaster.sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(LISTENER);
                intent.putExtra(STATUS,false);
                broadcaster.sendBroadcast(intent);
            }
        };
        this.timer.start();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        this.timer.cancel();
        this.timer = null;
        super.onDestroy();
    }
}
