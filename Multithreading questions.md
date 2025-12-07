## Q.1: What are the states in the lifecycle of a Thread?

**1. New**
The thread object is created but `start()` has not been called. No system resources (like OS-level threads) are allocated yet.
**Example:**

```java
Thread t = new Thread(() -> { /* work */ });
// still in New state
```

**2. Runnable**
After calling `start()`, the thread becomes runnable. It may actually be running on the CPU or waiting in the ready queue. The JVM treats both as “Runnable.”
**Transitions to Runnable:**

* Calling `start()`
* Coming back from `Blocked`, `Waiting`, or `Timed Waiting` when conditions are satisfied
  **Example:**

```java
t.start(); // moves to Runnable
```

**3. Blocked**
The thread is alive but cannot proceed because it is waiting to acquire a monitor lock held by another thread. This happens only with intrinsic locks (`synchronized`).
**Triggers:**

* Entering a synchronized block where another thread holds the lock
  **Example:**

```java
synchronized(obj) { /* critical section */ }
```

If one thread holds `obj`’s lock, another thread trying to enter this block becomes **Blocked**.

**4. Waiting**
The thread is waiting indefinitely for another thread to perform a specific action. It does not have a time limit.
**Triggers:**

* `Object.wait()` without timeout
* `Thread.join()` without timeout
* `LockSupport.park()`
  **Example:**

```java
synchronized(obj) {
    obj.wait(); // Waiting until notify()/notifyAll()
}
```

**5. Timed Waiting**
The thread is waiting for a specific amount of time. It becomes runnable after the timeout expires or when it is notified.
**Triggers:**

* `Thread.sleep(ms)`
* `Object.wait(ms)`
* `Thread.join(ms)`
* `LockSupport.parkNanos()` / `parkUntil()`
  **Example:**

```java
Thread.sleep(2000); // Timed Waiting for 2 seconds
```

**6. Terminated**
The thread has finished execution either by completing normally or due to an uncaught exception.
**Example:**

```java
Thread t = new Thread(() -> System.out.println("done"));
t.start();
t.join(); // after this, state = Terminated
```

**Additional Notes**

* “Runnable” in Java includes both **Ready** and **Running**, unlike some OS diagrams.
* A thread cannot return to **New** once `start()` is called.
* A thread cannot exit **Terminated**; it's a final state.


---

## Q.2: What are the different ways of implementing thread?
**1. Extending `Thread` class**
Create a subclass of `Thread` and override `run()`.
**Example:**

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("running");
    }
}

MyThread t = new MyThread();
t.start();
```

**2. Implementing `Runnable` interface**
Pass a `Runnable` implementation to a `Thread`. This is preferred because Java doesn’t support multiple inheritance.
**Example:**

```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("running");
    }
}

Thread t = new Thread(new MyTask());
t.start();
```

**3. Implementing `Callable<V>` with `FutureTask`**
`Callable` returns a value and can throw checked exceptions. It must be wrapped in `FutureTask` or submitted to an executor.
**Example:**

```java
Callable<Integer> task = () -> 42;

FutureTask<Integer> future = new FutureTask<>(task);
Thread t = new Thread(future);
t.start();

Integer result = future.get();
```

**4. Using `ExecutorService` (thread pools)**
Modern and recommended approach. The thread lifecycle is managed by the pool; you only submit tasks. Supports both `Runnable` and `Callable`.
**Example:**

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
pool.submit(() -> System.out.println("running"));
pool.shutdown();
```

**5. Using `ForkJoinPool` / `RecursiveTask` or `RecursiveAction`**
Used for parallelism and divide-and-conquer workloads.
**Example:**

```java
class SumTask extends RecursiveTask<Integer> {
    @Override
    protected Integer compute() {
        return 10 + 20;
    }
}

ForkJoinPool pool = new ForkJoinPool();
int result = pool.invoke(new SumTask());
```

**6. Using `CompletableFuture`**
High-level asynchronous programming. Automatically uses the common ForkJoinPool unless a custom executor is provided.
**Example:**

```java
CompletableFuture<Void> f =
    CompletableFuture.runAsync(() -> System.out.println("running"));
```

These are the primary ways Java enables thread creation and async execution.



## Q.3: What is the difference between Process and Thread?
**Process**

* An independent program in execution.
* Has its **own memory space**: code, heap, stack, file descriptors.
* Context switching between processes is **expensive**.
* Processes do **not** share memory by default; communication requires IPC (pipes, sockets, shared memory).
* Failure in one process usually **does not affect** others.

**Thread**

* A lightweight unit of execution **inside a process**.
* Threads **share the same memory** (heap, code) of the process.
* Each thread has its **own stack and registers**, but not its own heap.
* Context switching between threads is **faster**.
* Communication is easy because of shared memory, but this also introduces **synchronization issues** (race conditions).

**Key differences**

* **Isolation**: Processes are isolated; threads are not.
* **Address space**: Processes have separate memory; threads share memory.
* **Cost**: Process creation and switching are heavier; threads are lightweight.
* **Communication**: Processes require IPC; threads communicate through shared data structures.
* **Failure impact**: A thread crashing can bring down the entire process; processes fail independently.





## Q.4: What is difference between user Thread and daemon Thread?
**User Thread**

* Non-daemon thread; default type when you create a thread.
* JVM waits for all user threads to finish before shutting down.
* Used for core application logic.
* Example: Request handlers, workers, background jobs that must complete.

**Daemon Thread**

* Low-priority, service threads that provide support to user threads.
* JVM **does not wait** for daemon threads; if only daemon threads remain, the JVM exits immediately.
* Used for tasks that can be abandoned without harming the program.
* Example: Garbage Collector, background monitoring threads, cleanup schedulers.

**How to set daemon**

```java
Thread t = new Thread(() -> { /* work */ });
t.setDaemon(true); // must be done before start()
t.start();
```



## Q.5: How does thread communicate with each other?
**Thread communication mechanisms:**

---

#### **1. Shared Memory (Most Common)**

Threads share the same heap, so they communicate by reading/writing shared variables.

**Issue:** Causes race conditions → requires synchronization.

**Example:**

```java
int[] counter = {0};

Thread t1 = new Thread(() -> counter[0]++);
Thread t2 = new Thread(() -> counter[0]++);
```

---

#### **2. `synchronized` + `wait()` / `notify()` / `notifyAll()`**

Classic monitor-based communication.
One thread waits; another thread signals.

**Usage pattern:**

* Thread A calls `wait()` → releases the lock and waits.
* Thread B calls `notify()` or `notifyAll()` → wakes A.

**Example:**

```java
synchronized (obj) {
    while (!condition) {
        obj.wait();
    }
    // continue
}

synchronized (obj) {
    condition = true;
    obj.notify();
}
```

---

#### **3. Using `volatile` variables**

Ensures visibility across threads.
A write by one thread becomes immediately visible to others.

**Example:**

```java
volatile boolean flag = false;
```

Used for:

* Stop signals
* Status flags
* Lightweight coordination
  (Not for atomic compound operations.)

---

#### **4. Using Atomic Classes (`AtomicInteger`, `AtomicBoolean`, etc.)**

Provides lock-free, thread-safe operations.

**Example:**

```java
AtomicInteger count = new AtomicInteger();
count.incrementAndGet();
```

Used for:

* Counters
* Status updates
* CAS (compare-and-swap) coordination

---

#### **5. High-Level Concurrency Utilities (java.util.concurrent)**

###### **a. `BlockingQueue`**

Most practical way for thread-to-thread communication.
One thread puts messages, another takes them.

**Example:**

```java
BlockingQueue<String> q = new ArrayBlockingQueue<>(10);

q.put("msg");     // producer
String m = q.take(); // consumer
```

###### **b. `CountDownLatch`**

One or more threads wait until a set of operations completes.

###### **c. `CyclicBarrier`**

Multiple threads wait until all reach the barrier.

###### **d. `Semaphore`**

Controls access to shared resources.

###### **e. `Exchanger`**

Two threads exchange objects.

---

#### **6. Futures and CompletableFutures**

Asynchronous result passing.

**Example:**

```java
CompletableFuture<Integer> f =
    CompletableFuture.supplyAsync(() -> 10);

f.thenAccept(result -> System.out.println(result));
```

---

#### **Summary**

Threads communicate through:

1. Shared memory
2. `wait/notify`
3. `volatile`
4. Atomic classes
5. Concurrency utilities (`BlockingQueue`, locks, latches, barriers)
6. Futures / CompletableFutures

These cover all major inter-thread communication patterns in Java.


## Q.6: What do you understand about Thread Priority?
**Thread Priority**
A numeric hint (1–10) that tells the JVM scheduler how important a thread is *relative* to other threads. It does **not** guarantee execution order; it only influences scheduling on some JVMs and operating systems.

**Range:**

* `Thread.MIN_PRIORITY` → **1**
* `Thread.NORM_PRIORITY` → **5** (default)
* `Thread.MAX_PRIORITY` → **10**

**Setting priority:**

```java
Thread t = new Thread(() -> {});
t.setPriority(Thread.MAX_PRIORITY);
t.start();
```

**Notes:**

* Priority affects **time slices**; higher priority *may* get more CPU time.
* JVM behavior is platform-dependent. Some OS schedulers largely ignore Java thread priority.
* Priority should not be used to control application logic.
* Priority inversion can occur: a high-priority thread waits for a low-priority thread holding a lock.

**Practical usage:**

* Almost never needed in real applications.
* Only used for fine-grained tuning for compute-heavy systems—but even then, thread pools and executors are preferred.


## Q.7: What is Thread Scheduler and Time Slicing?
**Thread Scheduler**
The JVM component (backed by the OS scheduler) that decides **which runnable thread gets CPU time**. Java threads map to OS-level threads, so actual scheduling is done by the operating system.

Responsibilities:

* Pick one runnable thread at a time.
* Preempt threads based on the OS scheduling algorithm.
* Consider priority *as a hint*, not a rule.

Key points:

* JVM cannot force exact order of execution.
* Priority may influence scheduling, but behavior is platform-dependent.
* Scheduler switches threads via **context switching**.

---

**Time Slicing**
The mechanism where CPU time is divided into **small slices** and distributed among runnable threads.

How it works:

* A thread runs for a slice (e.g., a few ms).
* When its slice expires, the scheduler may preempt it.
* Another runnable thread gets the next slice.

Characteristics:

* Prevents a single thread from monopolizing CPU.
* Makes multitasking appear parallel on single-core CPU.
* Slice duration is OS-dependent, not controlled by Java.

---

**Summary**

* **Thread Scheduler:** Chooses which thread runs next.
* **Time Slicing:** Gives each runnable thread a small time window to run before switching.


## Q.8: What is context-switching in multi-threading?
**Context Switching**
The process where the CPU stops executing one thread and switches to another by saving and restoring execution state.

**What gets saved (thread context):**

* Program counter (next instruction)
* CPU registers
* Thread stack pointer
* Metadata needed to resume execution later

**Where it is stored:**
Saved in the thread’s Thread Control Block / OS data structure.

**When context switching happens:**

* A thread’s time slice expires
* A higher-priority thread becomes runnable
* A thread blocks (I/O, lock acquisition, sleep, wait)
* The OS scheduler preempts the running thread

**Cost:**

* Non-trivial overhead
* Involves saving/restoring registers and switching memory mappings
* Excessive switching leads to **context-switch overhead**, reducing CPU efficiency

**In Java:**

* Java does not perform context switching itself.
* Under the hood, Java threads map to OS threads, so the OS performs all context switching.


