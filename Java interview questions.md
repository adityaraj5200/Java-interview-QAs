## Core Java

## Q.1: What is the JDK, JRE, and JVM?
* **JVM (Java Virtual Machine):**
  * Abstract machine that runs Java bytecode.
  * Provides platform independence by converting bytecode into machine code (via interpreter or JIT compiler).
  * Handles tasks like class loading, bytecode verification, memory management (heap, stack, garbage collection), and execution of code.
  * JVM is platform-dependent (different implementations for Windows, Linux, etc.), but bytecode is platform-independent.

* **JRE (Java Runtime Environment):**
  * Provides the libraries, JVM, and supporting files required to run Java programs.
  * Contains core class libraries (like java.lang, java.util, etc.) and other runtime components.
  * Does **not** include development tools such as compiler (`javac`) or debugger.
  * Suitable for users who only want to run Java applications, not develop them.

* **JDK (Java Development Kit):**
  * Complete package for Java developers.
  * Includes JRE + development tools (like `javac`, `javadoc`, `jdb`, etc.).
  * Provides everything needed to develop, compile, debug, and run Java applications.
  * Comes in different editions (Standard Edition, Enterprise Edition, Micro Edition).


## Q.2: Is Java pass-by-value or pass-by-reference?
- Java is strictly pass-by-value. For object references, the reference itself is passed by value (i.e., a copy of the reference). You can mutate the object via the copied reference, but you cannot rebind the caller’s reference.

```java
class Box { int n; }
void mutate(Box b) { b.n = 42; }      // affects caller
void rebind(Box b) { b = new Box(); } // does not affect caller
```

## Q.3: `==` vs `.equals()`
- `==`: Reference equality for objects; value equality for primitives.
- `.equals()`: Value equality; by default inherits `Object.equals` (reference equality) unless overridden.
- If you override `equals`, you must also override `hashCode` to keep the general contract.

```java
record Point(int x, int y) { } // Java 16+, auto equals/hashCode
Point a = new Point(1, 2);
Point b = new Point(1, 2);
System.out.println(a == b);        // false (different objects)
System.out.println(a.equals(b));   // true (structural equality)
```

## Q.4: What is immutability? Why is `String` immutable?
- Immutable objects cannot change state after construction, enabling thread-safety, caching, and safe sharing.

- String is immutable because of:

  * **Security:** Strings are often used in sensitive operations like database connections, file paths, and network connections. If strings were mutable, malicious code could change their values after creation, leading to security risks.

  * **Caching and String Pool:** The JVM maintains a String pool to reuse string objects. Immutability ensures that once a string is created and stored in the pool, it cannot be changed. This saves memory and improves performance.

  * **Thread-safety:** Since strings cannot be modified, multiple threads can share the same string instance without synchronization. This makes them inherently thread-safe.

  * **Hashcode Consistency:** Strings are frequently used as keys in hash-based collections (like `HashMap`). If strings were mutable, their hashcode could change, breaking the contract of `Map` and causing retrieval issues.

  * **Performance optimization:** Operations like substring, concatenation, and intern rely on immutability for correctness and efficiency.

## Q.5: What is string interning?

- String interning in Java refers to the optimization technique where the JVM stores only one copy of a given string literal in memory, regardless of how many times that exact string appears in the source code. This means that if two string literals have the same value, they will refer to the same object instance in memory.

```java
String a = "hello";
String b = "hello";
System.out.println(a == b); // true (pooled)
String c = new String("hello");
System.out.println(a == c); // false (new object)
```


## Q.6: Explain the four pillars of OOP
- Encapsulation: Binding data (fields/variables) and the methods (functions) that operate on that data into a single unit (class), and restricting direct access to some of the object's components.
It's mainly achieved by:
  1. Declaring fields as private (data hiding)
  2. Providing public getter and setter methods to control access.
- Abstraction: Expose only necessary behavior, possibly via interfaces/abstract classes.
- Inheritance: Inheritance is the mechanism by which a new class can inherit properties (attributes and behaviors) from an existing class, creating a parent-child relationship between classes, allowing for code reuse and the creation of more specialized classes that extend or modify the behavior of their parents.
- Polymorphism: Polymorphism is the ability of an object to take on multiple forms during runtime. This enables objects to exhibit different behaviors based on their specific implementation

```java
interface Payment { void pay(double amount); }
class CardPayment implements Payment { public void pay(double amount) { /* ... */ } }
class UpiPayment implements Payment { public void pay(double amount) { /* ... */ } }
void checkout(Payment p) { p.pay(100.0); } // polymorphism
```

## Q.7: Abstract class vs interface
* **Abstract Class**

  * Cannot be instantiated.
  * Can have both **abstract methods** (no body) and **concrete methods** (with implementation).
  * Can have **instance variables**, constructors, and static methods.
  * Supports **single inheritance** (a class can extend only one abstract class).
  * Useful when classes share a common base with some default behavior.

* **Interface**

  * Specifies a set of methods a class must implement.
  * Contains only **abstract methods** (until Java 7). Since Java 8, can have **default** and **static methods**, and since Java 9, **private methods**.
  * Cannot have instance variables (only `public static final` constants).
  * A class can implement **multiple interfaces** (supports multiple inheritance of type).
  * Used to define a **contract** that multiple classes can implement.


## Q.8: `ArrayList` vs `LinkedList`
- `ArrayList`: Backed by dynamic array; O(1) random access; amortized O(1) append; inserting/removing in middle is O(n).
- `LinkedList`: Doubly-linked list; O(1) add/remove at ends; random access is O(n); higher memory overhead.
- Prefer `ArrayList` unless frequent mid-list insertions/removals dominate and access patterns justify `LinkedList`.

```java
List<Integer> a = new ArrayList<>();
a.add(1); a.add(2);
List<Integer> l = new LinkedList<>();
l.addFirst(0);
```

## Q.9: How does `HashMap` work?
* `HashMap` stores data in **key-value pairs**.
* Internally, it uses an **array of buckets**, where each bucket is a linked list (or a balanced tree since Java 8 if collisions are high).
* When you put a key-value pair:

  * The **hashCode()** of the key is computed.
  * The hash is mapped to an index in the array (`index = hash % capacity`).
  * If the bucket at that index is empty → store the entry.
  * If not empty → handle **collision** using chaining (linked list) or tree (if too many collisions).
* When you get a value:

  * The key’s hashCode is computed again.
  * The correct bucket index is found.
  * It traverses the bucket, checking keys with **equals()** to find the right entry.
* Load factor (default **0.75**) determines when to resize the HashMap. Once threshold = capacity × load factor is reached, the HashMap **doubles its size** and rehashes all keys.
* `hashCode()` ensures fast bucket lookup, while `equals()` ensures key uniqueness.


## Q.10: Why are `equals()` and `hashCode()` important?
- Proper `hashCode` and `equals` ensure keys distribute well and equality works.
`equals()` determines if two objects are equal based on their content or state, while `hashCode()` calculates a unique integer value for an object to help efficiently store it in a hash-based data structure like HashMap or HashSet. Both methods play essential roles in maintaining the correct behavior of collections and consistent object comparison within Java applications.


```java
class User {
  final String id;
  User(String id) { this.id = id; }
  @Override public boolean equals(Object o) { return o instanceof User u && id.equals(u.id); }
  @Override public int hashCode() { return id.hashCode(); }
}
```

## Q.11: Ordering differences: `HashMap` vs `LinkedHashMap` vs `TreeMap`
- `HashMap`: No ordering guarantees.
- `TreeMap`: Sorted by natural order or a provided `Comparator`.
- `LinkedHashMap`: Predictable iteration order (insertion or access order).

```java
Map<String,Integer> lm = new LinkedHashMap<>();
Map<String,Integer> tm = new TreeMap<>(Comparator.reverseOrder());
```

---

## Q.12: What are Generics in java?

Generics allows us to write code that works with different data types using a single class, interface or method. Instead of creating separate versions for each type, we use type parameters (like `<T>`) to make the code reusable and type-safe.

* **Purpose / Why used:**

  * **Type safety:** Errors are caught at compile-time rather than runtime.
  * **Code reusability:** Same class or method can work with multiple data types.
  * **Eliminates explicit casting:** No need to cast objects when retrieving from collections.
  * **Improves readability:** Makes the intended type clear to developers.

---

Example: Generic Class:

```java
class Box<T> {
    private T value;
    public void set(T value){ this.value=value; }
    public T get(){ return value; }
}

public class Demo {
    public static void main(String[] args) {
        Box<Integer> intBox=new Box<>();
        intBox.set(10);
        System.out.println(intBox.get()); // 10

        Box<String> strBox=new Box<>();
        strBox.set("Hello");
        System.out.println(strBox.get()); // Hello
    }
}
```

Generics make the `Box` class **reusable for any type** while ensuring **type safety**.


## Q.13: Difference between `Runnable` vs `Callable`?
In Java, both Runnable and Callable are interfaces used for concurrent programming to execute tasks asynchronously. However, there are some key differences between them:

1. **Runnable**: The Runnable interface is the simplest way of creating a new thread in Java. It defines a single method called `run()` that should be implemented by users to specify what the thread does when it starts executing. There is no return type for the run() method, and exceptions thrown from within the run() method are unchecked.
2. **Callable**: The Callable interface extends Runnable and provides additional functionality by allowing a task to return a result value and throw checked exceptions. It defines two methods: `call()`, which performs the main task, and `get()`, which retrieves the result value from the completed future task object. Unlike Runnable, Callable allows for returning a value or throwing checked exceptions, which can be handled by calling the get() method on the Future object returned by an ExecutorService.

