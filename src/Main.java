package src;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        List<Car> Cars = Parser.parseInput("src/input.txt");
        ArrayList<Car> carsQueue = new ArrayList<>(Cars);
        ArrayList<Thread> carThreads = new ArrayList<>();

        carsQueue.sort(Car::compareTo);

        // Loop through the list and print each element
        for (Car c : carsQueue) {
            Car car = new Car(c);
            carThreads.add(car);
            car.start();
        }

        for (Thread car : carThreads) {
            try {
                car.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Lot.printReport();
    }
}

