=====================================
ARRAYS (1D)
===========

1. Declaration & Initialization

```java
int n = size;
int[] arr = new int[n];
```

// declares and initializes a 1D array of dynamic size n

2. Insert / Add

```java
arr[idx] = value;
```

// inserts value at index idx

3. Remove / Delete

```java
arr[idx] = 0;
```

// logical deletion by resetting value at index

4. Search / Contains

```java
boolean found = false;
for (int x : arr)
{
    if (x == target)
    {
        found = true;
        break;
    }
}
```

// linear search for target

5. Update / Modify

```java
arr[idx] = newValue;
```

// updates element at index

6. Traversal / Iteration

```java
for (int i = 0; i < arr.length; i++)
{
    use(arr[i]);
}

for (int x : arr)
{
    use(x);
}
```

// index-based and for-each traversal

7. Common Built-in Methods

* arr.length → returns array size
* Arrays.sort(arr) → sorts array
* Arrays.fill(arr, val) → fills array with val
* Arrays.copyOf(arr, n) → copies array

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n)
* Access: O(1)

9. Common C++ → Java Pitfalls

* No dynamic resizing like vector
* No push_back equivalent
* Must use arr.length, not arr.size()
* Arrays passed by reference behavior

=====================================

=====================================
ARRAYS (2D)
===========

1. Declaration & Initialization

```java
int n = size;
int[][] mat = new int[n][n];
```

// declares and initializes a 2D array of size n × n

2. Insert / Add

```java
mat[i][j] = value;
```

// inserts value at row i, column j

3. Remove / Delete

```java
mat[i][j] = 0;
```

// logical deletion by resetting cell value

4. Search / Contains

```java
boolean found = false;
for (int i = 0; i < mat.length; i++)
{
    for (int j = 0; j < mat[0].length; j++)
    {
        if (mat[i][j] == target)
        {
            found = true;
            break;
        }
    }
}
```

// linear search in 2D matrix

5. Update / Modify

```java
mat[i][j] = newValue;
```

// updates element at (i, j)

6. Traversal / Iteration

```java
for (int i = 0; i < mat.length; i++)
{
    for (int j = 0; j < mat[0].length; j++)
    {
        use(mat[i][j]);
    }
}

for (int[] row : mat)
{
    for (int x : row)
    {
        use(x);
    }
}
```

// row-wise traversal using indexed and for-each loops

7. Common Built-in Methods

* mat.length → number of rows
* mat[0].length → number of columns
* Arrays.fill(mat[i], val) → fill a row
* Arrays.deepToString(mat) → debug print

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n²)
* Access: O(1)

9. Common C++ → Java Pitfalls

* 2D arrays are arrays of arrays
* mat.length gives rows only
* mat[i].length can vary (jagged arrays)
* No pointer arithmetic

=====================================
ARRAYS (3D)
===========

1. Declaration & Initialization

```java
int n = size;
int[][][] cube = new int[n][n][n];
```

// declares and initializes a 3D array of size n × n × n

2. Insert / Add

```java
cube[i][j][k] = value;
```

// inserts value at position (i, j, k)

3. Remove / Delete

```java
cube[i][j][k] = 0;
```

// logical deletion by resetting cell value

4. Search / Contains

```java
boolean found = false;
for (int i = 0; i < cube.length; i++)
{
    for (int j = 0; j < cube[i].length; j++)
    {
        for (int k = 0; k < cube[i][j].length; k++)
        {
            if (cube[i][j][k] == target)
            {
                found = true;
                break;
            }
        }
    }
}
```

// linear search in 3D array

5. Update / Modify

```java
cube[i][j][k] = newValue;
```

// updates element at (i, j, k)

6. Traversal / Iteration

```java
for (int i = 0; i < cube.length; i++)
{
    for (int j = 0; j < cube[i].length; j++)
    {
        for (int k = 0; k < cube[i][j].length; k++)
        {
            use(cube[i][j][k]);
        }
    }
}

for (int[][] layer : cube)
{
    for (int[] row : layer)
    {
        for (int x : row)
        {
            use(x);
        }
    }
}
```

// index-based and for-each traversal of 3D array

7. Common Built-in Methods

