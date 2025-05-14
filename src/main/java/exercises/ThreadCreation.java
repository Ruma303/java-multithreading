package exercises;

public class ThreadCreation {

    public static class MyClass extends Thread {
        @Override
        public void run() {
            System.out.println("Thread dalla classe Thread è in esecuzione");
        }
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread dall'interfaccia Runnable è in esecuzione");
        }
    }

    // Modi di creare un thread
    public static void main(String[] args) {

        // Otteniamo il thread principale
        Thread mainThread = Thread.currentThread();
        System.out.println("Il main thread \"" + mainThread.getName() + "\" è in esecuzione");

        // Creare un thread da una classe che estende Thread
        MyClass myClass = new MyClass();
        myClass.start();
        System.out.println("Creazione thread con la classe Thread: " + myClass.getName());
        myClass.interrupt();

        // Casuale
        if (myClass.isAlive()) {
            System.out.println(myClass.getName() + " è ancora attivo");
        } else {
            System.out.println(myClass.getName() + " è stato disattivato");
        }

        // Creare un thread da una classe che implementa Runnable
        MyRunnable myRunnable = new MyRunnable();
        myRunnable.run(); // Esegui il metodo run() direttamente sincronicamente sul main thread, non crea un nuovo thread
        new Thread(myRunnable, "myRunnable").start();
        System.out.println("Creazione thread con l'interfaccia Runnable");

        // Creare un thread da una classe anonima
        Runnable anonymousRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread da una classe anonima è in esecuzione");
            }
        };
        new Thread(anonymousRunnable, "anonymousRunnable").start();
        System.out.println("Creazione thread con una classe anonima");

        // Creare un thread da una lambda
        Runnable lambdaRunnable = () -> System.out.println("Thread da una lambda è in esecuzione");
        new Thread(lambdaRunnable, "lambdaRunnable").start();
        System.out.println("Creazione thread con una lambda");
    }
}