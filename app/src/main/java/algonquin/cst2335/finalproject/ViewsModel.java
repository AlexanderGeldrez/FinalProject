
package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 * A ViewModel class for managing data related to songs in the DeezerSong app.
 */
public class ViewsModel extends ViewModel {
    /**
     * MutableLiveData for storing the list of songs retrieved from the Deezer API.
     */
    public MutableLiveData<ArrayList<String>> songs = new MutableLiveData< >();
    /**
     * MutableLiveData for storing the selected song.
     */
    public MutableLiveData<String> selectedSong = new MutableLiveData<>();
}
