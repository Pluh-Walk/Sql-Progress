package it2b.colina.tasktracker;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IT2BCOLINATASKTRACKER {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("==The Task Tracker==");
            System.out.println("1. EMPLOYEE");
            System.out.println("2. TASKS");
            System.out.println("3. PROJECTS");
            System.out.println("4. REPORTS");
            System.out.println("5. EXIT");

            int action = 0; 
            boolean validInput = false; 

            while (!validInput) {
                System.out.print("ENTER ACTION: ");
                try {
                    action = sc.nextInt();
                    sc.nextLine(); 
                    validInput = true; 
                } catch (InputMismatchException e) {
                    System.out.println("==The Task Tracker==");
                    System.out.println("1. EMPLOYEE");
                    System.out.println("2. TASKS");
                    System.out.println("3. PROJECTS");
                    System.out.println("4. REPORTS");
                    System.out.println("5. EXIT");
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    sc.nextLine(); 
                }
            }

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
                    project.projectExec(); 
                    break;
            
                case 4:
                    Reports report  = new Reports();
                    report.showReportsMenu(); 
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
                response = sc.nextLine();
            } else {
                response = "N"; 
            }

        } while(response.equalsIgnoreCase("Y"));
        
        System.out.println("Kaon Pakag Tae");
        sc.close();
    }    
}