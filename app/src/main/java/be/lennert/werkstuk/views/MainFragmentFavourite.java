package be.lennert.werkstuk.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.ListViewAdapter;


import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.IRecipe;
import be.lennert.werkstuk.utils.StringUtils;


public class MainFragmentFavourite extends Fragment implements ListViewAdapter.ListItemClickListener {

    public static final String RECIPE_ID = "be.lennert.werkstuk.DB_RECIPE_ID";

    private ArrayList<DBRecipe> recipes = new ArrayList<DBRecipe>();
     ArrayList<IRecipe> mData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadView();
    }

    public void loadView(){

        isRecyclerViewEmpty(recipes.isEmpty());
    }

    private void isRecyclerViewEmpty(boolean isEmpty) {
        final TextView emptyView = (TextView) getView().findViewById(R.id.empty_rv_favourite);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rv_favourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(isEmpty) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText(StringUtils.toTitleCase(getString(R.string.NoSaved)));
        }else{
            mData = new ArrayList<IRecipe>();

            for (DBRecipe r:recipes)  mData.add((IRecipe) r);
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            ListViewAdapter adapter = new ListViewAdapter(mData,this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(MainFragmentFavourite.this.getActivity(), DetailActivity.class);
        intent.putExtra(RECIPE_ID,id);
        startActivity(intent);
    }
}