package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gate {
    private static Logger logger = LogManager.getLogger();
    private static int counter = 1;
    private int gateId;
    private LogisticBase base = LogisticBase.getInstance();

    public Gate(){
        this.gateId = counter;
        counter++;
        logger.info("Gate #" + gateId + " created");
    }

    public int getGateId() {
        return gateId;
    }

//    public void unloadTruck(int cargoWeight) throws LogisticBaseException{
//        logger.info("Gate " + gateId + " : Unloading method start: " + cargoWeight + " cargo");
//        if (base.calculateFreeSpace() >= cargoWeight){
//            logger.info("Gate " + gateId + " : Base has space");
//            base.addCargo(cargoWeight);
//            logger.info("Gate " + gateId + " : Base space after unloadTruck: " + base.calculateFreeSpace());
//        } else {
//            logger.error("Gate " + gateId + " : NO SPACE");
//            throw new LogisticBaseException("no space on base");
//        }
//    }

    public void unloadTruck(int cargoWeight){
        logger.info("Gate " + gateId + " : Unloading method start: " + cargoWeight + " cargo");
        base.addCargo(cargoWeight);
    }

    public void loadTruck(int truckCapacity){
        logger.info("Gate " + gateId + " : Loading method start: " + truckCapacity + " cargo");
        base.getCargo(truckCapacity);
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
        return gateId == gate.gateId;
    }

    @Override
    public int hashCode() {
        return gateId;
    }
}
