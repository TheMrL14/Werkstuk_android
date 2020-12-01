package be.lennert.werkstuk.views;

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
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.IngredientListViewAdapter;
import be.lennert.werkstuk.adapters.ListViewAdapter;
import be.lennert.werkstuk.model.apimodels.Joke;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.APIListener;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.utils.StringUtils;
import be.lennert.werkstuk.viewmodel.CardViewModel;
import be.lennert.werkstuk.viewmodel.FavouritesViewModel;
import retrofit2.Response;

import static be.lennert.werkstuk.controllers.FoodAPIClient.FOODAPI;

public class MainFragmentShoppingList extends Fragment {

    public CardViewModel vm;

    private ArrayList<DBCardIngredient> ingredients = new ArrayList<DBCardIngredient>();

    public MainFragmentShoppingList() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this).get(CardViewModel.class);
        loadView();
        view.findViewById(R.id.btnClearCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.nuke();
                loadView();
            }
        });

        vm.getAllIngredients().observe(getViewLifecycleOwner(), new Observer<List<DBCardIngredient>>() {
            @Override
            public void onChanged(List<DBCardIngredient> dbIngredient) {
                ingredients = new ArrayList<>();
                for(DBCardIngredient i : dbIngredient){
                    File file = ImageUtils.getLocalFile(getContext(), i.getName());
                    if(file.exists())i.setImage(file.getAbsolutePath());
                    ingredients.add(i);
                }
                loadView();
            }
        });
    }

    public void loadView(){

        isRecyclerViewEmpty(ingredients.isEmpty());
    }
    private void isRecyclerViewEmpty(boolean isEmpty) {
        final TextView emptyView = (TextView) getView().findViewById(R.id.empty_rv_search);
        final RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvShoppingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (isEmpty) {
           //
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            IngredientListViewAdapter adapter = new IngredientListViewAdapter(ingredients, getContext(), new IngredientListViewAdapter.CheckBoxListener() {
                @Override
                public void onItemClick(String id, boolean isDone) {
                    for(DBCardIngredient i : ingredients) {
                        if(i.getId() == id){
                            i.setDone(isDone);
                            vm.setIsDone(i);
                        }
                    }
                }
            });
            recyclerView.setAdapter(adapter);
            System.out.println(ingredients);
        }
    }

}