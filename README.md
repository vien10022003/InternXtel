# Java Core Learning - Internship XTel

## 📋 Table of Contents

- [Java Core Basic](#java-core-basic)
  - [1. Kỹ thuật lập trình căn bản](#1-kỹ-thuật-lập-trình-căn-bản)
  - [2. Lập trình hướng đối tượng](#2-lập-trình-hướng-đối-tượng)
  - [3. Exception Handling](#3-exception-handling)
  - [4. Lập trình xử lý với Database](#4-lập-trình-xử-lý-với-database)
  - [5. File Config](#5-file-config)
  - [6. Đóng gói chương trình](#6-đóng-gói-chương-trình)
  - [7. Bài tập thực hành](#7-bài-tập-thực-hành-basic)
- [Java Core Advance](#java-core-advance)
  - [1. Stream I/O Nâng cao](#1-stream-io-nâng-cao)
  - [2. Thread & Multithreading](#2-thread--multithreading)
  - [3. Lập trình mạng](#3-lập-trình-mạng)
  - [4. MultiThread & Queue](#4-multithread--queue)
  - [5. Logger](#5-logger)
  - [6. Bài tập thực hành](#6-bài-tập-thực-hành-advance)

---

## 🎯 Java Core Basic

### 1. Kỹ thuật lập trình căn bản

#### 1.1 Cài đặt công cụ
- Download và cài đặt **IntelliJ IDEA**

#### 1.2 Kiểu dữ liệu (8 loại)

| Kiểu | Giá trị mặc định | Bộ nhớ | Min | Max |
|------|---|---|---|---|
| boolean | false | ~1 bit | false | true |
| char | '\u0000' (Unicode UTF-16) | 2 byte | '\u0000' (0) | '\uffff' (65535) |
| byte | 0 | 1 byte | -128 | 127 |
| short | 0 | 2 byte | -32,768 | 32,767 |
| int | 0 | 4 byte | -2³¹ | 2³¹ - 1 |
| long | 0L | 8 byte | -2⁶³ | 2⁶³ - 1 |
| float | 0.0f | 4 byte | ~1.4E-45 | ~3.4028235E38 |
| double | 0.0d | 8 byte | ~4.9E-324 | ~1.7976931348623157E308 |

#### 1.3 Wrapper Class vs Primitive Type

| Đặc điểm | Wrapper Class | Primitive Type |
|---|---|---|
| **Định nghĩa** | Cung cấp cơ chế chuyển đổi primitive → đối tượng | Kiểu dữ liệu được định nghĩa sẵn |
| **Lớp liên kết** | Được sử dụng để tạo đối tượng | Không phải là đối tượng |
| **Giá trị rỗng** | Cho phép `null` | Không cho phép `null` |
| **Bộ nhớ** | Yêu cầu cao hơn | Yêu cầu thấp hơn |
| **Dùng với Collection** | Có thể dùng (ArrayList, Set, etc.) | Không thể dùng |


##### 1.3.1 Heap vs Stack (Bộ nhớ)

**Stack (Ngăn xếp):**
- Lưu trữ các **biến primitive** (int, boolean, double, v.v.) và **tham chiếu (reference)** đến object
- Cấp phát/giải phóng **tự động**: khi biến ra khỏi scope → tự xóa
- Tốc độ **nhanh** nhưng dung lượng **hạn chế**
- Mỗi thread có **stack riêng**
- Nếu stack đầy → **StackOverflowError** (thường do đệ quy vô hạn)

**Heap (Đống):**
- Lưu trữ các **object** (được khởi tạo bằng `new`)
- Cấp phát **thủ công** (`new`), giải phóng bởi **Garbage Collector** (GC) tự động
- Tốc độ **chậm hơn** nhưng dung lượng **lớn hơn**
- **Chia sẻ giữa tất cả thread**
- Nếu heap đầy → **OutOfMemoryError**

**Ví dụ:**
```java
public void example() {
    int age = 25;              // Stack: lưu giá trị 25
    String name = "Alice";     // Stack: lưu tham chiếu → Heap: lưu "Alice"
    Person person = new Person("Bob");  // Stack: tham chiếu → Heap: object Person
    
    // Stack: age, name, person (chứa địa chỉ)
    // Heap: "Alice", "Bob", object Person
}
// Khi hàm kết thúc → age, name, person tự động xóa khỏi stack
// Heap được GC dọn dẹp khi không còn tham chiếu
```

**Hình ảnh:**
```
STACK (Ngăn xếp)          HEAP (Đống)
┌─────────────────┐       ┌──────────────────┐
│ age: 25         │       │ "Alice" (String) │
├─────────────────┤       ├──────────────────┤
│ name: @ref1 ───────→    │ "Bob" (String)   │
├─────────────────┤       ├──────────────────┤
│ person: @ref2 ─────→    │ Person{...}      │
└─────────────────┘       └──────────────────┘
```

**So sánh:**

| Tiêu chí | Stack | Heap |
|---|---|---|
| **Lưu gì** | Primitive, reference | Object (new) |
| **Tốc độ** | Nhanh | Chậm hơn |
| **Dung lượng** | Nhỏ (~1-8MB) | Lớn (~heap size) |
| **Giải phóng** | Tự động (scope) | GC tự động |
| **Thread** | Riêng từng thread | Chung tất cả thread |
| **Error** | StackOverflowError | OutOfMemoryError |
| **Thread-safe** | An toàn (riêng) | Cần đồng bộ hóa |

##### 1.3.2 Collection Framework (Tập hợp)

**Khái niệm**: Collection là cấu trúc dữ liệu để lưu trữ nhiều object, có thể thêm/xóa/tìm kiếm dễ dàng.

**Phân loại:**

**1️⃣ List (Danh sách - có thứ tự, cho phép trùng)**

```java
// ArrayList: mảng động, dễ truy cập (index), chậm thêm/xóa
List<String> arrayList = new ArrayList<>();
arrayList.add("Alice");
arrayList.add("Bob");
arrayList.add("Alice");  // Cho phép trùng
System.out.println(arrayList.get(0));  // Nhanh: O(1)
arrayList.remove(1);  // Chậm: O(n) vì phải dịch chuyển

// LinkedList: danh sách liên kết, chậm truy cập, nhanh thêm/xóa
List<String> linkedList = new LinkedList<>();
linkedList.add("Alice");
linkedList.add("Bob");
linkedList.add(1, "Charlie");  // Nhanh thêm vào giữa
System.out.println(linkedList.get(0));  // Chậm: phải duyệt từ đầu
```

**ArrayList vs LinkedList:**

| Tiêu chí | ArrayList | LinkedList |
|---|---|---|
| **Cấu trúc** | Mảng động | Danh sách liên kết |
| **Truy cập (get)** | O(1) nhanh | O(n) chậm |
| **Thêm/Xóa cuối** | O(1) nhanh | O(1) nhanh |
| **Thêm/Xóa giữa** | O(n) chậm | O(1) nhanh |
| **Bộ nhớ** | Liên tục | Rải rác |
| **Khi dùng** | Đọc nhiều | Thêm/xóa nhiều |

**2️⃣ Set (Tập hợp - không có thứ tự, không cho phép trùng)**

```java
// HashSet: nhanh nhất, không có thứ tự
Set<String> hashSet = new HashSet<>();
hashSet.add("Alice");
hashSet.add("Bob");
hashSet.add("Alice");  // Tự loại bỏ trùng
System.out.println(hashSet.size());  // 2
// Không có get(index), duyệt bằng for-each

// TreeSet: sắp xếp tự động, chậm hơn
Set<String> treeSet = new TreeSet<>();
treeSet.add("Charlie");
treeSet.add("Alice");
treeSet.add("Bob");
System.out.println(treeSet);  // Output: [Alice, Bob, Charlie] - tự động sort

// LinkedHashSet: giữ thứ tự thêm vào
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("Charlie");
linkedHashSet.add("Alice");
linkedHashSet.add("Bob");
System.out.println(linkedHashSet);  // Output: [Charlie, Alice, Bob]
```

**3️⃣ Map (Bản đồ - key-value pairs, mỗi key duy nhất)**

```java
// HashMap: nhanh nhất, không sắp xếp
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("Alice", 25);
hashMap.put("Bob", 30);
hashMap.put("Alice", 26);  // Ghi đè giá trị cũ
System.out.println(hashMap.get("Alice"));  // 26
System.out.println(hashMap.containsKey("Bob"));  // true

// TreeMap: sắp xếp theo key
Map<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Charlie", 28);
treeMap.put("Alice", 25);
treeMap.put("Bob", 30);
// Duyệt: Alice, Bob, Charlie (sorted)

// Duyệt Map
for (String key : hashMap.keySet()) {
    System.out.println(key + ": " + hashMap.get(key));
}

// Hoặc dùng entrySet (nhanh hơn)
for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

**4️⃣ Queue (Hàng đợi - FIFO: First-In-First-Out)**

```java
// LinkedList cũng có thể làm Queue
Queue<String> queue = new LinkedList<>();
queue.add("Alice");    // offer()/add(): thêm vào cuối
queue.add("Bob");
queue.add("Charlie");

System.out.println(queue.peek());  // "Alice" (xem mà không xóa)
System.out.println(queue.poll());  // "Alice" (xóa và trả về)
System.out.println(queue.poll());  // "Bob"
// Còn lại: Charlie

// PriorityQueue: theo ưu tiên (priority)
Queue<Integer> pq = new PriorityQueue<>();
pq.add(30);
pq.add(10);
pq.add(20);
System.out.println(pq.poll());  // 10 (nhỏ nhất - ưu tiên cao)
System.out.println(pq.poll());  // 20
```

**So sánh tất cả Collection:**

| Loại | Thứ tự | Trùng | Truy cập | Khi dùng |
|---|---|---|---|---|
| **ArrayList** | ✓ | ✓ | Nhanh (index) | Đọc nhiều |
| **LinkedList** | ✓ | ✓ | Chậm | Thêm/xóa giữa |
| **HashSet** | ✗ | ✗ | Nhanh tìm | Loại bỏ trùng |
| **TreeSet** | ✓ Sort | ✗ | Chậm | Cần sắp xếp |
| **HashMap** | ✗ | ✗ key | Nhanh | Key-value |
| **TreeMap** | ✓ Sort | ✗ key | Chậm | Key-value sorted |
| **Queue** | FIFO | ✓ | Đặc biệt | Hàng đợi |

**Ví dụ tổng hợp:**
```java
// Đọc danh sách học sinh, loại bỏ trùng, sắp xếp
List<String> students = new ArrayList<>();
students.add("Alice");
students.add("Bob");
students.add("Alice");

// Loại bỏ trùng
Set<String> uniqueStudents = new HashSet<>(students);
System.out.println(uniqueStudents);  // [Alice, Bob]

// Sắp xếp
List<String> sorted = new ArrayList<>(new TreeSet<>(students));
System.out.println(sorted);  // [Alice, Bob]

// Map lưu điểm
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 8);
scores.put("Bob", 9);
scores.put("Charlie", 7);

// Tìm điểm cao nhất
int maxScore = Collections.max(scores.values());
System.out.println("Điểm cao nhất: " + maxScore);
```

#### 1.4 Các câu lệnh điều kiện & vòng lặp

**If - Else If - Else (điều kiện 1 chiều)**
```java
int score = 75;
if (score >= 90) {
    System.out.println("Xuất sắc (A)");
} else if (score >= 80) {
    System.out.println("Giỏi (B)");
} else if (score >= 70) {
    System.out.println("Khá (C)");
} else {
    System.out.println("Trung bình (D)");
}
```

**Switch-Case (lựa chọn theo giá trị)**
```java
int day = 3;
switch (day) {
    case 1:
        System.out.println("Thứ 2");
        break;  // Bắt buộc, nếu không sẽ "rơi xuống" case tiếp theo
    case 2:
        System.out.println("Thứ 3");
        break;
    case 3:
        System.out.println("Thứ 4");
        break;
    default:
        System.out.println("Ngày không hợp lệ");
}
```

**For Loop (lặp với số lần cố định)**
```java
// For truyền thống: for (khởi tạo; điều kiện; bước nhảy)
for (int i = 1; i <= 5; i++) {
    System.out.println("i = " + i);  // In: i = 1, 2, 3, 4, 5
}

// For-each: duyệt mảng/collection dễ dàng
int[] arr = {10, 20, 30};
for (int num : arr) {
    System.out.println(num);  // In: 10, 20, 30
}
```

**While Loop (lặp khi điều kiện đúng)**
```java
int count = 1;
while (count <= 3) {
    System.out.println("Count = " + count);
    count++;  // Tăng count, nếu không → vòng lặp vô hạn (infinite loop)
}
// Output: Count = 1, Count = 2, Count = 3
```

**Do-While Loop (chạy ít nhất 1 lần, rồi kiểm tra điều kiện)**
```java
int num = 1;
do {
    System.out.println("num = " + num);
    num++;
} while (num <= 3);  // Kiểm tra điều kiện sau khi chạy lần đầu
// Output: num = 1, num = 2, num = 3

// Ví dụ: nhập dữ liệu hợp lệ (do-while lý tưởng hơn)
int input;
do {
    System.out.print("Nhập số từ 1-10: ");
    input = scanner.nextInt();
} while (input < 1 || input > 10);  // Nhập lại nếu không hợp lệ
```

**Break & Continue**
```java
// break: thoát khỏi vòng lặp
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break;  // Dừng ngay lập tức khi i == 5
    }
    System.out.println(i);  // In: 1, 2, 3, 4 (dừng trước 5)
}

// continue: bỏ qua vòng lặp hiện tại, sang vòng tiếp theo
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;  // Bỏ qua khi i == 3
    }
    System.out.println(i);  // In: 1, 2, 4, 5 (bỏ 3)
}
```

#### 1.5 Giải thuật làm việc với mảng

![alt text](https://images.viblo.asia/5f9f719d-de0d-4323-b829-264cf9c05be5.png)

| Thuật toán | Ý tưởng | Độ phức tạp | Ghi chú |
|---|---|---|---|
| **Bubble Sort** | Đổi chỗ phần tử liền kề | O(n²) | For lồng nhau, so sánh liền kề nhỏ hơn thì đổi chỗ |
| **Selection Sort** | Chọn phần tử nhỏ nhất | O(n²) | Tìm bé nhất cho xuống đầu, lặp tới hết |
| **Insertion Sort** | Chèn phần tử vào vị trí đúng | O(n²) trung bình, O(n) tốt nhất | Chạy từ phần tử thứ 2, so sánh & dịch chuyển để chèn |
| **Quick Sort** | Chọn chốt, nhỏ hơn sang trái, lớn hơn sang phải | O(n log n) | 2 hàm: phân vùng & triển khai đệ quy |
| **Merge Sort** | Chia đôi & gộp lại có thứ tự | O(n log n) | Đệ quy, sau đó gộp 2 mảng đã sort |
| **Heap Sort** | Tạo thành tree với mỗi node có 2 leaf, node cha luôn lớn hơn node lá, luôn tìm được node lớn nhất | O(n log n) | Chia 2 phần: (1) Xây dựng heap - duyệt từ dưới cây lên để tạo cấu trúc cây, đưa phần tử lớn nhất lên; (2) Sắp xếp - đổi chỗ i với con lớn hơn, di chuyển xuống theo cây, lặp lại cho đến khi node i không còn bé hơn con (node = i thì lá phải = 2i và trái = 2i+1; cha nó là i/2)|

**Quick Sort Example:**
```java
void sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        sort(arr, low, pi - 1);
        sort(arr, pi + 1, high);
    }
}
```

**Merge Sort Example:**
```java
void mergeSort(int *arr, int l, int r) {
    if (l >= r) return;
    
    int mid = (l + r) / 2;
    mergeSort(arr, l, mid);
    mergeSort(arr, mid + 1, r);
    mergeParts(arr, l, mid, r);
}

// mergeParts(): gộp 2 mảng đã sắp xếp
// while (trái còn && phải còn) {
//   cái nào nhỏ hơn thì cho vào mảng temp; ++
// }
```

**Insertion Sort Example:**
```java
void insertionSort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int key = arr[i];
        int j = i - 1;
        
        // Dịch chuyển các phần tử lớn hơn key sang phải
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        // Chèn key vào vị trí đúng
        arr[j + 1] = key;
    }
}
```

**Heap Sort Example:**
```java
void heapSort(int arr[], int n) {
    // Xây dựng max-heap
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, n, i);
    }
    
    // Lấy phần tử lớn nhất từ heap
    for (int i = n - 1; i > 0; i--) {
        // Đổi root (phần tử lớn nhất) với phần tử cuối
        int temp = arr[0];
        arr[0] = arr[i];
        arr[i] = temp;
        
        // Heapify lại root
        heapify(arr, i, 0);
    }
}

void heapify(int arr[], int n, int i) {
    int largest = i;
    int left = 2 * i + 1;      // con trái
    int right = 2 * i + 2;     // con phải
    
    // Tìm phần tử lớn nhất trong 3: parent, left child, right child
    if (left < n && arr[left] > arr[largest]) {
        largest = left;
    }
    if (right < n && arr[right] > arr[largest]) {
        largest = right;
    }
    
    // Nếu phần tử lớn nhất không phải parent, đổi chỗ & heapify
    if (largest != i) {
        int temp = arr[i];
        arr[i] = arr[largest];
        arr[largest] = temp;
        heapify(arr, n, largest);
    }
}
```

#### 1.6 Stream Input/Output - Các loại

**ByteStream** (xử lý: ảnh, video, PDF, binary data)
```
InputStream (read())
├── FileInputStream        
├── BufferedInputStream   (đọc thành khối lớn)
└── DataInputStream       (đọc int, double, string)

OutputStream
├── FileOutputStream      
├── BufferedOutputStream  (ghi theo khối)
├── DataOutputStream      (ghi int, double, string)
└── PrintStream          (ghi text ở dạng byte)
```

**Character Stream** (xử lý: text, Unicode)
```
Reader (đọc)
├── FileReader          
├── BufferedReader      (đọc nhanh)
└── InputStreamReader   (byte → char)

Writer (ghi)
├── FileWriter          
├── BufferedWriter      (ghi có bộ đệm)
├── OutputStreamWriter  (char → byte)
└── PrintWriter         (ghi text)
```

Buffer: Dữ liệu được ghi vào bộ đệm, và chỉ khi bộ đệm đầy hoặc gọi flush(), dữ liệu mới được gửi đến luồng đầu ra bên dưới
---

### 2. Lập trình hướng đối tượng

#### 2.1 Khái niệm cơ bản

| Khái niệm | Giải thích |
|---|---|
| **Class** | Khuôn mẫu mô tả cấu trúc & hành vi chung của một nhóm đối tượng |
| **Đối tượng** | Thực thể cụ thể của class, chiếm bộ nhớ |

#### 2.2 Bốn đặc điểm OOP

1. **Đóng gói (Encapsulation)**
   - Giấu dữ liệu bên trong class, không cho phép truy cập trực tiếp
   - Truy cập có kiểm soát via access modifier: `public` (mọi nơi), `private` (chỉ class này), `default` (package), `protected` (package + subclass)
   - Sử dụng getter/setter để kiểm soát dữ liệu
   
   **Ví dụ:**
   ```java
   public class BankAccount {
       private double balance = 0;  // Ẩn, không cho truy cập trực tiếp
       
       public double getBalance() { 
           return balance;  // Chỉ lấy, không sửa
       }
       
       public void deposit(double amount) {
           if (amount > 0) {  // Kiểm tra dữ liệu hợp lệ
               balance += amount;
           }
       }
       
       public boolean withdraw(double amount) {
           if (amount <= balance) {  // Kiểm tra trước khi trừ
               balance -= amount;
               return true;
           }
           return false;  // Không đủ tiền
       }
   }
   ```

2. **Kế thừa (Inheritance)**
   - Tái sử dụng code từ class cha, tránh lặp lại code
   - Dùng `extends` cho class, `implements` cho interface
   - Kiểu: Class cha (Lớp cha) → Class con (Lớp con) kế thừa
   
   **Ví dụ:**
   ```java
   // Class cha
   public class Animal {
       protected String name;  // protected: subclass có thể dùng
       
       public Animal(String name) {
           this.name = name;
       }
       
       public void sound() {
           System.out.println("Kêu gì đó");
       }
   }
   
   // Class con kế thừa
   public class Dog extends Animal {
       public Dog(String name) {
           super(name);  // Gọi constructor của class cha
       }
       
       @Override
       public void sound() {
           System.out.println(name + " sủa: Gâu gâu!");
       }
   }
   
   // Sử dụng
   Dog dog = new Dog("Milu");
   dog.sound();  // Output: Milu sủa: Gâu gâu!
   ```

3. **Đa hình (Polymorphism)**
   - Một phương thức có nhiều hình thức triển khai khác nhau
   - **Method Overriding** (Ghi đè - runtime): class con viết lại phương thức của class cha
   - **Method Overloading** (Nạp chồng - compile-time): cùng tên nhưng khác số lượng/loại tham số
   
   **Ví dụ Overriding:**
   ```java
   public class Shape {
       public void draw() {
           System.out.println("Vẽ hình chung");
       }
   }
   
   public class Circle extends Shape {
       @Override
       public void draw() {
           System.out.println("Vẽ hình tròn");
       }
   }
   
   public class Rectangle extends Shape {
       @Override
       public void draw() {
           System.out.println("Vẽ hình chữ nhật");
       }
   }
   
   // Sử dụng - cùng loại (Shape), hành vi khác nhau
   Shape shape1 = new Circle();
   Shape shape2 = new Rectangle();
   shape1.draw();  // Output: Vẽ hình tròn
   shape2.draw();  // Output: Vẽ hình chữ nhật
   ```
   
   **Ví dụ Overloading:**
   ```java
   public class Calculator {
       public int add(int a, int b) {
           return a + b;
       }
       
       public double add(double a, double b) {  // Khác loại tham số
           return a + b;
       }
       
       public int add(int a, int b, int c) {  // Khác số lượng tham số
           return a + b + c;
       }
   }
   
   Calculator calc = new Calculator();
   System.out.println(calc.add(5, 3));           // Output: 8
   System.out.println(calc.add(5.5, 3.2));       // Output: 8.7
   System.out.println(calc.add(5, 3, 2));        // Output: 10
   ```

4. **Trừu tượng (Abstraction)**
   - Ẩn chi tiết phức tạp, chỉ công khai những phương thức cần thiết
   - Sử dụng `abstract class` (có thể có phương thức cụ thể) hoặc `interface` (chỉ khai báo, Java 8+ có default)
   
   **Ví dụ Abstract Class:**
   ```java
   public abstract class Vehicle {
       protected String brand;
       
       public Vehicle(String brand) {
           this.brand = brand;
       }
       
       // Phương thức trừu tượng - class con bắt buộc triển khai
       public abstract void start();
       
       // Phương thức cụ thể - có thể dùng chung
       public void displayBrand() {
           System.out.println("Thương hiệu: " + brand);
       }
   }
   
   public class Car extends Vehicle {
       public Car(String brand) {
           super(brand);
       }
       
       @Override
       public void start() {
           System.out.println(brand + " khởi động chiếc xe ô tô...");
       }
   }
   
   // Không thể new Vehicle() vì abstract, phải qua subclass
   Vehicle car = new Car("Toyota");
   car.start();  // Output: Toyota khởi động chiếc xe ô tô...
   ```
   
   **Ví dụ Interface:**
   ```java
   public interface Drawable {
       void draw();  // Phương thức interface (công khai, trừu tượng)
   }
   
   public interface Resizable {
       void resize(int size);
   }
   
   public class Image implements Drawable, Resizable {
       @Override
       public void draw() {
           System.out.println("Vẽ ảnh");
       }
       
       @Override
       public void resize(int size) {
           System.out.println("Thay đổi kích thước ảnh: " + size);
       }
   }
   ```

#### 2.3 Cấu trúc một Class

```java
// 2️⃣ Class chính (Entity)
public class Dog {
    // 🔹 Thuộc tính: private + final
    private String name;
    private int age;
    public static final String SPECIES = "Canis lupus familiaris";

    // 🔹 Constructor: khởi tạo đối tượng
    public Dog(String name, int age) {
        this.name = name;
        setAge(age);  // dùng setter để validate
    }

    // 🔹 Getter & Setter
    public String getName() { 
        return name; 
    }
    
    public int getAge() { 
        return age; 
    }
    
    public void setAge(int age) {
        if (age > 0) this.age = age;
    }

    // 🔹 Phương thức thường
    public void bark() {
        System.out.println(name + " sủa: Gâu gâu!");
    }
}
```

**Cú pháp khai báo:**
```java
[access_modifier] [non_access_modifier] class ClassName {
    [access_modifier] [non_access_modifier] DataType fieldName = initialValue;
    [access_modifier] ClassName([params]) { ... }
    [access_modifier] [non_access_modifier] ReturnType methodName([params]) { ... }
}
```

---

### 3. Exception Handling

#### 3.1 Phân loại Exception

| Loại | Thời điểm xảy ra | Ví dụ | Xử lý |
|---|---|---|---|
| **Compile-time Exception** | Lúc biên dịch | IOException, ClassNotFoundException | Bắt buộc xử lý trước khi build |
| **Runtime Exception** | Lúc chạy chương trình | NullPointerException, ArrayIndexOutOfBoundsException | Không bắt buộc nhưng nên xử lý |

#### 3.2 Cơ chế Try-Catch-Finally

```java
try {
    // Code có thể gây lỗi
    int[] arr = {1, 2, 3};
    int result = arr[5];  // ArrayIndexOutOfBoundsException - vì chỉ có 3 phần tử (0-2)
} catch (ArrayIndexOutOfBoundsException e) {
    // Xử lý lỗi cụ thể
    System.out.println("Lỗi: Chỉ số mảng ngoài phạm vi! " + e.getMessage());
} catch (Exception e) {
    // Xử lý lỗi chung (luôn đặt cuối cùng vì Exception là cha của tất cả)
    System.out.println("Lỗi không xác định: " + e);
} finally {
    // Luôn chạy, dù có lỗi hay không
    // → dùng để giải phóng tài nguyên (đóng file, đóng database connection, v.v.)
    System.out.println("Đã xử lý xong!");
}
```

**Ví dụ thực tế - Đọc file:**
```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("data.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (FileNotFoundException e) {
    System.out.println("File không tồn tại: " + e.getMessage());
} catch (IOException e) {
    System.out.println("Lỗi khi đọc file: " + e.getMessage());
} finally {
    // Đóng file resource
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi đóng file: " + e);
        }
    }
}
```

**Try-with-resources (Java 7+) - Tự động đóng tài nguyên:**
```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (FileNotFoundException e) {
    System.out.println("File không tồn tại");
} catch (IOException e) {
    System.out.println("Lỗi khi đọc: " + e.getMessage());
}
// reader.close() được gọi tự động
```

#### 3.3 Throw & Throws

- **`throw`**: Ném trực tiếp một đối tượng exception tại runtime, dừng chương trình ngay lập tức
- **`throws`**: Khai báo phương thức có thể ném exception, chuyển trách nhiệm xử lý cho hàm gọi

**Ví dụ Throw:**
```java
public class User {
    private int age;
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            // Ném exception với thông báo lỗi
            throw new IllegalArgumentException("Tuổi phải từ 0 đến 150, nhận được: " + age);
        }
        this.age = age;
    }
}

