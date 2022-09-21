package nus.iss.team1.project1.models;


import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Integer id;
    private String create_time;
    private String order_time;
    private double total_fee;
    private Integer status;
    private Integer user_id;
    private Integer canteen_id;
    private List<OrderItem> orderItems;
}