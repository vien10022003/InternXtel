package Thread;

// VAN DE 1: RACE CONDITION
// Khi nhieu thread cung doc/ghi bien chung -> ket qua khong xac dinh
// Vi du: 2 thread cung tang bien counter, ket qua < 2000 (chu khong = 2000)

public class RaceCondition {
    
    // CACH SAI: Khong synchronized
    static class UnsafeCounter {
        private int count = 0;  // Bien chung
        
        public void increment() {
            count++;  // 3 buoc: doc, cong, ghi
            // Thread A: doc (100) -> cong -> ghi (101)
            // Thread B: doc (100) -> cong -> ghi (101)  ← Dung du lieu cu!
            // Ket qua: 101 (chu khong phai 102)
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // CACH DUNG: Dung synchronized
    static class SafeCounter {
        private int count = 0;
        
        public synchronized void increment() {
            count++;  // Chi 1 thread duoc vao tai 1 thoi diem
        }
        
        public synchronized int getCount() {
            return count;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== RACE CONDITION ===\n");
        
        // Test 1: Khong synchronized (sai)
        System.out.println("Test 1: Khong dung synchronized");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        });
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("Ket qua (sai): " + unsafeCounter.getCount());
        System.out.println("Ky vong: 2000");
        System.out.println("Nhan xet: Ket qua < 2000 vi race condition!\n");
        
        // Test 2: Dung synchronized (dung)
        System.out.println("Test 2: Dung synchronized");
        SafeCounter safeCounter = new SafeCounter();
        
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                safeCounter.increment();
            }
        });
        
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                safeCounter.increment();
            }
        });
        
        t3.start(); t4.start();
        t3.join(); t4.join();
        
        System.out.println("Ket qua (dung): " + safeCounter.getCount());
        System.out.println("Ky vong: 2000");
        System.out.println("Nhan xet: Ket qua = 2000, da khoa properly!\n");
        
        // Test 3: Dung Atomic (nhanh hon synchronized)
        System.out.println("Test 3: Dung Atomic");
        java.util.concurrent.atomic.AtomicInteger atomicCounter = 
            new java.util.concurrent.atomic.AtomicInteger(0);
        
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.incrementAndGet();
            }
        });
        
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.incrementAndGet();
            }
        });
        
        t5.start(); t6.start();
        t5.join(); t6.join();
        
        System.out.println("Ket qua (AtomicInteger): " + atomicCounter.get());
    }
}
