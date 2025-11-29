# C++ to Java Learning Syntax Cheat Sheet

### 1. **Printing Output**

| Task               | C++                  | Java                     |
| ------------------ | -------------------- | ------------------------ |
| Print a value      | `cout << x;`         | `System.out.print(x);`   |
| Print with newline | `cout << x << endl;` | `System.out.println(x);` |

---

### 2. **Taking Input**

| Task                   | C++                | Java                                                         |
| ---------------------- | ------------------ | ------------------------------------------------------------ |
| Read integer           | `cin >> x;`        | `Scanner sc = new Scanner(System.in); int x = sc.nextInt();` |
| Read string (no space) | `cin >> s;`        | `String s = sc.next();`                                      |
| Read full line         | `getline(cin, s);` | `String s = sc.nextLine();`                                  |

---

### 3. **For Loop**

| Task           | C++                    | Java                   |
| -------------- | ---------------------- | ---------------------- |
| Basic for loop | `for(int i=0;i<n;i++)` | `for(int i=0;i<n;i++)` |

✅ Same syntax

---

### 4. **While Loop**

| Task       | C++            | Java           |
| ---------- | -------------- | -------------- |
| While loop | `while(x > 0)` | `while(x > 0)` |

✅ Same syntax

---

### 5. **If–Else**

| Task              | C++             | Java            |
| ----------------- | --------------- | --------------- |
| Conditional check | `if(x > 0) {}`  | `if(x > 0) {}`  |
| If–else block     | `if(a){} else{}`| `if(a){} else{}`|

✅ Same syntax

### 6. **Array Declaration & Initialization**

| Task             | C++                    | Java                      |
| ---------------- | ---------------------- | ------------------------- |
| Fixed size array | `int arr[5];`          | `int[] arr = new int[5];` |
| Initialization   | `int arr[] = {1,2,3};` | `int[] arr = {1,2,3};`    |
| Size of array    | `sizeof(arr)/4`        | `arr.length`              |

---

### 7. **Traversing an Array**

| Task                | C++                              | Java                             |
| ------------------- | -------------------------------- | -------------------------------- |
| Using for loop      | `for(int i=0;i<n;i++) cout<<arr[i];` | `for(int i=0;i<arr.length;i++) System.out.print(arr[i]);` |
| Range-based loop   | `for(int x : arr)`               | `for(int x : arr)`               |

✅ Same syntax for range-based loop

---

### 8. **String Declaration & Usage**

| Task            | C++                   | Java                 |
| --------------- | --------------------- | -------------------- |
| String variable | `string s = "abc";`   | `String s = "abc";`  |
| Length          | `s.length()`          | `s.length()`         |
| Access char     | `s[0]`                | `s.charAt(0)`        |

⚠️ Java strings are immutable.

---

### 9. **String Concatenation**

| Task             | C++           | Java            |
| ---------------- | ------------- | --------------- |
| Using `+`        | `s = s + t;`  | `s = s + t;`    |
| Using function  | `s.append(t)`| `s.concat(t)`  |

⚠️ `append` works on `StringBuilder` in Java.

---

### 10. **Function / Method Declaration**

| Task           | C++                     | Java                           |
| -------------- | ----------------------- | ------------------------------ |
| Basic function | `int add(int a,int b)`  | `static int add(int a,int b)`  |
| Function call  | `int c = add(2,3);`     | `int c = add(2,3);`            |

⚠️ All Java methods must be inside a class.



### 11. **Pass by Value**

| Task         | C++             | Java                   |
| ------------ | --------------- | ---------------------- |
| Pass integer | `void f(int x)` | `static void f(int x)` |

✅ Same behavior: value is copied.

---

### 12. **Pass by Reference**

| Task            | C++                 | Java                          |
| --------------- | ------------------- | ----------------------------- |
| Using reference | `void f(int &x)`    | ❌ Not supported               |
| Equivalent way | Reference variable | Use **array / object wrapper** |

Example:

