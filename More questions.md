## Q.1. What is the difference between Hashmap and HashTable and ConcurrentHashMap?
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

## Q.2. Draw an **internal working diagram of ConcurrentHashMap locking** (bucket-level locking vs Hashtable global lock)?

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

## Q.3. How does Garbage Collection work in JVM?

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

## Q.3. Explain volatile and synchronized in multithreading?

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



## Q.4. Deep copy vs Shallow copy?


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

## Q.5. How does the Java memory model work (Heap, Stack, Metaspace)?
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


## Q.6. What happens if two keys have the same hashcode?
In Java, if two keys have the same **hashCode**, the following happens inside a `HashMap` (or similar hash-based collections):

1. **Bucket selection:** Both keys will map to the same bucket (index) in the hash table.
2. **Collision resolution:** Since they are in the same bucket, Java uses **equals()** to check if the keys are actually equal.

   * If `equals()` returns **true**, the new value will replace the old value.
   * If `equals()` returns **false**, both keys coexist in the same bucket, stored as a **linked list** (before Java 8) or **balanced tree** (Java 8 onwards if bucket size exceeds threshold).

👉 Summary:

* Same `hashCode` → same bucket.
* `equals()` true → overwrite.
* `equals()` false → both stored (collision handled).




## Q.7. Difference between synchronized block and ConcurrentHashMap?
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

## Q.8. Why ConcurrentHashMap are Much faster than synchronized blocks under high concurrency?
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



## Q.9. How does Garbage Collection work in JVM?
The core process of JVM Garbage Collection typically involves the following phases:

**Marking Phase:**

The garbage collector identifies "live" or "reachable" objects. It starts by identifying a set of "root" objects (e.g., local variables on the stack, static variables, active threads). From these roots, it recursively traverses the object graph, marking all objects that are reachable. Any object not marked during this phase is considered "dead" or "unreachable" and eligible for collection.

**Sweeping Phase:**

After marking, the garbage collector scans the heap and identifies the memory occupied by the unmarked (dead) objects. This memory is then reclaimed and added back to the free memory pool, making it available for future object allocations. 



## Q.10 Explain the Java Memory Model briefly?
The **Java Memory Model (JMM)** is a specification that defines how threads interact with memory in a Java Virtual Machine (JVM), particularly in multithreaded environments. It establishes rules and guarantees regarding the visibility, ordering, and atomicity of variable access, ensuring consistent behavior of concurrent programs across different hardware and JVM implementations.

Key aspects of the JMM include:

* **Visibility:** The JMM ensures that changes made by one thread to shared variables become visible to other threads in a timely and predictable manner. Without these guarantees, threads might operate on stale or inconsistent data due to caching or compiler optimizations.
* **Ordering:** The JMM defines rules for the permissible reordering of instructions by compilers and processors. While reordering can optimize performance, the JMM ensures that such reorderings do not alter the observable behavior of a multithreaded program, particularly with respect to synchronization constructs.
* **Atomicity:** The JMM guarantees that certain operations, like reading or writing a primitive variable (excluding long and double which are not guaranteed to be atomic without volatile), are performed as a single, indivisible unit, preventing partial updates or data corruption.
* **Happens-Before Relationship:** This is a core concept in the JMM, establishing a partial ordering of operations. If action A happens-before action B, then the effects of A are visible to B. This relationship is crucial for reasoning about memory visibility and ensuring correct synchronization. Examples include the happens-before relationship between unlocking a monitor and a subsequent lock on the same monitor, or writing to a volatile variable and a subsequent read of that variable.

The JMM is fundamental for developing robust and reliable concurrent applications in Java, providing the necessary framework for thread safety and preventing issues like data races and inconsistent state. Developers utilize synchronization constructs like `synchronized` blocks, `volatile` variables, and concurrent utility classes (e.g., from `java.util.concurrent`) to adhere to the JMM's guarantees and ensure correct program behavior.


## Q.11 What is Future in java? What are some more related concepts to Future in java?
 In Java, a `Future` is an object that represents the result of an asynchronous computation. It allows you to carry out a computation in the background and obtain its result without waiting for it to complete, or check if the computation is still running.

The `Future` interface in Java provides methods such as:

1. `get()`: Blocks until the computation is done and retrieves the result.
2. `isDone()`: Determines whether the computation has completed.
3. `cancel(boolean mayInterruptIfRunning)`: Attempts to cancel a computation before it finishes, if possible.

Related concepts to Future in Java include:

1. ExecutorService: An interface that manages a group of threads and executes tasks asynchronously. It provides methods for submitting tasks (such as `submit()` for returning a `Future` object) and shutting down the thread pool.
2. Callable: A functional interface in Java. Extends the Runnable interface, but instead of a void return type, it has a generic return type. Callables are used to specify a task that returns a result when executed asynchronously.
3. CompletionService: An interface that manages a pool of Futures and returns completed tasks in a block-less manner using the `take()` method. This allows for more efficient handling of multiple asynchronous computations.
4. Fork/Join Framework: A library provided by Java that implements an efficient solution for parallelizing recursive tasks, such as divide-and-conquer algorithms. It uses a work-stealing thread pool and automatically manages the partitioning and merging of subtasks.
5. Callback interface: A functional interface used to handle results or exceptions from asynchronous computations. The `FutureCallback` and `FutureTaskCallback` interfaces are examples of callbacks in Java.
6. Promise: A construct that represents a value that may not be available yet but will become available at some point in the future. It can be used to handle values produced by asynchronous functions, similar to Futures, but with additional features like attaching callbacks for handling errors or completing the promise manually.
7. Reactive programming: A programming paradigm that focuses on data streams and the propagation of changes through observable sequences of data. In Java, libraries such as RxJava provide support for reactive programming concepts, including Observables (a sequence of items) and operators for transforming and combining them. This can be used in combination with Futures to manage multiple asynchronous computations more efficiently.

---

## Q.12 Can you teach me about Java 8 features (Lambdas, Streams, Functional Interfaces)?

### Lambdas:

 Certainly! In Java 8 and later versions, lambda expressions were introduced as a way to write concise functional-style code. Lambda expressions allow you to create small anonymous functions that can be used in place of traditional methods and interfaces.

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
 Absolutely! In Java, Stream is a sequence of elements that can be processed in parallel or sequentially using functional-style operations. A Stream can come from various sources such as collections (e.g., arrays and lists), I/O channels, or even generating sequences on the fly.

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

## Q.13 


## Q.14 


## Q.15 


## Q.16 


