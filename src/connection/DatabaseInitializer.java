package connection;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                Statement stmt =
                        conn.createStatement()
        ) {

            // MEMBERS TABLE
        	stmt.executeUpdate(
        	        "CREATE TABLE Members (" +
        	                "MemberID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY " +
        	                "(START WITH 1, INCREMENT BY 1), " +
        	                "Name VARCHAR(100), " +
        	                "Email VARCHAR(100), " +
        	                "ActiveLoans INT DEFAULT 0)"
        	);

            // BOOKS TABLE
            stmt.executeUpdate(
                    "CREATE TABLE Books (" +
                            "BookID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY " +
                            "(START WITH 1, INCREMENT BY 1), " +
                            "Title VARCHAR(100), " +
                            "Author VARCHAR(100), " +
                            "ISBN VARCHAR(20), " +
                            "Available BOOLEAN)"
            );

            // LOANS TABLE
            stmt.executeUpdate(
                    "CREATE TABLE Loans (" +
                            "LoanID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY " +
                            "(START WITH 1, INCREMENT BY 1), " +
                            "BookID INT, " +
                            "MemberID INT, " +
                            "LoanDate DATE, " +
                            "ReturnDate DATE, " +
                            "FOREIGN KEY (BookID) REFERENCES Books(BookID), " +
                            "FOREIGN KEY (MemberID) REFERENCES Members(MemberID))"
            );

            // INDEXES
            stmt.executeUpdate(
                    "CREATE INDEX idx_isbn ON Books(ISBN)");

            stmt.executeUpdate(
                    "CREATE INDEX idx_member ON Loans(MemberID)");

            stmt.executeUpdate(
                    "CREATE INDEX idx_return ON Loans(ReturnDate)");

           

            System.out.println(
                    "Database initialized successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Tables may already exist.");

            System.out.println(e.getMessage());
        }
    }
}