## Q.9: What is Deadlock? How to analyze and avoid deadlock situation?
**Deadlock**
A situation where two or more threads are **permanently blocked**, each waiting for a resource (lock) held by another, forming a circular wait. None of them can proceed.

**Classic example (two locks):**

```java
Object A = new Object();
Object B = new Object();

Thread t1 = new Thread(() -> {
    synchronized (A) {
        synchronized (B) { }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (B) {
        synchronized (A) { }
    }
});
```

`t1` waits for `B`, `t2` waits for `A` → deadlock.

---

**Characteristics (Coffman Conditions)**

Deadlock occurs when all four are present:

1. **Mutual Exclusion** – Only one thread can hold a resource.
2. **Hold and Wait** – A thread holds one lock and waits for another.
3. **No Preemption** – Locks cannot be forcibly taken.
4. **Circular Wait** – Cycle of threads waiting for each other.

---

### **How to Analyze Deadlock**

#### **1. Thread Dump (jstack or JVM tools)**

Look for:

* `"BLOCKED"` threads
* `"waiting to lock <xyz>"`
* Cyclic dependencies

Example snippet from a thread dump:

```
Thread-1: waiting to lock <0x1> held by Thread-2
Thread-2: waiting to lock <0x2> held by Thread-1
```

This indicates a deadlock cycle.

#### **2. Logging and Instrumentation**

Add logs before acquiring locks to trace lock order:

```java
log("Thread-1 trying to acquire Lock A");
```

#### **3. Profilers / Monitoring tools**

Use:

* VisualVM
* JConsole
* Java Mission Control
  These can detect deadlocks automatically.

---

### **How to Avoid Deadlock**

#### **1. Impose a Global Lock Ordering (most effective)**

Always acquire locks in the same order across all threads.

Example:

```java
// Always lock A then B
synchronized(A) {
    synchronized(B) {
        // safe
    }
}
```

#### **2. Use Try-Lock with Timeout (`ReentrantLock.tryLock`)**

Avoid waiting forever.

```java
if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
    try {
        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
            try { /* work */ }
            finally { lock2.unlock(); }
        }
    } finally {
        lock1.unlock();
    }
}
```

If not acquired, back off.

#### **3. Minimize Locking Scope**

Keep synchronized sections as small as possible to reduce contention.

#### **4. Prefer Higher-Level Concurrency Utilities**

Use:

* `ConcurrentHashMap`
* `BlockingQueue`
* `Atomic` variables
  These reduce or eliminate manual locking.

#### **5. Avoid Nested Locks When Possible**

Single lock per resource or use lock striping.

#### **6. Immutable Data Structures**

No locking needed for read-only immutable objects.

#### **7. Use Deadlock Detection Algorithms (rare in app code)**

Some systems implement periodic deadlock detection by building a wait-for graph.

---

### **Summary**

* **Deadlock** = threads wait forever due to circular lock dependency.
* **Analyze** using thread dumps, profiling tools, and logging.
* **Avoid** using lock ordering, try-lock timeouts, minimal locking, and high-level concurrency tools.

This covers both conceptual understanding and practical prevention.


## Q.10: What is Thread Pool? How can we create Thread Pool in Java?
**Thread Pool**
A thread pool is a managed group of reusable worker threads. Instead of creating a new thread for every task, tasks are submitted to the pool and executed by available worker threads. This reduces thread-creation overhead and provides controlled concurrency.

**Advantages**

* Reuses existing threads → lower creation/destruction cost
* Limits maximum concurrency → prevents resource exhaustion
* Provides task queuing and scheduling
* Offers clean shutdown and monitoring
* Forms the foundation of most concurrent applications (servers, schedulers, pipelines)

---

### **How to Create Thread Pools in Java**

#### **1. Using `Executors` Utility (Simple and Common)**

###### **a. Fixed Thread Pool**

Fixed number of threads; extra tasks wait in a queue.

```java
ExecutorService pool = Executors.newFixedThreadPool(4);

pool.submit(() -> {
    System.out.println("task running");
});

pool.shutdown();
```

###### **b. Cached Thread Pool**

Creates threads on demand; reuses idle threads; unbounded.

```java
ExecutorService pool = Executors.newCachedThreadPool();
```

###### **c. Single Thread Executor**

Guarantees sequential execution.

```java
ExecutorService pool = Executors.newSingleThreadExecutor();
```

###### **d. Scheduled Thread Pool**

For delayed and periodic tasks.

```java
ScheduledExecutorService scheduler =
    Executors.newScheduledThreadPool(2);

scheduler.schedule(() -> {
    System.out.println("delayed");
}, 1, TimeUnit.SECONDS);
```

---

### **2. Using `ThreadPoolExecutor` Directly (Most Powerful, Recommended for Production)**

Allows full control over:

* core pool size
* max pool size
* keep-alive time
* queue type
* rejection policies
* thread factory

**Example:**

```java
ThreadPoolExecutor pool =
    new ThreadPoolExecutor(
        4,                      // core threads
        8,                      // max threads
        60, TimeUnit.SECONDS,   // idle thread keep-alive
        new ArrayBlockingQueue<>(100), // task queue
        new ThreadPoolExecutor.CallerRunsPolicy() // rejection handler
    );

pool.execute(() -> System.out.println("task"));
```

Key components:

* **core threads** stay alive
* **extra threads** created only when needed
* **work queue** holds pending tasks
* **rejection handler** executes when queue is full and max threads reached

---

### **3. Using `ForkJoinPool` (Parallelism / Divide-and-Conquer)**

```java
ForkJoinPool pool = new ForkJoinPool();

pool.submit(() -> {
    // parallel tasks
});
```

Used for tasks that split into subtasks (`RecursiveAction`, `RecursiveTask`).

---

### **4. Using `CompletableFuture` (High-Level Async, Uses Common ForkJoinPool)**

```java
CompletableFuture.runAsync(() -> {
    System.out.println("async running");
});
```

You can attach your own executor:

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
CompletableFuture.runAsync(() -> {}, pool);
```

---

### **Summary**

**Thread Pool** = Reusable worker threads that execute tasks efficiently and safely.

**Creation Methods:**

1. **Executors** → quick setup
2. **ThreadPoolExecutor** → full control
3. **ForkJoinPool** → parallelism model
4. **CompletableFuture** → async pipelines (default pool: ForkJoin)

These cover all practical thread-pool implementations in Java.


## Q.11: Why wait(), notify() and notifyAll() must be called from inside of the synchronized block or method.?
**Reason 1: They operate on the monitor lock of the object**
`wait()`, `notify()`, and `notifyAll()` are monitor methods. A monitor = intrinsic lock of an object.
To call these methods safely, the thread **must own the object’s monitor**.
Owning the monitor happens **only when inside a synchronized block/method** on that same object.

If a thread calls `obj.wait()` without holding `obj`’s lock → `IllegalMonitorStateException`.

---

**Reason 2: Atomic release + wait must be guaranteed**
`wait()` performs two actions **atomically**:

1. Releases the monitor lock.
2. Puts the thread into the wait set of that monitor.

This atomicity is only possible when the thread already holds the lock.
Without synchronization, the JVM cannot guarantee that this lock-release + state-change happens safely.

---

**Reason 3: Ensures correct coordination and prevents race conditions**
`notify()` and `notifyAll()` wake threads waiting on the same monitor.
If you don’t synchronize:

* The notifier might send a notification before another thread calls `wait()`, causing a missed signal.
* Two threads can manipulate shared state unsafely → inconsistent conditions and spurious wakeups.

Synchronization guarantees:

* The shared condition (e.g., `while (!condition) wait();`) is checked under a lock.
* The notifying thread also updates the condition under the same lock.
  This ensures correct happens-before semantics.

---

**Reason 4: Wait sets are tied to object monitors**
Every object has:

* **One monitor lock**
* **One wait set (queue of waiting threads)**

To interact with the wait set, the thread must be working through that object’s monitor logic — which requires holding the lock.

---

### **Summary**

`wait()`, `notify()`, `notifyAll()` must be called inside synchronized code because:

1. They require the calling thread to **own the object’s monitor**.
2. They need atomic **release-lock-and-wait** behavior.
3. They ensure correct coordination of shared state, preventing race conditions.
4. The wait set is managed by the monitor; you must hold the monitor to use it.

This is a fundamental rule of Java’s monitor-based concurrency model.


## Q.12: What is the difference between wait() and sleep() method?
**Purpose**

* **`wait()`** → Thread coordination; used for inter-thread communication.
* **`sleep()`** → Pause current thread for a time; no communication semantics.

---

### **1. Lock Behavior**

#### **wait()**

* Must be called inside a synchronized block/method.
* **Releases the monitor lock** on the object while waiting.
* Allows other threads to acquire the lock and modify the condition.

#### **sleep()**

* Does **not** require synchronization.
* **Does not release any lock** the thread holds.

---

### **2. Who Wakes the Thread**

#### **wait()**

* Woken by:

  * `notify()`
  * `notifyAll()`
  * Spurious wakeups
* Often used with a loop:

  ```java
  synchronized (obj) {
      while (!condition) obj.wait();
  }
  ```

#### **sleep()**

* Wakes automatically after the specified time.
* Can also be interrupted.

---

### **3. Use Case**

#### **wait()**

* Producer–Consumer patterns
* Coordinating threads via shared state
* Requires a monitor lock

#### **sleep()**

* Delay, backoff, time-based pauses
* No relation to shared resources or signaling

---

### **4. Defined In**

* **`wait()`** → Defined in `Object` class (because every object has a monitor).
* **`sleep()`** → Static method in `Thread` class.

---

### **5. Checked Exceptions**

* Both throw `InterruptedException`.

---

### **Summary Table**

| Aspect                      | `wait()`                  | `sleep()`       |
| --------------------------- | ------------------------- | --------------- |
| Requires synchronized block | Yes                       | No              |
| Releases lock while waiting | Yes                       | No              |
| Purpose                     | Thread communication      | Pause execution |
| Wake mechanism              | notify/notifyAll/spurious | Time expiry     |
| Defined in                  | `Object`                  | `Thread`        |
| Use cases                   | Coordination              | Timing delays   |

This captures the full conceptual and practical difference.


## Q.13: What is static synchronization?
**Static Synchronization**
Synchronization applied on **static methods**.
A static synchronized method locks **the Class object**, not an instance.

When a thread enters a `static synchronized` method, it acquires the monitor lock of `ClassName.class`.

---

### **How It Works**

* Instance synchronized methods use the **object’s** monitor.
* Static synchronized methods use the **class-level** monitor.
* All instances of the class share the same class-level lock.

So:

* One thread in a `static synchronized` method blocks *all other threads* trying to enter any static synchronized method of that class.
* But it **does not** block instance-level synchronized methods.

---

### **Example**

```java
class Counter {

    private static int count = 0;

