package minotaur;

import minotaur.one.Birthday;
import minotaur.two.CrystalVase;

public class App {
    public static void main(String[] args) {
        // Birthday birthday = new Birthday(100);
        // birthday.simulate();
        CrystalVase crystalVase = new CrystalVase(5);
        crystalVase.simulate();
    }
}
