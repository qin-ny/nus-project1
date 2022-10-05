package nus.iss.team1.project1.models;

import lombok.Data;

@Data
public class OrderItem {
    private Integer id;
    private String name;
    private Integer number;
    private double fee;
    private Integer order_id;
    private Dish dish;
    private Integer dish_id;
}
