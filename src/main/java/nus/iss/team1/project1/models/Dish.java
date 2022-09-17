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
    private String type;
    private Integer sales_num_thirty;
    private Integer canteen_id;
}