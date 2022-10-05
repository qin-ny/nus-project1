package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;
import nus.iss.team1.project1.services.DishService;
import nus.iss.team1.project1.services.DishTypeService;
import nus.iss.team1.project1.utils.ResponseResult;
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
    public ResponseResult create(@RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            double price = jsonObject.getDouble("price");
            String description = jsonObject.getString("description");
            Integer typeID = jsonObject.getInteger("type_id");
            Integer canteenID = jsonObject.getInteger("canteenID");
            String stock = jsonObject.getString("stock");

            int result = dishService.create(name,price,description,typeID,canteenID,stock);
            if(result > 0) {
                return ResponseResult.success(result);
            }
            else {
                return ResponseResult.success("Name Already Exist", null);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "canteen_id", required = false) String canteenID,
                              @RequestParam(name = "type_id", required = false) String typeID,
                              @RequestParam(name = "order_type", required = false) String orderType) {
        try{
            List<Dish> list = dishService.get(canteenID,typeID,orderType);
            return ResponseResult.success(JSON.toJSON(list));
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type_group",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult getByType(@RequestParam(name = "canteen_id") String canteenID) {
        try{
            JSONObject content = new JSONObject();

            List<DishType> dishTypes = dishTypeService.get(canteenID, null);
            for (DishType dishType: dishTypes) {
                List<Dish> list = dishService.get(canteenID, String.valueOf(dishType.getId()), null);
                content.put(dishType.getType(), JSON.toJSON(list));
            }
            return ResponseResult.success(content);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult update(@RequestBody String json, @PathVariable("id") Integer id) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String price = jsonObject.getString("price");
            String description = jsonObject.getString("description");
            String type = jsonObject.getString("type");
            String stock = jsonObject.getString("stock");

            int retID = dishService.update(id,name,price,description,type,null,stock);
            return ResponseResult.success(retID);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = {
            "{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult delete(@PathVariable(value = "id", required = false) List<String> idList) {
        try{
            for (String id: idList) {
                dishService.delete(Integer.parseInt(id));
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult getType(@RequestParam(name = "canteen_id", required = false) String canteenID,
                          @RequestParam(name = "type", required = false) String type) {
        try{
            List<DishType> list = dishTypeService.get(canteenID,type);
            return ResponseResult.success(list);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult createType(@RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");
            Integer canteenID = jsonObject.getInteger("canteenID");

            int result = dishTypeService.create(type,canteenID);
            if(result > 0) {
                return ResponseResult.success(result);
            }
            else {
                return ResponseResult.error("Name Already Exist", null);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = {
            "/type/{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult deleteType(@PathVariable(value = "id") List<String> idList) {
        try{
            for (String id: idList) {
                dishTypeService.delete(Integer.parseInt(id));
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }
}