* cube.length → size of first dimension
* cube[i].length → size of second dimension
* cube[i][j].length → size of third dimension
* Arrays.deepToString(cube) → debug print

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n³)
* Access: O(1)

9. Common C++ → Java Pitfalls

* 3D arrays are nested arrays, not contiguous memory
* Each level length must be accessed separately
* Jagged 3D arrays are allowed
* No sizeof operator

=====================================

=====================================
STRINGS
=======

1. Declaration & Initialization

```java
int n = size;
char[] buf = new char[n];
String s = new String(buf);
```

// initializes a String of dynamic length n using a char buffer

2. Insert / Add

```java
s = s + ch;
```

// creates a new String with character appended

3. Remove / Delete

```java
s = s.substring(0, idx) + s.substring(idx + 1);
```

// removes character at index idx by reconstruction

4. Search / Contains

```java
boolean found = s.indexOf(target) != -1;
```

// checks if target exists in the string

5. Update / Modify

```java
char[] arr = s.toCharArray();
arr[idx] = newChar;
s = new String(arr);
```

// modifies character at index via char array conversion

6. Traversal / Iteration

```java
for (int i = 0; i < s.length(); i++)
{
    use(s.charAt(i));
}

for (char c : s.toCharArray())
{
    use(c);
}
```

// index-based and for-each traversal

7. Common Built-in Methods

* s.length() → returns string length
* s.charAt(i) → character at index
* s.substring(l, r) → substring [l, r)
* s.indexOf(x) → first occurrence index
* s.equals(t) → content comparison
* s.compareTo(t) → lexicographic comparison

8. Time Complexity

* Insert: O(n)
* Delete: O(n)
* Search: O(n)
* Access: O(1)

9. Common C++ → Java Pitfalls

* Strings are immutable
* Use equals(), not == for comparison
* No direct indexing assignment
* * creates new String each time

=====================================


=====================================
ARRAYLIST (1D)
==============

1. Declaration & Initialization

```java
int n = size;
List<Integer> arr = new ArrayList<>(n);
```

// declares ArrayList with initial capacity n

2. Insert / Add

```java
arr.add(value);
arr.add(idx, value);
```

// appends at end or inserts at index

3. Remove / Delete

```java
arr.remove(idx);
arr.remove(Integer.valueOf(value));
```

// removes by index or by value

4. Search / Contains

```java
boolean found = arr.contains(target);
```

// checks if element exists

5. Update / Modify

```java
arr.set(idx, newValue);
```

// updates element at index

6. Traversal / Iteration

```java
for (int i = 0; i < arr.size(); i++)
{
    use(arr.get(i));
}

for (int x : arr)
{
    use(x);
}
```

// index-based and for-each traversal

7. Common Built-in Methods

* arr.size() → number of elements
* arr.get(i) → access element
* arr.add(x) → append element
* arr.clear() → remove all elements
* arr.isEmpty() → checks emptiness

8. Time Complexity

* Insert: O(1) amortized / O(n) at index
* Delete: O(n)
* Search: O(n)
* Access: O(1)

9. Common C++ → Java Pitfalls

* No operator[] access
* Must use get() and set()
* remove(int) vs remove(Integer) ambiguity
* Capacity ≠ size

=====================================

=====================================
ARRAYLIST (2D)
==============

1. Declaration & Initialization

```java
int n = size;
List<List<Integer>> mat = new ArrayList<>(n);
for (int i = 0; i < n; i++)
{
    mat.add(new ArrayList<>(n));
}
```

// initializes a 2D ArrayList with n rows

2. Insert / Add

```java
mat.get(i).add(value);
mat.get(i).add(j, value);
```

// appends to row i or inserts at column j

3. Remove / Delete

```java
mat.get(i).remove(j);
mat.get(i).remove(Integer.valueOf(value));
```

// removes by index or by value from a row

4. Search / Contains

```java
boolean found = false;
for (List<Integer> row : mat)
{
    if (row.contains(target))
    {
        found = true;
        break;
    }
}
```

// searches target in entire 2D list

5. Update / Modify

```java
mat.get(i).set(j, newValue);
```

// updates element at (i, j)

6. Traversal / Iteration

