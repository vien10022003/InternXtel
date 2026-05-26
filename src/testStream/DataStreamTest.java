package testStream;

import java.io.*;

// Vi du 3: DataInputStream & DataOutputStream
// Dung cho: Ghi/doc cac kieu du lieu (int, double, String, boolean)
// Toc do: Trung binh (co xu ly kieu du lieu)

public class DataStreamTest {
    
    public static void main(String[] args) throws IOException {
        // Ghi du lieu co kieu
        writeData("src/testStream/dataStream.dat");
        
        // Doc du lieu co kieu
        readData("src/testStream/dataStream.dat");
    }
    
    // Ghi cac kieu du lieu vao file
    static void writeData(String filename) throws IOException {
        DataOutputStream dos = new DataOutputStream(
            new FileOutputStream(filename)
        );
        try {
            dos.writeInt(42);                    // int
            dos.writeDouble(3.14);               // double
            dos.writeBoolean(true);              // boolean
            dos.writeUTF("Hello DataStream");    // String (voi do dai)
            System.out.println("Ghi data thanh cong!");
        } finally {
            dos.close();
        }
    }
    
    // Doc cac kieu du lieu tu file
    static void readData(String filename) throws IOException {
        DataInputStream dis = new DataInputStream(
            new FileInputStream(filename)
        );
        try {
            int intValue = dis.readInt();
            double doubleValue = dis.readDouble();
            boolean boolValue = dis.readBoolean();
            String strValue = dis.readUTF();
            
            System.out.println("Doc data:");
            System.out.println("  Int: " + intValue);
            System.out.println("  Double: " + doubleValue);
            System.out.println("  Boolean: " + boolValue);
            System.out.println("  String: " + strValue);
        } finally {
            dis.close();
        }
    }
}
