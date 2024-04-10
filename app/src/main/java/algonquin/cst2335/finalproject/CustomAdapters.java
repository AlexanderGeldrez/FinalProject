package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapters extends RecyclerView.Adapter<CustomAdapters.ViewHolder> {
    private List<WordDefinition> dataList; // Now using WordDefinition

    public CustomAdapters(List<WordDefinition> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView; // To display the word
        TextView definitionTextView; // To display the definition

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.textViewWord);
            definitionTextView = itemView.findViewById(R.id.textViewDefinition);
        }
        public void bind(String wordText, String definitionText) {
            wordTextView.setText(wordText);
            definitionTextView.setText(definitionText);
        }
    }
}
