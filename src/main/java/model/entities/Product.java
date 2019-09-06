package model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Product {
    private UUID id;
    private String title;
    private ProductType type;
    private List<Component> components;
    private List<DatePoint> datePoints;

}
