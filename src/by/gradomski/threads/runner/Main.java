package by.gradomski.threads.runner;

import by.gradomski.threads.entity.Gate;
import by.gradomski.threads.entity.LogisticBase;
import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.factory.TruckFactory;

public class Main {
    public static void main(String[] args) {
        Gate gate;
        LogisticBase base = LogisticBase.getInstance();
        for(int i=0; i < LogisticBase.BASE_SIZE; i++){
            gate = new Gate();
            base.addGateToList(gate);
        }

        TruckFactory factory = new TruckFactory();
        Truck truck ;
        for(int i=0; i < 10; i++){
            truck = factory.createTruck(i);
            truck.start();
        }
    }
}
