package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<String> dataList; // Change String to your data model type

    public CustomAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    public void updateData(List<String> newData) {
        this.dataList = newData;
        notifyDataSetChanged(); // Notify the adapter to re-render the list
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String beachName = dataList.get(position); // Assuming dataList contains beach names
        String beachLocation = ""; // You need to retrieve beach location data based on your application
        holder.beachNameTextView.setText(beachName);
        holder.bind(beachName, beachLocation);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView beachNameTextView;
        TextView beachLocationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            beachNameTextView = itemView.findViewById(R.id.textViewBeachName);
            beachLocationTextView = itemView.findViewById(R.id.textViewBeachLocation);
        }

        public void bind(String beachName, String beachLocation) {
            beachNameTextView.setText(beachName);
            beachLocationTextView.setText(beachLocation);
        }
    }
}
