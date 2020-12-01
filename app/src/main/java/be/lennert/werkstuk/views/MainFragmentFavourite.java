package be.lennert.werkstuk.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.CONSTANTS;
import be.lennert.werkstuk.MainActivity;
import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.ListViewAdapter;


import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.IRecipe;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.utils.StringUtils;
import be.lennert.werkstuk.viewmodel.FavouritesViewModel;
import be.lennert.werkstuk.viewmodel.RecipeViewModel;


public class MainFragmentFavourite extends Fragment implements ListViewAdapter.ListItemClickListener {


    public FavouritesViewModel vm;
    SearchView searchView;
    RecyclerView recyclerView;
    TextView emptyView;

    private ArrayList<DBRecipe> recipes = new ArrayList<DBRecipe>();
     ArrayList<IRecipe> mData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        downloadData();
        loadView();

    }


    public void downloadData(){
        vm.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<DBRecipe>>() {
            @Override
            public void onChanged(List<DBRecipe> dbRecipes) {
                recipes = new ArrayList<>();
                for(DBRecipe r : dbRecipes){
                    File file = ImageUtils.getLocalFile(getContext(),r.getImage());
                    if(file.exists()) r.setImage(file.getAbsolutePath());
                    recipes.add(r);
                }
                loadView();
            }
        });
    }


    public void loadView(){
        isRecyclerViewEmpty(recipes.isEmpty());
    }

    private void isRecyclerViewEmpty(boolean isEmpty) {
        emptyView = (TextView) getView().findViewById(R.id.empty_rv_favourite);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rv_favourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(isEmpty) hideRecyclerView();
        else showRecyclerView();

    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(MainFragmentFavourite.this.getActivity(), DetailActivity.class);
        intent.putExtra(CONSTANTS.RECIPE_ID,id);
        intent.putExtra(CONSTANTS.CONNECTION,false);
        startActivity(intent);
    }

    private void hideRecyclerView(){
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(StringUtils.toTitleCase(getString(R.string.NoSaved)));
    }

    private void showRecyclerView(){
        mData = new ArrayList<IRecipe>();
        for (DBRecipe r:recipes)  mData.add((IRecipe) r);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        ListViewAdapter adapter = new ListViewAdapter(mData,this,false);
        recyclerView.setAdapter(adapter);
    }
}