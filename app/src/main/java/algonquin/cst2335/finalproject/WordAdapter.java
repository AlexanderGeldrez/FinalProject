package algonquin.cst2335.finalproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> words;
    private Context context;

    public WordAdapter(List<Word> words, Context context) {
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.wordTextView.setText(word.getWord());
        holder.phoneticTextView.setText(word.getPhonetic());
        holder.partOfSpeechTextView.setText(word.getPartOfSpeech());
        holder.definitionTextView.setText(word.getDefinition());
        holder.exampleTextView.setText(word.getExample());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView wordTextView;
        private TextView phoneticTextView;
        private TextView partOfSpeechTextView;
        private TextView definitionTextView;
        private TextView exampleTextView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.word_text_view);
            phoneticTextView = itemView.findViewById(R.id.phonetic_text_view);
            partOfSpeechTextView = itemView.findViewById(R.id.part_of_speech_text_view);
            definitionTextView = itemView.findViewById(R.id.definition_text_view);
            exampleTextView = itemView.findViewById(R.id.example_text_view);
        }
    }
}