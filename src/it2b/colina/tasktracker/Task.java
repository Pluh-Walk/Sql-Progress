package it2b.colina.tasktracker;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Task {

    private config conf; 
    private Scanner sc;

    public Task() {
        conf = new config(); 
        sc = new Scanner(System.in); 
    }

    public void taskExec() {
        String response = null;

        do {
            System.out.println("1. ADD TASK: ");
            System.out.println("2. VIEW TASKS: ");
            System.out.println("3. UPDATE TASK: ");
            System.out.println("4. DELETE TASK: ");
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
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    viewTasks();
                    updateTask();
                    break;
                case 4:
                    viewTasks();
                    deleteTask();
                    break;
                case 5:
                    System.out.println("Returning to main menu.");
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
            }

            // Loop for valid confirmation input
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to continue? (Y/N): ");
                response = sc.nextLine();

                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true; // Valid response, continue
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true; // Valid response, exit
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }

        } while (response.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
        sc.close(); 
    }

    public void addTask() {
        String taskName = "";
        String taskDesc = "";

        // Validate task name
        while (true) {
            System.out.print("Task Name: ");
            taskName = sc.nextLine();
            if (isValidTaskName(taskName)) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter a valid task name (letters and spaces only).");
            }
        }

        // Validate task description
        while (true) {
            System.out.print("Task Description: ");
            taskDesc = sc.nextLine();
            if (isValidTaskDescription(taskDesc)) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter a valid task description (letters and spaces only).");
            }
        }

        String dateCreated = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        String sql = "INSERT INTO tbl_tasks (task_name, task_desc, date_created) VALUES (?, ?, ?)";
        conf.addRecord(sql, taskName, taskDesc, dateCreated); 
    }

    public void viewTasks() {
        String taskQuery = "SELECT task_id, task_name, task_desc, date_created FROM tbl_tasks"; 
        String[] taskHeaders = {"Task ID", "Task Name", "Description", "Date Created"};
        String[] taskColumns = {"task_id", "task_name", "task_desc", "date_created"};

        conf.viewRecords(taskQuery, taskHeaders, taskColumns); 
    }

    private void updateTask() {
        System.out.print("Enter Task ID to edit: ");
        int taskId = sc.nextInt();
        sc.nextLine(); 

        String newTaskName = "";
        String newTaskDesc = "";

        // Validate new task name
        while (true) {
            System.out.print("Enter new task name: ");
            newTaskName = sc.nextLine();
            if (isValidTaskName(newTaskName)) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter a valid task name (letters and spaces only).");
            }
        }

        // Validate new task description
        while (true) {
            System.out.print("Enter new task description: ");
            newTaskDesc = sc.nextLine();
            if (isValidTaskDescription(newTaskDesc)) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter a valid task description (letters and spaces only).");
            }
        }

        String sql = "UPDATE tbl_tasks SET task_name = ?, task_desc = ? WHERE task_id = ?";
        conf.updateRecord(sql, newTaskName, newTaskDesc, taskId);

        System.out.println("Task updated successfully.");
    }

    private void deleteTask() {
    int taskId = 0; 
    boolean validId = false; 

   
    while (!validId) {
        System.out.print("Enter Task ID to delete: ");
        try {
            taskId = sc.nextInt(); 
            sc.nextLine();
            validId = true; 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid Task ID (numeric).");
            sc.nextLine(); 
        }
    }

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
    // Method to check if the task name is valid (only letters and spaces)
    private boolean isValidTaskName(String name) {
        return name.matches("[a-zA-Z\\s]+"); // Only allows letters and spaces
    }

    // Method to check if the task description is valid (only letters and spaces)
    private boolean isValidTaskDescription(String description) {
        return description.matches("[a-zA-Z0-9\\s,.!?]+"); // Allows letters, numbers, spaces, and common punctuation
    }
}