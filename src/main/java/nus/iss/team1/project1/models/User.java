package nus.iss.team1.project1.models;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer type;

}
