package algonquin.cst2335.finalproject;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    public DefinitionAdapter adapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_saved_search);

        // Initialize Views
        searchEditText = findViewById(R.id.SearchTerm);
        recyclerView = findViewById(R.id.recyclerViewSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter
        adapter = new DefinitionAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(this);
    }

    private void searchDefinition(String query) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + query;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<String> definitions = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONArray meanings = jsonObject.getJSONArray("meanings");
                                for (int j = 0; j < meanings.length(); j++) {
                                    JSONObject meaning = meanings.getJSONObject(j);
                                    String definition = meaning.getString("definition");
                                    definitions.add(definition);
                                }
                            }
                            adapter.setDefinitions(definitions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SearchActivity", "Error: " + error.getMessage());
                        Toast.makeText(SearchActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}

