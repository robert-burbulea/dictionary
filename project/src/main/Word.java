package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Word {
    private String word;
    private String word_en;
    private String type;
    private String[] singular;
    private String[] plural;
    private ArrayList<Definition> definitions;

    public Word(String word, String word_en, String type, String[] singular, String[] plural, ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = singular;
        this.plural = plural;
        this.definitions = definitions;
    }

    public static boolean addWord(Word word, String language) {
        for (Word w : Main.collection.getDictionaries().get(language).getWords()) {
            if (word.equals(w)) {
                return false;
            }
        }
        Main.collection.getDictionaries().get(language).getWords().add(word);
        return true;
    }

    public static boolean removeWord(String word, String language) {
        ArrayList<Word> words = Main.collection.getDictionaries().get(language).getWords();
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getWord())) {
                Main.collection.getDictionaries().get(language).getWords().remove(i);
                return true;
            }
        }
        return false;
    }

    public static String[] findSynonyms(String word, String language) {
        ArrayList<Word> words = Main.collection.getDictionaries().get(language).getWords();
        String[] synonyms = null;
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getWord())) {
                //search synonyms
                for (int j = 0; j < words.get(i).getDefinitions().size(); j++) {
                    if (words.get(i).getDefinitions().get(j).getDictType().equals("synonyms")) {
                        // creating an array of synonyms
                        synonyms = words.get(i).getDefinitions().get(j).getText();
                    }
                }
                break;
            }
        }
        return synonyms;
    }

    public static Word findWord(String word, String language) {
        ArrayList<Word> words = Main.collection.getDictionaries().get(language).getWords();
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getWord())) {
                return words.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Definition> getDefinitionsForWord(String word, String language) {
        Word wordObject = findWord(word, language);
        ArrayList<Definition> definitions = new ArrayList<Definition>();
        for (Definition definition : wordObject.getDefinitions()) {
            definitions.add(definition);
        }
        definitions.sort(new YearComparator());
        return definitions;
    }

    @Override
    public String toString() {
        return "\t{\n" +
                "\t\"word\": " + "\"" + word + "\"" + ",\n" +
                "\t\"word_en\": " + "\"" + word_en + "\"" + ",\n" +
                "\t\"type\": " + "\"" + type + "\"" + ",\n" +
                "\t\"singular\": " + Arrays.toString(singular) + ",\n" +
                "\t\"plural\": " + Arrays.toString(plural) + ",\n" +
                "\t\"definitions\": \n" +
                Definition.definitionsToString(definitions) +
                "\t\t" + "\n" +
                "\t}\n";
    }

    public static String wordsToString(ArrayList<Word> words) {
        String s = "\t[\n";
        for (int i = 0; i < words.size(); i++) {
            s = s + words.get(i).toString();
        }
        s = s + "\t]\n";
        return s;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getSingular() {
        return singular;
    }

    public void setSingular(String[] singular) {
        this.singular = singular;
    }

    public String[] getPlural() {
        return plural;
    }

    public void setPlural(String[] plural) {
        this.plural = plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    public static void printAll(ArrayList<Word> words) {
        try {
            for (int i = 0; i < words.size(); i++) {
                System.out.println(words.get(i));
            }
            System.out.println("\n\n");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
