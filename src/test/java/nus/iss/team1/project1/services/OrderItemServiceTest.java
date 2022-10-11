package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.OrderItemDao;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderItemServiceTest {


    @Autowired
    private OrderItemService orderItemService;

    @MockBean(name="orderItemDao")
    private OrderItemDao orderItemMockDao;
    @Test
    public void create() throws Exception {
        OrderItem orderItem = new OrderItem();
        Dish dish =  new Dish();
        dish.setId(1);
        dish.setName("Big Mac");
        dish.setCanteen_id(1);
        dish.setPrice(15);
        orderItem.setDish(dish);
        orderItem.setDish_id(1);
        orderItem.setFee(30);
        orderItem.setNumber(2);
        orderItem.setOrder_id(1);
        orderItem.setName("Big Mac");
        Mockito.doNothing().when(orderItemMockDao).create(orderItem);
        OrderItem o = orderItemService.create(dish.getName(),orderItem.getNumber(),orderItem.getFee(),orderItem.getOrder_id(),
                orderItem.getDish_id());
        Assertions.assertEquals("Big Mac",o.getName());
        Assertions.assertEquals(30,o.getFee());
        Assertions.assertEquals(2,o.getNumber());
    }

    @Test
    public void get() throws Exception{
        OrderItem orderItem = new OrderItem();
        Dish dish =  new Dish();
        dish.setId(1);
        dish.setName("Big Mac");
        dish.setCanteen_id(1);
        dish.setPrice(15);
        orderItem.setDish(dish);
        orderItem.setDish_id(1);
        orderItem.setFee(30);
        orderItem.setNumber(2);
        orderItem.setOrder_id(1);
        orderItem.setName("Big Mac");
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        Mockito.when(orderItemMockDao.get(1,1)).thenReturn(orderItemList);
        List<OrderItem> o = orderItemService.get(1,1);
        Assertions.assertEquals(1,o.size());
        Assertions.assertEquals("Big Mac",o.get(0).getName());
        Assertions.assertEquals(30,o.get(0).getFee());
        Assertions.assertEquals(2,o.get(0).getNumber());
    }
}