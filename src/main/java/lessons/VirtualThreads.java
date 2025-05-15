package lessons;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreads {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Virtual thread " + Thread.currentThread().getName() + " is running");
            }
        };

        // Creiamo un thread virtuale e lo avviamo
        Thread v1 = Thread.ofVirtual().start(runnable);

        // Creiamo un thread virtuale ma non lo avviamo
        Thread v2 = Thread.ofVirtual().unstarted(runnable);

        // Avviamo il thread virtuale
        v2.start();

        // Eseguire join() per attendere il completamento del thread virtuale
        try {
            v1.join();
            v2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        VThreads.createThreads(100_000);
    }

    public static class VThreads {
        static List<Thread> vThreads = new ArrayList<>();

        public static void createThreads(int numThreads) {
            for (int i = 0; i < numThreads; i++) {
                int vThreadIndex = i;
                Thread thread = Thread.ofVirtual().start(() -> {
                    int result = 1;
                    for (int j = 0; j < 10; j++) {
                        result *= ( j + 1 );
                    }
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished with result: [" + vThreadIndex + "]: " +  result);
                });
                vThreads.add(thread);
            }

            for (int i=0; i < vThreads.size(); i++) {
                try{
                    vThreads.get(i).join();
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    e.printStackTrace();
                } finally {
                    System.out.println("Thread " + Thread.currentThread().getName() + " finished");
                }
            }
        }
    }
}
