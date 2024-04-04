package algonquin.cst2335.finalproject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_SEARCH_TERM = "search_term";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SearchTermAdapter searchTermAdapter;
    private List<String> savedSearchTerms = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedSearchTerms = new ArrayList<>();
        searchTermAdapter = new SearchTermAdapter(savedSearchTerms);
        recyclerView.setAdapter(searchTermAdapter);
        searchTermAdapter.notifyDataSetChanged();
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Load saved search terms from SharedPreferences
        loadSearchTerms();
    }

    // Load saved search terms from SharedPreferences
    private void loadSearchTerms() {
        String searchTerm = sharedPreferences.getString(PREF_SEARCH_TERM, "");
        if (!searchTerm.isEmpty()) {
            savedSearchTerms.add(searchTerm);
            searchTermAdapter.notifyDataSetChanged();
        }
    }

    // Save search term to SharedPreferences
    private void saveSearchTerm(String term) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SEARCH_TERM, term);
        editor.apply();
    }

    // Handle toolbar menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle toolbar menu item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_help:
                showHelpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Show help dialog
    private void showHelpDialog() {
        // Implement your help dialog logic here
    }

    // Handle click on a saved search term
    private void onSearchTermClicked(String term) {
        // Start new activity with the selected search term
        Intent intent = new Intent(this, DictionaryActivity.class);
        intent.putExtra("search_term", term);
        startActivity(intent);
    }
}
