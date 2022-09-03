package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/user") // This means URL's start with /Foodies/user
public class UserController {
    @Autowired
    private UserService userService;

    //user login
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject login(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            String type = jsonObject.getString("type");

            int result = userService.validate(userName, password,type);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","Login Success");
                resObject.put("content","Login Success");
                System.out.println("Login Success");
            }
            else {
                resObject.put("resultCode",-1);
                resObject.put("msg","Login Fail");
                resObject.put("content","Username or Password Error");
                System.out.println("Login Fail，Username or Password Error");
            }
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            resObject.put("content",e.getMessage());
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }

    //user create
    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject create(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            String type = jsonObject.getString("type");

            int result = userService.create(userName, password,type);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","User Created");
                resObject.put("content","User Created");
                System.out.println("User Created");
            }
            else {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Create Fail");
                resObject.put("content","User Already Exist");
                System.out.println("User Create Fail，User Already Exist");
            }
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            resObject.put("content",e.getMessage());
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }


}
