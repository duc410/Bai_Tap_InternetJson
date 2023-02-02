package com.example.asynctaskexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExchangeRateActivity extends AppCompatActivity {

    EditText editFrom, editTo;
    Spinner spinnerFrom, spinnerTo;

    List<String> keys;
    List<String> names;
    JSONObject jRates;

    ArrayAdapter<String> adapterFrom, adapterTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        keys = new ArrayList<>();
        names = new ArrayList<>();

        adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        adapterTo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);

        spinnerFrom.setAdapter(adapterFrom);
        spinnerTo.setAdapter(adapterTo);

        editFrom = findViewById(R.id.edit_from);
        editTo = findViewById(R.id.edit_to);

        editFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String fromString = editable.toString();
                if (fromString.isEmpty())
                    return;

                double from = Double.parseDouble(fromString);
                String keyFrom = keys.get(spinnerFrom.getSelectedItemPosition());
                String keyTo = keys.get(spinnerTo.getSelectedItemPosition());

                try {
                    double rate1 = jRates.getDouble(keyFrom);
                    double rate2 = jRates.getDouble(keyTo);

                    double to = from / rate1 * rate2;
                    editTo.setText(to + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        new GetNames().execute();
        new GetRates().execute();
    }

    class GetNames extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.apilayer.com/fixer/symbols?apikey=n6UDHXoNTnQx3MV5mrqPR7irjlQlNmpy");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = reader.readLine()) != null)
                    result += line + "\n";
                reader.close();
                Log.v("TAG", result);

                JSONObject jObj = new JSONObject(result);
                if (jObj.getBoolean("success")) {
                    JSONObject jSymbols = jObj.getJSONObject("symbols");
                    JSONArray jArr = jSymbols.names();

                    for (int i = 0; i < jArr.length(); i++) {
                        String key = jArr.getString(i);
                        keys.add(key);
                        names.add(jSymbols.getString(key));
                    }
                } else
                    return false;

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.v("TAG", "Result: " + result);
            if (result) {
                adapterFrom.notifyDataSetChanged();
                adapterTo.notifyDataSetChanged();
            }
        }
    }

    class GetRates extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.apilayer.com/fixer/latest?apikey=n6UDHXoNTnQx3MV5mrqPR7irjlQlNmpy");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String result = "";
                while ((line = reader.readLine()) != null)
                    result += line + "\n";
                reader.close();
                Log.v("TAG", result);

                JSONObject jObj = new JSONObject(result);
                if (jObj.getBoolean("success")) {
                    jRates = jObj.getJSONObject("rates");
                    return true;
                } else
                    return false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.v("TAG", "Result: " + result);
            if (result) {
                adapterFrom.notifyDataSetChanged();
                adapterTo.notifyDataSetChanged();
            }
        }
    }
}