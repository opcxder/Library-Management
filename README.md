# Library Management System

The Library Management System is a Java-based application that allows users to manage a library's book collection and student records.

## Features

- Register new students and update their information
- Add, update, and delete books in the library
- Borrow and return books
- View the list of available books
- View the list of books borrowed by a student
- Detect overdue books

## Getting Started

### Prerequisites

- Java 11 or higher
- MySQL database server

### Installation

1. Clone the repository:
git clone https://github.com/your-username/library-management-system.git


Copy code

2. Navigate to the project directory:
cd library-management-system


Copy code

3. Create the database schema using the `library.sql` script:
mysql -u username -p < src/resources/library.sql


Copy code

4. Update the database connection settings in the `ConfigManager` class:

```java
public static String getDatabaseUrl() {
    return "jdbc:mysql://localhost:3306/library";
}

public static String getDatabaseUsername() {
    return "your-username";
}

public static String getDatabasePassword() {
    return "your-password";
}
Build the project using Maven:

Copy code
mvn clean install
Run the application:

Copy code
java -jar target/LibraryManagementSystem-1.0-SNAPSHOT.jar