package hu.miroszlav.shoppinglistapplication.di;

import javax.inject.Singleton;

import dagger.Component;
import hu.miroszlav.shoppinglistapplication.android.activity.DashboardActivity;
import hu.miroszlav.shoppinglistapplication.android.activity.ItemDetailActivity;
import hu.miroszlav.shoppinglistapplication.android.activity.LoginActivity;
import hu.miroszlav.shoppinglistapplication.android.activity.SplashActivity;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(LoginActivity loginActivity);
    void inject(DashboardActivity dashboardActivity);
    void inject(SplashActivity splashActivity);
    void inject(ItemDetailActivity itemDetailActivity);
}
