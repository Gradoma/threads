package by.gradomski.threads.factory;

import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.queue.TruckQueue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TruckFactory extends Thread{
    private TruckQueue truckQueue;
    private int count;

    public TruckFactory(int count){
        this.count = count;
        truckQueue = new TruckQueue();
    }

    public TruckQueue getTruckQueue(){
        return truckQueue;
    }

    @Override
    public void run() {
        for(int i = 0; i < count; i++){
            Thread.currentThread().setName("Truck Generator");
            Truck truck = new Truck(i + 1, getRandomCapacity(), getRandomBoolean());
            System.out.println("Created: " + truck.getId() + ", " + truck.getCapacity() + ", " + truck.hasFriedge());
            truckQueue.addTruck(truck);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private int getRandomCapacity(){
        Random random = new Random();
        return random.nextInt(16) + 4;
    }

    private boolean getRandomBoolean(){
        double randomDouble = Math.random();
        return randomDouble <= 0.3;
    }
}
