# Most Common Java Interview Questions

A curated, modular set of high‑yield Java interview questions with detailed answers and concise code snippets. Expand or append sections as you practice.

## Table of Contents
- [Core Java](#core-java)
  - [What is the JDK, JRE, and JVM?](#what-is-the-jdk-jre-and-jvm)
  - [Is Java pass-by-value or pass-by-reference?](#is-java-pass-by-value-or-pass-by-reference)
  - [`==` vs `.equals()`](#-vs-equals)
  - [Immutability and `String` interning](#what-is-immutability-why-is-string-immutable-what-is-string-interning)
- [Object-Oriented Programming (OOP)](#object-oriented-programming-oop)
  - [Explain the four pillars of OOP](#explain-the-four-pillars-of-oop)
  - [Abstract class vs interface](#abstract-class-vs-interface)
  - [Inheritance vs composition](#inheritance-vs-composition)
- [Collections Framework](#collections-framework)
  - [`ArrayList` vs `LinkedList`](#arraylist-vs-linkedlist)
  - [`HashMap` internals and `equals()`/`hashCode()`](#how-does-hashmap-work-why-are-equals-and-hashcode-important)
  - [Fail-fast vs fail-safe iterators](#fail-fast-vs-fail-safe-iterators)
  - [Ordering in `HashMap`, `LinkedHashMap`, `TreeMap`](#ordering-differences-hashmap-vs-linkedhashmap-vs-treemap)
- [Generics](#generics)
  - [Type erasure and implications](#what-is-type-erasure-implications)
  - [Wildcards and PECS](#wildcards-and-pecs)
  - [Bounded type parameters](#bounded-type-parameters)
- [Multithreading](#multithreading)
  - [`Thread` vs `Runnable` vs `Callable`](#thread-vs-runnable-vs-callable)
  - [`start()` vs `run()`](#start-vs-run)
  - [`synchronized`, intrinsic locks, and reentrancy](#synchronized-intrinsic-locks-and-reentrancy)
  - [`volatile` semantics](#what-does-volatile-do)
- [Concurrency](#concurrency)
  - [`wait()/notify()` vs `Lock`/`Condition`](#waitnotify-vs-lockcondition)
  - [Producer–consumer with `BlockingQueue`](#producerconsumer-with-blockingqueue)
  - [Deadlock, livelock, starvation](#deadlock-livelock-starvation)
  - [Thread confinement and `ThreadLocal`](#thread-confinement-and-threadlocal)
- [Memory Management](#memory-management)
  - [Stack vs heap; object layout](#stack-vs-heap-object-layout)
  - [Classloading and Metaspace](#classloading-and-metaspace)
- [Garbage Collection (GC)](#garbage-collection-gc)
  - [Generational GC basics](#generational-gc-basics)
  - [Popular collectors](#popular-collectors-at-a-glance)
  - [Reachability and GC roots](#reachability-and-gc-roots)
- [Exception Handling](#exception-handling)
  - [Checked vs unchecked exceptions](#checked-vs-unchecked-exceptions)
  - [Best practices and try-with-resources](#best-practices)
  - [Custom exceptions](#custom-exceptions)
- [Java 8: Lambdas, Streams, Method References, Optional](#java-8-lambdas-streams-method-references-optional)
  - [Functional interfaces and lambda syntax](#functional-interfaces-and-lambda-syntax)
  - [Stream pipeline anatomy](#stream-pipeline-anatomy)
  - [Common stream operations](#common-stream-operations)
  - [`Optional` usage](#optional-usage)
- [Design Patterns](#design-patterns)
  - [Singleton (safe variants)](#singleton-thread-safe)
  - [Factory and Abstract Factory](#factory-and-abstract-factory)
  - [Builder](#builder)
  - [Strategy](#strategy)
  - [Observer](#observer)
  - [Decorator](#decorator)
  - [Adapter](#adapter)
  - [Template Method](#template-method)
  - [Proxy](#proxy)
  - [Command](#command)
- [Appendix: Quick Checklists](#appendix-quick-checklists)
  - [Concurrency gotchas](#concurrency-gotchas)
  - [`equals`/`hashCode` rules](#equalshashcode-rules)
  - [Stream performance tips](#stream-performance-tips)
- [How to Extend This Document](#how-to-extend-this-document)

---

## Core Java

### What is the JDK, JRE, and JVM?
- JDK (Java Development Kit): Tools for development (compiler `javac`, javadoc, debuggers) plus a JRE.
- JRE (Java Runtime Environment): Libraries and JVM needed to run Java applications; no compiler.
- JVM (Java Virtual Machine): The engine that executes Java bytecode; provides classloading, bytecode verification, JIT compilation, and GC. Different JVM implementations may exist, but the Java language targets the JVM via bytecode.

### Is Java pass-by-value or pass-by-reference?
- Java is strictly pass-by-value. For object references, the reference itself is passed by value (i.e., a copy of the reference). You can mutate the object via the copied reference, but you cannot rebind the caller’s reference.

```java
class Box { int n; }
void mutate(Box b) { b.n = 42; }      // affects caller
void rebind(Box b) { b = new Box(); } // does not affect caller
```

### `==` vs `.equals()`
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

### What is immutability? Why is `String` immutable? What is string interning?
- Immutable objects cannot change state after construction, enabling thread-safety, caching, and safe sharing.
- `String` is immutable for security (e.g., classloaders, URLs), caching (string pool), and thread-safety.
- String interning in Java refers to the optimization technique where the JVM stores only one copy of a given string literal in memory, regardless of how many times that exact string appears in the source code. This means that if two string literals have the same value, they will refer to the same object instance in memory.

```java
String a = "hello";
String b = "hello";
System.out.println(a == b); // true (pooled)
String c = new String("hello");
System.out.println(a == c); // false (new object)
```

---

## Object-Oriented Programming (OOP)

### Explain the four pillars of OOP
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

### Abstract class vs interface
- Abstract class: Can have state, constructors, and both abstract/concrete methods. Single inheritance only.
- Interface: Primarily behavior contracts; can have default/static methods; a class can implement multiple interfaces. No instance state (except constants).
- Choose interface when modeling capabilities; abstract class when providing a common base with shared state or protected helpers.

### Inheritance vs composition
- Inheritance: “is-a” relationship; can lead to tight coupling and brittle hierarchies.
- Composition: “has-a” relationship; favors delegation and better encapsulation.
- Prefer composition for flexibility; reserve inheritance for clear is-a relationships.

```java
class Engine { void start() {} }
class Car { private final Engine engine = new Engine(); void start() { engine.start(); } } // composition
```

---

## Collections Framework

### `ArrayList` vs `LinkedList`
- `ArrayList`: Backed by dynamic array; O(1) random access; amortized O(1) append; inserting/removing in middle is O(n).
- `LinkedList`: Doubly-linked list; O(1) add/remove at ends; random access is O(n); higher memory overhead.
- Prefer `ArrayList` unless frequent mid-list insertions/removals dominate and access patterns justify `LinkedList`.

```java
List<Integer> a = new ArrayList<>();
a.add(1); a.add(2);
List<Integer> l = new LinkedList<>();
l.addFirst(0);
```

### How does `HashMap` work? Why are `equals()` and `hashCode()` important?
- `HashMap` uses an array of buckets; keys hash to a bucket index. Within a bucket, collisions are resolved via linked lists or balanced trees (since Java 8).
- Lookup: compute hash → index → traverse bucket → compare using `equals`.
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

### Fail-fast vs fail-safe iterators
- Fail-fast (e.g., most `java.util` collections): Detect structural modification during iteration and throw `ConcurrentModificationException`.
- Fail-safe (e.g., `CopyOnWriteArrayList`, `ConcurrentHashMap` iterators): Iterate over a snapshot; updates may not be visible during iteration; no CME.

```java
List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
for (Integer x : list) {
  if (x == 2) list.add(99); // ConcurrentModificationException
}
```

### Ordering differences: `HashMap` vs `LinkedHashMap` vs `TreeMap`
- `HashMap`: No ordering guarantees.
- `LinkedHashMap`: Predictable iteration order (insertion or access order).
- `TreeMap`: Sorted by natural order or a provided `Comparator`.

```java
Map<String,Integer> lm = new LinkedHashMap<>();
Map<String,Integer> tm = new TreeMap<>(Comparator.reverseOrder());
```

---

## Generics

### What is type erasure? Implications?
- Java generics are implemented via type erasure: generic type info is not available at runtime (mostly).
- Implications: No primitive type parameters; no `new T[]`; no runtime checks of parameterized type arguments; `instanceof List<String>` is illegal.

```java
// Illegal: List<String> at runtime is just List
// if (list instanceof List<String>) { ... } // compile error
boolean b = list instanceof List; // allowed
```

### Wildcards and PECS
- PECS: Producer Extends, Consumer Super.
  - Use `? extends T` when reading (producer).
  - Use `? super T` when writing (consumer).

```java
void readNumbers(List<? extends Number> src) { Number n = src.get(0); }
void writeIntegers(List<? super Integer> dst) { dst.add(42); }
```

### Bounded type parameters
- Upper bounds: `<T extends Number & Comparable<T>>`
- Lower bounds apply to wildcards only (`? super T`), not type parameters.
- Multiple bounds: class first, then interfaces.

```java
static <T extends Comparable<T>> T max(T a, T b) { return a.compareTo(b) >= 0 ? a : b; }
```

---

## Multithreading

### `Thread` vs `Runnable` vs `Callable`
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

### `start()` vs `run()`
- `run()`: The task’s logic; calling it directly runs in the current thread.
- `start()`: Creates a new OS thread that then calls `run()` asynchronously.

```java
new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
```

### `synchronized`, intrinsic locks, and reentrancy
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


### What does `volatile` do?
- Ensures visibility and ordering for a single variable: reads see the latest write; prevents reordering across volatile boundaries.
- Does not provide atomicity for compound actions (e.g., `x++`).

```java
volatile boolean running = true;
void stop() { running = false; } // other thread will see it
```

---

## Concurrency

### `wait()/notify()` vs `Lock`/`Condition`

1. `wait()`: The `wait()` method allows a thread to voluntarily relinquish the object's lock and enter a waiting state until it is notified by another thread. When a thread calls the `wait()` method, it releases the lock on the object, allowing other threads to access that object.

2. `notify()`: The `notify()` method wakes up one of the waiting threads that are currently queued on an object due to calling `wait()`. When a thread calls `notify()`, it does not determine which specific waiting thread should be awakened, but one of them is chosen arbitrarily by the JVM.

3. `notifyAll()`: The `notifyAll()` method wakes up all the threads that are currently queued on an object due to calling `wait()`. When a thread calls `notifyAll()`, it notifies all waiting threads and allows them to compete for the lock again.

These methods are often used in situations where multiple threads need to coordinate their actions, such as when working with shared resources or data structures that require specific states before other operations can be performed. It's important to note that `wait()`, `notify()`, and `notifyAll()` should only be called from inside a synchronized block or method to ensure proper access control to the shared object being waited upon.

The main difference between `notify()` and `notifyAll()` lies in the number of threads that will be awakened: using `notify()` wakes up one thread, while `notifyAll()` wakes up all waiting threads. This means that if there are multiple threads waiting on an object when `notifyAll()` is called, they will all be notified and compete for the lock again, whereas with `notify()`, only one thread will be awakened at a time.

To better understand their usage in real-life scenarios, I recommend checking out examples that demonstrate how these methods can be used to implement producer-consumer patterns or resource management within concurrent programs.
- `Lock`/`Condition` (from `java.util.concurrent.locks`) provide more flexible features (tryLock, fairness, multiple conditions).

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

### Producer–consumer with `BlockingQueue`
- Simplifies coordination; built-in blocking semantics, no manual signaling.

```java
BlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
// producer
new Thread(() -> { try { for (int i=0;i<100;i++) q.put(i); } catch (InterruptedException ignored) {} }).start();
// consumer
new Thread(() -> { try { while (true) System.out.println(q.take()); } catch (InterruptedException ignored) {} }).start();
```

### Deadlock, livelock, starvation
In concurrent programming, there are three main issues related to synchronization:

1. **Deadlock**: A deadlock occurs when two or more threads are unable to proceed because each thread is waiting for a resource that the other thread holds. The resulting situation leads to both threads being blocked indefinitely, causing a standstill in the program. To avoid deadlocks, it's essential to follow best practices such as ordering locks, avoiding circular waits, and implementing timeouts.

2. **Livelock**: Livelock is a situation where two or more threads are continuously trying to change each other's state but never making progress because the changes made by one thread invalidate the assumptions of the other thread before it can make any meaningful progress. Unlike deadlock, livelock does not involve waiting for resources but rather continuous communication between threads that results in no net forward progress. To avoid livelock, try to design your algorithms and data structures to prevent circular dependencies and ensure that changes made by one thread can be reasonably expected to persist long enough for the other thread to make some progress.

3. **Starvation**: Starvation occurs when a thread is repeatedly denied access to a shared resource or processor time, causing it to be deprived of resources for an extended period. This results in the thread never making any meaningful progress while other threads continue running. To avoid starvation, implement fair scheduling policies and ensure that each thread gets a chance to execute within a reasonable timeframe. Priority-based scheduling or implementing self-correcting algorithms can help prevent starvation in multithreaded applications.

By understanding these issues and applying good design principles, developers can create more robust and efficient concurrent programs that avoid deadlocks, livelock, and starvation scenarios.


---

## Memory Management

### Stack vs heap memory
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

## Garbage Collection (GC)

      In Java, the Garbage Collector (GC) is a built-in component responsible for automatically managing memory allocation and deallocation of objects that are no longer needed by the program. The primary goal of garbage collection is to free up resources occupied by unused objects, prevent memory leaks, and minimize the impact on application performance due to fragmented memory.

The Java garbage collector operates in two main modes:

1. **Serial GC**: A single-threaded garbage collector designed for applications with limited memory requirements and simple workloads. Serial GC is simple to understand but can be slower during garbage collection compared to other collectors.

2. **Parallel GC**: A multi-threaded garbage collector that runs concurrently with the application, allowing it to collect garbage faster than the serial collector without significantly impacting the application's performance. Parallel GC is suitable for applications with more demanding memory requirements and complex workloads.

3. **Concurrent Mark Sweep (CMS)**: A parallel, concurrent low-pause-time garbage collector that runs in two phases: marking (scanning the heap to identify live objects) and sweeping (deallocating unreferenced objects). CMS is suitable for applications where minimizing pauses during garbage collection is crucial.

4. **G1 Garbage Collector**: A next-generation, concurrent collector designed to minimize application pauses while achieving low memory occupancy. G1 divides the heap into equal-sized regions and performs parallel garbage collection by processing multiple regions simultaneously. It also allows for more flexible heap size configuration and better scalability.

To optimize garbage collection in Java applications:

- Use appropriate memory settings (Xms, Xmx) to allocate the correct amount of memory based on your application's needs.
- Avoid creating excessively large objects or holding onto unreferenced objects for extended periods. This can help reduce garbage collection overhead and minimize memory fragmentation.
- Utilize finalizers sparingly since they add complexity and can negatively impact application performance during garbage collection.
- Profile your application's memory usage to identify potential memory leaks, allocate more memory as needed, or optimize your code for better memory management.

By understanding the basics of Java garbage collection, you can develop more efficient and reliable applications that manage memory effectively without requiring manual memory deallocation.

---

## Exception Handling

### Checked vs unchecked exceptions
Got it. Let’s go step by step.

---

**What are Exceptions in Java?**

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

### A list of **most common Java exception handling interview questions** (with short notes so you can make revision notes):

**Basics**

1. **What is the difference between checked and unchecked exceptions?**

   * Checked: Checked at compile time (IOException, SQLException).
   * Unchecked: Runtime exceptions (NullPointerException, ArrayIndexOutOfBounds).

2. **What is the difference between `throw` and `throws`?**

   * `throw`: Used to explicitly throw an exception inside a method.
   * `throws`: Declares exceptions a method can throw.

3. **What is the difference between `final`, `finally`, and `finalize()`?**

   * `final`: Constant, prevent inheritance/overriding.
   * `finally`: Always executes after try-catch (used for cleanup).
   * `finalize()`: Called by GC before object destruction.

**Core Handling**

4. **Can `finally` block be skipped?**

   * Yes, if JVM exits (`System.exit(0)`) or fatal error occurs.

5. **Can we have multiple `catch` blocks?**

   * Yes, from specific → general exception types.

6. **Can a `try` block exist without `catch`?**

   * Yes, but must have `finally`.

7. **Can we catch multiple exceptions in one `catch`?**

   * Yes, Java 7+: `catch(IOException | SQLException e)`.


**Advanced**

8. **What happens if an exception occurs inside `finally`?**

   * It overrides the exception from `try/catch`.

9. **Difference between `Error` and `Exception`?**

   * Exception: recoverable (business logic issues).
   * Error: unrecoverable (OutOfMemoryError, StackOverflowError).

10. **What is `try-with-resources`?**

* Introduced in Java 7 for AutoCloseable resources (closes automatically).

11. **Custom exceptions in Java?**

* Extend `Exception` (checked) or `RuntimeException` (unchecked).

12. **What happens if exception is not caught?**

* Propagates up call stack → JVM handles → prints stack trace → program exits.


---

## Java 8: Lambdas, Streams, Method References, Optional

### Functional interfaces and lambda syntax
**Functional Interfaces:**

- A functional interface is an interface with a single abstract method (SAM), which enables the use of lambda expressions and method references in Java.
- Functional interfaces are annotated with `@FunctionalInterface` to ensure that they only contain one abstract method.
- Some built-in functional interfaces in Java include `Runnable`, `Callable`, `Consumer`, `Supplier`, `Function`, `Predicate`, and `BiFunction`, among others.

**Lambda Syntax:**

- Lambda expressions are anonymous functions that can be used to create instances of functional interfaces without explicitly defining a separate class for each function implementation.
- The general structure of a lambda expression is as follows:
  ```
    (parameters) -> { statements }
  ```
- You can also omit the return keyword and expression if the body of the lambda consists of only one statement, or if the functional interface's method returns void.
- Lambda expressions can capture and store references to any variables from the enclosing scope that are used in the lambda body (known as capturing variables).
- To call a lambda expression, you invoke its associated functional interface method on an instance of the appropriate class (e.g., `MyLambda myLambda = () -> System.out.println("Hello!");` and then `myLambda.invoke()` or `myLambda.run()`, depending on the functional interface).

**Method references:**

* Method references are a feature in Java that allows you to refer to a method directly without creating an instance of the class or implementing an interface. This provides a concise way to use pre-existing methods as if they were lambda expressions or functional interfaces.

* Here's an example demonstrating method reference syntax:

  ```java
  List<String> list = Arrays.asList("Apple", "Banana", "Orange");
  list.sort(String::compareTo);
  ```

  In this example, `String::compareTo` is a method reference that refers to the `compareTo()` method of the `String` class. Instead of creating an anonymous class or lambda expression to sort the list, you can use the method reference directly as an argument for the `sort()` method.

  There are three types of method references:
  1. Instance Method Reference - `Object::instanceMethod` refers to a non-static instance method of any object. You'll need to provide an instance of the class when using this method reference.
  2. Static Method Reference - `Class::staticMethod` refers to a static method in any class.
  3. Constructor Method Reference - `ClassName::new` refers to a constructor of a specific class.


To summarize:
- Functional interfaces are essential for functional programming in Java, as they allow for the use of lambda expressions and method references to create anonymous functions that implement a single abstract method.
- Lambda syntax provides a concise way to define anonymous functions, with the general structure consisting of parameters, an arrow (`->`), and the function body.
- Method references are a concise way to refer to pre-existing methods as if they were lambda expressions or functional interfaces, providing an efficient and readable alternative for common operations.
- Instance method reference syntax is `Object::instanceMethod`, static method reference syntax is `Class::staticMethod`, and constructor method reference syntax is `ClassName::new`.


### Stream pipeline anatomy
  Sure! The Stream API in Java offers a powerful way to process collections of data using functional programming concepts. Here's the anatomy of a basic stream pipeline:

1. Source - A stream pipeline starts with a source, which is a collection (such as an array, list, or set) or a stream itself. The source provides elements for processing in the pipeline.

2. Intermediate Operations - These operations transform the input data and produce new streams containing the results. Each intermediate operation can be chained together to create complex pipelines. Common intermediate operations include:
   - `filter(Predicate predicate)` - Filters elements based on a provided predicate.
   - `map(Function mapper)` - Transforms each element using a provided function.
   - `distinct()` - Removes duplcate elements from the stream.
   - `skip(long n)` - Skips the first n elements of the stream and returns the remaining elements.
   - `limit(long n)` - Returns only the first n elements of the stream.

3. Terminal Operations - These operations consume the elements in the pipeline, produce a result (such as a summary statistic or reduced value), and then terminate the pipeline. Common terminal operations include:
   - `count()` - Returns the number of elements in the stream.
   - `forEach(Consumer action)` - Applies an action to each element of the stream.
   - `collect(Collector collector)` - Collects the elements into a specific data structure (such as a list or set).
   - `max()`, `min()`, `sum()`, and other aggregate operations - Returns the maximum, minimum, sum, or other aggregate results of the stream.

Here's an example demonstrating a simple stream pipeline:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
               .filter(n -> n % 2 == 0) // filter odd numbers
               .map(n -> n * n)          // square each number
               .reduce(0, Integer::sum); // sum up the squares of even numbers
```

In this example, a stream is created from an array of integers, filtered to only include even numbers, transformed by squaring each number, and finally reduced to find the sum of all squared even numbers.

To summarize:
- A stream pipeline consists of a source (a collection or stream), intermediate operations that transform data, and terminal operations that produce results from processed elements.
- Common intermediate operations include filtering, mapping, distinctions, skipping, limiting, and sorting, while common terminal operations include counting, forEach actions, collecting, aggregating, and reducing.
- By chaining together multiple operations in a stream pipeline, you can create powerful data processing solutions efficiently and concisely using functional programming concepts.

```java
int sum = Stream.of(1,2,3,4,5)
  .filter(x -> x % 2 == 1)
  .mapToInt(Integer::intValue)
  .sum();
```

### `Optional` usage
`Optional<T>` is a container object that either contains a non-null value or represents an empty value (null). It helps manage null values more effectively by providing a way to handle situations where a method might return null.


**Why not just use `if(obj != null)`?**

* Yes, it works, but **null references are dangerous** because:

  * They can easily be forgotten and cause `NullPointerException` (NPE).
  * `null` does not express *intent* — we don’t know whether it means "missing value", "not initialized", or "error".


**Why use `Optional` instead?**

`Optional<T>` is a **container object** that may or may not contain a value. It was introduced in **Java 8** to handle optional values in a more **expressive and safer way**.

Benefits:

1. **Readability / Intent** → Code shows clearly when a value might be absent.

   ```java
   Optional<String> username = findUserById(id); 
   ```

   This tells the reader: the user *might not exist*.

2. **Avoid null checks** → Instead of repeating `if(obj != null)`, we can use built-in safe methods:

   ```java
   username.ifPresent(u -> System.out.println("Hello " + u));
   String name = username.orElse("Guest");
   ```

3. **Encourages functional style** → Use `map`, `filter`, `flatMap` to chain transformations safely:

   ```java
   String upper = username.map(String::toUpperCase).orElse("UNKNOWN");
   ```

4. **Null-safety in APIs** → When an API returns `Optional<T>`, the caller *knows* they must handle the absence of a value.
   Contrast with returning `null` → caller might forget the null check.

---

**Example: Traditional vs Optional**

**Traditional null check:**

```java
String name = getName();
if (name != null) {
    System.out.println(name.toUpperCase());
}
```

**Using Optional:**

```java
Optional<String> name = getNameOptional();
name.map(String::toUpperCase).ifPresent(System.out::println);
```

---

### ⚠️ When NOT to use `Optional`

* As **fields in DTOs/entities** (bad for serialization frameworks, adds overhead).
* In **performance-critical code** (unboxing overhead).
* Use it mainly for **method return types** to express "might be missing".

---

👉 So the difference is:

* `obj != null` is a raw null check (unsafe, ambiguous).
* `Optional` is **a semantic, safer, and more expressive way** of modeling "nullable" values, reducing accidental `NullPointerException`s.


### Q. What is the difference between `.of()` and `.ofNullable()` ?
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

---


## Appendix: Quick Checklists

### Concurrency gotchas
- Avoid publishing partially constructed objects.
- Use immutable or effectively immutable data where possible.
- Prefer high-level concurrent utilities over low-level `wait/notify`.
- Keep synchronized regions small; avoid nested locks; define a lock ordering.

### `equals`/`hashCode` rules
- Reflexive, symmetric, transitive, consistent; `x.equals(null)` is false.
- Equal objects must have equal hash codes.
- Use the same fields in both methods; consider `Objects.equals`/`Objects.hash`.

### Stream performance tips
- Avoid unnecessary boxing/unboxing (`mapToInt`, `mapToLong`).
- Beware `parallel()` unless you have CPU-bound work and sensible splitting.
- Prefer collectors over manual mutable accumulators unless necessary.

---

## How to Extend This Document
- Add new sections as `###` under an existing domain (e.g., “Java 17 features”).
- Append new questions using the pattern:
  - Question as `###`
  - Concise theory answer
  - Short code snippet demonstrating usage
- Keep examples minimal and focused on the concept being tested.


