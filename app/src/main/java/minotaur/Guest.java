package minotaur;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

enum Status {
    AVAILABLE(true), BUSY(false);

    private Boolean status;

    public Boolean value() {
        return this.status;
    }

    private Status(Boolean status) {
        this.status = status;
    }
}

public class Guest implements Runnable {
    private Integer id;
    private AtomicBoolean isRoomAvailable;
    private Boolean entered = false;
    private Lock roomLock;

    public Guest(Integer id, AtomicBoolean isRoomAvailable, Lock roomLock) {
        this.id = id;
        this.isRoomAvailable = isRoomAvailable;
        this.roomLock = roomLock;
    }

    @Override
    public void run() {
        while (!this.entered) {
            if (this.isRoomAvailable.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value())) {
                this.roomLock.lock();
                try {
                    System.out.println("meme");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    this.entered = true;
                    this.isRoomAvailable.set(Status.AVAILABLE.value());
                    this.roomLock.unlock();
                }
            }
        }
    }
}
