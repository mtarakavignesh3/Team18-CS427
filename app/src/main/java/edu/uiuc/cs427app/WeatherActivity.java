package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Volley tutorial here: https://google.github.io/volley/simple.html
        RequestQueue queue = Volley.newRequestQueue(this);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityName = getIntent().getStringExtra("city");
        String welcome = "Welcome to "+cityName+" !";
        String cityWeatherInfo = "Detailed information about the weather of "+cityName;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);
        // Get the weather information from a Service that connects to a weather server and show the results

        Button buttonWeatherCancel = findViewById(R.id.weatherButtonCancel);
        buttonWeatherCancel.setOnClickListener(this);

        if (cityName != null) {
            getWeather(queue, cityName);
        }
    }

    /**
     * Calls the weather API and gets the weather
     * @param queue Request queue for http GET requests.
     * @param cityName name of the city to get weather for
     */
    public void getWeather(RequestQueue queue, String cityName){
        // There is no security requirement for this milestone
        String api_key = "c1c33ddef7624186a5ba5c857da50161";
        String api_endpoint = "https://api.weatherbit.io/v2.0/current";

        // API documentation here: https://www.weatherbit.io/api/weather-current
        api_endpoint += "?city=" + cityName + "&key=" + api_key + "&units=I";
        TextView weatherText = findViewById(R.id.weatherText);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api_endpoint,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject weatherArray = response.getJSONArray("data").getJSONObject(0);

                    Double temp = weatherArray.getDouble("temp");
                    Double humidity = weatherArray.getDouble("rh");
                    String windDirection = weatherArray.getString("wind_cdir");
                    String windSpeed = weatherArray.getString("wind_spd");
                    String weather = weatherArray.getJSONObject("weather").getString("description");
                    // Observed time in UTC
                    String weatherObserveTime = weatherArray.getString("ob_time");

                    // TODO: Format weather API response better
                    weatherText.setText(Double.toString(temp) + "\n"
                            + Double.toString(humidity) + "\n"
                            + windDirection + "\n"
                            + windSpeed + "\n"
                            + weather + "\n"
                            + weatherObserveTime);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.weatherButtonCancel) { // Ends the activity and returns to the calling activity
            finish();
        }
    }
}