package market;
public class Transaction {
    private final String customerID;
    private final String customerName;
    private final String date;
    private final String productName;

    public Transaction(String customerID, String customerName, String date, String productName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.date = date;
        this.productName = productName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Customer Name: " + customerName + ", Date: " + date + ", Product Name: " + productName;
    }
}