package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class SunriseSunsetActivity extends AppCompatActivity {

    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button lookupButton;
    private TextView sunriseTextView;
    private TextView sunsetTextView;
    private RequestQueue requestQueue; // Volley request queue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset); // Replace with your layout file

        latitudeEditText = findViewById(R.id.latitudeInput);
        longitudeEditText = findViewById(R.id.longitudeInput);
        lookupButton = findViewById(R.id.lookupButton);
        sunriseTextView = findViewById(R.id.textViewSunrise);
        sunsetTextView = findViewById(R.id.textViewSunset);

        requestQueue = Volley.newRequestQueue(this); // Instantiate the RequestQueue

        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookupSunriseSunset();
            }
        });
    }

    private void lookupSunriseSunset() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        if (!isValidCoordinate(latitude) || !isValidCoordinate(longitude)) {
            sunriseTextView.setText("Invalid latitude or longitude.");
            return;
        }

        String url = "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&formatted=0";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject results = jsonResponse.getJSONObject("results");
                            String sunrise = results.getString("sunrise");
                            String sunset = results.getString("sunset");

                            sunriseTextView.setText("Sunrise: " + sunrise);
                            sunsetTextView.setText("Sunset: " + sunset);
                        } catch (JSONException e) {
                            sunriseTextView.setText("An error occurred while parsing the response.");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sunriseTextView.setText("An error occurred while fetching data.");
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    private boolean isValidCoordinate(String coordinate) {
        // Add your logic here to validate the coordinates
        // For now, let's just check if it's not empty
        return !coordinate.isEmpty();
    }

    // Additional methods and logic as needed
}
