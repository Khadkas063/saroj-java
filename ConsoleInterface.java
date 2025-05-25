package ems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private List<Employee> employees;
    private Scanner scanner;

    public ConsoleInterface() {
        employees = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Employee Management System - Text Interface");
        boolean exit = false;

        while (!exit) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    removeEmployee();
                    break;
                case "3":
                    viewEmployees();
                    break;
                case "4":
                    sortEmployees();
                    break;
                case "5":
                    searchEmployee();
                    break;
                case "0":
                    exit = true;
                    System.out.println("Exiting Text Interface. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("1 - Add Employee");
        System.out.println("2 - Remove Employee by ID");
        System.out.println("3 - View All Employees");
        System.out.println("4 - Sort Employees by ID");
        System.out.println("5 - Search Employee by ID");
        System.out.println("0 - Exit");
        System.out.print("Enter choice: ");
    }

    private void addEmployee() {
        try {
            System.out.print("Enter Employee ID (integer): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Position: ");
            String position = scanner.nextLine().trim();
            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();

            RegularEmployee emp = new RegularEmployee(id, name, position, salary, department);
            employees.add(emp);
            System.out.println("Employee added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input number format. Please try again.");
        }
    }

    private void removeEmployee() {
        try {
            System.out.print("Enter Employee ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Employee emp = binarySearch(id);
            if (emp != null) {
                employees.remove(emp);
                System.out.println("Employee removed successfully.");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input number format. Please try again.");
        }
    }

    private void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("\nEmployee List:");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    private void sortEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to sort.");
            return;
        }
        Collections.sort(employees);
        System.out.println("Employees sorted by ID.");
    }

    private void searchEmployee() {
        if (employees.isEmpty()) {
            System.out.println("Employee list is empty. Please add employees first.");
            return;
        }
        try {
            System.out.print("Enter Employee ID to search: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Collections.sort(employees);
            Employee emp = binarySearch(id);
            if (emp != null) {
                System.out.println("Employee found: " + emp);
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input number format. Please try again.");
        }
    }

    private Employee binarySearch(int id) {
        int left = 0;
        int right = employees.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            Employee midEmp = employees.get(mid);
            if (midEmp.getId() == id) {
                return midEmp;
            } else if (midEmp.getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
