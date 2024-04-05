package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private CustomAdapter customAdapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SunriseSunsetProject");

        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);
        latitudeInput.setText(prefs.getString("lastLatitude", ""));
        longitudeInput.setText(prefs.getString("lastLongitude", ""));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(getData());
        recyclerView.setAdapter(customAdapter);

        setupButtons();
    }

    private void setupButtons() {
        Button showToastButton = findViewById(R.id.showToastButton);
        showToastButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "This is a Toast message", Toast.LENGTH_SHORT).show());

        Button showSnackbarButton = findViewById(R.id.showSnackbarButton);
        showSnackbarButton.setOnClickListener(v -> Snackbar.make(findViewById(android.R.id.content), "This is a Snackbar message", Snackbar.LENGTH_SHORT).show());

        Button showAlertDialogButton = findViewById(R.id.showAlertDialogButton);
        showAlertDialogButton.setOnClickListener(v -> showAlertDialog());

        Button lookupButton = findViewById(R.id.lookupButton);
        lookupButton.setOnClickListener(this::onLookupButtonClick);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert Dialog")
                .setMessage("This is an AlertDialog message")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    private List<String> getData() {
        return Arrays.asList("Beach 1", "Beach 2", "Beach 3");
    }

    public void onLookupButtonClick(View view) {
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);
        String latitudeStr = latitudeInput.getText().toString();
        String longitudeStr = longitudeInput.getText().toString();

        try {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastLatitude", latitudeStr);
            editor.putString("lastLongitude", longitudeStr);
            editor.apply();

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
    public void showFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }


    public void onSaveToFavoritesClicked(View view) {
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);
        String latitudeStr = latitudeInput.getText().toString();
        String longitudeStr = longitudeInput.getText().toString();

        if (!latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
            Location location = new Location(latitudeStr, longitudeStr); // Ensure Location constructor matches this usage
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            new Thread(() -> {
                db.locationDao().insert(location);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Saved to favorites!", Toast.LENGTH_LONG).show());
            }).start();
        } else {
            Toast.makeText(this, "Please enter valid latitude and longitude values", Toast.LENGTH_LONG).show();
        }
    }


    private String formatTime(String isoTime) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = isoFormat.parse(isoTime);

            SimpleDateFormat localFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            localFormat.setTimeZone(TimeZone.getDefault());
            return localFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Time Error";
        }
    }
}
