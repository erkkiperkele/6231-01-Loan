package Services;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


public class LockFactory implements Closeable {

    private final int DEFAULT_MAX_CONCURRENT_ACCESS = 1;
    private static LockFactory ourInstance = new LockFactory();

    private HashMap<String, Integer> readCounts;

    private HashMap<String, Semaphore> writeLocks;
    private HashMap<String, Semaphore> readLocks;
    private HashMap<String, Semaphore> readCountsMutex;


    private LockFactory() {

        writeLocks = new HashMap<>();
        readLocks = new HashMap<>();
        readCountsMutex = new HashMap<>();
        readCounts = new HashMap<>();
    }

    public static LockFactory getInstance() {

        return ourInstance;
    }

    public void readlock(String valueToLockOn) {

        Semaphore writeLock = getLazyWriteLock(valueToLockOn);
        Semaphore readLock = getLazyReadLock(valueToLockOn);
        Semaphore readCount = getLazyReadCountLock(valueToLockOn);

        try {
            readLock.acquire();
            readCount.acquire();
            Integer oldCount = getCount(valueToLockOn);
            Integer newCount = oldCount + 1;

            if (newCount == 1) {
                writeLock.acquire();
            }

            readCounts.replace(valueToLockOn, oldCount, newCount);

        } catch (InterruptedException e) {
        } finally {
            readCount.release();
            readLock.release();
        }

    }

    public void readUnlock(String valueToUnlock) {

        Semaphore writeLock = getLazyWriteLock(valueToUnlock);
        Semaphore readLock = getLazyReadLock(valueToUnlock);
        Semaphore readCount = getLazyReadCountLock(valueToUnlock);

        try {
            readCount.acquire();

            Integer oldCount = readCounts.get(valueToUnlock);
            Integer newCount = oldCount - 1;
            readCounts.replace(valueToUnlock, oldCount, newCount);

            if (newCount == 0) {
                writeLock.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            readCount.release();

        }
    }

    public void writeLock(String valueToLockOn) {

        System.out.println(
                String.format("Thread #%d WRITE LOCKS on value %s.", Thread.currentThread().getId(), valueToLockOn)
        );


        Semaphore writeLock = getLazyWriteLock(valueToLockOn);
        Semaphore readLock = getLazyReadLock(valueToLockOn);

        try {
            readLock.acquire();
            writeLock.acquire();
        } catch (InterruptedException e) {
            System.err.println(
                    String.format("Thread #%d COULD NOT WRITE LOCK on value %s.", Thread.currentThread().getId(), valueToLockOn)
            );
        }
    }

    public void writeUnlock(String valueToUnlock) {

        System.out.println(
                String.format("Thread #%d WRITE UNLOCKS value %s.", Thread.currentThread().getId(), valueToUnlock)
        );

        Semaphore writeLock = getLazyWriteLock(valueToUnlock);
        Semaphore readLock = getLazyReadLock(valueToUnlock);

        writeLock.release();
        readLock.release();
    }

    private Integer getCount(String index)
    {
        Integer count = readCounts.get(index);
        if (count == null)
        {
            count =  0;
            readCounts.put(index, count);
        }
        return count;
    }

    private synchronized Semaphore getLazyWriteLock(String valueToLockOn) {
        Semaphore valueLock = writeLocks.get(valueToLockOn);
        if (valueLock == null) {
            valueLock = new Semaphore(1);
            writeLocks.put(valueToLockOn, valueLock);
        }
        return writeLocks.get(valueToLockOn);
    }

    private synchronized Semaphore getLazyReadLock(String valueToLockOn) {
        Semaphore readlock = readLocks.get(valueToLockOn);
        if (readlock == null) {
            readlock = new Semaphore(1);
            readLocks.put(valueToLockOn, readlock);
        }
        return readLocks.get(valueToLockOn);
    }

    private synchronized Semaphore getLazyReadCountLock(String valueToLockOn) {
        Semaphore mutex = readCountsMutex.get(valueToLockOn);
        if (mutex == null) {
            mutex = new Semaphore(1);
            readCountsMutex.put(valueToLockOn, mutex);
        }

        return mutex;
    }

    @Override
    public void close() throws IOException {
        writeLocks
                .values()
                .forEach(l -> l.release());
    }
}
