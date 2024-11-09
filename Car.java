import java.util.concurrent.Semaphore;

public class Car extends Thread {
    private int gateNumber;
    private int carId;
    private int arrivalTime;
    private int parkingDuration;
    private static int currentTime = 0;

    public Car(int gateNumber, int carId, int arrivalTime, int parkingDuration) {
        this.gateNumber = gateNumber;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
    }

    public Car(Car car) {
        this.gateNumber = car.gateNumber;
        this.carId = car.carId;
        this.arrivalTime = car.arrivalTime;
        this.parkingDuration = car.parkingDuration;
    }

    public void print() {
        System.out.println("Gate Number: " + gateNumber + " Car Id: " + carId + " Arrival Time: " + arrivalTime + " Parking Duration: " + parkingDuration);
    }

    @Override
    public void run() {
        try {
            if (gateNumber < 0 || gateNumber > 3) {
                System.out.println("invalid gate number");
                return;
            }
            while (currentTime < arrivalTime) {
                Thread.sleep(1000); // sleep until the car arrived to the gate
                currentTime++;
            }
            ParkingLot.carArrived(gateNumber, carId, arrivalTime);

            boolean parked = false;
            while (!parked) {
                parked = ParkingLot.tryToPark(gateNumber, carId);
                if (!parked) {
                    Thread.sleep(1000); // Wait and try again if parking is full

                }
            }

            Thread.sleep(parkingDuration * 1000); // Simulate parking duration
            ParkingLot.leaveSpot(gateNumber, carId, parkingDuration);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

