package task_1.task_1_5;

import task_1.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App5 {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            System.out.println("Menu:");
            System.out.println("1.Add");
            System.out.println("2.Show");
            System.out.println("3.Exit");

            switch (scanner.nextLine()) {
                case "1":
                    addPerson(scanner, personList);
                    break;
                case "2":
                    showPersons(personList);
                    break;
                case "3":
                    break;
                default:
                    break;
            }

        }
    }

    public static void addPerson(Scanner scanner, List<Person> personList){
        String first;
        String last;

        System.out.println("Input values person");
        System.out.print("firstName: ");
        first = scanner.nextLine();
        System.out.print("lastName: ");
        last = scanner.nextLine();

        personList.add(new Person(first, last));
    }

    public static void showPersons(List<Person> personList) {
        if (personList.isEmpty()) {
            System.out.println("list is empty!");
        }
        personList.forEach(System.out::println);
    }
}
