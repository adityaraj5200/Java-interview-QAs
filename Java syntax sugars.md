## How to get the total sum of an array arr[] in java through lambda?

In Java, you can compute the **total sum of an array using lambda expressions** via the **Streams API**.

### 1. Using `Arrays.stream()` (Recommended)

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        int sum = Arrays.stream(arr)
                        .sum();

        System.out.println(sum); // 15
    }
}
```

✅ Uses a lambda internally and is the **cleanest and fastest** approach.

---

### 2. Explicit Lambda with `reduce()`

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        int sum = Arrays.stream(arr)
                        .reduce(0, (a, b) -> a + b);

        System.out.println(sum); // 15
    }
}
```

This shows the lambda explicitly:
`(a, b) -> a + b`

---

### 3. Using `forEach()` with Lambda (Not Recommended)

```java
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        AtomicInteger sum = new AtomicInteger(0);
        Arrays.stream(arr).forEach(sum::addAndGet);

        System.out.println(sum.get()); // 15
    }
}
```

⚠️ Works, but less clean and not idiomatic for summation.

---

### 4. For `Integer[]` (Object array)

```java
Integer[] arr = {1, 2, 3, 4, 5};

int sum = Arrays.stream(arr)
                .mapToInt(Integer::intValue)
                .sum();
```

---

### Best Practice Summary

| Scenario             | Recommended                |
| -------------------- | -------------------------- |
| `int[]`              | `Arrays.stream(arr).sum()` |
| Want explicit lambda | `reduce((a, b) -> a + b)`  |
| Avoid                | `forEach()` for summation  |


## How to sort in reverse order? ```int[] capacity```

