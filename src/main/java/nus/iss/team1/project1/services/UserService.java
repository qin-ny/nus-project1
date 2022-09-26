package nus.iss.team1.project1.services;

import  nus.iss.team1.project1.models.User;
import java.util.List;

public interface UserService {
    public User validate(String userName,String password,String type);
    public int create(String userName,String password,String name,String gender,String phone, String email,String NRIC,String type);
    public List<User> get(String userName, String name, String gender, String type);
    public int modifyUser(String userName,String gender,String phone,String email,String NRIC,String type);
    public int modifyPassword(String userName,String password);
}