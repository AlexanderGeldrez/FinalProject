package algonquin.cst2335.finalproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// Step 2: Create Volley Singleton Class (VolleySingleton.java)
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private final Context context;

    private VolleySingleton(Context context) {
        this.context = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }


    // Step 3: Make Network Requests in Activity or Fragment
    public void fetchSunriseSunsetTimes(String latitude, String longitude, final SunriseSunsetListener listener) {
        // Format the URL with the provided latitude and longitude
        String url = "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&formatted=0";

        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Parse the JSON response to extract sunrise and sunset times
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject results = jsonResponse.getJSONObject("results");
                        String sunrise = results.getString("sunrise");
                        String sunset = results.getString("sunset");

                        // Notify listener with sunrise and sunset times
                        listener.onSuccess(sunrise, sunset);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                        listener.onError("Error parsing JSON response");
                    }
                },
                error -> {
                    // Handle error scenario
                    error.printStackTrace();
                    listener.onError("Error fetching data from server");
                });

        // Add the request to the request queue
        getRequestQueue().add(stringRequest);
    }

    // Define an interface for callback
    public interface SunriseSunsetListener {
        void onSuccess(String sunrise, String sunset);
        void onError(String message);
    }
}
