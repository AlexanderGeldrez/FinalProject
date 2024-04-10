package algonquin.cst2335.finalproject;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
public class FavActivity  extends AppCompatActivity {

        private RecyclerView favoritesRecyclerView;
        private FavAdapter favoritesAdapter; // Ensure this adapter works with WordDefinition objects
        private AppDatabase db;
        private View snackbarContainer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_favorites_dictionary); // Use the correct layout
            snackbarContainer = findViewById(R.id.snackbar_container);

            db = AppDatabase.getDatabase(getApplicationContext());
            favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
            favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            favoritesAdapter = new FavAdapter(this, new ArrayList<>(), new FavAdapter.OnItemClickListener() {
                @Override
                public void onDeleteClick(int position) {
                    WordDefinition wordDefinitionToDelete = favoritesAdapter.getWordDefinitionAt(position);
                    deleteWordDefinition(wordDefinitionToDelete, position);
                }

                @Override
                public void onReinsert(WordDefinition wordDefinition) {
                    reinsertWordDefinition(wordDefinition);
                }
            });

            favoritesRecyclerView.setAdapter(favoritesAdapter);

            db.wordDefinitionDAO().getAllWordDefinition().observe(this, wordDefinitions -> {
                Log.d("FavoritesActivity", "Observed changes in the database");
                favoritesAdapter.updateWordDefinitions(wordDefinitions);
            });
        }

        public void deleteAllFavorites(View view) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All Favorites")
                    .setMessage("Are you sure you want to delete all favorite definitions?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteAllWordDefinitions())
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        private void deleteWordDefinition(WordDefinition wordDefinition, int position) {
            new Thread(() -> {
                db.wordDefinitionDAO().delete(wordDefinition.getId()); // Assuming your DAO has a delete method that accepts an ID
                runOnUiThread(() -> {
                    Snackbar.make(snackbarContainer, "Definition deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", v -> reinsertWordDefinition(wordDefinition))
                            .show();
                });
            }).start();
        }

        private void reinsertWordDefinition(WordDefinition wordDefinition) {
            new Thread(() -> {
                db.wordDefinitionDAO().insert(wordDefinition);
                runOnUiThread(() -> Log.d("FavoritesActivity", "Word definition reinserted"));
            }).start();
        }

        private void deleteAllWordDefinitions() {
            new Thread(() -> {
                db.wordDefinitionDAO().deleteAll();
                runOnUiThread(() -> Log.d("FavoritesActivity", "All word definitions deleted"));
            }).start();
        }
    }


