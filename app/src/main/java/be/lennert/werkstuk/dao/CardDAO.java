package be.lennert.werkstuk.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import be.lennert.werkstuk.model.dbmodels.DBCardIngredient;
import be.lennert.werkstuk.model.dbmodels.DBRecipe;

@Dao
public abstract class CardDAO {


    @Transaction
    @Query("SELECT * FROM card_ingredients")
    public abstract LiveData<List<DBCardIngredient>> loadAllIngredients();

    public void insert(List<DBCardIngredient> ingredients){
        for(DBCardIngredient ingredient : ingredients) {
            DBCardIngredient dbIngredient = getIngredientById(ingredient.getId());
            if (dbIngredient == null) insertInToDb(ingredient);
            else {
                dbIngredient.addAmount(ingredient.getAmount());
                insertInToDb(dbIngredient);
            }
        }
    }

    @Transaction
    @Query("SELECT * FROM card_ingredients where id = :id")
    public abstract DBCardIngredient getIngredientById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertInToDb(DBCardIngredient ingredient);
}
