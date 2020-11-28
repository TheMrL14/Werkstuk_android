package be.lennert.werkstuk.model.dbmodels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import be.lennert.werkstuk.model.apimodels.ExtendedIngredient;
import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.utils.StringUtils;

@Entity(tableName = "ingredients")
public class DBIngredient implements IIngredient {
    @PrimaryKey(autoGenerate = true)
    private int ingredientId;
    private String name;
    private double quantity;
    private String unit;
    private String image;
    private byte[] imageByte;
    private int relationId;

    public DBIngredient() {
    }

    public DBIngredient( String name, double quantity, String unit, String image, int relationId) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.image = image;
        this.relationId = relationId;
    }

    public DBIngredient(IIngredient e, int relationId) {
        this(e.getName(),e.getAmount(),e.GetUnit(),e.getImage(), relationId);
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getAmount() {
        return this.quantity;
    }

    @Override
    public String GetUnit() {
        return this.unit;
    }

    @Override
    public String getMetricAmount(int servings) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.roundToNearestTen(quantity*servings));
        sb.append(unit);
        return sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRecipeId() {
        return relationId;
    }

    public void setRecipeId(int recipeId) {
        this.relationId = recipeId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
}
