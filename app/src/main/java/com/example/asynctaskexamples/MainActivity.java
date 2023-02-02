package com.example.asynctaskexamples;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView, textView2;
    SlowTask4 task4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        textView2 = findViewById(R.id.text_view2);

        findViewById(R.id.button_quick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Quick work ... done");
            }
        });
        findViewById(R.id.button_slow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new SlowTask1().execute();
//                new SlowTask2().execute(10);
//                new SlowTask3().execute(10);

//                task4 = new SlowTask4();
//                task4.execute(20);

//                new GetTask().execute();

//                new DownloadTask().execute();

//                new GetTask2().execute();

                ImageView imageView = findViewById(R.id.image_view);
                Picasso.get()
                        .load("https://lebavui.github.io/walls/wall1.jpg")
                        .into(imageView);
            }
        });
        findViewById(R.id.button_cancel_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task4.cancel(true);
            }
        });
        findViewById(R.id.button_task1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlowTask4().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10);
            }
        });

        findViewById(R.id.button_task2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlowTask5().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 20);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected())
            Log.v("TAG", "Connected");
        else
            Log.v("TAG", "Not Connected");

        NetworkInfo info2 = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info2 != null && info2.isConnected())
            Log.v("TAG", "Wifi Connected");
        else
            Log.v("TAG", "Wifi Not Connected");

        NetworkInfo info3 = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info3 != null && info3.isConnected())
            Log.v("TAG", "Mobile Connected");
        else
            Log.v("TAG", "Mobile Not Connected");

        String str = "[\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 2,\n" +
                "    \"title\": \"qui est esse\",\n" +
                "    \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 3,\n" +
                "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\n" +
                "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"\n" +
                "  }]";

        try {
            JSONArray jArr = new JSONArray(str);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                int id = jObj.getInt("id");
                String title = jObj.getString("title");

                Log.v("TAG", id + " - " + title);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int param1 = 1;
        int param2 = 2;
        String param3 = "Value 3";
        boolean param4 = true;
        double param5 = 3.14;

        JSONObject params = new JSONObject();
        try {
            params.put("param1", param1);
            params.put("param2", param2);
            params.put("param3", param3);
            params.put("param4", param4);
            params.put("param5", param5);

            String s = params.toString();
            Log.v("TAG", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class SlowTask1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            textView.setText("Slow work ... started");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            textView.setText("Slow work ... done");
        }
    }

    class SlowTask2 extends AsyncTask<Integer, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            textView.setText("Slow work ... started");
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Processing");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int n = params[0];
            try {
                Thread.sleep(n * 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            textView.setText("Slow work ... done");
            dialog.dismiss();
        }
    }

    class SlowTask3 extends AsyncTask<Integer, Integer, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            textView.setText("Slow work ... started");
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Processing");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int n = params[0];
            for (int i = 0; i < n; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i + 1, n);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            int max = values[1];

            textView.setText(progress + "");
            dialog.setProgress(progress);
            dialog.setMax(max);
        }

        @Override
        protected void onPostExecute(Void unused) {
            textView.setText("Slow work ... done");
            dialog.dismiss();
        }
    }

    class SlowTask4 extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            textView.setText("1: Slow work ... started");
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                int n = params[0];
                for (int i = 0; i < n; i++) {
                    Thread.sleep(1000);
                    publishProgress(i + 1, n);
                }
                return true;
            } catch (InterruptedException iex) {
                Log.v("TAG", "Task cancelled");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            textView.setText("1: " + progress + "");
        }

        @Override
        protected void onCancelled() {
            textView.setText("1: Slow work ... cancelled");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                textView.setText("1: Slow work ... done");
        }
    }

    class SlowTask5 extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            textView2.setText("2: Slow work ... started");
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                int n = params[0];
                for (int i = 0; i < n; i++) {
                    Thread.sleep(800);
                    publishProgress(i + 1, n);
                }
                return true;
            } catch (InterruptedException iex) {
                Log.v("TAG", "Task cancelled");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            textView2.setText("2: " + progress + "");
        }

        @Override
        protected void onCancelled() {
            textView2.setText("2: Slow work ... cancelled");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                textView2.setText("2: Slow work ... done");
        }
    }

    class GetTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://lebavui.github.io/jsons/users.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                Log.v("TAG", "Response Code: " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = reader.readLine()) != null)
                    result += line + "\n";
                reader.close();

                Log.v("TAG", result);

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Downloading");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://lebavui.github.io/videos/ecard.mp4");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                Log.v("TAG", "Response Code: " + responseCode);

                int fileSize = conn.getContentLength();

                InputStream inputStream = conn.getInputStream();
                FileOutputStream outputStream = openFileOutput("test.mp4", MODE_PRIVATE);
                byte[] buffer = new byte[2048];
                int len;
                int downloadedBytes = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                    downloadedBytes += len;
                    publishProgress(downloadedBytes, fileSize);
                }

                inputStream.close();
                outputStream.close();

                Log.v("TAG", "Download completed");

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setMax(values[1]);
            dialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            dialog.dismiss();
        }
    }

    class GetTask2 extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://lebavui.github.io/jsons/users.json")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String result = response.body().string();
                Log.v("TAG", "Result: " + result);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}