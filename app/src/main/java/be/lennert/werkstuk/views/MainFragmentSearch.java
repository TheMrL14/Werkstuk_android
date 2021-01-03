package be.lennert.werkstuk.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.lennert.werkstuk.CONSTANTS;
import be.lennert.werkstuk.MainActivity;
import be.lennert.werkstuk.R;
import be.lennert.werkstuk.adapters.ListViewAdapter;
import be.lennert.werkstuk.model.interfaces.APIListener;
import be.lennert.werkstuk.model.interfaces.IRecipe;
import be.lennert.werkstuk.model.apimodels.ComplexSearchModel;
import be.lennert.werkstuk.model.apimodels.Joke;
import be.lennert.werkstuk.model.apimodels.ResponseWrapper;
import be.lennert.werkstuk.utils.StringUtils;
import retrofit2.Response;

import static be.lennert.werkstuk.controllers.FoodAPIClient.FOODAPI;

public class MainFragmentSearch extends Fragment implements ListViewAdapter.ListItemClickListener {


    private ArrayList<IRecipe> recipes;
    TextView emptyView;
    SearchView searchView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set view vars
        emptyView = (TextView) getView().findViewById(R.id.empty_rv_search);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvSearchedRecipes);
        searchView = (SearchView) getView().findViewById(R.id.searchBar);

        //set listeners
        searchView.setOnQueryTextListener(queryTextListener());

        //load init view
        hideRecyclerView();


    }

    private void loadView() {
        if (recipes.isEmpty())hideRecyclerView();
        else showRecyclerView();
    }

    private String createJokeText(Response<Joke> response) {
        if(getActivity() == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(getActivity().getString(R.string.NoFound));
        sb.append("\n \n");
        sb.append(response.body().getText());
        String text = sb.toString();
        return StringUtils.toTitleCase(text);
    }

    private void showRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        ListViewAdapter adapter = new ListViewAdapter(recipes, this, true);
        recyclerView.setAdapter(adapter);
    }

    private void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        FOODAPI.getRandomJoke(new APIListener<Joke>() {
            @Override
            public void call(Response<Joke> response) {
                emptyView.setText(createJokeText(response));
            }
        });
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(MainFragmentSearch.this.getActivity(), DetailActivity.class);
        intent.putExtra(CONSTANTS.RECIPE_ID, id);
        intent.putExtra(CONSTANTS.CONNECTION, true);
        startActivity(intent);
    }

    SearchView.OnQueryTextListener queryTextListener(){
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FOODAPI.searchRecipes(query, new APIListener<ResponseWrapper>() {
                    @Override
                    public void call(Response<ResponseWrapper> response) {
                        recipes = new ArrayList<IRecipe>();
                        List<ComplexSearchModel> responseBody = response.body().getResponse();
                        for (ComplexSearchModel recipe : responseBody)
                            recipes.add((IRecipe) recipe);
                        loadView();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
}
