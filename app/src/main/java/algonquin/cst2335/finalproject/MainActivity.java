package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_ITEM_MEMBER1 = 1;
    private static final int MENU_ITEM_MEMBER2 = 2;
// Add more constants for other menu items if needed
    private AppDatabase db;
    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter adapter;
    private List<Location> locations;
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
        adapter = new FavoritesAdapter(this, locations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        favoritesRecyclerView.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Fetch data for RecyclerView
        List<String> dataList = getData();

        // Create and set adapter
        CustomAdapter adapter = new CustomAdapter(dataList);
        recyclerView.setAdapter(adapter);

        SunriseSunsetAPI.fetchSunriseSunset(getApplicationContext(), 43.7, -79.42, new SunriseSunsetAPI.SunriseSunsetListener() {
            @Override
            public void onSuccess(String sunrise, String sunset) {
                // Update UI with sunrise and sunset times
                TextView textViewSunrise = findViewById(R.id.textViewSunrise);
                TextView textViewSunset = findViewById(R.id.textViewSunset);
                textViewSunrise.setText("Sunrise: " + sunrise);
                textViewSunset.setText("Sunset: " + sunset);
            }

            @Override
            public void onError(String message) {
                // Display error message
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        Button showToastButton = findViewById(R.id.showToastButton);
        Button showSnackbarButton = findViewById(R.id.showSnackbarButton);
        Button showAlertDialogButton = findViewById(R.id.showAlertDialogButton);

        showToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });

        showSnackbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar();
            }
        });

        showAlertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showToast() {
        Toast.makeText(MainActivity.this, "This is a Toast message", Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar() {
        Snackbar.make(findViewById(android.R.id.content), "This is a Snackbar message", Snackbar.LENGTH_SHORT).show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert Dialog")
                .setMessage("This is an AlertDialog message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Positive button clicked
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Negative button clicked
                    }
                })
                .show();
    }





    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        // Add your logic here to fetch data
        // For example, fetching data from a database or an API
        // Replace this example with your actual data-fetching logic

        // Sample data for demonstration purposes
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");

        return dataList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case MENU_ITEM_MEMBER1:
                startActivity(new Intent(this, SunriseSunsetActivity.class));
                return true;
            case MENU_ITEM_MEMBER2:
                startActivity(new Intent(this, RecipeSearchActivity.class));
                return true;
            // Add more cases for other menu items...
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public void onLookupButtonClick(View view) {
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);

        String latitude = latitudeInput.getText().toString();
        String longitude = longitudeInput.getText().toString();

        // Save the last search terms
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastLatitude", latitude);
        editor.putString("lastLongitude", longitude);
        editor.apply();

        // Proceed to fetch sunrise and sunset times
        fetchSunriseSunsetTimes(latitude, longitude);
    }



    public void fetchSunriseSunsetTimes(String latitude, String longitude) {
        // Initialize the request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Format the URL with the provided latitude and longitude
        String url = "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&formatted=0";

        // Create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Here, parse the JSON response to extract sunrise and sunset times
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject results = jsonResponse.getJSONObject("results");
                        String sunrise = results.getString("sunrise");
                        String sunset = results.getString("sunset");

                        // Use this information as needed in your app
                        // For example, update the UI with these times
                        updateUIWithSunTimes(sunrise, sunset);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                    }
                },
                error -> {
                    // Handle error scenario
                    error.printStackTrace();
                });

        // Add the request to the request queue
        queue.add(stringRequest);
    }

    private void updateUIWithSunTimes(String sunrise, String sunset) {
        // Update your UI elements with the sunrise and sunset times
        // This might involve setting the text of TextViews as an example
    }

    // Add methods for inserting, deleting locations, making network requests, etc.

}
