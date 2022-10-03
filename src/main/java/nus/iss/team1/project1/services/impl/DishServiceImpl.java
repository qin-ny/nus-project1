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
    public int create(String name, double price, String description, Integer typeID, String canteenID, String stock){
        //check if user already exist, if do, return -1
        int cnt = dishDao.checkDishExist(name,canteenID) ;
        if(cnt > 0 ){
            return -1;
        }


        dishDao.create(name, price,description,typeID,canteenID,Integer.parseInt(stock));
        return 1;
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
    public int update(Integer id, String name, String price, String description, String type, Integer sales_num_thirty, String stock){
//        if (name!=null || price!=0.0D || description != null || type != null) {
//
//        }
        double newPrice = -1;
        if (price!=null) {
            newPrice = Double.parseDouble(price);
        }

        dishDao.update(id, name, newPrice, description, type, sales_num_thirty, stock);
        return id;
    }

    @Override
    public int delete(Integer id) {
        dishDao.delete(id);
        return id;
    }


}