    public static synchronized void increment() {
        count++;
    }
}
```

Equivalent expanded form:

```java
public static void increment() {
    synchronized (Counter.class) {
        count++;
    }
}
```

---

### **Why Use Static Synchronization**

* To protect **static shared data** across all objects.
* To ensure correctness when multiple instances operate on the same shared static state.

---

### **Important Points**

* Locking is on `ClassName.class`.
* Static and instance-level locks are **different**.
* If static data is modified by multiple threads, use static synchronization.

This is static synchronization: locking at the class level instead of object level.


## Q.14: How is the safety of a thread achieved?
**Thread safety** means shared data is accessed by multiple threads **without corrupting state** or producing inconsistent results.

Thread safety is achieved through the following mechanisms:

---

### **1. Synchronization (Intrinsic Locks)**

Using `synchronized` ensures only one thread at a time can execute the critical section.

**Example:**

```java
synchronized(obj) {
    shared++;
}
```

Guarantees:

* Mutual exclusion
* Visibility of writes (happens-before)

---

### **2. Explicit Locks (`ReentrantLock`, `ReadWriteLock`)**

More flexible than `synchronized`.

**Example:**

```java
lock.lock();
try {
    shared++;
} finally {
    lock.unlock();
}
```

Provides:

* Try-lock
* Timed lock
* Fair scheduling
* Read-write separation

---

### **3. Volatile Variables**

Ensures visibility and prevents instruction reordering, but **does not** ensure atomicity.

**Example use cases:**

* Stop flags
* Status variables

```java
volatile boolean running = true;
```

---

### **4. Atomic Classes (`AtomicInteger`, `AtomicLong`, etc.)**

Provide lock-free thread-safe operations via CAS (compare-and-swap).

```java
AtomicInteger count = new AtomicInteger();
count.incrementAndGet();
```

Useful when synchronization is too costly.

---

### **5. Immutable Objects**

Immutable objects are inherently thread-safe because state never changes.

Example:

* `String`
* Custom immutable classes

```java
final class Point {
    private final int x, y;
}
```

---

### **6. Thread Confinement**

A variable is safe if it is never shared across threads.

Techniques:

* Local variables
* Thread-local storage (`ThreadLocal`)

```java
ThreadLocal<SimpleDateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat());
```

---

### **7. High-Level Concurrency Utilities (`java.util.concurrent`)**

Safer alternatives to manual locking:

* `ConcurrentHashMap`
* `BlockingQueue`
* `CopyOnWriteArrayList`
* `Semaphore`
* `CountDownLatch`
* `CyclicBarrier`
* `Exchanger`

Example:

```java
BlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
```

---

### **8. Thread Pools (Controlled Concurrency)**

Avoid creating uncontrolled numbers of threads, preventing race conditions and resource starvation.

```java
ExecutorService pool = Executors.newFixedThreadPool(5);
```

---

### **Summary**

Thread safety is achieved through:

1. `synchronized`
2. Explicit locks
3. `volatile`
4. Atomic variables
5. Immutable objects
6. Thread confinement
7. Concurrency utilities
8. Thread pools and structured concurrency

These mechanisms ensure **mutual exclusion, visibility, ordering, and safe access** to shared state.


## Q.15: What is difference between start() and run() method of thread class?
**start()**

* Creates a **new OS-level thread**.
* The thread moves to the **Runnable** state.
* The JVM scheduler calls the thread’s `run()` method asynchronously.
* Can be called **only once**; calling again throws `IllegalThreadStateException`.

**run()**

* A normal method call; **does not create a new thread**.
* Executes in the **current thread’s call stack**, synchronously.
* Can be called multiple times like any other method.

---

### **Example**

```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));

// Case 1
t.start(); 
// Output: Thread-0  (executes in a new thread)

// Case 2
t.run();
// Output: main       (executes in the main thread)
```

---

### **Key Differences Table**

| Aspect                  | `start()`                | `run()`            |
| ----------------------- | ------------------------ | ------------------ |
| Creates new thread      | Yes                      | No                 |
| Executes asynchronously | Yes                      | No                 |
| Executes in             | Separate thread          | Current thread     |
| Multiple calls allowed  | No                       | Yes                |
| Lifecycle behavior      | Moves thread to Runnable | Just a method call |

This is the exact distinction expected in interviews.


## Q.16: What is Thread Group? Why it's advised not to use it?
**Thread Group**
A `ThreadGroup` is a hierarchical grouping of threads. It was designed to manage multiple threads together—e.g., set their max priority, interrupt all of them at once, or enumerate them.

**Example:**

```java
ThreadGroup group = new ThreadGroup("MyGroup");
Thread t = new Thread(group, () -> {});
t.start();
```

**Capabilities:**

* Grouping threads under a common parent
* Setting maximum priority for the group
* Interrupting all threads in the group
* Listing/enumerating all threads in a group

---

### **Why It’s Advised Not To Use ThreadGroup**

#### **1. Obsolete Design**

Thread groups were introduced in early Java versions before modern concurrency utilities existed. Today, almost all practical needs are handled better by `ExecutorService`, `ThreadPoolExecutor`, `ForkJoinPool`, and other concurrency classes.

#### **2. Weak and Incomplete Isolation**

* Cannot reliably manage or enforce security.
* Cannot fully prevent threads from escaping the group.
* Many APIs ignore thread groups entirely.

#### **3. Poor Error Handling**

`ThreadGroup` attempted to handle uncaught exceptions but was replaced by `Thread.setUncaughtExceptionHandler()`, which is superior.

#### **4. Deprecated Functionality**

Much of `ThreadGroup`’s intended functionality is labeled as “not recommended” in the official JDK docs. Modern Java considers it a vestigial feature.

#### **5. No Real Control Over Child Threads**

It cannot:

* Stop a thread safely
* Suspend or resume threads
* Enforce synchronization
* Replace proper resource management

#### **6. Inconsistent with Modern Concurrency Frameworks**

Thread pools and executors internally manage their own worker threads. They don’t rely on or expose thread groups. Most APIs ignore thread groups as design baggage.

---

### **Summary**

**Thread Group** = legacy mechanism to group and manage threads.

**Not recommended because:**

* Outdated
* Weak control
* Poor error handling
* Replaced by modern concurrency utilities
* Limited usefulness in real-world systems

Today, the recommended approach is **ExecutorService / ThreadPoolExecutor**, not `ThreadGroup`.


## Q.17: How do you stop a thread in java?
**Stopping a thread safely** means letting the thread **finish its work and exit on its own**, not killing it forcefully.

Java does **not** support safe force-stopping of threads.
The correct approach is **cooperative interruption**.

---

**1. Using `interrupt()` + interruption checks (Correct way)**

#### **Thread logic:**

```java
class Worker implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // do work
            // check interruption in blocking calls
        }
    }
}
```

#### **Stopping the thread:**

```java
Thread t = new Thread(new Worker());
t.start();
t.interrupt();   // request stop
```

#### **If thread is in blocking methods (sleep, wait, join, BlockingQueue):**

These methods throw **InterruptedException**, and you exit the loop.

---

**2. Using a custom flag (volatile boolean)**

Used when the thread is doing non-blocking work.

```java
class Worker implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            // work
        }
    }

    public void stop() {
        running = false;
    }
}
```

This works, but `interrupt()` is preferred when thread may block.

---

**3. Combining both (Best practice)**

Use an interruption flag + cooperate with blocking operations.

```java
class Worker implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // work
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore flag
        }
    }
}
```

---

**Methods NOT to use**

#### **1. `Thread.stop()` (Deprecated, unsafe)**

* Can leave shared objects in inconsistent states.
* Kills the thread immediately without cleanup.
  **Never use it.**

#### **2. `Thread.suspend()` and `Thread.resume()` (Deprecated)**

* Lead to deadlocks because `suspend()` freezes a thread while it may hold locks.

---

**Summary**

| Method                  | Safe | Notes                                  |
| ----------------------- | ---- | -------------------------------------- |
| `interrupt()`           | ✔    | Recommended, works with blocking calls |
| `volatile boolean` flag | ✔    | Works for non-blocking tasks           |
| `stop()`                | ✘    | Unsafe, deprecated                     |
| `suspend()/resume()`    | ✘    | Deprecated, causes deadlocks           |

**Safe stopping = cooperative stopping.** The thread must exit by checking a signal, not by being forcefully killed.


## Q.18: Can we call run() method of a Thread class?
Yes, we **can** call `run()` directly, but it **does not start a new thread**. It behaves like a normal method call.

---

### **What actually happens when you call `run()`?**

* Executes in the **current thread** (e.g., `main` thread).
* No new OS thread is created.
* No context switching.
* The thread lifecycle (`NEW → RUNNABLE → TERMINATED`) is **not** triggered.

**Example:**

```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));

t.run();    // prints: main
```

---

### **What happens when you call `start()`?**

* Creates a **new thread**.
* JVM schedules it and invokes `run()` internally.

**Example:**

```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));

t.start();  // prints: Thread-0
```

---

### **Conclusion**

* **run()** → normal method call, no new thread.
* **start()** → creates a new thread and then calls `run()` on that thread.


## Q.19: What is difference between Yield and Sleep method in Java?
**Purpose**

* **`yield()`** → Hint to the scheduler to give other threads a chance.
* **`sleep(ms)`** → Pause the current thread for a fixed duration.

---

### **Key Differences**

#### **1. Behavior**

**yield()**

* Tells the scheduler: *“I’m willing to pause; schedule another runnable thread.”*
* The thread moves from **Running → Runnable**.
* The scheduler **may** immediately schedule the same thread again (no guarantee).

**sleep()**

* Pauses the current thread for a defined time.
* Moves from **Running → Timed Waiting**.
* Guaranteed not to run until sleep time completes or the thread is interrupted.

---

#### **2. Lock Behavior**

Both **retain locks** (do not release any monitor lock they hold).

---

#### **3. Time Guarantee**

**yield()**

* No guarantee of how long the pause lasts.
* May not pause at all depending on scheduler.

**sleep()**

* Guaranteed suspension for at least the specified duration (unless interrupted).

---

#### **4. Use Cases**

**yield()**

* Rarely useful.
* Minor hint to the scheduler in spin-wait loops.

**sleep()**

* Timed delays
* Polling intervals
* Backoff strategies
* Scheduled pauses

---

#### **5. Defined In**

* `yield()` → `Thread.yield()`
* `sleep()` → `Thread.sleep(ms)`

---

### **Summary Table**

| Aspect              | `yield()`           | `sleep()`                           |
| ------------------- | ------------------- | ----------------------------------- |
| Scheduler hint      | Yes                 | No                                  |
| Duration guaranteed | No                  | Yes                                 |
| Releases lock       | No                  | No                                  |
| Moves to            | Runnable            | Timed Waiting                       |
| Interruptible       | No (does not throw) | Yes (throws `InterruptedException`) |
| Common use          | Rare                | Common                              |

This is the complete and interview-oriented difference between `yield()` and `sleep()`.


## Q.20: What is Java Thread Dump, How can we get Java Thread dump of a Program?
**Java Thread Dump**
A thread dump is a snapshot of **all threads** in the JVM at a specific moment.
It shows each thread’s:

* Name
* State (RUNNABLE, BLOCKED, WAITING, etc.)
* Stack trace
* Locks held and locks waited on
* Deadlock information (if present)

Thread dumps are used for analyzing:

* Deadlocks
* High CPU usage
* Thread starvation
* Blocking and synchronization issues
* Performance bottlenecks

---

**How to Generate a Java Thread Dump**

### **1. Using OS Signals (Most Common in Production)**

#### **Linux / macOS**

Send **SIGQUIT**:

```
kill -3 <pid>
```

The dump is printed to standard output or to the application log.

#### **Windows**

Press:

```
Ctrl + Break
```

in the console where the JVM is running.

---

### **2. Using `jstack` Tool (JDK built-in)**

```
jstack <pid>
```

For a more detailed dump:

```
jstack -l <pid>
```

Outputs:

* Thread names
* Stack traces
* Lock information
* Native frames (if needed)

---

### **3. Using JDK Mission Control / JConsole / VisualVM**

#### **JConsole / VisualVM**

* Connect to the running JVM.
* Open **Threads** tab.
* Click **Thread Dump**.

#### **Java Mission Control (JMC)**

* Connect to JVM.
* Open **Threads**.
* Click **Thread Dump**.

These tools also show:

* CPU usage per thread
* Deadlock detection
* Live graphical thread activity

---

### **4. Programmatically (From Code)**

```java
Map<Thread, StackTraceElement[]> dump = Thread.getAllStackTraces();
```

Or:

```java
ThreadMXBean bean = ManagementFactory.getThreadMXBean();
ThreadInfo[] info = bean.dumpAllThreads(true, true);
```

Useful for internal debugging systems.

---

### **5. From Application Servers / Framework Tools**

For example:

* Tomcat manager → “Thread Dump”
* WebLogic Admin console → Diagnostic dump
* Kubernetes exec → then run `jstack` inside the pod

---

**Final Summary**

**Java Thread Dump** = snapshot of all threads, their states, and lock interactions.
**How to get it:**

1. `kill -3 <pid>` (Linux/macOS)
2. `Ctrl + Break` (Windows)
3. `jstack <pid>`
4. JConsole, VisualVM, Mission Control
5. Programmatically via `ThreadMXBean`

Thread dumps are essential for diagnosing deadlocks, high CPU problems, and concurrency issues.


## Q.21: What will happen if we dont override Thread class run() method?
If you **don’t override `run()`** in a thread class, then:

#### **1. The default `run()` method does nothing**

`Thread`’s default implementation is:

```java
public void run() {
    if (target != null) {
        target.run();
    }
}
```

If no `Runnable` target is provided and you don’t override `run()`, the thread executes **an empty method**.

So the thread starts, does nothing, and terminates immediately.

---

#### **2. `start()` will still create a new thread**

A new OS thread is created, but it performs no useful work.

---

#### **3. No compile-time error, no runtime error**

The program runs normally but doesn’t perform any task inside the thread.

---

#### **Cases**

#### **Case A — Extending `Thread` but not overriding `run()`**

```java
class MyThread extends Thread { }

