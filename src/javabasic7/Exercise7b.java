package javabasic7;

import java.util.Scanner;

// Bai tap 7b: Tinh tien dien
// Yeu cau:
// - Nhap so dien da dung trong thang
// - Bang gia:
//   * 100 so dau: 1000d/so
//   * Tu 100-150 so: 1500d/so
//   * Tu 150 so tro len: 2000d/so

public class Exercise7b {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("==== BAI TAP 7B: TINH TIEN DIEN ====\n");
        
        System.out.println("BANG GIA DIEN:");
        System.out.println("- 100 so dau: 1,000 dong/so");
        System.out.println("- So 100-150: 1,500 dong/so");
        System.out.println("- So 150 tro len: 2,000 dong/so");
        System.out.println();
        
        try {
            System.out.print("Nhap so dien da su dung (kWh): ");
            int soDien = Integer.parseInt(scanner.nextLine().trim());
            
            if (soDien < 0) {
                System.out.println("LOI: So dien khong the am!");
                return;
            }
            
            long tienDien = tinhTienDien(soDien);
            
            System.out.println("\n--- KET QUA TINH TIEN ---");
            System.out.println("So dien: " + soDien + " kWh");
            System.out.println("Tien dien: " + String.format("%,d", tienDien) + " dong");
            System.out.println("Chi tiet: " + getChiTiet(soDien));
            
        } catch (NumberFormatException e) {
            System.out.println("LOI: Ban phai nhap 1 so nguyen!");
        }
    }
    
    // Phuong thuc tinh tien dien
    static long tinhTienDien(int soDien) {
        long tien = 0;
        
        // 100 so dau: 1000d/so
        if (soDien <= 100) {
            tien = soDien * 1000L;
        }
        // Tu 100-150: 1500d/so
        else if (soDien <= 150) {
            tien = 100 * 1000L + (soDien - 100) * 1500L;
        }
        // Tu 150 tro len: 2000d/so
        else {
            tien = 100 * 1000L + 50 * 1500L + (soDien - 150) * 2000L;
        }
        
        return tien;
    }
    
    // Chi tiet tien dien
    static String getChiTiet(int soDien) {
        StringBuilder sb = new StringBuilder();
        
        if (soDien <= 100) {
            sb.append(soDien).append(" so x 1,000 = ").append(String.format("%,d", soDien * 1000L));
        } else if (soDien <= 150) {
            int soLoai1 = 100;
            int soLoai2 = soDien - 100;
            sb.append(soLoai1).append(" so x 1,000 = ").append(String.format("%,d", soLoai1 * 1000L))
              .append(", ")
              .append(soLoai2).append(" so x 1,500 = ").append(String.format("%,d", soLoai2 * 1500L));
        } else {
            int soLoai1 = 100;
            int soLoai2 = 50;
            int soLoai3 = soDien - 150;
            sb.append(soLoai1).append(" so x 1,000 = ").append(String.format("%,d", soLoai1 * 1000L))
              .append(", ")
              .append(soLoai2).append(" so x 1,500 = ").append(String.format("%,d", soLoai2 * 1500L))
              .append(", ")
              .append(soLoai3).append(" so x 2,000 = ").append(String.format("%,d", soLoai3 * 2000L));
        }
        
        return sb.toString();
    }
}
