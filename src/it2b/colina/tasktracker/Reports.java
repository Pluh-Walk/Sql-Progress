package it2b.colina.tasktracker;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Reports {

    private config conf;
    private Scanner sc;

    public Reports() {
        conf = new config();
        sc = new Scanner(System.in);
    }



    public void showReportsMenu() {
    String response = "N"; 

    do {
        System.out.println("=== Reports Menu ===");
        System.out.println("1. View Project Details");
        System.out.println("2. View Employee Overview");
        System.out.println("3. View Task Details");
        System.out.println("4. Back to Main Menu");

        int reportAction = 0; 
        boolean validInput = false; 

        while (!validInput) {
            System.out.print("ENTER ACTION: ");
            try {
                reportAction = sc.nextInt();
                sc.nextLine(); 
                validInput = true; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                sc.nextLine(); 
            }
        }

        switch (reportAction) {
            case 1:
                viewProjectDetails();
                break;
            case 2:
                viewEmployeeOverview();
                break;
            case 3:
                viewTaskDetails();
                break;
            case 4:
                System.out.println("Returning to main menu."); 
                return;
            default:
                System.out.println("Invalid action. Please try again.");
                break;
        }

        if (reportAction != 4) { 
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to continue viewing reports? (Y/N): ");
                response = sc.nextLine();

                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true; 
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true; 
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }

    } while (response.equalsIgnoreCase("Y"));
}





public void viewProjectDetails() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Project prog = new Project();

    System.out.println("=== View Project Details ===");
    prog.viewProjects();

    int projectId = getValidIntegerInput("Enter Project ID to view details: ");

    String sql = "SELECT p.project_id, t.task_name, t.task_desc " +
                 "FROM tbl_projects p " +
                 "JOIN tbl_tasks t ON p.task_id = t.task_id " +
                 "WHERE p.project_id = ?";

    try (Connection conn = conf.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, projectId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("\n--------------- Project Details ---------------");
            System.out.printf("%-20s: %s%n", "Project ID", rs.getInt("project_id"));
            System.out.printf("%-20s: %s%n", "Task Name", rs.getString("task_name"));
            System.out.printf("%-20s: %s%n", "Task Desc", rs.getString("task_desc"));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            System.out.printf("%-20s: %s%n", "Current Time", formattedNow);
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("No project found with ID: " + projectId);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving project details: " + e.getMessage());
    }
}

private int getValidIntegerInput(String prompt) {
    Scanner sc = new Scanner(System.in);
    int value = 0;
    boolean valid = false;

    while (!valid) {
        System.out.print(prompt);
        try {
            value = sc.nextInt();
            sc.nextLine();
            valid = true;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.nextLine();
        }
    }
    return value;
}



   public void viewEmployeeOverview() { 
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Employee emp = new Employee();
    
    emp.viewEmployees();
    

int employeeId = getValidIntegerInput("Enter Employee ID to view tasks: ");

    String sql = "SELECT e.e_id, e.f_name, e.l_name, e.e_email, t.task_name, t.task_desc, p.due_date " +
                 "FROM tbl_employees e " +
                 "JOIN tbl_tasks t ON e.e_id = e.e_id " +
                 "JOIN tbl_projects p ON p.project_id = p.project_id " + 
                 "WHERE e.e_id = ?";

    try (Connection conn = conf.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, employeeId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("\n--------------- Employee Tasks ---------------");
            System.out.printf("%-20s: %s%n", "Employee ID", rs.getInt("e_id"));
            System.out.printf("%-20s: %s%n", "First Name", rs.getString("f_name"));
            System.out.printf("%-20s: %s%n", "Last Name", rs.getString("l_name"));
            System.out.printf("%-20s: %s%n", "Email", rs.getString("e_email"));
            System.out.printf("%-20s: %s%n", "Current Task Taken", rs.getString("task_name"));
            System.out.printf("%-20s: %s%n", "Task Description", rs.getString("task_desc"));
            System.out.printf("%-20s: %s%n", "Due Date", rs.getString("due_date")); 

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            System.out.printf("%-20s: %s%n", "Current DateTime", formattedNow);
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("No tasks found for employee ID: " + employeeId);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving employee tasks: " + e.getMessage());
    }
}

private int getValidInteger(String prompt) {
    Scanner sc = new Scanner(System.in);
    int value = 0;
    boolean valid = false;

    while (!valid) {
        System.out.print(prompt);
        try {
            value = sc.nextInt();
            sc.nextLine(); 
            valid = true;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.nextLine(); 
        }
    }
    return value;
}

public void viewTaskDetails() {
    Task task = new Task();
    
    Scanner sc = new Scanner(System.in);
    
    task.viewTasks();
    
    int taskId = getValidIntegerInput("Enter Task ID to view details: ");

    String sql = "SELECT t.task_name, t.task_desc, p.due_date, e.e_id, e.f_name, e.l_name, e.e_email " +
                 "FROM tbl_tasks t " +
                 "JOIN tbl_projects p ON p.project_id = p.project_id " +
                 "LEFT JOIN tbl_employees e ON t.task_id = t.task_id " + 
                 "WHERE t.task_id = ?";

    try (Connection conn = conf.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, taskId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("\n--------------- Task Details ---------------");
            System.out.printf("%-20s: %s%n", "Task Name", rs.getString("task_name"));
            System.out.printf("%-20s: %s%n", "Task Desc", rs.getString("task_desc"));
            System.out.printf("%-20s: %s%n", "Due Date", rs.getString("due_date"));

            // Displaying associated employees
            System.out.println("\n--- Employees Assigned to this Task ---");
            do {
                System.out.printf("%-20s: %s %s (%s)%n", "Employee ID", rs.getInt("e_id"), rs.getString("f_name"), rs.getString("l_name"));
                System.out.printf("%-20s: %s%n", "Email", rs.getString("e_email"));
                System.out.println("----------------------------------------");
            } while (rs.next());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            System.out.printf("%-20s: %s%n", "Current DateTime", formattedNow);
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("No task found with ID: " + taskId);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving task details: " + e.getMessage());
    }
}

private int getValidIntegerInpt(String prompt) {
    Scanner sc = new Scanner(System.in);
    int value = 0;
    boolean valid = false;

    while (!valid) {
        System.out.print(prompt);
        try {
            value = sc.nextInt();
            sc.nextLine(); // Clear the buffer
            valid = true;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.nextLine(); // Clear invalid input
        }
    }
    return value;
}
}