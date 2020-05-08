package by.gradomski.threads.entity;

public class Truck implements Comparable<Truck>{
    private int id;
    private int capacity;
    private boolean hasFriedge;

    public Truck(){}

    public Truck(int id, int capacity, boolean hasFriedge){
        this.id = id;
        this.capacity = capacity;
        this.hasFriedge = hasFriedge;
    }

    public int getTruckId() {
        return id;
    }

    public void setTruckId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean hasFriedge() {
        return hasFriedge;
    }

    public void setHasFriedge(boolean hasFriedge) {
        this.hasFriedge = hasFriedge;
    }

    @Override
    public int compareTo(Truck o) {
        if(hasFriedge && !o.hasFriedge()){
            return -1;
        }
        if (!hasFriedge && o.hasFriedge){
            return +1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Truck truck = (Truck) o;
        if (id != truck.id) {
            return false;
        }
        if (capacity != truck.capacity) {
            return false;
        }
        return hasFriedge == truck.hasFriedge;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + capacity;
        result = 31 * result + (hasFriedge ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName());
        builder.append(", ");
        builder.append(id);
        builder.append(", ");
        builder.append(capacity);
        builder.append(", ");
        builder.append(hasFriedge);
        return builder.toString();
    }
}
