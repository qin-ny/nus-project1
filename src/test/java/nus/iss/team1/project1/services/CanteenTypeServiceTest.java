package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.CanteenTypeDao;
import nus.iss.team1.project1.models.CanteenType;
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
class CanteenTypeServiceTest {
    @Autowired
    private CanteenTypeService canteenTypeService;

    @MockBean(name="canteenTypeDao")
    private CanteenTypeDao canteenTypeMockDao;

    @Test
    public void get() throws Exception {
        CanteenType  canteenType1 = new CanteenType();
        canteenType1.setType("Western");
        canteenType1.setId(1);
        CanteenType canteenType2 = new CanteenType();
        canteenType2.setType("Chinese");
        canteenType2.setId(2);
        CanteenType canteenType3 = new CanteenType();
        canteenType3.setType("Japanese");
        canteenType3.setId(3);
        List<CanteenType> types =  new ArrayList<>();
        types.add(canteenType1);
        types.add(canteenType2);
        types.add(canteenType3);
        Mockito.when(canteenTypeMockDao.get()).thenReturn(types);
        List<CanteenType> c = canteenTypeService.get();
        Assertions.assertEquals(3,c.size());
        Assertions.assertEquals("Western",c.get(0).getType());
        Assertions.assertEquals("Chinese",c.get(1).getType());
        Assertions.assertEquals("Japanese",c.get(2).getType());
    }

    @Test
    public void update() throws Exception {
        CanteenType  canteenType1 = new CanteenType();
        canteenType1.setType("WesternFood");
        canteenType1.setId(1);
        Mockito.doNothing().when(canteenTypeMockDao).update(1,"WesternFood");
        int result = canteenTypeService.update(1,"WesternFood");
        Assertions.assertEquals(1,result);
    }

    @Test
    public void delete() throws Exception{
        CanteenType  canteenType1 = new CanteenType();
        canteenType1.setType("WesternFood");
        canteenType1.setId(1);
        Mockito.doNothing().when(canteenTypeMockDao).delete(1);
        int result = canteenTypeService.delete(1);
        Assertions.assertEquals(1,result);
    }
}