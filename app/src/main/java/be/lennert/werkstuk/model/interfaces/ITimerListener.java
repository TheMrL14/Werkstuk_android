package be.lennert.werkstuk.model.interfaces;

public interface ITimerListener<T> {
    void onTick(T millisUntilFinished);
    void onFinish();
}


