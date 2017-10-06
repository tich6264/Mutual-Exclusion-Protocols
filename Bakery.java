import java.awt.*; //Label

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.sleep;

/**
 * Created by Tiffany on 2/9/2017.
 */

/*Bakery lock algorithm:
maintains first come first served property - models number dispensing machines found in bakeries
 */
public class Bakery{// implements Lock {
    AtomicBoolean[] flag;
    AtomicInteger[] label;

    public Bakery(int n) {
        //Does thread want to enter critical section or not
        flag = new AtomicBoolean[n];
        //integer indicating the thread's relative order when entering the bakery
        label = new AtomicInteger[n];

        //set all flags and labels to 0 (not active)
        for (int i = 0; i < n; i++) {
            flag[i] = new AtomicBoolean();
            label[i] = new AtomicInteger();
        }
    }

    public void lock() {

        int i = ThreadID.get();
        flag[i].set(true); //raising flag, I am interested
        //find the max label that already exists in the label array (or queue)
        int max = find_max();
        //(assign your thread to a label that is 1 greater than anyone else in the array)
        label[i].set(max + 1); //assign label
        //boolean contain_k = IntStream.of(label).anyMatch(x -> x == max );

        for (int j = 0; j < label.length; j++) {
            //while there is someone else that has their flag up and they are before you
            //     or the array contains a thread with the same label as you, judge by id
            while (j != i && flag[j].get() && (label[j].get() < label[i].get() || label[j].get() == label[i].get() && j < i)) {
                try {
                    sleep(1);
                }
                catch(InterruptedException e) {
                    System.out.println("Interrupted exception");
                }

                /*nothing*/
                //wait until all threads with smaller labels or with the same number,
                //but with a smaller thread ID, finishes their work
            }
        }
    }

    public int find_max() {
        int max = 0;
        for (int j = 0; j < label.length; j++) {
            if (label[j].get() >= max)
                max = label[j].get();
        }
        return max;
    }

    public void unlock() {
        flag[ ThreadID.get()].set(false); //I am not interested anymore
    }

}


