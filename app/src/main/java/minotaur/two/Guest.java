package minotaur.two;

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
    private Lock roomLock;

    public Guest(Integer id, AtomicBoolean isRoomAvailable, Lock roomLock) {
        this.id = id;
        this.isRoomAvailable = isRoomAvailable;
        this.roomLock = roomLock;
    }

    private Boolean doesGuestWantToEnter() {
        int percentage = ThreadLocalRandom.current().nextInt(0, 10);
        return percentage < 2 ? false : true;
    }

    private void enterRoom() {
        System.out.println("Guest " + this.id + " entered the room");
    }

    private void exitRoom() {
        System.out.println("Guest " + this.id + " exited the room");
    }

    @Override
    public void run() {
        Boolean wantsToEnter = this.doesGuestWantToEnter();

        while (wantsToEnter) {
            if (this.isRoomAvailable.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value())) {
                this.roomLock.lock();
                try {
                    this.enterRoom();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    this.exitRoom();
                    this.isRoomAvailable.set(Status.AVAILABLE.value());
                    this.roomLock.unlock();
                    wantsToEnter = this.doesGuestWantToEnter();
                }
            }
        }
    }
}
