package entities;

import java.time.LocalDate;

public class Summary {
    private Integer idSummary;
    private String electrodeNumber;
    private String type;
    private LocalDate produceDate;
    private String customer;
    private LocalDate consumeDate;
    private String note;

    public Summary() {
    }

    public Summary(Integer idSummary, String electrodeNumber, String type, LocalDate produceDate, String customer, LocalDate consumeDate, String note) {
        this.idSummary = idSummary;
        this.electrodeNumber = electrodeNumber;
        this.type = type;
        this.produceDate = produceDate;
        this.customer = customer;
        this.consumeDate = consumeDate;
        this.note = note;
    }

    public Summary(String electrodeNumber, String type, LocalDate produceDate, String customer, LocalDate consumeDate, String note) {
        this.electrodeNumber = electrodeNumber;
        this.type = type;
        this.produceDate = produceDate;
        this.customer = customer;
        this.consumeDate = consumeDate;
        this.note = note;
    }

    public Integer getIdSummary() {
        return idSummary;
    }

    public void setIdSummary(Integer idSummary) {
        this.idSummary = idSummary;
    }

    public String getElectrodeNumber() {
        return electrodeNumber;
    }

    public void setElectrodeNumber(String electrodeNumber) {
        this.electrodeNumber = electrodeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(LocalDate consumeDate) {
        this.consumeDate = consumeDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "idSummary=" + idSummary +
                ", electrodeNumber='" + electrodeNumber + '\'' +
                ", type='" + type + '\'' +
                ", produceDate=" + produceDate +
                ", customer='" + customer + '\'' +
                ", consumeDate=" + consumeDate +
                ", note='" + note + '\'' +
                '}';
    }
}
