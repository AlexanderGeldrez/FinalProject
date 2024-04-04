package algonquin.cst2335.finalproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SearchActivity apiClient;
    private EditText searchEditText;
    private RecyclerView wordRecyclerView;
    private WordAdapter wordAdapter;
    private List<Word> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = new SearchActivity(this);

        searchEditText = findViewById(R.id.search_edit_text);
        wordRecyclerView = findViewById(R.id.word_recycler_view);
        wordList = new ArrayList<>();

        wordAdapter = new WordAdapter(wordList, this);
        wordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wordRecyclerView.setAdapter(wordAdapter);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String word = searchEditText.getText().toString();
                searchWord(word);
                return true;
            }
            return false;
        });
    }

    public void searchWord(String word) {
        apiClient.searchWord(word, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                wordList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray wordsJsonArray = jsonObject.getJSONArray("words");
                        for (int j = 0; j < wordsJsonArray.length(); j++) {
                            JSONObject wordJsonObject = wordsJsonArray.getJSONObject(j);
                            String wordText = wordJsonObject.getString("text");
                            String phoneticText = wordJsonObject.getString("phonetic");
                            String partOfSpeechText = wordJsonObject.getString("partOfSpeech");
                            String definitionText = wordJsonObject.getString("definition");
                            String exampleText = wordJsonObject.optString("example", "");
                            Word wordObject = new Word(wordText, phoneticText, partOfSpeechText, definitionText, exampleText);
                            wordList.add(wordObject);
                        }
                    } catch (Exception e) {
                        Log.e("SearchActivity", e.getMessage());
                    }
                }
                wordAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });
    }
}