package nus.iss.team1.project1.services;

import com.alibaba.fastjson.JSONArray;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;

import java.util.List;

public interface CanteenService {
    public int create(String name, String description, String userID, JSONArray canteenTypes);
    public List<Canteen> get(String userID,String orderType,String keyword);
    public int update(Integer id, String name, String description, JSONArray canteenTypes);
    public int delete(Integer id);
//    public int updateStar(String id, String star。。。。);
}