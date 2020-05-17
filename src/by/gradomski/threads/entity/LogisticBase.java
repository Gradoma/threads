package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static Logger logger = LogManager.getLogger();
    public static final int BASE_SIZE = 3;
    private final Semaphore semaphore = new Semaphore(BASE_SIZE, true);
    private final Queue<Gate> gateList = new LinkedList<>();
    private PriorityQueue<Truck> truckQueue = new PriorityQueue<>();
    private static LogisticBase instance;
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private static Lock gateLock = new ReentrantLock();

    private LogisticBase() { }

    public static LogisticBase getInstance() {
        if(instance == null){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new LogisticBase();
                }
            } finally {
                lock.unlock();
            }
            return instance;
        }
        return instance;
    }

    public boolean addGateToList(Gate gate) {
        return gateList.add(gate);
    }

    public Gate getGate(Truck truck) throws LogisticBaseException{
        try {
            if (semaphore.tryAcquire()) {
                gateLock.lock();
                logger.info("Gate free!");
                return gateList.poll();
            }
            addToQueue(truck);
            semaphore.acquire();
            gateLock.lock();
            return gateList.poll();
        } catch (InterruptedException e){
            logger.error("InterruptedException");
            throw new LogisticBaseException(e);
        }
        finally {
            if (truckQueue.contains(truck)){
                leaveQueue(truck);
            }
            gateLock.unlock();
        }
    }

    public void returnGate(Gate gate) {
        gateLock.lock();
        gateList.add(gate);
        semaphore.release();
        gateLock.unlock();
    }

    private void addToQueue(Truck truck){
        try {
            lock.lock();
            truckQueue.add(truck);
            logger.info("Truck " + truck.getTruckId() + " added. Queue : \n" + truckQueue);
            while (truckQueue.peek() != truck) {
                condition.await();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void leaveQueue(Truck truck){
        try{
            lock.lock();
            truckQueue.remove(truck);
            logger.info("Truck " + truck.getTruckId() + " left. Queue : \n" + truckQueue);
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }
}
