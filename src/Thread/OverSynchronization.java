package Thread;

// VAN DE 4: OVER-SYNCHRONIZATION
// Lock qua rong -> thread phai cho nhau kha nhieu -> toc do cham

public class OverSynchronization {
    
    // CACH SAI: Khoa toan bo object
    static class SlowCounter {
        private int count = 0;
        private long computeTime = 0;
        
        // Nhieu hanh dong duoc khoa cung luc -> thread phai cho nhau
        public synchronized void increment() {
            count++;
        }
        
        public synchronized void doHeavyComputation() {
            // Tac vu nang (bat dau tao blocking toan bo)
            try { Thread.sleep(10); } catch (InterruptedException e) {}
            computeTime++;
        }
        
        public synchronized int getCount() {
            return count;
        }
    }
    
    // CACH DUNG 1: Khoa tung bien rieng
    static class FastCounter {
        private int count = 0;
        private long computeTime = 0;
        private Object countLock = new Object();
        private Object computeLock = new Object();
        
        public void increment() {
            synchronized (countLock) {  // Chi khoa count
                count++;
            }
        }
        
        public void doHeavyComputation() {
            synchronized (computeLock) {  // Chi khoa computeTime
                try { Thread.sleep(10); } catch (InterruptedException e) {}
                computeTime++;
            }
        }
        
        public int getCount() {
            synchronized (countLock) {
                return count;
            }
        }
    }
    
    // CACH DUNG 2: Dung ConcurrentHashMap (tuc dung fine-grained locks)
    static class VeryFastCounter {
        private int count = 0;
        
        public void increment() {
            // Khong khoa - dung AtomicInteger tron hon
            // (trong thuc te se dung atomic)
            count++;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== OVER-SYNCHRONIZATION ===\n");
        
        // Test 1: Lock qua rong (cham)
        System.out.println("Test 1: Khoa toan bo (cham)");
        SlowCounter slow = new SlowCounter();
        
        long startTime = System.currentTimeMillis();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                slow.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                slow.doHeavyComputation();
            }
        });
        
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                slow.increment();
            }
        });
        
        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();
        
        long slowTime = System.currentTimeMillis() - startTime;
        System.out.println("Thoi gian (over-sync): " + slowTime + "ms");
        System.out.println("Count: " + slow.getCount());
        System.out.println("Nhan xet: T1 va T3 phai cho T2 lam heavy computation xong\n");
        
        // Test 2: Lock tung bien (nhanh hon)
        System.out.println("Test 2: Khoa tung bien rieng (nhanh hon)");
        FastCounter fast = new FastCounter();
        
        startTime = System.currentTimeMillis();
        
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                fast.increment();
            }
        });
        
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                fast.doHeavyComputation();
            }
        });
        
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                fast.increment();
            }
        });
        
        t4.start(); t5.start(); t6.start();
        t4.join(); t5.join(); t6.join();
        
        long fastTime = System.currentTimeMillis() - startTime;
        System.out.println("Thoi gian (fine-grained locks): " + fastTime + "ms");
        System.out.println("Count: " + fast.getCount());
        System.out.println("Toc do toi uu: " + (slowTime > 0 ? (slowTime / fastTime) : "N/A") + "x");
        System.out.println("Nhan xet: T1 va T3 chap nhan khoa chi khi update count, khong can cho heavy computation\n");
        
        // So sanh
        System.out.println("So sanh:");
        System.out.println("- Over-sync (1 lock): Toan bo thread phai cho nhau");
        System.out.println("- Fine-grained (nhieu locks): Chi lock gia tri can thiep, khac locks khong can cho");
        System.out.println("- ConcurrentHashMap: Dung internal segmentation de cai thien concurrency");
    }
}
