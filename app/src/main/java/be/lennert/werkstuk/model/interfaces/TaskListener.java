package be.lennert.werkstuk.model.interfaces;

public interface TaskListener<T> {
    void onTaskCompleted(T t);
}
