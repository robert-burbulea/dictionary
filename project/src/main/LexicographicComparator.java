package main;

import java.util.Comparator;

public class LexicographicComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        return w1.getWord().compareTo(w2.getWord());
    }
}
