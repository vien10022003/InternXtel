package javabasic7;

import java.util.List;
import java.util.Scanner;
import javabasic4.DatabaseConnection;

// Bai tap 7d: Quan ly sinh vien voi Database
// Yeu cau:
// - Nhap n sinh vien: ten, gioi tinh, que quan, tuoi
// - Insert vao DB (ban phim -> Enter -> DB)
// - Ten khong duoc trung
// - ID tu tang

public class Exercise7d {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("==== BAI TAP 7D: QUAN LY SINH VIEN VOI DATABASE ====\n");
        
        System.out.println("Ket noi den Database Oracle...\n");
        
        // Test ket noi
        if (DatabaseConnection.getConnection() == null) {
            System.out.println("Khong the ket noi Database, ket thuc!");
            return;
        }
        
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Chon thao tac (1-5): ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    addNewStudent();
                    break;
                case "2":
                    viewAllStudents();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    running = false;
                    System.out.println("Thoat chuong trinh...");
                    DatabaseConnection.closeConnection();
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long nhap lai!");
            }
            
            System.out.println();
        }
    }
    
    static void showMenu() {
        System.out.println("\n------ MENU ------");
        System.out.println("1. Them sinh vien moi");
        System.out.println("2. Xem tat ca sinh vien");
        System.out.println("3. Cap nhat thong tin sinh vien");
        System.out.println("4. Xoa sinh vien");
        System.out.println("5. Thoat");
        System.out.println("-----------------");
    }
    
    // 1. Them sinh vien moi
    static void addNewStudent() {
        System.out.println("\n>> Them sinh vien moi");
        
        try {
            System.out.print("Ten sinh vien: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Loi: Ten khong duoc trong");
                return;
            }
            
            // Kiem tra ten da ton tai
            if (StudentDAO.isNameExists(name)) {
                System.out.println("Loi: Ten '" + name + "' da ton tai trong database!");
                return;
            }
            
            System.out.print("Gioi tinh (Nam/Nu): ");
            String gender = scanner.nextLine().trim();
            
            if (gender.isEmpty()) {
                System.out.println("Loi: Gioi tinh khong duoc trong");
                return;
            }
            
            System.out.print("Que quan: ");
            String hometown = scanner.nextLine().trim();
            
            if (hometown.isEmpty()) {
                System.out.println("Loi: Que quan khong duoc trong");
                return;
            }
            
            System.out.print("Tuoi: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            
            if (age <= 0 || age > 100) {
                System.out.println("Loi: Tuoi phai lon hon 0 va nho hon 100");
                return;
            }
            
            Student student = new Student(name, gender, hometown, age);
            boolean success = StudentDAO.insertStudent(student);
            
            if (success) {
                System.out.println("Them sinh vien thanh cong!");
            } else {
                System.out.println("That bai khi them sinh vien!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai dinh dang!");
        }
    }
    
    // 2. Xem tat ca sinh vien
    static void viewAllStudents() {
        System.out.println("\n>> Xem tat ca sinh vien");
        List<Student> students = StudentDAO.getAllStudents();
        StudentDAO.printStudentList(students);
    }
    
    // 3. Cap nhat sinh vien
    static void updateStudent() {
        System.out.println("\n>> Cap nhat thong tin sinh vien");
        
        try {
            System.out.print("Nhap ID sinh vien: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Ten sinh vien moi: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Loi: Ten khong duoc trong");
                return;
            }
            
            System.out.print("Gioi tinh (Nam/Nu): ");
            String gender = scanner.nextLine().trim();
            
            if (gender.isEmpty()) {
                System.out.println("Loi: Gioi tinh khong duoc trong");
                return;
            }
            
            System.out.print("Que quan: ");
            String hometown = scanner.nextLine().trim();
            
            if (hometown.isEmpty()) {
                System.out.println("Loi: Que quan khong duoc trong");
                return;
            }
            
            System.out.print("Tuoi: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            
            if (age <= 0 || age > 100) {
                System.out.println("Loi: Tuoi phai lon hon 0 va nho hon 100");
                return;
            }
            
            Student newData = new Student(name, gender, hometown, age);
            boolean success = StudentDAO.updateStudent(studentId, newData);
            
            if (success) {
                System.out.println("Cap nhat thanh cong!");
            } else {
                System.out.println("That bai khi cap nhat!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai dinh dang!");
        }
    }
    
    // 4. Xoa sinh vien
    static void deleteStudent() {
        System.out.println("\n>> Xoa sinh vien");
        
        try {
            System.out.print("ID sinh vien can xoa: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Ban co chac chan muon xoa khong? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y")) {
                boolean success = StudentDAO.deleteStudent(studentId);
                
                if (success) {
                    System.out.println("Xoa sinh vien thanh cong!");
                } else {
                    System.out.println("That bai khi xoa sinh vien!");
                }
            } else {
                System.out.println("Da huy thao tac xoa!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: ID phai la so!");
        }
    }
}
