package javabasic7;

import java.util.Scanner;

// Bai tap 7a: Nhap so voi so lan gioi han
// Yeu cau:
// - Nhap 1 so cho toi khi nhap dung -> thong bao thanh cong
// - Toi da 5 lan nhap
// - Sai -> dung chuong trinh & hien thong bao loi

public class Exercise7a {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("==== BAI TAP 7A: NHAP SO VOI SO LAN GIOI HAN ====\n");
        
        // Tham so
        int maxAttempts = 5;
        int correctNumber = 42; // So can doan
        
        System.out.println("Chung ta co 1 so can doan, ban co " + maxAttempts + " lan de doan dung.");
        System.out.println("So can doan nam trong khoang 1-100");
        System.out.println();
        
        // Vong lap nhap
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Lan nhap thu " + attempt + "/" + maxAttempts + " - Nhap so: ");
            
            try {
                int userInput = Integer.parseInt(scanner.nextLine().trim());
                
                // Kiem tra so
                if (userInput == correctNumber) {
                    System.out.println("\n>>> CHUC MUNG! Ban doan dung so: " + correctNumber);
                    System.out.println(">>> Thanh cong sau " + attempt + " lan nhap!");
                    return; // Thoat chuong trinh
                } else if (userInput < correctNumber) {
                    System.out.println(">>> Ghi chu: So ban nhap nho hon so can doan");
                } else {
                    System.out.println(">>> Ghi chu: So ban nhap lon hon so can doan");
                }
                
                // Thong bao con bao nhieu lan
                int lanCon = maxAttempts - attempt;
                if (lanCon > 0) {
                    System.out.println(">>> Con lai " + lanCon + " lan\n");
                } else {
                    System.out.println();
                }
                
            } catch (NumberFormatException e) {
                System.out.println(">>> LOI: Ban phai nhap 1 so nguyen! Chi tro lai.");
                attempt--; // Khong tinh vao so lan nhap
                System.out.println();
            }
        }
        
        // Het so lan nhap
        System.out.println(">>> HET SO LAN NHAP!");
        System.out.println(">>> So dung la: " + correctNumber);
        System.out.println(">>> THAT BAI! Chuong trinh se dung!");
    }
}
