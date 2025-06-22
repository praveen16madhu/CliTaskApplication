package com.praveen.connection;


	
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;
	import java.util.UUID;
	
	import com.praveen.dto.*;

	public class ConnectionToBackEnd {

	    static Scanner scanner = new Scanner(System.in);
	    static RestTemplate restTemplate = new RestTemplate();
	    static String baseUrl = "http://localhost:8099/"; // backend URL

	    public static void main(String[] args) {

	        while (true) {
	            System.out.println("\n----- MENU -----");
	            System.out.println("1. Create User");
	            System.out.println("2. Create Task");
	            System.out.println("3. Get Tasks");
	            System.out.println("4. Delete Task");
	            System.out.println("5. Update Task");
	            System.out.println("6. Create Task");
	            
	            
	            
	            System.out.println("6. Exit");
	            System.out.print("Enter option:");
	            int option = scanner.nextInt();
	            scanner.nextLine(); // clear newline

	            switch (option) {
	                case 1:
	                    createUser();
	                    break;
	                case 2:
	                    createTask();
	                    break;
	                    
	                case 3:
	                	getTasks();
	                	break;
	                	
	                case 4:
	                	deleteTask();
	                	break;
	                	
	                case 5:
	                	updateTask();
	                	break;
	                	
	                case 6:
	                    System.out.println(" Exiting...");
	                    return;
	                default:
	                    System.out.println(" Invalid option");
	            }
	        }
	    }

	    static void createUser() {
	        System.out.print("Enter username:\n ");
	        String name = scanner.nextLine();

	        System.out.print("Enter email: \n");
	        String email = scanner.nextLine();
	        
	        
	        

	        
	        
	        
	        System.out.println(" enter the password here");
	        String password = scanner.nextLine();
	        
	        User user = new User( name, email,password);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<User> request = new HttpEntity<>(user, headers);
	        
	        
	        if(user.getUserName().isEmpty()|| user.getPassword().isEmpty() || user.getEmailId().isEmpty()) {
	        	System.out.println("can create the user because of invalid entered fields");
	        	return;
	        }
	        	
	        try {
	            ResponseEntity<String> response = restTemplate.postForEntity(
	                    baseUrl + "users/createuser", request, String.class);

	            System.out.println("✅ " + response.getBody());

	        } catch (Exception e) {
	            System.out.println(" Failed to create user: " + e.getMessage());
	        }
	    }

	    static void createTask() {
	        System.out.print("Enter task name: ");
	        String taskName = scanner.nextLine();

	        System.out.print("Enter description: ");
	        String description = scanner.nextLine();

	        System.out.print("Enter assignee user ID: ");
	        String assigneeId = scanner.nextLine();

	        String taskId = UUID.randomUUID().toString();

	        Task task = new Task(taskId, taskName, description, assigneeId);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<Task> request = new HttpEntity<>(task, headers);

	        try {
	            ResponseEntity<TaskDtoFromBackEnd> response = restTemplate.postForEntity(
	                    baseUrl + "/task", request, TaskDtoFromBackEnd.class);

	            System.out.println("✅ " + response.getBody().toString());

	        } catch (HttpServerErrorException ex) {
	            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
	            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
	        } 
	        
	        catch (HttpClientErrorException ex) {
	            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
	            System.out.println(" Message " + ex.getResponseBodyAsString());
	        } 
	    }
	    
	    
	    
	    
	    static void getTasks() {
	    	
	    	System.out.println("enter the user id to get the tasks");
	    	
	    	 String id = scanner.nextLine();
	    	    
	    	    // no input is entered
	    	   if( id.trim().isEmpty()) {
	    		   
	    		   System.out.println("please enter the valid task id to delete");
	    		   
	    	   }
	    	   
	    	   // in order to check wether the number is valid or not
	    	   
	    	   else {
	    		    try {
	    		        Long value = Long.valueOf(id);
	    		        
	    		    } catch (NumberFormatException e) {
	    		        System.out.println("Invalid number format: " + e.getMessage());
	    		        return;
	    		    }
	    	   }
	    	
	    	try {
	    	
	    	ResponseEntity<List<TaskDtoFromBackEnd>> response = restTemplate.exchange(
	    		    baseUrl+"tasks/gettasks"+id,
	    		    HttpMethod.GET,
	    		    null,
	    		    new ParameterizedTypeReference<List<TaskDtoFromBackEnd>>() {}
	    		);
	    	
	    	

	    		List<TaskDtoFromBackEnd> tasks = response.getBody();
	    		
	    		
	    		
	    		for( TaskDtoFromBackEnd task:  tasks) {
	    			
	    		// for printing the data	
	    			System.out.println("  the task name "+ task.getTaskName());
	    			
	    			System.out.println("  the task name "+ task.getDescription());
	    			
	    			
	    			
	    			
	    		}
	    		
	    	}
	    	
	    	catch (HttpServerErrorException ex) {
	            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
	            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
	        } 
	        
	        catch (HttpClientErrorException ex) {
	            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
	            System.out.println(" Message " + ex.getResponseBodyAsString());
	        } 
	    	
	    	
	       	
	    	
	    	
	    }  
	    
	    
	    
	     static void deleteTask() {
	    	    System.out.println("Enter the task ID to delete:");
	    	    String id = scanner.nextLine();
	    	    
	    	    // no input is entered
	    	   if( id.trim().isEmpty()) {
	    		   
	    		   System.out.println("please enter the valid task id to delete");
	    		   
	    	   }
	    	   
	    	   // in order to check wether the number is valid or not
	    	   
	    	   else {
	    		    try {
	    		        Long value = Long.valueOf(id);
	    		        
	    		    } catch (NumberFormatException e) {
	    		        System.out.println("Invalid number format: " + e.getMessage());
	    		        return;
	    		    }
	    	   }
	    	    
	    	    
	    	    

	    	    try {
	    	        ResponseEntity<String> response = restTemplate.exchange(
	    	            baseUrl + "/tasks/deletetask" + id,      // URL with path variable
	    	            HttpMethod.DELETE,            // HTTP DELETE method
	    	            null,                         // No body needed
	    	            String.class                  // Expecting a string message
	    	        );

	    	        System.out.println("✅ Deleted: " + response.getBody());

	    	    } catch (HttpClientErrorException e) {
	    	        System.out.println(" Client Error: " + e.getStatusCode());
	    	        System.out.println("Message: " + e.getResponseBodyAsString());
	    	    } catch (HttpServerErrorException e) {
	    	        System.out.println(" Server Error: " + e.getStatusCode());
	    	        System.out.println("Message: " + e.getResponseBodyAsString());
	    	    } catch (Exception e) {
	    	        System.out.println(" Unexpected Error: " + e.getMessage());
	    	    }
	    	}
	     
	     
	     static void updateTask() {
	    	 
	    	 
	    	 
	    	 System.out.println("enter the task id to update");
	    	 
	    	 String id = scanner.nextLine();
	    	    
	    	    // no input is entered
	    	   if( id.trim().isEmpty()) {
	    		   
	    		   System.out.println("please enter the valid task id to delete");
	    		   
	    	   }
	    	   
	    	   // in order to check wether the number is valid or not
	    	   
	    	   else {
	    		    try {
	    		        Long value = Long.valueOf(id);
	    		        
	    		    } catch (NumberFormatException e) {
	    		        System.out.println("Invalid number format: " + e.getMessage());
	    		        return;
	    		    }
	    	   }
	    	 
	    	 
	    	 
	    	 System.out.println("enter the updated taskname ");
	    	 
	    	 String taskname = scanner.nextLine();
	    	 
	    	 System.out.println("enter the updated description ");
	    	 String description = scanner.nextLine();
	    	 
	    	 
	    	 
	    	 try {
	    	        ResponseEntity<TaskDtoFromBackEnd> response = restTemplate.exchange(
	    	            baseUrl + "/tasks/updatetask" + id,      // URL with path variable
	    	            HttpMethod.PATCH,            
	    	            null,                         // No body needed
	    	            TaskDtoFromBackEnd.class                  // Expecting a string message
	    	        );

	    	        System.out.println("updated details " + response.getBody());

	    	    } 
	    	 catch (HttpClientErrorException e) {
	    	        System.out.println(" Client Error: " + e.getStatusCode());
	    	        System.out.println("Message: " + e.getResponseBodyAsString());
	    	    } catch (HttpServerErrorException e) {
	    	        System.out.println(" Server Error: " + e.getStatusCode());
	    	        System.out.println("Message: " + e.getResponseBodyAsString());
	    	    } catch (Exception e) {
	    	        System.out.println(" Unexpected Error: " + e.getMessage());
	    	    }
	    	}
	    	 
	    	 
	    	 
	    	 
	     }

	    
	    
	    
	



