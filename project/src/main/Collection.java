package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Collection {
    private final File folder;
    private ArrayList<String> fileNames;
    private ArrayList<Word> allWords;
    private HashMap<String, Dictionary> dictionaries;

    public Collection() {
        folder = new File("dictionaries");
        fileNames = this.listFilesForFolder(folder);
        allWords = new ArrayList<Word>();
        dictionaries = new HashMap<String, Dictionary>();
    }

    public ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> fileNames = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                //System.out.println(fileEntry.getName());
                fileNames.add(fileEntry.getName());
            }
        }
        return fileNames;
    }

    public void addAllWordsAndDictionaries() {
        for (String filename : fileNames) {
            // adding words from a file to the allWords collection
            ArrayList<Word> newWords = Dictionary.fileToWords("dictionaries\\" + filename);
            Dictionary.addWords(allWords, newWords);
            //System.out.println("S-a adaugat: " + filename);

            // creating a Dictionary object and adding it to the hashmap
            int index = filename.indexOf("_");
            String language = "";
            if (index != -1) {
                language = filename.substring(0, index);
            }
            Dictionary dictionary = new Dictionary(language, newWords);
            dictionaries.put(language, dictionary);
        }
    }

    public String translateWord(String word, String fromLanguage, String toLanguage) {
        ArrayList<Word> fromWords = this.dictionaries.get(fromLanguage).getWords();
        ArrayList<Word> toWords = this.dictionaries.get(toLanguage).getWords();
        String word_en = "";
        // finding the word in english from fromWords
        for (int i = 0; i < fromWords.size(); i++) {
            if (word.equals(fromWords.get(i).getWord())) {
                word_en = fromWords.get(i).getWord_en();
            }
        }

        // finding the equivalent in toWords
        for (int i = 0; i < toWords.size(); i++) {
            if (word_en.equals(toWords.get(i).getWord_en())) {
                return toWords.get(i).getWord();
            }
        }
        return "the translation of the word \"" + word + "\" doesn't exist";
    }

    public String translateSentence(String sentence, String fromLanguage, String toLanguage) {
        ArrayList<Word> fromWords = this.dictionaries.get(fromLanguage).getWords();
        ArrayList<Word> toWords = this.dictionaries.get(toLanguage).getWords();
        String[] sentenceWords = sentence.split(" ");
        String[] translatedSentence = new String[sentenceWords.length];
        for (int i = 0; i < sentenceWords.length; i++) {
            translatedSentence[i] = this.translateWord(sentenceWords[i], fromLanguage, toLanguage);
        }
        String translatedString = translatedSentence[0];
        for (int i = 1; i < translatedSentence.length; i++) {
            translatedString = translatedString + " " + translatedSentence[i];
        }
        return translatedString;
    }

    public String[] translateSentences(String sentence, String fromLanguage, String
            toLanguage) {
        // translate every word
        String translatedSentenceString = this.translateSentence(sentence, fromLanguage, toLanguage);
        String[] translatedSentenceArray = translatedSentenceString.split(" ");
        ArrayList<String> translatedSentence = new ArrayList<String>();
        for (String s: translatedSentenceArray) {
            translatedSentence.add(s);
        }

        if (translatedSentence == null) return null;

        // for every translated word, find its synonyms
        //create synonyms matrix for all translated words
        ArrayList<ArrayList<String>> synonyms = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < translatedSentence.size(); i++) {
            synonyms.add(new ArrayList<String>());
        }
        for (int i = 0; i < translatedSentence.size(); ++i) {
            // adding the word itself
            String[] ithWordSynonyms = Word.findSynonyms(translatedSentence.get(i), toLanguage);
            synonyms.get(i).add(translatedSentence.get(i));
            // next, add the synonyms
            if (ithWordSynonyms != null) {
                for (int j = 0; j < ithWordSynonyms.length && ithWordSynonyms.length != 0; j++) {
                    synonyms.get(i).add(ithWordSynonyms[j]);
                }
            }
        }
        return generateTranslationsEasy(synonyms);

    }

    public String[] generateTranslationsEasy(ArrayList<ArrayList<String>> synonyms) {
        // finding the minimum number of synonyms for a given word
        // if it's lower than 3, give fewer translations
        int minimum = 100;
        for (int i = 0; i < synonyms.size(); i++) {
            if (synonyms.get(i).size() < minimum) {
                minimum = synonyms.get(i).size();
            }
        }
        if (minimum >= 3) {
            minimum = 3;
        }

        String[] translationsArray = new String[minimum];
        for (int i = 0; i < minimum; i++) {
            translationsArray[i] = "";
        }
        //generate 3 translations, each with different words
        for (int i = 0; i < minimum; i++) {
            for (int j = 0; j < synonyms.size(); j++) {
//                translations.get(i).add(synonyms.get(j).get(i));
                translationsArray[i] = translationsArray[i] + synonyms.get(j).get(i) + " ";
            }
        }
        return translationsArray;
    }


//    work in progress
//    public ArrayList<ArrayList<String>> generateTranslations(ArrayList<ArrayList<String>> synonyms,
//                                                  ArrayList<ArrayList<String>> partialTranslation,
//                                                  int currentWord,
//                                                  int currentTranslation) throws IndexOutOfBoundsException {
//        // if all translations are done
//        if (currentTranslation == 3) return partialTranslation;
//        // if the translation is finished
//        if (currentWord == synonyms.size()) {
//            return generateTranslations(synonyms, partialTranslation, 0, currentTranslation + 1);
//        }
//
//        // take all synonyms and generate translations
//        // if word was used in the first translation, don't use it in the next translation
//        // that's why currentSynonym starts with currentTranslation
//        for (int currentSynonym = 0; currentSynonym < synonyms.get(currentWord).size(); currentSynonym++) {
//
//            partialTranslation.get(currentTranslation).add(synonyms.get(currentWord).get(currentSynonym));
//            return generateTranslations(synonyms, partialTranslation, currentWord + 1, currentTranslation);
//        }
//
//        return null;
//    }


    public File getFolder() {
        return folder;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    public ArrayList<Word> getAllWords() {
        return allWords;
    }

    public void setAllWords(ArrayList<Word> allWords) {
        this.allWords = allWords;
    }

    public HashMap<String, Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(HashMap<String, Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public String toString() {
        return "Collection\n" +
                "\"folder\": " + "\"" + folder + "\"" + ",\n" +
                "\"fileNames\": " + fileNames + ",\n" +
                "\"allWords\": \n" +
                Word.wordsToString(allWords) +
                "\n" +
                "\"dictionaries\": \n" +
                Dictionary.dictionariesToString(dictionaries) +
                "\n";
    }
}
