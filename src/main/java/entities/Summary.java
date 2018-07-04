package entities;

import java.time.LocalDate;

public class Summary {
    private Integer idSummary;
    private Integer idElectrode;
    private LocalDate produceDate;
    private String customer;
    private LocalDate consumeDate;
    private String note;

    public Summary() {
    }

    public Summary(Integer idSummary, Integer idElectrode, LocalDate produceDate, String customer, LocalDate consumeDate, String note) {
        this.idSummary = idSummary;
        this.idElectrode = idElectrode;
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

    public Integer getIdElectrode() {
        return idElectrode;
    }

    public void setIdElectrode(Integer idElectrode) {
        this.idElectrode = idElectrode;
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
}
