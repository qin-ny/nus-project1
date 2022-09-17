package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.services.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService {
    @Autowired
    private CanteenDao canteenDao;

    @Override
    public int create(String name,String description,String userID){
        //check if user already exist, if do, return -1
        int cnt = canteenDao.checkCanteenExist(name) ;
        if(cnt > 0 ){
            return -1;
        }
        canteenDao.create(name, description,userID);
        return 1;
    }

    @Override
    public List<Canteen> getCanteen(String userID,String orderType){
        return canteenDao.getCanteen(userID,orderType);
    }
}