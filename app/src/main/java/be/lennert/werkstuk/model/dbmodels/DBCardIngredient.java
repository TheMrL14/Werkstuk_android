package be.lennert.werkstuk.model.dbmodels;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import be.lennert.werkstuk.model.apimodels.ExtendedIngredient;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.utils.StringUtils;


@Entity(tableName = "card_ingredients")
public class DBCardIngredient implements IIngredient {

    @PrimaryKey()
    @NonNull
    private String id;
    private String name;
    private double amount;
    private String unit;
    private String image;


    public DBCardIngredient(){};

    public DBCardIngredient( String name, double amount, String unit, String image) {
        this.id = StringUtils.generateStringId(name);
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.image = image;
    }

    @Ignore
    public DBCardIngredient(ExtendedIngredient i, int portions) {
       this(i.getName(),i.getMeasures().getMetric().getAmount()*portions,i.getMeasures().getMetric().getUnitShort(),i.getImage());
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public String getMetricAmount(int servings) {
        return null;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    public void addAmount(double amount){
        this.amount += (double) amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
