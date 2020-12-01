package be.lennert.werkstuk.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;


public class IngredientListViewAdapter extends RecyclerView.Adapter<IngredientListViewAdapter.ViewHolder> {

    private List<DBCardIngredient> ingredientList;
    final private CheckBoxListener mOnClickListener;
    private final int colorPrimary;
    private final int colorBackground;


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTitle,textViewQuantity;
        private final ImageFilterView imgView;
        private final CheckBox checkBox;
        private final LinearLayout container;

        public ViewHolder(@NonNull final View itemView)  {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.txtListTitle);
            textViewQuantity = (TextView) itemView.findViewById(R.id.txtListQuantity);
            imgView = (ImageFilterView) itemView.findViewById(R.id.imgListThumbnail);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbxList);
            container = itemView.findViewById(R.id.containerListItem);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = ((CheckBox) v);
                    String id = ingredientList.get(getAdapterPosition()).getId();
                    mOnClickListener.onItemClick(id, checkBox.isChecked());
                }
            });
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewQuantity() {
            return textViewQuantity;
        }

        public ImageFilterView getImgView() {
            return imgView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public LinearLayout getContainer() {
            return container;
        }
    }

    public IngredientListViewAdapter(List<DBCardIngredient> mData, Context context, CheckBoxListener checkBoxListener) {
        this.ingredientList = mData;
        this.mOnClickListener = checkBoxListener;
        this.colorPrimary = context.getResources().getColor(R.color.colorPrimary);
        this.colorBackground = context.getResources().getColor(R.color.white);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient_list, parent, false);

        return new IngredientListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //Get variables for items
        final String ingredientName = ingredientList.get(position).getName();
        final String quantity = ingredientList.get(position).getMetricAmount(1);
        final String image = (String) ingredientList.get(position).getImage();
        final boolean isDone = ingredientList.get(position).isDone();
        Bitmap bmp = BitmapFactory.decodeFile(image);

        //Set Items variables
        viewHolder.getTextViewTitle().setText(ingredientName);
        viewHolder.getTextViewQuantity().setText(quantity);
        viewHolder.getCheckBox().setChecked(isDone);
        viewHolder.getImgView().setImageBitmap(bmp);

        //Set Background color for item
        if(isDone)viewHolder.getContainer().setBackgroundColor(colorPrimary);
        else viewHolder.getContainer().setBackgroundColor(colorBackground);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    //listener for onClick
    public interface CheckBoxListener {
        void onItemClick(String id, boolean isDone);
    }

}
