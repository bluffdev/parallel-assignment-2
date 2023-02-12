package minotaur;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur implements Runnable {
    private Integer numOfGuests;
    private AtomicBoolean[] guestTags;
    private Set<Integer> enteredSet;
    private Integer guestCounter;
    private ReentrantLock labyrinthLock;
    private Integer last;
    private AtomicBoolean isPartyOver;

    public Minotaur(Integer numOfGuests, AtomicBoolean[] guestTags, ReentrantLock labyrinthLock,
            AtomicBoolean isPartyOver) {
        this.numOfGuests = numOfGuests;
        this.guestTags = guestTags;
        this.enteredSet = new HashSet<Integer>();
        this.guestCounter = 0;
        this.labyrinthLock = labyrinthLock;
        this.last = 0;
        this.isPartyOver = isPartyOver;
    }

    @Override
    public void run() {
        while (guestCounter < numOfGuests) {
            // System.out.println("Not End");
            // if (this.guestTags[this.last].get() == true)
            // continue;
            // else {
            // System.out.println("meme");
            // }
            this.labyrinthLock.lock();
            try {
                if (this.guestTags[this.last].get() == false) {
                    // System.out.println("Minotaur Locked");
                    // generate a random number
                    int randomGuest = ThreadLocalRandom.current().nextInt(0, numOfGuests);
                    // let that person enter the labyrinth
                    this.guestTags[randomGuest].set(true);
                    this.last = randomGuest;
                    // if its their first time increment counter
                    if (!this.enteredSet.contains(randomGuest)) {
                        this.enteredSet.add(randomGuest);
                        this.guestCounter += 1;
                        if (this.guestCounter == numOfGuests) {
                            this.isPartyOver.set(true);
                            System.out.println(this.enteredSet);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.labyrinthLock.unlock();
                // System.out.println("Minotaur Unlocked");
            }
        }
    }
}
