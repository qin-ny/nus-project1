package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.CustomerDao;
import nus.iss.team1.project1.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean(name="customerDao")
    private CustomerDao customerMockDao;

    @Test
    public void update() throws Exception{
        Customer customer = new Customer();
        customer.setUser_id(1);
        customer.setId(1);
        customer.setIs_member(1);
        customer.setReward_points(5000);
        Mockito.doNothing().when(customerMockDao).update(1,5000);
        int result = customerService.update(1,5000);
        Assertions.assertEquals(1,result);
    }

    @Test
    public void getCustomer() throws Exception{
        Customer customer = new Customer();
        customer.setUser_id(1);
        customer.setId(1);
        customer.setIs_member(1);
        customer.setReward_points(5000);
        Mockito.when(customerMockDao.getCustomer(1)).thenReturn(customer);
        Customer c = customerService.getCustomer(1);
        Assertions.assertEquals(1,c.getId());
        Assertions.assertEquals(5000,c.getReward_points());
        Assertions.assertEquals(1,c.getIs_member());
    }
}