// Sử dụng
try {
    User user = new User();
    user.setAge(-5);  // Sẽ ném IllegalArgumentException
} catch (IllegalArgumentException e) {
    System.out.println("Lỗi: " + e.getMessage());
}
```

**Ví dụ Throws:**
```java
// Phương thức khai báo nó có thể ném IOException
public static String readFile(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String content = "";
    String line;
    while ((line = reader.readLine()) != null) {
        content += line + "\n";
    }
    reader.close();
    return content;
}

// Khi gọi phương thức, phải xử lý exception
try {
    String data = readFile("data.txt");
    System.out.println(data);
} catch (IOException e) {
    System.out.println("Không thể đọc file: " + e.getMessage());
}
```

**Tạo Exception tùy chỉnh:**
```java
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public void validateAge(int age) throws InvalidAgeException {
    if (age < 18) {
        throw new InvalidAgeException("Phải từ 18 tuổi trở lên!");
    }
}
```

---

### 4. Lập trình xử lý với Database

#### 4.1 Cài đặt
- Cài đặt Oracle (10G hoặc 11G)
- Hoặc dùng Oracle XE (Express Edition) - miễn phí, nhẹ hơn

#### 4.2 Các kiến thức cần học
- Các câu lệnh cơ bản SQL: SELECT, INSERT, UPDATE, DELETE, WHERE, JOIN, GROUP BY, ORDER BY
- Table: tạo bảng, alter, drop
- Store Procedure: khối lệnh PL/SQL để thực hiện logic phức tạp
- Function: hàm trả về giá trị
- Trigger: tự động chạy khi có sự kiện (INSERT, UPDATE, DELETE)
- Sequence: tạo số tự tăng cho ID
- Partition: chia dữ liệu lớn thành các phần nhỏ
- Job & Schedule: chạy PL/SQL theo lịch biểu

#### 4.3 Kết nối Java với Database qua JDBC

**Các bước:**
1. Tải Driver JDBC (Oracle, MySQL, PostgreSQL, v.v.)
2. Mở kết nối đến Database
3. Thực thi câu lệnh SQL
4. Xử lý kết quả
5. Đóng kết nối

**Ví dụ:**
```java
import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";  // URL kết nối
        String username = "scott";  // Tên đăng nhập
        String password = "tiger";  // Mật khẩu
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 1. Load Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // 2. Mở kết nối
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");
            
            // 3. Tạo Statement để thực thi SQL
            stmt = conn.createStatement();
            
            // 4. Thực thi câu lệnh SELECT
            String query = "SELECT * FROM emp WHERE salary > 5000";
            rs = stmt.executeQuery(query);
            
            // 5. Xử lý kết quả
            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                System.out.println("ID: " + empId + ", Tên: " + name + ", Lương: " + salary);
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Oracle không tìm thấy!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 6. Đóng tài nguyên (nên dùng try-with-resources)
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