MyThread t = new MyThread();
t.start();   // thread starts, does nothing
```

#### **Case B — Passing a Runnable**

This works because `target` is not null:

```java
Runnable r = () -> System.out.println("Hi");
Thread t = new Thread(r);
t.start();   // prints "Hi"
```

#### **Case C — Extending Thread + Passing Runnable (override ignored if missing)**

If you extend `Thread` but don’t override `run()`, and pass a `Runnable` target:

```java
class MyThread extends Thread { }

Thread t = new MyThread(() -> System.out.println("Hi"));
t.start();   // prints "Hi"
```

Here `Thread.run()` will call target.run().

---

**Summary**

If you don’t override `run()` and don’t provide a `Runnable`:

* The new thread executes an empty method.
* No useful work happens.
* Thread starts → runs → terminates immediately.

Overriding `run()` or passing a `Runnable` is required if you want the thread to perform work.


## Q.22: What is difference between the Thread class and Runnable interface for creating a Thread?
**1. Inheritance vs Composition**

* **Thread class** → You *extend* `Thread`.

  * Not flexible (Java allows only single inheritance).
* **Runnable interface** → You *implement* `Runnable` and pass it to a `Thread`.

  * More flexible; your class can extend something else.

---

**2. Separation of Task and Thread**

* **Thread subclass** mixes:

  * *What to run* (task logic)
  * *How to run* (thread management)

* **Runnable** cleanly separates:

  * `Runnable` = task
  * `Thread` = execution mechanism

This is cleaner and better for real applications.

---

**3. Reusability**

* **Thread subclass**

  * Task cannot be reused without creating a new Thread object.

* **Runnable**

  * The same task can be executed by multiple threads or submitted to executors.

---

**4. Use with Thread Pools**

* **Thread subclass**

  * **Cannot** be used by thread pools (`ExecutorService`).
  * Executors accept Runnable/Callable tasks.

* **Runnable**

  * Works naturally with thread pools.

---

**5. Code Examples**

#### **Using Thread class**

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("running");
    }
}

new MyThread().start();
```

#### **Using Runnable (preferred)**

```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("running");
    }
}

Thread t = new Thread(new MyTask());
t.start();
```

---

**6. Memory Sharing**
Both approaches create a thread that shares process memory, no difference at runtime.

---

### **Summary Table**

| Aspect            | Thread Class              | Runnable Interface       |
| ----------------- | ------------------------- | ------------------------ |
| Inheritance       | Requires extending Thread | Can extend another class |
| Reusability       | Low                       | High                     |
| Task vs Execution | Coupled                   | Separated                |
| Thread Pools      | Not supported             | Fully supported          |
| Flexibility       | Less                      | More                     |
| Recommended       | No                        | Yes (industry standard)  |

**Runnable is the recommended and modern way to create tasks for threads.**


## Q.23: What does join() method do in Java threading?
**`join()`** makes one thread **wait** for another thread to **finish execution**.

---

### **What it does**

When you call:

```java
t.join();
```

the **current thread** pauses until thread **t** completes (moves to TERMINATED).

---

### **Why it’s used**

* Ensure a worker thread finishes before continuing.
* Combine results of multiple threads.
* Sequentialize parts of concurrent logic.

---

### **Example**

```java
Thread t = new Thread(() -> {
    System.out.println("task");
});

t.start();
t.join();         // main thread waits here

System.out.println("finished");
```

Output:

```
task
finished
```

---

### **Overloaded versions**

#### **1. `join()`**

Waits indefinitely until the thread completes.

#### **2. `join(long millis)`**

Waits for at most `millis` milliseconds.

#### **3. `join(long millis, int nanos)`**

More precise timed waiting.

If timeout expires and the thread hasn’t finished, control returns.

---

### **Behavior details**

* Throws `InterruptedException` if the waiting thread is interrupted.
* Does **not** need synchronized block.
* Internally uses `wait()` on the thread object.

---

### **Summary**

`join()` = *“Wait for this thread to complete before proceeding.”*


## Q.24: What is race-condition?
A **race condition** occurs when multiple threads access and modify shared data **at the same time**, and the final outcome depends on the **timing** of their execution.
Because operations interleave unpredictably, the result becomes inconsistent and incorrect.

---

### **Example**

Two threads increment the same variable:

```java
int count = 0;

Thread t1 = new Thread(() -> count++);
Thread t2 = new Thread(() -> count++);
```

`count++` is **not atomic**. It consists of:

1. Read `count`
2. Add 1
3. Write back

Interleaving like this:

```
t1: read 0
t2: read 0
t1: write 1
t2: write 1
```

Final value = **1**, but correct value = **2** → race condition.

---

### **Typical Causes**

* Non-atomic read–modify–write operations
* Shared mutable state accessed by multiple threads
* Missing synchronization
* Inconsistent lock ordering

---

### **Symptoms**

* Incorrect results
* Rare or intermittent bugs
* Hard-to-reproduce failures
* Data corruption

---

### **How to Prevent Race Conditions**

#### **1. Synchronization**

```java
synchronized (lock) {
    count++;
}
```

#### **2. Locks (`ReentrantLock`, `ReadWriteLock`)**

```java
lock.lock();
try { count++; }
finally { lock.unlock(); }
```

#### **3. Atomic variables**

```java
AtomicInteger count = new AtomicInteger();
count.incrementAndGet();
```

#### **4. Immutable data**

No modification → no race.

#### **5. Thread confinement**

Keep variables local; don’t share across threads.

---

### **Summary**

A race condition happens when threads access shared mutable data concurrently without proper synchronization, causing unpredictable and incorrect outcomes.



## Q.25: What is Lock interface in Java Concurrency API? What is the Difference between ReentrantLock and Synchronized?
**Lock Interface (java.util.concurrent.locks.Lock)**
`Lock` is a concurrency abstraction that provides **explicit locking** with more capabilities than `synchronized`.
It allows:

* Explicit lock acquisition and release
* Try-lock with timeout
* Fair locking
* Interruptible lock acquisition
* Multiple condition variables

Core methods:

```java
lock()
tryLock()
tryLock(long time, TimeUnit unit)
unlock()
lockInterruptibly()
newCondition()
```

---

**Difference Between `ReentrantLock` and `synchronized`**

### **1. Lock Acquisition Control**

#### **synchronized**

* Automatic: lock acquired at block/method entry, released at exit.
* No flexibility.

#### **ReentrantLock**

* Explicit: must call `lock()` and `unlock()`.
* Gives fine-grained control.

---

### **2. Ability to Try for a Lock**

#### **synchronized**

* No try-lock. If lock is held, thread must wait.

#### **ReentrantLock**

```java
if (lock.tryLock()) { ... }
```

* Non-blocking lock attempt
* Timed lock attempt:

```java
lock.tryLock(100, TimeUnit.MILLISECONDS);
```

---

### **3. Interruptible Lock Acquisition**

#### **synchronized**

* Lock acquisition **cannot** be interrupted.

#### **ReentrantLock**

```java
lock.lockInterruptibly();
```

* Thread can respond to interruption while waiting.

---

### **4. Fairness**

#### **synchronized**

* No fairness; JVM decides scheduling.

#### **ReentrantLock**

Supports fairness (first-come-first-served):

```java
new ReentrantLock(true);
```

---

### **5. Multiple Condition Variables**

#### **synchronized**

* Only one wait set per monitor (`wait/notify/notifyAll`).

#### **ReentrantLock**

Multiple conditions:

```java
Condition c1 = lock.newCondition();
Condition c2 = lock.newCondition();
```

Gives advanced producer–consumer patterns.

---

### **6. Performance**

* `ReentrantLock` may outperform `synchronized` under high contention.
* From Java 6+, `synchronized` is heavily optimized, so difference is smaller.

---

### **7. Debuggability**

#### **synchronized**

* Hard to determine if a lock is held by a thread.

#### **ReentrantLock**

```java
lock.isLocked()
lock.isHeldByCurrentThread()
lock.getHoldCount()
```

---

### **8. Lock Release Safety**

#### **synchronized**

* Automatically released even if exceptions occur.

#### **ReentrantLock**

Must manually use:

```java
lock.lock();
try {
    // work
} finally {
    lock.unlock();
}
```

If you forget `unlock()`, deadlock risk.

---

**Summary Table**

| Feature                   | synchronized | ReentrantLock          |
| ------------------------- | ------------ | ---------------------- |
| Lock acquisition          | implicit     | explicit               |
| Lock release              | automatic    | manual (finally block) |
| Try-lock                  | No           | Yes                    |
| Timed lock                | No           | Yes                    |
| Interruptible lock        | No           | Yes                    |
| Fairness                  | No           | Yes (optional)         |
| Multiple conditions       | No           | Yes                    |
| Debugging helpers         | No           | Yes                    |
| Risk of forgetting unlock | No           | Yes                    |

---

**When to Use What**

**Use `synchronized` when:**

* Simple mutual exclusion is enough
* No advanced features required
* You want minimal risk and simpler code

**Use `ReentrantLock` when:**

* You need try-lock or timed lock
* You want a fair lock
* You need multiple condition queues
* You require interruptible waits

This is the exact comparison expected in interviews.


## Q.26: What is the difference between the Runnable and Callable interface?
**Runnable vs Callable — Core Difference**

