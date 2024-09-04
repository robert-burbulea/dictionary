package main;

import java.util.Comparator;

public class YearComparator implements Comparator<Definition> {
    @Override
    public int compare(Definition o1, Definition o2) {
        return (o1.getYear() - o2.getYear());
    }
}
