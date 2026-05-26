package Thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// VI DU TONG HOP: Producer & Consumer Pattern
// Giải quyết: Race Condition, Thread Leakage
// Dung: BlockingQueue (thread-safe) + ExecutorService

public class ProducerConsumer {
    
    static class Producer implements Runnable {
        private BlockingQueue<Integer> queue;
        private int id;
        
        public Producer(int id, BlockingQueue<Integer> queue) {
            this.id = id;
            this.queue = queue;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    int value = id * 100 + i;
                    queue.put(value);  // Block neu queue day
                    System.out.println("Producer " + id + " tao: " + value);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("Producer " + id + " interrupted!");
            }
        }
    }
    
    static class Consumer implements Runnable {
        private BlockingQueue<Integer> queue;
        private int id;
        
        public Consumer(int id, BlockingQueue<Integer> queue) {
            this.id = id;
            this.queue = queue;
        }
        
        @Override
        public void run() {
            try {
                while (true) {
                    Integer value = queue.take();  // Block neu queue rong
                    System.out.println("Consumer " + id + " nhan: " + value);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer " + id + " interrupted!");
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== PRODUCER & CONSUMER PATTERN ===\n");
        
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);  // Size = 5
        java.util.concurrent.ExecutorService executor = 
            java.util.concurrent.Executors.newFixedThreadPool(5);
        
        // Tao 2 Producer
        executor.submit(new Producer(1, queue));
        executor.submit(new Producer(2, queue));
        
        // Tao 2 Consumer
        executor.submit(new Consumer(1, queue));
        executor.submit(new Consumer(2, queue));
        
        Thread.sleep(5000);
        
        // Shutdown an toan
        executor.shutdown();
        if (!executor.awaitTermination(3, java.util.concurrent.TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        
        System.out.println("\nNhan xet:");
        System.out.println("- BlockingQueue: Giai quyet race condition (thread-safe)");
        System.out.println("- ExecutorService: Giai quyet thread leakage (auto shutdown)");
        System.out.println("- Producer/Consumer: Pattern de thuc hanh multithreading");
    }
}
