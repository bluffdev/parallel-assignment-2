package minotaur;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class BirthdayGuest implements Runnable {
    private Integer id;
    private ReentrantLock labyrinthLock;
    private AtomicBoolean cupcakeWasEaten;
    private AtomicBoolean[] tags;
    private AtomicBoolean isPartyOver;

    public BirthdayGuest(Integer id, ReentrantLock labyrinthLock, AtomicBoolean cupcakeWasEaten, AtomicBoolean[] tags,
            AtomicBoolean isPartyOver) {
        this.id = id;
        this.labyrinthLock = labyrinthLock;
        this.cupcakeWasEaten = cupcakeWasEaten;
        this.tags = tags;
        this.isPartyOver = isPartyOver;
    }

    public void enter() {
        System.out.println("Guest " + this.id + " entered the labyrinth");
    }

    public void exit() {
        System.out.println("Guest " + this.id + " exited the labyrinth");
    }

    public void eatCupcake() {
        System.out.println("Guest " + this.id + " ate a cupcake");
    }

    public void dontEatCupcake() {
        System.out.println("Guest " + this.id + " did not eat a cupcake");
    }

    public void requestNewCupcake() {
        System.out.println("Guest " + this.id + " requested a new cupcake");
    }

    @Override
    public void run() {
        do {
            labyrinthLock.lock();
            try {
                if (this.tags[this.id - 1].get() == true) {
                    this.enter();
                    if (this.cupcakeWasEaten.get() == true) {
                        requestNewCupcake();
                    }
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
                    if (randomNum == 0) {
                        dontEatCupcake();
                    } else if (randomNum == 1) {
                        eatCupcake();
                        this.cupcakeWasEaten.set(true);
                    }
                    this.exit();
                    this.tags[this.id - 1].set(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                labyrinthLock.unlock();
            }
        } while (this.isPartyOver.get() == false);
    }
}
