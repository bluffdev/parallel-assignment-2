package minotaur.two;

import java.util.concurrent.atomic.AtomicBoolean;

enum Status {
    AVAILABLE(false), BUSY(true);

    private Boolean status;

    public Boolean value() {
        return this.status;
    }

    private Status(Boolean status) {
        this.status = status;
    }
}

// This is a TTAS Backoff lock from the textbook that will represent the sign
public class SignBackoffLock {
    private AtomicBoolean state;

    public SignBackoffLock() {
        this.state = new AtomicBoolean();
    }

    public void lock() {
        while (true) {
            int delay = 1;
            // The guest checks if the room is available
            while (state.get() == Status.BUSY.value()) {
                // The guest leaves and comes back later
            }
            // The guest attempts to enter the room before someone else does
            if (state.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value())) {
                return;
            }
            // If the guest misses than they will not try to enter for a period of time
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (delay < 6) {
                    delay *= 2;
                }
            }
        }
    }

    public void unlock() {
        this.state.set(Status.AVAILABLE.value());
    }
}