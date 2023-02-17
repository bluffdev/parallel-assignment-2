package minotaur.one;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class Guest implements Runnable {
    private Integer id;
    private Lock labyrinthLock;
    private AtomicBoolean cupcakeWasEaten;
    private AtomicBoolean tag;
    private AtomicBoolean isPartyOver;

    public Guest(Integer id, Lock labyrinthLock, AtomicBoolean cupcakeWasEaten, AtomicBoolean tag,
            AtomicBoolean isPartyOver) {
        this.id = id;
        this.labyrinthLock = labyrinthLock;
        this.cupcakeWasEaten = cupcakeWasEaten;
        this.tag = tag;
        this.isPartyOver = isPartyOver;
    }

    private void enter() {
        System.out.println("Guest " + this.id + " entered the labyrinth");
    }

    private void exit() {
        System.out.println("Guest " + this.id + " exited the labyrinth");
    }

    private void eatCupcake() {
        System.out.println("Guest " + this.id + " ate a cupcake");
    }

    private void dontEatCupcake() {
        System.out.println("Guest " + this.id + " did not eat a cupcake");
    }

    private void requestNewCupcake() {
        System.out.println("Guest " + this.id + " requested a new cupcake");
    }

    @Override
    public void run() {
        do {
            labyrinthLock.lock();
            try {
                if (this.tag.get() == true) {
                    this.enter();
                    if (this.cupcakeWasEaten.get() == true) {
                        this.requestNewCupcake();
                        this.cupcakeWasEaten.set(false);
                    }
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
                    if (randomNum == 0) {
                        this.dontEatCupcake();
                    } else if (randomNum == 1) {
                        this.eatCupcake();
                        this.cupcakeWasEaten.set(true);
                    }
                    this.exit();
                    this.tag.set(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                labyrinthLock.unlock();
            }
        } while (this.isPartyOver.get() == false);
    }
}
