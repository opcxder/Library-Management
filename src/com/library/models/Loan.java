package com.library.models;

import com.library.services.BookService;
import com.library.services.StudentService;
import com.library.util.DateUtils;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int studentId;
    private int bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(int loanId, int studentId, int bookId, LocalDate loanDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOverdue() {
        return DateUtils.isDateOverdue(this.returnDate);
    }

    public Student getStudent() {
        StudentService studentService = new StudentService();
        return studentService.getStudentById(this.studentId);
    }

    public Book getBook() {
        BookService bookService = new BookService();
        return bookService.getBookById(this.bookId);
    }
}