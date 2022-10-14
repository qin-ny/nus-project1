package nus.iss.team1.project1.models;

import lombok.Data;

@Data
public class Member {
    private Integer id;
    private Integer level;
    private String expired_time;
    private boolean availability;
    private Integer customer_id;
}
