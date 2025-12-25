import functions.*;
import functions.basic.Exp;
import functions.basic.Log;
import threads.*;

public class Main {

    public static void main(String[] args) {
        // 1. Тест интегрирования
        System.out.println("1: Exp() integral");
        testIntegration();

        // 2. Последовательное выполнение
        System.out.println("\n2: nonThread");
        nonThread();

        // 3. Простые потоки (synchronized)
        System.out.println("\n3: simpleThreads");
        simpleThreads();

        // 4. Сложные потоки (Семафор)
        System.out.println("\n4: complicatedThreads");
        complicatedThreads();
    }

    //1
    private static void testIntegration() {
        Function exp = new Exp();
        double left = 0;
        double right = 1;
        double step = 0.000001; 
        double res = Functions.integral(exp, left, right, step);
        System.out.println("Integral of exp(x) from 0 to 1: " + res);
        System.out.println("Expected (e - 1): " + (Math.E - 1));
    }

    //2
    public static void nonThread() {
        Task task = new Task(100);
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < task.tasksCount; i++) {
            task.function = new Log(1 + random.nextDouble() * 9);
            task.leftX = random.nextDouble() * 100;
            task.rightX = 100 + random.nextDouble() * 100;
            task.step = random.nextDouble();

            System.out.println("Source " + task.leftX + " " + task.rightX + " " + task.step);

            double res = Functions.integral(task.function, task.leftX, task.rightX, task.step);
            System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " " + res);
        }
    }

    //3
    public static void simpleThreads() {
        Task task = new Task(100);
        
        Thread generator = new Thread(new SimpleGenerator(task));
        Thread integrator = new Thread(new SimpleIntegrator(task));

        generator.start();
        integrator.start();
        
        // ВАЖНО: Ждем завершения потоков, чтобы вывод не смешался со следующим заданием
        try {
            generator.join();
            integrator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //4
    public static void complicatedThreads() {
        Task task = new Task(100);
        SimpleSemaphore semaphore = new SimpleSemaphore();

        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);

        generator.start();
        integrator.start();

        // Ждем немного, чтобы потоки поработали
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        generator.interrupt();
        integrator.interrupt();
        
        // Ждем завершения
        try {
            generator.join();
            integrator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}