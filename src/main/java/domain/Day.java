package domain;

public class Day {
    private int dayNumber;
    private int count;

    public Day(int dayNumber, int count) {
        this.dayNumber = dayNumber;
        this.count = count;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
