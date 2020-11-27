package be.lennert.werkstuk.model.interfaces;

import java.util.ArrayList;

import be.lennert.werkstuk.model.apimodels.ComplexSearchModel;
import retrofit2.Response;

public interface APIListener<T> {
     public abstract void call(Response<T> response);
}