| Feature                   | Runnable                    | Callable                             |
| ------------------------- | --------------------------- | ------------------------------------ |
| Return value              | No                          | Yes                                  |
| Throws checked exceptions | No                          | Yes                                  |
| Method                    | `run()`                     | `call()`                             |
| Used with                 | `Thread`, `ExecutorService` | `ExecutorService` (with `Future`)    |
| Introduced in             | Java 1.0                    | Java 5 (with `java.util.concurrent`) |

---

**1. Method Signatures**

#### **Runnable**

```java
public void run();
```

* Returns nothing
* Cannot throw checked exceptions

#### **Callable**

```java
public V call() throws Exception;
```

* Returns a value of type `V`
* Can throw checked exceptions

---

**2. How They Are Executed**

#### **Runnable**

Can be passed to:

```java
new Thread(runnable).start();
executor.submit(runnable);
```

#### **Callable**

Cannot be run by `Thread` directly.
Must be submitted to an `ExecutorService`:

```java
Future<Integer> f = executor.submit(callable);
```

---

**3. Returning Results**

#### **Runnable**

* No direct result.
* To return something, you must use shared variables or side effects.

#### **Callable**

* Returns value through `Future`:

```java
Integer result = f.get();
```

---

**4. Exception Handling**

#### **Runnable**

Checked exceptions must be handled inside `run()`.

#### **Callable**

Checked exceptions are allowed and are rethrown via `Future.get()`.

---

**Example Comparison**

#### **Runnable**

```java
class MyTask implements Runnable {
    public void run() {
        System.out.println("running");
    }
}
```

#### **Callable**

```java
class MyTask implements Callable<Integer> {
    public Integer call() throws Exception {
        return 42;
    }
}
```

---

**Usage Recommendation**

* Use **Runnable** when no result or checked exception handling is needed.
* Use **Callable** when you need:

  * a return value
  * exception propagation
  * asynchronous computation with `Future`

Callable is the advanced, flexible version of Runnable.


## Q.27: What is the Thread's interrupt flag? How does it relate to the InterruptedException?
**Thread Interrupt Flag**
Each thread has a **boolean interrupt flag** that indicates whether an interrupt request has been sent to it.

* Calling `thread.interrupt()` **sets** this flag to `true`.
* The thread **does NOT stop automatically**.
* It’s only a signal: “Please stop what you’re doing when you can.”

You check it using:

```java
Thread.currentThread().isInterrupted()
```

or by handling `InterruptedException`.

---

**How InterruptedException is Related**

#### **1. Blocking methods react to the interrupt flag**

If a thread is in a *blocking* operation when interrupted:

* `Object.wait()`
* `Thread.sleep()`
* `Thread.join()`
* `BlockingQueue.put()/take()`
* `Lock.lockInterruptibly()`

…then the JVM:

1. **Clears the interrupt flag**, and
2. **Throws `InterruptedException`**.

This gives the thread a chance to exit gracefully.

---

**2. If the thread is NOT blocking**

If the thread is performing normal computation (CPU work):

* No exception is thrown.
* The interrupt flag stays **true**.
* The thread must **check the flag manually**.

Example:

```java
while (!Thread.currentThread().isInterrupted()) {
    // work
}
```

---

**3. `InterruptedException` temporarily clears the flag**

Inside a blocking method, if interruption happens:

* Flag becomes **false** after exception is thrown.
* If your logic wants to preserve the interruption, you must restore it:

```java
catch (InterruptedException e) {
    Thread.currentThread().interrupt(); // restore flag
}
```

---

**Summary**

**Interrupt flag:**

* A boolean marker on each thread.
* Set by `interrupt()`.
* Checked by `isInterrupted()`.

**InterruptedException:**

* Thrown only by blocking methods.
* Thrown because the interrupt flag was set.
* Clears the interrupt flag when thrown.

**Relationship:**

* `interrupt()` → sets flag
* Blocking method sees flag → throws InterruptedException → clears flag
* Thread should catch and decide whether to stop or restore the flag.

This is the foundation of cooperative thread interruption in Java.



## Q.28: What is Java Memory Model (JMM)? Describe its purpose and basic ideas.
**Java Memory Model (JMM)**
The Java Memory Model defines **how threads interact through memory**. It specifies **what a thread is allowed to see** when another thread writes to shared variables, and under what rules memory operations become **visible and ordered** across threads.

Its purpose is to ensure **correct, predictable multithreading behavior** across different CPUs, compilers, and JVM optimizations.

---

**Purpose of JMM**

1. **Visibility guarantees**
   Ensures that writes by one thread become visible to others under specific rules.

2. **Ordering guarantees**
   Prevents unsafe instruction reordering that would break concurrency logic.

3. **Defines happens-before rules**
   Establishes when one action's result is guaranteed to be visible to another.

4. **Provides a formal model for concurrency libraries**
   All features like `volatile`, `synchronized`, locks, atomics are built on JMM rules.

5. **Ensures cross-platform consistency**
   Prevents CPU-specific memory reordering issues from breaking Java programs.

---

**Core Concepts of JMM**

### **1. Main Memory vs Working Memory**

* Each thread has a **working memory** (CPU registers + caches).
* All threads share **main memory** (heap).
* A thread may not immediately see updates made by another thread unless memory rules enforce visibility.

---

### **2. Happens-Before Relationship**

This is the foundation of JMM.

If action A **happens-before** action B, then:

* A’s results are visible to B
* A is ordered before B

Key happens-before rules:

* **Unlock happens-before next lock** of the same monitor
* **Write to a volatile happens-before read of that volatile**
* **Thread.start() happens-before the new thread begins**
* **Thread.join() happens-before the join returns**
* **Program order rule:** actions within the same thread follow code order

---

### **3. Visibility**

JMM specifies when updates become visible:

#### **With synchronized**

* Exiting synchronized block → flush changes to main memory
* Entering synchronized block → reload values from main memory

#### **With volatile**

* Write to volatile is always visible to other threads
* Prevents caching and reordering around volatile accesses

#### **Without synchronization**

* No visibility guarantee
* Threads may see stale data

---

### **4. Atomicity**

Some operations are inherently atomic:

* Reads/writes of 32-bit primitives
* Reads/writes of volatile variables

Non-atomic example:

* `count++` is not atomic

---

### **5. Reordering**

The JVM and CPU may reorder instructions for optimization.
JMM defines rules to prevent such reorderings from breaking correctness.

Key points:

* `volatile` prevents reordering of access around it
* `synchronized` prevents reorderings inside the critical section relative to outside

---

**Why JMM is Important**

Without JMM:

* Visibility bugs would be unpredictable
* CPU and JVM optimizations would cause inconsistent behavior
* Multithreading would not be portable across machines and architectures
* Locks, volatile, atomics would not work reliably

---

**Summary**

The **Java Memory Model** defines:

* How threads read/write shared memory
* Which writes are visible to which reads
* What ordering guarantees exist
* How synchronization primitives enforce visibility and ordering

It ensures correct, consistent, and portable multithreading behavior in Java.


## Q.29: Describe the conditions of livelock and starvation.
**Livelock** and **starvation** are two concurrency problems where threads fail to make progress, but for different reasons.

---

**1. Livelock**

### **Definition**

Threads are **actively changing state** (not blocked) but still **make no progress** because they repeatedly respond to each other in a way that prevents completion.

It’s like two people stepping aside for each other in a hallway but continuously moving back and forth.

### **Key characteristics**

* Threads are **not blocked**; they continue running.
* Each thread keeps retrying an action.
* Progress is impossible because threads keep interfering.

### **Example**

Two threads using `tryLock()` and backing off when they fail:

```java
while (true) {
    if (lock1.tryLock()) {
        if (lock2.tryLock()) {
            break; // success
        } else {
            lock1.unlock();
        }
    }
    // Retry immediately → both threads keep releasing each other
}
```

Both threads keep “courteously” giving up their locks → **no progress**.

---

**2. Starvation**

### **Definition**

A thread **never gets CPU time or never gets access to a required resource** because other threads constantly take precedence.

### **Key causes**

* Thread priority: low-priority thread is never scheduled.
* Locks or resources are always acquired by other threads first.
* Unfair locks (`synchronized` or `ReentrantLock` with unfair policy).
* Resource-hogging threads.

### **Example: unfair locking**

```java
ReentrantLock lock = new ReentrantLock(false); // unfair

Thread low = new Thread(() -> {
    while (true) {
        lock.lock();   // might never acquire
        try { /* work */ }
        finally { lock.unlock(); }
    }
});
```

High-priority threads or frequently-running threads keep acquiring the lock → low-priority thread starves.

---

**Difference Between Livelock and Starvation**

| Aspect           | Livelock                                      | Starvation                          |
| ---------------- | --------------------------------------------- | ----------------------------------- |
| Thread state     | Active, not blocked                           | May be active or waiting            |
| Progress         | None                                          | None, for the starving thread       |
| Cause            | Threads interfere with each other by reacting | Scheduling bias or resource hogging |
| Example behavior | Repeated retries                              | Never scheduled / never gets lock   |

---

**Summary**

* **Livelock:** Threads are running but make no progress because they repeatedly react to each other.
* **Starvation:** A thread is indefinitely deprived of CPU or resources due to unfair scheduling or monopolization.

Both are concurrency pathologies, but caused by different mechanisms.


## Q.30: How do I share a variable between 2 Java threads?
**Ways to share a variable between two Java threads**

---

**1. Use a shared object reference (most common)**

Both threads access the same object instance.

```java
class SharedData {
    int value = 0;
}

SharedData data = new SharedData();

Thread t1 = new Thread(() -> data.value++);
Thread t2 = new Thread(() -> data.value--);
```

This is shared memory.
**But NOT thread-safe** without synchronization if both modify it.

---

**2. Use `volatile` for visibility (not atomic)**

Ensures updates from one thread become visible to another.

```java
volatile int flag = 0;

Thread t1 = new Thread(() -> flag = 1);
Thread t2 = new Thread(() -> {
    while (flag == 0) {}
});
```

Use volatile for:

* status flags
* read-mostly variables

Not safe for operations like `count++`.

---

**3. Use Atomic variables (atomic + lock-free)**

Guarantees safe updates without `synchronized`.

```java
AtomicInteger counter = new AtomicInteger();

Thread t1 = new Thread(counter::incrementAndGet);
Thread t2 = new Thread(counter::incrementAndGet);
```

Atomic variables provide:

* atomicity
* visibility
* no locking

---

**4. Use synchronized blocks (mutual exclusion)**

Protects the shared variable with a common lock.

```java
class Shared {
    int count = 0;
}

Shared s = new Shared();
Object lock = new Object();

Thread t1 = new Thread(() -> {
    synchronized(lock) {
        s.count++;
    }
});
Thread t2 = new Thread(() -> {
    synchronized(lock) {
        s.count--;
    }
});
```

---

**5. Use explicit locks (`ReentrantLock`)**

```java
ReentrantLock lock = new ReentrantLock();
int[] num = {0};

Thread t1 = new Thread(() -> {
    lock.lock();
    try { num[0]++; }
    finally { lock.unlock(); }
});
```

Provides:

* tryLock
* fairness
* interruptible locking

---

**6. Use thread-safe data structures**

If the shared variable is part of a structure:

* `ConcurrentHashMap`
* `CopyOnWriteArrayList`
* `BlockingQueue`
* `ConcurrentLinkedQueue`

Example:

```java
BlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
```

---

**Summary**

You share a variable between threads by passing the **same object reference** to both, and then ensuring **thread safety** using:

1. Shared object (basic sharing)
2. `volatile` (visibility)
3. Atomic variables (atomicity + visibility)
4. `synchronized` (locking)
5. `ReentrantLock` (advanced locking)
6. Concurrent data structures