Here's a comparison table for better understanding:

|                   | `Runnable`            | `Callable`           |
|-------------------|-----------------------|----------------------|
| Interface         | Yes, extends Object   | Yes, extends Runnable |
| Run method        | Yes (void)            | Yes (returns T)       |
| Return type       | No return type         | Returns a value of type T |
| Exceptions thrown | Unchecked exceptions   | Checked exceptions    |
| Result retrieval  | Not possible           | Retrievable via Future.get() |

In summary, if you need to execute a task without returning any result or throwing checked exceptions, Runnable is the best choice. If your task requires returning a value or throwing checked exceptions, use Callable and retrieve the result using Future objects in combination with an ExecutorService.

- Prefer using `ExecutorService` to manage threads.

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
Future<Integer> f = pool.submit(() -> 40 + 2);
System.out.println(f.get());
pool.shutdown();
```

## Difference between `start()` vs `run()`?
- `run()`: The task’s logic; calling it directly runs in the current thread.
- `start()`: Creates a new OS thread that then calls `run()` asynchronously.

```java
new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
```

## Q.14: What is synchronized, intrinsic locks, and reentrancy?
- Methods/blocks marked `synchronized` acquire the intrinsic lock of the object/class.

  An intrinsic lock (or monitor lock) is a built-in synchronization mechanism associated with each object instance in Java. When a thread acquires an intrinsic lock of an object, it prevents other threads from acquiring the lock until it releases it.
  ```java
  class Counter {
    private int count;
    public synchronized void inc() { count++; }
    public synchronized int get() { return count; }
  }
  ```
- Reentrancy: Reentrancy occurs when a thread holding a lock on an object re-enters that same object's synchronized method or block. In Java, synchronized blocks and methods support reentrancy by design. This means that if a thread already holds the intrinsic lock of an object and then enters another synchronized block or method within the same object, it will be able to acquire the lock again without causing deadlocks.
  
  Here's a simple example demonstrating reentrancy in Java:
  ```java
  public class ReentrantObject {
    public void doWork() {
        synchronized(this) {
            System.out.println("Acquired lock");
            // critical section code here
            otherMethod();
        }
    }

    public void otherMethod() {
        synchronized(this) {
            System.out.println("Re-entered the lock");
            // more critical section code here
        }
    }
  }
  ```


- Synchronization provides mutual exclusion and establishes happens-before relationships for visibility.


## Q.15: What does `volatile` do?
- Ensures visibility and ordering for a single variable: reads see the latest write; prevents reordering across volatile boundaries.
- Does not provide atomicity for compound actions (e.g., `x++`).

```java
volatile boolean running = true;
void stop() { running = false; } // other thread will see it
```

## Q.16: `wait()/notify()` vs `Lock`/`Condition`?

1. `wait()`: The `wait()` method allows a thread to voluntarily relinquish the object's lock and enter a waiting state until it is notified by another thread. When a thread calls the `wait()` method, it releases the lock on the object, allowing other threads to access that object.

2. `notify()`: The `notify()` method wakes up one of the waiting threads that are currently queued on an object due to calling `wait()`. When a thread calls `notify()`, it does not determine which specific waiting thread should be awakened, but one of them is chosen arbitrarily by the JVM.

3. `notifyAll()`: The `notifyAll()` method wakes up all the threads that are currently queued on an object due to calling `wait()`. When a thread calls `notifyAll()`, it notifies all waiting threads and allows them to compete for the lock again.

These methods are often used in situations where multiple threads need to coordinate their actions, such as when working with shared resources or data structures that require specific states before other operations can be performed. It's important to note that `wait()`, `notify()`, and `notifyAll()` should only be called from inside a synchronized block or method to ensure proper access control to the shared object being waited upon.

The main difference between `notify()` and `notifyAll()` lies in the number of threads that will be awakened: using `notify()` wakes up one thread, while `notifyAll()` wakes up all waiting threads. This means that if there are multiple threads waiting on an object when `notifyAll()` is called, they will all be notified and compete for the lock again, whereas with `notify()`, only one thread will be awakened at a time.

```java
Lock lock = new ReentrantLock();
Condition notEmpty = lock.newCondition();
Queue<Integer> q = new ArrayDeque<>();

void put(int x) {
  lock.lock();
  try { q.add(x); notEmpty.signal(); }
  finally { lock.unlock(); }
}

int take() throws InterruptedException {
  lock.lock();
  try {
    while (q.isEmpty()) notEmpty.await();
    return q.remove();
  } finally { lock.unlock(); }
}
```

### Deadlock, livelock, starvation
In concurrent programming, there are three main issues related to synchronization:

1. **Deadlock**: A deadlock occurs when two or more threads are unable to proceed because each thread is waiting for a resource that the other thread holds. The resulting situation leads to both threads being blocked indefinitely, causing a standstill in the program. To avoid deadlocks, it's essential to follow best practices such as ordering locks, avoiding circular waits, and implementing timeouts.

2. **Livelock**: Livelock is a situation where two or more threads are continuously trying to change each other's state but never making progress because the changes made by one thread invalidate the assumptions of the other thread before it can make any meaningful progress. Unlike deadlock, livelock does not involve waiting for resources but rather continuous communication between threads that results in no net forward progress. To avoid livelock, try to design your algorithms and data structures to prevent circular dependencies and ensure that changes made by one thread can be reasonably expected to persist long enough for the other thread to make some progress.

3. **Starvation**: Starvation occurs when a thread is repeatedly denied access to a shared resource or processor time, causing it to be deprived of resources for an extended period. This results in the thread never making any meaningful progress while other threads continue running. To avoid starvation, implement fair scheduling policies and ensure that each thread gets a chance to execute within a reasonable timeframe. Priority-based scheduling or implementing self-correcting algorithms can help prevent starvation in multithreaded applications.

By understanding these issues and applying good design principles, developers can create more robust and efficient concurrent programs that avoid deadlocks, livelock, and starvation scenarios.


## Q.17: Stack vs heap memory?
In Java, both `stack` and `heap` are used to store data structures, but they serve different purposes and have distinct properties:

**Stack Memory**:
- The stack is a last-in, first-out (LIFO) data structure that stores method frames during the execution of a program.
- Each thread has its own stack, with the JVM managing the memory allocation dynamically as methods are invoked and returned.
- Stack variables have automatic scope, meaning they exist only within the method in which they were declared.
- Memory on the stack is managed efficiently by the JVM, eliminating the need for garbage collection since local variables are automatically deallocated when their corresponding method returns.

**Heap Memory**:
- The heap is a large memory area where Java objects (instances of classes) are stored and dynamically allocated during runtime.
- All threads in the program share the same heap, allowing them to access and manipulate shared objects.
- Objects on the heap have manual scope, meaning they can exist for an extended period as long as they're referenced by other objects or variables.
- Memory on the heap is managed by the garbage collector (GC), which automatically deallocates unreferenced objects to free up memory when needed.

In summary:
- The stack stores method frames, and it is used for local variables with automatic scope. Stack memory is efficient but has a fixed size per thread and is limited.
- The heap stores Java objects and provides manual scope, allowing for dynamic allocation of objects during runtime. Heap memory is more flexible and can grow or shrink as needed but requires the garbage collector to manage memory efficiently.


---

## What is Garbage Collection (GC)?

In Java, the Garbage Collector (GC) is a built-in component responsible for automatically managing memory allocation and deallocation of objects that are no longer needed by the program. The primary goal of garbage collection is to free up resources occupied by unused objects, prevent memory leaks, and minimize the impact on application performance due to fragmented memory.

The Java garbage collector operates in two main modes:

1. **Serial GC**: A single-threaded garbage collector designed for applications with limited memory requirements and simple workloads. Serial GC is simple to understand but can be slower during garbage collection compared to other collectors.

2. **Parallel GC**: A multi-threaded garbage collector that runs concurrently with the application, allowing it to collect garbage faster than the serial collector without significantly impacting the application's performance. Parallel GC is suitable for applications with more demanding memory requirements and complex workloads.

3. **Concurrent Mark Sweep (CMS)**: A parallel, concurrent low-pause-time garbage collector that runs in two phases: marking (scanning the heap to identify live objects) and sweeping (deallocating unreferenced objects). CMS is suitable for applications where minimizing pauses during garbage collection is crucial.

4. **G1 Garbage Collector**: A next-generation, concurrent collector designed to minimize application pauses while achieving low memory occupancy. G1 divides the heap into equal-sized regions and performs parallel garbage collection by processing multiple regions simultaneously. It also allows for more flexible heap size configuration and better scalability.


## Q.18:  What are Exceptions in Java? Checked vs unchecked exceptions?

An **exception** is an event that disrupts the normal flow of a program (e.g., dividing by zero, accessing an invalid index, missing file, etc.).

Java classifies exceptions into two types:

* **Checked Exceptions**
* **Unchecked Exceptions**

**1. Checked Exceptions**

* Checked at **compile-time**.
* The compiler forces you to **handle them** (either with `try-catch` or by declaring `throws`).
* Typically represent **recoverable conditions** (like missing files, network issues, database errors).

Examples:

* `IOException`
* `SQLException`
* `FileNotFoundException`
* `ClassNotFoundException`

Example Code:

```java
import java.io.*;

