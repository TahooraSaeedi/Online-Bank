package Server;

public class Loan {
    private final String amount;
    private final int months;
    private final String eachMonth;
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

    public int getMonths() {
        return months;
    }

    public String getEachMonth() {
        return eachMonth;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}