package javabasic4;

// Class dai dien cho Employee trong database
public class Employee {
    private int empId;
    private String name;
    private String job;
    private double salary;
    private String hireDate;
    
    public Employee(int empId, String name, String job, double salary, String hireDate) {
        this.empId = empId;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.hireDate = hireDate;
    }
    
    // Constructor khong co ID (dung cho Insert, database tu tao ID)
    public Employee(String name, String job, double salary, String hireDate) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.hireDate = hireDate;
    }
    
    // Getter va Setter
    public int getEmpId() {
        return empId;
    }
    
    public void setEmpId(int empId) {
        this.empId = empId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getJob() {
        return job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", hireDate='" + hireDate + '\'' +
                '}';
    }
}
