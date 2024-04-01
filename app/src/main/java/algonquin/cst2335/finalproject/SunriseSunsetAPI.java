package algonquin.cst2335.finalproject;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SunriseSunsetAPI {

    // URL for Sunrise & Sunset API
    private static final String API_URL = "https://api.sunrise-sunset.org/json?";

    // Callback interface for handling API responses
    public interface SunriseSunsetListener {
        void onSuccess(String sunrise, String sunset);
        void onError(String message);
    }

    // Method to fetch sunrise and sunset times from the API
    public static void fetchSunriseSunset(Context context, double latitude, double longitude, SunriseSunsetListener listener) {
        // Initialize the request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Format the URL with the provided latitude and longitude
        String url = API_URL + "lat=" + latitude + "&lng=" + longitude + "&formatted=0";

        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response to extract sunrise and sunset times
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject results = jsonResponse.getJSONObject("results");
                            String sunrise = results.getString("sunrise");
                            String sunset = results.getString("sunset");

                            // Callback with sunrise and sunset times
                            listener.onSuccess(sunrise, sunset);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            listener.onError("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error scenario
                        listener.onError("Error fetching data: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        queue.add(stringRequest);
    }
}
