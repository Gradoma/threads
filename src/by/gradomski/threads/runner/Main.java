package by.gradomski.threads.runner;

import by.gradomski.threads.entity.Gate;
import by.gradomski.threads.entity.LogisticBase;
import by.gradomski.threads.entity.Truck;
import by.gradomski.threads.exception.FileReaderException;
import by.gradomski.threads.factory.TruckFactory;
import by.gradomski.threads.parser.TruckParser;
import by.gradomski.threads.reader.impl.FileReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gate gate;
        LogisticBase base = LogisticBase.getInstance();
        for(int i=0; i < LogisticBase.BASE_SIZE; i++){
            gate = new Gate();
            base.addGateToList(gate);
        }

        FileReader reader = new FileReader();
        String pathString = "resource/file/trucks.txt";
        String fileString = null;
        try{
            fileString = reader.read(pathString);
        } catch (FileReaderException e){
            e.printStackTrace();
        }

        TruckParser parser = new TruckParser();
        List<int[]> parameters = parser.parse(fileString);
        TruckFactory factory = new TruckFactory();
        Truck truck ;
        for(int[] i : parameters){
            int truckNumber = i[0];
            int capacity = i[1];
            int loadedWeight = i[2];
            int hasFridge = i[3];
            truck = factory.createTruck(truckNumber, capacity, loadedWeight, hasFridge);
            truck.start();
        }
    }
}
