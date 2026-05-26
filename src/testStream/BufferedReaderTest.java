package testStream;

import java.io.*;

// Vi du 5: BufferedReader & BufferedWriter
// Dung cho: Doc/ghi text theo dong (line by line)
// Toc do: Nhanh hon (co buffer)

public class BufferedReaderTest {
    
    public static void main(String[] args) throws IOException {
        // Ghi file
        writeFile("src/testStream/bufferedReader.txt");
        
        // Doc file
        readFile("src/testStream/bufferedReader.txt");
    }
    
    // Ghi file bang BufferedWriter
    static void writeFile(String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(
            new FileWriter(filename)
        );
        try {
            bw.write("Dòng 1: Hello");
            bw.newLine();  // Xuat xuong dong
            bw.write("Dong 2: BufferedWriter");
            bw.newLine();
            bw.write("Dong 3: Efficient");
            bw.flush();
            System.out.println("Ghi file (line by line) thanh cong!");
        } finally {
            bw.close();
        }
    }
    
    // Doc file bang BufferedReader (doc theo dong)
    static void readFile(String filename) throws IOException {
        java.io.BufferedReader br = new java.io.BufferedReader(
            new FileReader(filename)
        );
        try {
            System.out.println("Doc file (line by line):");
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(lineNum + ": " + line);
                lineNum++;
            }
        } finally {
            br.close();
        }
    }
}
