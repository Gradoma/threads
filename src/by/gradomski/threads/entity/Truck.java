package by.gradomski.threads.entity;

public class Truck {
    private int id;
    private int capacity;
    private boolean hasFriedge;

    public Truck(){}

    public Truck(int id, int capacity, boolean hasFriedge){
        this.id = id;
        this.capacity = capacity;
        this.hasFriedge = hasFriedge;
    }
}
