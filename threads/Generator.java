// threads/Generator.java
package threads;

import functions.basic.Log;
import java.util.Random;

public class Generator extends Thread {
    private final Task task;
    private final SimpleSemaphore semaphore;

    public Generator(Task task, SimpleSemaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < task.tasksCount; i++) {
            try {
                semaphore.beginWrite();
                
                task.function = new Log(1 + random.nextDouble() * 9);
                task.leftX = random.nextDouble() * 100;
                task.rightX = 100 + random.nextDouble() * 100;
                task.step = random.nextDouble();
                
                System.out.println("Source " + task.leftX + " " + task.rightX + " " + task.step);
                
                semaphore.endWrite();
                
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}