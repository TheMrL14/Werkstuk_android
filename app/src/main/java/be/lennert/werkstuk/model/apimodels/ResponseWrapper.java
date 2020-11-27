package be.lennert.werkstuk.model.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWrapper {
    @SerializedName("results")
    private List<ComplexSearchModel> response = null;

    public List<ComplexSearchModel> getResponse() {
        return response;
    }

    public void setResponse(List<ComplexSearchModel> response) {
        this.response = response;
    }
}
