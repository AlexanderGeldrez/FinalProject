package algonquin.cst2335.finalproject;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailViewHolder extends RecyclerView.ViewHolder {
    private TextView detailTextView;

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        // Initialize the TextView
        detailTextView = itemView.findViewById(R.id.definition_text_view);
    }

    // Method to bind data to the ViewHolder
    public void bind(String detail) {
        detailTextView.setText(detail);
    }
}

