package be.lennert.werkstuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.lennert.werkstuk.R;

public class TimerViewAdapter extends RecyclerView.Adapter<TimerViewAdapter.ViewHolder> {

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
        public Switch getaSwitch() {return aSwitch;}
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
        //
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
