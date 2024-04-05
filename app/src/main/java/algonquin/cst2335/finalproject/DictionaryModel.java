package algonquin.cst2335.finalproject;
public class DictionaryModel {
    private final String word;
    private final String definition;

    public DictionaryModel(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
