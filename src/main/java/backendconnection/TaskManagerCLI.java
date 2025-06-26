package backendconnection;


import java.util.List;
import java.util.Scanner;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import backendconnection.dto.Task;
import backendconnection.dto.User;



public class TaskManagerCLI {
	
	
    static Scanner scanner = new Scanner(System.in);
    static RestTemplate restTemplate = new RestTemplate();
    static String baseUrl = "http://localhost:8099/"; // backend URL


    public static void main(String[] args) {

        int choice = -1;

        while (choice != 9) {
            showMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        createTask();
                        break;
                    case 3:
                        assignTask();
                        break;
                    case 4:
                        reassignTask();
                        break;
                    case 5:
                        updateTask();
                        break;
                    case 6:
                        deleteTask();
                        break;
                    case 7:
                        viewAllTasks();
                        break;
                    case 8:
                        viewAllUsers();
                        break;
                    case 9:
                        System.out.println("Exiting... Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Task Manager CLI ---");
        System.out.println("1. Create new user");
        System.out.println("2. Create new task");
        System.out.println("3. Assign task to user");
        System.out.println("4. Reassign task");
        System.out.println("5. Update task");
        System.out.println("6. Delete task");
        System.out.println("7. View all tasks");
        System.out.println("8. View all users");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    // Define methods below
    
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
                    baseUrl + "users", request, String.class);

            System.out.println("✅ " + response.getBody());

        } catch (HttpClientErrorException e) {
            // 4xx errors (like user already exists, bad input, etc.)
            System.out.println("❌ Client Error: " + e.getStatusCode());
            System.out.println("Reason: " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            // 5xx errors (server internal issues including JDBC-related exceptions)
            System.out.println("❌ Server Error: " + e.getStatusCode());
            System.out.println("Reason: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            // Catch any other unexpected exception
            System.out.println("❌ Failed to create user: " + e.getMessage());
        }

    }


    static void createTask() {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();


        Task task = new Task(taskName,description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Task> request = new HttpEntity<>(task, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + "tasks", request, String.class);

            System.out.println("✅ " + response.getBody());

        } catch (HttpServerErrorException ex) {
            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
        } 
        
        catch (HttpClientErrorException ex) {
            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
            System.out.println(" Message " + ex.getResponseBodyAsString());
        } 
        catch (Exception e) {
            // Catch any other unexpected exception
            System.out.println("❌ Failed to create user: " + e.getMessage());
        }
    }
    
    
    private static void assignTask() {
        System.out.println("Assigning task to user...");

        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        String url = baseUrl + "tasks/"+userId+"/assign/"+taskId;

        RestTemplate restTemplate = createRestTemplateWithPatchSupport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // no body needed but header is okay

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    requestEntity,
                    String.class
            );

            System.out.println("✅ " + response.getBody());
        }
        catch (HttpServerErrorException ex) {
            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
        } 
        
