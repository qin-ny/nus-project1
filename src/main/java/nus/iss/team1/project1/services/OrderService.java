package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Order;

import java.util.List;

public interface OrderService {
    public Order create(String orderTime, double totalFee, Integer status, Integer canteenID, Integer userID);
    public List<Order> get(String canteenID,String userID, String status, String orderType);
    public int updateStatus(String orderID, String status);
}