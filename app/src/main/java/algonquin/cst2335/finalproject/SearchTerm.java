package algonquin.cst2335.finalproject;
import java.util.List;

public class SearchTerm {
    private String word;
    private List<String> definitions;

    public SearchTerm(String word, List<String> definitions) {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }
}