```java
for (int i = 0; i < mat.size(); i++)
{
    for (int j = 0; j < mat.get(i).size(); j++)
    {
        use(mat.get(i).get(j));
    }
}

for (List<Integer> row : mat)
{
    for (int x : row)
    {
        use(x);
    }
}
```

// index-based and for-each traversal

7. Common Built-in Methods

* mat.size() → number of rows
* mat.get(i) → access row
* mat.get(i).size() → size of a row
* mat.clear() → clears all rows

8. Time Complexity

* Insert: O(1) amortized / O(n) at index
* Delete: O(n)
* Search: O(n²)
* Access: O(1)

9. Common C++ → Java Pitfalls

* Each row must be initialized separately
* mat.size() gives rows, not total elements
* Jagged rows are allowed
* No contiguous memory guarantee

=====================================

=====================================
ARRAYLIST (3D)
==============

1. Declaration & Initialization

```java
int n = size;
List<List<List<Integer>>> cube = new ArrayList<>(n);
for (int i = 0; i < n; i++)
{
    List<List<Integer>> layer = new ArrayList<>(n);
    for (int j = 0; j < n; j++)
    {
        layer.add(new ArrayList<>(n));
    }
    cube.add(layer);
}
```

// initializes a 3D ArrayList of size n × n × n

2. Insert / Add

```java
cube.get(i).get(j).add(value);
cube.get(i).get(j).add(k, value);
```

// appends to or inserts at depth k

3. Remove / Delete

```java
cube.get(i).get(j).remove(k);
cube.get(i).get(j).remove(Integer.valueOf(value));
```

// removes by index or by value at (i, j)

4. Search / Contains

```java
boolean found = false;
for (List<List<Integer>> layer : cube)
{
    for (List<Integer> row : layer)
    {
        if (row.contains(target))
        {
            found = true;
            break;
        }
    }
}
```

// searches target in entire 3D list

5. Update / Modify

```java
cube.get(i).get(j).set(k, newValue);
```

// updates element at (i, j, k)

6. Traversal / Iteration

```java
for (int i = 0; i < cube.size(); i++)
{
    for (int j = 0; j < cube.get(i).size(); j++)
    {
        for (int k = 0; k < cube.get(i).get(j).size(); k++)
        {
            use(cube.get(i).get(j).get(k));
        }
    }
}

for (List<List<Integer>> layer : cube)
{
    for (List<Integer> row : layer)
    {
        for (int x : row)
        {
            use(x);
        }
    }
}
```

// index-based and for-each traversal of 3D ArrayList

7. Common Built-in Methods

* cube.size() → number of layers
* cube.get(i).size() → rows in layer i
* cube.get(i).get(j).size() → depth at (i, j)
* cube.clear() → clears all layers

8. Time Complexity

* Insert: O(1) amortized / O(n) at index
* Delete: O(n)
* Search: O(n³)
* Access: O(1)

9. Common C++ → Java Pitfalls

* Every level must be manually initialized
* size() differs per dimension
* Heavy memory overhead vs arrays
* No contiguous memory guarantee

=====================================


=====================================
LINKEDLIST
==========

1. Declaration & Initialization

```java
int n = size;
List<Integer> list = new LinkedList<>();
for (int i = 0; i < n; i++)
{
    list.add(0);
}
```

// initializes LinkedList with dynamic size n using placeholder values

2. Insert / Add

```java
list.add(value);
list.add(idx, value);
```

// inserts at end or at index

3. Remove / Delete

```java
list.remove(idx);
list.remove(Integer.valueOf(value));
```

// removes by index or by value

4. Search / Contains

```java
boolean found = list.contains(target);
```

// checks if target exists

5. Update / Modify

```java
list.set(idx, newValue);
```

// updates value at index

6. Traversal / Iteration

```java
for (int i = 0; i < list.size(); i++)
{
    use(list.get(i));
}

for (int x : list)
{
    use(x);
}
```

// index-based and for-each traversal

7. Common Built-in Methods

* list.add(x) → insert element
* list.remove(i) → remove by index
* list.get(i) → access element
* list.set(i, x) → update element
* list.size() → number of elements
* list.clear() → remove all elements

