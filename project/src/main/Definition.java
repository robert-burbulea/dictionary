package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Definition {
    private String dict;
    private String dictType;
    private int year;
    private String[] text;

    public Definition(String dict, String dictType, int year, String[] text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public Definition() {
    }

    public static boolean addDefinitionForWord(String word, String language, Definition definition) {
        ArrayList<Word> words = Main.collection.getDictionaries().get(language).getWords();
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getWord())) {
                // search if definition already exists
                for (int j = 0; j < words.get(i).getDefinitions().size(); j++) {
                    if (definition.equals(words.get(i).getDefinitions().get(j))) {
                        return false;
                    }
                }
                // otherwise, add definition
                Main.collection.getDictionaries().get(language).getWords().get(i).getDefinitions().add(definition);
                return true;
            }
        }
        return false;
    }

    public static boolean removeDefinition(String word, String language, String dictionary) {
        ArrayList<Word> words = Main.collection.getDictionaries().get(language).getWords();
        // search word
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getWord())) {
                // search definition
                for (int j = 0; j < words.get(i).getDefinitions().size(); j++) {
                    if(dictionary.equals(words.get(i).getDefinitions().get(j).getDict())) {
                        Main.collection.getDictionaries().get(language).getWords().get(i).getDefinitions().remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "\t\t{" +
                "\t\"dict\": " + "\"" + dict + "\"" + ",\n" +
                "\t\t\t\"dictType\": " + "\"" + dictType + "\"" + ",\n" +
                "\t\t\t\"year\": " +  year  + ",\n" +
                "\t\t\t\"text\": " +  Arrays.toString(text) +
                "\t\t}";
    }

    public static String definitionsToString(ArrayList<Definition> definitions) {
        String s = "\t\"definitions\": [\n";
        for (int i = 0; i < definitions.size(); i++) {
            s = s + definitions.get(i).toString();
        }
        s = s + "\t\t]\n";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        Definition d = (Definition) o;
        if (!this.dict.equals(d.getDict())) return false;
        if (!this.dictType.equals(d.getDictType())) return false;
        if (this.year != d.getYear()) return false;
        if (!Arrays.deepEquals(this.text, d.getText())) return false;
        return true;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
