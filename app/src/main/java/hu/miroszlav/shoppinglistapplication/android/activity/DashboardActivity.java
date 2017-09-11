package hu.miroszlav.shoppinglistapplication.android.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.android.adapter.ItemAdapter;
import hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider;
import hu.miroszlav.shoppinglistapplication.model.Item;
import hu.miroszlav.shoppinglistapplication.service.ItemService;
import hu.miroszlav.shoppinglistapplication.service.LoginService;
import hu.miroszlav.shoppinglistapplication.util.AbstractObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static hu.miroszlav.shoppinglistapplication.android.util.ViewUtils.getItemDecorator;
import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class DashboardActivity extends RxAppCompatActivity {

    @Inject LoginService loginService;
    @Inject ItemService itemService;

    @BindView(R.id.add_item_button) FloatingActionButton addButton;
    @BindView(R.id.dashboard_toolbar) Toolbar toolbar;
    @BindView(R.id.item_list_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.items_recycler_view) RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        getInjectorProvider().getMainComponent().inject(this);

        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(new ArrayList<Item>());
        recyclerView.setAdapter(itemAdapter);
        recyclerView.addItemDecoration(getItemDecorator(4));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillInRecyclerView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillInRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void fillInRecyclerView() {
        itemService.getItems()
                .subscribeOn(io())
                .observeOn(mainThread())
                .compose(this.<List<Item>>bindToLifecycle())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
                .subscribe(new AbstractObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        itemAdapter.setItems(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        makeText(DashboardActivity.this, R.string.item_fetch_error, LENGTH_SHORT).show();
                    }
                });
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
