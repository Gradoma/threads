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
    private static final int BASE_SIZE = 3;
    private final Semaphore semaphore = new Semaphore(BASE_SIZE, true);
    private final Queue<Gate> gateList = new LinkedList<>();
    private PriorityQueue<Truck> truckQueue = new PriorityQueue<>();
    private static LogisticBase instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public int baseCapacity = 1000;

    private LogisticBase() { };

    public static LogisticBase getInstance() {
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

    public boolean addGateToList(Gate gate) {
        return gateList.add(gate);
    }
//
//    public void addTruckToQueue(Truck truck) {
//        try {
//            lock.lock();
//            logger.info("Truck " + truck.getTruckId() + " will be added to Queue");
//            if (truckQueue.isEmpty()) {
//                logger.info("Truck Queue is empty");
//                truckQueue.add(truck);
//                logger.info("Truck " + truck.getTruckId() + " added. \nQueue after adding: " + truckQueue);
//            } else {
//                truckQueue.add(truck);
//                logger.info("Truck " + truck.getTruckId() + " added. \nQueue after adding: " + truckQueue);
//            }
//        }catch (InterruptedException e){
//            logger.error("InterruptedException ");
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void leaveQueue() {
//        Truck truck = null;
//        try {
//
//            logger.info("Method leave");
//            lock.lock();
//            truck = truckQueue.remove();
//            logger.info("Truck " + truck.getTruckId() + " unloaded and leave Queue!!! \nQueue after leaving: " + truckQueue);
//        } finally {
//            lock.unlock();
//        }
//    }

//    public Gate addTruckToQueue(Truck truck) {
//        Gate gate = null;
//        try {
//            lock.lock();
//            logger.info("Truck " + truck.getTruckId() + " will be added to Queue");
//            truckQueue.add(truck);
//            logger.info("Truck " + truck.getTruckId() + " added. \nQueue after adding: " + truckQueue);
//            if (truckQueue.peek().equals(truck)) {
//                logger.info("Truck " + truck.getTruckId() + " is 1st in Queue");
//                semaphore.acquire();
//                gate = gateList.poll();
//            } else {
//                logger.info("Truck " + truck.getTruckId() + " don't first.");
//                condition.await();
//            }
//        }catch (InterruptedException e){
//            logger.error("InterruptedException ");
//            e.printStackTrace();
//        } finally {
//            condition.signalAll();
//            lock.unlock();
//        }
//        if(gate == null){
//            logger.warn("Gate is null!!");
//        }
//        return gate;
//    }
//
//    public boolean leaveQueue(Truck truck){
//        try {
//            lock.lock();
//            logger.info("Method leave");
//            if(truckQueue.contains(truck)){
//                truck = truckQueue.remove();
//                logger.info("Truck " + truck.getTruckId() + " unloaded and leave Queue!!! \nQueue after leaving: " + truckQueue);
//                return true;
//            }
//            return false;
//        } finally {
//            condition.signalAll();
//            lock.unlock();
//        }
//    }

    public Gate getGateAcquire() throws LogisticBaseException{
        try {
            semaphore.acquire();
            return gateList.poll();
        } catch (InterruptedException e) {
            throw new LogisticBaseException(e);
        }
    }

    public Gate getGate(int truckNumber) throws LogisticBaseException {      // DELETE PARAMETER!!!!
        logger.info("Truck " + truckNumber + " trying to get Gate...");
//
        if (semaphore.tryAcquire()) {
            logger.info("Gate free!!!");
            return gateList.poll();
        }
        throw new LogisticBaseException("no free gates");
    }

    public void returnGate(Gate gate) {
        gateList.add(gate);
        semaphore.release();
    }
}
