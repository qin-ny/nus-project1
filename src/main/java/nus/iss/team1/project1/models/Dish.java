package nus.iss.team1.project1.models;


import lombok.Data;

@Data
public class Dish {
    private Integer id;
    private String name;
    private double price;
    private Integer stock;
    private String description;
    private Integer availability;
    private Integer dish_type_id;
    private Integer sales_num_thirty;
    private Integer canteen_id;
    private static Integer selected = 0;
}