package be.lennert.werkstuk.model.apimodels;

import com.google.gson.annotations.SerializedName;

public class Joke {
    @SerializedName("text")
    private String text;

    public Joke(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
