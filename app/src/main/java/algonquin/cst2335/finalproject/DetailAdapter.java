package algonquin.cst2335.finalproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private List<String> details;

    public DetailAdapter(List<String> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.bind(details.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView detailTextView;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTextView = itemView.findViewById(R.id.definition_text_view);
        }

        public void bind(String detail) {
            detailTextView.setText(detail);
        }
    }
}

