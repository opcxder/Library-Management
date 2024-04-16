package com.library.models;
import com.library.database.DatabaseConnection;
import com.library.services.LoanService;
import com.library.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private static final Logger logger = LogManager.getLogger(Student.class);

    private int studentId;
    private String name;
    private String collegeId;
    private String email;
    private String phoneNumber;

    public Student() {
    }

    public Student(int studentId, String name, String collegeId, String email, String phoneNumber) {
        this.studentId = studentId;
        this.name = name;
        this.collegeId = collegeId;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String collegeId = resultSet.getString("CollegeID");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                Student student = new Student(studentId, name, collegeId, email, phoneNumber);
                students.add(student);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving students: {}", e.getMessage());
        }
        return students;
    }

    public static Student getStudentById(int studentId) {
        Student student = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE StudentID = ?")) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                String collegeId = resultSet.getString("CollegeID");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                student = new Student(studentId, name, collegeId, email, phoneNumber);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving student: {}", e.getMessage());
        }
        return student;
    }

    public boolean save() {
        String sql = "INSERT INTO Students (Name, CollegeID, Email, PhoneNumber) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, collegeId);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error saving student: {}", e.getMessage());
            return false;
        }
    }

    public boolean update() {
        String sql = "UPDATE Students SET Name = ?, CollegeID = ?, Email = ?, PhoneNumber = ? WHERE StudentID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, collegeId);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setInt(5, studentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error updating student: {}", e.getMessage());
            return false;
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM Students WHERE StudentID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error deleting student: {}", e.getMessage());
            return false;
        }
    }

    public List<Loan> getBorrowedBooks() {
        LoanService loanService = new LoanService();
        return loanService.getBorrowedBooksByStudent(this);
    }

    public boolean borrowBook(Book book) {
        LoanService loanService = new LoanService();
        return loanService.borrowBook(this, book);
    }

    public boolean returnBook(Book book) {
        LoanService loanService = new LoanService();
        return loanService.returnBook(this, book);
    }

    public boolean isBookOverdue(Loan loan) {
        return DateUtils.isDateOverdue(loan.getReturnDate());
    }
}