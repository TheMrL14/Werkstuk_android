package be.lennert.werkstuk.model;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import be.lennert.werkstuk.model.interfaces.ITimerListener;
import be.lennert.werkstuk.services.TimerService;
import be.lennert.werkstuk.utils.StringUtils;

public class Timer implements Serializable {



    private long currentTime;
    private long totalTimeLeft;
    private boolean isPaused = false;
    private boolean isFinished = false;
    private Intent intent;
    BroadcastReceiver receiver;
    private String title;
    private int id;


    public Timer(int id,String title, long h, long m, long s) {
        this.id = id;
        this.title = title;
        this.totalTimeLeft= StringUtils.timeToMilis(h,m,s);
    }

    public Timer(String title, long h, long m, long s) {
        this.title = title;
        this.totalTimeLeft= StringUtils.timeToMilis(h,m,s);
    }

    public void start(Context context,Activity activity){
        intent = new Intent(context, TimerService.class);
        intent.putExtra(TimerService.MILLIS,this.totalTimeLeft);
        intent.putExtra(TimerService.ID,this.id);
        activity.startService(intent);
    }

    public void stop(Activity activity){
        activity.stopService(intent);
    }

    public void pause(Activity activity){
        isPaused = true;
        totalTimeLeft = currentTime;
        activity.stopService(intent);
    }

    public void play(Context context,Activity activity){
        isPaused = false;
        this.start(context,activity);
    }


    public void setUITime(long time)  {
        currentTime = time;
    }

    public String getUITime(){
        return StringUtils.millisToString(currentTime);
    }

    public String getTitle() {
        return title;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setId(int id){
        this.id = id;
    }

}
