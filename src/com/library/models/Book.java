package com.library.models;

import com.library.database.DatabaseConnection;
import com.library.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private static final Logger logger = LogManager.getLogger(Book.class);

    private int bookId;
    private String bookName;
    private String author;
    private int quantity;
    private int publicationYear;
    private byte[] bookImage;

    public Book() {
    }

    public Book(int bookId, String bookName, String author, int quantity, int publicationYear, byte[] bookImage) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.quantity = quantity;
        this.publicationYear = publicationYear;
        this.bookImage = bookImage;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public byte[] getBookImage() {
        return bookImage;
    }

    public void setBookImage(byte[] bookImage) {
        this.bookImage = bookImage;
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Books")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("BookID");
                String bookName = resultSet.getString("BookName");
                String author = resultSet.getString("Author");
                int quantity = resultSet.getInt("Quantity");
                int publicationYear = resultSet.getInt("PublicationYear");
                byte[] bookImage = resultSet.getBytes("BookImage");
                Book book = new Book(bookId, bookName, author, quantity, publicationYear, bookImage);
                books.add(book);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving books: {}", e.getMessage());
        }
        return books;
    }

    public static Book getBookById(int bookId) {
        Book book = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Books WHERE BookID = ?")) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String bookName = resultSet.getString("BookName");
                String author = resultSet.getString("Author");
                int quantity = resultSet.getInt("Quantity");
                int publicationYear = resultSet.getInt("PublicationYear");
                byte[] bookImage = resultSet.getBytes("BookImage");
                book = new Book(bookId, bookName, author, quantity, publicationYear, bookImage);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving book: {}", e.getMessage());
        }
        return book;
    }

    public boolean save() {
        String sql = "INSERT INTO Books (BookName, Author, Quantity, PublicationYear, BookImage) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookName);
            statement.setString(2, author);
            statement.setInt(3, quantity);
            statement.setInt(4, publicationYear);
            statement.setBytes(5, bookImage);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error saving book: {}", e.getMessage());
            return false;
        }
    }

    public boolean update() {
        String sql = "UPDATE Books SET BookName = ?, Author = ?, Quantity = ?, PublicationYear = ?, BookImage = ? WHERE BookID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookName);
            statement.setString(2, author);
            statement.setInt(3, quantity);
            statement.setInt(4, publicationYear);
            statement.setBytes(5, bookImage);
            statement.setInt(6, bookId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error updating book: {}", e.getMessage());
            return false;
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM Books WHERE BookID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error deleting book: {}", e.getMessage());
            return false;
        }
    }

    public void loadBookImage(String imagePath) {
        try {
            this.bookImage = ImageUtils.readImageBytes(imagePath);
        } catch (IOException e) {
            logger.error("Error loading book image: {}", e.getMessage());
        }
    }
}