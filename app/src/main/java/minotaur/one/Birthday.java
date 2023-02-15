package minotaur.one;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Birthday {
    private Integer guestCount;
    private Thread[] guests;
    private ReentrantLock labyrinthLock;
    private AtomicBoolean cupcakeWasEaten;
    private AtomicBoolean[] guestTags;
    private AtomicBoolean isPartyOver;

    public Birthday(Integer guestCount) {
        this.guestCount = guestCount;
        this.guests = new Thread[guestCount];
        this.labyrinthLock = new ReentrantLock();
        this.cupcakeWasEaten = new AtomicBoolean();
        this.guestTags = new AtomicBoolean[guestCount];
        for (int i = 0; i < guestCount; i++) {
            this.guestTags[i] = new AtomicBoolean();
        }
        this.isPartyOver = new AtomicBoolean();
    }

    public void simulate() {
        // Initializes threads
        Minotaur minotaur = new Minotaur(guestCount, this.guestTags, labyrinthLock, this.isPartyOver);
        Thread minotaurThread = new Thread(minotaur);
        minotaurThread.start();
        for (int i = 0; i < guestCount; i++) {
            Guest newGuest = new Guest(i + 1, labyrinthLock, cupcakeWasEaten, this.guestTags[i],
                    this.isPartyOver);
            guests[i] = new Thread(newGuest);
            guests[i].start();
        }

        try {
            minotaurThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
