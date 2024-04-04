package algonquin.cst2335.finalproject;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity {

    private static final String TAG = "DictionaryApiClient";
    private RequestQueue queue;
    private Context context;

    public SearchActivity(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void searchWord(String word, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(request);
    }
}