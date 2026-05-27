package javaAdvance6;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Bài 6b: In số random theo chu kỳ
 * Yêu cầu:
 * - Chạy real-time, cứ n giây in 1 số nguyên random ra màn hình
 * - Dừng chương trình sau n phút
 */
public class Exercise6b {
    public static void main(String[] args) {
        System.out.println("=== Bài 6b: In số random theo chu kỳ ===\n");

        // Thông số cấu hình
        int intervalSeconds = 2;      // Khoảng thời gian giữa mỗi lần in (giây)
        int durationMinutes = 1;      // Thời gian chạy tổng cộng (phút)
        long durationMillis = durationMinutes * 30 * 1000;  // Chuyển sang ms

        System.out.println("Cấu hình:");
        System.out.println("- In 1 số random mỗi " + intervalSeconds + " giây");
        System.out.println("- Chạy trong " + durationMinutes + " phút (" + durationMillis + "ms)");
        System.out.println("- Nhấn Ctrl+C để dừng sớm\n");

        Timer timer = new Timer();
        Random random = new Random();
        final int[] count = {0};  // Đếm số lần in

        // Schedule task chạy định kỳ
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count[0]++;
                int randomNum = random.nextInt(1000);  // Random từ 0-999
                String timestamp = LocalDateTime.now().toString();
                System.out.println("[" + count[0] + "] [" + timestamp + "] Random: " + randomNum);
            }
        }, 0, intervalSeconds * 1000);  // Bắt đầu ngay, lặp mỗi n*1000 ms

        // Dừng timer sau n phút
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();  // Hủy timer
                System.out.println("\n✓ Hết " + durationMinutes + " phút. Dừng in số.");
                System.out.println("Tổng cộng đã in: " + count[0] + " số.");
                System.out.println("=== Bài 6b hoàn thành ===\n");
                System.exit(0);  // Thoát chương trình
            }
        }, durationMillis);
    }
}
