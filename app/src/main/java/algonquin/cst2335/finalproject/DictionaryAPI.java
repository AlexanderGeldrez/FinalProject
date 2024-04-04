package algonquin.cst2335.finalproject;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAPI {
    private static final String TAG = DictionaryAPI.class.getSimpleName();
    private static final String BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    private Context context;
    private RequestQueue requestQueue;

    public DictionaryAPI(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getDefinitions(String word, final DictionaryAPIListener listener) {
        String url = BASE_URL + word;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<String> definitions = new ArrayList<>();
                            JSONArray meanings = response.getJSONArray("meanings");
                            for (int i = 0; i < meanings.length(); i++) {
                                JSONObject meaningObj = meanings.getJSONObject(i);
                                JSONArray definitionsArray = meaningObj.getJSONArray("definitions");
                                for (int j = 0; j < definitionsArray.length(); j++) {
                                    JSONObject definitionObj = definitionsArray.getJSONObject(j);
                                    String definition = definitionObj.getString("definition");
                                    definitions.add(definition);
                                }
                            }
                            listener.onDefinitionsReceived(definitions);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                            listener.onError("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error making API request: " + error.getMessage());
                        listener.onError("Error making API request");
                    }
                });
        requestQueue.add(request);
    }

    public interface DictionaryAPIListener {
        void onDefinitionsReceived(List<String> definitions);
        void onError(String errorMessage);
    }
}
