package javabasic7;

// Entity class dai dien cho Student trong database
public class Student {
    private int studentId;
    private String name;
    private String gender;
    private String hometown;
    private int age;
    
    public Student(int studentId, String name, String gender, String hometown, int age) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.hometown = hometown;
        this.age = age;
    }
    
    // Constructor khong co ID (dung cho Insert, database tu tao ID)
    public Student(String name, String gender, String hometown, int age) {
        this.name = name;
        this.gender = gender;
        this.hometown = hometown;
        this.age = age;
    }
    
    // Getter va Setter
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getHometown() {
        return hometown;
    }
    
    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", hometown='" + hometown + '\'' +
                ", age=" + age +
                '}';
    }
}
