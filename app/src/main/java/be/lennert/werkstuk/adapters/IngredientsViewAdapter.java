package be.lennert.werkstuk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.interfaces.IIngredient;

public class IngredientsViewAdapter  extends RecyclerView.Adapter<IngredientsViewAdapter.ViewHolder> {

    public static final String imagePath = "https://spoonacular.com/cdn/ingredients_100x100/";

    private List<IIngredient> ingredientList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTitle,textViewQuantity;
        private final ImageFilterView imgView ;
        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            textViewTitle = (TextView) itemView.findViewById(R.id.txtIngredientsTitle);
            textViewQuantity = (TextView) itemView.findViewById(R.id.txtIngredientsQuantity);
            imgView = (ImageFilterView) itemView.findViewById(R.id.imgIngredientsThumbnail);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }
        public TextView getTextViewQuantity() {
            return textViewQuantity;
        }
        public ImageFilterView getImageView() {
            return imgView;
        }

    }

    public IngredientsViewAdapter(List<IIngredient> mData) {
        this.ingredientList = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientsViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            String title = ingredientList.get(position).getName();
            String quantity = ingredientList.get(position).getMetricAmount(1);
            String image =imagePath + ingredientList.get(position).getImage();

            viewHolder.getTextViewTitle().setText(title);
            viewHolder.getTextViewQuantity().setText(quantity);
            Picasso.get().load(image).into(viewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

}
