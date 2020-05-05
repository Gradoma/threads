package by.gradomski.threads.runner;

import by.gradomski.threads.generator.TruckGenerator;

public class Main {
    public static void main(String[] args) {
        TruckGenerator generator = new TruckGenerator(10);
        generator.start();
        try{
            generator.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        while (!generator.getTruckQueue().queue.isEmpty()){
            System.out.println("from queue:" + generator.getTruckQueue().queue.remove());
        }
    }
}
