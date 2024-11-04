package it2b.colina.tasktracker;

import java.util.Scanner;

public class Task {

    private config conf; 
    private Scanner sc;

    public Task() {
        conf = new config(); 
        sc = new Scanner(System.in); 
    }

    public void taskExec() {
        String response;

        do {
            System.out.println("1. ADD TASK: ");
            System.out.println("2. VIEW TASKS: ");
            System.out.println("3. UPDATE TASK: ");
            System.out.println("4. DELETE TASK: ");
            System.out.println("5. BACK TO MAIN MENU: "); // Updated option to go back

            System.out.print("ENTER ACTION: ");
            int action = sc.nextInt();
            sc.nextLine(); 

            switch (action) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    System.out.println("Returning to main menu.");
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid action. Please try again.");
            }

            System.out.print("Do you want to continue? (Y/N): ");
            response = sc.nextLine();
        } while (response.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
        sc.close(); // Close the scanner here
    }

    public void addTask() {
        System.out.print("Task Name: ");
        String taskName = sc.nextLine();
        System.out.print("Task Description: ");
        String taskDesc = sc.nextLine();
        System.out.print("Assigned Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();  // Consume newline
        System.out.print("Task Status: ");
        String status = sc.nextLine();
        System.out.print("Task Due Date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine();

        String sql = "INSERT INTO tbl_tasks (task_name, task_desc, employee_id, task_status, due_date) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, taskName, taskDesc, employeeId, status, dueDate); 
    }

    // Viewing all tasks
    private void viewTasks() {
        String taskQuery = "SELECT * FROM tbl_tasks";
        String[] taskHeaders = {"Task ID", "Task Name", "Description", "Employee ID", "Status", "Due Date"};
        String[] taskColumns = {"task_id", "task_name", "task_desc", "employee_id", "task_status", "due_date"};

        conf.viewRecords(taskQuery, taskHeaders, taskColumns); 
    }

    // Updating a task
    private void updateTask() {
        System.out.print("Enter Task ID to edit: ");
        int taskId = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter new task name: ");
        String newTaskName = sc.nextLine();

        System.out.print("Enter new task description: ");
        String newTaskDesc = sc.nextLine();

        System.out.print("Enter new task status: ");
        String newStatus = sc.nextLine();

        System.out.print("Enter new due date (YYYY-MM-DD): ");
        String newDueDate = sc.nextLine();

        String sql = "UPDATE tbl_tasks SET task_name = ?, task_desc = ?, task_status = ?, due_date = ? WHERE task_id = ?";
        conf.updateRecord(sql, newTaskName, newTaskDesc, newStatus, newDueDate, taskId);

        System.out.println("Task updated successfully.");
    }

    private void deleteTask() {
        System.out.print("Enter Task ID to delete: ");
        int taskId = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Are you sure you want to delete Task ID " + taskId + "? (Y/N): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM tbl_tasks WHERE task_id = ?";
            conf.deleteRecord(sql, taskId);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}