class CheckedExample {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("file.txt"); // may throw FileNotFoundException
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
```

If you don’t handle it, **compiler error** will occur.

---

**2. Unchecked Exceptions**

* Checked at **runtime** (not at compile-time).
* The compiler does **not force** you to handle them.
* Usually caused by **programming errors** (invalid logic, wrong data input).
* Typically represent **non-recoverable conditions**.

Examples:

* `NullPointerException`
* `ArrayIndexOutOfBoundsException`
* `ArithmeticException` (divide by zero)
* `IllegalArgumentException`

Example Code:

```java
class UncheckedExample {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        System.out.println(arr[5]); // ArrayIndexOutOfBoundsException at runtime
    }
}
```

The compiler won’t complain, but program **crashes at runtime**.

---


### Best practices
- Fail fast; validate arguments early.
- Preserve cause chains: wrap with a meaningful message and pass the cause.
- Prefer specific exception types over generic `Exception`.
- Don’t swallow exceptions; at least log with context.
- Use try-with-resources for closeable resources.

```java
try (BufferedReader br = Files.newBufferedReader(Path.of("data.txt"), StandardCharsets.UTF_8)) {
  String line;
  while ((line = br.readLine()) != null) { /* process */ }
} catch (IOException e) {
  throw new UncheckedIOException("Failed to read data.txt", e);
}
```

### Custom exceptions
- Extend the appropriate base (checked vs unchecked) and provide constructors that accept messages and causes.

```java
class DomainException extends RuntimeException {
  DomainException(String message){super(message);}
  DomainException(String message, Throwable cause){super(message, cause);}
}
```


## Q.19: What is the difference between `throw` and `throws`?

   * `throw`: Used to explicitly throw an exception inside a method.
   * `throws`: Declares exceptions a method can throw.

## Q.20: What is the difference between `final`, `finally`, and `finalize()`?

   * `final`: Constant, prevent inheritance/overriding.
   * `finally`: Always executes after try-catch (used for cleanup).
   * `finalize()`: Called by GC before object destruction.


## Q.21: Can `finally` block be skipped?
Yes, if JVM exits (`System.exit(0)`) or fatal error occurs.

## Q.22: Can we have multiple `catch` blocks?
Yes, from specific → general exception types.

## Q.23: Can a `try` block exist without `catch`?
Yes, but must have `finally`.

## Q.24: Can we catch multiple exceptions in one `catch`?
Yes, Java 7+: `catch(IOException | SQLException e)`.

## Q.25: What happens if an exception occurs inside `finally`?
If an exception occurs **inside the `finally` block**, it **overrides** any exception thrown from the `try` or `catch` blocks.

Here’s what happens step by step:

1. If both `try`/`catch` and `finally` throw exceptions —
   → the **exception from `finally`** is the one that actually propagates out of the method.
   → the original exception from `try`/`catch` is **lost** unless explicitly handled or logged.

2. If no exception in `try`/`catch`, but `finally` throws —
   → only the `finally` exception is thrown.

---

### Example:

```java
public class Test {
    public static void main(String[] args) {
        try {
            System.out.println("In try");
            throw new RuntimeException("Try Exception");
        } finally {
            System.out.println("In finally");
            throw new RuntimeException("Finally Exception");
        }
    }
}
```

**Output:**

```
In try
In finally
Exception in thread "main" java.lang.RuntimeException: Finally Exception
```

The “Try Exception” is lost; only the “Finally Exception” propagates.


## Q.26: Difference between `Error` and `Exception`?

   * Exception: recoverable (business logic issues).
   * Error: unrecoverable (OutOfMemoryError, StackOverflowError).

## Q.27: What is `try-with-resources`?
**`try-with-resources`** is a feature introduced in **Java 7** that automatically closes resources (like files, streams, connections) after use — without needing an explicit `finally` block. Unlike traditional try-finally, you don’t have to explicitly close the resource — it’s done automatically, even in case of exceptions.

It ensures **automatic resource management** and prevents **resource leaks**.

---

### ✅ **Syntax**

```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    System.out.println(br.readLine());
} catch (IOException e) {
    e.printStackTrace();
}
```

Here, `br` is automatically closed after the `try` block — even if an exception occurs.



## Q.28: Custom exceptions in Java?
In Java, we can create custom exceptions by extending either the `Exception` class (for checked exceptions) or the `RuntimeException` class (for unchecked exceptions). This allows us to represent application-specific error conditions clearly.

**Example:**

```java
// Checked custom exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Unchecked custom exception
class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class Test {
    static void validateAge(int age) throws InvalidAgeException {
        if(age<18) throw new InvalidAgeException("Age must be at least 18");
    }

    public static void main(String[] args) {
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("Caught Exception: "+e.getMessage());
        }
    }
}
```

**Summary:**

* Extend `Exception` → Checked custom exception (must be declared or handled).
* Extend `RuntimeException` → Unchecked custom exception (optional to handle).
* Include constructors that pass messages to the superclass for clarity.


## Q.29: What happens if exception is not caught?

* Propagates up call stack → JVM handles → prints stack trace → program exits.


---

## Q.30: What are some features of java 8?

* **Stream API** → For processing collections in a functional style (filter, map, reduce).
* **Lambda Expressions** → Enables functional programming by passing behavior as parameters.
* **Functional Interfaces** → Interfaces with a single abstract method (e.g., `Runnable`, `Comparator`).
* **Optional Class** → To handle `null` values and avoid `NullPointerException`.
* **CompletableFuture & Concurrency Updates** → For better async programming.
* **Default and Static Methods in Interfaces** → Interfaces can have method implementations using `default` and can have static utility methods also.


## Q.31: Explain Lambda expression with code example?

* **Definition**: Lambda expressions are anonymous functions (no name, return type inferred) introduced in Java 8 to enable functional programming.
* **Syntax**:

  ```java
  (parameters) -> expression
  (parameters) -> { statements }
  ```

### Example 1: Runnable without Lambda

```java
Runnable r1=new Runnable(){
    public void run(){
        System.out.println("Hello from thread");
    }
};
new Thread(r1).start();
```

### Example 2: Runnable with Lambda

```java
Runnable r2=()->System.out.println("Hello from thread");
new Thread(r2).start();
```

### Example 3: Sorting with Comparator

```java
List<String> names=Arrays.asList("Aditya","Raj","Kumar");

// Without Lambda
Collections.sort(names,new Comparator<String>(){
    public int compare(String a,String b){
        return a.compareTo(b);
    }
});

// With Lambda
Collections.sort(names,(a,b)->a.compareTo(b));
```

### Example 4: Stream with Lambda

```java
List<Integer> nums=Arrays.asList(1,2,3,4,5);
nums.stream().filter(n->n%2==0).forEach(n->System.out.println(n));
```

👉 Lambda helps write shorter, cleaner, and more readable code by removing boilerplate.


## Q.32: What are `Functional Interfaces`?

* **Definition**: A **functional interface** is an interface with exactly **one abstract method**.
* They can have any number of **default** or **static** methods.
* They are the target type for **lambda expressions** and **method references**.
* Annotated with `@FunctionalInterface` (optional but recommended).

---

### Example 1: Custom Functional Interface

```java
@FunctionalInterface
interface MyFunctionalInterface {
    void greet(String name); // Single abstract method
}

public class Demo {
    public static void main(String[] args) {
        // Using Lambda
        MyFunctionalInterface f = (n)->System.out.println("Hello, "+n);
        f.greet("Aditya");
    }
}
```

---

### Example 2: Using Built-in Functional Interface

```java
import java.util.function.Predicate;

public class Demo {
    public static void main(String[] args) {
        Predicate<Integer> isEven=n->n%2==0;
        System.out.println(isEven.test(4)); // true
        System.out.println(isEven.test(5)); // false
    }
}
```

## Q.33: What are Stream APIs?
* **Stream API** (introduced in Java 8) is used to process collections of data (like `List`, `Set`) in a functional style.
* It allows performing operations such as **filtering, mapping, sorting, and reducing** with concise, readable code.
* **Streams are not data structures**, they don’t store elements. Instead, they provide a pipeline of operations on data.
* Key features:

  * Declarative (clean, SQL-like operations).
  * Supports **parallel processing** for performance.
  * Supports **intermediate operations** (return Stream) and **terminal operations** (produce result).

---

### Example 1: Filtering & Mapping

```java
import java.util.*;
import java.util.stream.*;

