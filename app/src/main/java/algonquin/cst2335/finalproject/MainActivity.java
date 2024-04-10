package algonquin.cst2335.finalproject;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import algonquin.cst2335.finalproject.DictionaryActivity;
import algonquin.cst2335.finalproject.R;




    public class MainActivity extends AppCompatActivity {

        // Initialize variables for each team member's project
        private DictionaryActivity dictionaryActivity;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            dictionaryActivity = new DictionaryActivity();


            // Add graphical icons for each activity to the toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Handle clicks on toolbar icons to launch respective activities
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_result) {
                    startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
                    return true;

                } else {
                    return false;
                }
            });

        }



    }
