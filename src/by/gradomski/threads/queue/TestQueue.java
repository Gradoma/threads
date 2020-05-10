package by.gradomski.threads.queue;

import by.gradomski.threads.entity.Gate;
import by.gradomski.threads.entity.LogisticBase;
import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestQueue {
    private static Logger logger = LogManager.getLogger();
    private static TestQueue instance = null;
    private PriorityQueue<Truck> truckQueue = new PriorityQueue<>();
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private LogisticBase base = LogisticBase.getInstance();

    private TestQueue(){};

    public static TestQueue getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new TestQueue();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public Gate addTruckToQueue(Truck truck) {
        Gate g = null;
        try {
            lock.lock();
            logger.info("Truck " + truck.getTruckId() + " will be added to Queue");
            truckQueue.add(truck);
            logger.info("Truck " + truck.getTruckId() + " added. \nQueue after adding: " + truckQueue);
            while (!truckQueue.peek().equals(truck)){
                logger.info("Truck " + truck.getTruckId() + " don't first.");
                condition.await();
            }
            logger.info("Truck " + truck.getTruckId() + " is 1st in Queue");
            g = base.getGateAcquire();

//            if (truckQueue.peek().equals(truck)) {
//                logger.info("Truck " + truck.getTruckId() + " if 1st in Queue");
//                g = base.getGateAcquire();
//            } else {
//                logger.info("Truck " + truck.getTruckId() + " don't first.");
//                condition.await();
//            }
        }catch (InterruptedException e){
            logger.error("InterruptedException ");
            e.printStackTrace();
        }catch (LogisticBaseException l){
            logger.error("LogisticBaseEx l ");
            l.printStackTrace();
        } finally {
            condition.signalAll();
            lock.unlock();
        }
        if(g == null){
            System.out.println("==========GATE NULL WILL RETURN!!!!======");
        }
        return g;
    }

    public boolean leaveQueue(Truck truck){
        try {
            lock.lock();
            logger.info("Method leave for Truck " + truck.getTruckId());
            if(truckQueue.contains(truck)){
                truck = truckQueue.remove();
                logger.info("Truck " + truck.getTruckId() + " unloaded and leave Queue!!! \nQueue after leaving: " + truckQueue);
                return true;
            }
            return false;
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }
}
