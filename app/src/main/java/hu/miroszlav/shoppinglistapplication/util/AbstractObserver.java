package hu.miroszlav.shoppinglistapplication.util;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class AbstractObserver<T> implements Observer<T>{

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("", "", e);
    }

    @Override
    public void onComplete() {
    }
}
