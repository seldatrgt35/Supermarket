package market;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;


public class SupermarketTransactions {

    private static int TABLE_SIZE = 10000; // Size of the hash table
    private static final int LIST_SIZE = 1000; // Size of the sorted list to store customer transactions
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    public long indextime = 0;

    private final HashTable<String , LinkedList<Transaction>> hashtable; // Hash table to store customer transactions

    public SupermarketTransactions() {
        hashtable = new HashTable();
    }

    public void readDataset() throws IOException {
        Scanner scanner = new Scanner(new FileReader("./Customer.txt"));
        String line;
        long time = System.currentTimeMillis();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            System.out.println(line);
            String[] tokens = line.split(",");
            String customerID = tokens[0];
            String customerName = tokens[1];
            String dateString = tokens[2];
            String productName = tokens[3];

            Transaction transaction = new Transaction(customerID, customerName, dateString, productName);

            if (!hashtable.contains(String.valueOf(customerID))) {
                //putLinearProbing(customerID, customerName, dateString, productName);
                hashtable.put(String.valueOf(customerID), new LinkedList());
            }

            LinkedList<Transaction> transactions = hashtable.get(String.valueOf(customerID));
            transactions.addFirst(transaction); // Add transaction to the head of the list (reverse chronological order)
        }
        indextime = System.currentTimeMillis() - time;
        scanner.close();
    }

    public LinkedList<Transaction> getTransactionsByCustomerID(String customerID) {
        LinkedList<Transaction> transactions = hashtable.get(String.valueOf(customerID));
        if (transactions == null) {
            return new LinkedList<>();
        }
        return new LinkedList<>();
    }
    // Functionality to remove a customer and associated value from the hash table
    public void remove(String customerID) {
        hashtable.remove(customerID);
    }

    // Functionality to dynamically resize the hash table
    private void resize() {
        int newCapacity = TABLE_SIZE * 2; // Double the current table size
        HashTable<String, LinkedList<Transaction>> newHashtable = new HashTable();

        // Rehash existing entries to the new table
        for (Map.Entry<String, LinkedList<Transaction>> entry : hashtable.entrySet()) {
            newHashtable.put(entry.getKey(), entry.getValue());
        }

        // Update the capacity and hash table reference
        TABLE_SIZE = newCapacity;
        hashtable.clear();
        hashtable.putAll(newHashtable);
    }
    public void put(String customerID, String customerName, String dateString, String productName) {
        // Check the load factor and resize if needed
        if ((double) hashtable.size() / TABLE_SIZE >= LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        Transaction transaction = new Transaction(customerID, customerName, dateString, productName);

        if (!hashtable.contains(String.valueOf(Integer.parseInt(customerID)))) {
            hashtable.put(customerID, new LinkedList<>());
        }

        LinkedList<Transaction> transactions = hashtable.get(customerID);
        transactions.addFirst(transaction); // Add transaction to the head of the list (reverse chronological order)
    }
    // Simple Summation Function (SSF) to generate hash code
    public int calculateSSFHashCode(String s) {
        int hashCode = 0;
        for (char ch : s.toCharArray()) {
            hashCode += ch;
        }
        return hashCode;
    }

    // Polynomial Accumulation Function (PAF) to generate hash code
    public int calculatePAFHashCode(String s, int z) {
        int hashCode = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            int charValue = Character.toLowerCase(ch) - 'a' + 1; // Convert characters to numbers (1-26)

            // Use Horner's rule to perform the calculation and apply the modulus operator
            hashCode = (hashCode * z + charValue) % Integer.MAX_VALUE;
        }

        return hashCode;
    }
    public void putLinearProbing(String customerID, String customerName, String dateString, String productName) {
        int hash = calculateSSFHashCode(customerID);

        while (hashtable.contains(String.valueOf(customerID))) {
            hash = (hash + 1) % TABLE_SIZE; // Linear Probing: bir sonraki uygun hücreye geç
        }

        if ((double) hashtable.size() / TABLE_SIZE >= LOAD_FACTOR_THRESHOLD) {
            resize();
            putLinearProbing(customerID, customerName, dateString, productName);
        } else {
            Transaction transaction = new Transaction(customerID, customerName, dateString, productName);
            hashtable.put(customerID, new LinkedList<>(Collections.singletonList(transaction)));
        }
    }
    public static void printTransactions(SupermarketTransactions transactions, String customerID) {
        LinkedList<Transaction> customerTransactions = transactions.getTransactionsByCustomerID(customerID);
        System.out.println("Transactions for Customer ID: " + customerID);
        for (Transaction transaction : customerTransactions) {
            System.out.println(transaction);
        }
        System.out.println();
    }

    public void putDoubleHashing(String customerID, String customerName, String dateString, String productName) {
        int hash = calculateSSFHashCode(customerID);
        int d = TABLE_SIZE - (hash % TABLE_SIZE); // DH için ikincil hash fonksiyonu
        int j = 0;

        while (hashtable.contains(String.valueOf(customerID))) {
            hash = (hash + j * d) % TABLE_SIZE; // Double Hashing: bir sonraki uygun hücreye geç
            j++;
        }

        if ((double) hashtable.size() / TABLE_SIZE >= LOAD_FACTOR_THRESHOLD) {
            resize();
            putDoubleHashing(customerID, customerName, dateString, productName);
        } else {
            Transaction transaction = new Transaction(customerID, customerName, dateString, productName);
            hashtable.put(customerID, new LinkedList<>());
        }
    }
}