**Cách tốt hơn - Try-with-resources:**
```java
public void insertEmployee(String name, double salary) {
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String sql = "INSERT INTO emp (name, salary) VALUES (?, ?)";
    
    try (Connection conn = DriverManager.getConnection(url, "scott", "tiger");
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Dùng PreparedStatement để tránh SQL Injection
        pstmt.setString(1, name);
        pstmt.setDouble(2, salary);
        
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Thêm mới thành công!");
        }
        
    } catch (SQLException e) {
        System.out.println("Lỗi khi thêm dữ liệu: " + e.getMessage());
    }
}
```

**PreparedStatement vs Statement:**
- **Statement**: Chỉnh sửa trực tiếp SQL, dễ bị SQL Injection (lỗ hổng bảo mật)
- **PreparedStatement**: Sử dụng `?` placeholder, an toàn hơn, tốc độ nhanh hơn nếu thực thi nhiều lần

---

### 5. File Config

- Sử dụng Java 14 hoặc các phiên bản khác
- Ở mức cơ bản: tự viết & đọc file text cấu hình
- Lợi ích: truyền tham số mà không cần build lại code (hard-code)

**File Properties**

`config.properties` (file cấu hình):
```properties
# Cấu hình Database
db.url=jdbc:oracle:thin:@localhost:1521:XE
db.username=scott
db.password=tiger
db.port=1521

# Cấu hình ứng dụng
app.name=My Application
app.version=1.0
app.debug=true
```

