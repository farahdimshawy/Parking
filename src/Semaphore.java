package src;

public class Semaphore {
    private int permits;

    // Constructor
    public Semaphore(int maxSpots) {
        this.permits = maxSpots;
    }
    public Semaphore() {
        new Semaphore(permits);
    }
    public synchronized boolean tryAcquire() throws InterruptedException {
        if(permits == 0) {return false;}
        permits--;
        return true;
    }
    public synchronized void release() {
        permits++;
    }
}
