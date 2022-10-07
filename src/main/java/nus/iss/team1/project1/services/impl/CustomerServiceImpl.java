package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.CustomerDao;
import nus.iss.team1.project1.models.Customer;
import nus.iss.team1.project1.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public int create(Integer user_id){
        Customer customer = new Customer();
        customer.setUser_id(user_id);
        customerDao.create(customer);
        return customer.getId();
    }
    @Override
    public int update(Integer user_id,Integer rewardPoint){
        customerDao.update(user_id, rewardPoint);
        return user_id;
    }
    @Override
    public Customer getCustomer(Integer user_id){
        return customerDao.getCustomer(user_id);
    }
}