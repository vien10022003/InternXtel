package Thread;

// VAN DE 5: THREAD LEAKAGE
// Tao thread khong shutdown -> tai nguyen can kiet (memory, file descriptor)
// Khi phat trien, co the tao 1000 thread va khong dong -> Java Heap Full

public class ThreadLeakage {
    
    // CACH SAI: Tao thread khong shutdown
    static class ThreadLeakProblem {
        private Thread worker;
        
        public void startWorker() {
            worker = new Thread(() -> {
                while (true) {  // Vong lap vo tan
                    try {
                        System.out.println("Worker is running...");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Worker interrupted!");
                        break;
                    }
                }
            });
            worker.start();
            // Sai: Khong co cach dung thread nay
        }
        
        public void stop() {
            if (worker != null) {
                worker.interrupt();  // Phai goi interrupt() de dung
            }
        }
    }
    
    // CACH DUNG 1: Dung ExecutorService (tot nhat)
    static class ThreadPoolSolution {
        private java.util.concurrent.ExecutorService executor;
        
        public void init() {
            executor = java.util.concurrent.Executors.newFixedThreadPool(5);
            System.out.println("Created ThreadPool with 5 threads");
        }
        
        public void submitTask(Runnable task) {
            executor.submit(task);
        }
        
        public void shutdown() {
            executor.shutdown();  // Khong tao thread moi
            try {
                if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    executor.shutdownNow();  // Force stop neu van con thread
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            System.out.println("ThreadPool shutdown!");
        }
    }
    
    // CACH DUNG 2: Dung try-with-resources (ExecutorService implements AutoCloseable)
    static class AutoCloseableSolution {
        public void runWithAutoClose() throws Exception {
            try (java.util.concurrent.ExecutorService executor = 
                    java.util.concurrent.Executors.newFixedThreadPool(3)) {
                
                for (int i = 0; i < 5; i++) {
                    executor.submit(() -> {
                        System.out.println("Task: " + Thread.currentThread().getName());
                        try { Thread.sleep(500); } catch (InterruptedException e) {}
                    });
                }
                
                executor.shutdown();
                executor.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
            }  // Auto-close va shutdown
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== THREAD LEAKAGE ===\n");
        
        // Test 1: Thread Leakage (cach sai)
        System.out.println("Test 1: Thread Leakage (tao 5 threads, khong shutdown)");
        ThreadLeakProblem leaky = new ThreadLeakProblem();
        
        for (int i = 0; i < 5; i++) {
            leaky.startWorker();  // LUAT: Tao 5 threads, khong co cach dung
            // Chi co 1 reference (bi ghi de), con 4 threads o trong background!
        }
        
        Thread.sleep(1000);
        System.out.println("Nhan xet: 5 threads dang chay o background, khong the dung ca!");
        System.out.println("-> Thread Leakage! Neu lam dieu nay hang trieu lan, memory se day!\n");
        
        // Test 2: ExecutorService (cach dung)
        System.out.println("Test 2: Dung ExecutorService (cach dung)");
        ThreadPoolSolution pool = new ThreadPoolSolution();
        pool.init();
        
        for (int i = 0; i < 10; i++) {
            pool.submitTask(() -> {
                System.out.println("Task: " + Thread.currentThread().getName());
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        }
        
        Thread.sleep(500);
        pool.shutdown();
        System.out.println("Nhan xet: ThreadPool tu dong quan ly thread lifetime!\n");
        
        // Test 3: Try-with-resources (tot nhat)
        System.out.println("Test 3: Dung try-with-resources");
        AutoCloseableSolution autoClose = new AutoCloseableSolution();
        autoClose.runWithAutoClose();
        System.out.println("Nhan xet: Auto-close dam bao shutdown, khong lo thread leak!\n");
        
        // So sanh cac cach
        System.out.println("So sanh:");
        System.out.println("1. Manual Thread: De xay ra thread leak, phai tu goi interrupt()");
        System.out.println("2. ExecutorService: Tot hon, quan ly pool cua threads");
        System.out.println("3. Try-with-resources: Tot nhat, auto-close dam bao cleanup!");
    }
}
