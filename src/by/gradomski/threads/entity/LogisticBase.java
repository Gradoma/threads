package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static Logger logger = LogManager.getLogger();
    private static final int BASE_SIZE = 3;
    private final Semaphore semaphore = new Semaphore(BASE_SIZE, true);
    private final Queue<Gate> gateList = new LinkedList<>();
    private static LogisticBase instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    public int baseCapacity = 1000;

    private LogisticBase(){};

    public static LogisticBase getInstance(){
        lock.lock();
        try{
            if(instance == null) {
                instance = new LogisticBase();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public boolean addGateToList(Gate gate){
        return gateList.add(gate);
    }

    public Gate getGate(int truckNumber) throws LogisticBaseException{      // DELETE PARAMETER!!!!
        logger.info("Truck " + truckNumber + " trying to get Gate...");
        try{
            semaphore.acquire();
            return gateList.poll();
        } catch (InterruptedException e){
            throw new LogisticBaseException(e);
        }
    }

    public void returnGate(Gate gate){
        gateList.add(gate);
        semaphore.release();
    }
}
