package be.lennert.werkstuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.IStep;

public class RecipeViewAdapter extends RecyclerView.Adapter<RecipeViewAdapter.ViewHolder> {

    private List<IStep> steps;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStepNr,textViewStep,textViewStepIngredients;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStepNr = (TextView) itemView.findViewById(R.id.txtStepNr);
            textViewStep = (TextView) itemView.findViewById(R.id.txtStep);
            textViewStepIngredients = (TextView) itemView.findViewById(R.id.txtStepIngredients);
        }

        public TextView getTextViewStepNr() {return textViewStepNr;}

        public TextView getTextViewStep() {return textViewStep;}

        public TextView getTextViewStepIngredients() {return textViewStepIngredients;}
    }

    public RecipeViewAdapter() {}

    public RecipeViewAdapter(List<IStep> recipe) {
       steps = recipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_step, parent, false);
        return new RecipeViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        IStep step = steps.get(position);
        String stepText = step.getStep();
        String nr = Integer.toString(step.getNumber());
        StringBuilder ingredientsList = new StringBuilder();
        if(step.getIngredients().size() > 0) {
            for (IIngredient i: step.getIngredients()) {
                ingredientsList.append(i.getName());
                ingredientsList.append(", ");
            }
        }else{
           ingredientsList.append(" ");
        }


        viewHolder.getTextViewStep().setText(stepText);
        viewHolder.getTextViewStepNr().setText(nr);
        viewHolder.getTextViewStepIngredients().setText(ingredientsList.toString());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

}
