package testStream;

import java.io.*;

// Vi du 2: BufferedInputStream & BufferedOutputStream
// Dung cho: Doc/ghi file to hon de toi uu hoa toc do
// Toc do: Nhanh hon (co buffer 8192 byte mac dinh)

public class BufferedStreamTest {
    
    public static void main(String[] args) throws IOException {
        // Ghi du lieu
        writeFile("src/testStream/bufferedStream.txt", "Hello from Chào!");
        
        // Doc du lieu
        readFile("src/testStream/bufferedStream.txt");
    }
    
    // Ghi file bang BufferedOutputStream
    static void writeFile(String filename, String data) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try {
            byte[] bytes = data.getBytes();
            bos.write(bytes);
            bos.flush();  // Thuc day du lieu tu buffer vao file
            System.out.println("Ghi file (buffered) thanh cong!");
        } finally {
            bos.close();  // Dong BufferedOutputStream se tu dong dong FileOutputStream
        }
    }
    
    // Doc file bang BufferedInputStream
    static void readFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try {
            byte[] bytes = new byte[1024];
            int bytesRead = bis.read(bytes);
            String data = new String(bytes, 0, bytesRead);
            System.out.println("Doc file (buffered): " + data);
        } finally {
            bis.close();
        }
    }
}
