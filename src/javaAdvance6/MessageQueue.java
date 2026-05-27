package javaAdvance6;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Lớp MessageQueue: Quản lý queue tin nhắn thread-safe
 * - Sử dụng BlockingQueue (tự động đồng bộ hóa)
 * - Producer: gọi put() để thêm tin nhắn (chờ nếu queue đầy)
 * - Consumer: gọi take() để lấy tin nhắn (chờ nếu queue rỗng)
 */
public class MessageQueue {
    private final BlockingQueue<Message> queue;
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * Thêm tin nhắn vào queue
     * Nếu queue đầy → thread sẽ chờ cho tới khi có chỗ trống
     */
    public void put(Message message) throws InterruptedException {
        queue.put(message);
    }

    /**
     * Lấy tin nhắn từ queue (loại bỏ khỏi queue)
     * Nếu queue rỗng → thread sẽ chờ cho tới khi có message
     */
    public Message take() throws InterruptedException {
        return queue.take();
    }

    /**
     * Kiểm tra số lượng tin nhắn hiện tại
     */
    public int size() {
        return queue.size();
    }

    /**
     * Kiểm tra queue có rỗng không
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Kiểm tra queue có đầy không
     */
    public boolean isFull() {
        return queue.size() >= capacity;
    }

    /**
     * Lấy dung lượng tối đa
     */
    public int getCapacity() {
        return capacity;
    }
}
