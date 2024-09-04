package main;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    static Collection collection;

    public static void main(String[] args) {
        try {
            Checker checker = new Checker();

            // checking all tasks
            checker.checkTask1();
            checker.checkTask2();
            checker.checkTask3_1();
            checker.checkTask3_2();
            checker.checkTask4_1();
            checker.checkTask4_2();
            checker.checkTask5_1();
            checker.checkTask5_2();
            checker.checkTask6_1();
            checker.checkTask6_2();
            checker.checkTask7();
            checker.checkTask8();
            checker.checkTask9();
            checker.checkTask10();

            System.out.println(collection);
            String[] s1 = new String[]{"ana", "are"};
            String[] s2 = new String[]{"ana", "are"};
            System.out.println(Arrays.equals(s1, s2));
            System.out.println(Arrays.deepEquals(s1, s2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
