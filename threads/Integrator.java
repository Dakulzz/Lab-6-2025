// threads/Integrator.java
package threads;

import functions.Functions;

public class Integrator extends Thread {
    private final Task task;
    private final SimpleSemaphore semaphore;

    public Integrator(Task task, SimpleSemaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.tasksCount; i++) {
            try {
                semaphore.beginRead();
                
                double result = Functions.integral(task.function, task.leftX, task.rightX, task.step);
                System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " " + result);
                
                semaphore.endRead();
                
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}