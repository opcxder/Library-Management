package com.library.services;

import com.library.database.DatabaseConnection;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.Student;
import com.library.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);

    public List<Book> getAllBooks() {
        return Book.getAllBooks();
    }

    public Book getBookById(int bookId) {
        return Book.getBookById(bookId);
    }

    public boolean addBook(String bookName, String author, int quantity, int publicationYear, String imagePath) {
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setQuantity(quantity);
        book.setPublicationYear(publicationYear);
        book.loadBookImage(imagePath);
        return book.save();
    }

    public boolean updateBook(int bookId, String bookName, String author, int quantity, int publicationYear, String imagePath) {
        Book book = getBookById(bookId);
        if (book != null) {
            book.setBookName(bookName);
            book.setAuthor(author);
            book.setQuantity(quantity);
            book.setPublicationYear(publicationYear);
            book.loadBookImage(imagePath);
            return book.update();
        }
        return false;
    }

    public boolean deleteBook(int bookId) {
        Book book = getBookById(bookId);
        if (book != null) {
            return book.delete();
        }
        return false;
    }

    public boolean borrowBook(Student student, Book book) {
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            book.update();
            return student.borrowBook(book);
        }
        return false;
    }

    public boolean returnBook(Student student, Book book) {
        book.setQuantity(book.getQuantity() + 1);
        book.update();
        return student.returnBook(book);
    }

    public List<Loan> getBorrowedBooksByStudent(Student student) {
        return student.getBorrowedBooks();
    }

    public boolean isBookOverdue(Loan loan) {
        return loan.getStudent().isBookOverdue(loan);
    }
}