package by.gradomski.threads.entity;

public class Truck implements Comparable<Truck>{
    private int id;
    private int capacity;
    private boolean hasFridge;

    public Truck(){}

    public Truck(int id, int capacity, boolean hasFridge){
        this.id = id;
        this.capacity = capacity;
        this.hasFridge = hasFridge;
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

    public boolean hasFridge() {
        return hasFridge;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }

    @Override
    public int compareTo(Truck o) {
        if(hasFridge && !o.hasFridge()){
            return -1;
        }
        if (!hasFridge && o.hasFridge){
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
        return hasFridge == truck.hasFridge;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + capacity;
        result = 31 * result + (hasFridge ? 1 : 0);
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
        builder.append(hasFridge);
        return builder.toString();
    }
}
