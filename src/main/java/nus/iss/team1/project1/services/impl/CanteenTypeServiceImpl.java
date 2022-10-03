package nus.iss.team1.project1.services.impl;


import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.dao.CanteenTypeDao;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.services.CanteenTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanteenTypeServiceImpl implements CanteenTypeService {
    @Autowired
    private CanteenTypeDao canteenTypeDao;

    @Override
    public int create(String type){
        int cnt = canteenTypeDao.checkCanteenTypeExist(type) ;
        if(cnt > 0 ){
            return -1;
        }

        CanteenType canteenType = new CanteenType();
        canteenType.setType(type);

        canteenTypeDao.create(canteenType);
        return canteenType.getId();
    }

    @Override
    public List<CanteenType> get(){
        return canteenTypeDao.get();
    }

    @Override
    public int update(Integer id, String type) {
        canteenTypeDao.update(id, type);
        return id;
    }

    @Override
    public int delete(Integer id) {
        canteenTypeDao.delete(id);
        return id;
    }
}
