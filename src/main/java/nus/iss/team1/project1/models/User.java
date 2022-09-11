package nus.iss.team1.project1.models;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String NRIC_FIN;
    private String phone_number;
    private String gender;
    private String email;
    private String username;
    private String password;
    private String type;
}