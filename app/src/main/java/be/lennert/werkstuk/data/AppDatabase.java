package be.lennert.werkstuk.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.lennert.werkstuk.dao.CardDAO;
import be.lennert.werkstuk.dao.RecipeDAO;
import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;
import be.lennert.werkstuk.model.dbmodels.DBStep;

@Database(entities = {DBRecipe.class, DBIngredient.class, DBStep.class, DBCardIngredient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();
    public abstract CardDAO cardDAO();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context myContext) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(myContext.getApplicationContext(),
                            AppDatabase.class, "recipe_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase.Callback sAppDatabaseCallback = new AppDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //Populate
        }
    };
}

