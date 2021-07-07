package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText city;
    Button display;
    TextView temp, humid, pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (EditText) findViewById(R.id.cityname);
        display = (Button) findViewById(R.id.displayButton);
        temp = (TextView) findViewById(R.id.temperature);
        humid = (TextView) findViewById(R.id.humidity);
        pres = (TextView) findViewById(R.id.pressure);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWeather();
            }

            private void displayWeather() {

                String ct = city.getText().toString();
                String URL = "https://api.openweathermap.org/data/2.5/weather?q=" + ct + "&appid=678e6af1379fe58c2e5745396d5ba404";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject ob = response.getJSONObject("main");
                            String tempvalue = ob.getString("temp");
                            double tempvaluedouble = Double.parseDouble(tempvalue);
                            tempvaluedouble = tempvaluedouble - 273.15;
                            tempvalue = Double.toString(tempvaluedouble).substring(0,5);
                            String humidvalue = ob.getString("humidity");
                            String presvalue = ob.getString("pressure");
                            temp.setText("Temperature : " + tempvalue + " C");
                            humid.setText("Humidity : " + humidvalue + "%");
                            pres.setText("Pressure : " + presvalue + " Pa");

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "An error occured! Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Invalid city name!", Toast.LENGTH_SHORT).show();
                        temp.setText("");
                        humid.setText("");
                        pres.setText("");

                    }
                });
                queue.add(request);

            }
        });

    }

}