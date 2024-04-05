package algonquin.cst2335.finalproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private final ArrayList<DictionaryModel> definitions;

    public DictionaryAdapter(ArrayList<DictionaryModel> definitions) {
        this.definitions = definitions;
    }

    public void updateData(ArrayList<DictionaryModel> newData) {
        definitions.clear();
        definitions.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DictionaryModel model = definitions.get(position);
        holder.wordTextView.setText(model.getWord());
        holder.definitionTextView.setText(model.getDefinition());
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView, definitionTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            definitionTextView = itemView.findViewById(R.id.definitionTextView);
        }
    }
}
