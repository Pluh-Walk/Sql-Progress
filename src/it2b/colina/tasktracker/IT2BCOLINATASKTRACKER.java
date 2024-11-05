package it2b.colina.tasktracker;

import java.util.Scanner;

public class IT2BCOLINATASKTRACKER {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {        
            System.out.println("1. EMPLOYEE");
            System.out.println("2. TASKS");
            System.out.println("3. PROJECTS");
            System.out.println("4. REPORTS");
            System.out.println("5. EXIT");
        
            System.out.print("ENTER ACTION: ");
            int action = sc.nextInt();
        
            switch(action) {
                case 1:
                    Employee emp = new Employee();
                    emp.empExec();
                    break;
             
                case 2:
                    Task task = new Task();
                    task.taskExec(); 
                    break;
            
                case 3:
                    Project project = new Project();
                    project.projectExec(); // Call the project execution method
                    break;
            
                case 4:
                    System.out.println("Thank you for using the system.");
                    break;
                
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

            if (action != 5) {
                System.out.print("Do you want to continue? (Y/N): ");
                response = sc.next();
            } else {
                response = "N"; 
            }
        
        } while(response.equalsIgnoreCase("Y"));
        
        System.out.println("Kaon Pakag Tae");
        sc.close();
    }    
}