public class Demo {
    public static void main(String[] args) {
        List<String> names=Arrays.asList("Aditya","Raj","Alex","Ankit");

        // Filter names starting with 'A' and convert to uppercase
        List<String> result=names.stream()
                                 .filter(n->n.startsWith("A"))
                                 .map(String::toUpperCase)
                                 .toList();

        System.out.println(result); // [ADITYA, ALEX, ANKIT]
    }
}
```

---

### Example 2: Reduction

```java
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        List<Integer> nums=Arrays.asList(1,2,3,4,5);

        int sum=nums.stream().reduce(0,(a,b)->a+b);
        System.out.println(sum); // 15
    }
}
```

---

### Common Stream Operations

* **Intermediate**: `filter()`, `map()`, `sorted()`, `distinct()`, `limit()`
* **Terminal**: `collect()`, `forEach()`, `reduce()`, `count()`, `toList()`

## Q.34: What are CompletableFuture? why and when to use them?

* **CompletableFuture** (Java 8) is an advanced implementation of `Future` used for asynchronous programming.
* Unlike plain `Future`, it allows you to **manually complete tasks**, chain multiple async operations, and handle results/errors without blocking.

---

### Why use CompletableFuture?

* To perform **non-blocking async tasks** (e.g., API calls, DB queries).
* To **combine multiple tasks** (run in parallel, then merge results).
* To **chain tasks** (perform next step after previous completes).
* To **handle exceptions** gracefully in async flows.

---

### Key Features

* `runAsync()` → run a task without return value.
* `supplyAsync()` → run a task that returns a value.
* `thenApply()` → transform result.
* `thenAccept()` → consume result (no return).
* `thenCombine()` → combine results of two futures.
* `exceptionally()` → handle errors.

---

### Example

```java
import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future=CompletableFuture.supplyAsync(() -> {
            // Simulate long task
            return 10;
        });

        // Transform result
        CompletableFuture<Integer> squared=future.thenApply(x -> x*x);

        // Get final result (blocking here just for demo)
        System.out.println(squared.get()); // 100
    }
}
```

---

✅ Use **CompletableFuture** when:

* You want **non-blocking async execution**.
* You need to **compose multiple async operations**.
* You want **cleaner async code** compared to `Future` + `ExecutorService`.


## Q.35: What are Optional Class in java? Why and when to use them?

* **Optional** (Java 8) is a container object that may or may not hold a non-null value.
* It was introduced to avoid **NullPointerException (NPE)** and make null checks more readable.

---

### Why use Optional?

* To represent **optional/nullable values** without explicitly using `null`.
* To **avoid explicit null checks** (`if(obj != null)`).
* To **express intent** clearly: "this value may be absent".

---

### When to use Optional?

* When a method may or may not return a value (instead of returning `null`).
* As a **return type**, not as a field in entities/DTOs (bad practice for serialization).
* For **functional-style operations** with `map`, `filter`, `orElse`.

---

### Example

```java
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // Creating Optional
        Optional<String> opt=Optional.ofNullable(null);

        // Check presence
        System.out.println(opt.isPresent()); // false

        // Default value
        String val=opt.orElse("default");
        System.out.println(val); // default

        // Map and filter
        Optional<String> name=Optional.of("Aditya");
        name.filter(s -> s.length()>3)
            .map(String::toUpperCase)
            .ifPresent(System.out::println); // ADITYA
    }
}
```

---

✅ Use **Optional** when:

* You want to avoid **NPE** in method return values.
* You want to make **code more declarative and readable**.
* You need to apply **functional operations** directly on possibly-null values.

Do you want me to also list **common methods of Optional** (like `of`, `empty`, `orElseGet`, `orElseThrow`) for quick notes?


## Q.36: What is the difference between `.of()` and `.ofNullable()` ?
 The main difference between `Optional.of()` and `Optional.ofNullable()` is that the former throws a `NullPointerException` if it receives null as an argument, whereas the latter wraps the provided value in an Optional instance without throwing an exception.

Here's the code snippet for each method:

```java
Optional<String> optional1 = Optional.of("Hello"); // No issues
Optional<String> optional2 = Optional.of(null); // Throws NullPointerException

Optional<String> optional3 = Optional.ofNullable("World"); // No issues
Optional<String> optional4 = Optional.ofNullable(null); // Creates an empty Optional instance
```

In the example above, `optional1` is created without any issues because it contains a non-null string value. However, calling `Optional.of(null)` will result in a `NullPointerException`. On the other hand, using `Optional.ofNullable()`, both `optional3` and `optional4` are created correctly, with `optional4` wrapping an empty Optional instance since it receives null as an argument.

To summarize:
- `Optional.of()` does not accept null values and throws a `NullPointerException` if provided null.
- `Optional.ofNullable()` accepts null values and wraps them in an empty Optional instance without throwing an exception.


## Q.37: Difference between java 8 and 17 and their features?

> Java 8 was a major release that introduced functional programming features, while Java 17 is a long-term support (LTS) release focused on performance, language enhancements, and modern APIs.

**Key Differences and Features:**

| Aspect | Java 8 (2014) | Java 17 (2021)|
| ------ | ------        |   ------      |
| **Programming Paradigm**   | Introduced **Functional Programming** with Lambdas, Streams, and Functional Interfaces | Focused on **Modern Java**—sealed classes, pattern matching, records                                                                                                     |
| **Core Features**          | - Lambda Expressions<br>- Streams API<br>- Optional Class<br>- Functional Interfaces<br><br>- Default & Static methods in interfaces | - **Records** (to create immutable data carriers)<br>- **Sealed Classes** (restricted inheritance)<br>- **Pattern Matching for instanceof**<br>- **Switch Expressions** (enhanced)<br>- **Text Blocks** (multi-line strings)<br>- **JEP 356: Enhanced Pseudo-Random Number Generators** |
| **Performance & JVM**      | Old Metaspace handling, PermGen removed                                                                                                                         | Improved Garbage Collectors (**ZGC, G1 optimizations**), faster startup and lower memory footprint                                                                                                                                                                                      |
| **LTS**                    | Yes (widely used in enterprises)                                                                                                                                | Yes (current LTS; long-term support till 2029)                                                                                                                                                                                                                                          |
| **Security & API Updates** | Introduced base changes                                                                                                                                         | Stronger encapsulation of JDK internals (JEP 403), better TLS, crypto updates                                                                                                                                                                                                           |

## Q.38: What is the difference between Hashmap and HashTable and ConcurrentHashMap?
Good question — this one comes up a lot in interviews. Here’s a crisp, **interview-ready comparison**:

---

### 🔑 Differences: **HashMap vs Hashtable vs ConcurrentHashMap**

| Feature              | **HashMap**                                                      | **Hashtable**                       | **ConcurrentHashMap**                                                       |
| -------------------- | ---------------------------------------------------------------- | ----------------------------------- | --------------------------------------------------------------------------- |
| **Thread Safety**    | ❌ Not synchronized (not thread-safe).                            | ✅ Synchronized (all methods).       | ✅ Thread-safe (uses modern concurrency techniques).                         |
| **Performance**      | Fastest (no sync overhead).                                      | Slower (global lock on entire map). | Much faster than Hashtable (locks only segments/buckets, not whole map).    |
| **Null Keys/Values** | ✅ 1 null key, multiple null values.                              | ❌ No null key/values.               | ❌ No null key, null values not allowed.                                     |
| **Introduced In**    | Java 1.2                                                         | Java 1.0 (legacy).                  | Java 5 (as part of `java.util.concurrent`).                                 |
| **Iteration**        | Iterator (fail-fast → throws `ConcurrentModificationException`). | Enumerator (not fail-fast).         | Iterator (fail-safe → won’t throw CME, but may not reflect recent updates). |
| **Use Case**         | Single-threaded, or manually synchronized.                       | Legacy, rarely used today.          | High-performance thread-safe alternative.                                   |

---

### ✅ Quick Example

```java
// HashMap
Map<Integer, String> map = new HashMap<>();
map.put(1, "One");
map.put(null, "NullKey"); // ✅ allowed
map.put(2, null);         // ✅ allowed

// Hashtable
Map<Integer, String> table = new Hashtable<>();
table.put(1, "One");
// table.put(null, "X");   // ❌ NullPointerException
// table.put(2, null);     // ❌ NullPointerException

// ConcurrentHashMap
Map<Integer, String> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put(1, "One");
// concurrentMap.put(null, "X"); // ❌ NullPointerException
// concurrentMap.put(2, null);   // ❌ NullPointerException
```

---

### 🔥 Interview Tips

* **HashMap** → use for *non-thread-safe*, single-threaded cases.
* **Hashtable** → avoid (only for legacy support).
* **ConcurrentHashMap** → use for *highly concurrent, thread-safe* scenarios.
* If asked: *“Why ConcurrentHashMap over Hashtable?”*
  👉 Because **Hashtable locks the entire map**, while **ConcurrentHashMap locks only portions (buckets/segments)**, giving much better concurrency.

---

## Q.39: Draw an **internal working diagram of ConcurrentHashMap locking** (bucket-level locking vs Hashtable global lock)?

### 🔑 Differences: **HashMap vs Hashtable vs ConcurrentHashMap**

| Feature              | **HashMap**                                                      | **Hashtable**                       | **ConcurrentHashMap**                                                       |
| -------------------- | ---------------------------------------------------------------- | ----------------------------------- | --------------------------------------------------------------------------- |
| **Thread Safety**    | ❌ Not synchronized (not thread-safe).                            | ✅ Synchronized (all methods).       | ✅ Thread-safe (uses modern concurrency techniques).                         |
| **Performance**      | Fastest (no sync overhead).                                      | Slower (global lock on entire map). | Much faster than Hashtable (locks only segments/buckets, not whole map).    |
| **Null Keys/Values** | ✅ 1 null key, multiple null values.                              | ❌ No null key/values.               | ❌ No null key, null values not allowed.                                     |
| **Introduced In**    | Java 1.2                                                         | Java 1.0 (legacy).                  | Java 5 (as part of `java.util.concurrent`).                                 |
| **Iteration**        | Iterator (fail-fast → throws `ConcurrentModificationException`). | Enumerator (not fail-fast).         | Iterator (fail-safe → won’t throw CME, but may not reflect recent updates). |
| **Use Case**         | Single-threaded, or manually synchronized.                       | Legacy, rarely used today.          | High-performance thread-safe alternative.                                   |

---

### ✅ Quick Example

```java
// HashMap
Map<Integer, String> map = new HashMap<>();
map.put(1, "One");
map.put(null, "NullKey"); // ✅ allowed
map.put(2, null);         // ✅ allowed

