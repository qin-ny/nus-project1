package nus.iss.team1.project1.models;


import lombok.Data;

import java.util.List;

@Data
public class Canteen {
    private Integer id;
    private String name;
    private double star;
    private String description;
    private Integer user_id;
    private List<CanteenType> canteenTypes;
    private Integer orderNums;
}