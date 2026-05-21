package benchmark;
import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class PerformanceEvaluator {

    // INDIVIDUAL INSERT TEST
    public static void individualInsertTest(
            int recordCount) {
        String query =
                "INSERT INTO Books(Title, Author, ISBN, Available) " +
                "VALUES(?, ?, ?, true)";

        long startTime;
        long endTime;

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(1,
                        "Book" + i);

                ps.setString(2,
                        "Author" + i);

                ps.setString(3,
                        "ISBN" + i);

                ps.executeUpdate();
            }

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nINDIVIDUAL INSERT TEST");

            System.out.println(
                    "Records: " + recordCount);

            System.out.println(
                    "Time: " + timeMs + " ms");

            System.out.println(
                    "Throughput: " +
                            (recordCount /
                                    (timeMs / 1000))
                            + " ops/sec");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    // BATCH INSERT TEST
    public static void batchInsertTest(
            int recordCount) {

        String query =
                "INSERT INTO Books(Title, Author, ISBN, Available) " +
                "VALUES(?, ?, ?, true)";
        long startTime;
        long endTime;

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(1,
                        "BatchBook" + i);

                ps.setString(2,
                        "BatchAuthor" + i);

                ps.setString(3,
                        "BISBN" + i);

                ps.addBatch();
            }

            ps.executeBatch();

            endTime =
                    System.nanoTime();

            double timeMs =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nBATCH INSERT TEST");

            System.out.println(
                    "Records: " + recordCount);

            System.out.println(
                    "Time: " + timeMs + " ms");

            System.out.println(
                    "Throughput: " +
                            (recordCount /
                                    (timeMs / 1000))
                            + " ops/sec");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void queryBenchmark() {

        long startTime;
        long endTime;

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                Statement stmt =
                        conn.createStatement()
        ) {

            // FULL TABLE SCAN
            startTime =
                    System.nanoTime();

            stmt.executeQuery(
                    "SELECT * FROM Loans");

            endTime =
                    System.nanoTime();

            double fullScanTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            // INDEXED LOOKUP
            startTime =
                    System.nanoTime();

            stmt.executeQuery(
                    "SELECT * FROM Loans " +
                    "WHERE MemberID = 1");

            endTime =
                    System.nanoTime();

            double indexedTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nQUERY BENCHMARK");

            System.out.println(
                    "Full Scan Time: "
                            + fullScanTime + " ms");

            System.out.println(
                    "Indexed Lookup Time: "
                            + indexedTime + " ms");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void statementVsPreparedBenchmark() {

        long startTime;
        long endTime;

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                Statement stmt =
                        conn.createStatement()
        ) {

            startTime =
                    System.nanoTime();

            stmt.executeQuery(
                    "SELECT * FROM Books WHERE BookID = 1");

            endTime =
                    System.nanoTime();

            double statementTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            String query =
                    "SELECT * FROM Books WHERE BookID=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setInt(1, 1);

            startTime =
                    System.nanoTime();

            ps.executeQuery();

            endTime =
                    System.nanoTime();

            double preparedTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nSTATEMENT VS PREPAREDSTATEMENT");

            System.out.println(
                    "Statement Time: "
                            + statementTime + " ms");

            System.out.println(
                    "PreparedStatement Time: "
                            + preparedTime + " ms");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
    public static void transactionGranularityBenchmark() {

        int recordCount = 100;

        String query =
                "INSERT INTO Books(Title, Author, ISBN, Available) " +
                "VALUES(?, ?, ?, true)";

        long startTime;
        long endTime;

        // -----------------------------------------
        // TEST 1: COMMIT AFTER EVERY OPERATION
        // -----------------------------------------

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            conn.setAutoCommit(false);

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(1,
                        "SingleCommitBook" + i);

                ps.setString(2,
                        "Author" + i);

                ps.setString(3,
                        "SCISBN" + i);

                ps.executeUpdate();

                // COMMIT AFTER EVERY INSERT
                conn.commit();
            }

            endTime =
                    System.nanoTime();

            double singleCommitTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nTRANSACTION GRANULARITY TEST");

            System.out.println(
                    "\nPer-Operation Commit:");

            System.out.println(
                    "Records: " + recordCount);

            System.out.println(
                    "Time: " + singleCommitTime + " ms");

            System.out.println(
                    "Throughput: " +
                            (recordCount /
                                    (singleCommitTime / 1000))
                            + " ops/sec");

            conn.setAutoCommit(true);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        // -----------------------------------------
        // TEST 2: SINGLE BATCH COMMIT
        // -----------------------------------------

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            conn.setAutoCommit(false);

            startTime =
                    System.nanoTime();

            for (int i = 1; i <= recordCount; i++) {

                ps.setString(1,
                        "BatchCommitBook" + i);

                ps.setString(2,
                        "Author" + i);

                ps.setString(3,
                        "BCISBN" + i);

                ps.executeUpdate();
            }

            // SINGLE COMMIT
            conn.commit();

            endTime =
                    System.nanoTime();

            double batchCommitTime =
                    (endTime - startTime)
                            / 1_000_000.0;

            System.out.println(
                    "\nSingle Batch Commit:");

            System.out.println(
                    "Records: " + recordCount);

            System.out.println(
                    "Time: " + batchCommitTime + " ms");

            System.out.println(
                    "Throughput: " +
                            (recordCount /
                                    (batchCommitTime / 1000))
                            + " ops/sec");

            conn.setAutoCommit(true);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        // -----------------------------------------
        // OBSERVATION
        // -----------------------------------------

        System.out.println(
                "\nObservation:");

        System.out.println(
                "Batch commit is faster because " +
                "fewer disk synchronization and " +
                "transaction overhead operations occur.");
    }
}