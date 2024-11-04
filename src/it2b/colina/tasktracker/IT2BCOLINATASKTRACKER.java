package it2b.colina.tasktracker;
import java.util.Scanner;

public class IT2BCOLINATASKTRACKER {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String response;
        
       
                
        do{        
                
        System.out.println("1. EMPLOYEE");
        System.out.println("2. TASKS");
        System.out.println("3. REPORTS");
        System.out.println("4. EXIT");
        
       
        System.out.println("ENTER ACTION: ");
        int action = sc.nextInt();
        
        switch(action){
            case 1:
                Employee emp = new Employee();
                emp.empExec();
            break;
             
            case 2:
                 Task task = new Task();
                    task.taskExec(); 
                    break;
            
            case 3:
                
            break;
            
            case 4:
                System.out.println("thank you for using the system");
                
            break;
             


            
            
            
        }
            System.out.println("Do you want to continue? (Y/N): ");
            response = sc.next();
        
    }while(response.equalsIgnoreCase("Y"));
        System.out.println("Kaon Pakag Tae");
    }    
    
    
    

    
   }