package minotaur.two;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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

public class SignTTASLock implements Lock {
    private AtomicBoolean state;

    public SignTTASLock() {
        this.state = new AtomicBoolean();
    }

    @Override
    public void lock() {
        while (true) {
            while (state.get() == Status.BUSY.value()) {
            }
            if (state.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value())) {
                return;
            }
        }
    }

    @Override
    public boolean tryLock() {
        if (state.get() == Status.BUSY.value()) {
            return false;
        }

        return state.compareAndSet(Status.AVAILABLE.value(), Status.BUSY.value());
    }

    @Override
    public void unlock() {
        this.state.set(Status.AVAILABLE.value());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
