package entities;

import domain.Day;

import java.time.Month;
import java.time.Year;
import java.util.List;

public class AccoutingHistory {
    private int id;
    private Detail detail;
    private Year year;
    private Month month;
    private String acc;
    private List<Day> days;

    public AccoutingHistory() {}

    public AccoutingHistory(Year year, Month month, String acc, List<Day> days) {
        this.year = year;
        this.month = month;
        this.acc = acc;
        this.days = days;
    }

    public AccoutingHistory(Detail detail, Year year, Month month, String acc, List<Day> days) {
        this.detail = detail;
        this.year = year;
        this.month = month;
        this.acc = acc;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
