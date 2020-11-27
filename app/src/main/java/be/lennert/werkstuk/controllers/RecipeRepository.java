package be.lennert.werkstuk.controllers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import be.lennert.werkstuk.dao.RecipeDAO;
import be.lennert.werkstuk.data.AppDatabase;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.TaskListener;

public class RecipeRepository {
    private RecipeDAO recipeDAO;
    private LiveData<List<DBRecipe>> allRecipes;

    public RecipeRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        recipeDAO = db.recipeDAO();
        allRecipes = recipeDAO.loadAllRecipes();
    }

    public LiveData<List<DBRecipe>> getAllRecipes() {
        return allRecipes;
    }

    public void insert (DBRecipe r,TaskListener l) {
        new insertAsyncTask(recipeDAO,l).execute(r);
    }

    public void isRecipeSaved(int id, TaskListener l){
        new isRecipeSavedAsyncTask(recipeDAO,l).execute(id);
    }

    private static class insertAsyncTask extends AsyncTask<DBRecipe, Void, Void> {

        private RecipeDAO mAsyncTaskDao;

        TaskListener listener;

        insertAsyncTask(RecipeDAO dao, TaskListener listener)
        {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(final DBRecipe... params) {
            mAsyncTaskDao.insertFull(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onTaskCompleted(true);
            //System.out.println((List<DBRecipe>) mAsyncTaskDao.loadAllRecipes());
        }


    }

    private static class isRecipeSavedAsyncTask extends AsyncTask<Integer, Void, Boolean> {

        private RecipeDAO mAsyncTaskDao;
        TaskListener listener;

        isRecipeSavedAsyncTask(RecipeDAO dao, TaskListener listener)
        {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(final Integer... params) {
             int id = mAsyncTaskDao.recipeIsSaved(params[0]);
             return id !=0;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            listener.onTaskCompleted(bool);
        }
    }



}


