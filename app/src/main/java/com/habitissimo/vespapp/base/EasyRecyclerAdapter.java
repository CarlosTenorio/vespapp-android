package com.habitissimo.vespapp.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.habitissimo.vespapp.base.EasyRecyclerAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public abstract class EasyRecyclerAdapter<T, VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> items = new ArrayList<>();

    public EasyRecyclerAdapter() {
    }

    @Override public void onBindViewHolder(VH holder, int position) {
        onBind(holder, items.get(position));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    protected abstract void onBind(VH holder, T item);

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setItems(T... items) {
        setItems(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
