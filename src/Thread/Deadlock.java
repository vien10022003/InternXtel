package Thread;

// VAN DE 2: DEADLOCK
// Khi >= 2 thread cho lock cua nhau mai mai
// Tien toa: Thread A giu lock1, cho lock2
//          Thread B giu lock2, cho lock1
// -> 2 thread kho cho nhau mau mau

public class Deadlock {
    
    static Object lock1 = new Object();
    static Object lock2 = new Object();
    
    // CACH SAI: De xay ra deadlock
    static class DeadlockThread extends Thread {
        int threadId;
        
        public DeadlockThread(int threadId) {
            this.threadId = threadId;
        }
        
        @Override
        public void run() {
            if (threadId == 1) {
                synchronized (lock1) {
                    System.out.println("Thread 1 giu lock1, cho lock2...");
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                    
                    synchronized (lock2) {
                        System.out.println("Thread 1 vao lock2");
                    }
                }
            } else {
                synchronized (lock2) {
                    System.out.println("Thread 2 giu lock2, cho lock1...");
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                    
                    synchronized (lock1) {  // <- Deadlock o day!
                        System.out.println("Thread 2 vao lock1");
                    }
                }
            }
        }
    }
    
    // CACH DUNG: Khoa theo thu tu co dinh
    static class NoDeadlockThread extends Thread {
        int threadId;
        
        public NoDeadlockThread(int threadId) {
            this.threadId = threadId;
        }
        
        @Override
        public void run() {
            // Luan lua (lock1 -> lock2) cho ca 2 thread
            synchronized (lock1) {
                System.out.println("Thread " + threadId + " giu lock1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                synchronized (lock2) {
                    System.out.println("Thread " + threadId + " vao lock2 - OK!");
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== DEADLOCK ===\n");
        
        // Test 1: Deadlock (se treo)
        System.out.println("Test 1: De xay ra Deadlock (se treo 2 second roi time out)");
        Thread t1 = new DeadlockThread(1);
        Thread t2 = new DeadlockThread(2);
        
        t1.start(); t2.start();
        
        // Cho 2 giay, neu van treo thi deadlock xay ra
        Thread.sleep(2000);
        
        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("-> DEADLOCK XAYRA! Hai thread dang treo!");
            System.out.println("Ly do: T1 giu lock1 cho lock2, T2 giu lock2 cho lock1");
            // Tat threads (khong the dung duoc, phai tat chuong trinh)
        } else {
            System.out.println("-> Khong deadlock");
        }
        
        System.out.println();
        
        // Test 2: Khong deadlock (dung cach)
        System.out.println("Test 2: Khoa theo thu tu co dinh (khong deadlock)");
        Thread t3 = new NoDeadlockThread(1);
        Thread t4 = new NoDeadlockThread(2);
        
        t3.start(); t4.start();
        t3.join(); t4.join();
        
        System.out.println("-> OK, khong deadlock!\n");
        
        // Cach khac: Dung tryLock voi timeout
        System.out.println("Test 3: Dung tryLock voi timeout (an toan hon)");
        java.util.concurrent.locks.ReentrantLock lock3 = new java.util.concurrent.locks.ReentrantLock();
        java.util.concurrent.locks.ReentrantLock lock4 = new java.util.concurrent.locks.ReentrantLock();
        
        Thread t5 = new Thread(() -> {
            try {
                if (lock3.tryLock(1, java.util.concurrent.TimeUnit.SECONDS)) {
                    System.out.println("Thread 1 giu lock3");
                    Thread.sleep(100);
                    
                    if (lock4.tryLock(1, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.out.println("Thread 1 vao lock4 - OK!");
                        lock4.unlock();
                    } else {
                        System.out.println("Thread 1 khong the lay lock4 (timeout)");
                    }
                    lock3.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        Thread t6 = new Thread(() -> {
            try {
                if (lock4.tryLock(1, java.util.concurrent.TimeUnit.SECONDS)) {
                    System.out.println("Thread 2 giu lock4");
                    Thread.sleep(100);
                    
                    if (lock3.tryLock(1, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.out.println("Thread 2 vao lock3 - OK!");
                        lock3.unlock();
                    } else {
                        System.out.println("Thread 2 khong the lay lock3 (timeout)");
                    }
                    lock4.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t5.start(); t6.start();
        t5.join(); t6.join();
        
        System.out.println("-> tryLock khong bi deadlock, chi timeout thoi!");
    }
}