8. Time Complexity

* Insert: O(1) at ends / O(n) at index
* Delete: O(1) at ends / O(n) at index
* Search: O(n)
* Access: O(n)

9. Common C++ → Java Pitfalls

* No direct node-level access
* get(i) is O(n), not O(1)
* Higher overhead than ArrayList
* Not cache-friendly

=====================================


=====================================
STACK
=====

1. Declaration & Initialization

```java
int n = size;
Stack<Integer> st = new Stack<>();
for (int i = 0; i < n; i++)
{
    st.push(0);
}
```

// initializes stack with dynamic size n using placeholder values

2. Insert / Add

```java
st.push(value);
```

// pushes value onto the top of stack

3. Remove / Delete

```java
int removed = st.pop();
```

// removes and returns the top element

4. Search / Contains

```java
boolean found = st.contains(target);
```

// checks if target exists in stack

5. Update / Modify

```java
int x = st.pop();
st.push(newValue);
```

// replaces top element with new value

6. Traversal / Iteration

```java
for (int x : st)
{
    use(x);
}

for (int i = st.size() - 1; i >= 0; i--)
{
    use(st.get(i));
}
```

// bottom-to-top and top-to-bottom traversal

7. Common Built-in Methods

* st.push(x) → push element
* st.pop() → remove and return top
* st.peek() → return top without removing
* st.isEmpty() → check if empty
* st.search(x) → 1-based position from top

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n)
* Access: O(1) for top

9. Common C++ → Java Pitfalls

* Stack is synchronized and slower
* Prefer Deque for modern stack use
* search() is 1-based index
* Inherits from Vector

=====================================


=====================================
QUEUE
=====

1. Declaration & Initialization

```java
int n = size;
Queue<Integer> q = new LinkedList<>();
for (int i = 0; i < n; i++)
{
    q.offer(0);
}
```

// initializes queue with dynamic size n using placeholder values

2. Insert / Add

```java
q.offer(value);
```

// inserts element at the rear of queue

3. Remove / Delete

```java
int removed = q.poll();
```

// removes and returns front element

4. Search / Contains

```java
boolean found = q.contains(target);
```

// checks if target exists in queue

5. Update / Modify

```java
int x = q.poll();
q.offer(newValue);
```

// replaces front element with new value

6. Traversal / Iteration

```java
for (int x : q)
{
    use(x);
}
```

// front-to-rear traversal

7. Common Built-in Methods

* q.offer(x) → insert at rear
* q.poll() → remove and return front
* q.peek() → return front without removing
* q.isEmpty() → check if empty
* q.size() → number of elements

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n)
* Access: O(1) for front

9. Common C++ → Java Pitfalls

* No push() or pop() methods
* poll() returns null if empty
* remove() throws exception if empty
* Front element accessed via peek()

=====================================

=====================================
DEQUE
=====

1. Declaration & Initialization

```java
int n = size;
Deque<Integer> dq = new ArrayDeque<>();
for (int i = 0; i < n; i++)
{
    dq.addLast(0);
}
```

// initializes deque with dynamic size n using placeholder values

2. Insert / Add

```java
dq.addFirst(value);
dq.addLast(value);
```

// inserts at front or rear

3. Remove / Delete

```java
int front = dq.pollFirst();
int rear = dq.pollLast();
```

// removes from front or rear

4. Search / Contains

```java
boolean found = dq.contains(target);
```

// checks if target exists in deque

5. Update / Modify

```java
int x = dq.pollFirst();
dq.addFirst(newValue);
```

// replaces front element with new value

6. Traversal / Iteration

```java
for (int x : dq)
{
    use(x);
}

Iterator<Integer> it = dq.descendingIterator();
while (it.hasNext())
{
    use(it.next());
}
```

// front-to-rear and rear-to-front traversal

7. Common Built-in Methods

* dq.addFirst(x) → insert at front
* dq.addLast(x) → insert at rear
* dq.pollFirst() → remove front
* dq.pollLast() → remove rear
* dq.peekFirst() → view front
* dq.peekLast() → view rear
* dq.isEmpty() → check if empty

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(n)
* Access: O(1) at both ends

9. Common C++ → Java Pitfalls

