package algonquin.cst2335.finalproject;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DictionaryViewHolder extends RecyclerView.ViewHolder {

    private TextView partOfSpeechTextView;
    private TextView definitionTextView;

    public DictionaryViewHolder(@NonNull View itemView) {
        super(itemView);

        partOfSpeechTextView = itemView.findViewById(R.id.part_of_speech_text_view);
        definitionTextView = itemView.findViewById(R.id.definition_text_view);
    }

    public void bind(String partOfSpeech, String definition) {
        partOfSpeechTextView.setText(partOfSpeech);
        definitionTextView.setText(definition);
    }
}
