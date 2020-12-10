package be.lennert.werkstuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.Timer;
import be.lennert.werkstuk.model.interfaces.ITimerClickListener;
import be.lennert.werkstuk.model.interfaces.ITimerListener;
import be.lennert.werkstuk.utils.StringUtils;

public class TimerViewAdapter extends RecyclerView.Adapter<TimerViewAdapter.ViewHolder>  {

    private ArrayList<Timer> timers;
    private static ITimerClickListener listener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitle, txtTime;
        private final ImageButton aSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtStepTitle);
            txtTime = (TextView) itemView.findViewById(R.id.txtClock);
            aSwitch = (ImageButton) itemView.findViewById(R.id.swtchClockOnOff);


        }

        //Getters
        public TextView getTxtTitle() {return txtTitle; }
        public TextView getTxtTime() {return txtTime;}
        public ImageButton getASwitch() {return aSwitch;}


    }


    public TimerViewAdapter(ArrayList<Timer> mData, ITimerClickListener l) {
        this.listener = l;
        this.timers =mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timer, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ITimerClickListener(holder.getAdapterPosition());
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String time = StringUtils.toTitleCase(timers.get(position).getUITime());
        String title = StringUtils.toTitleCase(timers.get(position).getTitle());
        boolean isRunning =!timers.get(position).isPaused();

        holder.getTxtTime().setText(time);
        holder.getTxtTitle().setText(title);

        if(isRunning)holder.getASwitch().setImageResource(R.drawable.pause);
        else holder.getASwitch().setImageResource(R.drawable.play);
    }




    @Override
    public int getItemCount() {
        return this.timers.size();
    }

    public void updateData(Timer t){
        this.timers.set(t.getId(),t);
    }

}
