-- Create the Students table
CREATE TABLE Students (
  StudentID INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  CollegeID VARCHAR(50) NOT NULL,
  Email VARCHAR(100) NOT NULL,
  PhoneNumber VARCHAR(20) NOT NULL
);

-- Create the Books table
CREATE TABLE Books (
  BookID INT AUTO_INCREMENT PRIMARY KEY,
  BookName VARCHAR(255) NOT NULL,
  Author VARCHAR(100) NOT NULL,
  Quantity INT NOT NULL,
  PublicationYear INT NOT NULL,
  BookImage BLOB
);

-- Create the Loans table
CREATE TABLE Loans (
  LoanID INT AUTO_INCREMENT PRIMARY KEY,
  StudentID INT NOT NULL,
  BookID INT NOT NULL,
  LoanDate DATE NOT NULL,
  ReturnDate DATE NOT NULL,
  FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
  FOREIGN KEY (BookID) REFERENCES Books(BookID)
);

-- Insert sample data into the Students table
INSERT INTO Students (Name, CollegeID, Email, PhoneNumber)
VALUES
  ('John Doe', '12345', 'johndoe@email.com', '555-1234'),
  ('Jane Smith', '67890', 'janesmith@email.com', '555-5678'),
  ('Michael Johnson', '24680', 'mjohnson@email.com', '555-9012');

-- Insert sample data into the Books table
INSERT INTO Books (BookName, Author, Quantity, PublicationYear, BookImage)
VALUES
  ('To Kill a Mockingbird', 'Harper Lee', 10, 1960, NULL),
  ('1984', 'George Orwell', 15, 1949, NULL),
  ('The Great Gatsby', 'F. Scott Fitzgerald', 8, 1925, NULL),
  ('Pride and Prejudice', 'Jane Austen', 12, 1813, NULL),
  ('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', 20, 1997, NULL);

-- Insert sample data into the Loans table
INSERT INTO Loans (StudentID, BookID, LoanDate, ReturnDate)
VALUES
  (1, 1, '2023-04-01', '2023-04-15'),
  (2, 2, '2023-04-05', '2023-04-19'),
  (3, 3, '2023-04-10', '2023-04-24'),
  (1, 4, '2023-04-15', '2023-04-29'),
  (2, 5, '2023-04-20', '2023-05-04');