```cpp
void f(int &x) { x++; }
````

```java
static void f(int[] x) { x[0]++; }
```

---

### 13. **Swapping Two Numbers**

| Task          | C++                     | Java                             |
| ------------- | ----------------------- | -------------------------------- |
| Swap values   | `swap(a, b);`           | Use temp variable                |
| Inside method | `void f(int &a,int &b)` | `static void f(int[] a,int[] b)` |

Java example:

```java
static void swap(int[] a, int[] b){
    int t = a[0]; a[0] = b[0]; b[0] = t;
}
```

---

### 14. **Dynamic Array (Vector vs ArrayList)**

| Task           | C++               | Java                                        |
| -------------- | ----------------- | ------------------------------------------- |
| Declare        | `vector<int> v;`  | `ArrayList<Integer> v = new ArrayList<>();` |
| Insert element | `v.push_back(x);` | `v.add(x);`                                 |
| Access element | `v[i]`            | `v.get(i)`                                  |
| Update element | `v[i] = x;`       | `v.set(i, x);`                              |
| Size           | `v.size()`        | `v.size()`                                  |

⚠️ Java uses **wrapper classes** (`Integer` instead of `int`).

---

### 15. **Stack**

| Task     | C++              | Java                                 |
| -------- | ---------------- | ------------------------------------ |
| Declare  | `stack<int> st;` | `Stack<Integer> st = new Stack<>();` |
| Push     | `st.push(x);`    | `st.push(x);`                        |
| Top      | `st.top()`       | `st.peek()`                          |
| Pop      | `st.pop();`      | `st.pop();`                          |
| Is empty | `st.empty()`     | `st.isEmpty()`                       |

⚠️ Java uses **wrapper classes** (`Integer` instead of `int`).

### 16. **Queue**

| Task             | C++                          | Java                                              |
| ---------------- | ---------------------------- | ------------------------------------------------- |
| Declare          | `queue<int> q;`              | `Queue<Integer> q = new LinkedList<>();`          |
| Push             | `q.push(x);`                 | `q.add(x);`                                       |
| Front            | `q.front()`                  | `q.peek()`                                        |
| Pop              | `q.pop();`                   | `q.poll();`                                       |
| Is empty         | `q.empty()`                  | `q.isEmpty()`                                     |

---

### 17. **Deque**

| Task             | C++                          | Java                                                   |
| ---------------- | ---------------------------- | ------------------------------------------------------ |
| Declare          | `deque<int> dq;`             | `Deque<Integer> dq = new ArrayDeque<>();`              |
| Push front       | `dq.push_front(x);`          | `dq.addFirst(x);`                                      |
| Push back        | `dq.push_back(x);`           | `dq.addLast(x);`                                       |
| Front            | `dq.front()`                 | `dq.peekFirst()`                                       |
| Back             | `dq.back()`                  | `dq.peekLast()`                                        |
| Pop front        | `dq.pop_front();`            | `dq.pollFirst();`                                      |
| Pop back         | `dq.pop_back();`             | `dq.pollLast();`                                       |

---

### 18. **Set (Ordered / TreeSet)**

| Task             | C++                          | Java                                              |
| ---------------- | ---------------------------- | ------------------------------------------------- |
| Declare          | `set<int> s;`                | `TreeSet<Integer> s = new TreeSet<>();`           |
| Insert           | `s.insert(x);`               | `s.add(x);`                                       |
| Search           | `s.find(x)!=s.end()`         | `s.contains(x)`                                   |
| Remove           | `s.erase(x);`                | `s.remove(x);`                                    |
| Size             | `s.size()`                   | `s.size()`                                        |

---

### 19. **Unordered Set (HashSet)**

| Task             | C++                                  | Java                                      |
| ---------------- | ------------------------------------ | ----------------------------------------- |
| Declare          | `unordered_set<int> s;`              | `HashSet<Integer> s = new HashSet<>();`   |
| Insert           | `s.insert(x);`                       | `s.add(x);`                               |
| Search           | `s.count(x)`                         | `s.contains(x);`                          |
| Erase            | `s.erase(x);`                        | `s.remove(x);`                            |

---

### 20. **Map (Ordered / TreeMap)**

| Task             | C++                                  | Java                                             |
| ---------------- | ------------------------------------ | ------------------------------------------------ |
| Declare          | `map<int,int> mp;`                   | `TreeMap<Integer,Integer> mp = new TreeMap<>();`|
| Insert/Update    | `mp[k]=v;`                           | `mp.put(k,v);`                                  |
| Get value        | `mp[k]`                              | `mp.get(k);`                                    |
| Exists           | `mp.find(k)!=mp.end()`               | `mp.containsKey(k);`                            |
| Remove           | `mp.erase(k);`                       | `mp.remove(k);`                                 |

---

### 21. **Unordered Map (HashMap)**

| Task             | C++                                      | Java                                         |
| ---------------- | ---------------------------------------- | -------------------------------------------- |
| Declare          | `unordered_map<int,int> mp;`             | `HashMap<Integer,Integer> mp = new HashMap<>();` |
| Insert/Update    | `mp[k]=v;`                               | `mp.put(k,v);`                               |
| Get value        | `mp[k]`                                  | `mp.get(k);`                                 |
| Exists           | `mp.find(k)!=mp.end()`                   | `mp.containsKey(k);`                         |
| Remove           | `mp.erase(k);`                           | `mp.remove(k);`                              |

---

### 22. **Priority Queue (Heap)**

| Task                | C++                                      | Java                                               |
| ------------------- | ---------------------------------------- | -------------------------------------------------- |
| Max heap declare    | `priority_queue<int> pq;`               | `PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());` |
| Min heap declare    | `priority_queue<int,vector<int>,greater<int>> pq;` | `PriorityQueue<Integer> pq = new PriorityQueue<>();` |
| Push                | `pq.push(x);`                           | `pq.add(x);`                                       |
| Top                 | `pq.top();`                             | `pq.peek();`                                       |
| Pop                 | `pq.pop();`                             | `pq.poll();`                                       |

---

### 23. **Sorting an Array / List**

| Task                 | C++                               | Java                                       |
| -------------------- | --------------------------------- | ------------------------------------------ |
| Sort array ascending | `sort(arr, arr+n);`               | `Arrays.sort(arr);`                        |
| Sort vector/list     | `sort(v.begin(), v.end());`       | `Collections.sort(v);`                     |
| Sort descending      | `sort(v.rbegin(), v.rend());`     | `Collections.sort(v, Collections.reverseOrder());` |

---

### 24. **Binary Search**

| Task                    | C++                                      | Java                                  |
| ----------------------- | ---------------------------------------- | ------------------------------------- |
| Built-in binary search  | `binary_search(arr,arr+n,x)`             | `Arrays.binarySearch(arr,x) >= 0`     |
| On vector/list          | `binary_search(v.begin(),v.end(),x)`     | `Collections.binarySearch(v,x) >= 0`  |

---

### 25. **Lower Bound / Upper Bound**

| Task          | C++                                   | Java                                                         |
| ------------- | ------------------------------------- | ------------------------------------------------------------ |
| Lower bound   | `lower_bound(v.begin(),v.end(),x)`    | `Collections.binarySearch(v,x)` (manual adjustment needed) |
| Upper bound   | `upper_bound(v.begin(),v.end(),x)`    | ❌ Not directly available                                   |

⚠️ Java requires manual implementation for exact lower/upper bound.

### 26. **Pair / Key–Value Structure**

| Task             | C++                       | Java                                      |
| ---------------- | ------------------------- | ----------------------------------------- |
| Declare pair     | `pair<int,int> p;`        | `Map.Entry<Integer,Integer> p;`           |
| Initialization  | `p = {a,b};`              | `p = Map.entry(a,b);`                     |
| Access first    | `p.first`                 | `p.getKey()`                              |
| Access second   | `p.second`                | `p.getValue()`                            |

---

### 27. **Iterating Over Map**

| Task                     | C++                                             | Java                                                       |
| ------------------------ | ----------------------------------------------- | ---------------------------------------------------------- |
| Loop over map            | `for(auto &it : mp)`                           | `for(Map.Entry<Integer,Integer> it : mp.entrySet())`      |
| Access key               | `it.first`                                     | `it.getKey()`                                             |
| Access value             | `it.second`                                    | `it.getValue()`                                           |

---

### 28. **Comparator / Custom Sorting**

| Task                     | C++                                                     | Java                                                         |
| ------------------------ | ------------------------------------------------------- | ------------------------------------------------------------ |
| Custom sort (lambda)     | `sort(v.begin(),v.end(),[](a,b){return a>b;});`        | `Collections.sort(v,(a,b)->b-a);`                           |
| Sort 2D by 2nd column    | `sort(v.begin(),v.end(),cmp);`                          | `Collections.sort(v,(a,b)->a[1]-b[1]);`                     |

---

### 29. **2D Array**

| Task                     | C++                           | Java                                |
| ------------------------ | ----------------------------- | ----------------------------------- |
| Declare                  | `int a[n][m];`                | `int[][] a = new int[n][m];`        |
| Initialize               | `int a[2][2]={{1,2},{3,4}};`  | `int[][] a = {{1,2},{3,4}};`        |
| Access element           | `a[i][j]`                     | `a[i][j]`                           |

✅ Same indexing

---

### 30. **Fast Input / Output (Competitive Programming)**

| Task                     | C++                                   | Java                                                   |
| ------------------------ | ------------------------------------- | ------------------------------------------------------ |
| Fast input               | `ios::sync_with_stdio(false);`        | `BufferedReader br = new BufferedReader(new InputStreamReader(System.in));` |
| Fast output              | `cout`                                | `BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));` |
| Read integer             | `cin >> x;`                           | `int x = Integer.parseInt(br.readLine());`            |
| Print integer            | `cout << x`                           | `bw.write(x + "");`                                   |

---

### 31. **Reading Input with StringTokenizer**

| Task              | C++                          | Java                                                                 |
| ----------------- | ---------------------------- | -------------------------------------------------------------------- |
| Fast token input  | `cin >> x >> y;`             | `StringTokenizer st = new StringTokenizer(br.readLine());`<br>`int x = Integer.parseInt(st.nextToken());` |

---

### 32. **Vector of Vectors vs ArrayList of ArrayList**

| Task                     | C++                                      | Java                                                     |
| ------------------------ | ---------------------------------------- | -------------------------------------------------------- |
| Declare                  | `vector<vector<int>> v;`                | `ArrayList<ArrayList<Integer>> v = new ArrayList<>();` |
| Add row                  | `v.push_back({1,2,3});`                 | `v.add(new ArrayList<>(List.of(1,2,3)));`               |
| Access element           | `v[i][j]`                               | `v.get(i).get(j)`                                       |

---

### 33. **Bit Manipulation**

| Task                     | C++                | Java                 |
| ------------------------ | ------------------ | -------------------- |
| Left shift               | `x << 1`           | `x << 1`             |
| Right shift              | `x >> 1`           | `x >> 1`             |
| Check ith bit            | `(x & (1<<i))`     | `(x & (1<<i))`       |
| Set ith bit              | `x |= (1<<i)`      | `x |= (1<<i)`        |
| Toggle ith bit           | `x ^= (1<<i)`      | `x ^= (1<<i)`        |

✅ Same syntax

---

### 34. **Modulus with Negative Numbers**

| Task                   | C++            | Java             |
| ---------------------- | -------------- | ---------------- |
| `-5 % 3`               | `-2`           | `-2`             |
| Fix to positive mod    | `(x%m+m)%m`    | `(x%m+m)%m`      |

✅ Same formula used

---

### 35. **Recursion**

| Task                     | C++                                  | Java                                    |
| ------------------------ | ------------------------------------ | --------------------------------------- |
| Basic recursion          | `int f(int x){ return f(x-1); }`     | `static int f(int x){ return f(x-1); }` |
| Base condition           | `if(x==0) return 1;`                 | `if(x==0) return 1;`                    |

⚠️ Java methods must be `static` if called from `main`.
---

### 36. **Pointers vs References**

| Task                | C++                         | Java                              |
| ------------------- | --------------------------- | ---------------------------------- |
| Pointer declaration| `int *p;`                   | ❌ Not supported                   |
| Address of variable| `p = &x;`                   | ❌ Not available                   |
| Dereference        | `*p`                        | ❌ Not available                   |
| Equivalent concept | Raw memory access           | Object reference (implicit)        |

⚠️ Java does not allow direct memory access.

---

### 37. **Struct vs Class**

| Task                 | C++                              | Java                              |
| -------------------- | -------------------------------- | ---------------------------------- |
| Declare              | `struct Node{ int x; };`         | `class Node { int x; }`            |
| Create object        | `Node n;`                        | `Node n = new Node();`             |
| Access field         | `n.x`                            | `n.x`                              |

⚠️ Java has **no struct**, only classes.

---

### 38. **Boolean Type**

| Task               | C++            | Java             |
| ------------------ | -------------- | ---------------- |
| Declare boolean    | `bool flag;`   | `boolean flag;` |
| True / False       | `true/false`   | `true/false`    |

---

### 39. **Character Input**

| Task               | C++            | Java                                  |
| ------------------ | -------------- | ------------------------------------- |
| Read a character   | `cin >> ch;`   | `char ch = sc.next().charAt(0);`      |

---

### 40. **Exception Handling**

| Task                   | C++                              | Java                                   |
| ---------------------- | -------------------------------- | -------------------------------------- |
| Try–catch block        | `try { } catch(...) { }`         | `try { } catch(Exception e) { }`       |
| Throw exception        | `throw x;`                       | `throw new Exception();`               |

⚠️ Java has **checked exceptions**.

---

### 41. **Linked List Node Definition**

| Task               | C++                                   | Java                                      |
| ------------------ | ------------------------------------- | ----------------------------------------- |
| Node structure     | `struct Node{ int val; Node* next; };`| `class Node{ int val; Node next; }`       |
| Create node        | `Node* n = new Node();`               | `Node n = new Node();`                    |
| Assign next        | `n->next = head;`                     | `n.next = head;`                          |

⚠️ No pointers in Java, only object references.

---

### 42. **Stack Using Array**

| Task               | C++                                  | Java                                   |
| ------------------ | ------------------------------------ | -------------------------------------- |
| Declare array      | `int st[1000];`                      | `int[] st = new int[1000];`            |
| Top index          | `int top = -1;`                      | `int top = -1;`                        |
| Push               | `st[++top] = x;`                     | `st[++top] = x;`                       |
| Pop                | `top--;`                             | `top--;`                               |
| Peek               | `st[top]`                            | `st[top]`                              |

✅ Same implementation logic.

---

### 43. **Adjacency List for Graph**

| Task                      | C++                                      | Java                                                     |
| ------------------------- | ---------------------------------------- | -------------------------------------------------------- |
| Declare                  | `vector<vector<int>> adj(V);`           | `ArrayList<ArrayList<Integer>> adj = new ArrayList<>();` |
| Add edge (u → v)         | `adj[u].push_back(v);`                  | `adj.get(u).add(v);`                                   |

Java initialization:

```java
for(int i=0;i<V;i++) adj.add(new ArrayList<>());
````

