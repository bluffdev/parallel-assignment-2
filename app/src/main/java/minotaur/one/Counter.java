package minotaur.one;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class Counter implements Runnable {
    private Integer guestCount;
    private AtomicBoolean[] guestTags;
    private Set<Integer> enteredSet;
    private Lock labyrinthLock;
    private Integer last;
    private AtomicBoolean isPartyOver;

    public Counter(Integer guestCount, AtomicBoolean[] guestTags, Lock labyrinthLock,
            AtomicBoolean isPartyOver) {
        this.guestCount = guestCount;
        this.guestTags = guestTags;
        this.enteredSet = new HashSet<Integer>();
        this.labyrinthLock = labyrinthLock;
        this.last = 0;
        this.isPartyOver = isPartyOver;
    }

    @Override
    public void run() {
        while (this.enteredSet.size() < guestCount) {
            this.labyrinthLock.lock();
            try {
                if (this.guestTags[this.last].get() == false) {
                    // find out which guest will enter the labyrinth
                    int randomGuest = ThreadLocalRandom.current().nextInt(0, this.guestCount);

                    // mark down guest as entering the labyrinth
                    this.guestTags[randomGuest].set(true);
                    this.last = randomGuest;

                    // if its their first time add to set
                    if (!this.enteredSet.contains(randomGuest)) {
                        this.enteredSet.add(randomGuest);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (this.enteredSet.size() == this.guestCount) {
                    this.isPartyOver.set(true);
                }
                this.labyrinthLock.unlock();
            }
        }
    }
}
