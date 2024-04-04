package algonquin.cst2335.finalproject;
public class Word {

    private String word;
    private String phonetic;
    private String partOfSpeech;
    private String definition;
    private String example;

    public Word(String word, String phonetic, String partOfSpeech, String definition, String example) {
        this.word = word;
        this.phonetic = phonetic;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }
        public String getDefinition () {
            return definition;
        }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setDefinition (String definition){
            this.definition = definition;
        }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}