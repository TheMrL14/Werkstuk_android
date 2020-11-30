package be.lennert.werkstuk.controllers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import be.lennert.werkstuk.dao.RecipeDAO;
import be.lennert.werkstuk.data.AppDatabase;
import be.lennert.werkstuk.model.apimodels.Recipe;
import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.dbmodels.DBStep;
import be.lennert.werkstuk.model.dbmodels.dbrelationmodels.DBRecipeWithIngredients;
import be.lennert.werkstuk.model.dbmodels.dbrelationmodels.DBStepWithIngredients;
import be.lennert.werkstuk.model.interfaces.IIngredient;
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
    public void getRecipeByID(int id,TaskListener l) {
        new getRecipeByIdAsyncTask(recipeDAO,l).execute(id);
    }

    public void insert (DBRecipe r,TaskListener l) {
        new insertAsyncTask(recipeDAO,l).execute(r);
    }

    public void isRecipeSaved(int id, TaskListener l){new isRecipeSavedAsyncTask(recipeDAO,l).execute(id);}

    public void deleteRecipe(DBRecipe r, TaskListener l){new deleteAsyncTask(recipeDAO,l).execute(r);}

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

    private static class deleteAsyncTask extends AsyncTask<DBRecipe, Void, Void> {

        private RecipeDAO mAsyncTaskDao;

        TaskListener listener;

        deleteAsyncTask(RecipeDAO dao, TaskListener listener)
        {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(final DBRecipe... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onTaskCompleted(true);
        }
    }

    private static class getRecipeByIdAsyncTask extends AsyncTask<Integer, Void, DBRecipe> {

        private RecipeDAO mAsyncTaskDao;
        TaskListener listener;

        getRecipeByIdAsyncTask(RecipeDAO dao, TaskListener listener)
        {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected DBRecipe doInBackground(final Integer... params) {
            int id = params[0];
            DBRecipeWithIngredients returnVal = mAsyncTaskDao.getRecipeById(id);
            DBRecipe recipe = returnVal.recipe;
            recipe.setIngredients(returnVal.recipeIngredients);
            List<DBStep> steps = mAsyncTaskDao.getStepsWithId(id);
            for(DBStep s : steps){

                s.setIngredientsFromDB(mAsyncTaskDao.getIngredientsWithId(s.getStepId()));
            }
            recipe.setDbsteps(steps);
            return recipe;
        }

        @Override
        protected void onPostExecute(DBRecipe recipe) {
            super.onPostExecute(recipe);
            listener.onTaskCompleted(recipe);
        }
    }
}


