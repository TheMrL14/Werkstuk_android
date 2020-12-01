package be.lennert.werkstuk.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import be.lennert.werkstuk.utils.ImageUtils;

public class IngredientsViewAdapter  extends RecyclerView.Adapter<IngredientsViewAdapter.ViewHolder> {



    private List<IIngredient> ingredientList;
    private int portions;
    private boolean isOnline;

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

    public IngredientsViewAdapter(List<IIngredient> mData, int portions, boolean isOnline) {
        this.ingredientList = mData;
        this.portions = portions;
        this.isOnline = isOnline;
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
        //
            String title = ingredientList.get(position).getName();
            String quantity = ingredientList.get(position).getMetricAmount(this.portions);
            String image = ImageUtils.imagePathIngredients + ingredientList.get(position).getImage();

            viewHolder.getTextViewTitle().setText(title);
            viewHolder.getTextViewQuantity().setText(quantity);
           if(isOnline) Picasso.get().load(image).into(viewHolder.getImageView());
           else{
               Bitmap bmp = BitmapFactory.decodeFile(image);
               viewHolder.getImageView().setImageBitmap(bmp);
           }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setPortions(int portions){
        this.portions = portions;
    }
}
