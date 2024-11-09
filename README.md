# Parking System Simulation
This project simulates a parking system using semaphores and threads. The system manages parking spots, gates, and car arrivals with thread synchronization mechanisms. Cars arrive at specified times, stay for a predetermined duration, and then exit. The challenge is to ensure proper management of parking spots and synchronization among concurrent arrivals and departures.

## Features
**Thread Synchronization:** Uses semaphores and threading to manage access to the limited number of parking spots.
**Concurrency Management:** Handles multiple cars arriving at different times at different gates.
**Simulation Realism:** Accurate timing for car arrivals and duration of parking using sleep().
**Logging and Reporting:** Tracks the number of cars currently parked and the total number of cars served.

## System Specifications
**Parking Spots:** 4 spots available in total.
**Gates:** 3 gates (Gate 1, Gate 2, Gate 3).
**Car Arrival:** Cars arrive at specified times, one car per thread per gate.
## Requirements
Java 8 or higher: Required for using Semaphore and threading for synchronization.
Input File: The car arrival schedule is provided through a .txt file.

## Prerequisites
Make sure you have Java 8 or higher installed. You can download it from the official Java website.

## How It Works

**1. Parking Spots Management:**
The system uses a semaphore to manage access to the limited number of parking spots (4 in total).
Each car arrival is handled by a separate thread, which attempts to acquire a parking spot.

**2. Concurrency Management:**
Multiple cars can try to enter the parking system at the same time.
If no spot is available, cars will wait until one becomes free.

**3. Logging and Reporting:**
The system logs each car’s arrival, parking, and departure, along with the status of parking spots.
After the simulation ends, it reports the number of cars currently parked and the total number of cars served.

## Expected Output
The system will output logs of car arrivals, parking, and departures. The final report will show the total number of cars served and the parking status at the end of the simulation.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
Inspired by real-world parking systems, this simulation uses thread synchronization mechanisms to manage limited resources effectively.

