package com.library.services;

import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.Student;
import com.library.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StudentService {
    private static final Logger logger = LogManager.getLogger(StudentService.class);

    public List<Student> getAllStudents() {
        return Student.getAllStudents();
    }

    public Student getStudentById(int studentId) {
        return Student.getStudentById(studentId);
    }

    public boolean registerStudent(String name, String collegeId, String email, String phoneNumber) {
        Student student = new Student();
        student.setName(name);
        student.setCollegeId(collegeId);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        return student.save();
    }

    public boolean updateStudent(int studentId, String name, String collegeId, String email, String phoneNumber) {
        Student student = getStudentById(studentId);
        if (student != null) {
            student.setName(name);
            student.setCollegeId(collegeId);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            return student.update();
        }
        return false;
    }

    public boolean deleteStudent(int studentId) {
        Student student = getStudentById(studentId);
        if (student != null) {
            return student.delete();
        }
        return false;
    }

    public List<Loan> getBorrowedBooks(Student student) {
        return student.getBorrowedBooks();
    }

    public boolean borrowBook(Student student, Book book) {
        return student.borrowBook(book);
    }

    public boolean returnBook(Student student, Book book) {
        return student.returnBook(book);
    }

    public boolean isBookOverdue(Loan loan) {
        return loan.getStudent().isBookOverdue(loan);
    }

    public boolean isStudentEligibleToBorrow(Student student) {
        List<Loan> borrowedBooks = getBorrowedBooks(student);
        for (Loan loan : borrowedBooks) {
            if (isBookOverdue(loan)) {
                return false;
            }
        }
        return true;
    }
}