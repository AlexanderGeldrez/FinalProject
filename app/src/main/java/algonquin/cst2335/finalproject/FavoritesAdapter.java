package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.LocationViewHolder> {

    private List<Location> locations; // Data source for the RecyclerView
    private final LayoutInflater inflater;

    // Constructor
    public FavoritesAdapter(Context context, List<Location> locations) {
        this.inflater = LayoutInflater.from(context);
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.tvLatitude.setText(location.getLatitude());
        holder.tvLongitude.setText(location.getLongitude());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void updateLocations(List<Location> newLocations) {
        locations = newLocations;
        notifyDataSetChanged();
    }


    // ViewHolder class
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLatitude, tvLongitude;

        public LocationViewHolder(View itemView) {
            super(itemView);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
        }
    }
}
