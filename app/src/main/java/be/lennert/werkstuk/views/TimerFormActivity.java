package be.lennert.werkstuk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.lennert.werkstuk.MainActivity;
import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.Timer;
import be.lennert.werkstuk.services.TimerService;

public class TimerFormActivity extends AppCompatActivity {

    public static final String NEWTIMER = "form_activity_to_timer_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_timer_form);

        Button btn = findViewById(R.id.btnSaveTimer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtHour = (EditText) findViewById(R.id.txtHour);
                EditText txtMinutes = (EditText) findViewById(R.id.txtMinutes);
                EditText txtSeconds = (EditText) findViewById(R.id.txtSeconds);

                long h = checkIfEmpty(txtHour);
                long m = checkIfEmpty(txtMinutes);
                long s = checkIfEmpty(txtSeconds);
                String title =  ((EditText) findViewById(R.id.txtTitle)).getText().toString();

                Intent i = new Intent(v.getContext(), MainActivity.class);

                int id = MainFragmentTimer.timers.size();
                i.putExtra(NEWTIMER,true);
                MainFragmentTimer.timers.add(new Timer(id,title,h,m,s));
                MainFragmentTimer.newTimerID = id;
                startActivity(i);
            }
        });


    }
    private long checkIfEmpty(EditText txt){
        if(txt.getText().length() > 0)return Long.parseLong(txt.getText().toString());
        else return 0;
    }

}