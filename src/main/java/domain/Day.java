package domain;

public class Day {
    private int dayNumber;
    private double count;

    public Day(int dayNumber, double count) {
        this.dayNumber = dayNumber;
        this.count = count;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("DN: %d | C: %d",dayNumber,count);
    }
}
