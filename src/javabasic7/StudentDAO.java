package javabasic7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javabasic4.DatabaseConnection;

// DAO = Data Access Object
// Thuc hien cac thao tac CRUD voi bang STUDENT
public class StudentDAO {
    
    // SELECT - Lay tat ca sinh vien
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT STUDENT_ID, NAME, GENDER, HOMETOWN, AGE FROM STUDENT";
        
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int studentId = rs.getInt("STUDENT_ID");
                String name = rs.getString("NAME");
                String gender = rs.getString("GENDER");
                String hometown = rs.getString("HOMETOWN");
                int age = rs.getInt("AGE");
                
                Student student = new Student(studentId, name, gender, hometown, age);
                students.add(student);
            }
            
            System.out.println("Lay du lieu thanh cong, tong: " + students.size() + " sinh vien");
            
        } catch (SQLException e) {
            System.out.println("Loi khi lay tat ca sinh vien: " + e.getMessage());
        }
        
        return students;
    }
    
    // SELECT - Kiem tra ten da ton tai chua
    public static boolean isNameExists(String name) {
        String sql = "SELECT COUNT(*) as cnt FROM STUDENT WHERE NAME = ?";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("cnt");
                    return count > 0;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi kiem tra ten: " + e.getMessage());
        }
        
        return false;
    }
    
    // INSERT - Them sinh vien moi
    public static boolean insertStudent(Student student) {
        // Kiem tra ten da ton tai chua
        if (isNameExists(student.getName())) {
            System.out.println("Loi: Ten '" + student.getName() + "' da ton tai trong database!");
            return false;
        }
        
        String sql = "INSERT INTO STUDENT (NAME, GENDER, HOMETOWN, AGE) VALUES (?, ?, ?, ?)";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender());
            pstmt.setString(3, student.getHometown());
            pstmt.setInt(4, student.getAge());
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Them sinh vien thanh cong!");
                return true;
            } else {
                System.out.println("Khong the them sinh vien");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi them sinh vien: " + e.getMessage());
            return false;
        }
    }
    
    // UPDATE - Cap nhat thong tin sinh vien
    public static boolean updateStudent(int studentId, Student newData) {
        // Kiem tra neu thay doi ten thi ten moi khong duoc trung
        String currentName = getStudentName(studentId);
        if (!currentName.equals(newData.getName()) && isNameExists(newData.getName())) {
            System.out.println("Loi: Ten '" + newData.getName() + "' da ton tai trong database!");
            return false;
        }
        
        String sql = "UPDATE STUDENT SET NAME = ?, GENDER = ?, HOMETOWN = ?, AGE = ? WHERE STUDENT_ID = ?";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newData.getName());
            pstmt.setString(2, newData.getGender());
            pstmt.setString(3, newData.getHometown());
            pstmt.setInt(4, newData.getAge());
            pstmt.setInt(5, studentId);
            
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Cap nhat sinh vien " + studentId + " thanh cong!");
                return true;
            } else {
                System.out.println("Khong tim thay sinh vien de cap nhat");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi cap nhat sinh vien: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Xoa sinh vien
    public static boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM STUDENT WHERE STUDENT_ID = ?";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Xoa sinh vien " + studentId + " thanh cong!");
                return true;
            } else {
                System.out.println("Khong tim thay sinh vien de xoa");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi xoa sinh vien: " + e.getMessage());
            return false;
        }
    }
    

    
    // Ho tro: Lay ten sinh vien theo ID
    private static String getStudentName(int studentId) {
        String sql = "SELECT NAME FROM STUDENT WHERE STUDENT_ID = ?";
        String name = "";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("NAME");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Loi khi lay ten sinh vien: " + e.getMessage());
        }
        
        return name;
    }
    
    // Ho tro: In danh sach sinh vien
    public static void printStudentList(List<Student> students) {
        System.out.println("\n========== DANH SACH SINH VIEN ==========");
        if (students.isEmpty()) {
            System.out.println("Khong co sinh vien nao");
        } else {
            System.out.printf("%-8s | %-20s | %-8s | %-20s | %-5s%n",
                    "ID", "TEN", "GIOI TINH", "QUE QUAN", "TUOI");
            System.out.println("-".repeat(70));
            
            for (Student student : students) {
                System.out.printf("%-8d | %-20s | %-8s | %-20s | %-5d%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getGender(),
                        student.getHometown(),
                        student.getAge());
            }
        }
        System.out.println("=========================================\n");
    }
}
