package com.praveen.connection;

public class Task {
    private String id;
    private String taskName;
    private String description;
    private String assigneeId;

    public Task() {}

    public Task(String taskId, String taskName, String description, String assigneeId) {
        this.id = taskId;
        this.taskName = taskName;
        this.description = description;
        this.assigneeId = assigneeId;
    }

    public String getId() { return id; }
    public void setTaskId(String taskId) { this.id = taskId; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAssigneeId() { return assigneeId; }
   
}