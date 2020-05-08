package by.gradomski.threads.entity;

public class Gate {
    private static int counter = 1;
    private int gateId;

    public Gate(){
        this.gateId = counter;
        counter++;
    }

    public int getGateId() {
        return gateId;
    }

    public void using(){
        // some code
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Gate gate = (Gate) o;
        return gateId == gate.gateId;
    }

    @Override
    public int hashCode() {
        return gateId;
    }
}
