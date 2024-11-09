package src;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        List<Car> Cars = Parser.parseInput("src/input.txt");
        ArrayList<Thread> carThreads = new ArrayList<>();


        // Loop through the list and print each element
        for (Car c : Cars) {
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

        ParkingLot.printReport();
    }
}

