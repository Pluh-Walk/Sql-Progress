package it2b.colina.tasktracker;

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
                response = "N"; // Set response to 'N' to exit
                break;
            default:
                System.out.println("Invalid action. Please try again.");
                break;
        }

        if (reportAction != 4) { // Only ask about continuing if not returning to main menu
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to continue viewing reports? (Y/N): ");
                response = sc.nextLine();

                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true; // Valid response, continue
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true; // Valid response, exit
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }

    } while (response.equalsIgnoreCase("Y"));
}

   public void viewProjectDetails() {
    System.out.println("=== Project Details ===");
    
    
    String projectQuery = "SELECT project_id, task_id, employee_id, due_date, status FROM tbl_projects"; 
    String[] projectHeaders = {"Project ID", "Task ID", "Employee ID", "Due Date", "Status"};
    String[] projectColumns = {"project_id", "task_id", "employee_id", "due_date", "status"};
    
   
    conf.viewRecords(projectQuery, projectHeaders, projectColumns);

    
    String detailQuery = "SELECT p.project_id, e.e_id, e.e_email, t.task_id, t.task_name, t.date_created, p.due_date, p.status " +
                         "FROM tbl_projects p " +
                         "JOIN tbl_employees e ON p.employee_id = e.e_id " +
                         "JOIN tbl_tasks t ON p.task_id = t.task_id";

    System.out.println("=== Detailed Project Overview ===");
    String[] detailHeaders = {"Project ID", "Employee ID", "Employee Email", "Task ID", "Task Name", "Date Created", "Due Date", "Status"};
    String[] detailColumns = {"project_id", "e_id", "e_email", "task_id", "task_name", "date_created", "due_date", "status"};

    conf.viewRecords(detailQuery, detailHeaders, detailColumns);
}

    public void viewEmployeeOverview() {
        System.out.println("=== Employee Overview ===");
        String employeeQuery = "SELECT e.e_id,e.f_name,e.l_name, e.e_email, t.task_id, t.task_name, p.due_date " +
                               "FROM tbl_employees e " +
                               "LEFT JOIN tbl_projects p ON e.e_id = p.employee_id " +
                               "LEFT JOIN tbl_tasks t ON p.task_id = t.task_id";

        String[] employeeHeaders = {"Employee ID","Employee Email", "First Name", "Last Name", "Task ID", "Task Name", "Due Date"};
        String[] employeeColumns = {"e_id", "e_email","f_name","l_name", "task_id", "task_name", "due_date"};

        conf.viewRecords(employeeQuery, employeeHeaders, employeeColumns);
    }

    public void viewTaskDetails() {
    System.out.println("=== Task Details ===");
    
    
    String taskQuery = "SELECT t.task_id, t.task_name, t.date_created, p.due_date, e.e_id, e.e_email " +
                       "FROM tbl_tasks t " +
                       "LEFT JOIN tbl_projects p ON t.task_id = p.task_id " +  
                       "LEFT JOIN tbl_employees e ON p.employee_id = e.e_id"; 

    String[] taskHeaders = {"Task ID", "Task Name", "Date Created", "Due Date", "Employee ID", "Employee Email"};
    String[] taskColumns = {"task_id", "task_name", "date_created", "due_date", "e_id", "e_email"};

    conf.viewRecords(taskQuery, taskHeaders, taskColumns);
}
}