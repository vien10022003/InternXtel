package testStream;

import java.io.*;

// Vi du 6: PrintWriter
// Dung cho: Ghi text theo kieu printf/println (tien loi nhat)
// Toc do: Trung binh (co buffer tu dong)

public class PrintWriterTest {

    public PrintWriterTest(String filename) {

    }

    public static void main(String[] args) throws IOException {
        // Ghi file
        writeFile("src/testStream/data6.txt");
        
        // Doc file
        readFile("src/testStream/data6.txt");
    }
    
    // Ghi file bang PrintWriter
    static void writeFile(String filename) throws IOException {
        java.io.PrintWriter pw = new java.io.PrintWriter(
            new FileWriter(filename)
        );
        try {
            pw.println("Sinh vien: Nguyen Van A");
            pw.println("Tuoi: 20");
            pw.println("Diem: " + 8.5);
            pw.printf("Xep loai: %s\n", "Gioi");
            pw.flush();
            System.out.println("Ghi file (PrintWriter) thanh cong!");
        } finally {
            pw.close();
        }
    }
    
    // Doc file
    static void readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(
            new FileReader(filename)
        );
        try {
            System.out.println("Doc file:");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } finally {
            br.close();
        }
    }
}
