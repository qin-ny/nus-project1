package nus.iss.team1.project1.models;


import lombok.Data;

@Data
public class Customer {
    private Integer id;
    private Integer reward_points;
    private Integer is_member;
    private Integer user_id;
}