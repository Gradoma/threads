package by.gradomski.threads.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gate {
    private static Logger logger = LogManager.getLogger();
    private static int counter = 1;
    private int gateId;
    private final int gateCapacity = 100;
    private int currentLoaded = 30;
    private final double loadFactor = 0.75;

    public Gate(){
        this.gateId = counter;
        counter++;
        logger.info("Gate #" + gateId + " created");
    }

    public int getGateId() {
        return gateId;
    }

    public void unloadTruck(int cargoWeight){
        logger.info("Gate " + gateId + " : Unloading method start: " + cargoWeight + " cargo");
        if (gateCapacity * loadFactor < currentLoaded){
            logger.debug("LoadFactor exceeded, storage will be erased");
            currentLoaded = 0;
        }
        currentLoaded += cargoWeight;
    }

    public int loadTruck(int truckCapacity){
        logger.info("Gate " + gateId + " : Loading method start: " + truckCapacity + " cargo");
        if (currentLoaded == 0){
            logger.debug("No cargo in storage, generate cargo");
            currentLoaded = 50;
        }
        if(currentLoaded < truckCapacity){
            logger.info("Not enough cargo to load truck full");
            int cargoToLoad = currentLoaded;
            currentLoaded = 0;
            return cargoToLoad;
        }
        currentLoaded -= truckCapacity;
        return truckCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Gate gate = (Gate) o;
        if (gateId != gate.gateId){
            return false;
        }
        return currentLoaded == gate.currentLoaded;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = gateId;
        result = prime * result + currentLoaded;
        return result;
    }
}