// Hashtable
Map<Integer, String> table = new Hashtable<>();
table.put(1, "One");
// table.put(null, "X");   // ❌ NullPointerException
// table.put(2, null);     // ❌ NullPointerException

// ConcurrentHashMap
Map<Integer, String> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put(1, "One");
// concurrentMap.put(null, "X"); // ❌ NullPointerException
// concurrentMap.put(2, null);   // ❌ NullPointerException
```

---

### 🔥 Interview Tips

* **HashMap** → use for *non-thread-safe*, single-threaded cases.
* **Hashtable** → avoid (only for legacy support).
* **ConcurrentHashMap** → use for *highly concurrent, thread-safe* scenarios.
* If asked: *“Why ConcurrentHashMap over Hashtable?”*
  👉 Because **Hashtable locks the entire map**, while **ConcurrentHashMap locks only portions (buckets/segments)**, giving much better concurrency.

---

Do you want me to also draw an **internal working diagram of ConcurrentHashMap locking** (bucket-level locking vs Hashtable global lock)?

---

## Q.40: How does Garbage Collection work in JVM?

**Garbage Collection in JVM**

* In Java, objects are created on the **heap**. When they are no longer referenced, they become **eligible for garbage collection**.
* The **Garbage Collector (GC)** automatically frees up this memory by removing unreachable objects.
* JVM divides the heap into **Young Generation** (short-lived objects) and **Old Generation** (long-lived objects).

  * **Minor GC**: Cleans the Young Generation.
  * **Major GC**: Cleans the Old Generation.
* GC uses algorithms like **mark and sweep** (mark live objects, sweep the rest) and sometimes **compacting** to remove memory fragmentation.
* Developers can’t force GC, but can suggest it with `System.gc()`.

**Interview line:**
“Garbage Collection in Java automatically manages memory by removing objects no longer referenced. It mainly works with the Young and Old generations using algorithms like mark-sweep and compaction. This reduces memory leaks and makes Java memory-safe without requiring manual deallocation.”

## Q.41: Explain volatile and synchronized in multithreading?

### **1. `volatile`**

* `volatile` is a **keyword** used with variables.
* It ensures **visibility** of changes to a variable across threads.
* Without `volatile`, one thread may read a cached value, not the updated one.
* With `volatile`, every read/write goes directly to **main memory**.
* But `volatile` does **not** guarantee atomicity (e.g., `count++` is not safe even if `count` is volatile).

👉 Use `volatile` when multiple threads read/write a single variable, and operations are independent (like a status flag).

---

### **2. `synchronized`**

* `synchronized` is used for **methods** or **blocks**.
* It ensures **mutual exclusion** (only one thread can execute at a time on the locked object).
* It also ensures **visibility** (similar to volatile) by flushing changes to main memory when lock is released.
* Guarantees **atomicity + visibility**.

👉 Use `synchronized` when multiple threads modify shared state and you need **thread safety**.

---

✅ **In one line**:

* `volatile` → ensures **visibility only**.
* `synchronized` → ensures **visibility + atomicity (mutual exclusion)**.



## Q.42: Deep copy vs Shallow copy?


### **Shallow Copy**

* Copies the **object structure** but not the nested objects.
* The new object references the **same inner objects** as the original.
* Changes in nested objects of one copy affect the other.
* In Java: `clone()` (default `Object.clone()`) creates a shallow copy.

👉 Example: Copying a list of objects but both lists still point to the same objects.

---

### **Deep Copy**

* Copies the **object and all nested objects recursively**.
* The new object has completely **independent copies** of everything.
* Changes in one object do **not** affect the other.
* In Java: Need to implement manually (e.g., custom `clone()` or using serialization/third-party libraries).

👉 Example: Copying a list of objects where both lists have **separate object instances**.

---

✅ **In one line**:

* **Shallow copy** → copies references of inner objects (shared).
* **Deep copy** → copies everything recursively (independent).

---

## Q.43: How does the Java memory model work (Heap, Stack, Metaspace)?
Here’s a clear **interview-friendly explanation** of the **Java Memory Model (JMM):**

---

### **1. Heap**

* Shared memory for all threads.
* Stores **objects** and **instance variables**.
* Divided into:

  * **Young Generation** (new objects, further divided into Eden + Survivor spaces).
  * **Old Generation (Tenured)** (long-lived objects).
* Garbage Collection mainly happens here.

---

### **2. Stack**

* Each thread has its own **stack** (not shared).
* Stores **method frames**: local variables, references, and function calls.
* Memory is freed automatically when a method ends.

---

### **3. Metaspace** (replaces PermGen from Java 8 onward)

* Stores **class metadata** (class definitions, method info, static variables).
* Unlike PermGen (which was fixed size), Metaspace grows dynamically in native memory.

---

### **Quick Analogy (easy to say in interview)**

* **Heap** = common storage room for all objects.
* **Stack** = personal desk of each thread where it keeps its local notes.
* **Metaspace** = library storing blueprints (class definitions).

---


## Q.44: What happens if two keys have the same hashcode?
In Java, if two keys have the same **hashCode**, the following happens inside a `HashMap` (or similar hash-based collections):

1. **Bucket selection:** Both keys will map to the same bucket (index) in the hash table.
2. **Collision resolution:** Since they are in the same bucket, Java uses **equals()** to check if the keys are actually equal.

   * If `equals()` returns **true**, the new value will replace the old value.
   * If `equals()` returns **false**, both keys coexist in the same bucket, stored as a **linked list** (before Java 8) or **balanced tree** (Java 8 onwards if bucket size exceeds threshold).

👉 Summary:

* Same `hashCode` → same bucket.
* `equals()` true → overwrite.
* `equals()` false → both stored (collision handled).




## Q.45: Difference between synchronized block and ConcurrentHashMap?
Here’s the difference between a **synchronized block** and a **ConcurrentHashMap**:

---

### **1. Synchronized Block (e.g., wrapping HashMap)**

* You manually synchronize code using `synchronized` keyword.
* Example:

  ```java
  Map<Integer,String> map=Collections.synchronizedMap(new HashMap<>());
  synchronized(map) {
      map.put(1,"A");
  }
  ```
* **Locking**: Entire map (or block) is locked → only **one thread** can access at a time.
* **Performance**: Slower under high concurrency (coarse-grained lock).
* **Iteration**: Must manually synchronize during iteration to avoid `ConcurrentModificationException`.

---

### **2. ConcurrentHashMap**

* Designed for concurrency; no need for explicit synchronization.
* **Locking**: Uses **fine-grained locks** (segments/buckets before Java 8, synchronized bins + CAS after Java 8).
* Multiple threads can operate on different buckets concurrently.
* **Performance**: Much faster than synchronized blocks under high concurrency.
* **Iteration**: Provides **weakly consistent iterator** (doesn’t throw `ConcurrentModificationException`, may reflect changes during iteration).

---

👉 **Key Difference in One Line**:

* `synchronized block` → full map lock (blocking).
* `ConcurrentHashMap` → fine-grained locking + better concurrency.

---

## Q.46: Why ConcurrentHashMap are Much faster than synchronized blocks under high concurrency?
ConcurrentHashMap is much faster than synchronized blocks under high concurrency because of **how locking is handled**:

---

### **1. Synchronized Blocks**

* When you wrap a `HashMap` with synchronization (`Collections.synchronizedMap`),
  the **entire map** is locked for every read and write.
* Only **one thread** can access the map at a time → all other threads wait.
* This creates a **bottleneck** under heavy load (coarse-grained lock).

---

### **2. ConcurrentHashMap**

* Uses **fine-grained locking** instead of locking the whole map.
* Before Java 8:

  * The map was divided into **segments** (like mini-HashMaps).
  * A lock applied only to the segment being modified.
  * Different threads could safely operate on different segments concurrently.
* Java 8 and later:

  * Removed segments, now uses **synchronized on buckets (bins)** + **CAS (Compare-And-Swap)** for updates.
  * Multiple threads can modify different buckets at the same time with minimal blocking.
* Reads are usually **non-blocking** (no lock needed).

---

### **3. Iteration**

* Iterators of synchronized maps require full lock.
* Iterators of `ConcurrentHashMap` are **weakly consistent**:

  * They don’t throw `ConcurrentModificationException`.
  * They may reflect some updates while iterating.

---

✅ **Summary**:

* Synchronized map → single lock = high contention.
* ConcurrentHashMap → fine-grained locking + CAS = reduced contention + better scalability.



## Q.47: How does Garbage Collection work in JVM?
The core process of JVM Garbage Collection typically involves the following phases:

**Marking Phase:**

The garbage collector identifies "live" or "reachable" objects. It starts by identifying a set of "root" objects (e.g., local variables on the stack, static variables, active threads). From these roots, it recursively traverses the object graph, marking all objects that are reachable. Any object not marked during this phase is considered "dead" or "unreachable" and eligible for collection.

**Sweeping Phase:**

After marking, the garbage collector scans the heap and identifies the memory occupied by the unmarked (dead) objects. This memory is then reclaimed and added back to the free memory pool, making it available for future object allocations. 



## Q.48: Explain the Java Memory Model briefly?
The **Java Memory Model (JMM)** is a specification that defines how threads interact with memory in a Java Virtual Machine (JVM), particularly in multithreaded environments. It establishes rules and guarantees regarding the visibility, ordering, and atomicity of variable access, ensuring consistent behavior of concurrent programs across different hardware and JVM implementations.

Key aspects of the JMM include:

* **Visibility:** The JMM ensures that changes made by one thread to shared variables become visible to other threads in a timely and predictable manner. Without these guarantees, threads might operate on stale or inconsistent data due to caching or compiler optimizations.
* **Ordering:** The JMM defines rules for the permissible reordering of instructions by compilers and processors. While reordering can optimize performance, the JMM ensures that such reorderings do not alter the observable behavior of a multithreaded program, particularly with respect to synchronization constructs.
* **Atomicity:** The JMM guarantees that certain operations, like reading or writing a primitive variable (excluding long and double which are not guaranteed to be atomic without volatile), are performed as a single, indivisible unit, preventing partial updates or data corruption.
* **Happens-Before Relationship:** This is a core concept in the JMM, establishing a partial ordering of operations. If action A happens-before action B, then the effects of A are visible to B. This relationship is crucial for reasoning about memory visibility and ensuring correct synchronization. Examples include the happens-before relationship between unlocking a monitor and a subsequent lock on the same monitor, or writing to a volatile variable and a subsequent read of that variable.

The JMM is fundamental for developing robust and reliable concurrent applications in Java, providing the necessary framework for thread safety and preventing issues like data races and inconsistent state. Developers utilize synchronization constructs like `synchronized` blocks, `volatile` variables, and concurrent utility classes (e.g., from `java.util.concurrent`) to adhere to the JMM's guarantees and ensure correct program behavior.


## Q.49: What is Future in java?
 In Java, a `Future` is an object that represents the result of an asynchronous computation. It allows you to carry out a computation in the background and obtain its result without waiting for it to complete, or check if the computation is still running.

The `Future` interface in Java provides methods such as:

1. `get()`: Blocks until the computation is done and retrieves the result.
2. `isDone()`: Determines whether the computation has completed.
3. `cancel(boolean mayInterruptIfRunning)`: Attempts to cancel a computation before it finishes, if possible.

---

## Q.50: Can you teach me about Java 8 features (Lambdas, Streams, Functional Interfaces)?

### Lambdas:

 In Java 8 and later versions, lambda expressions were introduced as a way to write concise functional-style code. Lambda expressions allow you to create small anonymous functions that can be used in place of traditional methods and interfaces.

Here's the basic syntax for a lambda expression:

```java
(parameters) -> { statements; }
```

The parameters section consists of zero or more parameter names separated by commas, enclosed within parentheses. The body is wrapped in curly braces `{}`, and it can contain any number of statements to be executed when the lambda expression is called.

Example: A simple lambda that takes an integer as input and returns its double:

```java
(int x) -> { return 2 * x; }
```

To use a lambda, it must be assigned to a functional interface or passed as an argument to a method that accepts functional interfaces. Functional interfaces are interfaces that contain only one abstract method (i.e., an abstract class with one abstract method). Examples of built-in Java 8 functional interfaces include:

1. `Function<T, R>`: Takes an input of type T and returns a result of type R.
2. `Consumer<T>`: Accepts an input of type T but does not return a value (void method).
3. `Supplier<T>`: Doesn't take any parameters but produces a value of type T.
4. `Predicate<T>`: Returns a boolean value based on the provided input of type T.
5. `BiFunction<T, U, R>`: Takes two inputs, one of type T and another of type U, and returns a result of type R.
6. `BiConsumer<T, U>`: Accepts two inputs of types T and U and does not return a value (void method).
7. `BiPredicate<T, U>`: Returns a boolean value based on the provided input of types T and U.
8. `UnaryOperator<T>`: Applies an operation to a single input of type T and returns the result of type T.

Example usage of lambda expressions with functional interfaces:

```java
Function<Integer, Integer> doubleFunction = (x) -> { return 2 * x; };

