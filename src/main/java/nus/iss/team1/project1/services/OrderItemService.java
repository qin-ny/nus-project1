package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.OrderItem;

import java.util.List;

public interface OrderItemService {
    public OrderItem create(String name, Integer number, double fee, Integer orderID, Integer dishID);
    public List<OrderItem> get(Integer orderID, Integer dishID);
}
