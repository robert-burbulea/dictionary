package main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private String language;
    private ArrayList<Word> words;

    public Dictionary(String language, ArrayList<Word> words) {
        this.language = language;
        this.words = words;
    }

    public static ArrayList<Word> fileToWords(String filename) {
        ArrayList<Word> newWords = null;
        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Word>>() {
            }.getType();
            newWords = gson.fromJson(new FileReader(filename), collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newWords;
    }

    public static void exportDictionary(String language) throws IOException {
        ArrayList<Word> wordsToExport = Main.collection.getDictionaries().get(language).getWords();
        wordsToExport.sort(new LexicographicComparator());
        for (Word word : wordsToExport) {
            word.getDefinitions().sort(new YearComparator());
        }

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new Gson().toJsonTree(wordsToExport).getAsJsonArray();
        jsonObject.add("jsonArray", jsonArray);
        System.out.println(jsonObject.toString());

        File file = new File("exports\\" + language + "_dict.json");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(jsonObject.toString());
        fileWriter.flush();
        fileWriter.close();


    }

    public static void addWords(ArrayList<Word> allWords, ArrayList<Word> wordsToAdd) {
        if (allWords == null) {
            allWords = wordsToAdd;
        } else {
            allWords.addAll(wordsToAdd);
        }
    }

    public static void printAll(HashMap<String, Dictionary> dictionaries) {
        try {
            for (Map.Entry<String, Dictionary> entry : dictionaries.entrySet()) {
                System.out.println(entry.getValue());
            }
            System.out.println("\n\n");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "[\n" +
                "\"language\": " + "\"" + language + "\"" + ",\n" +
                "\"words\":\n" +
                Word.wordsToString(words) +
                "\n";
    }

    public static String dictionariesToString(HashMap<String, Dictionary> dictionaries) {
        String s = "\"dictionaries\": [\n";
        for (Map.Entry<String, Dictionary> entry : dictionaries.entrySet()) {
            s = s + entry.getValue().toString();
        }
        s = s + "]\n";
        return s;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }


}
