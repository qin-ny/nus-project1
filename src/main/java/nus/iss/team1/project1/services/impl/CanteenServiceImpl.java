package nus.iss.team1.project1.services.impl;

import com.alibaba.fastjson.JSONArray;
import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.dao.CanteenTypeDao;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.CanteenTypeCanteen;
import nus.iss.team1.project1.services.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService {
    @Autowired
    private CanteenDao canteenDao;

    @Autowired
    private CanteenTypeDao canteenTypeDao;

    @Override
    public int create(String name,String description,String userID, JSONArray canteenTypes){
        //check if user already exist, if do, return -1
        int cnt = canteenDao.checkCanteenExist(name) ;
        if(cnt > 0 ){
            return -1;
        }
        Canteen canteen = new Canteen();
        canteen.setDescription(description);
        canteen.setName(name);
        canteen.setUser_id(Integer.parseInt(userID));
        canteenDao.create(canteen);

        try {
            for (Object canteenTypeID: canteenTypes.toArray()) {
                CanteenTypeCanteen canteenTypeCanteen = new CanteenTypeCanteen();
                canteenTypeCanteen.setCanteen_id(canteen.getId());
                canteenTypeCanteen.setCanteen_type_id(Integer.parseInt(canteenTypeID.toString()));
                canteenTypeDao.createCanteenCanteenType(canteenTypeCanteen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return canteen.getId();
    }

    @Override
    public List<Canteen> get(String userID,String orderType, String keyword, String type){
        if(keyword != null) {
            keyword = "%" + keyword + "%";
        }
        return canteenDao.get(userID,orderType,keyword,type);
    }

    @Override
    public int update(Integer id, String name, String description, JSONArray canteenTypes) {
        List<CanteenType> delCanteenTypes = canteenTypeDao.getByCanteenID(id);
        for (CanteenType delCanteenType: delCanteenTypes) {
            canteenTypeDao.deleteCanteenTypeCanteen(id, delCanteenType.getId());
        }

        try {
            for (Object canteenTypeID: canteenTypes.toArray()) {
                CanteenTypeCanteen canteenTypeCanteen = new CanteenTypeCanteen();
                canteenTypeCanteen.setCanteen_id(id);
                canteenTypeCanteen.setCanteen_type_id(Integer.parseInt(canteenTypeID.toString()));
                canteenTypeDao.createCanteenCanteenType(canteenTypeCanteen);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        canteenDao.update(id, name, description);
        return id;
    }

    @Override
    public int delete(Integer id) {
        canteenDao.delete(id);
        return id;
    }

}