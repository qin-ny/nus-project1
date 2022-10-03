package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.CanteenDao;
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

    @Autowired
    private CanteenDao canteenDao;

    @Override
    public Order create(String orderTime, double totalFee, Integer status, Integer canteenID, Integer userID){
        Order order = new Order();
        order.setOrder_time(orderTime);
        order.setTotal_fee(totalFee);
        order.setStatus(status);
        order.setCanteen(canteenDao.getByID(canteenID));
        order.setUser_id(userID);
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> get(String canteenID, String userID, String status, String orderType){
        String order;
        if(orderType!= null&& orderType.equals("1")){
             order = "asc";
        }
        else{
            order = "desc";
        }
        return orderDao.get(canteenID,userID,status,order);
    }

    @Override
    public int updateStatus(String orderID, String status){
        orderDao.updateStatus(orderID,status);
        return 1;
    }
}