package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.OrderItemDao;
import nus.iss.team1.project1.models.OrderItem;
import nus.iss.team1.project1.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public OrderItem create(Integer number, double fee, Integer orderID, Integer dishID){
        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(number);
        orderItem.setFee(fee);
        orderItem.setOrder_id(orderID);
        orderItem.setDish_id(dishID);
        orderItemDao.create(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> get(Integer orderID, Integer dishID){
        return orderItemDao.get(orderID,dishID);
    }
}
