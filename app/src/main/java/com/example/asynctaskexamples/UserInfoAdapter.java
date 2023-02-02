package com.example.asynctaskexamples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserInfoAdapter extends BaseAdapter {
    List<UserInfo> items;

    public UserInfoAdapter(List<UserInfo> items) {
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
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.user_info_layout, viewGroup, false);

        ImageView imageViewAvatar=view.findViewById((R.id.image_view_avatar));
        TextView textName = view.findViewById(R.id.text_name);
        TextView textEmail = view.findViewById(R.id.text_email);
        TextView textPhone = view.findViewById(R.id.text_phone);

        UserInfo item = items.get(i);
        Picasso.get()
                .load("https://lebavui.github.io"+item.getThumbNail())
                .into(imageViewAvatar);
        textName.setText(item.getName());
        textEmail.setText(item.getEmail());
        textPhone.setText(item.getPhone());

        return view;
    }
}
