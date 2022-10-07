package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Customer;

public interface CustomerService {
    public int create(Integer user_id);
    public int update(Integer user_id,Integer rewardPoint);
    public Customer getCustomer(Integer user_id);
}
