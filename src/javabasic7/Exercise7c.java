package javabasic7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Bai tap 7c: Sap xep mang voi Quick Sort
// Yeu cau:
// - Doc tu file input.txt
// - Cac so cach nhau bang dau cach hoac xuong dong
// - Sap xep bang Quick Sort

public class Exercise7c {
    
    public static void main(String[] args) {
        System.out.println("==== BAI TAP 7C: SAP XEP MANG VOI QUICK SORT ====\n");
        
        String inputFile = "src/javabasic7/input.txt";
        String outputFile = "src/javabasic7/output.txt";
        
        try {
            // Doc du lieu tu file
            int[] arr = readFromFile(inputFile);
            
            if (arr == null || arr.length == 0) {
                System.out.println("File trong hoac khong co du lieu!");
                return;
            }
            
            System.out.println("Du lieu doc tu file: " + inputFile);
            System.out.println("So phan tu: " + arr.length);
            System.out.print("Du lieu ban dau: ");
            printArray(arr);
            System.out.println();
            
            // Sap xep bang Quick Sort
            System.out.println("Dang sap xep bang Quick Sort...");
            long startTime = System.currentTimeMillis();
            quickSort(arr, 0, arr.length - 1);
            long endTime = System.currentTimeMillis();
            
            System.out.println("Sap xep thanh cong! (Thoi gian: " + (endTime - startTime) + "ms)");
            System.out.print("Du lieu sau sap xep: ");
            printArray(arr);
            System.out.println();
            
            // Ghi ket qua ra file
            writeToFile(outputFile, arr);
            System.out.println("Ket qua da ghi vao file: " + outputFile);
            
        } catch (IOException e) {
            System.out.println("LOI khi doc file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Doc du lieu tu file
    static int[] readFromFile(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Tach theo dau cach
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        try {
                            numbers.add(Integer.parseInt(token));
                        } catch (NumberFormatException e) {
                            System.out.println("Canh bao: Khong the parse '" + token + "', bo qua");
                        }
                    }
                }
            }
        }
        
        // Chuyen sang mang int
        int[] arr = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            arr[i] = numbers.get(i);
        }
        
        return arr;
    }
    
    // Ghi ket qua ra file
    static void writeToFile(String filename, int[] arr) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Ghi thong tin
            writer.println("=== KET QUA SAP XEP QUICK SORT ===");
            writer.println("Tong so phan tu: " + arr.length);
            writer.println("Du lieu sau sap xep:");
            
            // Ghi tung so
            for (int i = 0; i < arr.length; i++) {
                if (i > 0 && i % 10 == 0) {
                    writer.println();
                } else if (i > 0) {
                    writer.print(" ");
                }
                writer.print(arr[i]);
            }
            writer.println();
            
            // Ghi thong ke
            writer.println("\nThong ke:");
            writer.println("Gia tri nho nhat: " + arr[0]);
            writer.println("Gia tri lon nhat: " + arr[arr.length - 1]);
        }
    }
    
    // Quick Sort - sap xep tang dan
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);  // Sap xep ben trai
            quickSort(arr, pi + 1, high); // Sap xep ben phai
        }
    }
    
    // Phan vung: tim vi tri chot (pivot)
    static int partition(int[] arr, int low, int high) {
        // Chon phan tu cuoi cung lam pivot
        int pivot = arr[high];
        
        // i la chi so cua phan tu nho hon pivot
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // Doi cho
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Dat pivot vao vi tri dung
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // In mang
    static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