* Prefer ArrayDeque over LinkedList
* No direct indexing like vector/deque
* null elements not allowed in ArrayDeque
* Use poll/peek instead of remove/get

=====================================


=====================================
HASHMAP
=======

1. Declaration & Initialization

```java
int n = size;
Map<Integer, Integer> map = new HashMap<>(n);
```

// initializes HashMap with initial capacity n

2. Insert / Add

```java
map.put(key, value);
```

// inserts or updates key with value

3. Remove / Delete

```java
map.remove(key);
```

// removes entry with given key

4. Search / Contains

```java
boolean found = map.containsKey(key);
```

// checks if key exists

5. Update / Modify

```java
map.put(key, newValue);
```

// updates value for existing key

6. Traversal / Iteration

```java
for (Map.Entry<Integer, Integer> e : map.entrySet())
{
    use(e.getKey());
    use(e.getValue());
}

for (int k : map.keySet())
{
    use(k);
}

for (int v : map.values())
{
    use(v);
}
```

// iteration over entries, keys, and values

7. Common Built-in Methods

* map.put(k, v) → insert/update
* map.get(k) → get value by key
* map.remove(k) → remove key
* map.containsKey(k) → key existence
* map.size() → number of entries
* map.isEmpty() → check if empty
* map.clear() → remove all entries

8. Time Complexity

* Insert: O(1) average
* Delete: O(1) average
* Search: O(1) average
* Access: O(1) average

9. Common C++ → Java Pitfalls

* No operator[] for access
* get() returns null if key absent
* HashMap is not ordered
* Needs wrapper types, not primitives

=====================================

=====================================
HASHSET
=======

1. Declaration & Initialization

```java
int n = size;
Set<Integer> set = new HashSet<>(n);
```

// initializes HashSet with initial capacity n

2. Insert / Add

```java
set.add(value);
```

// inserts value into the set

3. Remove / Delete

```java
set.remove(value);
```

// removes value from the set

4. Search / Contains

```java
boolean found = set.contains(target);
```

// checks if target exists in set

5. Update / Modify

```java
set.remove(oldValue);
set.add(newValue);
```

// replaces oldValue with newValue

6. Traversal / Iteration

```java
for (int x : set)
{
    use(x);
}
```

// iterates over all elements (unordered)

7. Common Built-in Methods

* set.add(x) → insert element
* set.remove(x) → remove element
* set.contains(x) → existence check
* set.size() → number of elements
* set.isEmpty() → check if empty
* set.clear() → remove all elements

8. Time Complexity

* Insert: O(1) average
* Delete: O(1) average
* Search: O(1) average
* Access: O(1) average (via contains)

9. Common C++ → Java Pitfalls

* No duplicate elements allowed
* No direct indexing
* Iteration order is not guaranteed
* Needs wrapper types, not primitives

=====================================

=====================================
TREEMAP
=======

1. Declaration & Initialization

```java
int n = size;
Map<Integer, Integer> map = new TreeMap<>();
```

// initializes an empty TreeMap with dynamic expected size n

2. Insert / Add

```java
map.put(key, value);
```

// inserts or updates key with value in sorted order

3. Remove / Delete

```java
map.remove(key);
```

// removes entry with given key

4. Search / Contains

```java
boolean found = map.containsKey(key);
```

// checks if key exists in TreeMap

5. Update / Modify

```java
map.put(key, newValue);
```

// updates value for existing key

6. Traversal / Iteration

```java
for (Map.Entry<Integer, Integer> e : map.entrySet())
{
    use(e.getKey());
    use(e.getValue());
}

for (int k : map.keySet())
{
    use(k);
}
```

// in-order traversal by key (sorted)

7. Common Built-in Methods

* map.put(k, v) → insert/update
* map.get(k) → get value by key
* map.remove(k) → remove key
* map.firstKey() → smallest key
* map.lastKey() → largest key
* map.lowerKey(k) → greatest key < k
* map.higherKey(k) → smallest key > k

8. Time Complexity

* Insert: O(log n)
* Delete: O(log n)
* Search: O(log n)
* Access: O(log n)

9. Common C++ → Java Pitfalls

