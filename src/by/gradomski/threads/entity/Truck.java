package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import by.gradomski.threads.queue.TestQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Truck extends Thread implements Comparable<Truck>{
    private static Logger logger = LogManager.getLogger();
    private int id;
    private int capacity;
    private boolean hasFridge;
    private TestQueue testQueue = TestQueue.getInstance();                      // DELETE!!!!

    public Truck(){}

    public Truck(int id, int capacity, boolean hasFridge){
        this.id = id;
        this.capacity = capacity;
        this.hasFridge = hasFridge;
        logger.info("Truck created: " + id + ", "+ capacity + ", " + hasFridge);
    }

    public int getTruckId() {
        return id;
    }

    public void setTruckId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean hasFridge() {
        return hasFridge;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }

    @Override
    public void run() {
        // get Gate, start loading/unloading, return Gate
        logger.info("Truck " + this.id + " start running");
        LogisticBase base = LogisticBase.getInstance();
        Gate gate;
        try{
            gate = base.getGate(this.id);
            logger.info("Truck " + this.id + " get Gate №" + gate.getGateId());
        }catch (LogisticBaseException l){
            logger.warn("No free gates for Truck " + this.id);
            gate = testQueue.addTruckToQueue(this);
        }
        try {
            gate.loading(capacity);
            TimeUnit.MILLISECONDS.sleep(capacity * 100);
            logger.info("Truck " + this.id + "unloaded.");
        }catch (InterruptedException | LogisticBaseException e){
            logger.error("Truck " + this.id + " can't unload");
            e.printStackTrace();
        } finally {
            logger.info("Truck " + this.id + " return gate " + gate.getGateId()) ;
            base.returnGate(gate);
            boolean isLeft = testQueue.leaveQueue(this);
            logger.info("Truck " + this.id + " is LEFT? " + isLeft);
        }
    }

    @Override
    public int compareTo(Truck o) {
        if(hasFridge && !o.hasFridge()){
            return -1;
        }
        if (!hasFridge && o.hasFridge){
            return +1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Truck truck = (Truck) o;
        if (id != truck.id) {
            return false;
        }
        if (capacity != truck.capacity) {
            return false;
        }
        return hasFridge == truck.hasFridge;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + capacity;
        result = 31 * result + (hasFridge ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName());
        builder.append(", ");
        builder.append(id);
        builder.append(", ");
        builder.append(capacity);
        builder.append(", ");
        builder.append(hasFridge);
        return builder.toString();
    }
}
