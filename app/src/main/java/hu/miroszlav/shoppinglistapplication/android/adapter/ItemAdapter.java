package hu.miroszlav.shoppinglistapplication.android.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import hu.miroszlav.shoppinglistapplication.android.widget.ItemView;
import hu.miroszlav.shoppinglistapplication.model.Item;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {


    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemView itemView = new ItemView(parent.getContext());
        itemView.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.getItemView().setup(currentItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemView itemView;

        ItemViewHolder(ItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        ItemView getItemView() {
            return itemView;
        }
    }
}
