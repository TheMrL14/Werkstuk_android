package be.lennert.werkstuk.controllers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import be.lennert.werkstuk.dao.CardDAO;
import be.lennert.werkstuk.dao.RecipeDAO;
import be.lennert.werkstuk.data.AppDatabase;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.interfaces.TaskListener;

public class CardRepository {

    private CardDAO dao;
    private LiveData<List<DBCardIngredient>> allIngredients;

    public CardRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        dao = db.cardDAO();
        allIngredients = dao.loadAllIngredients();
    }

    public LiveData<List<DBCardIngredient>> getAllIngredients() {
        return allIngredients;
    }

    public void insert (List<DBCardIngredient> i, TaskListener l) {
        new CardRepository.insertAsyncTask(dao,l).execute(i);
    }

    public void nukeTable(){new nukeAsyncTask(dao).execute();}

    public void setDone(DBCardIngredient i){
        new CardRepository.booleanAsyncTask(dao).execute(i);
    }

    private static class insertAsyncTask extends AsyncTask<List<DBCardIngredient>, Void, Void> {

        private CardDAO mAsyncTaskDao;

        TaskListener listener;

        insertAsyncTask(CardDAO dao, TaskListener listener)
        {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(final List<DBCardIngredient>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onTaskCompleted(true);
            //System.out.println((List<DBRecipe>) mAsyncTaskDao.loadAllRecipes());
        }
    }

    private static class booleanAsyncTask extends AsyncTask<DBCardIngredient, Void, Void> {

        private CardDAO mAsyncTaskDao;

        booleanAsyncTask(CardDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DBCardIngredient... params) {
            mAsyncTaskDao.setDone(params[0]);
            return null;
        }

    }

    private static class nukeAsyncTask extends AsyncTask<Object, Void, Void> {

        private CardDAO mAsyncTaskDao;

        nukeAsyncTask(CardDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            mAsyncTaskDao.nukeTable();
            return null;
        };
    }
}
