package algonquin.cst2335.finalproject;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.ViewHolder> {

    private List<String> searchTerms;
    private SharedPreferences sharedPreferences;

    public SavedSearchAdapter() {
        this.sharedPreferences = sharedPreferences;
        searchTerms = new ArrayList<>();
    }

    public void addSearchTerm(String searchTerm) {
        searchTerms.add(searchTerm);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String searchTerm = searchTerms.get(position);
        holder.bind(searchTerm);
    }

    @Override
    public int getItemCount() {
        return searchTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSearchTerm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchTerm = itemView.findViewById(R.id.tvSearchTerm);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    deleteSearchTerm(position);
                }
            });
        }

        public void bind(String searchTerm) {
            tvSearchTerm.setText(searchTerm);
        }

        private void deleteSearchTerm(int position) {
            String searchTerm = searchTerms.get(position);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(searchTerm);
            editor.apply();
            searchTerms.remove(position);
            notifyItemRemoved(position);
        }
    }
}
