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
import be.lennert.werkstuk.adapters.IngredientsViewAdapter;
import be.lennert.werkstuk.model.interfaces.IIngredient;

public class FragmentIngredientDetail extends Fragment {

    private List<IIngredient> ingredients;

    public FragmentIngredientDetail() {
        //Default constructor
    }

    public FragmentIngredientDetail(List<IIngredient> mData) {
        ingredients = mData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_detail, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvIngredientsDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new IngredientsViewAdapter(ingredients));
        return view;
    }

}