        catch (HttpClientErrorException ex) {
            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
            System.out.println(" Message " + ex.getResponseBodyAsString());
        } 
        catch (Exception e) {
            // Catch any other unexpected exception
            System.out.println("❌ Failed to create user: " + e.getMessage());
        }
        
    }
    private static void reassignTask() {
        System.out.println("Reassigning task...");
        
        
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();

        String url = baseUrl +"tasks/"+userId+"/assign/"+taskId;

        RestTemplate restTemplate = createRestTemplateWithPatchSupport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // no body needed but header is okay

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    requestEntity,
                    String.class
            );

            System.out.println("✅ " + response.getBody());
        }
        catch (HttpServerErrorException ex) {
            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
        } 
        
        catch (HttpClientErrorException ex) {
            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
            System.out.println(" Message " + ex.getResponseBodyAsString());
        } 
        catch (Exception e) {
            // Catch any other unexpected exception
            System.out.println("❌ Failed to create user: " + e.getMessage());
        }
        
        
    }

    private static void updateTask() {
        System.out.println("Updating task...");
        
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine();
        
        
        System.out.print("Enter the task name: ");
        String taskName = scanner.nextLine();

        // Get new description
        System.out.print("Enter the new description: ");
        String description = scanner.nextLine();
        
        
        String url = baseUrl + "tasks/" +taskId;
        
        Task task = new Task(taskName,description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Task> request = new HttpEntity<>(task, headers);
        RestTemplate restTemplate = createRestTemplateWithPatchSupport();
        
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    request,
                    String.class
            );

            System.out.println("✅ " + response.getBody());
        }
        catch (HttpServerErrorException ex) {
            System.out.println(" Error Code: " + ex.getStatusCode());  // 500
            System.out.println(" Error Message: " + ex.getResponseBodyAsString());
        } 
        
        catch (HttpClientErrorException ex) {
            System.out.println(" Client Error (4xx) " + ex.getStatusCode());
            System.out.println(" Message " + ex.getResponseBodyAsString());
        } 
        catch (Exception e) {
            // Catch any other unexpected exception
            System.out.println("❌ Failed to create user: " + e.getMessage());
        }
        
        
        

    }

    
      static void deleteTask() {
   	    System.out.println("Enter the task ID to delete:");
   	    String id = scanner.nextLine();
   	    
   	    String idAfterTrim=id.trim();
   	    
   	    // no input is entered
   	   if( idAfterTrim.isEmpty()) {
   		   
   		   System.out.println("please enter the valid task id to delete");
   		   
   	   }
   	   
   	   // in order to check wether the number is valid or not
   	   
   	   else {
   		    try {
   		        Long value = Long.valueOf(idAfterTrim);
   		        
   		    } catch (NumberFormatException e) {
   		        System.out.println("Invalid number format: " + e.getMessage());
   		        return;
   		    }
   	   }
   	    
   	    
   	    

   	    try {
   	        ResponseEntity<String> response = restTemplate.exchange(
   	            baseUrl + "tasks/" + id,      // URL with path variable
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
    

    private static void viewAllTasks() {
        System.out.println("Fetching all tasks...");
        System.out.println("enter the user id to get the tasks:");
    	
   	    String id = scanner.nextLine();
   	  
   	
   	try {
   	
   	ResponseEntity<List<Task>> response = restTemplate.exchange(
   		    baseUrl+"tasks/"+id,
   		    HttpMethod.GET,
   		    null,
   		    new ParameterizedTypeReference<List<Task>>() {}
   		);
   	
   	

   		List<Task> tasks = response.getBody();
   		
   		System.out.printf("%-25s | %-50s%n", "Task Name", "Description");
   		System.out.println("---------------------------------------------------------------");

   		for (Task task : tasks) {
   		    System.out.printf("%-25s | %-50s%n", task.getTaskName(), task.getDescription());
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
   	
   	catch (Exception e) {
	        System.out.println(" Unexpected Error: " + e.getMessage());
	    }
   	
   	
      	
   	
   	
        
    }

    private static void viewAllUsers() {
    	 
     	
    	  
    	  
    	
    	try {
    	
    	ResponseEntity<List<User>> response = restTemplate.exchange(
    		    baseUrl+"users",
    		    HttpMethod.GET,
    		    null,
    		    new ParameterizedTypeReference<List<User>>() {}
    		);
    	
    	

    		List<User> users = response.getBody();
    		
    		System.out.printf("%-25s | %-50s%n", "User EmailId", "UserEmailId");
    		System.out.println("---------------------------------------------------------------");

    		for (User user :users ) {
    		    System.out.printf("%-25s | %-50s%n", user.getUserName(), user.getEmailId());
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
    	
    	catch (Exception e) {
 	        System.out.println(" Unexpected Error: " + e.getMessage());
 	    }
    	
    	
        
    }
    
    public static RestTemplate createRestTemplateWithPatchSupport() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
    
    
    
}
