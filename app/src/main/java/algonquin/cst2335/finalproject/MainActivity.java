
package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import algonquin.cst2335.finalproject.DetailsFragment;
import algonquin.cst2335.finalproject.ViewsModel;
import ui.recipeSearchActivity;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.finalproject.databinding.DeezerSongBinding;
import ui.recipeSearchActivity;


/**
 * The main activity class for the DeezerSong app.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * List of songs retrieved from the Deezer API.
     */
    ArrayList<String> songs;
    /**
     * RecyclerView adapter for displaying the list of songs.
     */
    private RecyclerView.Adapter myAdapter;
    /**
     * Data binding for the main activity.
     */
    ActivityMainBinding binding;

    /**
     * Creates the options menu in the activity.
     *
     * @param menu The options menu in which you place your items.
     * @return Returns true for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    /**
     * Handles item selections in the options menu.
     *
     * @param item The selected menu item.
     * @return Returns true to indicate that the event has been consumed.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.recipe:
                startActivity(new Intent(this, recipeSearchActivity.class));

                break;
            case R.id.sunrise:
                //startActivity(new Intent(this, recipeSearchActivity.class));

                break;
            case R.id.Help:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Info of this page")
                        .setMessage("This shows the song artist name and the song from that artist." +
                                "Put a url of the artist you want in the searchbar and click on the button.")
                        .setPositiveButton("Close", (dialog, which) -> {
                        })
                        .create()
                        .show();

                break;
        }

        return true;
    }
    /**
     * Volley RequestQueue for handling API requests.
     */
    RequestQueue queue = null;
    /**
     * ViewModel for managing the data related to songs.
     */
    ViewsModel SongsModel = null;
    /**
     * Initializes the activity, including setting up the UI and handling saved preferences.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        setSupportActionBar(binding.toolbar);
        SongsModel = new ViewModelProvider(this).get(ViewsModel.class);
        songs = new ArrayList<>();
        if (SongsModel.songs.getValue() != null){
            songs.addAll(SongsModel.songs.getValue());
        }

        SharedPreferences prefs = getSharedPreferences("SongData", Context.MODE_PRIVATE);
        String search = prefs.getString("SongSearch", "");
        binding.editTextText.setText(search);
        SharedPreferences.Editor editor = prefs.edit();
        SongsModel.selectedSong.observe(this, (song) -> {
            DetailsFragment songFrag = new DetailsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, songFrag).commit();
        });

        binding.button.setOnClickListener(click -> {
            String text = binding.editTextText.getText().toString();

            Toast.makeText(this,"Toast: "+ text,Toast.LENGTH_SHORT).show();
            editor.putString("SongSearch",text);
            editor.apply();

            try {
                String stringURL = "https://api.deezer.com/search/artist/?q="+ URLEncoder.encode(text,"UTF-8");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {
                            try {
                                songs.clear();
                                JSONArray songArray=response.getJSONArray("data");
                                //start
                                for (int i = 0; i < songArray.length(); i++){
                                    JSONObject song_obj = songArray.getJSONObject(i);
                                    String artist_name = song_obj.getString("name");
                                    String tracklistURL = song_obj.getString("tracklist");

                                    songs.add(artist_name);
                                }
                                SongsModel.songs.postValue(songs);

                                //end
                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        (error) -> {
                            Toast.makeText(this, "FAILED", Toast.LENGTH_LONG).show();
                        });
                queue.add(request);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        });

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                DeezerSongBinding binding = DeezerSongBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.songname.setText("");
                String obj = songs.get(position);
                holder.songname.setText(obj);
            }

            @Override
            public int getItemCount() {
                return songs.size();
            }
        });

    }
    /**
     * Custom ViewHolder for the RecyclerView items.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView songname;
        //        RecyclerView recyclerView;
        int position;
        /**
         * Constructs a new instance of MyRowHolder.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(clk -> {
                position = getAdapterPosition();
                String selected = songs.get(position);
                SongsModel.selectedSong.postValue(selected);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete the message " + songname.getText())
                        .setTitle("Question:")
                        .setPositiveButton("No", (dialog, cl) -> {
                        })
                        .setNegativeButton("Yes", ((dialog, cl) -> {
                            String m= songs.get(position);
                            songs.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(songname,"You deleted message #"+ position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",click ->{
                                        songs.add(position,m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        }))
                        .create().show();


            });
            songname = itemView.findViewById(R.id.songname);

        }
    }



}
