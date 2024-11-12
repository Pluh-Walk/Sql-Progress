package it2b.colina.tasktracker;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Project {

    private config conf;
    private Scanner sc;

    public Project() {
        conf = new config();
        sc = new Scanner(System.in);
    }

    public void projectExec() {
        String response;

        do {
            System.out.println("1. ASSIGN PROJECT: ");
            System.out.println("2. VIEW PROJECTS: ");
            System.out.println("3. UPDATE PROJECT: ");
            System.out.println("4. DELETE PROJECT: ");
            System.out.println("5. BACK TO MAIN MENU: ");

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
                    assignProject();
                    break;
                case 2:
                    viewProjects();
                    break;
                case 3:
                    viewProjects();
                    updateProject();
                    break;
                case 4:
                    deleteProject();
                    break;
                case 5:
                    System.out.println("Returning to main menu.");
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

    private void assignProject() {
        config cong = new config();
        Employee emp = new Employee();
        Task task = new Task();
        task.viewTasks();

        int taskId = 0;
        boolean validTaskId = false;
        while (!validTaskId) {
            System.out.print("Task ID to assign: ");
            try {
                taskId = sc.nextInt();
                sc.nextLine();
                String tsql = "SELECT task_id FROM tbl_tasks WHERE task_id = ?";
                if (conf.getSingleValue(tsql, taskId) != 0) {
                    validTaskId = true; 
                } else {
                    System.out.println("Task doesn't exist, select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Task ID.");
                sc.nextLine();
            }
        }

        emp.viewEmployees();
        int employeeId = 0;
        boolean validEmployeeId = false;
        while (!validEmployeeId) {
            System.out.print("Employee ID to assign: ");
            try {
                employeeId = sc.nextInt();
                sc.nextLine(); 
                String esql = "SELECT e_id FROM tbl_employees WHERE e_id = ?";
                if (conf.getSingleValue(esql, employeeId) != 0) {
                    validEmployeeId = true;
                } else {
                    System.out.println("Employee doesn't exist, select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Employee ID.");
                sc.nextLine();
            }
        }

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        System.out.print("Enter project status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_projects (task_id, employee_id, due_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, taskId, employeeId, dueDate, status);
        System.out.println("Project assigned successfully.");
    }

    private void viewProjects() {
        String projectQuery = "SELECT * FROM tbl_projects "
                + "JOIN tbl_tasks ON tbl_projects.task_id = tbl_tasks.task_id "
                + "JOIN tbl_employees ON tbl_projects.employee_id = tbl_employees.e_id";
        String[] projectHeaders = {"Project ID", "Task Name", "E_Name", "Due Date", "Status"};
        String[] projectColumns = {"project_id", "task_name", "l_name", "due_date", "status"};

        conf.viewRecords(projectQuery, projectHeaders, projectColumns);
    }

    private void updateProject() {
        int projectId = 0;
        boolean validProjectId = false;
        while (!validProjectId) {
            System.out.print("Enter Project ID to edit: ");
            try {
                projectId = sc.nextInt();
                sc.nextLine(); 
                validProjectId = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Project ID.");
                sc.nextLine();
            }
        }

        int newTaskId = 0;
        boolean validNewTaskId = false;
        while (!validNewTaskId) {
            System.out.print("Enter new Task ID: ");
            try {
                newTaskId = sc.nextInt();
                sc.nextLine();
                validNewTaskId = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Task ID.");
                sc.nextLine(); 
            }
        }

        int newEmployeeId = 0;
        boolean validNewEmployeeId = false;
        while (!validNewEmployeeId) {
            System.out.print("Enter new Employee ID: ");
            try {
                newEmployeeId = sc.nextInt();
                sc.nextLine(); 
                validNewEmployeeId = true; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Employee ID.");
                sc.nextLine(); 
            }
        }

        System.out.print("Enter new due date (YYYY-MM-DD): ");
        String newDueDate = sc.nextLine();

        System.out.print("Enter new status: ");
        String newStatus = sc.nextLine();

        String sql = "UPDATE tbl_projects SET task_id = ?, employee_id = ?, due_date = ?, status = ? WHERE project_id = ?";
        conf.updateRecord(sql, newTaskId, newEmployeeId, newDueDate, newStatus, projectId);
        System.out.println("Project updated successfully.");
    }

    private void deleteProject() {
        int projectId = 0;
        boolean validProjectId = false;
        while (!validProjectId) {
            System.out.print("Enter Project ID to delete: ");
            try {
                projectId = sc.nextInt();
                sc.nextLine(); 
                validProjectId = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Project ID.");
                sc.nextLine(); // Clear the invalid input
            }
        }

        System.out.print("Are you sure you want to delete Project ID " + projectId + "? (Y/N): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM tbl_projects WHERE project_id = ?";
            conf.deleteRecord(sql, projectId);
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}