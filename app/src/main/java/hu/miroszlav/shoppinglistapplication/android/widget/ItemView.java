package hu.miroszlav.shoppinglistapplication.android.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.model.Item;

public class ItemView extends CardView {

    @BindView(R.id.item_name) TextView itemNameView;
    @BindView(R.id.quantity_value) TextView quantityValueView;

    public ItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.view_item, this);
        ButterKnife.bind(this);
    }

    public void setup(Item item) {
        itemNameView.setText(item.getName());
        quantityValueView.setText(String.valueOf(item.getQuantity()));
    }
}
