package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.services.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/canteen") // This means URL's start with /Foodies/user
public class CanteenController {
    @Autowired
    private CanteenService canteenService;
    //canteen create
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject create(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String userID = jsonObject.getString("userID");

            int result = canteenService.create(name,description,userID);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","Canteen Created");
                resObject.put("content","Canteen Created");
                System.out.println("Canteen Created");
            }
            else if(result == -1) {
                resObject.put("resultCode",-1);
                resObject.put("msg","Canteen Create Fail");
                resObject.put("content","Name Already Exist");
                System.out.println("Canteen Create Fail，Name Already Exist");
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
    public JSONObject get(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String userID = jsonObject.getString("userID");
            String orderType = jsonObject.getString("orderType");
            String keyword = jsonObject.getString("keyword");

            List<Canteen> list = canteenService.get(userID,orderType,keyword);
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


}