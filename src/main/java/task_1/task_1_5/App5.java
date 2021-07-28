package task_1.task_1_5;

import task_1.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App5 {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Menu:");
            System.out.println("1.Add");
            System.out.println("2.Show");
            System.out.println("3.Exit");

            switch (scanner.nextLine()) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    break;
            }

        } while (scanner.nextLine().equals("3"));
    }
}
