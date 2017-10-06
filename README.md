# Mutual-Exclusion-Protocols
We will be exploring two mutual exclusion protocols that work for n threads, such that n > 2

## Filter Lock

### Description: 
A thread traverses n-1 levels or "waiting rooms" in order to aquire the lock and enter its critical section. At each level a thread is "filtered out." It is important to note that at least one thread trying to enter some level k succeeds and if more than one thread is trying to enter level k, then at least one is blocked

### Characteristics:
* Starvation-free
* Deadlock-free

## Bakery Lock

### Description: 
A boolean flag is set indicating whether a specified thread wants to enter the critical section or not.  Each thread has another integer that indicates a thread's relative order when entering the "bakery."  The Backery Lock is analogous to taking a number in a wait room such as at the DMV.  To elaborate, like each thread, you must wait for every person with a smaller ticket number to go first before you can be helped or int he case of thread, enter the critical section.  It is important to note that two concurrent threads can pick the same new integer label.  Nonetheless, it still maintains mutual exclusion by utilizing lexicographical order on its given number label as well as thread ID 

### Characteristics:
* Maintains First-Come-First Served Property
* Deadlock-free
* Starvation-free

#### Citation: The Art of Multiprocessor Programming by Maurice Herlihy and Nir Shavit
