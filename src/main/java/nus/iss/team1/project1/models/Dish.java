package nus.iss.team1.project1.models;


import lombok.Data;

import javax.persistence.Column;

@Data
public class Dish {
    private Integer id;
    private String name;
    private double price;
    private Integer stock;
    private String description;
    private Integer availability;
    @Column(name="dish_type_id")
    private Integer dish_type_id;
    @Column(name="sales_num_thirty")
    private Integer sales_num_thirty;
    @Column(name="canteen_id")
    private Integer canteen_id;
    private static Integer selected = 0;
}