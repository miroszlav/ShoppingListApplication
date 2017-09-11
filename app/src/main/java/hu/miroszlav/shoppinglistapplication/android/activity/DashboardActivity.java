package hu.miroszlav.shoppinglistapplication.android.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider;
import hu.miroszlav.shoppinglistapplication.service.ItemService;
import hu.miroszlav.shoppinglistapplication.service.LoginService;

import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;

public class DashboardActivity extends RxAppCompatActivity {

    @Inject LoginService loginService;
    @Inject ItemService itemService;

    @BindView(R.id.add_item_button) FloatingActionButton addButton;
    @BindView(R.id.dashboard_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        getInjectorProvider().getMainComponent().inject(this);

        setSupportActionBar(toolbar);
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
