package domain;

public class Day {
    private int dayNaumber;
    private int count;

    public Day(int dayNaumber, int count) {
        this.dayNaumber = dayNaumber;
        this.count = count;
    }

    public int getDayNaumber() {
        return dayNaumber;
    }

    public void setDayNaumber(int dayNaumber) {
        this.dayNaumber = dayNaumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
