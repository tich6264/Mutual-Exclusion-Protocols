import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.sleep;

/**
 * Created by Tiffany on 2/9/2017.
 */
/*
works for n threads, s.t. n>2
Direct generalization of Peterson lock to multiple threads
Filter Lock:
    -creates n-1  "waiting rooms" called levels that thread
     must traverse before aquiring the lock
    -last level is the critical section
    -Properties of levels:
        1) At least one thread trying to enter level "l" succeeds
        2)If more than one thread is trying to enter level "l"
            then at least one is blocked (i.e. continues to wait
            at that level)
    -
 */

public class Filter {
    //Java doesn't guarantee linearizability/ sequential consistency
    private AtomicInteger[] level;
    private AtomicInteger[] victim;

    private int NUM_THREADS;
    //1) thread A is at level 0
    //
    public Filter(int n){
        NUM_THREADS = n;
        level = new AtomicInteger[n];
        victim = new AtomicInteger[n];
        for(int i = 0; i < n; i++)
        {   //initially thread is at level 0
            level[i] = new AtomicInteger();
            victim[i] = new AtomicInteger();
        }

    }
    public void lock() {
        int me = ThreadID.get();
        for (int i = 1; i < NUM_THREADS; i++) {
            //indicates the highest lvl the thread is trying to enter - must pass through n-1 lvls
            level[me].set(i);
            //used to filter out one thread, excluding it from the next level
            victim[i].set(me);
            //spin while conflicts exist
            //A is a lvl j when it completes the loop
            for (int j = 0; j < NUM_THREADS; j++) { //n -1?
                //while there exits another thread other than myself
                //  whose level is greater than or equal to my level, and I am the victim
                //  then wait
                while ( j != me && level[j].get() >= i && victim[i].get() == me) {
                    try {
                        sleep(1);
                    }
                    catch(InterruptedException e) {
                        System.out.println("Interrupted exception");
                    }
                }
            }
        }
    }
    public void unlock(){
        int me = ThreadID.get();
        level[me].set(0);
    }
}

