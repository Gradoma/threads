package by.gradomski.threads.factory;

import by.gradomski.threads.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class TruckFactory {
    private static Logger logger = LogManager.getLogger();

    public Truck createTruck(int id, int capacity, int loadedWeight, int hasFriedge){
        boolean friedge = false;
        if (hasFriedge == 1){
            friedge = true;
        }
        return new Truck(id, capacity, loadedWeight, friedge);
    }
}
