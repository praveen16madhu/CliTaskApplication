package com.praveen.connection;



public class ApiResponse<T> {
    private T message;
    private String status;

    // Constructors
    public ApiResponse() {}

    // Getters & Setters
    public T getMessage() { return message; }
    public void setMessage(T message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

