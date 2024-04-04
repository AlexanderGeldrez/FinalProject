package algonquin.cst2335.finalproject;
public class DictionaryItem {
    private String word;
    private String definition;
    private String partOfSpeech;



    public DictionaryItem(String word, String definition, String partOfSpeech) {
        this.word = word;
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}

