package com.library.services;

import com.library.database.DatabaseConnection;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.Student;
import com.library.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public List<Loan> getBorrowedBooksByStudent(Student student) {
        List<Loan> loans = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Loans WHERE StudentID = ?")) {
            statement.setInt(1, student.getStudentId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int loanId = resultSet.getInt("LoanID");
                int bookId = resultSet.getInt("BookID");
                LocalDate loanDate = resultSet.getDate("LoanDate").toLocalDate();
                LocalDate returnDate = resultSet.getDate("ReturnDate").toLocalDate();
                Loan loan = new Loan(loanId, student.getStudentId(), bookId, loanDate, returnDate);
                loans.add(loan);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving borrowed books for student: {}", e.getMessage());
        }

        return loans;
    }

    public boolean borrowBook(Student student, Book book) {
        String sql = "INSERT INTO Loans (StudentID, BookID, LoanDate, ReturnDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, book.getBookId());
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(14)));
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error borrowing book: {}", e.getMessage());
            return false;
        }
    }

    public boolean returnBook(Student student, Book book) {
        String sql = "DELETE FROM Loans WHERE StudentID = ? AND BookID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, book.getBookId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error returning book: {}", e.getMessage());
            return false;
        }
    }

    public List<Loan> getOverdueLoansList() {
        List<Loan> overdueLoansList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Loans WHERE ReturnDate < ?")) {
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int loanId = resultSet.getInt("LoanID");
                int studentId = resultSet.getInt("StudentID");
                int bookId = resultSet.getInt("BookID");
                LocalDate loanDate = resultSet.getDate("LoanDate").toLocalDate();
                LocalDate returnDate = resultSet.getDate("ReturnDate").toLocalDate();
                Loan loan = new Loan(loanId, studentId, bookId, loanDate, returnDate);
                overdueLoansList.add(loan);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving overdue loans: {}", e.getMessage());
        }

        return overdueLoansList;
    }

    public boolean isLoanOverdue(Loan loan) {
        return DateUtils.isDateOverdue(loan.getReturnDate());
    }
}