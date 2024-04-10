package algonquin.cst2335.finalproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import algonquin.cst2335.finalproject.R;

public class DictionaryActivity extends AppCompatActivity {

    private EditText wordInput;
    private Button lookupButton, viewFavoritesButton, saveToFavoritesButton;
    private TextView definitionTextView;
    private RequestQueue requestQueue; // Volley request queue
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        wordInput = findViewById(R.id.wordInput);
        lookupButton = findViewById(R.id.lookupButton);
        definitionTextView = findViewById(R.id.definitionTextView);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);
        saveToFavoritesButton = findViewById(R.id.saveToFavoritesButton);

        prefs = getSharedPreferences("DictionaryPrefs", MODE_PRIVATE);
        wordInput.setText(prefs.getString("lastWord", ""));

        requestQueue = Volley.newRequestQueue(this);

        lookupButton.setOnClickListener(v -> lookupWord());
        viewFavoritesButton.setOnClickListener(v -> viewFavorites());
        saveToFavoritesButton.setOnClickListener(v -> saveToFavorites());
    }

    private void lookupWord() {
        String word = wordInput.getText().toString().trim();

        if (!word.isEmpty()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastWord", word);
            editor.apply();

            String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            JSONArray meanings = jsonObject.getJSONArray("meanings");
                            JSONObject firstMeaning = meanings.getJSONObject(0);
                            JSONArray definitions = firstMeaning.getJSONArray("definitions");
                            String definition = definitions.getJSONObject(0).getString("definition");

                            definitionTextView.setText(definition);
                        } catch (JSONException e) {
                            definitionTextView.setText("An error occurred while parsing the response.");
                        }
                    }, error -> definitionTextView.setText("An error occurred while fetching data."));

            requestQueue.add(stringRequest);
        } else {
            definitionTextView.setText("Please enter a word.");
        }
    }

    private void viewFavorites() {
        Intent intent = new Intent(DictionaryActivity.this, FavActivity.class);
        startActivity(intent);
    }


    private void saveToFavorites() {
        String word = wordInput.getText().toString().trim();
        String definition = definitionTextView.getText().toString();

        if (!word.isEmpty() && !definition.equals("Please enter a word.") && !definition.startsWith("An error occurred")) {
            WordDefinition wordDefinition = new WordDefinition();
            wordDefinition.setWord(word);
            wordDefinition.setDefinition(definition);

            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            new Thread(() -> {
                db.wordDefinitionDAO().insert(wordDefinition);
                runOnUiThread(() -> Toast.makeText(DictionaryActivity.this, "Saved to favorites!", Toast.LENGTH_LONG).show());
            }).start();
        } else {
            Toast.makeText(this, "No word to save or word not found.", Toast.LENGTH_LONG).show();
        }
    }



}

