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
- Download và cài đặt Eclipse, MyEclipse, NetBeans, hoặc **IntelliJ IDEA**

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

#### 1.4 Các câu lệnh điều kiện & vòng lặp
- `if`, `else if`, `else`
- `for`, `while`, `do-while`
- `switch-case`

#### 1.5 Giải thuật làm việc với mảng

| Thuật toán | Ý tưởng | Độ phức tạp | Ghi chú |
|---|---|---|---|
| **Bubble Sort** | Đổi chỗ phần tử liền kề | O(n²) | For lồng nhau, so sánh liền kề nhỏ hơn thì đổi chỗ |
| **Selection Sort** | Chọn phần tử nhỏ nhất | O(n²) | Tìm bé nhất cho xuống đầu, lặp tới hết |
| **Quick Sort** | Chọn chốt, chia 2 bên | O(n log n) | 2 hàm: phân vùng & triển khai đệ quy |
| **Merge Sort** | Chia đôi & gộp lại có thứ tự | O(n log n) | Đệ quy, sau đó gộp 2 mảng đã sort |

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
//   cái nào nhỏ hơn thì cho trước vào mảng temp
// }
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

---

### 2. Lập trình hướng đối tượng

#### 2.1 Khái niệm cơ bản

| Khái niệm | Giải thích |
|---|---|
| **Class** | Khuôn mẫu mô tả cấu trúc & hành vi chung của một nhóm đối tượng |
| **Đối tượng** | Thực thể cụ thể của class, chiếm bộ nhớ |

#### 2.2 Bốn đặc điểm OOP

1. **Đóng gói (Encapsulation)**
   - Giấu dữ liệu bên trong
   - Truy cập có kiểm soát via access modifier: `public`, `private`, `default`, `protected`
   - Sử dụng getter/setter

2. **Kế thừa (Inheritance)**
   - Tái sử dụng code từ class cha
   - Được thể hiện qua `extends` hoặc `implements`
   - Đặc biệt quan trọng: `abstract class` và `interface`

3. **Đa hình (Polymorphism)**
   - Nhiều hình dạng hành vi khác nhau tùy ngữ cảnh
   - **Method Overriding**: viết lại phương thức của class cha (runtime)
   - **Method Overloading**: cùng tên nhưng khác tham số (compile-time)

4. **Trừu tượng (Abstraction)**
   - Ẩn chi tiết, chỉ công khai chức năng cần thiết
   - Sử dụng `interface` và `abstract class`

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
} catch (IOException e) {
    // Xử lý lỗi cụ thể
} catch (Exception e) {
    // Xử lý lỗi chung (luôn đặt cuối cùng)
} finally {
    // Luôn chạy, dù có lỗi hay không
    // → dùng để giải phóng tài nguyên
}
```

#### 3.3 Throw & Throws

- **`throw`**: Ném trực tiếp một đối tượng exception tại runtime
- **`throws`**: Khai báo phương thức có thể ném exception, chuyển trách nhiệm xử lý cho hàm gọi

---

### 4. Lập trình xử lý với Database

#### 4.1 Cài đặt
- Cài đặt Oracle (10G hoặc 11G)

#### 4.2 Các kiến thức cần học
- Các câu lệnh cơ bản SQL
- Table, Store Procedure, Function, Trigger
- Sequence, Partition, Job, Schedule

#### 4.3 Bước tiếp theo
- Kết nối với Oracle từ Java
- Viết chương trình test

---

### 5. File Config

- Sử dụng Java 14 hoặc các phiên bản khác
- Ở mức cơ bản: tự viết & đọc file text cấu hình
- Lợi ích: truyền tham số mà không cần build lại code

---

### 6. Đóng gói chương trình

- Đóng gói chương trình thành file executable
- Chạy trên Windows, Linux, v.v.

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

- Tự nghiên cứu các thư viện nâng cao trong Java
- So sánh ưu nhược điểm các cách xử lý I/O

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


