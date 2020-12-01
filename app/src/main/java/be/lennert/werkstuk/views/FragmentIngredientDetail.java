package be.lennert.werkstuk.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.IngredientsViewAdapter;
import be.lennert.werkstuk.model.DBingredientList;
import be.lennert.werkstuk.model.apimodels.ExtendedIngredient;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.model.interfaces.TaskListener;
import be.lennert.werkstuk.utils.DownloadImage;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.utils.StringUtils;
import be.lennert.werkstuk.viewmodel.CardViewModel;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;

public class FragmentIngredientDetail extends Fragment {

    private List<IIngredient> ingredients;
    private int portions = 1;
    TextView txtPortions;
    private boolean isOnline;

    private CardViewModel viewModel;

    public FragmentIngredientDetail() {
        //Default constructor
    }

    public FragmentIngredientDetail(List<IIngredient> mData,boolean isOnline) {
        ingredients = mData;
        this.isOnline = isOnline;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CardViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_detail, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvIngredientsDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //IF offline fill with offline image
        if(!isOnline) ingredients = fillListWithImg(ingredients);

        recyclerView.setAdapter(new IngredientsViewAdapter(ingredients, portions,isOnline));

        txtPortions = (TextView) view.findViewById(R.id.txtPortions);
        setPortions(view,recyclerView);

        setBtnOnclickMethods(view,recyclerView);

        return view;
    }

    // download image
    private void downloadImage(List<IIngredient> list){
        for(IIngredient i : list){
            final String imagePath =  StringUtils.generateInternalImagePath(i.getName());
            new DownloadImage(getContext(),imagePath, new TaskListener<Boolean>() {
                @Override
                public void onTaskCompleted(Boolean b) {}
            }).execute((String) ImageUtils.imagePathIngredients + i.getImage());
        }
    }

    //upload list to Database ()
    private void uploadToDb(List<DBCardIngredient> list){
        viewModel.insertIngredients(list, new TaskListener() {
            @Override
            public void onTaskCompleted(Object o) {
                viewModel.getAllIngredients().observe(getViewLifecycleOwner(), new Observer<List<DBCardIngredient>>() {
                    @Override
                    public void onChanged(List<DBCardIngredient> ingredients) {
                        //DO SOMETHING AFTER UPLOAD
                    }
                });
            }
        });
    }

    // Button OnCLick Listeners
    private void setBtnOnclickMethods(View view, final RecyclerView recyclerView){

        Button btnRemove = (Button) view.findViewById(R.id.btnSub);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePortions(v);
                setPortions(v,recyclerView);

            }
        });

        Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPortions(v);
                setPortions(v,recyclerView);
            }
        });

        FloatingActionButton btnAddToCard = (FloatingActionButton) view.findViewById(R.id.btnToCart);
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(ingredients);
                uploadToDb(new DBingredientList(ingredients,portions).getIngredients());
            }
        });
    }

    // Portions methods
    private void removePortions(View view) {
        if(this.portions >1)this.portions--;
    }

    private void addPortions(View view) {
        if(this.portions <99)this.portions++;
    }

    private void setPortions(View view, RecyclerView recyclerView){


        StringBuilder sb = new StringBuilder("");
        sb.append(portions);
        sb.append(" ");
        if(portions == 1)sb.append(getString(R.string.sersving));
        else sb.append(getString(R.string.servings));
        txtPortions.setText(sb.toString());
        recyclerView.setAdapter(new IngredientsViewAdapter(ingredients, portions,isOnline));
    }

    private List<IIngredient> fillListWithImg(List<IIngredient> list){
        ArrayList<IIngredient> ingredientsWithImg = new ArrayList<>();
        for(IIngredient i : list){
            DBIngredient ing = (DBIngredient) i;
            File file = ImageUtils.getLocalFile(getContext(), i.getName());
            if(file.exists())ing.setImage(file.getAbsolutePath());
            ingredientsWithImg.add(ing);
        }
        return ingredientsWithImg;
    }
}