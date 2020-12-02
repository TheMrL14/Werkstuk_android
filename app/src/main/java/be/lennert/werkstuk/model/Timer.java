package be.lennert.werkstuk.model;

import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

public class Timer {

    private int id;
    private long mMillisInFuture;
    private final long mCountdownInterval = TimeUnit.SECONDS.toMillis(1);
    private long mPauseTime;
    private boolean isPaused = false;
    private String title;
    private CountDownTimer timer;

    public CountDownTimer StartTimer(){
        return new CountDownTimer(mMillisInFuture,mCountdownInterval){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };


    }

    private long timeToMilis(long h, long m, long s){
        long mili = 0;
        mili += TimeUnit.HOURS.toMillis(h);
        mili += TimeUnit.MINUTES.toMillis(m);
        mili += TimeUnit.SECONDS.toMillis(s);
        return mili;
    }


    public Timer(int id, String title, long h, long m, long s) {
        this.id = id;
        this.title = title;
        this.isPaused = false;
        this.mMillisInFuture =  mMillisInFuture = timeToMilis(h,m,s);
        this.timer = StartTimer();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