Consumer<String> printString = (s) -> System.out.println(s);

Supplier<Boolean> randomBoolean = () -> Math.random() > 0.5;
```


### Streams:
In Java, Stream is a sequence of elements that can be processed in parallel or sequentially using functional-style operations. A Stream can come from various sources such as collections (e.g., arrays and lists), I/O channels, or even generating sequences on the fly.

Here are some key features and concepts of Java Streams:

1. Intermediate and terminal operations: There are two types of operations available in Stream – intermediate and terminal. Intermediate operations do not return a result immediately but instead create an intermediary Stream that can be further processed with other intermediate or terminal operations. Terminal operations perform the actual computation and produce a result.
2. Pipeline pattern: Streams follow the pipeline pattern, which involves chaining multiple intermediate operations before calling a terminal operation to compute the final result.
3. Parallel vs sequential processing: By default, Streams are processed sequentially, but you can enable parallel processing using the `parallel()` method. However, be aware that parallel Streams may have performance overhead due to the coordination between threads and memory usage.
4. Short-circuiting: Some intermediate operations in Java Streams short-circuit their execution if the result can be determined with the available data before processing all elements in the Stream. For example, the `filter()` operation will stop processing elements once it encounters an element that does not meet the specified predicate condition.
5. Lazy evaluation: Streams are evaluated lazily, meaning they do not compute their results until a terminal operation is called. This allows for more efficient processing of large datasets by reducing memory consumption.
6. Collectors: Collectors are used to convert the result of a Stream into a specific data structure (e.g., lists, sets, or arrays). They provide various methods for grouping, partitioning, and summarizing elements in a Stream. Examples of collectors include `collect(toList())`, `collect(joining(" "))`, and `summingInt()`.
7. Optional vs Stream: Unlike Optional, which can contain either a value or an empty instance, Stream does not guarantee the presence of an element, but it allows you to process all available data if present.

Example usage of Java Streams with intermediate and terminal operations:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

int sum = numbers.stream()
    .filter(n -> n % 2 == 0) // Intermediate operation
    .mapToInt(i -> i * i)     // Intermediate operation
    .sum();                   // Terminal operation
```
In the example above, we create a Stream from a list of numbers and filter out odd numbers, square each remaining number, and then sum the results using the `sum()` terminal operation.

### Functional Interfaces:
In Java 8 and later versions, functional interfaces were introduced to support lambda expressions. A functional interface is an interface that contains only one abstract method (i.e., an abstract class with one abstract method). This makes it possible to use lambda expressions as arguments or return types when implementing the functional interface.

The `@FunctionalInterface` annotation was added in Java 8 to indicate that a given interface is intended to be used as a functional interface, ensuring that it adheres to the single-abstract-method requirement.

Here are some built-in Java 8 functional interfaces:

1. `Function<T, R>`: Takes an input of type T and returns a result of type R. Example usage: `(int x) -> { return x * x; }`.
2. `Consumer<T>`: Accepts an input of type T but does not return a value (void method). Example usage: `(String s) -> System.out.println(s)`.
3. `Supplier<T>`: Doesn't take any parameters but produces a value of type T. Example usage: `() -> new Random().nextInt(10)`.
4. `Predicate<T>`: Returns a boolean value based on the provided input of type T. Example usage: `(int x) -> x % 2 == 0`.
5. `BiFunction<T, U, R>`: Takes two inputs, one of type T and another of type U, and returns a result of type R. Example usage: `(int x, int y) -> x + y`.
6. `BiConsumer<T, U>`: Accepts two inputs of types T and U and does not return a value (void method). Example usage: `(String s1, String s2) -> System.out.println(s1 + " " + s2)`.
7. `BiPredicate<T, U>`: Returns a boolean value based on the provided input of types T and U. Example usage: `(int x, int y) -> x < y`.
8. `UnaryOperator<T>`: Applies an operation to a single input of type T and returns the result of type T. Example usage: `(int x) -> x * x`.
9. `BinaryOperator<T>`: Applies an associative binary operation on two inputs of type T, returning a result of type T. Example usage: `(int x, int y) -> x + y`.
10. `Function<T, void>`: Takes an input of type T and returns nothing (void method). Example usage: `(String s) -> System.out.println(s)`.

These functional interfaces can be used in various scenarios such as handling callbacks or processing data with lambda expressions. The ability to create custom functional interfaces allows developers to design their own abstractions for specific use cases, making it easier to write concise and flexible code using Java 8's lambda expressions.

## Q.51: What is `Future` and `CompletableFuture` in java?

### **1. `Future` in Java**

* Introduced in **Java 5** (as part of `java.util.concurrent`).
* Represents the result of an asynchronous computation.
* You submit a task to an `ExecutorService`, and it immediately returns a `Future` object, which acts as a placeholder for the result.

**Limitations of Future:**

1. **Blocking get()** – you have to call `future.get()` to wait for the result, which blocks the thread.
2. **No callbacks** – you cannot attach actions to run when the task completes.
3. **No chaining** – you cannot combine results of multiple futures easily.
4. **No manual completion** – only the executor sets the result.

**Example with Future:**

```java
import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(1000);
            return 42;
        });

        System.out.println("Doing something else...");
        Integer result = future.get(); // blocks
        System.out.println("Result: " + result);
        executor.shutdown();
    }
}
```

---

### **2. `CompletableFuture` in Java**