---

### 44. **BFS Traversal**

| Task          | C++                          | Java                                     |
| ------------- | ---------------------------- | ---------------------------------------- |
| Queue declare | `queue<int> q;`              | `Queue<Integer> q = new LinkedList<>();` |
| Push source   | `q.push(src);`               | `q.add(src);`                            |
| Pop           | `q.front(); q.pop();`        | `q.peek(); q.poll();`                    |
| Visit array   | `vector<bool> vis(V,false);` | `boolean[] vis = new boolean[V];`        |

Loop logic is identical.

---

### 45. **DFS Traversal (Recursive)**

| Task           | C++                         | Java                            |
| -------------- | --------------------------- | ------------------------------- |
| Function       | `void dfs(int u)`           | `static void dfs(int u)`        |
| Mark visit     | `vis[u] = true;`            | `vis[u] = true;`                |
| Loop neighbors | `for(int v:adj[u]) dfs(v);` | `for(int v:adj.get(u)) dfs(v);` |

✅ Traversal logic is same.

---


### 46. **Pair Sorting by Second Element**

| Task                     | C++                                                     | Java                                                        |
| ------------------------ | ------------------------------------------------------- | ----------------------------------------------------------- |
| Vector of pairs sort     | `sort(v.begin(),v.end(),cmp);`                          | `Collections.sort(v,(a,b)->a.getValue()-b.getValue());`    |

