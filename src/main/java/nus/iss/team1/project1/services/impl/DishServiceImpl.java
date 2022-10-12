package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.DishDao;
import nus.iss.team1.project1.dao.DishTypeDao;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.services.DishService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishDao dishDao;

    @Autowired
    private DishTypeDao dishTypeDao;

    @Override
    public int create(String name, double price, String description, Integer typeID, Integer canteenID, Integer stock){
        //check if user already exist, if do, return -1
        int cnt = dishDao.checkDishExist(name,canteenID) ;
        if(cnt > 0 ){
            return -1;
        }

        Dish dish = new Dish();
        dish.setName(name);
        dish.setPrice(price);
        dish.setDescription(description);
        dish.setDish_type_id(typeID);
        dish.setCanteen_id(canteenID);
        dish.setStock(stock);
        dish.setName(name);

        dishDao.create(dish);
        return dish.getId();
    }

    @Override
    public List<Dish> get(String canteenID,String type_id, String orderType){
        String order;
        if(orderType!= null&& orderType.equals("1")){
             order = "asc";
            return dishDao.getDishOrderByPrice(canteenID,type_id,order);
        }
        else if(orderType!= null&& orderType.equals("2")){
            order = "desc";
            return dishDao.getDishOrderByPrice(canteenID,type_id,order);
        }
        else if(orderType!= null&& orderType.equals("3")){
            return dishDao.getDishOrderBySales(canteenID,type_id);
        }
        return dishDao.getDish(canteenID,type_id);
    }

    @Override
    public Dish getDishByID(Integer id){
        return dishDao.getDishByID(id);
    }

    @Override
    public int update(Integer id, String name, String price, String description,
                      String type, Integer sales_num_thirty, String stock, Integer availability){
//        if (name!=null || price!=0.0D || description != null || type != null) {
//
//        }
        double newPrice = -1;
        if (price!=null) {
            newPrice = Double.parseDouble(price);
        }

        dishDao.update(id, name, newPrice, description, type, sales_num_thirty, stock, availability);
        return id;
    }

    @Override
    public int delete(Integer id) {
        dishDao.delete(id);
        return id;
    }


}