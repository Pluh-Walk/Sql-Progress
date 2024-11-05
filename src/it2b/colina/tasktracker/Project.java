package it2b.colina.tasktracker;

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

            System.out.print("ENTER ACTION: ");
            int action = sc.nextInt();
            sc.nextLine();

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

        System.out.print("Task ID to assign: ");
        int taskId = sc.nextInt();
        sc.nextLine(); // Consume newline

        String tsql = "SELECT task_id FROM tbl_tasks WHERE task_id = ?";
        while (conf.getSingleValue(tsql, taskId) == 0) {
            System.out.print("Task doesn't exist, Select Again: ");
            taskId = sc.nextInt();
            sc.nextLine(); // Consume newline
        }

        emp.viewEmployees();
        System.out.print("Employee ID to assign: ");
        int employeeId = sc.nextInt();
        sc.nextLine(); // Consume newline

        String esql = "SELECT e_id FROM tbl_employees WHERE e_id = ?";
        while (conf.getSingleValue(esql, employeeId) == 0) {
            System.out.print("Employee doesn't exist, Select Again: ");
            employeeId = sc.nextInt();
            sc.nextLine(); // Consume newline
        }

        // New fields for due date and status
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        System.out.print("Enter project status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_projects (task_id, employee_id, due_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, taskId, employeeId, dueDate, status);
        System.out.println("Project assigned successfully.");
    }

    private void viewProjects() {
        String projectQuery = "SELECT * FROM tbl_projects";
        String[] projectHeaders = {"Project ID", "Task ID", "Employee ID", "Due Date", "Status"};
        String[] projectColumns = {"project_id", "task_id", "employee_id", "due_date", "status"};

        conf.viewRecords(projectQuery, projectHeaders, projectColumns);
    }

    private void updateProject() {
        System.out.print("Enter Project ID to edit: ");
        int projectId = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter new Task ID: ");
        int newTaskId = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter new Employee ID: ");
        int newEmployeeId = sc.nextInt();
        sc.nextLine(); // Consume newline

        // New fields for due date and status
        System.out.print("Enter new due date (YYYY-MM-DD): ");
        String newDueDate = sc.nextLine();

        System.out.print("Enter new status: ");
        String newStatus = sc.nextLine();

        String sql = "UPDATE tbl_projects SET task_id = ?, employee_id = ?, due_date = ?, status = ? WHERE project_id = ?";
        conf.updateRecord(sql, newTaskId, newEmployeeId, newDueDate, newStatus, projectId);
        System.out.println("Project updated successfully.");
    }

    private void deleteProject() {
        System.out.print("Enter Project ID to delete: ");
        int projectId = sc.nextInt();
        sc.nextLine(); // Consume newline

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