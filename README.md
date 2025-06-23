 CLI Application (Command-Line Interface)
The project also includes a CLI-based frontend that interacts with the backend REST APIs to provide a simple and user-friendly terminal interface for managing users and tasks.

🔌 Backend Integration
The CLI application uses Java to connect with the Spring Boot backend using HTTP requests (e.g., via HttpURLConnection, RestTemplate, or similar Java HTTP client).

Menu Options
Upon running the CLI, you will see the following options:

pgsql
Copy
Edit
Welcome to the Task Board CLI!

1. Create a New User
2. Create a New Task
3. Get All Tasks by User ID
4. Update a Task
5. Delete a Task
6. Exit

Option Details
1️⃣ Create a New User
Prompts for:

Username

Email

Password

Validates that all fields are non-empty before making the API call.

2️⃣ Create a New Task
Prompts for:

Title

Description

Status (e.g., PENDING, DONE)

Assignee User ID

Verifies that the user ID exists and that no field is left empty.

3️⃣ Get All Tasks by User ID
Prompts for:

User ID

Fetches and displays all tasks associated with the entered User ID.

Handles case where no tasks are found or user does not exist.

4️⃣ Update a Task
Prompts for:

Task ID

New Title / Description / Status

Checks if the task exists and ensures all updated fields are valid before making the call.

5️⃣ Delete a Task
Prompts for:

Task ID

Sends delete request for the specified task.

Handles invalid or non-existing Task ID gracefully.

⚠️ Edge Case Handling
❌ Empty Fields: Input validation is done before making an API call. The user is prompted again if required fields are empty.

🔍 Non-existent User or Task: Proper messages are shown when user ID or task ID does not exist in the database.

🔁 Retry Mechanism: The app returns to the main menu for another attempt rather than terminating unexpectedly.

🛑 Invalid Options: User is notified if they enter an invalid menu option.