This covers all safe and unsafe ways of sharing data between threads.


## Q.31: What are the main components of concurrency API?
The **Java Concurrency API (java.util.concurrent)** consists of several major components that provide high-level, safe, and efficient concurrency utilities beyond low-level `synchronized` and `wait/notify`.

---

**1. Executors (Thread Pool Framework)**

Manages threads and executes tasks asynchronously.

Key components:

* **Executor**
* **ExecutorService**
* **ScheduledExecutorService**
* **ThreadPoolExecutor**
* **ForkJoinPool**
* **Future**
* **Callable**

Purpose:

* Replace manual thread creation
* Provide thread pools
* Manage task submission, scheduling, shutdown

---

**2. Locks**

More flexible locking than `synchronized`.

Important classes:

* **Lock** (interface)
* **ReentrantLock**
* **ReentrantReadWriteLock**
* **StampedLock**

Features:

* tryLock
* timed lock
* interruptible lock
* fairness policies
* multiple condition variables

---

**3. Synchronizers**

Reusable synchronization constructs.

Includes:

* **Semaphore**
* **CountDownLatch**
* **CyclicBarrier**
* **Phaser**
* **Exchanger**

Purpose:

* Thread coordination
* Blocking/unblocking
* Barrier synchronization
* Permits-based resource control

---

**4. Concurrent Collections**

Thread-safe data structures with high performance.

Key classes:

* **ConcurrentHashMap**
* **CopyOnWriteArrayList**
* **CopyOnWriteArraySet**
* **ConcurrentLinkedQueue**
* **ConcurrentLinkedDeque**
* **BlockingQueue** implementations:

  * ArrayBlockingQueue
  * LinkedBlockingQueue
  * PriorityBlockingQueue
  * DelayQueue
  * SynchronousQueue

These internalize all locking/atomic logic.

---

**5. Atomic Variables (Lock-Free Programming)**

Perform atomic operations using hardware-level CAS.

Classes:

* **AtomicInteger**
* **AtomicLong**
* **AtomicBoolean**
* **AtomicReference**
* Atomic arrays: **AtomicIntegerArray**, etc.
* Field updaters

Benefits:

* No locks
* Highly efficient under contention

---

**6. Fork/Join Framework**

For divide-and-conquer parallelism.

Components:

* **ForkJoinPool**
* **RecursiveAction**
* **RecursiveTask**

Used by:

* `parallelStream()`
* `CompletableFuture` (common pool)

---

**7. CompletableFuture**

Asynchronous programming framework.

Features:

* Chaining (`thenApply`, `thenRun`)
* Combined futures (`thenCombine`)
* Exception handling
* Pipeline-style async workflows
* Uses ForkJoinPool internally unless custom executor provided

---

**Summary of Main Components**

1. **Executors** → thread pools, task submission
2. **Locks** → explicit locking mechanisms
3. **Synchronizers** → semaphores, latches, barriers, phasers
4. **Concurrent Collections** → thread-safe data structures
5. **Atomic Variables** → lock-free atomic operations
6. **Fork/Join Framework** → parallel decomposition
7. **CompletableFuture** → async pipelines

These form the backbone of modern concurrent programming in Java.


## Q.32: What is Semaphore in Java concurrency?
**Semaphore** is a concurrency utility (in `java.util.concurrent`) used to **control access to a shared resource** by multiple threads through a **permit-based** mechanism.

---

**Core Idea**

A Semaphore maintains a count of **permits**.

* A thread must **acquire()** a permit before proceeding.
* When done, it must **release()** the permit.
* If no permits are available, the thread **blocks** until one is released.

This allows you to limit how many threads can access a resource *simultaneously*.

---

**Types of Semaphores**

#### **1. Counting Semaphore**

Has *N* permits.

Use cases:

* Limit access to a pool of resources (e.g., DB connections)
* Limit concurrent requests

Example:

```java
Semaphore sem = new Semaphore(3); // 3 permits
```

#### **2. Binary Semaphore** (1 permit)

Works like a mutual exclusion lock (similar to a mutex).

```java
Semaphore sem = new Semaphore(1);
```

---

**Basic Operations**

#### **acquire()**

Blocks until a permit becomes available.

```java
sem.acquire();    // may block
```

#### **tryAcquire()**

Non-blocking attempt.

```java
if (sem.tryAcquire()) {
    // proceed
}
```

#### **release()**

Releases a permit.

```java
sem.release();
```

---

**Fair vs Unfair Semaphores**

#### **Unfair (default)**

No guaranteed ordering. Faster under contentions.

```java
Semaphore sem = new Semaphore(3);
```

#### **Fair semaphore**

First-come-first-served.

```java
Semaphore sem = new Semaphore(3, true);
```

---

**Example: Limiting concurrent access**

```java
Semaphore sem = new Semaphore(2); // only 2 threads allowed

Runnable task = () -> {
    try {
        sem.acquire();
        System.out.println(Thread.currentThread().getName() + " acquired permit");
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        sem.release();
    }
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start(); // this one waits
```

---

**Key Use Cases**

* Limit simultaneous database connections
* Restrict access to a service or API
* Implement bounded resource pools
* Manage rate-limiting
* Build producer–consumer pipelines

---

**Summary**

**Semaphore** = object that controls access using permits.

* `acquire()` → request permit (might block)
* `release()` → free a permit
* Can be binary or counting
* Supports fairness
* Used to manage concurrent access to limited resources

It’s a powerful synchronization tool useful in many concurrency scenarios.


## Q.33: What is Callable and Future in Java concurrency?
**Callable** and **Future** are two tightly connected components of Java’s concurrency API used for asynchronous computation with return values.

---

**1. Callable**

`Callable<V>` represents a **task** that:

* returns a result of type `V`
* can throw checked exceptions
* is executed by an ExecutorService

#### **Method**

```java
V call() throws Exception;
```

#### **Example**

```java
Callable<Integer> task = () -> {
    return 10 + 20;
};
```

Callable is the advanced version of Runnable.

---

**2. Future**

`Future<V>` represents the **result** of an asynchronous computation submitted to an ExecutorService.

It provides methods to:

* retrieve the result
* check if the task is done
* cancel the task
* wait for completion

#### **Key Methods**

```java
V get()                // waits and returns result
V get(timeout)         // waits with timeout
boolean isDone()
boolean cancel(boolean mayInterrupt)
boolean isCancelled()
```

---

**3. Relationship Between Callable and Future**

When you submit a Callable to an ExecutorService:

```java
Future<Integer> future = executor.submit(task);
```

* The ExecutorService runs the Callable in a background thread.
* The result becomes available in the associated Future.

Then you can retrieve the result:

```java
int result = future.get(); // blocks until result is ready
```

---

**4. Example: Using Callable + Future with ExecutorService**

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 42;
};

Future<Integer> future = executor.submit(task);

System.out.println("Doing other work...");
int value = future.get();   // waits for the result
System.out.println(value);

executor.shutdown();
```

---

**When to Use Callable**

* When the task must return a value
* When you need to propagate exceptions
* When you want asynchronous computations with results

---

**Summary**

| Aspect            | Callable              | Future                               |
| ----------------- | --------------------- | ------------------------------------ |
| Role              | Defines a computation | Represents result of the computation |
| Method            | `call()`              | `get()`, `cancel()`                  |
| Returns value     | Yes                   | Stores and provides it               |
| Throws exceptions | Yes                   | Carries them to `get()`              |
| Used with         | ExecutorService       | Returned by ExecutorService          |

**Callable = producing the computation**
**Future = managing and retrieving the result**


## Q.34: What is blocking method in Java?
A **blocking method** is a method that **pauses the calling thread** until a certain condition is met.
During this time, the thread **cannot continue execution** and is placed in a WAITING or BLOCKED state by the JVM.

---

**Characteristics of Blocking Methods**

* The thread **does not run** while blocked.
* It **waits** for a resource, event, or time to pass.
* It **holds CPU zero time** (CPU is free to run other threads).
* Often throws `InterruptedException` because blocking can be interrupted.

---

**Common Blocking Methods in Java**

### **1. Thread-related blocking**

#### **`Thread.sleep(ms)`**

Pauses for a fixed time.

#### **`Thread.join()`**

Waits for another thread to finish.

---

### **2. Object monitor blocking**

#### **`Object.wait()`**

Waits for a `notify()` / `notifyAll()` call.

---

### **3. I/O blocking**

#### **`InputStream.read()`**

Blocks until data arrives.

#### **`Socket.accept()`**

Waits for a client connection.

#### **`BufferedReader.readLine()`**

Waits for input.

I/O is one of the most common sources of blocking.

---

### **4. Concurrency utilities blocking**

#### **`BlockingQueue.take()` / `put()`**

* `take()` waits for element
* `put()` waits for space

#### **`Semaphore.acquire()`**

Waits for a permit.

#### **`Future.get()`**

Waits for async task to complete.

#### **`CountDownLatch.await()`**

Waits for latch to reach zero.

#### **`CyclicBarrier.await()`**

Waits for other threads at the barrier.

---

**Why Blocking Matters**

Blocking:

* Can cause thread starvation
* Affects throughput
* Relates to locking and deadlock issues
* Requires careful interruption handling

Modern designs often prefer **non-blocking algorithms**, **CompletableFuture**, **reactive programming**, and **NIO** to reduce blocking.

---

**Summary**

A **blocking method** is one that makes a thread *wait* for an event, resource, or timeout.
Examples include `sleep()`, `join()`, `wait()`, I/O reads, `Future.get()`, `Semaphore.acquire()`, and `BlockingQueue.take()`.


## Q.35: What is atomic variable in Java?
An **atomic variable** in Java is a variable whose operations are performed **atomically**—meaning **completely**, with **no interference** from other threads—using **lock-free, hardware-level CAS (Compare-And-Swap)** instructions.

Atomic variables are part of the `java.util.concurrent.atomic` package.

---

**Purpose of Atomic Variables**

* Provide **thread-safe updates** to shared variables **without using locks**.
* Avoid the overhead and contention of `synchronized` blocks.
* Enable high-performance concurrent programming.

---

**Common Atomic Classes**

#### **1. Primitive atomics**

* `AtomicInteger`
* `AtomicLong`
* `AtomicBoolean`

#### **2. Reference atomics**

* `AtomicReference<T>`
* `AtomicMarkableReference<T>`
* `AtomicStampedReference<T>` (solves ABA problem)

#### **3. Atomic arrays**

* `AtomicIntegerArray`
* `AtomicLongArray`
* `AtomicReferenceArray<T>`

---

**Key Features**

### **1. Atomic Operations**

Operations like:

* `incrementAndGet()`
* `decrementAndGet()`
* `compareAndSet(expect, update)`
* `getAndAdd(delta)`

These are **atomic**, meaning no thread can observe them halfway.

Example:

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet(); // atomic increment
```

---

### **2. CAS (Compare-And-Swap) based**

Internally uses CPU CAS instructions:

* Compare memory value
* If matches expected, swap
* Otherwise retry

This is extremely fast under low contention.

---

### **3. Visibility Guarantees**

Atomic variables behave like `volatile`:

* Read/write operations always visible across threads
* Prevents stale values

---

### **4. Lock-Free and Non-Blocking**

Unlike `synchronized`, atomic variables:

* Do not block
* Avoid context switching
* Scale better under concurrent load

---

**Example**

```java
AtomicInteger count = new AtomicInteger();

Thread t1 = new Thread(() -> count.incrementAndGet());
Thread t2 = new Thread(() -> count.incrementAndGet());
```

