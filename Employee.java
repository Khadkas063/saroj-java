package ems;

/**
 * Base class representing an Employee.
 */
public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("ID:%d | Name:%s | Position:%s | Salary:%.2f", id, name, position, salary);
    }

    /**
     * Comparision based on Employee ID (for sorting)
     */
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }
}
