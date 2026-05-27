package javaAdvance6;

/**
 * Lớp Message đại diện cho một tin nhắn
 * Sử dụng cho bài 6c: Producer & Consumer Pattern
 */
public class Message {
    private final int id;
    private final String content;
    private final long timestamp;

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + content + " (Time: " + new java.text.SimpleDateFormat("HH:mm:ss.SSS")
                .format(new java.util.Date(timestamp)) + ")";
    }
}
