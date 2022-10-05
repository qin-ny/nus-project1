package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.User;
import nus.iss.team1.project1.services.UserService;
import nus.iss.team1.project1.utils.JwtUtil;
import nus.iss.team1.project1.utils.ResponseResult;
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
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult login(@RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            String type = jsonObject.getString("type");

            User user = userService.validate(userName, password,type);
            if(null != user) {
                String token = JwtUtil.getoken(user);
                JSONObject resObject = new JSONObject();
                resObject.put("content",user);
                resObject.put("token",token);
                return ResponseResult.success(resObject);
            }
            else {
                return ResponseResult.error("Username or Password Error", null);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    //user create
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult create(@RequestBody String json) {
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
            switch (result) {
                case -1:
                    return ResponseResult.error("UserName Already Exist", null);
                case -2:
                    return ResponseResult.error("Phone Already Exist", null);
                case -3:
                    return ResponseResult.error("NRIC/FIN Already Exist", null);
                case -4:
                    return ResponseResult.error("Email Already Exist", null);
                default:
                    return ResponseResult.success(result);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "user_name", required = false) String userName,
                          @RequestParam(name = "gender", required = false) String gender,
                          @RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "type", required = false) String type) {
        JSONObject resObject = new JSONObject();
        try{
            List<User> list = userService.get(userName, name, gender, type);
            return ResponseResult.success(JSON.toJSON(list));
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult modifyUser(@RequestBody String json) {
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
            switch (result) {
                case -1:
                    return ResponseResult.error("UserName Already Exist", null);
                case -2:
                    return ResponseResult.error("Phone Already Exist", null);
                case -3:
                    return ResponseResult.error("Email Already Exist", null);
                default:
                    return ResponseResult.success(result);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/modifyPassword",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult modifyPassword(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            int result = userService.modifyPassword(userName,password);
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }
}