* Introduced in **Java 8**.
* An enhanced version of `Future`.
* Supports **non-blocking**, **callback-based**, and **chaining** style of programming.
* Implements both `Future` and `CompletionStage`.

**Key features:**

1. **Asynchronous execution** – run tasks with `supplyAsync()` or `runAsync()`.
2. **Non-blocking callbacks** – attach `thenApply()`, `thenAccept()`, `thenRun()`, etc.
3. **Chaining** – chain multiple async computations together.
4. **Combining Futures** – `thenCombine()`, `allOf()`, `anyOf()` for parallel execution.
5. **Manual completion** – you can complete it using `complete()` method.

**Example with CompletableFuture:**

```java
import java.util.concurrent.*;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return 42;
        });

        future.thenApply(result -> result * 2)      // transforms the result
              .thenAccept(result -> System.out.println("Result: " + result));

        System.out.println("Doing something else...");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }
}
```

**Output (non-blocking):**

```
Doing something else...
Result: 84
```

---

### **Summary (Interview Answer)**

* **Future** → Represents the result of an async computation but only supports blocking retrieval (`get()`). No chaining, no callbacks.
* **CompletableFuture** → A more powerful, non-blocking, callback-based future with chaining, composition, and manual completion support.

---


## Q.52: What is Tomcat?

### **What is Tomcat?**

Apache Tomcat is a **web server + servlet container**.
It is the default embedded server in Spring Boot.

* **Web server** → Handles HTTP requests and responses (like Apache HTTP Server, Nginx).
* **Servlet container** → Provides the runtime environment for Java Servlets and JSPs (Java code that runs inside web requests).

Tomcat does **both**.

---

### **How it fits in Spring Boot?**

* When you run a Spring Boot app, it **starts an embedded Tomcat server** inside your application (no need to install separately).
* That’s why your app runs at `http://localhost:8080`.
* Tomcat handles incoming requests, and then Spring passes them to your controllers.

---

### **Key point**:

Tomcat = **middleman between browser and your Java code**.

* It speaks HTTP.
* It knows how to run Java Servlets.
* It makes sure every request-response cycle works smoothly.

---


## Q.53: What is Abstraction? How do you implement it in your project?
**Abstraction in Java**:
Abstraction is the process of hiding implementation details and showing only the essential features of an object. It helps reduce complexity and increase reusability by focusing on *what an object does* rather than *how it does it*.

In Java, **abstraction** can be achieved in two ways:

1. **Abstract classes** (partial abstraction).

   * Can have abstract methods (without body) and concrete methods (with implementation).
   * Used when you want to enforce some common behavior but still allow flexibility.
2. **Interfaces** (full abstraction, until Java 8).

   * Define only method signatures, and the implementing classes provide concrete logic.
   * From Java 8 onwards, interfaces can have `default` and `static` methods too.

---

### Example of Abstraction:

```java
// Abstract class example
abstract class Payment {
    abstract void makePayment(double amount);

    void generateReceipt() {
        System.out.println("Receipt generated");
    }
}

class CreditCardPayment extends Payment {
    @Override
    void makePayment(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class UpiPayment extends Payment {
    @Override
    void makePayment(double amount) {
        System.out.println("Paid " + amount + " using UPI");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Payment payment = new CreditCardPayment();
        payment.makePayment(500.0);   // Hides implementation, just calls abstracted method
        payment.generateReceipt();

        payment = new UpiPayment();
        payment.makePayment(200.0);
    }
}
```

---

### How I would explain in an interview ("How do you implement it in your project?"):

👉 *"In my project, we used abstraction mainly to design service layers. For example, in a payment module, we defined an interface `PaymentService` that had methods like `processPayment()`, and then we had multiple implementations like `CreditCardService`, `UpiService`, etc. The calling code only depended on the abstraction (`PaymentService`) and not on the concrete classes. This allowed us to easily extend the system in the future without changing the existing code."*


## Q.54: How do you create a REST API? Write a sample GET and POST API.
Here’s a simple **Spring Boot REST API example** showing both **GET** and **POST**:

```java
import org.springframework.web.bind.annotation.*;

// Step 1: Mark this class as a REST controller
@RestController
@RequestMapping("/api/users")  // Base path
public class UserController {

    // Sample GET API
    @GetMapping("/{id}")
    public String getUser(@PathVariable int id) {
        return "Fetching user with ID: " + id;
    }

    // Sample POST API
    @PostMapping
    public String createUser(@RequestBody User user) {
        return "User created with name: " + user.getName();
    }
}

// Simple DTO class
class User {
    private String name;
    private int age;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

---

### 🔑 Key Points

* `@RestController` → Tells Spring this class exposes REST APIs (returns JSON by default).
* `@RequestMapping("/api/users")` → Sets the base URL.
* `@GetMapping("/{id}")` → Example: `GET /api/users/5` → returns user 5.
* `@PostMapping` + `@RequestBody` → Accepts JSON body, deserializes into `User` object.
  Example request:

  ```json
  POST /api/users
  {
    "name": "Aditya",
    "age": 25
  }
  ```

This is the minimal working REST API.


## Q.55: Your Java application is running slow in production. What steps would you take to investigate?
Investigating performance issues in a Java application requires following a systematic approach to identify the root cause of the problem, optimize the code, and monitor the application's behavior. Here are some steps you can follow:

1. **Profiling**: Start by profiling your application using tools like VisualVM, JProfiler, or YourKit. These tools can help you understand where your application spends most of its time during execution, identify bottlenecks, and pinpoint slow-performing methods that need optimization.

2. **Memory leaks**: Check for memory leaks by analyzing the heap usage over time. Tools such as VisualVM or MAT (Memory Analyzer Tool) can be used to identify objects that are consuming excessive memory and causing performance issues.

3. **Threading**: Investigate thread contention, deadlocks, and thread starvation. Use profiling tools to monitor the number of threads, their CPU usage, and their blocking times. If you find any evidence of these issues, consider refactoring your code to address them or adjusting the thread pool configuration if applicable.

4. **Concurrency bugs**: Check for concurrent programming errors such as race conditions, data inconsistency, or incorrect use of locks. Use debugging tools like JVisualVM or JMC (Java Mission Control) to analyze thread dumps and heap snapshots to identify potential issues.

5. **Optimization**: Once you've identified bottlenecks in your application, optimize the code by using performance-focused techniques such as caching, lazy loading, or algorithm optimization. You might also consider upgrading hardware resources if necessary.

6. **Monitoring and logging**: Implement proper monitoring and logging mechanisms to track the application's behavior over time. This includes using log4j, SLF4J, or other logging libraries for error reporting and performance metrics. Use these logs to analyze trends, identify patterns, and quickly respond to any new issues that arise.

7. **Testing**: Make sure you have a comprehensive testing strategy in place, including unit tests, integration tests, and load tests. Regularly test your application under different conditions to ensure it continues to perform well as changes are made to address performance issues.

8. **Collaboration**: Work closely with other team members, such as system administrators or DevOps engineers, to optimize the underlying infrastructure and configuration settings that might affect application performance. This includes database configurations, network settings, or hardware resources like CPU, RAM, and disk I/O.


## Q.56: What are some Key features of java 8?
Here’s a concise list of **key features introduced in Java 8** (most asked in interviews):


1. **Lambda Expressions**

   * Enables writing anonymous functions in a clean, functional style.

   ```java
   List<String> list = Arrays.asList("A","B","C");
   list.forEach(s -> System.out.println(s));
   ```

2. **Functional Interfaces**

   * Interfaces with a single abstract method (SAM).
   * Example: `Runnable`, `Callable`, `Comparator`, or custom with `@FunctionalInterface`.

3. **Streams API**

   * Functional-style operations on collections (map, filter, reduce).

   ```java
   List<Integer> nums = Arrays.asList(1,2,3,4);
   nums.stream().filter(n -> n%2==0).forEach(System.out::println);
   ```

4. **Default and Static Methods in Interfaces**

   * Interfaces can now have method implementations.

   ```java
   interface MyInterface {
       default void show(){ System.out.println("Default method"); }
       static void display(){ System.out.println("Static method"); }
   }
   ```

5. **Method References & Constructor References**

   * Shorter syntax for calling methods/constructors.

   ```java
   list.forEach(System.out::println);
   ```

6. **Optional Class**

   * To avoid `NullPointerException`.

   ```java
   Optional<String> str = Optional.ofNullable(null);
   System.out.println(str.orElse("default"));
   ```

7. **New Date and Time API (`java.time` package)**

   * Modern replacement for `Date` and `Calendar`.

   ```java
   LocalDate today = LocalDate.now();
   LocalDate tomorrow = today.plusDays(1);
   ```

9. **CompletableFuture & Enhanced Concurrency**

   * For better asynchronous programming compared to `Future`.

10. **Collectors & Stream Enhancements**

* `Collectors.toList()`, `toSet()`, `joining()`, `groupingBy()`.

---

✅ **Interview-Ready Summary:**
Java 8 introduced functional programming to Java with lambda expressions, streams, and functional interfaces. It also improved interfaces with default/static methods, added `Optional` to handle nulls safely, introduced the new Date/Time API, and enhanced concurrency with `CompletableFuture`.

---

Do you want me to also prepare a **1-minute crisp version** (just 4–5 must-mention features) for when the interviewer expects a quick answer?



## Q.57: Why are strings immutable in java?
Strings in Java are **immutable** (cannot be changed once created). The reasons are mainly around **security, performance, and reliability**:

---

### 🔑 Reasons Why Strings are Immutable in Java

1. **Security**

   * Strings are used in sensitive operations (like file paths, database URLs, network connections, class loading).
   * If `String` were mutable, malicious code could change the content after validation.
   * Example: Changing a database password string after authentication would be a huge risk.

2. **Caching & Performance (String Pool)**

   * Java maintains a **String Pool** in the heap.
   * If strings were mutable, two references pointing to `"Hello"` could be changed independently → breaking the pool’s guarantee of reusability.
   * Immutability makes strings safe to cache and share.

3. **Thread Safety**

   * Immutable objects are inherently **thread-safe**.
   * Multiple threads can share the same string without synchronization issues.

4. **Hashcode Consistency**

   * Strings are often used as **keys in HashMap/HashSet**.
   * If a string were mutable, changing its value would change its hashcode → breaking hashing and lookups.
   * Immutability guarantees consistent hashcode for a string.

5. **Reliability in APIs**

   * Many Java APIs expect strings not to change unexpectedly.
   * Immutable design avoids unpredictable bugs.

---

✅ **Interview-ready one-liner:**
Strings in Java are immutable to ensure security, enable string pool caching, guarantee thread-safety, and provide consistent behavior in collections like HashMap.

## Q.58: What happens when this code is executed?
```java
class A {}
class B extends A {}

