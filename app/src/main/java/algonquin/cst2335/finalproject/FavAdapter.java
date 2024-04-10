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

/**
 * This adapter is responsible for populating a RecyclerView with favorite word definitions.
 */
public class FavAdapter extends RecyclerView.Adapter<FavAdapter.WordDefinitionViewHolder> {

    private List<WordDefinition> wordDefinitions; // Use WordDefinition objects
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;

    /**
     * Constructs a new FavAdapter.
     *
     * @param context          The context.
     * @param wordDefinitions  The list of favorite word definitions.
     * @param listener         The listener for item click events.
     */
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

    /**
     * Get the WordDefinition object at the specified position.
     *
     * @param position The position of the WordDefinition in the list.
     * @return The WordDefinition object at the specified position.
     */
    public WordDefinition getWordDefinitionAt(int position) {
        return wordDefinitions.get(position);
    }

    /**
     * Updates the list of favorite word definitions.
     *
     * @param newWordDefinitions The new list of favorite word definitions.
     */
    public void updateWordDefinitions(List<WordDefinition> newWordDefinitions) {
        this.wordDefinitions = newWordDefinitions;
        notifyDataSetChanged();
    }

    /**
     * Interface definition for a callback to be invoked when an item in the RecyclerView is clicked.
     */
    public interface OnItemClickListener {
        /**
         * Called when the delete button of an item is clicked.
         *
         * @param position The position of the item in the list.
         */
        void onDeleteClick(int position);

        /**
         * Called when the reinsert action is performed on an item.
         *
         * @param wordDefinition The WordDefinition object to be reinserted.
         */
        void onReinsert(WordDefinition wordDefinition); // Adjust for WordDefinition
    }

    /**
     * ViewHolder class for holding views of each item in the RecyclerView.
     */
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
