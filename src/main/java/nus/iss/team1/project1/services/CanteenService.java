package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Canteen;
import java.util.List;

public interface CanteenService {
    public int create(String name, String description, String userID);
    public List<Canteen> get(String userID,String orderType,String keyword);
    public int update(Integer id, String name, String description);
//    public int updateStar(String id, String star。。。。);
}