package task_1.task_1_3;

import task_1.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App3 {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int counter = 0;
        String first;
        String last;

        System.out.println("Input values person.");
        System.out.println("For exit input text 'exit'");
        while (true) {
            counter++;
            System.out.printf("Person %d%nfirstName: ", counter);
            first = sc.nextLine();
            if (first.equals("exit")) {
                break;
            }
            System.out.print("lastName: ");
            last = sc.nextLine();
            if (last.equals("exit")) {
                break;
            }
            persons.add(new Person(first, last));
        }

        persons.forEach(System.out::println);
    }
}
