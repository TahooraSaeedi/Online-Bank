package Server;

public class Loan {
    private String amount;
    private int months;
    private String eachMonth;
    private long time;

    public Loan(String amount, int months) {
        this.amount = amount;
        this.months = months;
        this.eachMonth = MyMath.findDivision(amount, months);
        this.time = System.currentTimeMillis();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public String getEachMonth() {
        return eachMonth;
    }

    public void setEachMonth(String eachMonth) {
        this.eachMonth = eachMonth;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}