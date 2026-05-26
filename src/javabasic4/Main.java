package javabasic4;

import java.util.List;
import java.util.Scanner;

// Main class - Test cac tac vu CRUD voi Database
public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("==== CHUONG TRINH QUAN LY NHAN VIEN ====");
        System.out.println("Ket noi den Database Oracle...\n");
        
        // Test ket noi
        if (DatabaseConnection.getConnection() == null) {
            System.out.println("Khong the ket noi Database, ket thuc!");
            return;
        }
        runApp();
    }
    static void runApp() {
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Chon thao tac (1-7): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    selectAllEmployees();
                    break;
                case "2":
                    selectEmployeeById();
                    break;
                case "3":
                    selectEmployeeBySalary();
                    break;
                case "4":
                    insertNewEmployee();
                    break;
                case "5":
                    updateEmployeeSalary();
                    break;
                case "6":
                    deleteEmployee();
                    break;
                case "7":
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
        System.out.println("1. Xem tat ca nhan vien (SELECT ALL)");
        System.out.println("2. Xem nhan vien theo ID (SELECT BY ID)");
        System.out.println("3. Xem nhan vien theo Luong (SELECT BY SALARY)");
        System.out.println("4. Them nhan vien moi (INSERT)");
        System.out.println("5. Cap nhat luong (UPDATE)");
        System.out.println("6. Xoa nhan vien (DELETE)");
        System.out.println("7. Thoat");
        System.out.println("-----------------");
    }
    
    // 1. SELECT - Xem tat ca nhan vien
    static void selectAllEmployees() {
        System.out.println("\n>> Lay tat ca nhan vien tu Database...");
        List<Employee> employees = EmployeeDAO.getAllEmployees();
        EmployeeDAO.printEmployeeList(employees);
    }
    
    // 2. SELECT - Xem nhan vien theo ID
    static void selectEmployeeById() {
        System.out.println("\n>> Nhap ID nhan vien can tim:");
        System.out.print("ID: ");
        
        try {
            int empId = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = EmployeeDAO.getEmployeeById(empId);
            
            if (emp != null) {
                System.out.println("Tim thay: " + emp);
            } else {
                System.out.println("Khong co nhan vien voi ID: " + empId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Loi: ID phai la so!");
        }
    }
    
    // 3. SELECT - Xem nhan vien theo Luong
    static void selectEmployeeBySalary() {
        System.out.println("\n>> Nhap muc luong toi thieu (hien thi nhung nhan vien co luong cao hon):");
        System.out.print("Luong toi thieu: ");
        
        try {
            double minSalary = Double.parseDouble(scanner.nextLine().trim());
            List<Employee> employees = EmployeeDAO.getEmployeesBySalary(minSalary);
            EmployeeDAO.printEmployeeList(employees);
        } catch (NumberFormatException e) {
            System.out.println("Loi: Luong phai la so!");
        }
    }
    
    // 4. INSERT - Them nhan vien moi
    static void insertNewEmployee() {
        System.out.println("\n>> Them nhan vien moi");
        
        try {
            System.out.print("Ten nhan vien: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Loi: Ten khong duoc trong");
                return;
            }
            
            System.out.print("Chuc vu (Job): ");
            String job = scanner.nextLine().trim();
            
            if (job.isEmpty()) {
                System.out.println("Loi: Chuc vu khong duoc trong");
                return;
            }
            
            System.out.print("Luong: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());
            
            if (salary <= 0) {
                System.out.println("Loi: Luong phai lon hon 0");
                return;
            }
            
            System.out.print("Ngay vao lam (YYYY-MM-DD): ");
            String hireDate = scanner.nextLine().trim();
            
            Employee emp = new Employee(name, job, salary, hireDate);
            boolean success = EmployeeDAO.insertEmployee(emp);
            
            if (success) {
                System.out.println("Them nhan vien thanh cong!");
            } else {
                System.out.println("That bai khi them nhan vien!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai dinh dang so!");
        }
    }
    
    // 5. UPDATE - Cap nhat luong nhan vien
    static void updateEmployeeSalary() {
        System.out.println("\n>> Cap nhat luong nhan vien");
        
        try {
            System.out.print("ID nhan vien: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Luong moi: ");
            double newSalary = Double.parseDouble(scanner.nextLine().trim());
            
            if (newSalary <= 0) {
                System.out.println("Loi: Luong phai lon hon 0");
                return;
            }
            
            boolean success = EmployeeDAO.updateEmployeeSalary(empId, newSalary);
            
            if (success) {
                System.out.println("Cap nhat luong thanh cong!");
            } else {
                System.out.println("That bai khi cap nhat luong!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai dinh dang so!");
        }
    }
    
    // 6. DELETE - Xoa nhan vien
    static void deleteEmployee() {
        System.out.println("\n>> Xoa nhan vien");
        
        try {
            System.out.print("ID nhan vien can xoa: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Ban co chac chan muon xoa khong? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y")) {
                boolean success = EmployeeDAO.deleteEmployee(empId);
                
                if (success) {
                    System.out.println("Xoa nhan vien thanh cong!");
                } else {
                    System.out.println("That bai khi xoa nhan vien!");
                }
            } else {
                System.out.println("Da huy thao tac xoa!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Loi: ID phai la so!");
        }
    }
    
}
