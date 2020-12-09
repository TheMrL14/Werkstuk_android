package be.lennert.werkstuk.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.IngredientListViewAdapter;
import be.lennert.werkstuk.adapters.TimerViewAdapter;
import be.lennert.werkstuk.model.Timer;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.services.TimerService;
import be.lennert.werkstuk.utils.StringUtils;

public class MainFragmentTimer extends Fragment {



    public static ArrayList<Timer> timers = new ArrayList<Timer>();
    public static int newTimerID =-1;

    private BroadcastReceiver receiver;
    private TextView emptyView;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create listener for timers on tick
        receiver = setUpReceiver();
        //check newTimerID for new timers added
        checkAndStartNewTimers();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tab_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set view vars
        emptyView = (TextView) view.findViewById(R.id.empty_rv_timer);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_timers);

        //CREATE AND SET FILTER
        IntentFilter filter = createFilter();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                filter
        );

        // Set Listener on btn => form page
        getView().findViewById(R.id.btnToTimerForm).setOnClickListener(toFormNewTimer());

        //setup view
        loadView();
    }

    public void loadView(){
        if(getView() == null)  return;
        if (timers.isEmpty()) hideRecyclerView();
        else showRecyclerView();
    }

    private void showRecyclerView(){
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TimerViewAdapter adapter = new TimerViewAdapter(timers);
        recyclerView.setAdapter(adapter);
    }

    private void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(StringUtils.toTitleCase(getString(R.string.EmptyTimerList)));
    }

    private void checkAndStartNewTimers(){
        if(newTimerID ==  -1) return;
        timers.get(newTimerID).start(getContext(),getActivity());
        newTimerID = -1;
    }

    private BroadcastReceiver setUpReceiver(){
        return new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isDone = intent.getBooleanExtra(TimerService.STATUS, false);
                int id = intent.getIntExtra(TimerService.ID,0);
                if(!isDone) {
                    long timeInMillis = intent.getLongExtra(TimerService.TIME, 0);
                    timers.get(id).setUITime(timeInMillis);
                    loadView();
                }else {
                    //
                }
            };
        };
    }

    private IntentFilter createFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimerService.STATUS);
        filter.addAction(TimerService.LISTENER);
        filter.addAction(TimerService.ID);
        return filter;
    }

    private View.OnClickListener toFormNewTimer(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimerFormActivity.class);
                startActivity(intent);
            }
        };
    }
}