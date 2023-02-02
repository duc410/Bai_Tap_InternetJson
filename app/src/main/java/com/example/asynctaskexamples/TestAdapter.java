package com.example.asynctaskexamples;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestAdapter extends BaseAdapter {

    JSONArray jsonArray;

    public TestAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return jsonArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        try {
            return jsonArray.getJSONObject(i).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(i);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
