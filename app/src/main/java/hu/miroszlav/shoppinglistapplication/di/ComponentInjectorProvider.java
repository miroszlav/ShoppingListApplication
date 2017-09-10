package hu.miroszlav.shoppinglistapplication.di;


import android.app.Application;

public final class ComponentInjectorProvider {

    private static final ComponentInjectorProvider instance = new ComponentInjectorProvider();
    private MainComponent mainComponent;

    private ComponentInjectorProvider() {
    }

    public static ComponentInjectorProvider getInjectorProvider() {
        return instance;
    }

    public void setup(Application application) {
        mainComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(application))
                .build();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
