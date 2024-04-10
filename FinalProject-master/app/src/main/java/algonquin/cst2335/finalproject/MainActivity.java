package algonquin.cst2335.finalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import algonquin.cst2335.finalproject.Mandeep.SunriseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    // Correctly moved outside of the onCreate method
    public void startSunriseActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SunriseActivity.class);
        startActivity(intent);
    }

    // Other methods for handling RecyclerViews, database operations, etc.
}
