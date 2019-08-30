package model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Price {
    private UUID id;
    private double amount;
    private double cost;
    private double unitCost;
    private TimePoint timePoint;

    public Price(@NonNull double amount, @NonNull double cost){
        this.id = UUID.randomUUID();
        this.cost = cost;
        this.amount = amount;
        this.unitCost = cost/amount;
        this.timePoint = new TimePoint(amount);
    }
}
