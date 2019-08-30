package model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO: подумать над сущностью

@Data
@NoArgsConstructor
public class DatePoint {
    private UUID id;
    private int year;
    private cMonth month;
    private int day;
    private double totalAmount;
    private List<Price> prices = new ArrayList<>();

    public DatePoint(@NonNull Price price){
        LocalDate now = LocalDate.now();
        this.id = UUID.randomUUID();
        this.year = now.getYear();
        this.day = now.getDayOfMonth();
        this.month = new cMonth(now);
        this.prices.add(price);
        this.totalAmount = calculateTotalAmount();
    }

    public void recalculateTotalAmount(){
        this.totalAmount = calculateTotalAmount();
    }

    private double calculateTotalAmount() {
        return prices.stream().mapToDouble(Price::getAmount).sum();
    }
}
