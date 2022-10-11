package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.dao.OrderDao;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.Order;
import nus.iss.team1.project1.models.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean(name="orderDao")
    private OrderDao orderMockDao;
    @MockBean(name="canteenDao")
    private CanteenDao canteenMockDao;
    @Test
    public void create() throws Exception{
        Order order = new Order();
        Canteen canteen = new Canteen();
        canteen.setId(1);
        canteen.setStar(3.5);
        canteen.setOrderNums(450);
        canteen.setName("McDonald's");
        order.setCanteen(canteen);
        order.setOrder_time("2022-10-11 13:00:00");
        order.setStatus(0);
        order.setUser_id(1);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =  new OrderItem();
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
        orderItemList.add(orderItem);
        order.setOrderItems(orderItemList);
        order.setTotal_fee(30);
        Mockito.when(canteenMockDao.getByID(1)).thenReturn(canteen);
        Mockito.doNothing().when(orderMockDao).create(order);
        Order o = orderService.create(order.getOrder_time(), order.getTotal_fee(), order.getStatus(),
                order.getCanteen().getId(), order.getUser_id());
        Assertions.assertEquals("McDonald's",o.getCanteen().getName());
        Assertions.assertEquals("2022-10-11 13:00:00",o.getOrder_time());
        Assertions.assertEquals(450,o.getCanteen().getOrderNums());
        Assertions.assertEquals(30,o.getTotal_fee());
        Assertions.assertEquals(1,o.getUser_id());
    }

    @Test
    public void get_ByOrderTimeAsc() throws Exception {
        Order order = new Order();
        Canteen canteen = new Canteen();
        canteen.setId(1);
        canteen.setStar(3.5);
        canteen.setOrderNums(450);
        canteen.setName("McDonald's");
        order.setCanteen(canteen);
        order.setOrder_time("2022-10-11 13:00:00");
        order.setStatus(0);
        order.setUser_id(1);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =  new OrderItem();
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
        orderItemList.add(orderItem);
        order.setOrderItems(orderItemList);
        order.setTotal_fee(30);
        Order order1 = new Order();
        order1.setCanteen(canteen);
        order1.setOrder_time("2022-10-11 14:00:00");
        order1.setStatus(0);
        order1.setUser_id(1);
        order1.setOrderItems(orderItemList);
        order1.setTotal_fee(30);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        Mockito.when(orderMockDao.get("1","1","0","asc")).thenReturn(orders);
        List<Order> o = orderService.get("1","1","0","1");
        Assertions.assertEquals("McDonald's",o.get(0).getCanteen().getName());
        Assertions.assertEquals("2022-10-11 13:00:00",o.get(0).getOrder_time());
        Assertions.assertEquals("2022-10-11 14:00:00",o.get(1).getOrder_time());
    }
    @Test
    public void get_ByOrderTimeDesc() throws Exception {
        Order order = new Order();
        Canteen canteen = new Canteen();
        canteen.setId(1);
        canteen.setStar(3.5);
        canteen.setOrderNums(450);
        canteen.setName("McDonald's");
        order.setCanteen(canteen);
        order.setOrder_time("2022-10-11 13:00:00");
        order.setStatus(0);
        order.setUser_id(1);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =  new OrderItem();
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
        orderItemList.add(orderItem);
        order.setOrderItems(orderItemList);
        order.setTotal_fee(30);
        Order order1 = new Order();
        order1.setCanteen(canteen);
        order1.setOrder_time("2022-10-11 14:00:00");
        order1.setStatus(0);
        order1.setUser_id(1);
        order1.setOrderItems(orderItemList);
        order1.setTotal_fee(30);
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order);
        Mockito.when(orderMockDao.get("1","1","0","desc")).thenReturn(orders);
        List<Order> o = orderService.get("1","1","0","2");
        Assertions.assertEquals("McDonald's",o.get(0).getCanteen().getName());
        Assertions.assertEquals("2022-10-11 14:00:00",o.get(0).getOrder_time());
        Assertions.assertEquals("2022-10-11 13:00:00",o.get(1).getOrder_time());
    }

    @Test
    void updateStatus() {
        Order order = new Order();
        order.setOrder_time("2022-10-11 13:00:00");
        order.setStatus(1);
        order.setId(1);
        Mockito.doNothing().when(orderMockDao).updateStatus("1","1");
        int result = orderService.updateStatus("1","1");
        Assertions.assertEquals(1,result);
    }
}