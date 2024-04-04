package algonquin.cst2335.finalproject;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DictionaryActivity extends AppCompatActivity {

    private static final String PREF_SEARCH_TERM = "search_term";
    private EditText etSearch;
    private RecyclerView recyclerView;
    private DictionaryAdapter adapter;
    private List<DictionaryItem> dictionaryItems;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dictionaryItems = new ArrayList<>();
        adapter = new DictionaryAdapter(dictionaryItems);
        recyclerView.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        // Load saved search term from SharedPreferences
        String searchTerm = sharedPreferences.getString(PREF_SEARCH_TERM, "");
        if (!searchTerm.isEmpty()) {
            etSearch.setText(searchTerm);
            performSearch(searchTerm);
        }
    }

    public void search(View view) {
        String searchTerm = etSearch.getText().toString().trim();
        if (!searchTerm.isEmpty()) {
            performSearch(searchTerm);
        } else {
            Toast.makeText(this, "Please enter a word to search", Toast.LENGTH_SHORT).show();
        }
    }

    private void performSearch(final String searchTerm) {
        // Clear previous search results
        dictionaryItems.clear();
        adapter.notifyDataSetChanged();

        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meanings = response.getJSONArray("meanings");
                            for (int i = 0; i < meanings.length(); i++) {
                                JSONObject meaningObj = meanings.getJSONObject(i);
                                String partOfSpeech = meaningObj.getString("partOfSpeech");
                                JSONArray definitions = meaningObj.getJSONArray("definitions");
                                for (int j = 0; j < definitions.length(); j++) {
                                    JSONObject definitionObj = definitions.getJSONObject(j);
                                    String definition = definitionObj.getString("definition");
                                    dictionaryItems.add(new DictionaryItem(partOfSpeech, definition, ""));

                                }
                            }
                            adapter.notifyDataSetChanged();
                            saveSearchTerm(searchTerm);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DictionaryActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(DictionaryActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(request);
    }

    private void saveSearchTerm(String term) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SEARCH_TERM, term);
        editor.apply();
    }

    // Show AlertDialog for notification
    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Positive button click handler
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

