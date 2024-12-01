package src;

import java.util.*;


public class Lot {
    static int totalCarsServed = 0;
    static int maxSpots = 4;
    static int occupiedSpots = 0;
    private final Queue<Car> carsWaiting = new PriorityQueue<>((car1, car2) -> {
        if (car1.getArrivalTime() != car2.getArrivalTime()) {
            return Integer.compare(car1.getArrivalTime(), car2.getArrivalTime());
        } else {
            return Integer.compare(car1.getCarId(), car2.getCarId());
        }
    });
    static List<Car> carsParked = new ArrayList<>();
    static List<Integer> carsPERGate = new ArrayList<>();
    Semaphore parkingSemaphore = new Semaphore(maxSpots);

    public Lot() {
    }

    static {
        // Initialize a counter for each gate (3 gates as per the example)
        for (int i = 0; i < 3; i++) {
            carsPERGate.add(0);
        }
    }

    public static synchronized void carArrived(Car car) {
        System.out.printf("Car %d from Gate %d arrived at time %d%n", car.getCarId(), car.getGateNumber(), car.getArrivalTime());
    }

    public synchronized boolean park(Car car) throws InterruptedException {

        if (!carsWaiting.isEmpty()) {
            Iterator<Car> iterator = carsWaiting.iterator();
            Car waitingCar = iterator.next(); // Get the first (oldest) car in the set
            if (parkingSemaphore.tryAcquire()) {
                iterator.remove(); // Remove the car from the waiting list
                carsParked.add(waitingCar);
                totalCarsServed++;
                occupiedSpots++;
                carsPERGate.set(waitingCar.getGateNumber() - 1,
                        carsPERGate.get(waitingCar.getGateNumber() - 1) + 1);

                System.out.printf("Car %d from Gate %d parked after waiting %d units of time. (Parking Status: %d spots occupied)%n",
                        waitingCar.getCarId(), waitingCar.getGateNumber(), waitingCar.getWaitingTime(), occupiedSpots);

                return true;
            } else {
                if (!carsWaiting.contains(car)) { // Check if the car is already in the waiting list
                    carsWaiting.add(car); // Add only if not already waiting
                    System.out.printf("Car %d from Gate %d waiting for a spot.%n", car.getCarId(), car.getGateNumber());

                    while (occupiedSpots == 4)
                        wait();
                }
                return false;
            }
        }
        //waiting list is empty
        else {
            if (parkingSemaphore.tryAcquire()) {
                carsParked.add(car);
                totalCarsServed++;
                occupiedSpots++;
                carsPERGate.set(car.getGateNumber() - 1,
                        carsPERGate.get(car.getGateNumber() - 1) + 1);

                System.out.printf("Car %d from Gate %d parked. (Parking Status: %d spots occupied)%n",
                        car.getCarId(), car.getGateNumber(), occupiedSpots);

                return true;
            } else {
                carsWaiting.add(car); // Add only if not already waiting
                System.out.printf("Car %d from Gate %d waiting for a spot.%n", car.getCarId(), car.getGateNumber());

                while (occupiedSpots == 4) wait();

                return false;
            }
        }

    }

    public synchronized void carDeparted(Car car) {
        if (carsParked.contains(car)) { // Check if the car is actually parked
            parkingSemaphore.release(); // Release a parking spot
            notifyAll();
            carsParked.remove(car);    // Remove the car from parked cars
            occupiedSpots--;
            System.out.printf("Car %d from Gate %d left after %d units of time. (Parking Status: %d spots occupied)%n",
                    car.getCarId(), car.getGateNumber(), car.getParkingDuration(), occupiedSpots);
        } else {
            System.err.printf("Car %d from Gate %d tried to leave but was not found in parked cars.%n", car.getCarId(), car.getGateNumber());
        }
    }

    public static void printReport() {
        System.out.println("\nTotal Cars Served: " + totalCarsServed);
        System.out.println("Current Cars in Parking: " + occupiedSpots);
        for (int i = 0; i < carsPERGate.size(); i++) {
            System.out.printf("Gate %d served %d cars.%n", i + 1, carsPERGate.get(i));
        }
    }
}
