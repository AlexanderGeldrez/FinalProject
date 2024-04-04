package algonquin.cst2335.finalproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private List<DictionaryItem> dictionaryItems;

    public DictionaryAdapter(List<DictionaryItem> dictionaryItems) {
        this.dictionaryItems = dictionaryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dictionary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DictionaryItem dictionaryItem = dictionaryItems.get(position);
        holder.tvPartOfSpeech.setText(dictionaryItem.getPartOfSpeech());
        holder.tvDefinition.setText(dictionaryItem.getDefinition());
    }

    @Override
    public int getItemCount() {
        return dictionaryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPartOfSpeech;
        TextView tvDefinition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPartOfSpeech = itemView.findViewById(R.id.text_part_of_speech);
            tvDefinition = itemView.findViewById(R.id.text_definition);
        }
    }
}

