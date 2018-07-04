package entities;

import domain.Day;

import java.util.List;

public class AccoutingHistory {
    private int id;
    private Detail detail;
    private List<Day> days;

    public AccoutingHistory(Detail detail, List<Day> days) {
        this.detail = detail;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
