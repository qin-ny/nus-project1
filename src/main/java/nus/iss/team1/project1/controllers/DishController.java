package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;
import nus.iss.team1.project1.services.DishService;
import nus.iss.team1.project1.services.DishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/dish") // This means URL's start with /Foodies/user
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishTypeService dishTypeService;
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
            Integer typeID = jsonObject.getInteger("type_id");
            String canteenID = jsonObject.getString("canteenID");
            String stock = jsonObject.getString("stock");

            int result = dishService.create(name,price,description,typeID,canteenID,stock);
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
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject get(@RequestParam(name = "canteen_id", required = false) String canteenID,
                              @RequestParam(name = "type_id", required = false) String typeID,
                              @RequestParam(name = "order_type", required = false) String orderType) {
        JSONObject resObject = new JSONObject();
        try{
            List<Dish> list = dishService.get(canteenID,typeID,orderType);
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
    @RequestMapping(value = "/type_group",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject getByType(@RequestParam(name = "canteen_id") String canteenID) {
        JSONObject resObject = new JSONObject();
        try{
            JSONObject content = new JSONObject();

            List<DishType> dishTypes = dishTypeService.get(canteenID, null);
            for (DishType dishType: dishTypes) {
                List<Dish> list = dishService.get(canteenID, String.valueOf(dishType.getId()), null);
                content.put(dishType.getType(), JSON.toJSON(list));

            }
            resObject.put("resultCode",1);
            resObject.put("msg","Query Success");
            resObject.put("content",content);
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
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject update(@RequestBody String json, @PathVariable("id") Integer id) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String price = jsonObject.getString("price");
            String description = jsonObject.getString("description");
            String type = jsonObject.getString("type");
            String stock = jsonObject.getString("stock");

            int retID = dishService.update(id,name,price,description,type,null,stock);
            resObject.put("resultCode",1);
            resObject.put("msg","Updated Success");
            resObject.put("id",retID);
//            resObject.put("content",JSON.toJSON(list));
            return resObject;
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
//            resObject.put("content",e.getMessage());
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }

    @ResponseBody
    @RequestMapping(value = {
            "{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")

    public JSONObject delete(@PathVariable(value = "id", required = false) List<String> idList) {
        JSONObject resObject = new JSONObject();
        try{
            for (String id: idList) {
                dishService.delete(Integer.parseInt(id));
            }
            resObject.put("resultCode",1);
            resObject.put("msg","Delete Success");
            return resObject;
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }

    @ResponseBody
    @RequestMapping(value = "/type",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject getType(@RequestParam(name = "canteen_id", required = false) String canteenID,
                          @RequestParam(name = "type", required = false) String type) {
        JSONObject resObject = new JSONObject();
        try{
            List<DishType> list = dishTypeService.get(canteenID,type);
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
    @RequestMapping(value = "/type",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject createType(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");
            Integer canteenID = jsonObject.getInteger("canteenID");

            int result = dishTypeService.create(type,canteenID);
            if(result > 0) {
                resObject.put("resultCode",1);
                resObject.put("msg","DishType Created");
                System.out.println("DishType Created");
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
    @RequestMapping(value = {
            "/type/{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public JSONObject deleteType(@PathVariable(value = "id") List<String> idList) {
        JSONObject resObject = new JSONObject();
        try{
            for (String id: idList) {
                dishTypeService.delete(Integer.parseInt(id));
            }
            resObject.put("resultCode",1);
            resObject.put("msg","Delete Success");
            return resObject;
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            System.out.println("Internal Fail,"+e.getMessage());
        }
        return resObject;
    }
}