package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This class is a custom RecyclerView adapter for displaying word definitions.
 * It binds the data to the RecyclerView and handles updates.
 * @author Tamana Kaushal
 */
public class CustomAdapters extends RecyclerView.Adapter<CustomAdapters.ViewHolder> {
    private List<WordDefinition> dataList; // Now using WordDefinition

    /**
     * Constructs a CustomAdapters object with the provided data list.
     *
     * @param dataList The list of WordDefinition objects to display.
     */
    public CustomAdapters(List<WordDefinition> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Updates the data list and notifies the adapter to re-render the list.
     *
     * @param newData The new list of WordDefinition objects.
     */
    public void updateData(List<WordDefinition> newData) {
        this.dataList = newData;
        notifyDataSetChanged(); // Notify the adapter to re-render the list
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordDefinition wordDefinition = dataList.get(position);
        holder.wordTextView.setText(wordDefinition.getWord());
        holder.definitionTextView.setText(wordDefinition.getDefinition());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * ViewHolder class to hold references to the views for each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView; // To display the word
        TextView definitionTextView; // To display the definition

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.textViewWord);
            definitionTextView = itemView.findViewById(R.id.textViewDefinition);
        }

        /**
         * Binds the word and definition text to the corresponding TextViews.
         *
         * @param wordText       The word text to display.
         * @param definitionText The definition text to display.
         */
        public void bind(String wordText, String definitionText) {
            wordTextView.setText(wordText);
            definitionTextView.setText(definitionText);
        }
    }
}
