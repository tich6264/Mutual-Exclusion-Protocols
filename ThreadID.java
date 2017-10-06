/**
 * Created by Tiffany on 2/9/2017.
 */

/*
Citation: "The Art of Multiprocessor Programming" pg. 459
 */
public class ThreadID {
    //holds the nxt identifier to be issued (get())
    private static volatile int nextID = 0;
    /* ThreadLocal<T> class:
     *  -supports each thread to have its own private instance of a variable
     *  -provides get(),(read); set(), update methods
     *
     */
    //inner class-manages thread's identifier
    //Integer: reference type, "< >"; int: primitive type
    private static class ThreadLocalID extends ThreadLocal<Integer>{
        //overrides ThreadLocal's init val
        protected synchronized Integer initialValue(){
            return nextID++;
        }

    }
    private static ThreadLocalID threadID = new ThreadLocalID();

    public static int get(){
        //1st time thread calls get- assigned next unused identifier
        return threadID.get();
    }
    public static void set(int index){
        threadID.set(index);
    }
}
