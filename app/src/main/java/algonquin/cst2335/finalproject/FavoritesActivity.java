package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter favoritesAdapter;
    private AppDatabase db;
    private View snackbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        snackbarContainer = findViewById(R.id.snackbar_container);

        db = AppDatabase.getDatabase(getApplicationContext());
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesAdapter = new FavoritesAdapter(this, new ArrayList<>(), new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Location locationToDelete = favoritesAdapter.getLocationAt(position);
                deleteLocation(locationToDelete, position);
            }

            @Override
            public void onReinsert(Location location) {
                reinsertLocation(location);
            }
        });

        favoritesRecyclerView.setAdapter(favoritesAdapter);

        db.locationDao().getAllLocations().observe(this, locations -> {
            Log.d("FavoritesActivity", "Observed changes in the database");
            favoritesAdapter.updateLocations(locations);
        });
    }

    public void deleteAllFavorites(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete All Favorites")
                .setMessage("Are you sure you want to delete all favorites?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteAllLocations())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteLocation(Location location, int position) {
        new Thread(() -> {
            db.locationDao().delete(location.getId());
            runOnUiThread(() -> {
                Snackbar.make(snackbarContainer, "Location deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> reinsertLocation(location))
                        .show();
            });
        }).start();
    }

    private void reinsertLocation(Location location) {
        new Thread(() -> {
            db.locationDao().insert(location);
            runOnUiThread(() -> Log.d("FavoritesActivity", "Location reinserted"));
        }).start();
    }

    private void deleteAllLocations() {
        new Thread(() -> {
            db.locationDao().deleteAll();
            runOnUiThread(() -> Log.d("FavoritesActivity", "All locations deleted"));
        }).start();
    }
}
