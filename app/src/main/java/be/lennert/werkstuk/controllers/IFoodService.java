package be.lennert.werkstuk.controllers;


import be.lennert.werkstuk.model.apimodels.Joke;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.apimodels.ResponseWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IFoodService {

    @GET("/recipes/complexSearch")
    Call<ResponseWrapper> searchRecipes(@Query("query") String query, @Query("apiKey") String apiKey,@Query("number") int number);

    @GET("/food/jokes/random")
    Call<Joke> getRandomJoke(@Query("apiKey") String apiKey);

    @GET("/recipes/{id}/information")
    Call<Recipe> searchRecipeById(@Path("id") int id, @Query("includeNutrition") boolean includeNutrition, @Query("apiKey") String apiKey);
}