`ConfigReader.java` (đọc file):
```java
import java.io.*;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();
    
    static {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file config: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}

// Sử dụng
String dbUrl = ConfigReader.getProperty("db.url");
String dbUser = ConfigReader.getProperty("db.username");
String appName = ConfigReader.getProperty("app.name", "Default App");
```

**Ưu điểm file config:**
- Thay đổi tham số mà không cần biên dịch lại code
- Dễ triển khai trên các môi trường khác nhau (dev, test, production)
- Quản lý password, URL database an toàn hơn

---

### 6. Đóng gói chương trình

Chuyển code Java từ các file `.java` thành một file chạy độc lập mà không cần source code.

**Các loại:**

| Loại | Định dạng | Mô tả | Cách chạy |
|---|---|---|---|
| **JAR** | `.jar` | Java Archive - nén các class files, resources, metadata | `java -jar app.jar` |
| **WAR** | `.war` | Web Archive - cho web application (JSP, Servlet) | Deploy vào Tomcat, Jetty |
| **EXE** | `.exe` | Executable - chạy trên Windows mà không cần Java | Double-click trực tiếp |

**Cách tạo JAR:**

**Dùng IDE (IntelliJ IDEA):**
- Menu: File → Project Structure → Artifacts
- Click "+" → JAR → From modules with dependencies
- Chọn Main Class
- Build → Build Artifacts → Build

