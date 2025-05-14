package lessons;

public class Threads {

    public static int SLEEPING_TIME = 5000;

    public static void main(String[] args) {

//        // Creazione logica di un thread
//        Runnable runnable = () -> {
//            String threadName = Thread.currentThread().getName();
//            System.out.println("Thread " + threadName + " is running");
//
//            try {
//                Thread.sleep(2000); // Simulate some work
//            } catch (InterruptedException e) {
//                System.err.println("Thread " + threadName + " was interrupted");
//                e.printStackTrace();
//            }
//
//            System.out.println("Thread " + threadName + " has finished");
//        };
//
//        // Creazione di thread distinti che eseguiranno la stessa logica
//        Thread thread1 = new Thread(runnable, "Thread-1");
//        thread1.start();
//        Thread thread2 = new Thread(runnable, "Thread-2");
//        thread2.start();


        // Attendere che i thread terminino
        StoppableRunnable stoppableRunnable = new StoppableRunnable();
        Thread thread3 = new Thread(stoppableRunnable, "Thread-3");
        // thread3.setDaemon(true); // Variante: Imposta il thread come daemon

        if (thread3.isDaemon()) {
            System.out.println(thread3.getName() + " è un demone");
        } else {
            System.out.println(thread3.getName() + " è un thread user");
        }

        thread3.start();

        // Interrompere il thread dopo 5 secondi
        try {
            Thread.sleep(SLEEPING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Requesting stop");
        stoppableRunnable.stop();
        System.out.println("Stop requested");

        // Attendere che il thread termini con join() (Solo thread utenti, non demoni)
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Interrompere i thread
    public static class StoppableRunnable implements Runnable {
        private boolean stopped = false;

        public synchronized void stop() {
            this.stopped = true;
        }

        public synchronized boolean isStopped() {
            return this.stopped;
        }

        private void closingThread(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " is running");
            int i = SLEEPING_TIME / 1000;
            System.out.println("waiting..." + i-- + "ms");
            while (!isStopped()) {
                closingThread(1000);
                System.out.println("waiting..." + i-- + "ms");
            }
            System.out.println("Thread has stopped");
        }
    }
}
