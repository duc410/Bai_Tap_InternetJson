package com.example.asynctaskexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ThirdActivity extends AppCompatActivity {

    Socket client;
    TextView textContent;
    EditText editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editMessage = findViewById(R.id.edit_message);
        textContent = findViewById(R.id.text_content);

        new ConnectAndGet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editMessage.getText().toString();
                new SendTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
            }
        });
    }

    class ConnectAndGet extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Connect to server
                String serverIP = "10.13.85.73";
                int serverPort = 9000;
                int timeOut = 5000;
                InetAddress serverAddress = InetAddress.getByName(serverIP);
                client = new Socket();
                client.connect(new InetSocketAddress(serverAddress, serverPort), timeOut);

                // Get data from server
                String result = "";
                byte[] buffer = new byte[1024];
                while (client.isConnected() && !client.isClosed()) {
                    int res = client.getInputStream().read(buffer);
                    if (res > 0) {
                        result = new String(buffer, 0, res);
                        publishProgress(result);
                        Log.v("TAG", "Received: " + result);
                    } else
                        Log.v("TAG", "Received: ERROR" + result);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textContent.append(values[0]);
        }
    }

    class SendTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String data = params[0];
                OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
                writer.write(data);
                writer.flush();
                // Không đóng output stream để tránh client bị đóng

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}