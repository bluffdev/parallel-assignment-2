package minotaur;

import java.util.concurrent.locks.ReentrantLock;

public class Birthday {
    private Integer guestCount;
    private Thread[] guests;
    private ReentrantLock labyrinthLock;

    public Birthday(Integer guestCount) {
        this.guestCount = guestCount;
        this.guests = new Thread[guestCount];
        this.labyrinthLock = new ReentrantLock();
    }

    public void simulate() {
        // Initializes threads
        for (int i = 0; i < guestCount; i++) {
            BirthdayGuest newGuest = new BirthdayGuest(i + 1, labyrinthLock);
            guests[i] = new Thread(newGuest);
            guests[i].start();
        }

        // Wait for threads to finish
        for (Thread guest : guests) {
            try {
                guest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
