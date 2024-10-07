package it2b.colina.tasktracker;

import java.util.Scanner;

public class Employee {

    private config config;  // Assuming config class handles database interaction
    private Scanner sc;

    public Employee() {
        config = new config();  // Initialize config once
        sc = new Scanner(System.in);  // Initialize Scanner once
    }

    public void empExec() {
        String response;

        do {
            System.out.println("1. ADD EMPLOYEE: ");
            System.out.println("2. VIEW EMPLOYEES: ");  // Corrected to VIEW EMPLOYEES
            System.out.println("3. UPDATE: ");
            System.out.println("4. DELETE: ");
            System.out.println("5. EXIT: ");

            System.out.print("ENTER ACTION: ");
            int action = sc.nextInt();
            sc.nextLine(); 

            switch (action) {
                case 1:
                    addEmployees();
                    break;
                case 2:
                    viewEmployees();  // Corrected call to viewEmployees
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();  
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    return;  
                default:
                    System.out.println("Invalid action. Please try again.");
            }

            System.out.print("Do you want to continue? (Y/N): ");
            response = sc.nextLine();
        } while (response.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
        sc.close();  // Close Scanner at the end
    }

public void addEmployees() {
    System.out.print("Employee First Name: ");
    String fname = sc.nextLine();
    System.out.print("Employee Last Name: ");
    String lname = sc.nextLine();
    System.out.print("Employee Email: ");
    String email = sc.nextLine();
    System.out.print("Employee Status: ");
    String status = sc.nextLine();

    String sql = "INSERT INTO tbl_employees (f_name, l_name, e_email, e_status) VALUES (?, ?, ?, ?)";
    config.addRecord(sql, fname, lname, email, status); // Correct method call
}

private void viewEmployees() {
    String employeesQuery = "SELECT * FROM tbl_employees"; // Corrected to employees
    String[] employeesHeaders = {"ID", "First Name", "Last Name", "Email", "Status"};
    String[] employeesColumns = {"e_id", "f_name", "l_name", "e_email", "e_status"};

    config.viewRecords(employeesQuery, employeesHeaders, employeesColumns); // Correct method call
}

    private void updateEmployee() {
        System.out.print("Enter Employee ID to edit: ");
        int employeeId = sc.nextInt();
        sc.nextLine();  

        System.out.print("Enter new first name: ");
        String newFirstName = sc.nextLine();

        System.out.print("Enter new last name: ");
        String newLastName = sc.nextLine();

        System.out.print("Enter new email: ");
        String newEmail = sc.nextLine();

        System.out.print("Enter new status: ");
        String newStatus = sc.nextLine();

        String sql = "UPDATE tbl_employees SET f_name = ?, l_name = ?, e_email = ?, e_status = ? WHERE e_id = ?";
        config.addRecord("oten.db", sql, newFirstName, newLastName, newEmail, newStatus, employeeId);

        System.out.println("Employee updated successfully.");
    }

    private void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int employeeId = sc.nextInt();
        sc.nextLine();  

        System.out.print("Are you sure you want to delete Employee ID " + employeeId + "? (Y/N): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM tbl_employees WHERE e_id = ?";
            config.addRecord("oten.db", sql, employeeId);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}