C++ comparator:

```cpp
bool cmp(pair<int,int>& a, pair<int,int>& b){
    return a.second < b.second;
}
```

Java uses lambda directly.

---

### 47. **Frequency Map (Counting Elements)**

| Task             | C++                          | Java                                             |
| ---------------- | ---------------------------- | ------------------------------------------------ |
| Declare freq map | `unordered_map<int,int> mp;` | `HashMap<Integer,Integer> mp = new HashMap<>();` |
| Increment count  | `mp[x]++;`                   | `mp.put(x, mp.getOrDefault(x,0)+1);`             |
| Check frequency  | `mp[x]`                      | `mp.get(x);`                                     |

---

### 48. **Set for Unique Elements**

| Task             | C++             | Java              |
| ---------------- | --------------- | ----------------- |
| Insert element   | `st.insert(x);` | `st.add(x);`      |
| Check existence  | `st.count(x)`   | `st.contains(x);` |
| Number of unique | `st.size()`     | `st.size()`       |

---

### 49. **2D Vector Resize vs 2D ArrayList Build**

| Task              | C++                                        | Java                                                   |
| ----------------- | ------------------------------------------ | ------------------------------------------------------ |
| Declare with size | `vector<vector<int>> v(n,vector<int>(m));` | `ArrayList<ArrayList<Integer>> v = new ArrayList<>();` |
| Add row           | auto handled                               | `v.add(new ArrayList<>());`                            |

Java requires manual row addition.

---

### 50. **Min / Max Element**

| Task        | C++                               | Java                 |
| ----------- | --------------------------------- | -------------------- |
| Max element | `*max_element(v.begin(),v.end())` | `Collections.max(v)` |
| Min element | `*min_element(v.begin(),v.end())` | `Collections.min(v)` |

---


### 51. **Disjoint Set / Union–Find**

| Task            | C++                                   | Java                                      |
| --------------- | ------------------------------------- | ----------------------------------------- |
| Parent array    | `vector<int> parent(n);`              | `int[] parent = new int[n];`              |
| Find function   | `int find(int x)`                     | `static int find(int x)`                  |
| Union function  | `void unite(int a,int b)`             | `static void unite(int a,int b)`          |

Path compression (same logic):

