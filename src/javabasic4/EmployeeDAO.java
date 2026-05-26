package javabasic4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO = Data Access Object
// CRUD (Create, Read, Update, Delete) voi bang Employee
public class EmployeeDAO {
    
    // SELECT - Lay tat ca nhan vien
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT EMPNO, ENAME, JOB, SAL, HIREDATE FROM EMP";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(getEmployeeFromQuery(rs));
            }

            System.out.println("Lay du lieu thanh cong, tong: " + employees.size() + " nhan vien");
            
        } catch (SQLException e) {
            System.out.println("Loi khi lay tat ca nhan vien: " + e.getMessage());
        }
        
        return employees;
    }

    private static Employee getEmployeeFromQuery(ResultSet rs) throws SQLException {

            int empId = rs.getInt("EMPNO");
            String name = rs.getString("ENAME");
            String job = rs.getString("JOB");
            double salary = rs.getDouble("SAL");
            String hireDate = rs.getString("HIREDATE");

            return new Employee(empId, name, job, salary, hireDate);

    }
    // SELECT - Lay nhan vien theo ID
    public static Employee getEmployeeById(int empId) {
        String sql = "SELECT EMPNO, ENAME, JOB, SAL, HIREDATE FROM EMP WHERE EMPNO = ?";
        Employee employee = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                employee = getEmployeeFromQuery(rs);
                System.out.println("Tim thay nhan vien: " + employee);
            } else {
                System.out.println("Khong tim thay nhan vien co ID: " + empId);
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.out.println("Loi khi tim nhan vien theo ID: " + e.getMessage());
        }
        
        return employee;
    }
    
    // SELECT - Lay nhan vien theo Salary lon hon
    public static List<Employee> getEmployeesBySalary(double minSalary) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT EMPNO, ENAME, JOB, SAL, HIREDATE FROM EMP WHERE SAL > ? ORDER BY SAL DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minSalary);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                employees.add(getEmployeeFromQuery(rs));
            }
            
            System.out.println("Tim thay " + employees.size() + " nhan vien co luong > " + minSalary);
            rs.close();
            
        } catch (SQLException e) {
            System.out.println("Loi khi tim nhan vien theo luong: " + e.getMessage());
        }
        
        return employees;
    }
    
    // INSERT - Them nhan vien moi
    // Neu tao sequence cho EMPNO thi se tu tang
    public static boolean insertEmployee(Employee employee) {
        String sql = "INSERT INTO EMP (EMPNO, ENAME, JOB, SAL, HIREDATE) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Lay EMPNO lon nhat + 1, hoac co the dung Sequence cua Oracle
            int newEmpNo = getMaxEmployeeId() + 1;
            
            pstmt.setInt(1, newEmpNo);
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getJob());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.setString(5, employee.getHireDate());
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Them nhan vien thanh cong! ID moi: " + newEmpNo);
                return true;
            } else {
                System.out.println("Khong the them nhan vien");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi them nhan vien: " + e.getMessage());
            return false;
        }
    }
    
    // UPDATE - Cap nhat thong tin nhan vien (sua luong)
    public static boolean updateEmployeeSalary(int empId, double newSalary) {
        String sql = "UPDATE EMP SET SAL = ? WHERE EMPNO = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, empId);
            
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Cap nhat luong cho nhan vien " + empId + " thanh cong!");
                return true;
            } else {
                System.out.println("Khong tim thay nhan vien de cap nhat");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi cap nhat luong: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Xoa nhan vien theo ID
    public static boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM EMP WHERE EMPNO = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Xoa nhan vien " + empId + " thanh cong!");
                return true;
            } else {
                System.out.println("Khong tim thay nhan vien de xoa");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi xoa nhan vien: " + e.getMessage());
            return false;
        }
    }
    
    // Hỗ trợ: Lấy ID nhân viên lớn nhất
    private static int getMaxEmployeeId() {
        String sql = "SELECT MAX(EMPNO) as maxId FROM EMP";
        int maxId = 7999; // Default neu bang trong
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                maxId = rs.getInt("maxId");
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi lay max employee ID: " + e.getMessage());
        }
        
        return maxId;
    }
    
    // Hỗ trợ: In danh sach nhan vien
    public static void printEmployeeList(List<Employee> employees) {
        System.out.println("\n========== DANH SACH NHAN VIEN ==========");
        if (employees.isEmpty()) {
            System.out.println("Khong co nhan vien nao");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
        System.out.println("=========================================\n");
    }
    
}
