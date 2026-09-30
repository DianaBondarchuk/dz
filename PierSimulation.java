import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

class Passenger {
    long arrivalTime;

    public Passenger(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

public class PierSimulation {
    private double avgPassengerInterval; 
    private double avgBoatInterval;      
    private boolean isFinalStop;
    private int maxCapacity;

    public PierSimulation(double avgPassengerInterval, double avgBoatInterval, boolean isFinalStop, int maxCapacity) {
        this.avgPassengerInterval = avgPassengerInterval;
        this.avgBoatInterval = avgBoatInterval;
        this.isFinalStop = isFinalStop;
        this.maxCapacity = maxCapacity;
    }

    public void runSimulation(int totalMinutes) {
        Queue<Passenger> queue = new ArrayDeque<>();
        Random random = new Random();

        int totalPassengers = 0;
        long totalWaitTime = 0;
        int maxQueueLength = 0;

        int nextPassengerTime = (int) (random.nextDouble() * avgPassengerInterval * 2) + 1;
        int nextBoatTime = (int) (random.nextDouble() * avgBoatInterval * 2) + 1;

        for (int minute = 1; minute <= totalMinutes; minute++) {
            // Поява пасажира
            if (minute >= nextPassengerTime) {
                queue.add(new Passenger(minute));
                nextPassengerTime = minute + Math.max(1, (int) (random.nextDouble() * avgPassengerInterval * 2));
            }

            // Прихід катера
            if (minute >= nextBoatTime) {
                int freeSeats;
                if (isFinalStop) {
                    freeSeats = maxCapacity; 
                } else {
                    freeSeats = random.nextInt(maxCapacity + 1);
                }

                while (!queue.isEmpty() && freeSeats > 0) {
                    Passenger p = queue.poll();
                    totalWaitTime += (minute - p.arrivalTime);
                    totalPassengers++;
                    freeSeats--;
                }

                nextBoatTime = minute + Math.max(1, (int) (random.nextDouble() * avgBoatInterval * 2));
            }

            if (queue.size() > maxQueueLength) {
                maxQueueLength = queue.size();
            }
        }

        double avgWait = totalPassengers == 0 ? 0 : (double) totalWaitTime / totalPassengers;
        System.out.println("=== РЕЗУЛЬТАТИ СИМУЛЯЦІЇ (Завдання 1) ===");
        System.out.printf("Середній час перебування пасажира на причалі: %.2f хв.\n", avgWait);
        System.out.println("Максимальна довжина черги: " + maxQueueLength);
        System.out.println("Залишилося людей у черзі: " + queue.size());
    }

    public static void main(String[] args) {
        PierSimulation sim = new PierSimulation(2.0, 10.0, false, 30);
        sim.runSimulation(720); 
    }
}