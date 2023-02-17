package minotaur.one;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Birthday {
    private Integer guestCount;

    public Birthday(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public void simulate() {
        AtomicBoolean cupcakeWasEaten = new AtomicBoolean();
        AtomicBoolean[] guestTags = new AtomicBoolean[this.guestCount];
        AtomicBoolean isPartyOver = new AtomicBoolean();
        for (int i = 0; i < guestCount; i++) {
            guestTags[i] = new AtomicBoolean();
        }
        Lock labyrinthLock = new ReentrantLock();

        Date start = new Date();

        // Initialize Threads
        Counter minotaur = new Counter(guestCount, guestTags, labyrinthLock, isPartyOver);
        Thread minotaurThread = new Thread(minotaur);
        minotaurThread.start();

        Thread[] guests = new Thread[this.guestCount];

        for (int i = 0; i < guestCount; i++) {
            Guest newGuest = new Guest(i + 1, labyrinthLock, cupcakeWasEaten, guestTags[i], isPartyOver);
            guests[i] = new Thread(newGuest);
            guests[i].start();
        }

        try {
            minotaurThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Wait for guest threads to finish
        for (Thread guest : guests) {
            try {
                guest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Date end = new Date();
        Long seconds = (end.getTime() - start.getTime());
        System.out.println("Runtime: " + seconds + "ms");
    }
}