**Chuyển JAR → EXE (Windows):**
- Dùng công cụ: Launch4j, JSmooth, IzPack
- Tự động đóng gói JRE (Java Runtime Environment) vào EXE
- Người dùng không cần cài Java


**Chạy JAR:**
   ```bash
   java -jar app.jar
   ```
---

### 7. Bài tập thực hành (Basic)

#### 7a. Nhập số với số lần giới hạn
```
Yêu cầu:
- Nhập 1 số cho tới khi nhập đúng → thông báo thành công
- Tối đa 5 lần nhập
- Sai → dừng chương trình & hiện thông báo lỗi
```

#### 7b. Tính tiền điện
```
Yêu cầu:
- Nhập số điện đã dùng trong tháng
- Bảng giá:
  • 100 số đầu: 1000đ/số
  • Từ 100-150 số: 1500đ/số
  • Từ 150 số trở lên: 2000đ/số
```

#### 7c. Sắp xếp mảng với Quick Sort
```
Yêu cầu:
- Đọc từ file input.txt
- Các số cách nhau bằng dấu cách hoặc xuống dòng
- Sắp xếp bằng Quick Sort
```

#### 7d. Quản lý sinh viên với Database
```
Yêu cầu:
- Nhập n sinh viên: tên, giới tính, quê quán, tuổi
- Insert vào DB (bàn phím → Enter → DB)
- Tên không được trùng
- ID tự tăng
```

