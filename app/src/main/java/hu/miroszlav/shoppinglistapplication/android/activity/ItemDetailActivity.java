package hu.miroszlav.shoppinglistapplication.android.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.model.Item;
import hu.miroszlav.shoppinglistapplication.service.ItemService;
import hu.miroszlav.shoppinglistapplication.util.AbstractObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;

import static android.widget.Toast.*;
import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;
import static hu.miroszlav.shoppinglistapplication.validator.ItemValidator.validateName;
import static hu.miroszlav.shoppinglistapplication.validator.ItemValidator.validateQuantity;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class ItemDetailActivity extends RxAppCompatActivity {


    @Inject ItemService itemService;

    @BindView(R.id.item_detail_toolbar) Toolbar toolbar;
    @BindView(R.id.name) TextInputEditText itemNameView;
    @BindView(R.id.quantity) TextInputEditText quantityView;

    private Item item;

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

        item = new Item();
    }

    @OnClick(R.id.save_item_button)
    void onSaveItemClicked() {
        if (validateName(itemNameView.getText().toString()) && validateQuantity(quantityView.getText().toString())) {
            item.setName(itemNameView.getText().toString());
            item.setQuantity(Integer.valueOf(quantityView.getText().toString()));
            itemService.saveItem(item)
                    .subscribeOn(io())
                    .observeOn(mainThread())
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new AbstractObserver<Void>() {
                        @Override
                        public void onNext(@NonNull Void item) {
                            finish();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            makeText(ItemDetailActivity.this, R.string.item_save_error, LENGTH_SHORT).show();
                        }
                    });
        } else {
            makeText(this, R.string.required_field_error, LENGTH_SHORT).show();
        }
    }


}
