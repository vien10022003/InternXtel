package javaAdvance6;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

/**
 * Bài 6a: Ghi random số vào file real-time
 * Yêu cầu:
 * - Chạy real-time, ghi các số nguyên random ra file output.txt
 * - Dừng chương trình khi gõ lệnh "stop"
 */
public class Exercise6a {
    private static volatile boolean isRunning = true;  // volatile để thread nhìn thấy thay đổi
    private static final String OUTPUT_FILE = "src/javaAdvance6/output.txt";

    public static void main(String[] args) {
        System.out.println("=== Bài 6a: Ghi random số vào file real-time ===");
        System.out.println("Ghi các số random ra file output.txt");
        System.out.println("Gõ 'stop' để dừng chương trình\n");

        // Thread 1: Ghi số random vào file
        Thread writerThread = new Thread(() -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE, true))) {
                Random random = new Random();

                while (isRunning) {
                    int randomNum = random.nextInt(1000);  // Random từ 0-999
                    String timestamp = LocalDateTime.now().toString();
                    String line = "[" + timestamp + "] " + randomNum;

                    writer.println(line);
                    writer.flush();  // Ghi ngay vào file
                    System.out.println("Đã ghi: " + line);

                    try {
                        Thread.sleep(500);  // Ghi mỗi 500ms
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                System.out.println("\n✓ Đã đóng file. Chương trình dừng.");
            } catch (IOException e) {
                System.out.println("Lỗi khi ghi file: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Thread 2: Nhận lệnh từ bàn phím
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (isRunning) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.equalsIgnoreCase("stop")) {
                        System.out.println("\n>> Lệnh 'stop' nhận được. Dừng chương trình...");
                        isRunning = false;
                        break;
                    } else {
                        System.out.println("Lệnh không hợp lệ. Gõ 'stop' để dừng.");
                    }
                }
            }
            scanner.close();
        });

        // Đặt thread input thành daemon (sẽ tự dừng khi main thread dừng)
        inputThread.setDaemon(true);

        writerThread.start();
        inputThread.start();

        try {
            writerThread.join();  // Chờ thread ghi hoàn thành
        } catch (InterruptedException e) {
            System.out.println("Main thread bị gián đoạn: " + e.getMessage());
        }

        System.out.println("=== Bài 6a hoàn thành ===\n");
    }
}
