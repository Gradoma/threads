package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Truck extends Thread implements Comparable<Truck>{
    private static Logger logger = LogManager.getLogger();
    private int id;
    private int capacity;
    private int loadedWeight;
    private boolean hasFridge;

    public Truck(){}

    public Truck(int id, int capacity, int loadedWeight, boolean hasFridge){
        this.id = id;
        this.capacity = capacity;
        this.loadedWeight = loadedWeight;
        this.hasFridge = hasFridge;
        logger.info("Truck created: " + id + ", "+ capacity + ", " + loadedWeight + ", " + hasFridge);
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

    public int getLoadedWeight() {
        return loadedWeight;
    }

    public void setLoadedWeight(int loadedWeight) {
        this.loadedWeight = loadedWeight;
    }

    public boolean hasFridge() {
        return hasFridge;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }

    public void run(){
        LogisticBase base = LogisticBase.getInstance();
        Gate gate = null;
        try {
            gate = base.getGate(this);
            logger.info("Truck " + this.id + " get Gate №" + gate.getGateId());
            if(loadedWeight != 0){
                gate.unloadTruck(loadedWeight);
                TimeUnit.MILLISECONDS.sleep(loadedWeight * 100);
                logger.info("Truck " + this.id + " unloaded.");
            } else {
                gate.loadTruck(capacity);
                TimeUnit.MILLISECONDS.sleep(capacity * 100);
                logger.info("Truck " + this.id + " loaded.");
            }
        } catch (LogisticBaseException e1) {
            logger.error("LogisticBaseException: " + e1.getMessage());
        } catch (InterruptedException iEx){
            logger.error("InterruptedException: " + iEx.getMessage());
        } finally {
            base.returnGate(gate);
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
        if (loadedWeight != truck.loadedWeight){
            return false;
        }
        return hasFridge == truck.hasFridge;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = id;
        result = prime * result + capacity;
        result = prime * result + loadedWeight;
        result = prime * result + (hasFridge ? 1 : 0);
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
        builder.append(loadedWeight);
        builder.append(", ");
        builder.append(hasFridge);
        return builder.toString();
    }
}
