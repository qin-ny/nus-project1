package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.DishDao;
import nus.iss.team1.project1.models.Dish;
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
class DishServiceTest {
    @Autowired
    private DishService dishService;

    @MockBean(name="dishDao")
    private DishDao dishMockDao;

    @Test
    public void get_ByAscPrice() throws Exception {
        Dish dish1 = new Dish();
        dish1.setName("Big Mac");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        Dish dish2 = new Dish();
        dish2.setName("FishBurger");
        dish2.setPrice(13);
        dish2.setCanteen_id(1);
        dish2.setDish_type_id(1);
        List<Dish> dishes =  new ArrayList<>();
        dishes.add(dish2);
        dishes.add(dish1);
        Mockito.when(dishMockDao.getDishOrderByPrice("1",1,"asc")).thenReturn(dishes);
        List<Dish> d =  new ArrayList<>();
        d = dishService.get("1",1,"1");
        Assertions.assertEquals(2,d.size());
        Assertions.assertEquals(13,d.get(0).getPrice());
        Assertions.assertEquals(15,d.get(1).getPrice());
    }

    @Test
    public void get_ByDescPrice() throws Exception {
        Dish dish1 = new Dish();
        dish1.setName("Big Mac");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        Dish dish2 = new Dish();
        dish2.setName("FishBurger");
        dish2.setPrice(13);
        dish2.setCanteen_id(1);
        dish2.setDish_type_id(1);
        List<Dish> dishes =  new ArrayList<>();
        dishes.add(dish1);
        dishes.add(dish2);
        Mockito.when(dishMockDao.getDishOrderByPrice("1",1,"desc")).thenReturn(dishes);
        List<Dish> d =  new ArrayList<>();
        d = dishService.get("1",1,"2");
        Assertions.assertEquals(2,d.size());
        Assertions.assertEquals(15,d.get(0).getPrice());
        Assertions.assertEquals(13,d.get(1).getPrice());
    }

    @Test
    public void get_BySalesDesc() throws Exception {
        Dish dish1 = new Dish();
        dish1.setName("Big Mac");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        dish1.setSales_num_thirty(20);
        Dish dish2 = new Dish();
        dish2.setName("FishBurger");
        dish2.setPrice(13);
        dish2.setCanteen_id(1);
        dish2.setDish_type_id(1);
        dish2.setSales_num_thirty(30);
        List<Dish> dishes =  new ArrayList<>();
        dishes.add(dish2);
        dishes.add(dish1);
        Mockito.when(dishMockDao.getDishOrderBySales("1",1)).thenReturn(dishes);
        List<Dish> d =  new ArrayList<>();
        d = dishService.get("1",1,"3");
        Assertions.assertEquals(2,d.size());
        Assertions.assertEquals(30,d.get(0).getSales_num_thirty());
        Assertions.assertEquals(20,d.get(1).getSales_num_thirty());
    }

    @Test
    public void getDishByID() throws Exception{
        Dish dish1 = new Dish();
        dish1.setName("Big Mac");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        dish1.setSales_num_thirty(20);
        dish1.setId(1);
        Mockito.when(dishMockDao.getDishByID(1)).thenReturn(dish1);
        Dish d = dishService.getDishByID(1);
        Assertions.assertEquals(1,d.getId());
        Assertions.assertEquals("Big Mac",d.getName());
        Assertions.assertEquals(15,d.getPrice());
    }

    @Test
    public void update() throws Exception{
        Dish dish1 = new Dish();
        dish1.setName("Ultra Mac");
        dish1.setDescription("Double Ham");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        dish1.setStock(50);
        dish1.setSales_num_thirty(20);
        dish1.setId(1);
        Mockito.doNothing().when(dishMockDao).update(1,"Ultra Mac",15,"Double Ham",
                1,20,50, 0);
        int result = dishService.update(1,"Ultra Mac","15","Double Ham",
                1,20,50, 1);
        Assertions.assertEquals(1,result);
    }

    @Test
    public void delete() throws Exception{
        Dish dish1 = new Dish();
        dish1.setName("Ultra Mac");
        dish1.setDescription("Double Ham");
        dish1.setPrice(15);
        dish1.setCanteen_id(1);
        dish1.setDish_type_id(1);
        dish1.setStock(50);
        dish1.setSales_num_thirty(20);
        dish1.setId(1);
        Mockito.doNothing().when(dishMockDao).delete(1);
        int result = dishService.delete(1);
        Assertions.assertEquals(1,result);
    }
}