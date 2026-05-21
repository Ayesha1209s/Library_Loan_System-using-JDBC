package business;
import java.sql.ResultSet;
import connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BusinessLogic {

    // REGISTER MEMBER
    public static void registerMember(
            String name,
            String email) {

        String query =
                "INSERT INTO Members(Name, Email, ActiveLoans) " +
                "VALUES(?, ?, 0)";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setString(1, name);
            ps.setString(2, email);

            ps.executeUpdate();

            System.out.println(
                    "Member registered successfully.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // ADD BOOK
    public static void addBook(
            String title,
            String author,
            String isbn) {

        String query =
                "INSERT INTO Books(Title, Author, ISBN, Available) " +
                "VALUES(?, ?, ?, true)";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);

            ps.executeUpdate();

            System.out.println(
                    "Book added successfully.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void viewActiveLoans(int memberId) {

        String query =
                "SELECT LoanID, BookID, LoanDate " +
                "FROM Loans " +
                "WHERE MemberID=? " +
                "AND ReturnDate IS NULL";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setInt(1, memberId);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\nACTIVE LOANS");

            while (rs.next()) {

                System.out.println(
                        "Loan ID: " +
                                rs.getInt("LoanID"));

                System.out.println(
                        "Book ID: " +
                                rs.getInt("BookID"));

                System.out.println(
                        "Loan Date: " +
                                rs.getDate("LoanDate"));

                System.out.println("----------------");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void viewOverdueBooks() {

        String query =
                "SELECT * FROM Loans " +
                "WHERE ReturnDate IS NULL " +
                "AND LoanDate < CURRENT_DATE - 30 DAYS";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println(
                    "\nOVERDUE BOOKS");

            while (rs.next()) {

                System.out.println(
                        "Loan ID: " +
                                rs.getInt("LoanID"));

                System.out.println(
                        "Book ID: " +
                                rs.getInt("BookID"));

                System.out.println(
                        "Member ID: " +
                                rs.getInt("MemberID"));

                System.out.println(
                        "Loan Date: " +
                                rs.getDate("LoanDate"));

                System.out.println("----------------");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void viewAllMembers() {

        String query = "SELECT * FROM Members";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println("\n========== MEMBERS TABLE ==========");

            System.out.printf(
                    "%-10s %-20s %-30s %-15s%n",
                    "MemberID",
                    "Name",
                    "Email",
                    "ActiveLoans");

            System.out.println(
                    "---------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-10d %-20s %-30s %-15d%n",

                        rs.getInt("MemberID"),

                        rs.getString("Name"),

                        rs.getString("Email"),

                        rs.getInt("ActiveLoans"));
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void viewAllBooks() {

        String query = "SELECT * FROM Books";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println("\n================ BOOKS TABLE ================");

            System.out.printf(
                    "%-10s %-25s %-20s %-15s %-10s%n",

                    "BookID",
                    "Title",
                    "Author",
                    "ISBN",
                    "Available");

            System.out.println(
                    "--------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-10d %-25s %-20s %-15s %-10s%n",

                        rs.getInt("BookID"),

                        rs.getString("Title"),

                        rs.getString("Author"),

                        rs.getString("ISBN"),

                        rs.getBoolean("Available"));
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void viewAllLoans() {

        String query = "SELECT * FROM Loans";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println("\n================ LOANS TABLE ================");

            System.out.printf(
                    "%-10s %-10s %-12s %-15s %-15s%n",

                    "LoanID",
                    "BookID",
                    "MemberID",
                    "LoanDate",
                    "ReturnDate");

            System.out.println(
                    "----------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-10d %-10d %-12d %-15s %-15s%n",

                        rs.getInt("LoanID"),

                        rs.getInt("BookID"),

                        rs.getInt("MemberID"),

                        rs.getDate("LoanDate"),

                        rs.getDate("ReturnDate"));
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}