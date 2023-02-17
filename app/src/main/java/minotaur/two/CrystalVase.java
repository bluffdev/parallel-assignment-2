package minotaur.two;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CrystalVase {
    private Integer guestCount;

    public CrystalVase(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public void simulate() {
        Lock roomLock = new ReentrantLock();
        AtomicBoolean isRoomAvailable = new AtomicBoolean(true);

        Date start = new Date();

        Thread[] guests = new Thread[this.guestCount];

        for (int i = 0; i < this.guestCount; i++) {
            Guest newGuest = new Guest(i + 1, isRoomAvailable, roomLock);
            guests[i] = new Thread(newGuest);
            guests[i].start();
        }

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
