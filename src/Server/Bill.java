package Server;

public class Bill {

    private String billId;
    private String paymentId;
    private String amount;

    public Bill(String billId, String paymentId, String amount) {
        this.billId = billId;
        this.paymentId = paymentId;
        this.amount = amount;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
