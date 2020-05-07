package by.gradomski.threads.queue;

import by.gradomski.threads.entity.Truck;

import java.sql.SQLOutput;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TruckQueue {
    public PriorityQueue<Truck> queue = new PriorityQueue<>(4);
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void addTruck(Truck truck){
        try{
            System.out.println("method add");
            lock.lock();
            if(queue.size() < 4){
                System.out.println("can add");
                queue.add(truck);
                System.out.println("truck added");
                TimeUnit.MILLISECONDS.sleep(250);
            } else {
                System.out.println("no space");
                condition.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public Truck getTruck(){
        Truck truck = null;
        try{
            System.out.println("method get");
            lock.lock();
            if (queue.size() > 0){
                System.out.println("can get");
                truck = queue.remove();
                System.out.println("truck get");
                TimeUnit.MILLISECONDS.sleep(250);
            } else {
                System.out.println("no truck");
                condition.await();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            condition.signalAll();
            lock.unlock();
        }
        return truck;


//        if (queue.size() > 0){
//            try {
//                lock.lock();
//                truck = queue.remove();
//                TimeUnit.MILLISECONDS.sleep(250);
//            } catch (InterruptedException e){
//                e.printStackTrace();
//            }finally {
//                condition.signalAll();
//                lock.unlock();
//            }
//        } else {
//            condition.await();
//        }
//        return truck;
    }
}
