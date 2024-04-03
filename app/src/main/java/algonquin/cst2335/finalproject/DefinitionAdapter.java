package algonquin.cst2335.finalproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.ViewHolder> {

    private List<String> definitions;

    // Constructor
    public DefinitionAdapter() {
        this.definitions = definitions;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDefinition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvDefinition);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_definition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String definition = definitions.get(position);
        holder.tvDefinition.setText(definition);
    }

    @Override
    public int getItemCount() {
        return definitions != null ? definitions.size() : 0;
    }

    // Setter method for updating definitions
    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }
}
