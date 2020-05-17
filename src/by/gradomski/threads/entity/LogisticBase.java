package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static Logger logger = LogManager.getLogger();
    public static final int BASE_SIZE = 3;
    public final int baseCapacity = 1000;
    private AtomicInteger currentLoaded = new AtomicInteger(0);
    private final Semaphore semaphore = new Semaphore(BASE_SIZE, true);
    private final Queue<Gate> gateList = new LinkedList<>();
    private PriorityQueue<Truck> truckQueue = new PriorityQueue<>();
    private static LogisticBase instance;
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private static ReentrantLock checkingLock = new ReentrantLock();
    private Condition checkingCondition = checkingLock.newCondition();
    private static ReentrantLock cargoLock = new ReentrantLock();
    private Condition cargoCondition = cargoLock.newCondition();

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

    public void calculateFreeSpace(){           //DELETE !!!
        cargoLock.lock();
        System.out.println("Total: " + baseCapacity);
        System.out.println("Loaded: " + currentLoaded.get());
        System.out.println("Free space: " + (baseCapacity - currentLoaded.get()));
        double percentLoaded = ((double)currentLoaded.get() / baseCapacity) * 100;
        double percentFree = 100 - percentLoaded;
        System.out.println("Free - " + percentFree + "; Loaded - " + percentLoaded);
        cargoLock.unlock();
    }

    public void addCargo(int cargoWeight){
        try{
            cargoLock.lock();
            while(baseCapacity - currentLoaded.get() < cargoWeight){
                cargoCondition.await();
            }
            currentLoaded.addAndGet(cargoWeight);
        } catch (InterruptedException e){
            logger.error("InterruptedException");
        } finally {
            cargoCondition.signalAll();
            cargoLock.unlock();
        }
    }

    public void getCargo(int truckCapacity){
        try{
            cargoLock.lock();
            while (currentLoaded.get() == 0 || currentLoaded.get() < truckCapacity){
                cargoCondition.await();
            }
            currentLoaded.addAndGet(-truckCapacity);
        } catch (InterruptedException e){
            logger.error("InterruptedException");
        } finally {
            cargoCondition.signalAll();
            cargoLock.unlock();
        }
    }

    public boolean addGateToList(Gate gate) {
        return gateList.add(gate);
    }

    public Gate getGate() {
        if (semaphore.tryAcquire()) {
            logger.info("Gate free!");
            return gateList.poll();
        }
        return null;
    }

    public void returnGate(Gate gate) {
        gateList.add(gate);
        semaphore.release();
    }

    public void addTruckToQueue(Truck truck){
        try {
            lock.lock();
            truckQueue.add(truck);
            logger.info("Truck " + truck.getTruckId() + " added. Queue after adding: \n" + truckQueue);
        }finally {
            lock.unlock();
        }
    }

    public Gate getGateByTruckQueue(Truck truck)throws LogisticBaseException{
        try {
            checkingLock.lock();
            while (truckQueue.peek() != truck) {
                checkingCondition.await();
            }
            checkingLock.unlock();
            semaphore.acquire();
            return gateList.poll();
        } catch (InterruptedException e) {
            throw new LogisticBaseException(e);
        } finally {
            if (checkingLock.isLocked()){
                checkingLock.unlock();
            }
        }
    }

    public void leaveQueue(Truck truck){
        try{
            lock.lock();
            checkingLock.lock();
            truckQueue.remove(truck);
            logger.info("Truck " + truck.getTruckId() + " left. Queue after leaving: \n" + truckQueue);
        }finally {
            lock.unlock();
            checkingCondition.signalAll();
            checkingLock.unlock();
        }
    }
}
