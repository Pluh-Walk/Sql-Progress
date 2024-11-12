package it2b.colina.tasktracker;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Employee {

    private config conf;  
    private Scanner sc;

    public Employee() {
        conf = new config();  
        sc = new Scanner(System.in);  
    }

    public void empExec() {
        String response;

        do {
            System.out.println("1. ADD EMPLOYEE: ");
            System.out.println("2. VIEW EMPLOYEES: ");  
            System.out.println("3. UPDATE EMPLOYEE: ");
            System.out.println("4. DELETE EMPLOYEE: ");
            System.out.println("5. EXIT: ");

            int action = 0; 
            boolean validInput = false; 

            while (!validInput) {
                System.out.print("ENTER ACTION: ");
                try {
                    action = sc.nextInt();
                    sc.nextLine();
                    validInput = true; 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    sc.nextLine(); 
                }
            }

            switch (action) {
                case 1:
                    addEmployees();
                    viewEmployees(); 
                    break;
                case 2:
                    viewEmployees();  
                    break;
                case 3:
                    viewEmployees(); 
                    updateEmployee();
                    viewEmployees(); 
                    break;
                case 4:
                    viewEmployees(); 
                    deleteEmployee();  
                    viewEmployees(); 
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
        sc.close(); 
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
        conf.addRecord(sql, fname, lname, email, status);
    }

    public void viewEmployees() {
        String employeesQuery = "SELECT * FROM tbl_employees"; 
        String[] employeesHeaders = {"ID", "First Name", "Last Name", "Email", "Status"};
        String[] employeesColumns = {"e_id", "f_name", "l_name", "e_email", "e_status"};

        conf.viewRecords(employeesQuery, employeesHeaders, employeesColumns); 
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
        conf.updateRecord(sql, newFirstName, newLastName, newEmail, newStatus, employeeId);

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
            conf.deleteRecord(sql, employeeId);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}