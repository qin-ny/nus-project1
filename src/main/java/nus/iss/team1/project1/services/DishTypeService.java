package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;

import java.util.List;

public interface DishTypeService {
    public int create(String type, Integer canteenID);
    public List<DishType> get(String canteenID, String type);
    public int update(Integer id, String type);
    public int delete(Integer id);
}