* Implemented as Red-Black Tree
* Always sorted by keys
* Slower than HashMap
* No null keys allowed in TreeMap

=====================================

=====================================
TREESET
=======

1. Declaration & Initialization

```java
int n = size;
Set<Integer> set = new TreeSet<>();
```

// initializes an empty TreeSet with dynamic expected size n

2. Insert / Add

```java
set.add(value);
```

// inserts value into set in sorted order

3. Remove / Delete

```java
set.remove(value);
```

// removes value from the set

4. Search / Contains

```java
boolean found = set.contains(target);
```

// checks if target exists in TreeSet

5. Update / Modify

```java
set.remove(oldValue);
set.add(newValue);
```

// replaces oldValue with newValue

6. Traversal / Iteration

```java
for (int x : set)
{
    use(x);
}

Iterator<Integer> it = ((TreeSet<Integer>) set).descendingIterator();
while (it.hasNext())
{
    use(it.next());
}
```

// ascending and descending order traversal

7. Common Built-in Methods

* set.add(x) → insert element
* set.remove(x) → remove element
* set.contains(x) → existence check
* ((TreeSet)set).first() → smallest element
* ((TreeSet)set).last() → largest element
* ((TreeSet)set).lower(x) → greatest < x
* ((TreeSet)set).higher(x) → smallest > x

8. Time Complexity

* Insert: O(log n)
* Delete: O(log n)
* Search: O(log n)
* Access: O(log n)

9. Common C++ → Java Pitfalls

* Implemented as Red-Black Tree
* Always sorted
* Slower than HashSet
* No null elements allowed

=====================================

=====================================
PRIORITYQUEUE (HEAP)
====================

1. Declaration & Initialization

```java
int n = size;
PriorityQueue<Integer> pq = new PriorityQueue<>(n);
```

// initializes a min-heap with initial capacity n

2. Insert / Add

```java
pq.offer(value);
```

// inserts value into the heap

3. Remove / Delete

```java
int removed = pq.poll();
```

// removes and returns the minimum element

4. Search / Contains

```java
boolean found = pq.contains(target);
```

// checks if target exists in heap

5. Update / Modify

```java
pq.remove(oldValue);
pq.offer(newValue);
```

// updates a value by remove and reinsert

6. Traversal / Iteration

```java
for (int x : pq)
{
    use(x);
}
```

// iterates over heap (not in sorted order)

7. Common Built-in Methods

* pq.offer(x) → insert element
* pq.poll() → remove and return min
* pq.peek() → view min without removing
* pq.isEmpty() → check if empty
* pq.size() → number of elements
* pq.clear() → remove all elements

8. Time Complexity

* Insert: O(log n)
* Delete: O(log n)
* Search: O(n)
* Access: O(1) for min via peek

9. Common C++ → Java Pitfalls

* Default is min-heap, not max-heap
* No direct decrease-key operation
* Iteration does not give sorted order
* Null elements not allowed

=====================================

=====================================
BITSET
======

1. Declaration & Initialization

```java
int n = size;
BitSet bs = new BitSet(n);
```

// initializes a BitSet with dynamic size n bits

2. Insert / Add

```java
bs.set(idx);
```

// sets bit at index idx to true

3. Remove / Delete

```java
bs.clear(idx);
```

// clears bit at index idx

4. Search / Contains

```java
boolean found = bs.get(idx);
```

// checks if bit at idx is set

5. Update / Modify

```java
bs.flip(idx);
```

// toggles bit at index idx

6. Traversal / Iteration

```java
for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1))
{
    use(i);
}
```

// iterates over all set bits

7. Common Built-in Methods

* bs.set(i) → set bit i
* bs.clear(i) → clear bit i
* bs.flip(i) → toggle bit i
* bs.get(i) → read bit i
* bs.cardinality() → count of set bits
* bs.or(other) → bitwise OR
* bs.and(other) → bitwise AND

8. Time Complexity

* Insert: O(1)
* Delete: O(1)
* Search: O(1)
* Access: O(1)

9. Common C++ → Java Pitfalls

* Grows dynamically beyond initial size
* More memory efficient than boolean[]
* Indices beyond size auto-expand
* No direct iteration like array

=====================================
