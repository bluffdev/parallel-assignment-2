package minotaur.two;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CrystalVase {
    private Integer guestCount;
    private Thread[] guests;
    private AtomicBoolean isRoomAvailable;

    public CrystalVase(Integer guestCount) {
        this.guestCount = guestCount;
        this.guests = new Thread[guestCount];
        this.isRoomAvailable = new AtomicBoolean(true);
    }

    public void simulate() {
        Lock roomLock = new ReentrantLock();

        for (int i = 0; i < this.guestCount; i++) {
            Guest newGuest = new Guest(i + 1, this.isRoomAvailable, roomLock);
            this.guests[i] = new Thread(newGuest);
            this.guests[i].start();
        }

        for (Thread guest : this.guests) {
            try {
                guest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
