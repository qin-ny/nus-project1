package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.models.User;
import nus.iss.team1.project1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nus.iss.team1.project1.dao.UserDao;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDAO;

    @Override
    public User validate(String userName,String password,String type){
        User user = userDAO.validate(userName, password,type);
        return user;
    }

    @Override
    public int create(String userName,String password,String name,String gender,String phone, String email,String NRIC,String type){
        //check if user already exist, if do, return -1
        int userNamecnt = userDAO.checkUserNameExist(userName);
        if(userNamecnt > 0){
            return -1;
        }
        int phonecnt = userDAO.checkPhoneExist(phone,type);
        if(phonecnt > 0){
            return -2;
        }
        int NRICcnt = userDAO.checkNRICExist(NRIC,type);
        if(NRICcnt > 0){
            return -3;
        }
        int emailcnt = userDAO.checkEmailExist(email,type);
        if(emailcnt > 0){
            return -4;
        }
        userDAO.create(userName, password,name,gender,phone,email,NRIC,type);
        return 1;
    }

    @Override
    public List<User> get(String userName, String name, String gender, String type){
        return userDAO.get(userName,name,gender,type);
    }

    @Override
    public int modifyUser(String userName,String gender,String phone,String email,String NRIC,String type){
        //check if user already exist, if do, return -1
        int userNamecnt = userDAO.checkUserNameExistModify(userName,NRIC);
        if(userNamecnt > 0){
            return -1;
        }
        int phonecnt = userDAO.checkPhoneExistModify(phone,type,NRIC);
        if(phonecnt > 0){
            return -2;
        }
        int emailcnt = userDAO.checkEmailExistModify(email,type,NRIC);
        if(emailcnt > 0){
            return -3;
        }
        userDAO.modify(userName, gender,phone,email,NRIC,type);
        return 1;
    }

    @Override
    public int modifyPassword(String userName,String password){
        userDAO.modifyPassword(userName,password);
        return 1;
    }
}