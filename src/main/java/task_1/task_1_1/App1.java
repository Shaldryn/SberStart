package task_1.task_1_1;

import task_1.Person;

public class App1 {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Заполните параметры Person(firstName, lastName)");
        }
        System.out.println(new Person(args[0], args[1]));
    }
}
