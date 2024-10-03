/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it2b.colina.tasktracker;

import java.util.Scanner;


public class Employee {
    
    public void empExec(){
    
    Scanner sc = new Scanner(System.in);
    String response;
        
       
                
        do{        
                
        System.out.println("1. ADD EMPLOYEE: ");
        System.out.println("2. VIEW TASKS: ");
        System.out.println("3. UPDATE: ");
        System.out.println("4. DELETE: ");
        System.out.println("5. EXIT: ");
        
       
        System.out.println("ENTER ACTION: ");
        int action = sc.nextInt();

        Employee emp = new Employee();
        switch(action){
            case 1:
                emp.addEmployees();
            break;
             
            case 2:
                emp.viewEmployees();
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
    
    
 
    
      public void addEmployees(){
          
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Employee First Name: ");
        String fname = sc.next();
        System.out.print("Employee Last Name: ");
        String lname = sc.next();
        System.out.print("Employee Email: ");
        String email = sc.next();
        System.out.print("Employee Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_employees (f_name, l_name, e_email, e_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, status);


    }
      
      private void viewEmployees() {
        config conf = new config();
        String empQuery = "SELECT * FROM tbl_employees";
        String[] empHeaders = {"Employee ID", "First Name", "Last Name", "Email", "Status"};
        String[] empColumns = {"e_id", "f_name", "l_name", "e_email", "e_status"};

        conf.viewRecords(empQuery, empHeaders, empColumns);
    }

    
   }

