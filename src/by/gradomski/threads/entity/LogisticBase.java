package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static final int BASE_SIZE = 3;
    private final Semaphore semaphore = new Semaphore(BASE_SIZE, true);
    private final Queue<Gate> gateList = new LinkedList<>();
    private static LogisticBase instance = null;
    private static ReentrantLock lock = new ReentrantLock();

    private LogisticBase(){};

    public LogisticBase getInstance(){
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

    public boolean addGateList(List<Gate> gates){
        return gateList.addAll(gates);
    }

    public Gate getGate() throws LogisticBaseException{
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
