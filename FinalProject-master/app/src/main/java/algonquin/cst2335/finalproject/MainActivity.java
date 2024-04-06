package algonquin.cst2335.finalproject;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import algonquin.cst2335.finalproject.Mandeep.SunriseActivity;
import algonquin.cst2335.finalproject.R;

public class MainActivity extends AppCompatActivity {

    // Initialize variables for each team member's project
    private SunriseActivity sunriseActivity;
//    private RecipeSearchActivity recipeSearchActivity;
//    private DictionaryActivity dictionaryActivity;
//    private DeezerSongSearchActivity deezerSongSearchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sunrise);

        // Initialize each team member's activity
        sunriseActivity = new SunriseActivity();
//        recipeSearchActivity = new RecipeSearchActivity();
//        dictionaryActivity = new DictionaryActivity();
//        deezerSongSearchActivity = new DeezerSongSearchActivity();

        // Add graphical icons for each activity to the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle clicks on toolbar icons to launch respective activities
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_sunrise_sunset) {
                startActivity(new Intent(MainActivity.this, SunriseActivity.class));
                return true;
//            } else if (item.getItemId() == R.id.recipe_search_icon) {
//                startActivity(new Intent(MainActivity.this, RecipeSearchActivity.class));
//                return true;
//            } else if (item.getItemId() == R.id.dictionary_icon) {
//                startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
//                return true;
//            } else if (item.getItemId() == R.id.deezer_song_search_icon) {
//                startActivity(new Intent(MainActivity.this, DeezerSongSearchActivity.class));
//                return true;
            } else {
                return false;
            }
        });

    }

    // Other methods for handling RecyclerViews, database operations, etc.

}
