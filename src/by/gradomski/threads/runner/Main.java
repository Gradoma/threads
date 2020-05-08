package by.gradomski.threads.runner;

import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.factory.TruckFactory;
import by.gradomski.threads.queue.TruckQueue;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TruckFactory generator = new TruckFactory(10);
        generator.start();
//        try{
//            factory.join();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
        TruckQueue truckQueue = generator.getTruckQueue();
        for(int i = 0; i< 10; i++) {
            Truck t = truckQueue.getTruck();
            if(t == null){
                continue;
            }
            System.out.println("truck get: " + t.toString());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        while (!factory.getTruckQueue().queue.isEmpty()){
//            System.out.println("from queue:" + factory.getTruckQueue().queue.remove());
//        }
    }
}
