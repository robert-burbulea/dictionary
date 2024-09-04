package main;

import java.io.IOException;
import java.util.ArrayList;

public class Checker {
    public void checkTask1() {
        Main.collection = new Collection();
        Main.collection.addAllWordsAndDictionaries();
        System.out.println(Main.collection);
    }
    public void checkTask2() {
        ArrayList<Definition> d1 = new ArrayList<Definition>();
        d1.add(new Definition("dexonline", "definitions", 2009, new String[]  {"A deveni senin", "A deveni sau a face să devină senin, liniștit, vesel"} ) );
        d1.add(new Definition("dexonline", "synonyms", 2009, new String[]  {"A inveseli", "a lumina"} ) );

        Word w1 = new Word("insenina", "lighten", "verb",
                new String[] {"inseninez", "inseninezi", "insenineaza"},
                new String[] {"inseninam", "inseninati", "insenineaza"},
                d1);
        // add word
        boolean ok = Word.addWord(w1, "ro");
        System.out.println(ok);
        // now it already exists
        ok = Word.addWord(w1, "ro");
        System.out.println(ok);

    }

    public void checkTask3_1() {
        // word can be removed
        boolean ok = Word.removeWord("pisică", "ro");
        System.out.println(ok);
        //System.out.println(Main.collection);
    }

    public void checkTask3_2() {
        // word doesn't exist
        boolean ok = Word.removeWord("camion", "ro");
        System.out.println(ok);
        //System.out.println(Main.collection);
    }

    public void checkTask4_1() {
        // definition doesn't exist
        Definition d1 = new Definition("Dicționar de sinonime", "synonyms", 1998, new String[]{"a păși", "a înainta"});
        boolean ok = Definition.addDefinitionForWord("merge", "ro", d1);
        System.out.println(ok);
    }

    public void checkTask4_2() {
        // definition already exists
        Definition d1 = new Definition("Dicționarul explicativ al limbii române (ediția a II-a revăzută și adăugită)",
                "definitions", 2009,
                new String[]{"A se mișca deplasându-se dintr-un loc în altul; a se deplasa, a umbla",
                "A putea fi înghițit ușor; a aluneca pe gât"});
        boolean ok = Definition.addDefinitionForWord("merge", "ro", d1);
        System.out.println(ok);
    }

    public void checkTask5_1() {
        // definition can be removed
        boolean ok = Definition.removeDefinition("merge", "ro", "Micul dicționar academic, ediția a II-a");
        System.out.println(ok);
    }

    public void checkTask5_2() {
        // definition can't be removed
        boolean ok = Definition.removeDefinition("merge", "ro", "DOOM");
        System.out.println(ok);
    }

    public void checkTask6_1() {
        // word exists in both languages
        String translatedWord = Main.collection.translateWord("pisică", "ro", "fr");
        System.out.println(translatedWord);
    }

    public void checkTask6_2() {
        // word doesn't exist in both languages
        Word.removeWord("chien", "fr");
        String translatedWord = Main.collection.translateWord("câine", "ro", "fr");
        System.out.println(translatedWord);
    }

    public void checkTask7() {
        Word joc = new Word("joc", "game", "noun", new String[]{"joc"}, new String[]{"jocuri"},
                new ArrayList<Definition>());
        Word chien = new Word("chien", "dog", "noun", new String[]{"chien"}, new String[]{"chiens"},
                new ArrayList<Definition>());
        Word.addWord(joc, "ro");
        Word.addWord(chien, "fr");

        String translatedSentence = Main.collection.translateSentence("pisică joc câine", "ro", "fr");
        System.out.println(translatedSentence);
    }

    public void checkTask8() {
        String[] translationsArray = Main.collection.translateSentences("pisică joc câine", "ro", "fr");
        for (String s : translationsArray) {
            System.out.println(s);
        }
    }

    public void checkTask9() {
        ArrayList<Definition> definitions = Word.getDefinitionsForWord("câine", "ro");
        for (Definition definition : definitions) {
            System.out.println(definition);
        }
    }

    public void checkTask10() throws IOException {
        Dictionary.exportDictionary("ro");
    }
}
