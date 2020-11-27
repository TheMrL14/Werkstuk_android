package be.lennert.werkstuk.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.RecipeViewAdapter;
import be.lennert.werkstuk.model.interfaces.IStep;


public class FragmentRecipeDetail extends Fragment {

    private List<IStep> recipe;

    public FragmentRecipeDetail() {
        //Default constructor
    }

    public FragmentRecipeDetail(List<IStep> mData) {
       this.recipe = mData;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvRecipeDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeViewAdapter(recipe));
        return view;
    }
}