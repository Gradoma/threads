package by.gradomski.threads.queue;

import by.gradomski.threads.entity.Truck;

import java.util.PriorityQueue;

public class TruckQueue {
    public PriorityQueue<Truck> queue = new PriorityQueue<>();

    public void addTruck(Truck truck){
        queue.add(truck);
    }
}
