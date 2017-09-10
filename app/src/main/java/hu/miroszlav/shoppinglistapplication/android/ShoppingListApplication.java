package hu.miroszlav.shoppinglistapplication.android;

import android.app.Application;

import hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider;


public class ShoppingListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentInjectorProvider.getInjectorProvider().setup(this);
    }
}
