package algonquin.cst2335.finalproject;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DictionaryDetailActivity extends AppCompatActivity {

    private TextView wordTextView;
    private TextView definitionTextView;
    private Button deleteButton;

    private String word;
    private String definition;

    private DictionaryDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_detail);

        // Initialize database helper
        databaseHelper = new DictionaryDatabaseHelper(this);

        // Initialize views
        wordTextView = findViewById(R.id.word_text_view);
        definitionTextView = findViewById(R.id.definition_text_view);
        deleteButton = findViewById(R.id.delete_button);

        // Get data from intent
        word = getIntent().getStringExtra("word");
        definition = getIntent().getStringExtra("definition");

        // Display word and definition
        wordTextView.setText(word);
        definitionTextView.setText(definition);

        // Set click listener for delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to delete item from database
                deleteItemFromDatabase();
            }
        });
    }

    private void deleteItemFromDatabase() {
        // Delete item from database
        databaseHelper.deleteSearchTerm(word);
        // Show toast message indicating item deletion
        Toast.makeText(this, "Item deleted from database", Toast.LENGTH_SHORT).show();
        // Finish the activity
        finish();
    }

    @Override
    protected void onDestroy() {
        // Close the database helper to prevent memory leaks
        databaseHelper.close();
        super.onDestroy();
    }
}


