package nus.iss.team1.project1.services;

import com.alibaba.fastjson.JSONArray;
import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.dao.CanteenTypeDao;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.CanteenTypeCanteen;
import nus.iss.team1.project1.services.CanteenService;
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

import static org.junit.jupiter.api.Assertions.*;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CanteenServiceTest {

    @Autowired
    private CanteenService canteenService;

    @MockBean(name="canteenDao")
    private CanteenDao canteenMockDAO;
    @MockBean(name="canteenTypeDao")
    private CanteenTypeDao canteenTypeMockDAO;


    @Test
    void get() {
        Canteen canteen1 = new Canteen();
        canteen1.setName("Hong Kong Canteen");
        canteen1.setId(1);
        canteen1.setUser_id(1);
        Canteen canteen2 = new Canteen();
        canteen2.setName("Guang Zhou Canteen");
        canteen2.setId(1);
        canteen2.setUser_id(1);
        Canteen canteen3 = new Canteen();
        canteen3.setName("Si Chuan Canteen");
        canteen3.setId(1);
        canteen3.setUser_id(1);
        List<Canteen> canteens =  new ArrayList<>();
        canteens.add(canteen1);
        canteens.add(canteen2);
        canteens.add(canteen3);
        Mockito.when(canteenMockDAO.get("1","1", "%Canteen%")).thenReturn(canteens);
        List<Canteen> c = canteenService.get("1","1", "Canteen");
        Assertions.assertEquals(3,c.size());
        Assertions.assertEquals("Hong Kong Canteen",c.get(0).getName());
        Assertions.assertEquals("Guang Zhou Canteen",c.get(1).getName());
        Assertions.assertEquals("Si Chuan Canteen",c.get(2).getName());
        Assertions.assertEquals(1,c.get(2).getUser_id());
    }

    @Test
    void update() {
        Canteen canteen1 = new Canteen();
        canteen1.setName("Hong Kong Little Canteen");
        canteen1.setId(1);
        canteen1.setUser_id(1);
        List<CanteenType> canteentypes =  new ArrayList<>();
        CanteenType canteenType = new CanteenType();
        canteenType.setId(1);
        canteenType.setType("Chinese");
        canteentypes.add(canteenType);
        canteen1.setCanteenTypes(canteentypes);
        canteen1.setOrderNums(30);
        canteen1.setStar(3.5);
        Mockito.when(canteenMockDAO.getByID(1)).thenReturn(canteen1);
        Mockito.doNothing().when(canteenTypeMockDAO).deleteCanteenTypeCanteen(1,1);
        CanteenTypeCanteen canteenTypeCanteen = new CanteenTypeCanteen();
        canteenTypeCanteen.setCanteen_id(1);
        canteenTypeCanteen.setCanteen_type_id(1);
        Mockito.doNothing().when(canteenTypeMockDAO).createCanteenCanteenType(canteenTypeCanteen);
        Mockito.doNothing().when(canteenMockDAO).update(1,"Hong Kong Little Canteen","");
        JSONArray newCanteenType = new JSONArray();
        newCanteenType.add(canteenType);
        int result = canteenService.update(1,"Hong Kong Little Canteen","",newCanteenType);
        Assertions.assertEquals(1,result);
    }

    @Test
    void delete() {
        Canteen canteen1 = new Canteen();
        canteen1.setName("Hong Kong Little Canteen");
        canteen1.setId(1);
        canteen1.setUser_id(1);
        Mockito.doNothing().when(canteenMockDAO).delete(1);
        int result = canteenService.delete(1);
        Assertions.assertEquals(1,result);
    }
}