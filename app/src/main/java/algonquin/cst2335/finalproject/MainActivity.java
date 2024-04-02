package algonquin.cst2335.finalproject;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText searchWord;
    private Button btnSearch;
    private RecyclerView rvDefinitions;
    private DefinitionAdapter adapter; // Define adapter variable
    private RecyclerView rvSavedSearchTerms;
    private SavedSearchAdapter savedSearchAdapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchWord = findViewById(R.id.editTextWord);
        btnSearch = findViewById(R.id.buttonSearch);
        rvDefinitions = findViewById(R.id.recyclerViewDefinitions);
        SharedPreferences sharedPreferences = getSharedPreferences("search_preferences", MODE_PRIVATE);
        String savedSearchTerm = sharedPreferences.getString("searchTerm", "");
        searchWord.setText(savedSearchTerm);
        // Initialize RecyclerView
        rvDefinitions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DefinitionAdapter(); // Initialize adapter
        rvDefinitions.setAdapter(adapter);
        rvSavedSearchTerms = findViewById(R.id.rvSavedSearchTerms);
        savedSearchAdapter = new SavedSearchAdapter();

// Set layout manager and adapter for RecyclerView
        rvSavedSearchTerms.setLayoutManager(new LinearLayoutManager(this));
        rvSavedSearchTerms.setAdapter(savedSearchAdapter);

// Retrieve saved search terms from SharedPreferences and update RecyclerView
        SharedPreferences sharedPreferences = getSharedPreferences("search_preferences", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            savedSearchAdapter.addSearchTerm(entry.getValue().toString());
        }
        if (!allEntries.isEmpty()) {
            findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = searchWord.getText().toString().trim();
                if (!word.isEmpty()) {
                    constructUrl(word);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve saved search term when activity is resumed
        SharedPreferences sharedPreferences = getSharedPreferences("search_preferences", MODE_PRIVATE);
        String savedSearchTerm = sharedPreferences.getString("searchTerm", "");
        searchWord.setText(savedSearchTerm);
    }
    private void constructUrl(String word) {
        // Construct URL for searching definitions
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        makeRequest(apiUrl);
    }

    private void makeRequest(String apiUrl) {
        // Make a request using Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the API
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        queue.add(request);
    }

    private void handleResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            JSONArray meanings = jsonObject.getJSONArray("meanings");
            List<String> definitions = new ArrayList<>();
            for (int i = 0; i < meanings.length(); i++) {
                JSONObject meaning = meanings.getJSONObject(i);
                String definition = meaning.getString("definition");
                definitions.add(definition);
            }
            adapter.setDefinitions(definitions); // Update RecyclerView with definitions
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
