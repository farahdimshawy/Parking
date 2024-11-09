import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static List<Car> parseInput(String filename) {
       List<Car> gateCarsMap = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                int gateNumber = Integer.parseInt(parts[0].split(" ")[1]);
                int carId = Integer.parseInt(parts[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkingDuration = Integer.parseInt(parts[3].split(" ")[1]);

                Car car = new Car(gateNumber, carId, arrivalTime, parkingDuration);
                gateCarsMap.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gateCarsMap;
    }
}
