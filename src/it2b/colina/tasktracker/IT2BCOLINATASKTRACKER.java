package it2b.colina.tasktracker;
import java.util.Scanner;

public class IT2BCOLINATASKTRACKER {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String response;
        
       
                
        do{        
                
        System.out.println("1. EMPLOYEE");
        System.out.println("2. TASKS");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");
        
       
        System.out.println("ENTER ACTION: ");
        int action = sc.nextInt();
        
        switch(action){
            case 1:
                Employee emp = new Employee();
                emp.empExec();
            break;
             
            case 2:
                 Task task = new Task();
                    task.taskExec();  // Call task menu
                    break;
            
            case 3:

        
            break;
            
            case 4:
                
            break;
             
            case 5:
                
      
            break;

            
            
            
        }
            System.out.println("Do you want to continue? (Y/N): ");
            response = sc.next();
        
    }while(response.equalsIgnoreCase("Y"));
        System.out.println("Kaon Pakag Tae");
    }    
    
    
    

    
   }