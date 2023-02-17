package minotaur.two;

import java.util.concurrent.ThreadLocalRandom;

public class Guest implements Runnable {
    private Integer id;
    private SignBackoffLock sign;

    public Guest(Integer id, SignBackoffLock sign) {
        this.id = id;
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
            this.sign.lock();
            try {
                this.enterRoom();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.exitRoom();
                this.sign.unlock();
                wantsToEnter = this.doesGuestWantToEnter();
            }
        }
    }
}
