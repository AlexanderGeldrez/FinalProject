package algonquin.cst2335.finalproject;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryAPI {

    // URL for the Dictionary API
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    // Callback interface for handling API responses
    public interface DictionaryListener {
        void onSuccess(String definition);
        void onError(String message);
    }

    // Method to fetch word definitions from the API
    public static void fetchWordDefinition(Context context, String word, DictionaryListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = API_URL + word; // Constructs the full API URL for the requested word

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject firstResult = jsonArray.getJSONObject(0);
                                JSONArray meanings = firstResult.getJSONArray("meanings");
                                if (meanings.length() > 0) {
                                    JSONObject firstMeaning = meanings.getJSONObject(0);
                                    JSONArray definitions = firstMeaning.getJSONArray("definitions");
                                    if (definitions.length() > 0) {
                                        JSONObject firstDefinition = definitions.getJSONObject(0);
                                        String definition = firstDefinition.getString("definition");
                                        listener.onSuccess(definition); // Pass the first definition found
                                    } else {
                                        listener.onError("No definitions found.");
                                    }
                                } else {
                                    listener.onError("No meanings found.");
                                }
                            } else {
                                listener.onError("Word not found.");
                            }
                        } catch (JSONException e) {
                            listener.onError("Error parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error fetching data: " + error.getMessage());
                    }
                });

        queue.add(stringRequest);
    }
}
