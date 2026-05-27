package javaAdvance6;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bài 6d - Server: Nhận dữ liệu từ Client qua Socket
 * Yêu cầu:
 * - Lắng nghe trên port được cấu hình
 * - Nhận chuỗi random từ client
 * - In ra màn hình
 * - Xử lý exception (rút dây mạng, v.v.)
 * - Ghi log file nếu lỗi
 */
public class Exercise6d_Server {
    private static final String LOG_FILE = "src/javaAdvance6/server_log.txt";
    private static PrintWriter logWriter;

    public static void main(String[] args) {
        System.out.println("=== Bài 6d - Server ===\n");

        // Khởi tạo log writer
        try {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.out.println("❌ Lỗi tạo file log: " + e.getMessage());
            return;
        }

        // Lấy cấu hình
        String host = SocketConfig.getServerHost();
        int port = SocketConfig.getServerPort();
        int receiveTimeout = SocketConfig.getReceiveTimeout();

        logMessage("=== Server khởi động ===");
        logMessage("Host: " + host + ", Port: " + port);
        logMessage("Receive Timeout: " + receiveTimeout + "ms");

        System.out.println("Server lắng nghe trên " + host + ":" + port);
        System.out.println("Receive Timeout: " + receiveTimeout + "ms");
        System.out.println("Log file: " + LOG_FILE);
        System.out.println("Chờ client kết nối...\n");

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            while (true) {
                Socket clientSocket = null;

                try {
                    // Chấp nhận kết nối từ client
                    clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().getHostAddress();
                    int clientPort = clientSocket.getPort();

                    System.out.println("\n✓ Client kết nối: " + clientIP + ":" + clientPort);
                    logMessage("Client kết nối: " + clientIP + ":" + clientPort);

                    // Thiết lập timeout
                    clientSocket.setSoTimeout(receiveTimeout);

                    // Xử lý client trong thread riêng
                    Thread clientThread = new Thread(new ClientHandler(clientSocket, logWriter));
                    clientThread.setName("Client-" + clientIP);
                    clientThread.start();

                } catch (SocketException e) {
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        System.out.println("❌ Lỗi socket với client: " + e.getMessage());
                        logMessage("Lỗi socket: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("❌ Lỗi khi chấp nhận client: " + e.getMessage());
                    logMessage("Lỗi chấp nhận client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Lỗi Server Socket: " + e.getMessage());
            logMessage("Lỗi Server Socket: " + e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("\n✓ Đã đóng ServerSocket");
                    logMessage("Server đóng");
                } catch (IOException e) {
                    System.out.println("Lỗi khi đóng ServerSocket: " + e.getMessage());
                }
            }
            if (logWriter != null) {
                logWriter.close();
            }
        }
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

    /**
     * Xử lý từng client trong thread riêng
     */
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private final PrintWriter logWriter;

        public ClientHandler(Socket socket, PrintWriter logWriter) {
            this.socket = socket;
            this.logWriter = logWriter;
        }

        @Override
        public void run() {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String message;
                int messageCount = 0;

                // Nhận dữ liệu từ client cho tới khi client đóng
                while ((message = reader.readLine()) != null) {
                    messageCount++;
                    String timestamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
                    System.out.println("[" + timestamp + "] " + Thread.currentThread().getName() + ": " + message);

                    synchronized (logWriter) {
                        logWriter.println("[" + timestamp + "] Nhận từ " + Thread.currentThread().getName()
                                + ": " + message);
                        logWriter.flush();
                    }
                }

                System.out.println("✓ " + Thread.currentThread().getName() + " đóng kết nối (nhận " + messageCount + " messages)");
                synchronized (logWriter) {
                    logWriter.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date())
                            + "] Client " + Thread.currentThread().getName() + " đóng (tổng: " + messageCount + " messages)");
                    logWriter.flush();
                }

            } catch (SocketTimeoutException e) {
                System.out.println("⚠ " + Thread.currentThread().getName() + " timeout: " + e.getMessage());
                synchronized (logWriter) {
                    logWriter.println("[TIMEOUT] " + Thread.currentThread().getName() + ": " + e.getMessage());
                    logWriter.flush();
                }
            } catch (IOException e) {
                System.out.println("❌ Lỗi I/O với " + Thread.currentThread().getName() + ": " + e.getMessage());
                synchronized (logWriter) {
                    logWriter.println("[ERROR] " + Thread.currentThread().getName() + ": " + e.getMessage());
                    logWriter.flush();
                }
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi đóng socket: " + e.getMessage());
                }
            }
        }
    }
}
