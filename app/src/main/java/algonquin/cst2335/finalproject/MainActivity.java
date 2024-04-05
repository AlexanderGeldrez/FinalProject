package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private RecyclerView favoritesRecyclerView;
    private RecyclerView recyclerView; // Class-level declaration to be initialized in onCreate
    private FavoritesAdapter favoritesAdapter;
    private List<Location> locations = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        latitudeInput.setText(prefs.getString("lastLatitude", ""));
        longitudeInput.setText(prefs.getString("lastLongitude", ""));

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesAdapter = new FavoritesAdapter(this, locations);
        favoritesRecyclerView.setAdapter(favoritesAdapter);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = findViewById(R.id.recyclerView); // Correctly initialize the class-level recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter customAdapter = new CustomAdapter(getData());
        recyclerView.setAdapter(customAdapter);

        Button showToastButton = findViewById(R.id.showToastButton);
        showToastButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "This is a Toast message", Toast.LENGTH_SHORT).show());

        Button showSnackbarButton = findViewById(R.id.showSnackbarButton);
        showSnackbarButton.setOnClickListener(v -> Snackbar.make(findViewById(android.R.id.content), "This is a Snackbar message", Snackbar.LENGTH_SHORT).show());

        Button showAlertDialogButton = findViewById(R.id.showAlertDialogButton);
        showAlertDialogButton.setOnClickListener(v -> showAlertDialog());

        Button lookupButton = findViewById(R.id.lookupButton);
        lookupButton.setOnClickListener(this::onLookupButtonClick);

        fetchDataAndUpdateUI(); // Fetch data asynchronously and update UI
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert Dialog")
                .setMessage("This is an AlertDialog message")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                })
                .show();
    }


    private List<String> getData() {
        // Implement actual data fetching logic here
        return Arrays.asList("Beach 1", "Beach 2", "Beach 3"); // Example data
    }

    private void fetchDataAndUpdateUI() {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate delay
                // Simulated data fetched
                List<String> fetchedData = Arrays.asList("Beach A", "Beach B", "Beach C");

                // Run on UI thread to update UI
                handler.post(() -> updateRecyclerView(fetchedData));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateRecyclerView(List<String> data) {
        CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateData(data); // Ensure this method is properly implemented in your CustomAdapter class
        }
    }


    public void onLookupButtonClick(View view) {
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);
        String latitudeStr = latitudeInput.getText().toString();
        String longitudeStr = longitudeInput.getText().toString();

        try {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);

            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastLatitude", latitudeStr);
            editor.putString("lastLongitude", longitudeStr);
            editor.apply();

            // Display loading or disable button if needed here
            // ...

            SunriseSunsetAPI.fetchSunriseSunset(this, latitude, longitude, new SunriseSunsetAPI.SunriseSunsetListener() {
                @Override
                public void onSuccess(String sunrise, String sunset) {
                    runOnUiThread(() -> {
                        TextView textViewSunrise = findViewById(R.id.textViewSunrise);
                        TextView textViewSunset = findViewById(R.id.textViewSunset);
                        textViewSunrise.setText(getString(R.string.sunrise) + " " + formatTime(sunrise));
                        textViewSunset.setText(getString(R.string.sunset) + " " + formatTime(sunset));
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show());
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid latitude and longitude values", Toast.LENGTH_LONG).show();
        }
    }

    private String formatTime(String isoTime) {
        try {
            // ISO 8601 format
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // The API response is in UTC
            Date date = isoFormat.parse(isoTime);

            // Convert to the local time zone and desired format
            SimpleDateFormat localFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            localFormat.setTimeZone(TimeZone.getDefault()); // Set to local time zone

            return localFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Time Error";
        }
    }

}