---

## 🚀 Java Core Advance

### 1. Stream I/O Nâng cao

Ngoài ByteStream và CharacterStream cơ bản, có những loại stream khác:

**Serialization - Lưu object thành file:**
```java
import java.io.*;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

// Ghi object vào file (Serialization)
public void saveObject(Person person, String filename) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
        oos.writeObject(person);  // Lưu object
        System.out.println("Object đã lưu!");
    }
}

// Đọc object từ file (Deserialization)
public Person loadObject(String filename) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
        Person person = (Person) ois.readObject();  // Đọc object
        System.out.println("Object đã tải: " + person);
        return person;
    }
}
```

**RandomAccessFile - Đọc/ghi ở vị trí bất kỳ:**
```java
public void modifyFileAtPosition(String filename, int position, String data) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
        file.seek(position);  // Đi tới vị trí
        file.writeBytes(data);  // Ghi dữ liệu
        System.out.println("Đã sửa file ở vị trí " + position);
    }
}
```

**NIO (Non-Blocking I/O) - Xử lý file lớn hiệu quả:**
```java
import java.nio.file.*;
import java.nio.channels.*;

// Coppy file lớn nhanh với Channel
public void copyFileFast(String source, String dest) throws IOException {
    try (FileInputStream in = new FileInputStream(source);
         FileOutputStream out = new FileOutputStream(dest);
         FileChannel inChannel = in.getChannel();
         FileChannel outChannel = out.getChannel()) {
        
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy file thành công!");
    }
}

// Đọc file dạng mảng byte (toàn bộ)
public byte[] readFileToBytes(String filename) throws IOException {
    Path path = Paths.get(filename);
    return Files.readAllBytes(path);
}

// Đọc file dạng List<String> (từng dòng)
public List<String> readFileLines(String filename) throws IOException {
    Path path = Paths.get(filename);
    return Files.readAllLines(path);
}
```

**So sánh các cách đọc/ghi:**

| Loại | Ưu điểm | Nhược điểm | Sử dụng |
|---|---|---|---|
| **FileInputStream/OutputStream** | Đơn giản, cơ bản | Chậm với file lớn | File nhỏ, thử nghiệm |
| **BufferedInputStream/OutputStream** | Nhanh hơn (buffering) | Cần kiểm soát buffer | File trung bình |
| **FileReader/FileWriter** | Unicode-friendly | Chậm với dữ liệu nhị phân | Xử lý text |
| **NIO (Channel)** | Cực nhanh, hiệu quả | Phức tạp hơn | File lớn, ứng dụng hiệu suất cao |
| **Files API (Java 7+)** | Ngắn gọn, tiện lợi | Tải toàn bộ vào bộ nhớ | Xử lý file nhanh gọn |

---

### 2. Thread & Multithreading

#### 2.1 Khái niệm

- **Thread**: Đơn vị thực thi nhỏ nhất của CPU
- **Multithreading**: Trong Java, mỗi thread là một đường chạy độc lập nhưng chia sẻ bộ nhớ Heap
- **Lợi ích**:
  - Tận dụng nhiều core CPU
  - Xử lý song song
  - Giữ UI mượt khi làm tác vụ nặng
  - Giảm thời gian chờ I/O

#### 2.2 Ba cách khởi tạo Thread

