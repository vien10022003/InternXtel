package testStream;

import java.io.*;

// Vi du 8: RandomAccessFile
// Dung cho: Doc/ghi o bat ky vi tri trong file (khong phai tua dau)
// Toc do: Nhanh neu chi sua mot phan file

public class RandomAccessFile {
    
    public static void main(String[] args) throws IOException {
        // Tao file voi du lieu ban dau
        createFile("src/testStream/random.txt");
        
        // Sua du lieu o vi tri cu the
        modifyAtPosition("src/testStream/random.txt");
        
        // Doc lai file
        readFile("src/testStream/random.txt");
    }
    
    // Tao file
    static void createFile(String filename) throws IOException {
        PrintWriter pw = new PrintWriter(filename);
        pw.println("Line 1: Hello");
        pw.println("Line 2: World");
        pw.println("Line 3: Java!");
        pw.close();
        System.out.println("Tao file thanh cong!");
    }
    
    // Sua du lieu o vi tri cu the
    static void modifyAtPosition(String filename) throws IOException {
        java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw");
        try {
            // Di toi byte thu 8 (sau "Line 1: ")
            raf.seek(8);
            raf.writeBytes("MODIFIED");
            System.out.println("Sua file thanh cong!");
        } finally {
            raf.close();
        }
    }
    
    // Doc lai file
    static void readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(
            new FileReader(filename)
        );
        try {
            System.out.println("Noi dung file sau khi sua:");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } finally {
            br.close();
        }
    }
}
