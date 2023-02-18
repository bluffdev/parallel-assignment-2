package minotaur.two;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class QNode {
    AtomicBoolean locked = new AtomicBoolean();
}

public class CLHLock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;
    ThreadLocal<QNode> myPred;

    public CLHLock() {
        this.tail = new AtomicReference<QNode>(new QNode());
        this.myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        this.myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public void lock() {
        QNode node = this.myNode.get();
        node.locked.set(true);
        QNode pred = tail.getAndSet(node);
        this.myPred.set(pred);
        while (pred.locked.get()) {
        }
    }

    public void unlock() {
        QNode node = this.myNode.get();
        node.locked.set(false);
        this.myNode.set(this.myPred.get());
    }
}
