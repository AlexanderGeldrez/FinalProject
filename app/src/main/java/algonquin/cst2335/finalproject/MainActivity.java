package algonquin.cst2335.finalproject;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private DictionaryAdapter adapter;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DictionaryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("DictionaryPrefs", MODE_PRIVATE);

        String lastSearchTerm = sharedPreferences.getString("lastSearchTerm", "");
        searchEditText.setText(lastSearchTerm);
    }

    public void searchDefinition(View view) {
        String searchTerm = searchEditText.getText().toString().trim();
        if (!searchTerm.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lastSearchTerm", searchTerm);
            editor.apply();

            ArrayList<DictionaryModel> definitions = dbHelper.getDefinitions(searchTerm);
            if (definitions.isEmpty()) {
                Toast.makeText(this, "No definitions found for " + searchTerm, Toast.LENGTH_SHORT).show();
            } else {
                // Display definitions using RecyclerView
                adapter.updateData(definitions);
            }
        } else {
            Toast.makeText(this, "Please enter a word to search", Toast.LENGTH_SHORT).show();
        }
    }


    public void viewSavedTerms(View view) {
        // Retrieve saved terms from SharedPreferences
        Set<String> savedTermsSet = sharedPreferences.getStringSet("savedTerms", new HashSet<>());
        ArrayList<String> savedTerms = new ArrayList<>(savedTermsSet);

        if (savedTerms.isEmpty()) {
            Toast.makeText(this, "No saved terms found", Toast.LENGTH_SHORT).show();
        } else {
            // Show saved terms to user
            for (String term : savedTerms) {
                System.out.println("Saved Term: " + term);
            }
        }
    }


    public void deleteDefinition(View view, String term) {
        // Create AlertDialog for confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the definition for " + term + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Delete the definition
                dbHelper.deleteDefinition(term);
                Toast.makeText(MainActivity.this, "Definition for " + term + " deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}

