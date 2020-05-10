package by.gradomski.threads.runner;

import by.gradomski.threads.entity.Gate;
import by.gradomski.threads.entity.LogisticBase;
import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.factory.TruckFactory;
import by.gradomski.threads.queue.TruckQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Gate gate;
        LogisticBase base = LogisticBase.getInstance();
        for(int i=0; i < 3; i++){
            gate = new Gate();
            base.addGateToList(gate);
        }


        TruckFactory factory = new TruckFactory();
        Truck truck ;
        for(int i=0; i < 10; i++){
            truck = factory.createTruck(i);
            truck.start();
        }

//        Gate g1 = new Gate();
//        System.out.println(g1.getGateId());
//        Gate g2 = new Gate();
//        System.out.println(g2.getGateId());
//

//        TruckQueue truckQueue = generator.getTruckQueue();
//        for(int i = 0; i< 10; i++) {
//            Truck t = truckQueue.getTruck();
//            if(t == null){
//                continue;
//            }
//            System.out.println("truck get: " + t.toString());
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
