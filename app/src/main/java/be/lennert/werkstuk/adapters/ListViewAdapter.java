package be.lennert.werkstuk.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.IRecipe;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {



    private List<IRecipe> recipes;
    final private ListItemClickListener mOnClickListener;
    private boolean isOnline;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView;
        private final ImageFilterView imgView ;
        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.txtRecipeThumbnail);
            imgView = (ImageFilterView) itemView.findViewById(R.id.imgRecipeThumbnail);
        }

        public TextView getTextView() {
            return textView;
        }
        public ImageFilterView getCircleImageView() {
            return imgView;
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            int id = recipes.get(pos).getId();
            mOnClickListener.onItemClick(id);

        }
    }

    public ListViewAdapter(List<IRecipe> dataSet,ListItemClickListener onClickListener, boolean isOnline){
        this.mOnClickListener = onClickListener;
        recipes = dataSet;
        this.isOnline = isOnline;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //Get variables for items
        String recipeName = recipes.get(position).getTitle();
        String image = (String) recipes.get(position).getImage();

        //set variables from items
        viewHolder.getTextView().setText(recipeName);

        //set image (if online download if offline get from local path
        if(isOnline)Picasso.get().load(image).into(viewHolder.getCircleImageView());
        else{
            Bitmap bmp = BitmapFactory.decodeFile(image);
            viewHolder.getCircleImageView().setImageBitmap(bmp);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface ListItemClickListener {
        void onItemClick(int recipeId);
    }

}


