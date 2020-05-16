package by.gradomski.threads.entity;

import by.gradomski.threads.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gate {
    private static Logger logger = LogManager.getLogger();
    private static int counter = 1;
    private int gateId;

    public Gate(){
        this.gateId = counter;
        counter++;
        logger.info("Gate #" + gateId + " created");
    }

    public int getGateId() {
        return gateId;
    }

    public void loading(int cargoWeight) throws LogisticBaseException{
        logger.info("Gate " + gateId + " : Loading method start: " + cargoWeight + " cargo");
        LogisticBase base = LogisticBase.getInstance();
        if (base.getFreeSpace() >= cargoWeight){
            logger.info("Gate " + gateId + " : has space");
            base.addCargo(-cargoWeight);
            logger.info("Gate " + gateId + " : space after loading: " + base.baseCapacity);
        } else {
            logger.error("Gate " + gateId + " : NO SPACE");
            throw new LogisticBaseException("no space on base");
        }
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
