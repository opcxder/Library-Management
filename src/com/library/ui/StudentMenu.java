package com.library.ui;

import com.library.models.Student;
import com.library.services.BookService;
import com.library.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private static final Logger logger = LogManager.getLogger(StudentMenu.class);
    private final StudentService studentService;
    private final BookService bookService;

    public StudentMenu() {
        this.studentService = new StudentService();
        this.bookService = new BookService();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== Student Menu ===");
            System.out.println("1. Register Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Borrowed Books");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    registerStudent(scanner);
                    break;
                case 2:
                    updateStudent(scanner);
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    viewBorrowedBooks(scanner);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void registerStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter college ID: ");
        String collegeId = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        if (studentService.registerStudent(name, collegeId, email, phoneNumber)) {
            System.out.println("Student registered successfully.");
        } else {
            System.out.println("Error registering student.");
        }
    }

    private void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new college ID: ");
        String collegeId = scanner.nextLine();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();

        if (studentService.updateStudent(studentId, name, collegeId, email, phoneNumber)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Error updating student.");
        }
    }

    private void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (studentService.deleteStudent(studentId)) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Error deleting student.");
        }
    }

    private void viewBorrowedBooks(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            List<com.library.models.Loan> borrowedBooks = studentService.getBorrowedBooks(student);
            if (borrowedBooks.isEmpty()) {
                System.out.println("Student has no borrowed books.");
            } else {
                System.out.println("Borrowed books for student " + student.getName() + ":");
                for (com.library.models.Loan loan : borrowedBooks) {
                    com.library.models.Book book = bookService.getBookById(loan.getBookId());
                    System.out.println("Book: " + book.getBookName());
                }
            }
        } else {
            System.out.println("Student not found.");
        }
    }
}