package Services;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


public class LockFactory implements Closeable {

    private final int DEFAULT_MAX_CONCURRENT_ACCESS = 1;
    private static LockFactory ourInstance = new LockFactory();
    private HashMap<String, Semaphore> locks;


    private LockFactory() {

        locks = new HashMap<>();
    }

    public static LockFactory getInstance() {

        return ourInstance;
    }

    public void lock(String valueToLockOn) {

        System.out.println(
                String.format("Thread #%d LOCKS on value %s.", Thread.currentThread().getId(), valueToLockOn)
        );

        Semaphore userNameLock = locks.get(valueToLockOn);
        if (userNameLock == null) {
            userNameLock = new Semaphore(DEFAULT_MAX_CONCURRENT_ACCESS);
            locks.put(valueToLockOn, userNameLock);
        }

        try {
            userNameLock.acquire();
        } catch (InterruptedException e) {
            System.err.println(
                    String.format("Thread #%d COULD NOT LOCK on value %s.", Thread.currentThread().getId(), valueToLockOn)
            );
        }
    }

    public void unlock(String valueToUnlock) {

        System.out.println(
                String.format("Thread #%d UNLOCKS value %s.", Thread.currentThread().getId(), valueToUnlock)
        );

        Semaphore userNameLock = locks.get(valueToUnlock);

        userNameLock.release();
    }

    @Override
    public void close() throws IOException {
        locks
                .values()
                .forEach(l -> l.release());
    }
}
