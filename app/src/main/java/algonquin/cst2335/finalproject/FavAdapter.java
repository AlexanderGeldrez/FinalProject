package algonquin.cst2335.finalproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.finalproject.R;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.WordDefinitionViewHolder> {

    private List<WordDefinition> wordDefinitions; // Use WordDefinition objects
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    public FavAdapter(Context context, List<WordDefinition> wordDefinitions, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.wordDefinitions = wordDefinitions;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public WordDefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_word_defintion, parent, false); // Ensure you have this layout
        return new WordDefinitionViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordDefinitionViewHolder holder, int position) {
        WordDefinition wordDefinition = wordDefinitions.get(position);
        holder.wordTextView.setText(wordDefinition.getWord());
        holder.definitionTextView.setText(wordDefinition.getDefinition());

        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(view -> {
            clickListener.onDeleteClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return wordDefinitions.size();
    }

    public WordDefinition getWordDefinitionAt(int position) {
        return wordDefinitions.get(position);
    }

    public void updateWordDefinitions(List<WordDefinition> newWordDefinitions) {
        this.wordDefinitions = newWordDefinitions;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onReinsert(WordDefinition wordDefinition); // Adjust for WordDefinition
    }

    static class WordDefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView, definitionTextView;

        WordDefinitionViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.tvWord); // Ensure these IDs match your layout
            definitionTextView = itemView.findViewById(R.id.tvDefinition);
            itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> {
                if (listener != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(getBindingAdapterPosition());
                }
            });
        }
    }
}

