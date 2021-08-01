package dit_tasks.task_1.task_1_8_9;

import dit_tasks.task_1.Person;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class App8_9 {

    private interface Exec {
        void exec(List<Person> data) throws Exception;
    }

    private static class MenuItem {

        private String name;
        private Exec exec;

        public MenuItem(String name, Exec exec) {
            this.name = name;
            this.exec = exec;
        }

        public String getName() {
            return name;
        }

        public Exec getExec() {
            return exec;
        }
    }

    private static class Menu {

        private String title;
        private List<MenuItem> items;
        private final Scanner scanner;
        private static final MenuItem quit = new MenuItem("quit", data -> System.exit(0));

        public Menu(String title, boolean addQuite, List<MenuItem> items, Scanner scanner) {
            this.title = title;
            this.items = items;
            this.scanner = scanner;

            initialize(addQuite, items);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private void initialize(boolean addQuit, List<MenuItem> items) {
            this.items = items;
            if (addQuit) {
                this.items.add(quit);
            }
        }

        private void display() {
            int option = 1;
            System.out.println(getTitle() + ":");
            for (MenuItem item : items) {
                System.out.println((option++) + ": " + item.getName());
            }
            System.out.print("select option: ");
            System.out.flush();
        }

        private void runMenu(List<Person> personList) throws IOException {

            while (true) {

                display();

                String line = scanner.nextLine();
                try {
                    int option = Integer.parseInt(line) - 1;
                    if (option >= 0 && option < items.size()) {
                        items.get(option).getExec().exec(personList);
                    } else {
                        System.out.println("not a valid menu option: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("not a valid menu option: " + line);
                }
            }
        }

        public static void main(String[] args) {
            List<Person> personList = new ArrayList<>();
            //test data
            personList.add(new Person("Ivan", "Pupkin"));
            personList.add(new Person("Ilya", "Soloniev"));
            personList.add(new Person("Georg", "Nikolaev"));
            personList.add(new Person("Nikola", "Petrov"));
            personList.add(new Person("Sergey", "Pupkin"));
            //--
            Scanner scanner = new Scanner(System.in);
            List<MenuItem> items = initMenuItem(scanner);
            Menu menu = new Menu("Work with persons", true, items, scanner);
            while (true) {
                try {
                    menu.runMenu(personList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static List<MenuItem> initMenuItem(Scanner scanner) {

            List<MenuItem> items = new ArrayList<>();
            items.add(new MenuItem("Add", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    String first;
                    String last;

                    System.out.println("Input values person");
                    System.out.print("firstName: ");
                    first = scanner.nextLine();
                    System.out.print("lastName: ");
                    last = scanner.nextLine();

                    data.add(new Person(first, last));
                    System.out.println(data.get(data.size() - 1) + " add success.");
                }
            }));
            items.add(new MenuItem("Show", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    if (data.isEmpty()) {
                        System.out.println("list is empty!");
                        return;
                    }
                    data.forEach(System.out::println);
                }
            }));
            items.add(new MenuItem("Show sorted unique", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    if (data.isEmpty()) {
                        System.out.println("list is empty!");
                        return;
                    }
                    data.stream()
                            .collect(Collectors.toCollection(
                                    () -> new TreeSet<Person>(Comparator.comparing(Person::getLastName))
                            )).forEach(System.out::println);
                }
            }));
            items.add(new MenuItem("Save to file", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("PersonsOOP.txt"))) {
                        for (Person person : data) {
                            writer.append(person.toString() + "\r\n");
                        }
                        System.out.println("list save to file!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));
            items.add(new MenuItem("Read from file", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    try (BufferedReader br = new BufferedReader(new FileReader("PersonsOOP.txt"))) {
                        String s;
                        while ((s = br.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
            items.add(new MenuItem("Clear data in memory", new Exec() {
                @Override
                public void exec(List<Person> data) throws Exception {
                    data.clear();
                    System.out.println("list is clear!");
                }
            }));

            return items;
        }
    }
}