Both threads update `count` safely without locks.

---

**Use Cases**

* Counters
* Sequence generators
* State flags
* Lock-free data structures
* Concurrent updates with minimal contention

---

**Summary**

Atomic variables are **lock-free, thread-safe**, visibility-consistent variables that allow **atomic operations** using hardware-level CAS, outperforming synchronized methods under most concurrent workloads.


## Q.36: What is Executors Framework?
The **Executors Framework** (part of `java.util.concurrent`) is Java’s standardized system for **managing threads**, **executing tasks asynchronously**, and providing **thread pools**. It replaces manual thread creation and simplifies concurrent program design.

---

**Purpose of Executors Framework**

1. Manage thread creation and lifecycle
2. Reuse threads (thread pooling) → better performance
3. Submit tasks asynchronously (`Runnable` / `Callable`)
4. Control concurrency (number of worker threads)
5. Handle results using `Future`
6. Provide scheduling (delayed and periodic tasks)

---

**Core Components**

### **1. Executor (Base Interface)**

The simplest interface:

```java
void execute(Runnable command);
```

Executes a Runnable task.

---

### **2. ExecutorService**

More powerful than Executor:

* Manages thread termination
* Submits tasks returning Future
* Allows shutdown and termination checks

Key methods:

```java
submit(Runnable/Callable)
shutdown()
awaitTermination()
invokeAll()
invokeAny()
```

---

### **3. ScheduledExecutorService**

Supports:

* Delayed tasks
* Repeated / periodic tasks

Example:

```java
scheduler.schedule(() -> {}, 1, TimeUnit.SECONDS);
```

---

### **4. Thread Pools (via Executors Factory Methods)**

#### **`Executors.newFixedThreadPool(n)`**

Fixed number of threads.

#### **`Executors.newCachedThreadPool()`**

Expanding/shrinking pool for short-lived tasks.

#### **`Executors.newSingleThreadExecutor()`**

One thread, tasks executed sequentially.

#### **`Executors.newScheduledThreadPool(n)`**

For scheduled tasks.

---

### **5. ThreadPoolExecutor (Core Implementation)**

The detailed, customizable implementation behind most pools.

Constructor parameters:

* corePoolSize
* maximumPoolSize
* keepAliveTime
* workQueue
* rejectedExecutionHandler
* threadFactory

Use this when you need full control.

---

### **6. Future and Callable**

Used with ExecutorService to return results of async tasks.

```java
Future<Integer> f = executor.submit(callable);
```

---

### **7. ForkJoinPool**

Advanced pool for divide-and-conquer parallelism.

---

**Benefits of the Executors Framework**

#### **1. Better Resource Management**

Reuses a fixed number of threads.

#### **2. Simpler Concurrency Code**

Avoids manual thread creation and joining.

#### **3. Improved Performance**

Thread pooling reduces creation overhead.

#### **4. Enables Async + Parallel Programming**

Provides easy ways to run tasks concurrently.

#### **5. Structured Shutdown**

Graceful and forceful shutdown options.

---

**Example Usage**

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

Future<Integer> result = executor.submit(() -> 42);
System.out.println(result.get());

executor.shutdown();
```

---

**Summary**

The **Executors Framework** standardizes and simplifies:

* asynchronous task execution
* thread pool management
* task scheduling
* concurrent programming patterns

It is the backbone of Java’s modern concurrency model.


## Q.37: What are the available implementations of ExecutorService in the standard library?
The standard Java library provides several **built-in implementations** of `ExecutorService` (and its subinterfaces).
All of them come from **java.util.concurrent**.

---

**1. ThreadPoolExecutor**  *(core implementation)*

The main, fully configurable executor.
All factory methods (`Executors.*`) ultimately create a `ThreadPoolExecutor`.

Key features:

* core/max pool size
* queue type selection
* rejection policies
* keep-alive time
* thread factory

Most commonly used under the hood.

---

**2. ScheduledThreadPoolExecutor**

Implements **ScheduledExecutorService**.

Used for:

* delayed tasks
* repeated (fixed-rate/fixed-delay) tasks

Created via:

```java
Executors.newScheduledThreadPool(n);
```

---

**3. ForkJoinPool**

Implements **ExecutorService** and **ForkJoinPool.ForkJoinWorkerThreadFactory**.

Used for:

* parallelism
* divide-and-conquer tasks (`RecursiveAction`, `RecursiveTask`)
* `parallelStream()` and `CompletableFuture` (common pool)

---

**4. Executors Framework Factory-Based Executors**

These **return ExecutorService instances** internally backed by ThreadPoolExecutor:

#### **a. FixedThreadPool**

```java
Executors.newFixedThreadPool(n); 
```

Backed by a `ThreadPoolExecutor` with:

* fixed core = max threads
* unbounded queue

#### **b. CachedThreadPool**

```java
Executors.newCachedThreadPool();
```

Backed by a `ThreadPoolExecutor` with:

* 0 core threads
* unbounded max threads
* 60s keep-alive
* direct handoff queue (SynchronousQueue)

#### **c. SingleThreadExecutor**

```java
Executors.newSingleThreadExecutor();
```

Backed by a `ThreadPoolExecutor` with:

* core = 1
* max = 1

Ensures sequential task execution.

#### **d. SingleThreadScheduledExecutor**

```java
Executors.newSingleThreadScheduledExecutor();
```

Backed by a `ScheduledThreadPoolExecutor` with 1 thread.

#### **e. WorkStealingPool** (Java 8+)

```java
Executors.newWorkStealingPool();
```

Backed by a `ForkJoinPool` using work-stealing.

---

**5. Delegated Executors**

The `Executors` factory methods sometimes wrap a ThreadPoolExecutor in wrapper classes that restrict functionality:

* `FinalizableDelegatedExecutorService`
* `DelegatedExecutorService`
* `DelegatedScheduledExecutorService`

These are **not public** classes but are part of the library (used internally).

---

**Summary Table**

| Implementation                       | Interface                | Notes                        |
| ------------------------------------ | ------------------------ | ---------------------------- |
| **ThreadPoolExecutor**               | ExecutorService          | Full control; most flexible  |
| **ScheduledThreadPoolExecutor**      | ScheduledExecutorService | For scheduled tasks          |
| **ForkJoinPool**                     | ExecutorService          | Parallelism / work stealing  |
| **newFixedThreadPool**               | ExecutorService          | Backed by ThreadPoolExecutor |
| **newCachedThreadPool**              | ExecutorService          | Thread scaling               |
| **newSingleThreadExecutor**          | ExecutorService          | One worker thread            |
| **newSingleThreadScheduledExecutor** | ScheduledExecutorService | One scheduled worker         |
| **newWorkStealingPool**              | ExecutorService          | Backed by ForkJoinPool       |

These are the main executor implementations available in Java’s standard library.


## Q.38: What kind of thread is the Garbage collector thread?
The **Garbage Collector (GC) thread** in Java is a **daemon thread**.

---

**Why GC is a Daemon Thread**

* Daemon threads provide **background services** for user threads.
* The JVM **does not wait** for daemon threads to complete before shutting down.
* GC is not essential once all user threads have finished, so it can be terminated automatically.

---

**Characteristics of GC Thread**

#### **1. Daemon thread**

Runs in the background.

#### **2. Managed entirely by the JVM**

You cannot start, stop, or modify GC threads manually.

#### **3. Priority may vary**

GC threads often run at a higher priority because memory management is crucial.

#### **4. May consist of multiple threads**

Modern garbage collectors (G1, Parallel GC, ZGC, Shenandoah) use:

* Parallel GC threads
* Concurrent marking threads
* Background sweeper threads

All of these are **daemon threads**.

---

**Summary**

The garbage collector runs as a **background daemon thread** (or multiple daemon threads), and the JVM can exit without waiting for them once all user threads have terminated.


## Q.39: How can we pause the execution of a Thread for specific time?
Use **`Thread.sleep()`** to pause a thread for a specific time.

---

**1. Using Thread.sleep()**

```java
Thread.sleep(milliseconds);
```

or with nanoseconds:

```java
Thread.sleep(millis, nanos);
```

This pauses the **current thread** for at least the specified duration.

Example:

```java
Thread.sleep(2000); // pause for 2 seconds
```

---

**2. Behavior Details**

* The thread enters **TIMED_WAITING** state.
* Does **not** release any monitor lock it holds.
* May wake up earlier if interrupted.
* Throws `InterruptedException` if the thread is interrupted while sleeping.

---

**3. Correct Usage**

Always use try/catch:

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

---

**Alternative (Higher-level API): ScheduledExecutorService**

If periodic or delayed tasks are needed:

```java
ScheduledExecutorService scheduler =
    Executors.newScheduledThreadPool(1);

scheduler.schedule(() -> {
    System.out.println("executed");
}, 2, TimeUnit.SECONDS);
```

---

**Summary**

To pause a thread for a specific time, use `Thread.sleep()` (the standard mechanism).


## Q.40: What is difference between Executor.submit() and Executer.execute() method?
**Difference Between `execute()` and `submit()`**

| Feature                 | `execute(Runnable)`                           | `submit(Runnable/Callable)`      |
| ----------------------- | --------------------------------------------- | -------------------------------- |
| Returns a result        | No                                            | Yes (`Future`)                   |
| Can submit Callable     | No                                            | Yes                              |
| Exception handling      | Exception thrown to thread’s uncaught handler | Exception captured inside Future |
| Return type             | `void`                                        | `Future<?>`                      |
| Know when task finishes | No                                            | Yes (using `Future.isDone()`)    |
| Retrieve result         | No                                            | Yes (`Future.get()`)             |
| Cancel task             | No                                            | Yes (`Future.cancel()`)          |

---

**1. execute() — Fire-and-forget**

#### Signature:

```java
void execute(Runnable command);
```

* Used for simple task submission.
* No return value or status tracking.
* If the task throws an exception:

  * It goes to the **thread’s uncaught exception handler**.

#### Example:

```java
executor.execute(() -> {
    System.out.println("task running");
});
```

---

**2. submit() — Asynchronous task with Future**

#### Signatures:

```java
Future<?> submit(Runnable task);
<T> Future<T> submit(Callable<T> task);
```

* Accepts both **Runnable** and **Callable**.
* Returns a **Future** to manage the result.
* Exceptions do not propagate; they get stored in the Future.

#### Example with Callable:

```java
Future<Integer> f = executor.submit(() -> 42);
System.out.println(f.get());   // prints 42
```

#### Exception behavior:

```java
Future<?> f = executor.submit(() -> { throw new RuntimeException(); });
f.get(); // throws ExecutionException, not the original exception
```

---

**When to Use What**

#### **Use `execute()` when:**

* You don’t care about result.
* Simple runnable tasks.
* Fire-and-forget logic.

#### **Use `submit()` when:**

* You need a return value.
* You want to handle exceptions through Future.
* You want to cancel or track progress.
* You are using `Callable`.

---

**Summary**

`execute()` = fire-and-forget, no result, no tracking.
`submit()` = asynchronous task with `Future`, captures result and exceptions.


## Q.41: What is Phaser in Java concurrency?
**Phaser** is an advanced synchronization barrier in `java.util.concurrent` used to coordinate threads across **multiple phases** of work.
It is more flexible and powerful than `CountDownLatch` and `CyclicBarrier`.

---

**Purpose of Phaser**

To allow a set of threads (parties) to:

* Wait for each other at **multiple synchronization points** (phases)
* Dynamically **register** or **deregister** during execution
* Handle variable numbers of participants

Phaser supports coordination of *multi-step* workflows.

---

**Key Concepts**

### **1. Phases**

A phaser works in **phases**.
Each time all parties arrive, the phaser:

* Advances to next phase
* Releases waiting threads

Think of it as “round-based synchronization.”

---

### **2. Parties**

The number of registered participants (threads) cooperating.

You can:

* `register()` new parties
* `arriveAndDeregister()` remove parties

This makes Phaser **dynamic**, unlike CountDownLatch/CyclicBarrier.

---

**Core Methods**

#### **arrive()**

Marks arrival but doesn’t wait.

#### **arriveAndAwaitAdvance()**

Arrives and waits for others to arrive (like barrier wait).

#### **arriveAndDeregister()**

Arrive and reduce number of parties.

#### **register()**

Add a new party dynamically.

#### **getPhase()**

Returns current phase number.

---

**Simple Example**

```java
Phaser phaser = new Phaser(3); // 3 parties

