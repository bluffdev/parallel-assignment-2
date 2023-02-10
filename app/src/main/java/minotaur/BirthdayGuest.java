package minotaur;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class BirthdayGuest implements Runnable {
    private Integer id;
    private ReentrantLock labyrinthLock;
    private Boolean visitedLabyrinth;

    public BirthdayGuest(Integer id, ReentrantLock labyrinthLock) {
        this.id = id;
        this.labyrinthLock = labyrinthLock;
        this.visitedLabyrinth = false;
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
        labyrinthLock.lock();
        try {
            this.enter();
            int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
            if (randomNum == 0) {
                dontEatCupcake();
            } else if (randomNum == 1) {
                eatCupcake();
            }
            this.exit();
            this.visitedLabyrinth = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            labyrinthLock.unlock();
        }
    }
}
