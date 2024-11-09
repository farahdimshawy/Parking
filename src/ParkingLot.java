package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class ParkingLot {
    private static final int MAX_CAPACITY = 4; // Set the maximum capacity of the parking lot
    private static Semaphore parkingSemaphore = new Semaphore(MAX_CAPACITY, true); // Semaphore to manage parking spots
    private static int occupiedSpots = 0;
    private static int totalCarsServed = 0;
    private static List<Integer> carsPerGate = new ArrayList<>();
    private static boolean carLeft = false;
    private static Set<Integer> carsWaiting = new HashSet<>();

    static {
        // Initialize a counter for each gate (3 gates as per the example)
        for (int i = 0; i < 3; i++) {
            carsPerGate.add(0);
        }
    }

    public static synchronized void carArrived(int gate, int carId, int arrivalTime) {
        System.out.printf("Car %d from Gate %d arrived at time %d%n", carId, gate, arrivalTime);
    }

    public static synchronized boolean tryToPark(int gate, int carId) throws InterruptedException {

        if (parkingSemaphore.tryAcquire()) {
            occupiedSpots++;
            totalCarsServed++;
            carsPerGate.set(gate - 1, carsPerGate.get(gate - 1) + 1);
            System.out.printf("Car %d from Gate %d parked. (Parking Status: %d spots occupied)%n", carId, gate, occupiedSpots);
            carsWaiting.remove(carId);
            return true;
        } else {
            if (!carsWaiting.contains(carId)) {
                carsWaiting.add(carId);  // Add the car to the waiting set
                System.out.printf("Car %d from Gate %d waiting for a spot.%n", carId, gate);
            }
            return false;
        }


    }

    public static synchronized void leaveSpot(int gate, int carId, int parkingDuration) {
        parkingSemaphore.release();
        occupiedSpots--;
        System.out.printf("Car %d from Gate %d left after %d units of time. (Parking Status: %d spots occupied)%n", carId, gate, parkingDuration, occupiedSpots);

    }

    public static void printReport() {
        System.out.println("\nTotal Cars Served: " + totalCarsServed);
        System.out.println("Current Cars in Parking: " + occupiedSpots);
        for (int i = 0; i < carsPerGate.size(); i++) {
            System.out.printf("Gate %d served %d cars.%n", i + 1, carsPerGate.get(i));
        }
    }
}
