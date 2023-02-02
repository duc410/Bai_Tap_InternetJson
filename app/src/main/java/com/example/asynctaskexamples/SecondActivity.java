package com.example.asynctaskexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    List<ItemModel> items;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        items = new ArrayList<>();
        adapter = new ItemAdapter(items);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        new GetDataTask().execute();
    }

    class GetDataTask extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SecondActivity.this);
            progressDialog.setMessage("Loading data");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = reader.readLine()) != null)
                    result += line + "\n";
                reader.close();
                Log.v("TAG", result);
                return result;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            try {
                if (s != null) {
                    JSONArray jArr = new JSONArray(s);
                    for (int i = 0 ; i < jArr.length(); i++) {
                        JSONObject jObj = jArr.getJSONObject(i);
                        ItemModel item = new ItemModel(
                                jObj.getInt("userId"),
                                jObj.getInt("id"),
                                jObj.getString("title"),
                                jObj.getString("body")
                        );
                        items.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}