```cpp
int find(int x){
    if(parent[x]==x) return x;
    return parent[x]=find(parent[x]);
}
````

```java
static int find(int x){
    if(parent[x]==x) return x;
    return parent[x]=find(parent[x]);
}
```

---

### 52. **Priority Queue of Pairs**

| Task             | C++                                                                 | Java                                                               |
| ---------------- | ------------------------------------------------------------------- | ------------------------------------------------------------------ |
| Declare min-heap | `priority_queue<pair<int,int>,vector<pair<int,int>>,greater<>> pq;` | `PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);` |
| Push             | `pq.push({d,u});`                                                   | `pq.add(new int[]{d,u});`                                          |
| Access top       | `pq.top().first`                                                    | `pq.peek()[0]`                                                     |
| Pop              | `pq.pop();`                                                         | `pq.poll();`                                                       |

---

### 53. **Sorting Custom Objects**

| Task          | C++                            | Java                                              |
| ------------- | ------------------------------ | ------------------------------------------------- |
| Sort by field | `sort(v.begin(),v.end(),cmp);` | `Collections.sort(v,(a,b)->a.x-b.x);`             |
| Reverse sort  | `sort(v.rbegin(),v.rend());`   | `Collections.sort(v,Collections.reverseOrder());` |

---

### 54. **BFS with Distance Array**

| Task              | C++                       | Java                                             |
| ----------------- | ------------------------- | ------------------------------------------------ |
| Distance array    | `vector<int> dist(V,-1);` | `int[] dist = new int[V]; Arrays.fill(dist,-1);` |
| Initialize source | `dist[src]=0;`            | `dist[src]=0;`                                   |
| Update neighbor   | `dist[v]=dist[u]+1;`      | `dist[v]=dist[u]+1;`                             |

---

### 55. **Reading Large Input of Integers (Fastest)**

| Task           | C++                                       | Java                                           |
| -------------- | ----------------------------------------- | ---------------------------------------------- |
| Fast I/O setup | `ios::sync_with_stdio(false);cin.tie(0);` | `FastScanner fs = new FastScanner(System.in);` |
| Read integer   | `cin >> x;`                               | `int x = fs.nextInt();`                        |

(Logic same, implementation differs)

---

### 56. **Topological Sort (BFS / Kahn’s Algorithm)**

| Task                     | C++                                      | Java                                                   |
| ------------------------ | ---------------------------------------- | ------------------------------------------------------ |
| Indegree array           | `vector<int> in(V,0);`                   | `int[] in = new int[V];`                               |
| Queue initialization     | `queue<int> q;`                          | `Queue<Integer> q = new LinkedList<>();`              |
| Push zero indegree       | `if(in[i]==0) q.push(i);`                | `if(in[i]==0) q.add(i);`                              |
| Pop node                 | `q.front(); q.pop();`                    | `q.poll();`                                           |

---

### 57. **Dijkstra’s Algorithm Skeleton**

| Task                     | C++                                                         | Java                                                                  |
| ------------------------ | ----------------------------------------------------------- | --------------------------------------------------------------------- |
| Distance array           | `vector<int> dist(V,1e9);`                                  | `int[] dist = new int[V]; Arrays.fill(dist, INF);`                    |
| Min heap                 | `priority_queue<pair<int,int>,...,greater<>> pq;`         | `PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);`   |
| Push source              | `pq.push({0,src});`                                         | `pq.add(new int[]{0,src});`                                          |
| Get min                  | `auto [d,u]=pq.top(); pq.pop();`                            | `int[] cur=pq.poll(); int d=cur[0], u=cur[1];`                      |

---

### 58. **Prefix Sum Array**

| Task                     | C++                               | Java                                  |
| ------------------------ | --------------------------------- | ------------------------------------- |
| Declare prefix           | `vector<int> pre(n+1,0);`          | `int[] pre = new int[n+1];`           |
| Build prefix             | `pre[i]=pre[i-1]+arr[i-1];`        | `pre[i]=pre[i-1]+arr[i-1];`           |
| Range sum [l,r]          | `pre[r+1]-pre[l]`                  | `pre[r+1]-pre[l]`                     |

✅ Same logic and formula.

---

### 59. **Bitset / Boolean Marking**

| Task                     | C++                     | Java                          |
| ------------------------ | ----------------------- | ----------------------------- |
| Declare bitset           | `bitset<1000> bs;`      | `boolean[] bs = new boolean[1000];` |
| Set bit                  | `bs[i]=1;`              | `bs[i]=true;`                 |
| Reset bit                | `bs[i]=0;`              | `bs[i]=false;`                |
| Test bit                 | `bs[i]`                 | `bs[i]`                       |

---

### 60. **Modular Exponentiation (Fast Power)**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ------------------------------------------ |
| Function header          | `long long modPow(...)`                  | `static long modPow(...)`                  |
| Multiplication mod       | `(a*b)%mod`                              | `(a*b)%mod`                                |
| Return type              | `long long`                              | `long`                                     |

Core logic is identical in both.

---

### 61. **Sliding Window (Two Pointers)**

| Task                     | C++                                  | Java                                  |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| Left / Right pointers    | `int l=0,r=0;`                       | `int l=0,r=0;`                        |
| Expand window            | `sum += arr[r++];`                   | `sum += arr[r++];`                    |
| Shrink window            | `sum -= arr[l++];`                   | `sum -= arr[l++];`                    |

✅ Logic and syntax are the same.

---

### 62. **Kadane’s Algorithm (Max Subarray Sum)**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Current sum              | `cur = max(a[i], cur+a[i]);`             | `cur = Math.max(a[i], cur+a[i]);`         |
| Global max               | `ans = max(ans, cur);`                  | `ans = Math.max(ans, cur);`               |

⚠️ Java uses `Math.max`.

---

### 63. **Binary Search Template (Manual)**

| Task                     | C++                                  | Java                                  |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| Mid calculation          | `int mid=l+(r-l)/2;`                 | `int mid=l+(r-l)/2;`                  |
| Left move                | `r=mid-1;`                           | `r=mid-1;`                            |
| Right move               | `l=mid+1;`                           | `l=mid+1;`                            |

✅ Identical implementation.

---

### 64. **Trie Node Structure**

| Task               | C++                                        | Java                                      |
| ------------------ | ------------------------------------------ | ----------------------------------------- |
| Node definition    | `struct Node{ Node* child[26]; bool end;};` | `class Node{ Node[] child=new Node[26]; boolean end; }` |
| Create node        | `new Node()`                               | `new Node()`                              |
| Check child        | `if(cur->child[c])`                       | `if(cur.child[c]!=null)`                 |

---

### 65. **Longest Common Prefix (Built-in Helpers)**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Prefix check             | `s.find(pref)==0`                        | `s.startsWith(pref)`                      |
| Substring                | `s.substr(0,k)`                          | `s.substring(0,k)`                        |

⚠️ Java `substring(l,r)` is `[l,r)` (r excluded).

---

### 66. **Union by Rank / Size (DSU Optimization)**

| Task                     | C++                                  | Java                                  |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| Rank array               | `vector<int> rank(n,0);`             | `int[] rank = new int[n];`            |
| Union by rank condition  | `if(rank[x]<rank[y]) parent[x]=y;`  | `if(rank[x]<rank[y]) parent[x]=y;`    |
| Increase rank            | `rank[x]++;`                         | `rank[x]++;`                          |

✅ Same logic, only containers differ.

---

### 67. **StringBuilder vs String (Optimization)**

| Task                     | C++                          | Java                                      |
| ------------------------ | ---------------------------- | ------------------------------------------ |
| Mutable string           | `string s; s+='a';`          | `StringBuilder sb = new StringBuilder();` |
| Append character         | `s.push_back('a');`          | `sb.append('a');`                          |
| Convert to string        | `string(s)`                  | `sb.toString()`                            |

⚠️ `StringBuilder` is used for efficiency in Java.

---

### 68. **Reading Multiple Test Cases**

| Task                     | C++                         | Java                                      |
| ------------------------ | --------------------------- | ------------------------------------------ |
| Read test cases          | `cin >> t;`                | `int t = sc.nextInt();`                    |
| Loop over test cases     | `while(t--)`               | `while(t-- > 0)`                           |

---

### 69. **Lambda / Anonymous Comparator**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Inline comparator        | `[](a,b){return a>b;}`                   | `(a,b)->b-a`                              |
| Sort with lambda         | `sort(v.begin(),v.end(),cmp);`           | `Collections.sort(v,(a,b)->b-a);`        |

✅ Both allow inline custom sorting.

---

### 70. **Bit Counting (Set Bits)**

| Task                     | C++                          | Java                          |
| ------------------------ | ---------------------------- | ----------------------------- |
| Built-in bit count       | `__builtin_popcount(x)`      | `Integer.bitCount(x)`         |
| Manual loop              | `while(x){cnt++; x&=x-1;}`   | `while(x!=0){cnt++; x&=x-1;}` |

✅ Same Kernighan’s algorithm.

---

### 71. **Dynamic Programming Table Initialization**

| Task                     | C++                                       | Java                                          |
| ------------------------ | ----------------------------------------- | --------------------------------------------- |
| 1D DP init to -1         | `vector<int> dp(n,-1);`                   | `int[] dp = new int[n]; Arrays.fill(dp,-1);` |
| 2D DP init to 0          | `vector<vector<int>> dp(n,vector<int>(m));` | `int[][] dp = new int[n][m];`              |

---

### 72. **Memoization with Map (DP on States)**

| Task                     | C++                                          | Java                                             |
| ------------------------ | -------------------------------------------- | ------------------------------------------------ |
| Declare memo map         | `unordered_map<int,int> memo;`               | `HashMap<Integer,Integer> memo = new HashMap<>();` |
| Check cached             | `if(memo.count(x))`                          | `if(memo.containsKey(x))`                        |
| Store result             | `memo[x]=ans;`                               | `memo.put(x,ans);`                               |

---

### 73. **Sorting with Custom Class (Comparable)**

| Task                     | C++                                   | Java                                         |
| ------------------------ | ------------------------------------- | -------------------------------------------- |
| Class with key           | `struct Node{ int x; };`              | `class Node implements Comparable<Node>`    |
| Compare function         | External `cmp` function               | `public int compareTo(Node o)`               |
| Sort                     | `sort(v.begin(),v.end(),cmp);`        | `Collections.sort(v);`                       |

Java compareTo:

```java
public int compareTo(Node o){
    return this.x - o.x;
}
```

---

### 74. **Sorting Primitive vs Object Arrays**

| Task               | C++                       | Java                      |
| ------------------ | ------------------------- | ------------------------- |
| Sort int array     | `sort(arr,arr+n);`        | `Arrays.sort(arr);`       |
| Sort Integer array | `sort(v.begin(),v.end())` | `Arrays.sort(arr, comp);` |

⚠️ Java comparators work only with **object types**, not primitives.

---

### 75. **Adjacency Matrix (Graph)**

| Task           | C++            | Java                         |
| -------------- | -------------- | ---------------------------- |
| Declare matrix | `int g[V][V];` | `int[][] g = new int[V][V];` |
| Add edge u–v   | `g[u][v]=1;`   | `g[u][v]=1;`                 |
| Check edge     | `if(g[u][v])`  | `if(g[u][v]==1)`             |

✅ Same representation and access.


### 76. **Substring Search (Naive / Built-in)**

| Task                     | C++                         | Java                                      |
| ------------------------ | --------------------------- | ------------------------------------------ |
| Check substring          | `s.find(t) != string::npos`| `s.contains(t)`                            |
| Find index               | `s.find(t)`                 | `s.indexOf(t)`                             |

---

### 77. **Fast Exponentiation (Power Function)**

| Task                     | C++                          | Java                          |
| ------------------------ | ---------------------------- | ----------------------------- |
| Built-in power           | `pow(a,b)`                   | `Math.pow(a,b)`               |
| Custom fast power        | `binExp(a,b)`                | `binExp(a,b)`                 |

⚠️ `Math.pow` returns `double`.

---

### 78. **Character to Integer Conversion**

| Task                     | C++                          | Java                          |
| ------------------------ | ---------------------------- | ----------------------------- |
| Char to digit            | `c - '0'`                    | `c - '0'`                     |
| String to int            | `stoi(s)`                    | `Integer.parseInt(s)`         |
| Int to String            | `to_string(x)`               | `String.valueOf(x)`           |

✅ Same `c-'0'` logic.

---

### 79. **Reverse an Array / List**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Reverse array            | `reverse(arr, arr+n);`                   | `for(i=0;i<n/2;i++) swap(...)`            |
| Reverse list             | `reverse(v.begin(), v.end());`           | `Collections.reverse(v);`                |

---

### 80. **Time Complexity Measurement**

| Task                     | C++                          | Java                          |
| ------------------------ | ---------------------------- | ----------------------------- |
| Start timer              | `clock_t t=clock();`         | `long t=System.currentTimeMillis();` |
| End timer                | `clock()-t`                  | `System.currentTimeMillis()-t` |

---

### 81. **Queue with Deque (Optimized BFS / Sliding Window)**

| Task                     | C++                                  | Java                                      |
| ------------------------ | ------------------------------------ | ----------------------------------------- |
| Declare deque            | `deque<int> dq;`                     | `Deque<Integer> dq = new ArrayDeque<>();` |
| Push back                | `dq.push_back(x);`                   | `dq.addLast(x);`                          |
| Push front               | `dq.push_front(x);`                  | `dq.addFirst(x);`                         |
| Pop front                | `dq.pop_front();`                    | `dq.pollFirst();`                        |
| Pop back                 | `dq.pop_back();`                     | `dq.pollLast();`                         |

---

### 82. **Multiset vs TreeMap (Frequency with Order)**

| Task                     | C++                                  | Java                                      |
| ------------------------ | ------------------------------------ | ----------------------------------------- |
| Declare                  | `multiset<int> ms;`                  | `TreeMap<Integer,Integer> mp = new TreeMap<>();` |
| Insert                   | `ms.insert(x);`                      | `mp.put(x, mp.getOrDefault(x,0)+1);`      |
| Erase one occurrence     | `ms.erase(ms.find(x));`              | Decrease freq / remove if zero            |
| Get min element          | `*ms.begin()`                        | `mp.firstKey()`                           |

---

### 83. **Lower Bound on TreeSet**

| Task                     | C++                                  | Java                                      |
| ------------------------ | ------------------------------------ | ----------------------------------------- |
| Lower bound              | `s.lower_bound(x)`                   | `s.ceiling(x)`                            |
| Strictly greater         | `s.upper_bound(x)`                   | `s.higher(x)`                             |

⚠️ `TreeSet` provides direct equivalent methods.

---

### 84. **Fast Output with StringBuilder**

| Task                     | C++                          | Java                                      |
| ------------------------ | ---------------------------- | ------------------------------------------ |
| Append output            | `cout << x << "\n";`         | `sb.append(x).append('\n');`               |
| Final print              | automatic flush              | `System.out.print(sb.toString());`         |

---

### 85. **Main Function Structure**

| Task                     | C++                          | Java                                      |
| ------------------------ | ---------------------------- | ------------------------------------------ |
| Entry point              | `int main()`                 | `public static void main(String[] args)`  |
| Return                   | `return 0;`                  | Not required                              |

⚠️ Java program must be inside a **class**.

---

### 86. **Greedy Interval Scheduling**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Store intervals          | `vector<pair<int,int>> v;`               | `ArrayList<int[]> v = new ArrayList<>();` |
| Sort by end time         | `sort(v.begin(),v.end(),cmp);`           | `Collections.sort(v,(a,b)->a[1]-b[1]);`   |
| Select interval          | `if(v[i].first>=lastEnd)`                | `if(v.get(i)[0] >= lastEnd)`              |

---

### 87. **Matrix Rotation (90°)**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Access element           | `mat[i][j]`                              | `mat[i][j]`                               |
| Swap corners             | `swap(mat[i][j],mat[j][i]);`             | Use temp variable                         |
| Reverse rows             | `reverse(mat[i].begin(),mat[i].end());` | Manual swap / `Collections.reverse` on list |

---

### 88. **Bitwise XOR Properties (Trick Usage)**

| Task                     | C++              | Java               |
| ------------------------ | ---------------- | ------------------ |
| XOR operator             | `a ^ b`          | `a ^ b`            |
| Self cancel              | `x ^ x = 0`      | `x ^ x = 0`        |
| With zero                | `x ^ 0 = x`      | `x ^ 0 = x`        |

✅ Same bitwise behavior.

---

### 89. **Using HashSet for Cycle Detection**

| Task                     | C++                                   | Java                                      |
| ------------------------ | ------------------------------------- | ----------------------------------------- |
| Declare set              | `unordered_set<int> st;`              | `HashSet<Integer> st = new HashSet<>();`  |
| Check visited            | `if(st.count(x))`                     | `if(st.contains(x))`                      |
| Insert                   | `st.insert(x);`                       | `st.add(x);`                              |

---

### 90. **Binary Heap Implementation (Array Based)**

| Task                     | C++                                  | Java                                  |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| Heap array               | `int heap[n];`                       | `int[] heap = new int[n];`            |
| Parent index             | `(i-1)/2`                            | `(i-1)/2`                             |
| Left child               | `2*i+1`                              | `2*i+1`                               |
| Right child              | `2*i+2`                              | `2*i+2`                               |

✅ Indexing formulas are identical.

---

### 91. **Monotonic Stack (Next Greater Element)**

| Task                     | C++                              | Java                                   |
| ------------------------ | -------------------------------- | -------------------------------------- |
| Declare stack            | `stack<int> st;`                 | `Stack<Integer> st = new Stack<>();`   |
| Push                     | `st.push(x);`                    | `st.push(x);`                          |
| Top                      | `st.top()`                       | `st.peek()`                            |
| Pop                      | `st.pop();`                      | `st.pop();`                            |
| While condition          | `while(!st.empty() && st.top()<x)` | `while(!st.isEmpty() && st.peek()<x)` |

---

### 92. **Sliding Window Maximum (Deque Trick)**

| Task                     | C++                                   | Java                                      |
| ------------------------ | ------------------------------------- | ----------------------------------------- |
| Declare deque            | `deque<int> dq;`                      | `Deque<Integer> dq = new ArrayDeque<>();` |
| Remove smaller elements  | `while(!dq.empty() && a[dq.back()]<=a[i]) dq.pop_back();` | `while(!dq.isEmpty() && a[dq.peekLast()]<=a[i]) dq.pollLast();` |
| Push index               | `dq.push_back(i);`                    | `dq.addLast(i);`                          |
| Front (max index)        | `dq.front()`                          | `dq.peekFirst()`                          |

---

### 93. **Two Sum Using HashMap**

| Task                     | C++                                              | Java                                              |
| ------------------------ | ------------------------------------------------ | ------------------------------------------------- |
| Declare map              | `unordered_map<int,int> mp;`                     | `HashMap<Integer,Integer> mp = new HashMap<>();`  |
| Check complement         | `if(mp.count(k-x))`                              | `if(mp.containsKey(k-x))`                         |
| Store index              | `mp[a[i]] = i;`                                  | `mp.put(a[i], i);`                                |

---

### 94. **Top-K Elements Using Heap**

| Task                     | C++                                                | Java                                                   |
| ------------------------ | -------------------------------------------------- | ------------------------------------------------------ |
| Min heap                 | `priority_queue<int,vector<int>,greater<int>> pq;`| `PriorityQueue<Integer> pq = new PriorityQueue<>();`  |
| Keep size ≤ k            | `if(pq.size()>k) pq.pop();`                        | `if(pq.size()>k) pq.poll();`                          |
| Result (top element)    | `pq.top()`                                        | `pq.peek()`                                           |

---

### 95. **Longest Substring Without Repeating Characters**

| Task                     | C++                                          | Java                                          |
| ------------------------ | -------------------------------------------- | --------------------------------------------- |
| Frequency structure     | `vector<int> freq(256,0);`                   | `int[] freq = new int[256];`                  |
| Expand window            | `freq[s[r]]++;`                              | `freq[s.charAt(r)]++;`                        |
| Shrink window            | `freq[s[l]]--; l++;`                         | `freq[s.charAt(l)]--; l++;`                   |

---

### 96. **Detect Cycle in Directed Graph (DFS)**

| Task                     | C++                                         | Java                                             |
| ------------------------ | ------------------------------------------- | ------------------------------------------------ |
| Visited arrays           | `vector<bool> vis, path;`                  | `boolean[] vis, path;`                           |
| Recursive DFS            | `bool dfs(int u)`                           | `static boolean dfs(int u)`                      |
| Back edge check          | `if(path[v]) return true;`                 | `if(path[v]) return true;`                       |

---

### 97. **KMP Prefix Function (LPS Array)**

| Task                     | C++                                  | Java                                  |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| LPS array                | `vector<int> lps(n);`                | `int[] lps = new int[n];`             |
| Compare chars            | `pat[i]==pat[len]`                   | `pat.charAt(i)==pat.charAt(len)`      |
| Backtrack                | `len = lps[len-1];`                  | `len = lps[len-1];`                   |

---

### 98. **Matrix BFS (Grid Traversal)**

| Task                     | C++                                      | Java                                      |
| ------------------------ | ---------------------------------------- | ----------------------------------------- |
| Direction arrays         | `int dx[4]={1,0,-1,0}; dy[4]={0,1,0,-1};`| `int[] dx={1,0,-1,0}, dy={0,1,0,-1};`      |
| Boundary check           | `nx>=0 && ny>=0 && nx<n && ny<m`         | `nx>=0 && ny>=0 && nx<n && ny<m`          |
| Push to queue            | `q.push({nx,ny});`                       | `q.add(new int[]{nx,ny});`                |

---

### 99. **Subset Generation Using Bitmask**

| Task                     | C++                               | Java                                  |
| ------------------------ | --------------------------------- | ------------------------------------- |
| Loop masks               | `for(int mask=0;mask<(1<<n);mask++)` | `for(int mask=0;mask<(1<<n);mask++)` |
| Check bit                | `if(mask&(1<<i))`                 | `if((mask&(1<<i))!=0)`                |

---

### 100. **Fast GCD / LCM**

| Task                     | C++                          | Java                          |
| ------------------------ | ---------------------------- | ----------------------------- |
| GCD                      | `__gcd(a,b)`                 | `Math.gcd(a,b)` ❌            |
| Custom GCD               | `gcd(a,b)`                   | `static int gcd(int a,int b)`|
| LCM                      | `a/__gcd(a,b)*b`             | `a/gcd(a,b)*b`               |

⚠️ Java pre-18 has no built-in `gcd`, implement manually.

---

