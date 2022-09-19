package nus.iss.team1.project1.models;


import lombok.Data;

@Data
public class Order {
    private Integer id;
    private String create_time;
    private String order_time;
    private double total_fee;
    private String status;
    private String user_id;
    private Integer canteen_id;
}