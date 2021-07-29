package task_1.task_1_7;

import task_1.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App7 {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            System.out.println("Menu:");
            System.out.println("1.Add");
            System.out.println("2.Show");
            System.out.println("3.Show sorted unique");
            System.out.println("4.Save to file");
            System.out.println("5.Exit");

            switch (scanner.nextInt()) {
                case 1:
                    addPerson(scanner, personList);
                    break;
                case 2:
                    showPersons(personList);
                    break;
                case 3:
                    showSortedUniquePerson(personList);
                    break;
                case 4:
                    savePersonsToFile(personList);
                    break;
                case 5:
                    isExit = true;
                    break;
                default:
                    System.out.println("incorrect command!");
                    break;
            }

        }
    }

    private static void addPerson(Scanner scanner, List<Person> personList) {
        String first;
        String last;

        System.out.println("Input values person");
        System.out.print("firstName: ");
        first = scanner.nextLine();
        System.out.print("lastName: ");
        last = scanner.nextLine();

        personList.add(new Person(first, last));
    }

    private static void showPersons(List<Person> personList) {
        if (personList.isEmpty()) {
            System.out.println("list is empty!");
        }
        personList.forEach(System.out::println);
    }

    private static void showSortedUniquePerson(List<Person> personList) {
        personList.stream()
                .filter(distinctByKey(Person::getLastName))
                .collect(Collectors.toList())
                .stream().sorted(Comparator.comparing(Person::getLastName))
                .forEach(System.out::println);
    }

    private static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static void savePersonsToFile(List<Person> personList) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Persons.txt"))) {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Calendar cal = Calendar.getInstance();
//            writer.append(dateFormat.format(cal.getTime()) + "\r\n");
            for (Person person : personList) {
                writer.append(person.toString() + "\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
