package com.example.asynctaskexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    List<UserInfo> items;
    UserInfoAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        items = new ArrayList<>();
        adapter = new UserInfoAdapter(items);
        listView = findViewById(R.id.list_view_2);
        listView.setAdapter(adapter);

        new GetData().execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity2.this, UserDetailActivity.class);
                intent.putExtra("userinfo",items.get(position));
                startActivity(intent);
            }
        });
    }




    class GetData extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity2.this);
            progressDialog.setMessage("Loading data");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://lebavui.github.io/jsons/users.json")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String result = response.body().string();
//                Log.v("TAG", "Result: " + result);
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
                        UserInfo item = new UserInfo(
                              jObj.getInt("id"),
                                jObj.getJSONObject("avatar").getString("thumbnail"),
                                jObj.getJSONObject("avatar").getString("photo"),
                                jObj.getString("email"),
                                jObj.getString("phone"),
                                jObj.getString("name"),
                                jObj.getString("username"),
                                jObj.getJSONObject("address").getString("street"),
                                jObj.getJSONObject("address").getString("city"),
                                jObj.getJSONObject("address").getJSONObject("geo").getDouble("lat"),
                                jObj.getJSONObject("address").getJSONObject("geo").getDouble("lng")
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