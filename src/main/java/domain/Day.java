package domain;

public class Day {
    private int dayNumber;
    private String acc;
    private int count;

    public Day(int dayNumber, String acc, int count) {
        this.dayNumber = dayNumber;
        this.acc = acc;
        this.count = count;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
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
