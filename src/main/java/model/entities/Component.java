package model.entities;

import exceptions.ComponentException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO: поудмать над сущностью; приход/расход, итог, на начало и конец года и т.д.

@Data
@NoArgsConstructor
public class Component {
    private UUID id;
    private String title;
    private double totalAmount;
    private List<DatePoint> points = new ArrayList<>();

    public Component(@NonNull String title, List<DatePoint> dps){
        this.id = UUID.randomUUID();
        this.title = title;
        this.points.addAll(dps);
        this.totalAmount = calculateTotalAmount();
    }

    public void incAmount(double incAmount){
        this.totalAmount+=incAmount;
    }

    public void decAmount(double decAmount){
        this.totalAmount-=decAmount;
        if (this.totalAmount<0){
            throw new ComponentException(
                    String.format("Can not decrease amount because current amount less than decreasing value -> current: %s decreasing: %s",
                            String.valueOf(this.totalAmount), String.valueOf(decAmount)));
        }
    }

    public void recalculateAmount(){
        this.totalAmount = calculateTotalAmount();
    }

    private double calculateTotalAmount(){
        double tmp = 0.0;
        for(DatePoint datePoint: points){
            tmp+=datePoint.getPrices().stream().mapToDouble(Price::getAmount).sum();
        }
        return tmp;
    }
}