A obj = new B();
```

> When `A obj = new B();` executes, the JVM creates a `B` object in the heap and assigns its reference to a variable `obj` of type `A` on the stack. This is **upcasting** — the compiler treats `obj` as type `A` (so only `A`’s members are accessible), but at runtime the actual object is of type `B`, enabling **runtime polymorphism** if methods are overridden.


## Q.59: What's the use of static keyword in java?
Here is a clean, **copy-paste ready** version for your notes:

---

### `static` Keyword in Java

In Java, the `static` keyword is used to make a variable, method, block, or nested class belong to the **class** instead of to an **object (instance)**. Only one copy is created and shared among all objects.

---

#### 1. Static Variable (Class Variable)

* Belongs to the class.
* Single shared copy for all objects.
* Memory allocated once at class loading.
* Accessed using the class name.

```java
class Student {
    static String college = "IIT";
    int rollNo;
}
```

Access:

```java
Student.college;
```

---

#### 2. Static Method (Class Method)

* Can be called without creating an object.
* Can access only static data directly.
* Cannot use `this` and `super`.
* Used for utility methods.

```java
class MathUtils {
    static int add(int a, int b) {
        return a + b;
    }
}
```

Call:

```java
MathUtils.add(5, 3);
```

---

#### 3. Static Block

* Used to initialize static variables.
* Executes once when the class is loaded.
* Executes before `main()`.

```java
class Demo {
    static int x;

    static {
        x = 10;
    }
}
```

---

#### 4. Static Nested Class

* Does not require an object of the outer class.
* Can access only static members of the outer class.

```java
class Outer {
    static class Inner {
        void show() {
            System.out.println("Static inner class");
        }
    }
}
```

Object creation:

```java
Outer.Inner obj = new Outer.Inner();
```

---

#### 5. Static `main` Method

* JVM calls `main` without creating an object.

```java
public static void main(String[] args)
```

---

#### Key Points

* Static members belong to the **class**, not the object.
* Static data is **shared**.
* Static methods cannot access **non-static** members directly.
* `this` and `super` cannot be used in static context.

---

#### Summary Table

| Feature             | Belongs To | Object Required |
| ------------------- | ---------- | --------------- |
| static variable     | Class      | No              |
| static method       | Class      | No              |
| static block        | Class      | No              |
| static nested class | Class      | No              |

---

**One-line definition**:
`static` is used to create class-level members that are shared by all objects.


## Q.60: Why do wrapper classes exist in Java? What extra functionality do they provide?
Wrapper classes in **Java** exist primarily to bridge the gap between **primitive types** (like `int`, `double`, `boolean`) and **object-oriented features** of the Java language.

### 1. **Primitives are not objects**

Java is an object-oriented language, but its primitive types are **not objects**.
Many Java APIs—especially collections—require objects, not primitives.

Example:

```java
List<int> numbers = new ArrayList<>(); // ❌ Not allowed
List<Integer> numbers = new ArrayList<>(); // ✅ Works because Integer is an object
```

Wrapper classes allow primitives to be **used where objects are required**.

---

### 2. **To provide utility methods**

Primitive types have no methods, but wrapper classes provide **useful helper methods**, such as:

#### Parsing strings:

```java
int n = Integer.parseInt("123");       // String → int
double val = Double.parseDouble("3.14");
```

#### Converting to string:

```java
String s = Integer.toString(10);
```

#### Getting metadata:

```java
int max = Integer.MAX_VALUE;
int bytes = Integer.BYTES;
```

---

### 3. **To allow null values**

Primitives **cannot be null**, but wrapper class objects can.

Example:

```java
Integer x = null;  // Allowed
int y = null;      // ❌ Not allowed
```

This is important when working with databases, JSON, optional fields, etc.

---

### 4. **Autoboxing and unboxing**

Wrapper classes enable automatic conversion between primitives and their object equivalents.

```java
Integer x = 5;  // autoboxing: int → Integer
int y = x;      // unboxing: Integer → int
```

This improves developer convenience while maintaining performance benefits of primitives.

---

### 🎯 **Summary: What Extra Functionality Do Wrapper Classes Provide?**

| Primitive | Wrapper Class | Additional Features                               |
| --------- | ------------- | ------------------------------------------------- |
| `int`     | `Integer`     | Parsing, constants, nullability, generics support |
| `double`  | `Double`      | Methods like `isNaN()`, parsing, metadata         |
| `boolean` | `Boolean`     | String conversion, constants (`TRUE`, `FALSE`)    |
| ...       | ...           | ...                                               |

#### **Extra Features Provided by Wrapper Classes**

* Can be used in **collections** and generic classes
* Hold **null** values
* Provide **utility methods** for parsing, converting, comparing
* Support **autoboxing/unboxing**
* Provide **metadata constants** (e.g., `MAX_VALUE`, `MIN_VALUE`)


## Q.61: What is reflection in java? Why and how to use it?
Reflection in Java is a powerful feature that lets your program **inspect and manipulate classes, methods, fields, and constructors at runtime** — even if you don’t know their names at compile time.

---

✅ **What is Reflection?**

Reflection is a mechanism in Java that allows you to:

* Examine a class’s structure (methods, fields, constructors)
* Instantiate objects dynamically
* Invoke methods dynamically
* Access or modify private fields/methods
* Load classes at runtime

All of this is done through the **java.lang.reflect** package and the **Class** object.

---

✅ **Why Use Reflection?**

Reflection enables dynamic and flexible behavior that is otherwise impossible with static typing.

### **Common Use Cases**

1. **Frameworks & Libraries**

   * Spring, Hibernate, Gson, JUnit use reflection heavily.
   * They inspect annotations, inject dependencies, map data to objects, etc.

2. **Dependency Injection / Inversion of Control**

   * Creating objects without calling `new` explicitly.

3. **Serialization / Deserialization**

   * Accessing private fields to convert objects to/from JSON or XML.

4. **Building Generic Tools**

   * IDEs, debuggers, and testing tools use reflection for runtime introspection.

5. **Plugin Systems**

   * Load classes dynamically based on configuration.

---

✅ **How to Use Reflection?**

### **1. Getting the Class object**

```java
Class<?> cls1 = Class.forName("com.example.MyClass");
Class<?> cls2 = obj.getClass();
Class<?> cls3 = MyClass.class;
```

---

### **2. Creating objects at runtime**

```java
Class<?> cls = Class.forName("com.example.Person");
Object obj = cls.getDeclaredConstructor().newInstance();
```

---

### **3. Accessing fields**

```java
Field field = cls.getDeclaredField("name");
field.setAccessible(true); // bypass private
field.set(obj, "Aditya");
System.out.println(field.get(obj));
```

---

### **4. Invoking methods**

```java
Method method = cls.getDeclaredMethod("sayHello");
method.setAccessible(true);
method.invoke(obj);
```

---

### **5. Accessing constructors**

```java
Constructor<?> constructor = cls.getConstructor(String.class);
Object obj2 = constructor.newInstance("Aditya");
```

---

⚠️ **Drawbacks / When NOT to Use Reflection**

Reflection is powerful but comes with trade-offs:

| Issue                        | Explanation                                      |
| ---------------------------- | ------------------------------------------------ |
| **Performance overhead**     | Reflective calls are slower.                     |
| **Security risks**           | Can break encapsulation (`setAccessible(true)`). |
| **Harder to maintain**       | Code becomes dynamic and less readable.          |
| **Compile-time safety lost** | Errors occur at runtime.                         |

**Use reflection only when necessary** — mainly in frameworks, libraries, or dynamic systems.

---

✅ **Simple Example**

### **Class**

```java
class Person {
    private String name;

    private void greet() {
        System.out.println("Hello, " + name);
    }
}
```

### **Reflection**

```java
Person p = new Person();

Class<?> cls = p.getClass();

// Access private field
Field f = cls.getDeclaredField("name");
f.setAccessible(true);
f.set(p, "Aditya");

// Call private method
Method m = cls.getDeclaredMethod("greet");
m.setAccessible(true);
m.invoke(p);
```
