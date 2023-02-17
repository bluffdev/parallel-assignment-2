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

// This is a TTAS lock from the textbook that represents the sign
public class SignTTASLock {
    private AtomicBoolean state;

    public SignTTASLock() {
        this.state = new AtomicBoolean();
    }

    public void lock() {
        while (true) {
            // The guest checks if the room is available
            while (state.get() == Status.BUSY.value()) {
                // The guest leaves and comes back later
            }
            // The guest attempts to enter the room before someone else does
            if (state.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value())) {
                return;
            }
        }
    }

    public boolean tryLock() {
        if (state.get() == Status.BUSY.value()) {
            return false;
        }

        return state.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value());
    }

    public void unlock() {
        this.state.set(Status.AVAILABLE.value());
    }
}