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
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.IngredientListViewAdapter;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.utils.ImageUtils;
import be.lennert.werkstuk.utils.StringUtils;
import be.lennert.werkstuk.viewmodel.CardViewModel;

public class MainFragmentShoppingList extends Fragment {

    public CardViewModel vm;

    TextView emptyView;
    RecyclerView recyclerView;
    Button btnClear;

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

        //Set view vars
        emptyView = (TextView) getView().findViewById(R.id.empty_rv_shoppingList);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvShoppingList);
        btnClear = (Button) getView().findViewById(R.id.btnClearCard);

        // Add DB connection & set item observer
        vm = ViewModelProviders.of(this).get(CardViewModel.class);
        vm.getAllIngredients().observe(getViewLifecycleOwner(), onItemChange());

        // listener for clear button
        view.findViewById(R.id.btnClearCard).setOnClickListener(deleteAllItems());

        //load view
        loadView();
    }

    private void loadView(){
        if (ingredients.isEmpty()) hideRecyclerView();
        else showRecyclerView();
    }

    //show list and button if items in list
    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        btnClear.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

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

    //show text if list = empty
    private void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
        btnClear.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(StringUtils.toTitleCase(getString(R.string.EmptyShoppingList)));
    }

    //delete all ingredients in DB
    private View.OnClickListener deleteAllItems(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.nuke();
                loadView();
            }
        };
    }

    //Ingredient observer when items are added or deleted
    private Observer<List<DBCardIngredient>> onItemChange(){
        return new Observer<List<DBCardIngredient>>() {
            @Override
            public void onChanged(List<DBCardIngredient> dbIngredient) {
                ingredients = new ArrayList<>();
                for(DBCardIngredient i : dbIngredient){
                    //get image for ingredient if exist
                    File file = ImageUtils.getLocalFile(getContext(), i.getName());
                    if(file.exists())i.setImage(file.getAbsolutePath());
                    ingredients.add(i);
                }
                loadView();
            }
        };
    }

}