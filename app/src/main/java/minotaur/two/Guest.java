package minotaur.two;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Guest implements Runnable {
    private Integer id;
    private AtomicBoolean isRoomAvailable;
    private SignTTASLock sign;

    public Guest(Integer id, AtomicBoolean isRoomAvailable, SignTTASLock sign) {
        this.id = id;
        this.isRoomAvailable = isRoomAvailable;
        this.sign = sign;
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
            // if (this.isRoomAvailable.compareAndSet(Status.AVAILABLE.value(),
            // Status.BUSY.value())) {
            this.sign.lock();
            try {
                this.enterRoom();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.exitRoom();
                this.isRoomAvailable.set(Status.AVAILABLE.value());
                this.sign.unlock();
                wantsToEnter = this.doesGuestWantToEnter();
            }
            // }
        }
    }
}
