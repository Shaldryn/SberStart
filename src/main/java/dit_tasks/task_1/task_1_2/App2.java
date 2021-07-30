package dit_tasks.task_1.task_1_2;

import dit_tasks.task_1.Person;

import java.util.Scanner;

public class App2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String first;
        String last;

        System.out.println("Input values person");
        System.out.print("firstName: ");
        first = sc.nextLine();
        System.out.print("lastName: ");
        last = sc.nextLine();

        System.out.println(new Person(first, last));
    }
}
