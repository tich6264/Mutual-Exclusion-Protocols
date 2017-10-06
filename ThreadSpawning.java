
public class ThreadSpawning implements Runnable {
    private static final int NUM_THREADS = 128;

    Bakery test1 = new Bakery(NUM_THREADS) ;
    private static int counter = 0;

    //code executed by each thread
    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {

            //Mutual Exclusion:
            //Only one thread can own a Lock object at a time (Lock <- interface)
            //restricting one thread at a time to the shared resource
            try{
                test1.lock();
                counter++;
                //Release Lock object, so another thread can enter
                test1.unlock();
            }
            finally {
                //test1.unlock();
            }

        }
    }

    public static void main(String[] args){
        //create a Thread array
        Thread[] thread = new Thread[NUM_THREADS];

        long startTime = System.currentTimeMillis();

        //Create a Runnable object
        //Runnable Interface defines run()
        Runnable obj = new ThreadSpawning();

        //spawn NUM_THREADS threads
        for (int i = 0; i < thread.length; i++) {

            //create threads
            thread[i] = new Thread(obj);

            //threads start running in parallel
            thread[i].start();
        }

        //After all threads have been spawned:

        for (int i = 0; i < thread.length; i++) {
            //block main thread
            try {
                //so it waits for thread[i] to finish
                //Causes current thread to pause execution until "thread"'s thread terminates
                thread[i].join();
            }
            catch(InterruptedException e) {
                //like sleep join is dependent on the OS for timing
                System.out.println("Interrupted exception");
                //e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

        System.out.println(counter);
    }
}



