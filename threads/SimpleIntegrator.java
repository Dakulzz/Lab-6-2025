// threads/SimpleIntegrator.java
package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.tasksCount; i++) {
            synchronized (task) {
                if (task.function == null) {
                    continue; // защита от NPE если генератор еще не успел создать задачу
                }
                double result = Functions.integral(task.function, task.leftX, task.rightX, task.step);
                System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " " + result);
            }
        }
    }
}