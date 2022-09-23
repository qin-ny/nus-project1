package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.User;
import nus.iss.team1.project1.services.UserService;
import nus.iss.team1.project1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

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

            User user = userService.validate(userName, password,type);
            if(null != user) {
                String token = JwtUtil.getoken(user);
                resObject.put("resultCode",1);
                resObject.put("msg","Login Success");
                resObject.put("content",user);
                resObject.put("token",token);
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
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject create(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            String name = jsonObject.getString("name");
            String gender = jsonObject.getString("gender");
            String phone = jsonObject.getString("phone");
            String email = jsonObject.getString("email");
            String NRIC = jsonObject.getString("NRIC");
            String type = jsonObject.getString("type");

            int result = userService.create(userName, password,name,gender,phone,email,NRIC,type);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","User Created");
                resObject.put("content","User Created");
                System.out.println("User Created");
            }
            else if(result == -1) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Create Fail");
                resObject.put("content","UserName Already Exist");
                System.out.println("User Create Fail，UserName Already Exist");
            }
            else if(result == -2) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Create Fail");
                resObject.put("content","Phone Already Exist");
                System.out.println("User Create Fail，Phone Already Exist");
            }
            else if(result == -3) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Create Fail");
                resObject.put("content","NRIC/FIN Already Exist");
                System.out.println("User Create Fail，NRIC/FIN Already Exist");
            }
            else if(result == -4) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Create Fail");
                resObject.put("content","Email Already Exist");
                System.out.println("User Create Fail，Email Already Exist");
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


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject getUser(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            String type = jsonObject.getString("type");

            List<User> list = userService.getUser(userName, password,type);
            resObject.put("resultCode",1);
            resObject.put("msg","Query Success");
            resObject.put("content",JSON.toJSON(list));
            return resObject;
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            resObject.put("content",e.getMessage());
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject modifyUser(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String gender = jsonObject.getString("gender");
            String phone = jsonObject.getString("phone");
            String email = jsonObject.getString("email");
            String NRIC = jsonObject.getString("NRIC");
            String type = jsonObject.getString("type");

            int result = userService.modifyUser(userName,gender,phone,email,NRIC,type);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","User Modify Success");
                resObject.put("content","User Modify Success");
                System.out.println("User Modify Success");
            }
            else if(result == -1) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Modify Fail");
                resObject.put("content","UserName Already Exist");
                System.out.println("User Create Fail，UserName Already Exist");
            }
            else if(result == -2) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Modify Fail");
                resObject.put("content","Phone Already Exist");
                System.out.println("User Modify Fail，Phone Already Exist");
            }
            else if(result == -3) {
                resObject.put("resultCode",-1);
                resObject.put("msg","User Modify Fail");
                resObject.put("content","Email Already Exist");
                System.out.println("User Modify Fail，Email Already Exist");
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

    @ResponseBody
    @RequestMapping(value = "/modifyPassword",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject modifyPassword(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            int result = userService.modifyPassword(userName,password);
            resObject.put("resultCode",1);
            resObject.put("msg","Password Modify Success");
            resObject.put("content","Password Modify Success");
            System.out.println("Password Modify Success");
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