package hu.miroszlav.shoppinglistapplication.di;

import javax.inject.Singleton;

import dagger.Component;
import hu.miroszlav.shoppinglistapplication.android.activity.DashboardActivity;
import hu.miroszlav.shoppinglistapplication.android.activity.LoginActivity;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(LoginActivity loginActivity);
    void inject(DashboardActivity dashboardActivity);
}
