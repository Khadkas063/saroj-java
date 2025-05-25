package ems;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIInterface extends JFrame {

    private List<Employee> employees;
    private DefaultTableModel tableModel;
    private JTable employeeTable;
    private JTextField idField, nameField, positionField, salaryField, departmentField, searchField, deleteField;
    private JLabel statusLabel;

    public GUIInterface() {
        employees = new ArrayList<>();
        setTitle("Employee Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(1000, 60));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(320, getHeight()));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Add / Edit Employee",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = createTextField("ID");
        nameField = createTextField("Name");
        positionField = createTextField("Position");
        salaryField = createTextField("Salary");
        departmentField = createTextField("Department");

        addField(formPanel, gbc, 0, "Employee ID:", idField);
        addField(formPanel, gbc, 1, "Name:", nameField);
        addField(formPanel, gbc, 2, "Position:", positionField);
        addField(formPanel, gbc, 3, "Salary:", salaryField);
        addField(formPanel, gbc, 4, "Department:", departmentField);

        JButton addBtn = new JButton("Add Employee");
        JButton removeBtn = new JButton("Remove Selected");
        styleButton(addBtn, new Color(40, 167, 69));
        styleButton(removeBtn, new Color(220, 53, 69));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        String[] columns = {"ID", "Name", "Position", "Salary", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(employeeTable);
        add(tableScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));

        searchField = new JTextField(8);
        deleteField = new JTextField(8);

        JButton searchBtn = new JButton("Search by ID");
        JButton sortBtn = new JButton("Sort by ID");
        JButton deleteBtn = new JButton("Delete by ID");

        // Apply black background and white text to control buttons
        styleButton(searchBtn, Color.BLACK);
        styleButton(sortBtn, Color.black);
        styleButton(deleteBtn, Color.black);
        searchBtn.setForeground(Color.black);
        sortBtn.setForeground(Color.black);
        deleteBtn.setForeground(Color.black);

        controlPanel.add(new JLabel("Search ID:"));
        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(sortBtn);
        controlPanel.add(new JLabel("Delete ID:"));
        controlPanel.add(deleteField);
        controlPanel.add(deleteBtn);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(controlPanel);
        bottomPanel.add(statusLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addEmployee());
        removeBtn.addActionListener(e -> removeSelectedEmployee());
        searchBtn.addActionListener(e -> searchEmployee());
        sortBtn.addActionListener(e -> sortEmployees());
        deleteBtn.addActionListener(e -> deleteById(deleteField.getText()));
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JTextField createTextField(String tooltip) {
        JTextField tf = new JTextField(15);
        tf.setToolTipText(tooltip);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return tf;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setFocusPainted(false);
    }

    private void setStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String position = positionField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            String dept = departmentField.getText().trim();

            RegularEmployee emp = new RegularEmployee(id, name, position, salary, dept);
            employees.add(emp);
            refreshTable();
            clearFields();
            setStatus("Employee added successfully.");
        } catch (Exception ex) {
            setStatus("Error: Please check your input values.");
        }
    }

    private void removeSelectedEmployee() {
        int selected = employeeTable.getSelectedRow();
        if (selected >= 0) {
            employees.remove(selected);
            refreshTable();
            setStatus("Selected employee removed.");
        } else {
            setStatus("Please select a row to remove.");
        }
    }

    private void searchEmployee() {
        try {
            int id = Integer.parseInt(searchField.getText().trim());
            Collections.sort(employees);
            int index = binarySearch(id);
            if (index >= 0) {
                employeeTable.setRowSelectionInterval(index, index);
                employeeTable.scrollRectToVisible(employeeTable.getCellRect(index, 0, true));
                setStatus("Employee with ID " + id + " found.");
            } else {
                setStatus("No employee found with ID " + id + ".");
            }
        } catch (Exception ex) {
            setStatus("Invalid ID. Please enter a number.");
        }
    }

    private void sortEmployees() {
        Collections.sort(employees);
        refreshTable();
        setStatus("Employees sorted by ID.");
    }

    private void deleteById(String input) {
        try {
            int id = Integer.parseInt(input.trim());
            boolean found = false;
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == id) {
                    employees.remove(i);
                    refreshTable();
                    setStatus("Employee with ID " + id + " deleted.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                setStatus("No employee found with ID " + id + ".");
            }
        } catch (NumberFormatException ex) {
            setStatus("Invalid ID. Please enter a number.");
        }
    }

    private int binarySearch(int id) {
        int low = 0, high = employees.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midId = employees.get(mid).getId();
            if (midId == id) return mid;
            if (midId < id) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Employee e : employees) {
            tableModel.addRow(new Object[]{
                e.getId(), e.getName(), e.getPosition(), e.getSalary(), ((RegularEmployee) e).getDepartment()
            });
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        positionField.setText("");
        salaryField.setText("");
        departmentField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIInterface().setVisible(true));
    }
}
