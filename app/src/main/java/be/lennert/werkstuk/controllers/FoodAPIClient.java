package be.lennert.werkstuk.controllers;

import android.util.Log;

import java.util.ArrayList;

import be.lennert.werkstuk.model.interfaces.APIListener;
import be.lennert.werkstuk.model.apimodels.ComplexSearchModel;
import be.lennert.werkstuk.model.apimodels.Joke;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.apimodels.ResponseWrapper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//https://medium.com/@sontbv/retrofit-2-for-beginners-creating-an-android-api-client-3c4370e1118
//https://stackoverflow.com/questions/41160019/how-can-i-update-activity-fragment-ui-from-retrofit-onresponse

public class FoodAPIClient {

    //2 API KEYS to handle more requests in development
    private final String API_KEY = "4326a1e7b13745e6b7719f97fc3a5ae8"; //hotmail account
    //private final String API_KEY = "624b181a64564261983ad51b102a56df"; //gmail account
    private final static String BASE_API_URL = "https://api.spoonacular.com/";
    private final int totalSearches = 50;

    private static FoodAPIClient instance;
    private IFoodService request;

    private ArrayList<ComplexSearchModel> returnList;

    // Singleton for API
    public static FoodAPIClient FOODAPI = getInstance();
    private FoodAPIClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(IFoodService.class);
    }

    private static synchronized FoodAPIClient getInstance() {
        if (instance == null)
            instance = new FoodAPIClient();
        return instance;
    }

    // API methods

    //GET recipe by query
    public void searchRecipes(String query, final APIListener callback){
        Call<ResponseWrapper> call = request.searchRecipes(query,API_KEY,totalSearches);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if(response.isSuccessful()) {
                    callback.call(response);
                } else{
                    Log.d("ERROR","error");
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.d("ERROR","error");
            }
        });

    }

    //GET recipe by id
    public void getRecipeById(int id,final APIListener callback){
        Call<Recipe> call = request.searchRecipeById(id,false,API_KEY);
        call.enqueue(new Callback<Recipe>(){
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                callback.call(response);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    //Get random Joke
    public void getRandomJoke( final APIListener callback){
        Call<Joke> call = request.getRandomJoke(API_KEY);
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if(response.isSuccessful()){
                    callback.call(response);
                }else{
                    Log.d("ERROR","error");
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {

            }
        });
    }




}
