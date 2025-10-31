## What is a Java POJO?
POJO stands for Plain Old Java Object. It is an ordinary Java object, not bound by any special restriction other than those forced by the Java Language Specification and not requiring any classpath. POJOs are used for increasing the readability and re-usability of a program. POJOs have gained the most acceptance because they are easy to write and understand. They were introduced in EJB 3.0 by Sun Microsystems.

They typically use private fields to encapsulate data, with public getter and setter methods to access and modify these fields.

## JavaBean
Beans are special type of Pojos. There are some restrictions on POJO to be a bean.

1. All JavaBeans are POJOs but not all POJOs are JavaBeans.
2. Serializable i.e. they should implement Serializable interface. Still, some POJOs who don't implement a Serializable interface are called POJOs because Serializable is a marker interface and therefore not of many burdens.
3. Fields should be private. This is to provide complete control on fields.
4. Fields should have getters or setters or both.
5. A no-arg constructor should be there in a bean.
6. Fields are accessed only by constructor or getter setters.


## Explain Java Memory model.

### Java Memory Model (JMM)

The **Java Memory Model (JMM)** defines how threads in Java interact through memory and how changes made by one thread become visible to others. It ensures **visibility, ordering, and atomicity** of shared variables in a multithreaded environment.

---

### Key Components

* **Heap:** Stores objects (shared among all threads).
* **Stack:** Each thread has its own stack storing method calls and local variables.
* **Working Memory (Thread-local):** Each thread caches copies of variables from main memory.
* **Main Memory:** The actual shared memory where all variables reside.

---

### Problems JMM Solves

1. **Visibility issue** – One thread updates a variable, but another may not see it immediately due to caching.

   * Fixed using `volatile` or synchronization.
2. **Reordering issue** – Compiler/CPU may reorder instructions for optimization, breaking multi-thread assumptions.

   * Fixed using happens-before rules.
3. **Atomicity issue** – Some operations (like `count++`) are not atomic.

   * Fixed using synchronization, locks, or atomic variables.

---

### Happens-Before Rules (Ensuring Ordering & Visibility)

* A write to a `volatile` variable **happens-before** a subsequent read of that variable.
* Unlocking a monitor (synchronized block) **happens-before** another thread locks the same monitor.
* Thread start/join establishes happens-before relationships.

---

### Example

```java
class Example {
    private volatile boolean flag=false;

    public void writer() {
        flag=true; // write is visible to other threads immediately
    }

    public void reader() {
        if(flag) { // guaranteed to see latest value
            System.out.println("Flag is true");
        }
    }
}
```

Here, without `volatile`, thread B may never see the updated `flag` due to caching.

---
