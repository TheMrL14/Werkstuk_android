package be.lennert.werkstuk.model.interfaces;

import retrofit2.Response;

public interface GetListener<T> {
    public void call(T response);
}
