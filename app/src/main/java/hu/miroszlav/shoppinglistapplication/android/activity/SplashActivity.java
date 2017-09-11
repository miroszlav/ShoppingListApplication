package hu.miroszlav.shoppinglistapplication.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.service.LoginService;

import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;

public class SplashActivity extends Activity {

    @Inject LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getInjectorProvider().getMainComponent().inject(this);

        if (loginService.isLoggedIn()) {
            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
