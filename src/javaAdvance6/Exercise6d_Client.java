package javaAdvance6;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Bài 6d - Client: Gửi chuỗi random tới Server qua Socket
 * Yêu cầu:
 * - Kết nối tới server
 * - Gửi liên tục chuỗi random
 * - Xử lý exception (rút dây mạng, v.v.)
 * - Ghi log file nếu lỗi
 * - Tham số từ file config
 */
public class Exercise6d_Client {
    private static final String LOG_FILE = "src/javaAdvance6/client_log.txt";
    private static PrintWriter logWriter;

    public static void main(String[] args) {
        System.out.println("=== Bài 6d - Client ===\n");

        // Khởi tạo log writer
        try {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.out.println("❌ Lỗi tạo file log: " + e.getMessage());
            return;
        }

        // Lấy cấu hình
        String serverHost = SocketConfig.getClientHost();
        int serverPort = SocketConfig.getClientPort();
        int connectionTimeout = SocketConfig.getConnectionTimeout();
        int sendTimeout = SocketConfig.getSendTimeout();
        long messageInterval = SocketConfig.getMessageInterval();
        int messageCount = SocketConfig.getMessageCount();

        logMessage("=== Client khởi động ===");
        logMessage("Server: " + serverHost + ":" + serverPort);
        logMessage("Connection Timeout: " + connectionTimeout + "ms");
        logMessage("Send Timeout: " + sendTimeout + "ms");

        System.out.println("Kết nối tới server " + serverHost + ":" + serverPort);
        System.out.println("Config: interval=" + messageInterval + "ms, count=" + messageCount);
        System.out.println("Log file: " + LOG_FILE);
        System.out.println();

        Socket socket = null;
        PrintWriter writer = null;

        try {
            // Tạo socket và kết nối tới server
            socket = new Socket();
            socket.setSoTimeout(connectionTimeout);

            System.out.println("Đang kết nối...");
            socket.connect(new InetSocketAddress(serverHost, serverPort), connectionTimeout);

            // Thiết lập send timeout
            socket.setSoTimeout(sendTimeout);

            System.out.println("✓ Kết nối thành công!");
            logMessage("Kết nối thành công tới " + serverHost + ":" + serverPort);

            // Tạo PrintWriter để gửi dữ liệu
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // Thread gửi dữ liệu
            PrintWriter finalWriter = writer;
            Thread senderThread = new Thread(() -> sendMessages(finalWriter, messageCount, messageInterval));
            senderThread.start();

            // Thread nhận lệnh dừng từ bàn phím
            Thread stopThread = new Thread(Exercise6d_Client::waitForStop);
            stopThread.setDaemon(true);
            stopThread.start();

            try {
                senderThread.join();
            } catch (InterruptedException e) {
                System.out.println("❌ Thread gửi bị gián đoạn: " + e.getMessage());
                logMessage("Thread gửi bị gián đoạn: " + e.getMessage());
            }

        } catch (SocketTimeoutException e) {
            System.out.println("❌ Timeout khi kết nối: " + e.getMessage());
            logMessage("Timeout kết nối: " + e.getMessage());
        } catch (ConnectException e) {
            System.out.println("❌ Không thể kết nối tới server: " + e.getMessage());
            logMessage("Không thể kết nối: " + e.getMessage());
            System.out.println("   Đảm bảo server đang chạy trên " + serverHost + ":" + serverPort);
        } catch (IOException e) {
            System.out.println("❌ Lỗi I/O: " + e.getMessage());
            logMessage("Lỗi I/O: " + e.getMessage());
        } finally {
            // Đóng tài nguyên
            try {
                if (writer != null) writer.close();
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                System.out.println("\n✓ Đã đóng kết nối");
                logMessage("Kết nối đóng");
            } catch (IOException e) {
                System.out.println("Lỗi khi đóng socket: " + e.getMessage());
            }

            if (logWriter != null) {
                logWriter.close();
            }
        }
    }

    /**
     * Gửi messages liên tục
     */
    private static void sendMessages(PrintWriter writer, int count, long interval) {
        Random random = new Random();
        int sent = 0;

        for (int i = 0; i < count; i++) {
            try {
                String randomString = "Random#" + (i + 1) + ": " + random.nextInt(10000);
                writer.println(randomString);

                String timestamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                System.out.println("[" + timestamp + "] Gửi: " + randomString);
                logMessage("Gửi: " + randomString);

                sent++;

                if (i < count - 1) {
                    Thread.sleep(interval);
                }

            } catch (InterruptedException e) {
                System.out.println("⚠ Thread ngủ bị gián đoạn");
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n✓ Đã gửi " + sent + " messages");
        logMessage("Gửi xong " + sent + " messages");
    }

    /**
     * Chờ lệnh "stop" từ bàn phím
     */
    private static void waitForStop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(Nhập 'stop' để dừng early, hoặc chương trình sẽ tự dừng)\n");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("stop")) {
                System.out.println("\n>> Lệnh 'stop' nhận được");
                logMessage("Lệnh 'stop' từ người dùng");
                System.exit(0);
            }
        }
        scanner.close();
    }

    /**
     * Ghi log vào file
     */
    private static void logMessage(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String logLine = "[" + timestamp + "] " + message;

        if (logWriter != null) {
            logWriter.println(logLine);
            logWriter.flush();
        }
    }
}
