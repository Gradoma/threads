package by.gradomski.threads.generator;

import by.gradomski.threads.entity.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TruckGenerator extends Thread{
    private List<Truck> truckList = new ArrayList<>();
    private int count;

    public TruckGenerator(int count){
        this.count = count;
    }

    public List<Truck> getTruckList(){
        return new ArrayList<>(truckList);
    }

    @Override
    public void run() {
        for(int i = 0; i < count; i++){
            Thread.currentThread().setName("Truck Generator");
            Truck truck = new Truck(i + 1, getRandomCapacity(), getRandomBoolean());
            System.out.println("Created: " + truck.getId() + ", " + truck.getCapacity() + ", " + truck.hasFriedge());
            truckList.add(truck);
            try {
                Thread.sleep(1000);
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