Runnable task = () -> {
    System.out.println(Thread.currentThread().getName() + " phase 0");
    phaser.arriveAndAwaitAdvance();

    System.out.println(Thread.currentThread().getName() + " phase 1");
    phaser.arriveAndAwaitAdvance();
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();
```

Execution:

```
Thread-0 phase 0
Thread-1 phase 0
Thread-2 phase 0
(all wait)
--- advance to phase 1 ---
Thread-0 phase 1
Thread-1 phase 1
Thread-2 phase 1
```

---

**Key Advantages Over Other Synchronizers**

### **1. Compared to CountDownLatch**

* CountDownLatch is **one-shot**, cannot reset.
* Phaser is **multi-phase**, reusable.

### **2. Compared to CyclicBarrier**

* CyclicBarrier has a **fixed** number of parties.
* Phaser allows **dynamic** registration and deregistration.

### **3. More granular control**

* Separate arrival and waiting steps (`arrive()`, `awaitAdvance()`)

---

**Common Use Cases**

* Multi-step workflows with multiple synchronization points
* Variable thread participation
* Staged processing in pipelines
* Parallel algorithms with dynamic worker counts

---

**Summary**

Phaser is a **multi-phase, dynamic synchronization barrier** that allows threads to repeatedly wait for each other across multiple steps of execution, with the ability to add or remove participants at any time.



## Q.42: How to stop a Thread in Java?
**Threads in Java must be stopped *cooperatively*.**
You **cannot** force-stop a thread safely.

The two correct ways:

---

**1. Interrupt the thread (preferred)**

Use **`interrupt()`** and make the thread check its interruption status.

#### **Thread logic**

```java
class Worker implements Runnable {
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // work
                Thread.sleep(100);   // blocking operations react to interrupt
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore flag
        }
    }
}
```

#### **Stopping**

```java
Thread t = new Thread(new Worker());
t.start();
t.interrupt();  // requests stop
```

* If thread is sleeping/waiting → `InterruptedException` is thrown.
* If thread is running → loop sees interrupt flag and exits.

---

**2. Use a `volatile boolean` flag (for non-blocking work)**

```java
class Worker implements Runnable {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // work
        }
    }

    public void stop() {
        running = false;
    }
}
```

This works only if the thread is not inside a blocking call.

---

**Methods to NEVER use**

#### **`Thread.stop()` — deprecated and dangerous**

* Kills the thread immediately.
* Leaves shared objects in inconsistent states.

#### **`Thread.suspend()` / `resume()` — deprecated**

* Causes deadlocks because suspend can freeze a thread holding locks.

---

**Summary**

* **Use `interrupt()`** → correct for blocking tasks.
* **Use `volatile` flag** → correct for CPU-bound loops.
* Never force-stop a thread using deprecated methods.

This is the only safe and recommended way to stop a thread in Java.


## Q.43: Why implementing Runnable is better than extending thread?
**Key reasons why implementing `Runnable` is better than extending `Thread`:**

---

**1. Java supports only single inheritance**

If you extend `Thread`, you cannot extend any other class.

```java
class MyTask extends Thread { }   // cannot extend anything else
```

If you implement `Runnable`, your class stays free to extend another class.

```java
class MyTask extends SomeBaseClass implements Runnable { }
```

This is the single biggest reason.

---

**2. Task (Runnable) is separated from Thread (execution mechanism)**

* `Runnable` = **what** to run
* `Thread`     = **how** to run

Separation gives cleaner design and better reusability.

Example:

```java
Runnable task = new MyTask();
new Thread(task).start();
```

---

**3. Runnable works with ExecutorService / Thread pools**

Thread pools **cannot** execute classes that extend `Thread`.

ExecutorService accepts only:

* `Runnable`
* `Callable`

```java
executor.submit(new MyRunnable());
```

If you extend `Thread`, you cannot submit it properly to a pool.

---

**4. Better reuse of task objects**

A single Runnable can be executed by multiple threads.

```java
Runnable r = new Job();
new Thread(r).start();
new Thread(r).start();
```

If you extend Thread, the logic is tied to the thread instance and cannot be reused.

---

**5. Prevents mixing responsibilities**

Extending `Thread` mixes:

* business logic (`run()`)
* threading logic (Thread lifecycle)

Implementing `Runnable` keeps logic focused and testable.

---

**6. More flexible and standard approach**

Most of the Java concurrency API (Executors, ScheduledExecutorService, ForkJoinPool, CompletableFuture) expects a Runnable/Callable, not a Thread subclass.

---

**7. Extending Thread wastes resources**

When you extend Thread, each instance *is itself a Thread object*, even if you only care about the `run()` logic.

Runnable is lighter.

---

**Summary Table**

| Reason                             | Runnable | Thread (extends) |
| ---------------------------------- | -------- | ---------------- |
| Single inheritance                 | ✔        | ✘                |
| Clean separation of task vs thread | ✔        | ✘                |
| Works with ExecutorService         | ✔        | ✘                |
| Reusable task objects              | ✔        | ✘                |
| Cleaner design                     | ✔        | ✘                |
| Lightweight                        | ✔        | ✘                |

---

**Conclusion:**
Implementing **Runnable** is preferred because it is flexible, reusable, works with thread pools, avoids inheritance limitations, and follows clean design principles.


## Q.44: Tell me about join() and wait() methods?
**join()** and **wait()** both cause a thread to pause, but they are used for completely different purposes and operate on different mechanisms.

---

**join()**

### **Purpose**

Wait for **another thread** to finish.

### **Defined In**

`java.lang.Thread`

### **How it works**

When you call:

```java
t.join();
```

the **current thread** waits until thread **t** completes (reaches TERMINATED state).

### **Characteristics**

* Does **not** require synchronization.
* Internally uses `wait()` on the thread object.
* Throws `InterruptedException`.
* Used for sequencing threads.

### **Example**

```java
Thread t = new Thread(() -> {
    doWork();
});

t.start();
t.join();    // main waits for t to finish
```

---

**wait()**

### **Purpose**

Used for **thread communication** via monitor locks (object-level coordination).

### **Defined In**

`java.lang.Object` (because every object is a monitor)

### **How it works**

```java
synchronized(obj) {
    obj.wait();
}
```

* The thread **releases the monitor lock** on `obj`.
* Enters the **WAITING** state.
* Stays waiting until `notify()` or `notifyAll()` (or interrupt) wakes it.

### **Characteristics**

* Must be called inside a `synchronized` block/method.
* Releases the lock while waiting.
* Used for producer–consumer patterns.
* Throws `InterruptedException`.

### **Example**

```java
synchronized(obj) {
    while (!condition) {
        obj.wait();
    }
}
```

---

**Key Differences**

| Aspect                      | `join()`                              | `wait()`                                 |
| --------------------------- | ------------------------------------- | ---------------------------------------- |
| Purpose                     | Wait for a thread to finish           | Thread communication / condition waiting |
| Defined in                  | `Thread`                              | `Object`                                 |
| Requires synchronized block | No                                    | Yes                                      |
| Releases lock               | No                                    | Yes                                      |
| Wakes up by                 | Thread completes                      | `notify()` / `notifyAll()`               |
| Who calls on whom           | One thread waits for *another thread* | A thread waits on *a monitor object*     |

---

**Summary**

* **join()** → “Wait until this thread finishes.”
* **wait()** → “Release the lock and wait until condition changes.”

They both pause threads, but their **roles, usage rules, and underlying mechanisms are completely different**.


## Q.45: How to implement thread-safe code without using the synchronized keyword?
Thread-safe code can be implemented **without `synchronized`** by using higher-level or lock-free concurrency mechanisms. These avoid manual locking while still guaranteeing visibility and atomicity.

---

**1. Atomic Variables (Lock-Free, CAS-Based)**

Use classes from `java.util.concurrent.atomic`.

Examples:

```java
AtomicInteger count = new AtomicInteger();

count.incrementAndGet();
count.compareAndSet(5, 10);
```

Guarantees:

* Atomic operations
* Visibility
* No blocking

---

**2. Explicit Locks (`ReentrantLock`, `ReadWriteLock`)**

Use the `Lock` API instead of `synchronized`.

Example:

```java
ReentrantLock lock = new ReentrantLock();

lock.lock();
try {
    // thread-safe section
} finally {
    lock.unlock();
}
```

Features:

* tryLock
* timed lock
* interruptible lock
* fair locking

---

**3. Thread-Safe Collections**

Use concurrent data structures that handle synchronization internally:

* `ConcurrentHashMap`
* `CopyOnWriteArrayList`
* `ConcurrentLinkedQueue`
* `BlockingQueue` (ArrayBlockingQueue, LinkedBlockingQueue)
* `ConcurrentSkipListMap`, etc.

Example:

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
```

No need for explicit locking.

---

**4. Immutable Objects**

Make objects immutable so they cannot be modified after creation.

Example:

```java
final class Point {
    private final int x, y;
}
```

Immutable objects are inherently thread-safe.

---

**5. Thread Confinement**

Keep data confined to a single thread.

Example using local variables:

```java
void process() {
    int localCount = 0; // confined to one thread
}
```

Nothing is shared → no synchronization needed.

---

**6. ThreadLocal Storage**

Each thread gets its **own copy** of a variable.

```java
ThreadLocal<SimpleDateFormat> df =
        ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
```

Eliminates shared state.

---

**7. Using `volatile` for Visibility (not atomic)**

Ensures writes are visible to other threads.

```java
volatile boolean flag = false;
```

Use volatile for:

* state flags
* one-way publication
* read-mostly variables

Not suitable for compound operations like `count++`.

---

**8. Higher-Level Synchronizers**

Use concurrency utilities instead of manual locks.

Examples:

* `Semaphore`
* `CountDownLatch`
* `CyclicBarrier`
* `Phaser`
* `Exchanger`

These enforce safe coordination without `synchronized`.

---

**9. Using Executors / Thread Pools**

Avoid manual thread sharing; isolate tasks.

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
pool.submit(task);
```

Thread pools help remove shared state and reduce race conditions.

---

**Summary**

Thread-safe code without `synchronized` can be achieved via:

1. **Atomic variables**
2. **ReentrantLock / ReadWriteLock**
3. **Concurrent collections**
4. **Immutable objects**
5. **Thread confinement**
6. **ThreadLocal**
7. **volatile**
8. **Synchronization utilities (Semaphore, Latch, Barrier)**
9. **ExecutorService-based task design**

These mechanisms provide safety, often with better performance and flexibility than raw `synchronized`.