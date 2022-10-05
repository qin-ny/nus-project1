package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Dish;

import java.util.List;

public interface DishService {
    public int create(String name, double price, String description, Integer type_id, Integer canteenID,String stock);
    public List<Dish> get(String canteenID,String type_id, String orderType);
    public Dish getDishByID(Integer id);
    public int update(Integer id, String name, String price, String description, String type, Integer sales_num_thirty, String stock);
    public int delete(Integer id);
}