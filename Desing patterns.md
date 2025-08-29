
# Design Patterns
Alright, let’s go step by step and cover the **Singleton Pattern** in detail (interview-ready).

---
# Singleton Pattern

## **1. Definition**

The **Singleton Pattern** ensures that **only one instance of a class exists** in the JVM and provides a global access point to it.

---

## **2. When to Use**

* You want to control **object creation** to have only one instance.
* Useful for:

  * Database connection pools
  * Logging frameworks
  * Configuration managers
  * Thread pools
  * Caches

---

## **3. Real-world Analogy**

Think of a **printer spooler**:

* If multiple applications try to access the printer, a spooler ensures jobs are queued and handled one at a time.
* Only one spooler instance should exist.

---

## **4. Key Characteristics**

* **Private constructor** (prevents external instantiation).
* **Static instance variable** (holds the single instance).
* **Public method** (to provide access to the instance).

---

## **5. Implementations in Java**

### **(a) Eager Initialization**

```java
public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {}  // private constructor

    public static Singleton getInstance() {
        return instance;
    }
}
```

* **Pros:** Simple, thread-safe.
* **Cons:** Instance is created even if never used (wastes memory).

---

### **(b) Lazy Initialization (Not Thread-Safe)**

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

* **Pros:** Created only when needed.
* **Cons:** Not thread-safe in multi-threaded environments.

---

### **(c) Thread-Safe (Synchronized)**

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

* **Pros:** Thread-safe.
* **Cons:** Synchronized method is slow under heavy load.

---

