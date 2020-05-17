package by.gradomski.threads.factory;

import by.gradomski.threads.entity.Truck;

public class TruckFactory {

    public Truck createTruck(int id, int capacity, int loadedWeight, int hasFriedge){
        boolean friedge = false;
        if (hasFriedge == 1){
            friedge = true;
        }
        return new Truck(id, capacity, loadedWeight, friedge);
    }
}
