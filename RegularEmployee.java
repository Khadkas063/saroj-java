package ems;

/**
 * RegularEmployee class, inherits from Employee and could hold extra attributes if needed.
 */
public class RegularEmployee extends Employee {
    private String department;

    public RegularEmployee(int id, String name, String position, double salary, String department) {
        super(id, name, position, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Department:%s", department);
    }
}
