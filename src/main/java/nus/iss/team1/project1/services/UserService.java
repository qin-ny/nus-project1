package nus.iss.team1.project1.services;

public interface UserService {
    public int validate(String userName,String password,String type);
    public int create(String userName,String password,String type);
}
