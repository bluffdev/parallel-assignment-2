package minotaur;

import java.util.Scanner;

import minotaur.one.Birthday;
import minotaur.two.CrystalVase;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        scan.close();

        String[] parts = input.split(" ");

        String problem = parts[0];
        Integer guestCount = Integer.parseInt(parts[1]);
        System.out.println(problem + " " + guestCount);

        if (problem.equals("birthday")) {
            Birthday birthday = new Birthday(guestCount);
            birthday.simulate();
        } else if (problem.equals("vase")) {
            CrystalVase crystalVase = new CrystalVase(guestCount);
            crystalVase.simulate();
        } else {
            System.out.println("Invalid input");
        }
    }
}
