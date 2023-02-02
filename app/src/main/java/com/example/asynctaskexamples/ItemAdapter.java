package com.example.asynctaskexamples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    List<ItemModel> items;

    public ItemAdapter(List<ItemModel> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_item, viewGroup, false);

        TextView textId = view.findViewById(R.id.text_id);
        TextView textUserId = view.findViewById(R.id.text_user_id);
        TextView textTitle = view.findViewById(R.id.text_title);

        ItemModel item = items.get(i);
        textId.setText(item.getId() + "");
        textUserId.setText(item.getUserId() + "");
        textTitle.setText(item.getTitle());

        return view;
    }
}
