package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Dish;

import java.util.List;

public interface DishService {
    public int create(String name, double price, String description, String type, String canteenID);
    public List<Dish> getDish(String canteenID,String type, String orderType);
    public int update(Integer id, String name, String price, String description, String type);
}