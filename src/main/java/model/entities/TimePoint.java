package model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TimePoint {
    private LocalTime time;
    private double amount;

    public TimePoint(double amount){
        this.amount = amount;
        this.time = LocalTime.now();
    }
}