### **(d) Double-Checked Locking (Recommended)**

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized(Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

* **Pros:** Thread-safe, avoids unnecessary synchronization.
* **Cons:** Slightly complex.

---

### **(e) Bill Pugh Singleton (Best Approach)**

```java
public class Singleton {
    private Singleton() {}

    private static class Helper {
        private static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return Helper.instance;
    }
}
```

* **Pros:** Thread-safe, lazy-loaded, efficient.
* **Cons:** Slightly less intuitive for beginners.

---

### **(f) Enum Singleton (Most Robust)**

```java
public enum Singleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("Doing work...");
    }
}
```

* **Pros:** Simplest, thread-safe, handles serialization.
* **Cons:** Can’t be lazily loaded, less flexible if you need inheritance.

---

## **6. Common Interview Questions**

1. **Why not just use a static class?**

   * Singleton allows lazy loading, can implement interfaces, and be passed around like an object.

2. **How do you make Singleton thread-safe?**

   * Use synchronized, double-checked locking, Bill Pugh method, or Enum.

3. **Is Singleton anti-pattern?**

   * It can be if misused (e.g., too many global states → tight coupling). Use only when truly needed.

---

## **7. Best Practices**

* Use **Bill Pugh** or **Enum** approach in Java.
* Keep constructor **private**.
* Declare instance as **volatile** when using double-checked locking.

---

# 2. Factory

## **1. Definition**

The **Factory Method Pattern** defines an **interface for creating objects**, but lets **subclasses decide which class to instantiate**.

* It **encapsulates object creation**.
* Client code only depends on an **abstract product**, not on concrete classes.

---

## **2. When to Use**

* When the **exact class** of the object to be created isn’t known until runtime.
* To achieve **loose coupling** between client code and object creation.
* When you want to follow **Open/Closed Principle**: add new types without modifying client code.

---

## **3. Real-world Analogy**

Think of a **Document Editor** (like MS Word or PDF Viewer):

* Each editor can open documents, but the exact type (WordDoc, PDFDoc, etc.) is decided by the factory (not the client).

Or a **Car Factory**:

* A factory method decides whether to produce a Sedan, SUV, or Hatchback.
* The customer (client) doesn’t worry about the internal creation process.

---

## **4. Key Characteristics**

* Defines a **common interface** for objects.
* Uses a **creator (factory)** class that defines the factory method.
* Subclasses of the factory decide which concrete product to return.

---

## **5. Structure**

* **Product** → Common interface or abstract class (e.g., `Shape`).
* **Concrete Product** → Actual implementations (e.g., `Circle`, `Square`).
* **Creator (Factory)** → Abstract class/interface declaring factory method.
* **Concrete Creator** → Subclass that decides which product to create.

---

## **6. Java Example**

### **Step 1: Product interface**

```java
interface Shape {
    void draw();
}
```

### **Step 2: Concrete Products**

```java
class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing Square");
    }
}
```

### **Step 3: Creator (Factory)**

```java
abstract class ShapeFactory {
    abstract Shape createShape();
}
```

### **Step 4: Concrete Factories**

```java
class CircleFactory extends ShapeFactory {
    public Shape createShape() {
        return new Circle();
    }
}

class SquareFactory extends ShapeFactory {
    public Shape createShape() {
        return new Square();
    }
}
```

### **Step 5: Client**

```java
public class FactoryMethodDemo {
    public static void main(String[] args) {
        ShapeFactory factory = new CircleFactory();
        Shape shape1 = factory.createShape();
        shape1.draw();

        factory = new SquareFactory();
        Shape shape2 = factory.createShape();
        shape2.draw();
    }
}
```

---

## **7. Output**

```
Drawing Circle
Drawing Square
```

---

## **8. Pros & Cons**

### ✅ Advantages

* Promotes **loose coupling**.
* Makes code more **flexible and extensible**.
* Easy to introduce **new products** without touching client code.

### ❌ Disadvantages

* Increases **number of classes** (each product has its own factory).
* More **complexity** compared to simple constructors.

---

## **9. Common Interview Questions**

1. **How is Factory Method different from Simple Factory?**

   * *Simple Factory* is just a static method returning objects.
   * *Factory Method* involves inheritance: subclasses override factory method to decide object creation.

2. **Difference between Factory Method and Abstract Factory?**

   * Factory Method → Creates **one product**.
   * Abstract Factory → Creates **families of related products**.

3. **Where have you seen Factory Method in Java?**

   * `java.util.Calendar.getInstance()`
   * `java.sql.DriverManager.getConnection()`
   * `java.text.NumberFormat.getInstance()`

---

## **10. Best Practices**

* Use when object creation logic is **complex** or may change.
* Use Abstract Factory when you need **multiple related objects**.
* For simple cases, a **static factory method** may be enough.

---

# 3. Abstract Factory Pattern

## **1. Definition**

The **Abstract Factory Pattern** provides an **interface for creating families of related objects**, without specifying their concrete classes.

* It’s like a **factory of factories**.
* Extends Factory Method to group related products together.

---

## **2. When to Use**

* When your system needs to be **independent of how products are created**.
* When you want to create **related objects (families)** that should work together.
* To ensure **consistency across products** (e.g., UI elements of the same theme).

---

## **3. Real-world Analogy**

Think of a **Furniture Store**:

* A **Modern Furniture Factory** produces **Modern Chair + Modern Table**.
* A **Victorian Furniture Factory** produces **Victorian Chair + Victorian Table**.
* The client can choose a factory but doesn’t need to know the details of object creation.

---

## **4. Key Characteristics**

* Groups related objects into a **family**.
* Each factory produces a **set of related products**.
* Promotes **loose coupling** by abstracting product creation.

---

## **5. Structure**

* **Abstract Factory** → Declares creation methods for product families.
* **Concrete Factories** → Implement product creation methods.
* **Abstract Product** → Interfaces for product types.
* **Concrete Product** → Actual implementations of products.
* **Client** → Uses factories but only depends on abstract interfaces.

---

## **6. Java Example**

### **Step 1: Abstract Products**

```java
interface Chair {
    void sitOn();
}

interface Table {
    void dineOn();
}
```

### **Step 2: Concrete Products**

```java
class ModernChair implements Chair {
    public void sitOn() {
        System.out.println("Sitting on a Modern Chair");
    }
}

class VictorianChair implements Chair {
    public void sitOn() {
        System.out.println("Sitting on a Victorian Chair");
    }
}

class ModernTable implements Table {
    public void dineOn() {
        System.out.println("Dining on a Modern Table");
    }
}

class VictorianTable implements Table {
    public void dineOn() {
        System.out.println("Dining on a Victorian Table");
    }
}
```

### **Step 3: Abstract Factory**

```java
interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}
```

### **Step 4: Concrete Factories**

```java
class ModernFurnitureFactory implements FurnitureFactory {
    public Chair createChair() {
        return new ModernChair();
    }
    public Table createTable() {
        return new ModernTable();
    }
}

class VictorianFurnitureFactory implements FurnitureFactory {
    public Chair createChair() {
        return new VictorianChair();
    }
    public Table createTable() {
        return new VictorianTable();
    }
}
```

### **Step 5: Client**

```java
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        FurnitureFactory factory = new ModernFurnitureFactory();
        Chair chair = factory.createChair();
        Table table = factory.createTable();
        chair.sitOn();
        table.dineOn();

        factory = new VictorianFurnitureFactory();
        chair = factory.createChair();
        table = factory.createTable();
        chair.sitOn();
        table.dineOn();
    }
}
```

---

## **7. Output**

```
Sitting on a Modern Chair
Dining on a Modern Table
Sitting on a Victorian Chair
Dining on a Victorian Table
```

---

## **8. Pros & Cons**

### ✅ Advantages

* Ensures **families of related objects** stay consistent.
* Promotes **loose coupling**.
* Makes it easy to **add new families** of products.

### ❌ Disadvantages

* Adding new **products** to existing families requires modifying all factories.
* More **complex** than Factory Method.

---

## **9. Common Interview Questions**

1. **Difference between Factory Method and Abstract Factory?**

   * Factory Method → Creates **one product**.
   * Abstract Factory → Creates **families of products**.

2. **Where do we see Abstract Factory in Java?**

   * `javax.xml.parsers.DocumentBuilderFactory`
   * `javax.xml.transform.TransformerFactory`
   * `javax.xml.xpath.XPathFactory`

3. **Why not just use if-else in client code?**

   * Abstract Factory avoids cluttering client with creation logic.

---

## **10. Best Practices**

* Use Abstract Factory when you need **families of related products**.
* If you need only **one product type**, stick to Factory Method.
* Often combined with **Singleton** (factories themselves are singletons).

---

# 4. Builder Pattern

## **1. Definition**

The **Builder Pattern** separates the **construction of a complex object** from its representation, so the same construction process can create different representations.

* Focuses on **step-by-step object creation**.
* Especially useful when constructors have **too many parameters** (some optional, some mandatory).

---

## **2. When to Use**

* When an object has **many attributes** (mandatory + optional).
* When you want to avoid **telescoping constructors** (constructors with many arguments).
* When you need a **flexible and readable way** to build objects.

---

## **3. Real-world Analogy**

Think of ordering a **meal at a restaurant**:

* Some items are **mandatory** (main dish).
* Some are **optional** (dessert, drink).
* The waiter (builder) helps you choose step by step, then serves a complete meal (final object).

---

## **4. Key Characteristics**

* **Immutable objects** are often built with this pattern.
* Builds object **incrementally** through chained method calls.
* Provides **better readability** compared to long constructors.

---

## **5. Structure**

* **Product** → The final complex object (e.g., `House`, `Car`).
* **Builder** → Defines steps to build product parts.
* **Concrete Builder** → Implements the building steps.
* **Director (optional)** → Controls the order of building steps.
* **Client** → Uses builder to get the final product.

---

## **6. Java Example**

### **Step 1: Product**

```java
class Computer {
    private String CPU;
    private String RAM;
    private String storage;
    private boolean hasGraphicsCard;

    // private constructor
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.hasGraphicsCard = builder.hasGraphicsCard;
    }

    @Override
    public String toString() {
        return "Computer [CPU=" + CPU + ", RAM=" + RAM + 
               ", storage=" + storage + ", graphicsCard=" + hasGraphicsCard + "]";
    }

    // Builder class inside Computer
    public static class Builder {
        private String CPU;
        private String RAM;
        private String storage;
        private boolean hasGraphicsCard;

        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }
        public Builder setRAM(String RAM) {
            this.RAM = RAM;
            return this;
        }
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        public Builder setGraphicsCard(boolean hasGraphicsCard) {
            this.hasGraphicsCard = hasGraphicsCard;
            return this;
        }
        public Computer build() {
            return new Computer(this);
        }
    }
}
```

### **Step 2: Client**

```java
public class BuilderDemo {
    public static void main(String[] args) {
        Computer gamingPC = new Computer.Builder()
                                .setCPU("Intel i9")
                                .setRAM("32GB")
                                .setStorage("1TB SSD")
                                .setGraphicsCard(true)
                                .build();

        Computer officePC = new Computer.Builder()
                                .setCPU("Intel i5")
                                .setRAM("16GB")
                                .setStorage("512GB SSD")
                                .build();

        System.out.println(gamingPC);
        System.out.println(officePC);
    }
}
```

---

## **7. Output**

```
Computer [CPU=Intel i9, RAM=32GB, storage=1TB SSD, graphicsCard=true]
Computer [CPU=Intel i5, RAM=16GB, storage=512GB SSD, graphicsCard=false]
```

---

## **8. Pros & Cons**

### ✅ Advantages

* Makes object creation **readable and maintainable**.
* Avoids **constructor telescoping problem**.
* Objects can be made **immutable**.
* Flexible: client can skip optional fields.

### ❌ Disadvantages

* More **boilerplate code**.
* Slightly complex compared to using constructors directly.

---

## **9. Common Interview Questions**

1. **How is Builder different from Factory/Abstract Factory?**

   * Builder → Creates **complex objects step by step**.
   * Factory → Chooses which object to create (but object is usually created in one shot).

2. **Where have you seen Builder in Java?**

   * `StringBuilder` / `StringBuffer`
   * `java.lang.StringBuilder.append()` (method chaining style)
   * `java.nio.ByteBuffer`
   * Many libraries (Lombok `@Builder`, Jackson ObjectMapper).

3. **Is Builder always better than constructors?**

   * Not always — use it when object has **too many optional parameters**.

---

## **10. Best Practices**

* Keep product’s constructor **private** to enforce builder usage.
* Use **method chaining** for readability.
* Good for **immutable classes**.
* Can be combined with **Director** if you want to enforce a sequence of steps.

---

# 5. Prototype

## **1. Definition**

The **Prototype Pattern** creates new objects by **cloning (copying)** an existing object, instead of creating new instances from scratch.

* Focus is on **object duplication**.
* Especially useful when **object creation is costly** (time or memory intensive).

---

## **2. When to Use**

* When the cost of creating a new object is **high** (e.g., involves database calls, network requests, or heavy computation).
* When you want to **avoid creating objects via `new` repeatedly**.
* When objects have **similar structure but differ slightly**.

---

## **3. Real-world Analogy**

Think of a **photocopy machine**:

* Instead of typing a document again (expensive), you just **clone** an existing one.
* You get an identical (or slightly modified) copy.

---

## **4. Key Characteristics**

* Requires a **clone() method**.
* Can support:

  * **Shallow Copy** → Copies only field values; nested objects are shared.
  * **Deep Copy** → Copies field values + clones nested objects too.

---

## **5. Structure**

* **Prototype (Interface/Abstract class)** → Declares `clone()` method.
* **Concrete Prototype** → Implements `clone()` method.
* **Client** → Calls `clone()` instead of using `new`.

---

## **6. Java Example**

### **Step 1: Prototype Interface**

```java
interface Prototype extends Cloneable {
    Prototype clone();
}
```

### **Step 2: Concrete Prototype**

```java
class Document implements Prototype {
    private String title;
    private String content;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public Prototype clone() {
        return new Document(this.title, this.content); // shallow copy
    }

    @Override
    public String toString() {
        return "Document [title=" + title + ", content=" + content + "]";
    }
}
```

### **Step 3: Client**

```java
public class PrototypeDemo {
    public static void main(String[] args) {
        Document original = new Document("Resume", "Aditya Raj - Software Engineer");
        Document copy = (Document) original.clone();

        System.out.println("Original: " + original);
        System.out.println("Copy: " + copy);
    }
}
```

---

## **7. Output**

```
Original: Document [title=Resume, content=Aditya Raj - Software Engineer]
Copy: Document [title=Resume, content=Aditya Raj - Software Engineer]
```

---

## **8. Shallow vs Deep Copy**

### Shallow Copy

* Clones **object fields only**, not referenced objects.
* Example: If a `List` is cloned, both copies share the same list reference.

### Deep Copy

* Clones fields + **clones referenced objects recursively**.
* Ensures complete independence.

```java
class DeepDocument implements Cloneable {
    private String title;
    private List<String> pages;

    public DeepDocument(String title, List<String> pages) {
        this.title = title;
        this.pages = new ArrayList<>(pages); // copy constructor
    }

    @Override
    public DeepDocument clone() {
        return new DeepDocument(this.title, new ArrayList<>(this.pages));
    }
}
```

---

## **9. Pros & Cons**

### ✅ Advantages

* Avoids expensive object creation.
* Simplifies creating complex objects.
* Reduces subclassing needs (no need for multiple constructors).

### ❌ Disadvantages

* Implementing **deep copy** can be tricky.
* Requires all classes to implement **Cloneable** or `clone()` method.
* Risk of issues if mutable objects are shallow-copied.

---

## **10. Common Interview Questions**

1. **What’s the difference between shallow and deep copy?**

   * Shallow → Copies references, shared objects.
   * Deep → Clones everything, fully independent.

2. **Where is Prototype used in Java?**

   * `Object.clone()` (native support in Java).
   * `java.lang.Cloneable` interface.
   * Serialization (another way to achieve deep copy).

3. **Prototype vs Factory Method?**

   * Prototype → Creates objects by **cloning existing ones**.
   * Factory Method → Creates objects by **defining a method for instantiation**.

---

## **11. Best Practices**

* Prefer **deep copy** when dealing with mutable objects.
* If immutability is possible, Prototype becomes less useful (immutable objects can be reused safely).
* Override `clone()` carefully (handle exceptions, avoid sharing mutable references).

---

Alright, let’s break down the **Adapter Pattern** in the same structured way as before.

---

# 6. Adapter Pattern

## **1. Definition**

The **Adapter Pattern** is a **structural design pattern** that allows objects with **incompatible interfaces** to work together by providing a **wrapper (adapter)** around one of them.

* It acts like a **translator** between two incompatible classes.

---

## **2. When to Use**

* When you want to **reuse existing classes** but their interfaces don’t match.
* When you need to integrate **legacy code** with new systems.
* When you work with **third-party libraries/APIs** that cannot be modified.

---

## **3. Real-world Analogy**

Think of a **power adapter**:

* Your laptop charger has a US plug, but the wall socket is European.
* An adapter lets them connect without changing either device.

---

## **4. Key Characteristics**

* Converts **one interface into another**.
* Doesn’t change existing classes; just wraps them.
* Two types:

  * **Class Adapter** → Uses inheritance.
  * **Object Adapter** → Uses composition (preferred in Java).

---

## **5. Structure**

* **Target (Interface)** → The interface expected by the client.
* **Adaptee** → The existing class with incompatible interface.
* **Adapter** → Wraps Adaptee and implements Target.
* **Client** → Uses only the Target interface.

---

## **6. Java Example**

### **Step 1: Target Interface**

```java
interface MediaPlayer {
    void play(String audioType, String fileName);
}
```

### **Step 2: Adaptee (incompatible interface)**

```java
class AdvancedMediaPlayer {
    void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}
```

### **Step 3: Adapter**

```java
class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedPlayer;

    public MediaAdapter() {
        advancedPlayer = new AdvancedMediaPlayer();
    }

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer.playMp4(fileName);
        } else {
            System.out.println("Format not supported: " + audioType);
        }
    }
}
```

### **Step 4: Client**

```java
class AudioPlayer implements MediaPlayer {
    private MediaAdapter adapter;

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else {
            adapter = new MediaAdapter();
            adapter.play(audioType, fileName);
        }
    }
}
```

### **Step 5: Demo**

```java
public class AdapterDemo {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.play("mp3", "song.mp3");
        player.play("mp4", "video.mp4");
        player.play("wav", "audio.wav");
    }
}
```

---

## **7. Output**

```
Playing mp3 file: song.mp3
Playing mp4 file: video.mp4
Format not supported: wav
```

---

## **8. Pros & Cons**

### ✅ Advantages

* Promotes **code reusability** (use old classes in new ways).
* Improves **flexibility** by separating interface from implementation.
* Client code doesn’t change even if adaptee changes.

### ❌ Disadvantages

* Adds extra **layers of abstraction** → Slight performance overhead.
* Too many adapters can make system design **complex and harder to understand**.

---

## **9. Common Interview Questions**

1. **Difference between Adapter and Decorator?**

   * Adapter → Converts interface to something else.
   * Decorator → Adds functionality to the same interface.

2. **Where have you seen Adapter in Java?**

   * `java.util.Arrays.asList()` → Adapts array to `List`.
   * `java.io.InputStreamReader` → Adapts `InputStream` to `Reader`.
   * `java.util.Collections.list(Enumeration e)` → Adapts `Enumeration` to `List`.

3. **When would you prefer Object Adapter over Class Adapter?**

   * Object Adapter (composition) is preferred in Java because Java doesn’t support multiple inheritance.

---

## **10. Best Practices**

* Use when you can’t change the **adaptee’s code** (legacy or third-party).
* Prefer **Object Adapter** (composition) → more flexible than Class Adapter.
* Keep adapters small and focused (avoid “god adapters”).

---

👉 Next, would you like me to cover **Decorator Pattern** (adds behavior dynamically) or move to **Strategy Pattern** (switching algorithms at runtime)?



Alright, let’s go step by step with the **Decorator Pattern** in the same structured way.

---

# 7. Decorator Pattern
## 1. Definition

The **Decorator Pattern** allows adding new behaviors or responsibilities to an object dynamically, without modifying its existing code.
It’s a **structural pattern** that works by wrapping objects with other objects (decorators).

---

## 2. **When to Use**

* When you want to add responsibilities to objects **at runtime** rather than at compile time (flexibility over inheritance).
* When subclassing would create a **large number of classes** to represent all possible combinations of behaviors.
* When you want to follow the **Open/Closed Principle** (open for extension, closed for modification).

---

## 3. **Real-World Analogy**

Think of a **coffee shop**:

* You order a **plain coffee**.
* You can decorate it with **milk, sugar, whipped cream, chocolate, etc.**.
  Each decorator wraps the base coffee and adds new behavior (cost and description).
  Instead of having a subclass for *CoffeeWithMilkAndSugarAndWhippedCream*, we just decorate objects dynamically.

---

## 4. **Structure**

* **Component (Interface/Abstract Class)** → Defines the base behavior.
* **ConcreteComponent** → The core object to be decorated.
* **Decorator (Abstract Class)** → Wraps a Component, delegates work, and may add extra behavior.
* **ConcreteDecorator(s)** → Add new functionality.

---

## 5. **UML Diagram (text-based)**

```
Component
   ↑
ConcreteComponent         Decorator (wraps Component)
                               ↑
                        ConcreteDecorator(s)
```

---

## 6. **Code Example (Java)**

```java
// Component
interface Coffee {
    String getDescription();
    double getCost();
}

// ConcreteComponent
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double getCost() { return 5.0; }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    public CoffeeDecorator(Coffee coffee) { this.decoratedCoffee = coffee; }
    public String getDescription() { return decoratedCoffee.getDescription(); }
    public double getCost() { return decoratedCoffee.getCost(); }
}

// ConcreteDecorator - Milk
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    public String getDescription() { return super.getDescription() + ", Milk"; }
    public double getCost() { return super.getCost() + 1.5; }
}

// ConcreteDecorator - Sugar
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }
    public String getDescription() { return super.getDescription() + ", Sugar"; }
    public double getCost() { return super.getCost() + 0.5; }
}

// Client
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());
    }
}
```

---

### **Output**

```
Simple Coffee $5.0
Simple Coffee, Milk, Sugar $7.0
```

---

## 7. **Key Points**

* Uses **composition** (wrapping objects) instead of **inheritance**.
* Supports adding behavior **dynamically at runtime**.
* Often used in **I/O streams in Java** (`BufferedInputStream`, `DataInputStream`, etc.).
* Can lead to **many small objects**, which may be harder to debug.

---

✅ That’s the **Decorator Pattern** explained in detail.

Do you want me to continue with the **Observer Pattern** next in the same structured style?


Alright, let’s go step by step with the **Observer Pattern** in the same detailed and structured manner:

---

# 8. Observer Pattern**

### **Definition**

The **Observer Pattern** defines a **one-to-many dependency** between objects.
When one object (called the **Subject**) changes its state, all its dependent objects (called **Observers**) are automatically notified and updated.

---

### **When & Why to Use**

* **When** multiple objects need to stay in sync with another object’s state.
* **Why** it promotes loose coupling:

  * Subject doesn’t know the exact details of Observers.
  * Observers can be added/removed dynamically.

---

### **Real-world Analogy**

Think of a **YouTube channel subscription system**:

* The **channel (Subject)** uploads a new video.
* All **subscribers (Observers)** get notified immediately.
* The channel doesn’t care who subscribed, it just notifies all.

---

### **UML Diagram**

```
+----------------+         +-------------------+
|    Subject     |<>------>|     Observer      |
+----------------+         +-------------------+
| + attach()     |         | + update()        |
| + detach()     |         +-------------------+
| + notify()     |
+----------------+
       ^
       |
+----------------+
| ConcreteSubject|
+----------------+
| state          |
+----------------+

+----------------+
| ConcreteObserver|
+----------------+
| update()        |
+----------------+
```

---

### **Code Example (Java)**

```java
// Observer Interface
interface Observer {
    void update(String message);
}

// Subject Interface
interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(String message);
}

// Concrete Subject
class YouTubeChannel implements Subject {
    private List<Observer> subscribers = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        subscribers.add(o);
    }

    @Override
    public void detach(Observer o) {
        subscribers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for(Observer o:subscribers){
            o.update(message);
        }
    }

    // Some business logic
    public void uploadVideo(String title) {
        System.out.println("Channel uploaded: " + title);
        notifyObservers("New video uploaded: " + title);
    }
}

// Concrete Observer
class Subscriber implements Observer {
    private String name;

    public Subscriber(String name) {
        this.name=name;
    }

    @Override
    public void update(String message) {
        System.out.println(name+" received notification: "+message);
    }
}

// Client
public class ObserverPatternDemo {
    public static void main(String[] args) {
        YouTubeChannel channel=new YouTubeChannel();

        Observer user1=new Subscriber("Alice");
        Observer user2=new Subscriber("Bob");

        channel.attach(user1);
        channel.attach(user2);

        channel.uploadVideo("Design Patterns Tutorial"); 
        // Alice and Bob both notified
    }
}
```

---

### **Output**

```
Channel uploaded: Design Patterns Tutorial
Alice received notification: New video uploaded: Design Patterns Tutorial
Bob received notification: New video uploaded: Design Patterns Tutorial
```

---

### **Key Points**

* **Subject** maintains a list of observers.
* **Observers** register/unregister themselves.
* **Loose coupling**: Subject doesn’t depend on concrete observers.
* Widely used in **event-driven systems**, **GUIs**, **pub-sub systems**.

---

### **Use Cases**

* GUI frameworks (e.g., button click listeners in Swing/JavaFX).
* Event notification systems (e.g., logging frameworks).
* Stock price update systems.
* Chat/messaging applications.
* Social media notifications.

---


# 9. Strategy Pattern
## 1. Definition**

The **Strategy Pattern** is a **behavioral design pattern** that allows you to define a family of algorithms, encapsulate each one, and make them interchangeable at runtime without changing the client code.
It promotes **composition over inheritance** by delegating behavior to different strategy classes.

---

## 2. **When to Use It**

* When you have multiple algorithms/behaviors for a specific task.
* When you want to **switch algorithms dynamically** at runtime.
* When using **`if-else` or `switch` statements** for selecting an algorithm starts making code messy.
* Example: Sorting (different algorithms like QuickSort, MergeSort, BubbleSort).

---

## 3. **Real-world Analogy**

Think of **navigation apps (like Google Maps)**.

* You can choose between different strategies: **Driving, Walking, Cycling, Public Transport**.
* The destination is the same, but the algorithm (strategy) to get there changes.
* Instead of hardcoding all possible paths, you just select the strategy you want.

---

## 4. **Structure**

Key participants:

* **Strategy (Interface):** Defines the common behavior/algorithm.
* **Concrete Strategies:** Implement the algorithm in different ways.
* **Context:** Maintains a reference to a strategy object and delegates the behavior to it.

---

## 5. **Code Example (Java)**

```java
// Step 1: Strategy Interface
interface PaymentStrategy {
    void pay(int amount);
}

// Step 2: Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void pay(int amount) {
        System.out.println(amount + " paid using Credit Card: " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    public PayPalPayment(String email) {
        this.email = email;
    }
    public void pay(int amount) {
        System.out.println(amount + " paid using PayPal account: " + email);
    }
}

// Step 3: Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}

// Step 4: Client Code
public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9876-5432"));
        cart.checkout(500);
        
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(300);
    }
}
```

### **Output**

```
500 paid using Credit Card: 1234-5678-9876-5432
300 paid using PayPal account: user@example.com
```

---

## 6. **Advantages**

✅ Promotes **open/closed principle** (easily add new strategies without modifying existing code).
✅ Removes complex **`if-else` chains**.
✅ Strategies can be changed **at runtime**.
✅ Improves **testability** (each strategy can be tested independently).

---

## 7. **Disadvantages**

❌ Increases the **number of classes** (one per strategy).
❌ Client must be **aware of different strategies** to choose the right one.

---

## 8. **Where It’s Used**

* Sorting algorithms (`Collections.sort()` in Java allows comparator strategies).
* Payment processing systems.
* Navigation/routing apps.
* Compression (ZIP, RAR, etc. strategies).
* Authentication strategies (OAuth, JWT, SAML, etc.).

---

👉 Next up, do you want me to explain the **Command Pattern** in the same way? It’s another very popular behavioral pattern.