| Cách | Cú pháp | Ưu điểm | Nhược điểm |
|---|---|---|---|
| **1. Kế thừa Thread** | `class MyThread extends Thread { public void run() {...} }` | Đơn giản, trực tiếp | Java không hỗ trợ đa kế thừa → không thể kế thừa class khác |
| **2. Implement Runnable** | `class Task implements Runnable { public void run() {...} }` | Linh hoạt, tách biệt logic & thread lifecycle | Hơi dài dòng |
| **3. ExecutorService** ⭐ | `ExecutorService pool = Executors.newFixedThreadPool(4);` | Quản lý pool, tái sử dụng thread, xử lý Future tốt, chuẩn production | Cần học `java.util.concurrent` |

#### 2.3 Thread Priority

- Giá trị: 1 → 10 (mặc định: 5)
- Cách set: `thread.setPriority(int)`
- ⚠️ **Lưu ý**: Thread priority cao được ưu tiên, nhưng thread priority thấp có thể không bao giờ chạy

#### 2.4 Vòng đời Thread

```
NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → RUNNABLE → TERMINATED
```

| Trạng thái | Ý nghĩa | Phương thức kích hoạt |
|---|---|---|
| **NEW** | Vừa tạo, chưa gọi `start()` | `new Thread()` |
| **RUNNABLE** | Đang chạy hoặc sẵn sàng chạy | `start()` |
| **BLOCKED** | Chờ monitor lock (synchronized) | Tranh chấp lock |
| **WAITING** | Chờ vô thời hạn | `Object.wait()`, `Thread.join()`, `LockSupport.park()` |
| **TIMED_WAITING** | Chờ có giới hạn thời gian | `Thread.sleep(ms)`, `wait(timeout)`, `join(timeout)` |
| **TERMINATED** | Đã kết thúc `run()` hoặc exception | Hết luồng |

#### 2.5 Deadlock

**Khái niệm**: Các thread chờ nhau mãi mãi vì mỗi thread giữ một lock và chờ lock của thread khác.

**Ví dụ:**
```java
Object lockA = new Object();
Object lockB = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lockA) {
        System.out.println("T1 giữ lockA, chờ lockB...");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lockB) { 
            System.out.println("T1 vào lockB"); 
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lockB) {
        System.out.println("T2 giữ lockB, chờ lockA...");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lockA) { 
            System.out.println("T2 vào lockA"); 
        }
    }
});

t1.start(); t2.start();
// → Chương trình treo vĩnh viễn (Deadlock)
```

#### 2.6 Timer & TimerTask

```java
Timer timer = new Timer();
timer.scheduleAtFixedRate(new TimerTask() {
    public void run() { 
        System.out.println("Chạy mỗi 2s"); 
    }
}, 0, 2000);  // delay 0ms, period 2000ms
```

---

### 3. Lập trình mạng

#### 3.1 Socket

**Định nghĩa**: Socket (ổ cắm mạng) là điểm cuối giao tiếp (endpoint) giữa 2 tiến trình trên mạng.
- **Xác định bởi**: IP Address + Port Number

**Vai trò trong Java**:
```
TCP  → java.net.Socket / java.net.ServerSocket
UDP  → java.net.DatagramSocket / java.net.DatagramPacket
```

**Nguyên lý hoạt động**:
1. Server bind vào port & lắng nghe
2. Client tạo socket kết nối đến IP:Port
3. Server chấp nhận kết nối → sinh socket mới xử lý client
4. Hai bên giao tiếp qua InputStream / OutputStream

#### 3.2 TCP (Transmission Control Protocol)

| Đặc điểm | Chi tiết |
|---|---|
| **Khái niệm** | Giao thức hướng kết nối (connection-oriented) |
| **Đảm bảo** | Dữ liệu đến đúng thứ tự, không mất mát, có kiểm tra lỗi |
| **Cơ chế** | Bắt tay 3 bước (3-way handshake): SYN → SYN-ACK → ACK |
| **Điều khiển** | Luồng & chống nghẽn (Flow/Congestion Control) |
| **Gửi lại** | Gửi lại gói tin nếu mất hoặc lỗi checksum |
| **Sắp xếp** | Sắp xếp lại thứ tự tại đích |
| **Đóng kết nối** | 4 bước (FIN/ACK) |
| **Java API** | ServerSocket, Socket, InputStream, OutputStream |

#### 3.3 UDP (User Datagram Protocol)

| Đặc điểm | Chi tiết |
|---|---|
| **Khái niệm** | Giao thức không kết nối (connectionless) |
| **Truyền** | Dữ liệu dạng gói độc lập (datagram) |
| **Đảm bảo** | ❌ Không đảm bảo giao hàng, không sắp xếp thứ tự |
| **Đặc tính** | Nhanh & overhead thấp |
| **Cơ chế** | Không bắt tay, không duy trì trạng thái kết nối |
| **Gói tin** | Tự chứa địa chỉ đích |
| **Phù hợp** | Ứng dụng chấp nhận mất dữ liệu nhưng cần tốc độ/real-time |
| **Java API** | DatagramSocket, DatagramPacket |

#### 3.4 So sánh TCP vs UDP

| Tiêu chí | TCP | UDP |
|---|---|---|
| **Kết nối** | Hướng kết nối (handshake) | Không kết nối |
| **Độ tin cậy** | ✅ Đảm bảo giao hàng | ❌ Có thể mất/lặp/rối thứ tự |
| **Tốc độ & Overhead** | Chậm hơn (header 20-60 bytes) | Nhanh, header nhỏ (8 bytes) |
| **Thứ tự dữ liệu** | ✅ Tự sắp xếp | ❌ Không đảm bảo |
| **Ứng dụng** | Web, FTP, Email, SSH, DB | Video call, Game, DNS, VoIP |
| **Java class** | Socket, ServerSocket | DatagramSocket, DatagramPacket |
| **Khi nào dùng** | Cần chính xác & tin cậy | Cần tốc độ, chấp nhận mất gói |

---

### 4. MultiThread & Queue

#### 4.1 Khái niệm MultiThread

- **Định nghĩa**: Khả năng một tiến trình (process) tạo ra nhiều luồng (Thread) chạy đồng thời
- **Chia sẻ**: Vùng nhớ Heap chung, nhưng stack riêng
- **Lợi ích**:
  - Tận dụng đa lõi CPU → tăng throughput
  - Xử lý bất đồng bộ I/O (DB, network, file) → giảm thời gian chờ
  - Giữ UI/response mượt khi làm tác vụ nặng

#### 4.2 Queue

**Khái niệm**: Cấu trúc dữ liệu FIFO (First-In-First-Out)
- **Đa luồng**: Phải dùng `java.util.concurrent.BlockingQueue` (thread-safe)
- **Lý do**: Tách biệt Producer & Consumer, cân bằng tải, tránh race condition

