package javaAdvance6;

import java.io.*;
import java.util.Properties;

/**
 * Lớp SocketConfig: Đọc cấu hình từ file socket_config.properties
 */
public class SocketConfig {
    private static final Properties props = new Properties();
    private static final String CONFIG_FILE = "src/javaAdvance6/socket_config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            props.load(fis);
            fis.close();
            System.out.println("✓ Đã tải cấu hình từ " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("⚠ Cảnh báo: Không thể đọc file cấu hình: " + e.getMessage());
            System.out.println("   Sẽ sử dụng giá trị mặc định");
        }
    }

    /**
     * Lấy giá trị string từ config
     */
    public static String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    /**
     * Lấy giá trị int từ config
     */
    public static int getInt(String key, int defaultValue) {
        String value = props.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Giá trị không phải số cho key: " + key);
            }
        }
        return defaultValue;
    }

    /**
     * Lấy giá trị long từ config
     */
    public static long getLong(String key, long defaultValue) {
        String value = props.getProperty(key);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Giá trị không phải số cho key: " + key);
            }
        }
        return defaultValue;
    }

    // Getter tiện lợi
    public static String getServerHost() {
        return getString("server.host", "localhost");
    }

    public static int getServerPort() {
        return getInt("server.port", 9090);
    }

    public static String getClientHost() {
        return getString("client.host", "localhost");
    }

    public static int getClientPort() {
        return getInt("client.port", 9090);
    }

    public static int getConnectionTimeout() {
        return getInt("connection.timeout", 5000);
    }

    public static int getSendTimeout() {
        return getInt("send.timeout", 5000);
    }

    public static int getReceiveTimeout() {
        return getInt("receive.timeout", 5000);
    }

    public static long getMessageInterval() {
        return getLong("message.interval.ms", 1000);
    }

    public static int getMessageCount() {
        return getInt("message.count", 20);
    }
}
