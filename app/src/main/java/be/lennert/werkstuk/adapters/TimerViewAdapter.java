package be.lennert.werkstuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.Timer;
import be.lennert.werkstuk.utils.StringUtils;

public class TimerViewAdapter extends RecyclerView.Adapter<TimerViewAdapter.ViewHolder> {

    private ArrayList<Timer> timers;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtTitle, txtTime;
        private final Switch aSwitch;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtStepTitle);
            txtTime = (TextView) itemView.findViewById(R.id.txtClock);
            aSwitch = (Switch) itemView.findViewById(R.id.swtchClockOnOff);
        }

        //Getters
        public TextView getTxtTitle() {return txtTitle; }
        public TextView getTxtTime() {return txtTime;}
        public Switch getASwitch() {return aSwitch;}
    }

    public TimerViewAdapter(ArrayList<Timer> mData) {
        this.timers =mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timer, parent, false);

        return new TimerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String time = StringUtils.toTitleCase(timers.get(position).getUITime());
        String title = StringUtils.toTitleCase(timers.get(position).getTitle());
        boolean isRunning =!timers.get(position).isPaused();

       holder.getTxtTime().setText(time);
        holder.getTxtTitle().setText(title);
        holder.getASwitch().setChecked(isRunning);
    }

    @Override
    public int getItemCount() {
        return this.timers.size();
    }


}
