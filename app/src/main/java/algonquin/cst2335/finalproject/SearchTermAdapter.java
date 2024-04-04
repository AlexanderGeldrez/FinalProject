package algonquin.cst2335.finalproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchTermAdapter extends RecyclerView.Adapter<SearchTermAdapter.ViewHolder> {

    private List<String> searchTerms;

    public SearchTermAdapter(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_term, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String term = searchTerms.get(position);
        holder.termTextView.setText(term);
    }

    @Override
    public int getItemCount() {
        return searchTerms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView termTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            termTextView = itemView.findViewById(R.id.term_text_view);
        }
    }
}
