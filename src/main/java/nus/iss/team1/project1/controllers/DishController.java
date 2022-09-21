package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/dish") // This means URL's start with /Foodies/user
public class DishController {
    @Autowired
    private DishService dishService;
    //canteen create
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject create(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            double price = jsonObject.getDouble("price");
            String description = jsonObject.getString("description");
            String type = jsonObject.getString("type");
            String canteenID = jsonObject.getString("canteenID");

            int result = dishService.create(name,price,description,type,canteenID);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","Dish Created");
                resObject.put("content","Dish Created");
                System.out.println("Dish Created");
            }
            else if(result == -1) {
                resObject.put("resultCode",-1);
                resObject.put("msg","Dish Create Fail");
                resObject.put("content","Name Already Exist");
                System.out.println("Dish Create Fail，Name Already Exist");
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
    @RequestMapping(value = "/getDish",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject getDish(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String canteenID = jsonObject.getString("canteenID");
            String type = jsonObject.getString("type");
            String orderType = jsonObject.getString("orderType");

            List<Dish> list = dishService.getDish(canteenID,type,orderType);
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