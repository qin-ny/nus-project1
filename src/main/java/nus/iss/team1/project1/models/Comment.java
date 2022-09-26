package nus.iss.team1.project1.models;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer order_id;
    private Integer user_id;
    private Integer canteen_id;
    private float star;
    private String comment;
}
