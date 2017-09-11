package hu.miroszlav.shoppinglistapplication.android.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.miroszlav.shoppinglistapplication.R;

import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;

public class ItemDetailActivity extends RxAppCompatActivity {


    @BindView(R.id.item_detail_toolbar) Toolbar toolbar;
    @BindView(R.id.name) TextInputEditText itemNameView;
    @BindView(R.id.quantity) TextInputEditText quantityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        getInjectorProvider().getMainComponent().inject(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ItemDetailActivity.this);
            }
        });
    }


}
