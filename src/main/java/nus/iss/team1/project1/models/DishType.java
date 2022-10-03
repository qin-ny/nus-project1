package nus.iss.team1.project1.models;

import lombok.Data;

@Data
public class DishType {
    private Integer id;
    private String type;
    private Integer canteen_id;
}
