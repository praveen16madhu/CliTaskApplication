package com.praveen.connection;

public class User {
   
    private String userName;
    private String emailId;
    private String password;

    public User() {}

    public User( String name, String email,String password) {
        
        this.userName = name;
        this.emailId = email;
        this.password=password;
    }

   
    

    public String getUserName() { return userName; }
    
    
    public String getPassword() {
    	
    	return password;
    }
    
    public String getEmailId() {
    	return emailId;
    	
    }
    
    
    public void setName(String name) { this.userName = name; }

    public String getEmail() { return emailId; }
    public void setEmail(String email) { this.emailId = email; }
}
