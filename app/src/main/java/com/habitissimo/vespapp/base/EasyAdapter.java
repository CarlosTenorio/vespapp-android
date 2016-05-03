package com.habitissimo.vespapp.base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class EasyAdapter<T> extends BaseAdapter {
    private List<T> items = new ArrayList<>();

    public EasyAdapter() {
    }

    public abstract @LayoutRes int getItemLayout();

    public @LayoutRes int getDropDownItemLayout() {
        return View.NO_ID;
    }

    public abstract void bind(T item, ViewHolder holder);

    public abstract ViewHolder createViewHolder(View view);

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override public final int getCount() {
        return items.size();
    }

    @Override public final T getItem(int position) {
        return items.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public final View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = inflate(convertView, parent, getItemLayout());
        bind(position, holder);

        return holder.itemView;
    }

    @Override public final View getDropDownView(int position, View convertView, ViewGroup parent) {
        int itemLayout = getDropDownItemLayout() == View.NO_ID ? getItemLayout() : getDropDownItemLayout();
        ViewHolder holder = inflate(convertView, parent, itemLayout);
        bind(position, holder);
        return holder.itemView;
    }

    private ViewHolder inflate(View convertView, ViewGroup parent, int itemLayout) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return holder;
    }

    private void bind(int position, ViewHolder holder) {
        T item = items.get(position);
        bind(item, holder);
    }

    public static class ViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
