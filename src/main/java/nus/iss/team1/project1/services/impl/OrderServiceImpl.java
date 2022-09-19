package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.OrderDao;
import nus.iss.team1.project1.models.Order;
import nus.iss.team1.project1.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public int create(String orderTime, double totalFee, Integer status, Integer canteenID, Integer userID){
        orderDao.create(orderTime,totalFee,status,canteenID,userID);
        return 1;
    }

    @Override
    public List<Order> getOrder(String canteenID, String userID, String status, String orderType){
        String order;
        if(orderType!= null&& orderType.equals("1")){
             order = "asc";
        }
        else{
            order = "desc";
        }
        return orderDao.getOrder(canteenID,userID,status,order);
    }

    @Override
    public int updateStatus(String orderID, String status){
        orderDao.updateStatus(orderID,status);
        return 1;
    }
}