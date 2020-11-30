
package be.lennert.werkstuk.model.apimodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import be.lennert.werkstuk.model.interfaces.IIngredient;
import be.lennert.werkstuk.utils.StringUtils;

public class ExtendedIngredient implements IIngredient {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("aisle")
    @Expose
    private String aisle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("consistency")
    @Expose
    private String consistency;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("originalString")
    @Expose
    private String originalString;
    @SerializedName("originalName")
    @Expose
    private String originalName;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("meta")
    @Expose
    private List<String> meta = null;
    @SerializedName("metaInformation")
    @Expose
    private List<String> metaInformation = null;
    @SerializedName("measures")
    @Expose
    private Measures measures;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExtendedIngredient() {
    }

    /**
     *
     * @param image
     * @param amount
     * @param original
     * @param aisle
     * @param consistency
     * @param originalName
     * @param unit
     * @param measures
     * @param meta
     * @param name
     * @param originalString
     * @param id
     * @param metaInformation
     */
    public ExtendedIngredient(int id, String aisle, String image, String consistency, String name, String original, String originalString, String originalName, double amount, String unit, List<String> meta, List<String> metaInformation, Measures measures) {
        super();
        this.id = id;
        this.aisle = aisle;
        this.image = image;
        this.consistency = consistency;
        this.name = name;
        this.original = original;
        this.originalString = originalString;
        this.originalName = originalName;
        this.amount = amount;
        this.unit = unit;
        this.meta = meta;
        this.metaInformation = metaInformation;
        this.measures = measures;
        this.amount = measures.getMetric().getAmount();
        this.unit = measures.getMetric().getUnitShort();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getConsistency() {
        return consistency;
    }

    public void setConsistency(String consistency) {
        this.consistency = consistency;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String GetUnit() {
        return this.unit;
    }



    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getMeta() {
        return meta;
    }

    public void setMeta(List<String> meta) {
        this.meta = meta;
    }

    public List<String> getMetaInformation() {
        return metaInformation;
    }

    public void setMetaInformation(List<String> metaInformation) {
        this.metaInformation = metaInformation;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }
    @Override
    public String getMetricAmount(int servings){
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.roundToNearestTen(measures.getMetric().getAmount()*servings));
        sb.append(measures.getMetric().getUnitShort());
        return sb.toString();
    }
}
