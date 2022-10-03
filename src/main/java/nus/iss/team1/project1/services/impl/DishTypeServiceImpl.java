package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.DishDao;
import nus.iss.team1.project1.dao.DishTypeDao;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;
import nus.iss.team1.project1.services.DishService;
import nus.iss.team1.project1.services.DishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishTypeServiceImpl implements DishTypeService {

    @Autowired
    private DishTypeDao dishTypeDao;

    @Override
    public int create(String type, Integer canteenID) {

        if (dishTypeDao.checkDishTypeExist(type) == 0) {
            DishType dishType = new DishType();
            dishType.setType(type);
            dishType.setCanteen_id(canteenID);

            dishTypeDao.create(dishType);
            return dishType.getId();
        }
        return -1;
    }

    @Override
    public List<DishType> get(String canteenID, String type) {
        return dishTypeDao.get(-1, canteenID, type);
    }

    @Override
    public int update(Integer id, String type) {
        dishTypeDao.update(id, type);
        return id;
    }

    @Override
    public int delete(Integer id) {
        dishTypeDao.delete(id);
        return id;
    }
}
