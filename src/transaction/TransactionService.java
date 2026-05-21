package transaction;

import connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;

public class TransactionService {

    // PROCESS LOAN
    public static void processLoan(
            int bookId,
            int memberId) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            // DISABLE AUTO COMMIT
            conn.setAutoCommit(false);

            // CHECK BOOK AVAILABILITY
            String checkBook =
                    "SELECT Available FROM Books " +
                    "WHERE BookID=?";

            PreparedStatement checkStmt =
                    conn.prepareStatement(checkBook);

            checkStmt.setInt(1, bookId);

            ResultSet rs =
                    checkStmt.executeQuery();

            if (rs.next()) {

                boolean available =
                        rs.getBoolean("Available");

                if (!available) {

                    System.out.println(
                            "Book not available.");

                    conn.rollback();

                    return;
                }

            } else {

                System.out.println(
                        "Book does not exist.");

                conn.rollback();

                return;
            }

            // SAVEPOINT
            Savepoint savepoint =
                    conn.setSavepoint();

            // UPDATE BOOK STATUS
            String updateBook =
                    "UPDATE Books SET Available=false " +
                    "WHERE BookID=?";

            PreparedStatement updateStmt =
                    conn.prepareStatement(updateBook);

            updateStmt.setInt(1, bookId);

            updateStmt.executeUpdate();

            // INSERT LOAN
            String insertLoan =
                    "INSERT INTO Loans(BookID, MemberID, LoanDate) " +
                    "VALUES(?, ?, CURRENT_DATE)";

            PreparedStatement loanStmt =
                    conn.prepareStatement(insertLoan);

            loanStmt.setInt(1, bookId);
            loanStmt.setInt(2, memberId);

            loanStmt.executeUpdate();

            // UPDATE MEMBER ACTIVE LOANS
            String updateMember =
                    "UPDATE Members " +
                    "SET ActiveLoans = ActiveLoans + 1 " +
                    "WHERE MemberID=?";

            PreparedStatement memberStmt =
                    conn.prepareStatement(updateMember);

            memberStmt.setInt(1, memberId);

            int rows =
                    memberStmt.executeUpdate();

            // SIMULATE FAILURE
            if (rows == 0) {

                System.out.println(
                        "Member update failed.");

                conn.rollback(savepoint);

                return;
            }

            // COMMIT
            conn.commit();

            System.out.println(
                    "Loan processed successfully.");

        } catch (Exception e) {

            try {

                if (conn != null) {

                    conn.rollback();

                    System.out.println(
                            "Transaction rolled back.");
                }

            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }

            System.out.println(e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);

                    conn.close();
                }

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }
    public static void returnBook(int bookId) {

        Connection conn = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            // UPDATE BOOK STATUS
            String updateBook =
                    "UPDATE Books SET Available=true " +
                    "WHERE BookID=?";

            PreparedStatement ps1 =
                    conn.prepareStatement(updateBook);

            ps1.setInt(1, bookId);

            ps1.executeUpdate();

            // UPDATE RETURN DATE
            String updateLoan =
                    "UPDATE Loans " +
                    "SET ReturnDate=CURRENT_DATE " +
                    "WHERE BookID=? AND ReturnDate IS NULL";

            PreparedStatement ps2 =
                    conn.prepareStatement(updateLoan);

            ps2.setInt(1, bookId);

            ps2.executeUpdate();

            conn.commit();

            System.out.println(
                    "Book returned successfully.");

        } catch (Exception e) {

            try {

                if (conn != null) {

                    conn.rollback();
                }

            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }

            System.out.println(e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);

                    conn.close();
                }

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }
}