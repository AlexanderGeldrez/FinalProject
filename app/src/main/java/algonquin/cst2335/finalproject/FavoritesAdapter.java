package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.LocationViewHolder> {

    private List<Location> locations;
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    public FavoritesAdapter(Context context, List<Location> locations, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.locations = locations;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.tvLatitude.setText(location.getLatitude());
        holder.tvLongitude.setText(location.getLongitude());

        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(view -> {
            clickListener.onDeleteClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public Location getLocationAt(int position) {
        return locations.get(position);
    }

    public void updateLocations(List<Location> newLocations) {
        locations = newLocations;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onReinsert(Location location);
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLatitude, tvLongitude;

        LocationViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
            itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> {
                if (listener != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(getBindingAdapterPosition());
                }
            });
        }
    }
}
