package javaAdvance6;

import java.util.Random;

/**
 * Bài 6c: Producer & Consumer Pattern
 * Yêu cầu:
 * - 1 message queue với size giới hạn
 * - Producer thread: tạo message định kỳ, đưa vào queue
 *   (nếu queue đầy → đợi cho tới khi có chỗ trống)
 * - Consumer thread: lấy 1 message, in ra màn hình
 *   (nếu queue rỗng → đợi cho tới khi có message)
 */
public class Exercise6c {
    public static void main(String[] args) {
        System.out.println("=== Bài 6c: Producer & Consumer Pattern ===\n");

        // Cấu hình
        int queueCapacity = 5;          // Dung lượng queue
        int producerCount = 2;          // Số lượng producer
        int consumerCount = 2;          // Số lượng consumer
        long durationMillis = 30000;    // Thời gian chạy (30 giây)

        System.out.println("Cấu hình:");
        System.out.println("- Queue capacity: " + queueCapacity);
        System.out.println("- Producer threads: " + producerCount);
        System.out.println("- Consumer threads: " + consumerCount);
        System.out.println("- Duration: " + (durationMillis / 1000) + " giây\n");

        // Tạo message queue
        MessageQueue queue = new MessageQueue(queueCapacity);

        // Tạo producer threads
        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            producers[i] = new Thread(new Producer(queue, i + 1, durationMillis));
            producers[i].setName("Producer-" + (i + 1));
            producers[i].start();
        }

        // Tạo consumer threads
        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Thread(new Consumer(queue, i + 1, durationMillis));
            consumers[i].setName("Consumer-" + (i + 1));
            consumers[i].start();
        }

        // Chờ tất cả thread hoàn thành
        try {
            for (Thread producer : producers) {
                producer.join();
            }
            for (Thread consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread bị gián đoạn: " + e.getMessage());
        }

        System.out.println("\n=== Bài 6c hoàn thành ===\n");
    }

    /**
     * Producer: Tạo message và đưa vào queue
     */
    static class Producer implements Runnable {
        private final MessageQueue queue;
        private final int producerId;
        private final long durationMillis;
        private int messageId = 0;

        public Producer(MessageQueue queue, int producerId, long durationMillis) {
            this.queue = queue;
            this.producerId = producerId;
            this.durationMillis = durationMillis;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            Random random = new Random();

            while (System.currentTimeMillis() - startTime < durationMillis) {
                try {
                    messageId++;
                    String content = "Producer-" + producerId + " tạo message #" + messageId 
                            + " (Random: " + random.nextInt(100) + ")";
                    Message message = new Message(producerId * 1000 + messageId, content);

                    System.out.println("📤 [" + Thread.currentThread().getName() + "] Đưa vào queue: " + message);

                    queue.put(message);  // Chờ nếu queue đầy

                    System.out.println("   Queue size: " + queue.size() + "/" + queue.getCapacity());

                    // Tạo message mỗi 1-3 giây
                    Thread.sleep(1000 + random.nextInt(2000));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("❌ " + Thread.currentThread().getName() + " bị gián đoạn!");
                    break;
                }
            }

            System.out.println("✓ " + Thread.currentThread().getName() + " dừng (tổng " + messageId + " messages)");
        }
    }

    /**
     * Consumer: Lấy message từ queue và in ra
     */
    static class Consumer implements Runnable {
        private final MessageQueue queue;
        private final int consumerId;
        private final long durationMillis;
        private int consumeCount = 0;

        public Consumer(MessageQueue queue, int consumerId, long durationMillis) {
            this.queue = queue;
            this.consumerId = consumerId;
            this.durationMillis = durationMillis;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < durationMillis) {
                try {
                    Message message = queue.take();  // Chờ nếu queue rỗng
                    consumeCount++;

                    System.out.println("📥 [" + Thread.currentThread().getName() + "] Lấy từ queue: " + message);
                    System.out.println("   Queue size: " + queue.size() + "/" + queue.getCapacity());

                    // Xử lý message (giả lập công việc)
                    Thread.sleep(500 + (long)(Math.random() * 1500));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("❌ " + Thread.currentThread().getName() + " bị gián đoạn!");
                    break;
                }
            }

            System.out.println("✓ " + Thread.currentThread().getName() + " dừng (tổng tiêu thụ " + consumeCount + " messages)");
        }
    }
}
