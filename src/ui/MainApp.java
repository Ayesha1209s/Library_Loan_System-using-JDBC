package ui;

import business.BusinessLogic;
import connection.ConnectionManager;
import connection.DatabaseInitializer;
import transaction.TransactionService;
import benchmark.PerformanceEvaluator;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== LIBRARY MENU =====");

            System.out.println("1. Register Member");
            System.out.println("2. Add Book");
            System.out.println("3. Process Loan");
            System.out.println("4. Return Book");
            System.out.println("5. View Active Loans");
            System.out.println("6. View Overdue Books");
            System.out.println("7. View All Members");
            System.out.println("8. View All Books");
            System.out.println("9. View All Loans");
            System.out.println("10. Run Benchmark Tests");
            System.out.println("11. Exit");

            System.out.print("Enter choice: ");

            choice =sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Name: ");

                    String name =sc.nextLine();

                    System.out.print("Enter Email: ");

                    String email =sc.nextLine();

                    BusinessLogic.registerMember(name, email);

                    break;

                case 2:

                    System.out.print("Enter Title: ");

                    String title =sc.nextLine();

                    System.out.print("Enter Author: ");

                    String author =sc.nextLine();

                    System.out.print("Enter ISBN: ");

                    String isbn =sc.nextLine();

                    BusinessLogic.addBook(title,author,isbn);

                    break;

                case 3:

                    System.out.print("Enter Book ID: ");

                    int bookId =sc.nextInt();
                    if(bookId <= 0) {
                    	   System.out.println("Invalid ID");
                    	   break;
                    	}
                    System.out.print("Enter Member ID: ");

                    int memberId =sc.nextInt();

                    TransactionService.processLoan(bookId,memberId);

                    break;

                case 4:

                    System.out.print("Enter Book ID: ");

                    int returnBookId =sc.nextInt();

                    TransactionService.returnBook(returnBookId);

                    break;

                case 5:

                    System.out.print("Enter Member ID: ");

                    int activeMemberId =sc.nextInt();

                    BusinessLogic.viewActiveLoans(activeMemberId);

                    break;

                case 6:

                    BusinessLogic.viewOverdueBooks();

                    break;
                    
                case 7:

                    BusinessLogic.viewAllMembers();

                    break;

                case 8:

                    BusinessLogic.viewAllBooks();

                    break;

                case 9:

                    BusinessLogic.viewAllLoans();

                    break;
                case 10:

                    System.out.println("\nRUNNING BENCHMARKS...");

                    // WARM-UP
                    PerformanceEvaluator.individualInsertTest(10);

                    // INSERT TESTS
                    PerformanceEvaluator.individualInsertTest(1000);

                    PerformanceEvaluator.batchInsertTest(1000);

                    // QUERY TEST
                    PerformanceEvaluator.queryBenchmark();

                    // STATEMENT VS PREPARED
                    PerformanceEvaluator.statementVsPreparedBenchmark();
                 // TRANSACTION GRANULARITY
                    PerformanceEvaluator.transactionGranularityBenchmark();
                    break;
                case 11:

                    System.out.println("Exiting application.");

                    break;
                default:
                	System.out.println("Invalid choice.");
            }

        }
        while (choice != 11);
        ConnectionManager.shutdown();
        sc.close();
    }
}