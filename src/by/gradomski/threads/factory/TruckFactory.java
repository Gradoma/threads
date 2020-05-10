package by.gradomski.threads.factory;

import by.gradomski.threads.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class TruckFactory {
    private static Logger logger = LogManager.getLogger();

    public Truck createTruck(int i){            //parameter int remove! (reading from file)
        return new Truck(i + 1, getRandomCapacity(), getRandomBoolean());
    }

    private int getRandomCapacity(){        //will be removed(reading from file)
        Random random = new Random();
        return random.nextInt(16) + 4;
    }

    private boolean getRandomBoolean(){     //will be removed(reading from file)
        double randomDouble = Math.random();
        return randomDouble <= 0.5;
    }
}
