package src;


public class Car extends Thread implements Comparable {
    private final int gateNumber;
    private final int carId;
    private final int arrivalTime;
    private final int parkingDuration;
    private static int currentTime = 0;
    private static final Lot lot = new Lot();

    public static synchronized void incrementTime() {
        currentTime++;
    }

    public static synchronized void setTime(int time) {
        currentTime = time;
    }

    public int getWaitingTime() {
        if (currentTime == arrivalTime) {
            return this.getCurrentTime() - this.getArrivalTime() + 1;
        }
        return this.getCurrentTime() - this.getArrivalTime();
    }

    public int getCarId() {
        return carId;
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getParkingDuration() {
        return parkingDuration;
    }

    public int getCurrentTime() {
        return currentTime;
    }

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
    public int compareTo(Object o) {
        Car car = (Car) o;
        return Integer.compare(this.arrivalTime, car.arrivalTime);
    }

    @Override
    public void run() {
        try {
            if (gateNumber < 0 || gateNumber > 3) {
                System.out.println("invalid gate number");
                return;
            }
            if (currentTime < arrivalTime) {
                Thread.sleep((arrivalTime - currentTime) * 1000L);
                currentTime = arrivalTime;
            }
            Lot.carArrived(this);

            boolean parked = false;
            while (!parked) {
                parked = lot.park(this);

            }
            // Car is now parked; simulate parking duration
            Thread.sleep(parkingDuration * 1000L);

            //setTime(currentTime + parkingDuration);
            lot.carDeparted(this);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

