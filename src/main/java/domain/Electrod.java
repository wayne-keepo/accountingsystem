package domain;

import java.time.LocalDate;

public class Electrod {
    private Integer id;
    private String electrodNumber;
    private String type;
//    private LocalDate produceDate;
//    private String customer;
//    private LocalDate consumeDate;
//    private String note;

    public Electrod() {
    }

    public Electrod(Integer id, String electrodNumber, String type) {
        this.id = id;
        this.electrodNumber = electrodNumber;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElectrodNumber() {
        return electrodNumber;
    }

    public void setElectrodNumber(String electrodNumber) {
        this.electrodNumber = electrodNumber;
    }

//    public LocalDate getProduceDate() {
//        return produceDate;
//    }
//
//    public void setProduceDate(LocalDate produceDate) {
//        this.produceDate = produceDate;
//    }
//
//    public String getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(String customer) {
//        this.customer = customer;
//    }
//
//    public LocalDate getConsumeDate() {
//        return consumeDate;
//    }
//
//    public void setConsumeDate(LocalDate consumeDate) {
//        this.consumeDate = consumeDate;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }

    public String getInfoForTree(){
        return electrodNumber+" "+type;
    }
}
