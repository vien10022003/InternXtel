package javabasic4;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Doc file config, tranh hard-code trong code
public class ConfigReader {
    private static final Properties props = new Properties();
    
    static {
        try {
            // Tim file config trong thu muc hien tai
            FileInputStream fis = new FileInputStream("src/javabasic4/config.properties");
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Loi khi doc file config: " + e.getMessage());
        }
    }
    
    // Lay property theo key, neu khong co tra ve default
    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
    
    // Lay property theo key
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
    
}
