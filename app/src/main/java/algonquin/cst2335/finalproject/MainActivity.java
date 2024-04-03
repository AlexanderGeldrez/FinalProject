package algonquin.cst2335.finalproject;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import algonquin.cst2335.finalproject.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DefinitionAdapter adapter;
    private EditText editText;
    private Button addButton;
    private SharedPreferences sharedPreferences;
private DictionaryDatabaseHelper dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize RecyclerView adapter
        adapter = new DefinitionAdapter(getItemsFromSharedPreferences());
        recyclerView.setAdapter(adapter);

        // Setup EditText and Button
        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
           case R.id.action_help:
                showHelpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addItem() {
        String newItem = editText.getText().toString().trim();
        if (!newItem.isEmpty()) {
            // Add item to SharedPreferences
            addNewItemToSharedPreferences(newItem);

            // Add item to RecyclerView
            adapter.addItem(newItem);

            // Clear EditText
            editText.getText().clear();
        } else {
            Toast.makeText(this, "Enter item first", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getItemsFromSharedPreferences() {
        List<String> items = new ArrayList<>();
        int itemCount = sharedPreferences.getInt("itemCount", 0);
        for (int i = 0; i < itemCount; i++) {
            String item = sharedPreferences.getString("item" + i, "");
            if (!item.isEmpty()) {
                items.add(item);
            }
        }
        return items;
    }

    private void addNewItemToSharedPreferences(String newItem) {
        int itemCount = sharedPreferences.getInt("itemCount", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("item" + itemCount, newItem);
        editor.putInt("itemCount", itemCount + 1);
        editor.apply();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("This is a help dialog.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
