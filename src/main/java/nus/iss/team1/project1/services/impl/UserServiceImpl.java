package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nus.iss.team1.project1.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDAO;

    @Override
    public int validate(String userName,String password,String type){
        int result = userDAO.validate(userName, password,type);
        return result;
    }

    @Override
    public int create(String userName,String password,String type){
        //check if userName already exist, if do, return -1
        int cnt = userDAO.checkExist(userName);
        if(cnt > 0){
            return -1;
        }
        userDAO.create(userName, password,type);
        return 1;
    }
}
