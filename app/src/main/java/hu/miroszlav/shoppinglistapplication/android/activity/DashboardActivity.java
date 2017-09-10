package hu.miroszlav.shoppinglistapplication.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider;
import hu.miroszlav.shoppinglistapplication.service.LoginService;

import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;

public class DashboardActivity extends AppCompatActivity {

    @Inject LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getInjectorProvider().getMainComponent().inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                loginService.logout();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