| BlockingQueue | Đặc điểm | Sử dụng |
|---|---|---|
| **ArrayBlockingQueue** | Cố định kích thước, dùng array, lock-based | Khi biết trước size |
| **LinkedBlockingQueue** | Kích thước tùy chọn, throughput cao | Mặc định cho mục đích chung |
| **SynchronousQueue** | Không lưu trữ, put() chờ take() | Handoff trực tiếp |
| **PriorityBlockingQueue** | Không giới hạn, sắp xếp theo priority | Yêu cầu ưu tiên |
| **ConcurrentLinkedQueue** | Non-blocking, lock-free (CAS) | Read-heavy workload |

**Cách dùng**:
- Thêm: `put()` (block nếu đầy), `offer()` (trả false nếu đầy)
- Lấy: `take()` (block nếu rỗng), `poll()` (trả null nếu rỗng)
- Tự động đồng bộ hóa nội bộ

#### 4.3 Thread Pool

**Khái niệm**: Tập hợp các thread được khởi tạo sẵn, quản lý bởi `ExecutorService`
- Tái sử dụng thread → giảm overhead CPU/memory

**Ưu điểm**:
- Kiểm soát số lượng thread tối đa
- Queue task tự động khi pool bận
- Tích hợp Future/CompletableFuture để lấy kết quả/hủy task
- Giảm GC pressure

**Nhược điểm**:
- `Executors.newFixedThreadPool()` dùng LinkedBlockingQueue không giới hạn → OutOfMemoryError
- Pool quá nhỏ → task chờ lâu; pool quá lớn → context switch nhiều
- Dễ deadlock nếu task chờ kết quả từ task khác cùng pool

#### 4.4 Đồng bộ vs Bất đồng bộ

| Loại | Định nghĩa | Đặc điểm |
|---|---|---|
| **Synchronous (Đồng bộ)** | Thread gọi tác vụ → chờ kết quả mới tiếp tục | Blocking, dễ debug, giảm thông lượng |
| **Asynchronous (Bất đồng bộ)** | Thread gửi tác vụ → tiếp tục chạy, nhận kết quả sau | Non-blocking, tối ưu I/O, phức tạp hơn |

#### 4.5 Vấn đề khi có nhiều Thread

1. **Race Condition**: Nhiều thread cùng đọc/ghi biến chung → kết quả không xác định
   - Giải pháp: Atomic, synchronized, thread-local

2. **Deadlock**: ≥2 thread chờ lock của nhau mãi
   - Giải pháp: Khóa theo thứ tự cố định, tryLock(timeout), giảm scope lock

3. **Memory Visibility**: Thread A sửa biến, Thread B không thấy
   - Giải pháp: synchronized, volatile, Atomic

4. **Over-synchronization**: Lock quá rộng → thread chờ nhiều
   - Giải pháp: ConcurrentHashMap, StampedLock, immutable objects

5. **Thread Leakage**: Tạo thread không shutdown → tài nguyên cạn kiệt
   - Giải pháp: Luôn dùng ExecutorService + shutdown(), try-with-resources

**Hướng dẫn**: Học về `synchronize`, `lock`, `unlock`, `wait`, `notify` → phần này phức tạp, cần nhiều thực hành


**synchronized**: 
- trên khối trong phương thức thường: chỉ 1 thread chạy trên phương thức đó
- trên phương thức (thường):chỉ 1 thread trên phương thức đó
- trên khối trong phương thức static: chỉ 1 thread chạy trên object đó
- trên phương thức (static): chỉ 1 thread trên object đó

---

### 5. Logger

#### 5.1 Mục đích & vai trò

- Theo dõi luồng xử lý nghiệp vụ, biết hệ thống chạy đúng/sai
- Khi lỗi xảy ra → log là "hộp đen" để xác định nguyên nhân nhanh
- Ghi hành động người dùng (login, xóa, thay đổi) → kiểm tra, điều tra
- Ghi lại thời gian xử lý, cảnh báo chậm, v.v.

#### 5.2 Cách tạo & sử dụng Log

- Tự tạo hoặc dùng thư viện hỗ trợ (Log4j, SLF4J, Logback, v.v.)
- Viết chương trình & ghi log

---

### 6. Bài tập thực hành (Advance)

#### 6a. Ghi random số vào file real-time
```
Yêu cầu:
- Chạy real-time, ghi các số nguyên random ra file output.txt
- Dừng chương trình khi gõ lệnh "stop"
```

#### 6b. In số random theo chu kỳ
```
Yêu cầu:
- Chạy real-time, cứ n giây in 1 số nguyên random ra màn hình
- Dừng chương trình sau n phút
```

#### 6c. Producer & Consumer Pattern
```
Yêu cầu:
- 1 message queue với size giới hạn
- Producer thread: tạo message định kỳ, đưa vào queue
  (nếu queue đầy → đợi cho tới khi có chỗ trống)
- Consumer thread: lấy 1 message, in ra màn hình
  (nếu queue rỗng → đợi cho tới khi có message)
```

#### 6d. Gửi nhận dữ liệu qua Socket
```
Yêu cầu:
- Viết 2 module: Machine A (client) & Machine B (server)
- A gửi liên tục chuỗi random → B nhận & in ra màn hình
- Xử lý exception (rút dây mạng, v.v.)
- Ghi log file nếu lỗi
- Đưa tham số vào file config:
  • IP, Port
  • Connection timeout, Send timeout, Receive timeout
- Mở rộng: Viết chương trình chat 2 chiều
```

---

## Yêu cầu chung

### Nguyên tắc học tập

**Thực hiện theo trình tự** - Không bỏ qua phần nào  
**Học để hiểu** - Không chỉ học vẹt  
**Tự tìm hiểu** - Nghiên cứu thêm kiến thức  

### Cách làm việc với từng phần mới

1. Học khái niệm chắc chắn
2. Đưa ra ví dụ minh họa
3. So sánh giữa các phương pháp
4. Thực hành ngay

### Yêu cầu code

- Comment đầy đủ, gọn gàng
- Làm thành tài liệu chi tiết
- Có ví dụ cụ thể

### Tự học thêm (ngoài chương trình)

- Các khái niệm & kỹ thuật khác
- Quy trình phát triển phần mềm (RUP, UML)
- Web Service

### Hết mỗi phần → Gửi Review

**Nội dung**: Tài liệu + Bài tập (Basic & Advance)

---


