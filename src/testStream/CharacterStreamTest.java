package testStream;

import java.io.*;

// Vi du 4: FileReader & FileWriter
// Dung cho: Xu ly text (Unicode, ky tu, dong)
// Toc do: Nhanh cho text, chi xu ly ky tu

public class CharacterStreamTest {
    
    public static void main(String[] args) throws IOException {
        // Ghi file
        writeFile("src/testStream/characterStream.txt", "Xin chao, day la FileWriter!\nDong 2");
        
        // Doc file
        readFile("src/testStream/characterStream.txt");
    }
    
    // Ghi file bang FileWriter
    static void writeFile(String filename, String data) throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(data);
            System.out.println("Ghi file (text) thanh cong!");
        }
    }
    
    // Doc file bang FileReader (khong co buffer, doc 1 ky tu)
    static void readFile(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename)) {
            char[] chars = new char[100];
            int charsRead = fr.read(chars);
            String data = new String(chars, 0, charsRead);
            System.out.println("Doc file (text):\n" + data);
        }
    }
}
