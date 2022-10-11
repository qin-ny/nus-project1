package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.DishTypeDao;
import nus.iss.team1.project1.models.DishType;
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
class DishTypeServiceTest {
    @Autowired
    private DishTypeService dishTypeService;

    @MockBean(name="dishTypeDao")
    private DishTypeDao dishTypeMockDao;

    @Test
    public void get() throws Exception{
        DishType dishType  = new DishType();
        dishType.setCanteen_id(1);
        dishType.setType("Dessert");
        dishType.setId(1);
        List<DishType> types =  new ArrayList<>();
        types.add(dishType);
        Mockito.when(dishTypeMockDao.get(-1,"1","Dessert")).thenReturn(types);
        List<DishType> d = dishTypeService.get("1","Dessert");
        Assertions.assertEquals(1,d.size());
        Assertions.assertEquals("Dessert",d.get(0).getType());
    }

    @Test
    public void update() throws Exception{
        DishType dishType = new DishType();
        dishType.setType("Burger");
        dishType.setId(1);
        Mockito.doNothing().when(dishTypeMockDao).update(1,"Burger");
        int result = dishTypeService.update(1,"Burger");
        Assertions.assertEquals(1,result);
    }

    @Test
    public void delete() throws Exception {
        DishType dishType = new DishType();
        dishType.setType("Burger");
        dishType.setId(1);
        Mockito.doNothing().when(dishTypeMockDao).delete(1);
        int result = dishTypeService.delete(1);
        Assertions.assertEquals(1,result);
    }
}