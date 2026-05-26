package testStream;

import java.io.*;

// Vi du 1: FileInputStream & FileOutputStream
// Dung cho: file nho, du lieu nhi phan (anh, video, PDF)
// Toc do: Cham (khong co buffer)

public class FileStreamTest {
    
    public static void main(String[] args) throws IOException {
        // Ghi du lieu
        writeFile("src/testStream/fileStream.txt", "Hello World!");
        
        // Doc du lieu
        readFile("src/testStream/fileStream.txt");
    }
    
    // Ghi file bang FileOutputStream
    static void writeFile(String filename, String data) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            // Chuyen String -> byte[]
            byte[] bytes = data.getBytes();
            fos.write(bytes);
            System.out.println("Ghi file thanh cong!");
        } finally {
            if (fos != null) fos.close();
        }
    }
    
    // Doc file bang FileInputStream
    static void readFile(String filename) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            byte[] bytes = new byte[100];  // Buffer nho
            int bytesRead = fis.read(bytes);  // Doc toi da 100 byte
            String data = new String(bytes, 0, bytesRead);
            System.out.println("Doc file: " + data);
        } finally {
            if (fis != null) fis.close();
        }
    }
}
