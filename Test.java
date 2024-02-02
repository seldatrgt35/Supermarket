package market;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class Test {
    public static void main(String[] args) throws IOException {
        try {
            SupermarketTransactions supermarketTransactions = new SupermarketTransactions();
            supermarketTransactions.readDataset();

            System.out.println(supermarketTransactions.indextime);



            // 1. Başlangıç zamanını al
            long startTime = System.nanoTime();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter scenario number (1-8): ");
            int scenarioNumber = scanner.nextInt();

            switch (scenarioNumber) {
                case 1:
                    testScenario(supermarketTransactions, "Load Factor α=50%, SSF, LP");
                    break;
                case 2:
                    testScenario(supermarketTransactions, "Load Factor α=50%, SSF, DH");
                    break;
                case 3:
                    testScenario(supermarketTransactions, "Load Factor α=50%, PAF, LP");
                    break;
                case 4:
                    testScenario(supermarketTransactions, "Load Factor α=50%, PAF, DH");
                    break;
                case 5:
                    testScenario(supermarketTransactions, "Load Factor α=80%, SSF, LP");
                    break;
                case 6:
                    testScenario(supermarketTransactions, "Load Factor α=80%, SSF, DH");
                    break;
                case 7:
                    testScenario(supermarketTransactions, "Load Factor α=80%, PAF, LP");
                    break;
                case 8:
                    testScenario(supermarketTransactions, "Load Factor α=80%, PAF, DH");
                    break;
                default:
                    System.out.println("Invalid scenario number. Please enter a number between 1 and 8.");
            }
            List<Transaction> transactions = supermarketTransactions.getTransactionsByCustomerID("11c34489-f95a-45ec-a833-8a329e4d1710");


            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;


            System.out.println("Elapsed Time for Operation: " + elapsedTime + " nanoseconds");
            System.out.println("--------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testScenario(SupermarketTransactions transactions, String scenarioName) {
        printTransactions(transactions, "11c34489-f95a-45ec-a833-8a329e4d1710");
        System.out.println("Testing Scenario: " + scenarioName);


        if (scenarioName.contains("SSF") && scenarioName.contains("LP")) {
            // Senaryo 1: Load Factor α=50%, SSF, LP
            transactions.putLinearProbing("newCustomerIDLP", "New Customer LP", "2023-12-01", "newProductLP");
        } else if (scenarioName.contains("SSF") && scenarioName.contains("DH")) {
            // Senaryo 2: Load Factor α=50%, SSF, DH
            transactions.putDoubleHashing("newCustomerIDDH", "New Customer DH", "2023-12-01", "newProductDH");
        } else if (scenarioName.contains("PAF") && scenarioName.contains("LP")) {
            // Senaryo 3: Load Factor α=50%, PAF, LP
            transactions.putLinearProbing("newCustomerIDLP", "New Customer LP", "2023-12-01", "newProductLP");
        } else if (scenarioName.contains("PAF") && scenarioName.contains("DH")) {
            // Senaryo 4: Load Factor α=50%, PAF, DH
            transactions.putDoubleHashing("newCustomerIDDH", "New Customer DH", "2023-12-01", "newProductDH");
        } else if (scenarioName.contains("SSF") && scenarioName.contains("LP")) {
            // Senaryo 5: Load Factor α=80%, SSF, LP
            transactions.putLinearProbing("newCustomerIDLP", "New Customer LP", "2023-12-01", "newProductLP");
        } else if (scenarioName.contains("SSF") && scenarioName.contains("DH")) {
            // Senaryo 6: Load Factor α=80%, SSF, DH
            transactions.putDoubleHashing("newCustomerIDDH", "New Customer DH", "2023-12-01", "newProductDH");
        } else if (scenarioName.contains("PAF") && scenarioName.contains("LP")) {
            // Senaryo 7: Load Factor α=80%, PAF, LP
            transactions.putLinearProbing("newCustomerIDLP", "New Customer LP", "2023-12-01", "newProductLP");
        } else if (scenarioName.contains("PAF") && scenarioName.contains("DH")) {
            // Senaryo 8: Load Factor α=80%, PAF, DH
            transactions.putDoubleHashing("newCustomerIDDH", "New Customer DH", "2023-12-01", "newProductDH");
        }



    printTransactions(transactions, "newCustomerIDLP");


    }
    public static void printTransactions(SupermarketTransactions transactions, String customerID) {
        List<Transaction> customerTransactions = transactions.getTransactionsByCustomerID(customerID);
        System.out.println("Transactions for Customer ID: " + customerID);
        for (Transaction transaction : customerTransactions) {
            System.out.println(transaction);
        }
        System.out.println();
    }}
