package com.library.ui;

import com.library.models.Book;
import com.library.models.Student;
import com.library.services.BookService;
import com.library.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class BookMenu {
    private static final Logger logger = LogManager.getLogger(BookMenu.class);
    private final BookService bookService;
    private final StudentService studentService;

    public BookMenu() {
        this.bookService = new BookService();
        this.studentService = new StudentService();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== Book Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    updateBook(scanner);
                    break;
                case 3:
                    deleteBook(scanner);
                    break;
                case 4:
                    viewBooks();
                    break;
                case 5:
                    borrowBook(scanner);
                    break;
                case 6:
                    returnBook(scanner);
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void addBook(Scanner scanner) {
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter publication year: ");
        int publicationYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter book image path: ");
        String imagePath = scanner.nextLine();

        if (bookService.addBook(bookName, author, quantity, publicationYear, imagePath)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error adding book.");
        }
    }

    private void updateBook(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter new book name: ");
        String bookName = scanner.nextLine();

        System.out.print("Enter new author: ");
        String author = scanner.nextLine();

        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter new publication year: ");
        int publicationYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter new book image path: ");
        String imagePath = scanner.nextLine();

        if (bookService.updateBook(bookId, bookName, author, quantity, publicationYear, imagePath)) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Error updating book.");
        }
    }

    private void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (bookService.deleteBook(bookId)) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Error deleting book.");
        }
    }

    private void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Available books in the library:");
            for (Book book : books) {
                System.out.println("Book ID: " + book.getBookId() + ", Book Name: " + book.getBookName() + ", Author: " + book.getAuthor() + ", Quantity: " + book.getQuantity());
            }
        }
    }

    private void borrowBook(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Student student = studentService.getStudentById(studentId);
        Book book = bookService.getBookById(bookId);

        if (student != null && book != null) {
            if (studentService.isStudentEligibleToBorrow(student)) {
                if (bookService.borrowBook(student, book)) {
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("Error borrowing book.");
                }
            } else {
                System.out.println("Student has overdue books and is not eligible to borrow.");
            }
        } else {
            System.out.println("Student or book not found.");
        }
    }

    private void returnBook(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Student student = studentService.getStudentById(studentId);
        Book book = bookService.getBookById(bookId);

        if (student != null && book != null) {
            if (bookService.returnBook(student, book)) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Error returning book.");
            }
        } else {
            System.out.println("Student or book not found.");
        }
    }
}