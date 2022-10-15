package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.annotation.token.Token;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;
import nus.iss.team1.project1.services.DishService;
import nus.iss.team1.project1.services.DishTypeService;
import nus.iss.team1.project1.utils.ResponseResult;
import nus.iss.team1.project1.utils.file.FileNameUtils;
import nus.iss.team1.project1.utils.file.FileUtils;
import nus.iss.team1.project1.utils.file.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/dish") // This means URL's start with /Foodies/user
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishTypeService dishTypeService;

    @Token
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
            Integer stock = jsonObject.getInteger("stock");

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

    @Token
    @ResponseBody
    @RequestMapping(value = "/image",method = RequestMethod.POST)
    public ResponseResult uploadImage(@RequestParam("image") MultipartFile file,
                                      @RequestParam("canteen_id") String canteenID,
                                      @RequestParam("dish_id") String dishID) {
        try{
            ImageUtils imageUtils = new ImageUtils(canteenID, dishID);
            if(!imageUtils.validateFormat(file)) {
                return ResponseResult.error("invalid image format", null);
            }
            imageUtils.upload(file);
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "/image",method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@RequestParam("canteen_id") String canteenID,
                                             @RequestParam("dish_id") String dishID) {
        try{
            ImageUtils imageUtils = new ImageUtils(canteenID, dishID);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageUtils.getImage());
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "canteen_id", required = false) String canteenID,
                              @RequestParam(name = "type_id", required = false) Integer typeID,
                              @RequestParam(name = "order_type", required = false) String orderType) {
        try{
            List<Dish> list = dishService.get(canteenID,typeID,orderType);
            return ResponseResult.success(JSON.toJSON(list));
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "/type_group",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult getByType(@RequestParam(name = "canteen_id") String canteenID) {
        try{
            ArrayList<JSONObject> content = new ArrayList<>();

            List<DishType> dishTypes = dishTypeService.get(canteenID, null);
            for (DishType dishType: dishTypes) {
                JSONObject typeContent = new JSONObject();
                List<Dish> list = dishService.get(canteenID, dishType.getId(), null);
                typeContent.put("type", dishType.getType());
                typeContent.put("id", dishType.getId());
                typeContent.put("dishes", list);
                content.add(typeContent);
            }
            return ResponseResult.success(content);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult update(@RequestBody String json, @PathVariable("id") Integer id) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String price = jsonObject.getString("price");
            String description = jsonObject.getString("description");
            Integer typeID = jsonObject.getInteger("type_id");
            Integer stock = jsonObject.getInteger("stock");
            Integer availability = null;
            if(jsonObject.containsKey("availability")) {
                availability = jsonObject.getBoolean("availability") ? 1:0;
            }


            int retID = dishService.update(id,name,price,description,typeID,null,stock,availability);
            return ResponseResult.success(retID);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = {
            "{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult delete(@PathVariable(value = "id", required = false) List<String> idList) {
        try{
            for (String id: idList) {
                Dish dish = dishService.getDishByID(Integer.parseInt(id));
                ImageUtils imageUtils = new ImageUtils(String.valueOf(dish.getCanteen_id()), String.valueOf(dish.getId()));
                dishService.delete(Integer.parseInt(id));
                imageUtils.delete();
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
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

    @Token
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

    @Token
    @ResponseBody
    @RequestMapping(value = {
            "/type/{id}"
    },method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult deleteType(@PathVariable(value = "id") List<Integer> idList) {
        try{
            for (Integer id: idList) {
                List<Dish> dishList = dishService.get(null, id, null);
                for (Dish dish: dishList) {
                    ImageUtils imageUtils = new ImageUtils(String.valueOf(dish.getCanteen_id()), String.valueOf(dish.getId()));
                    imageUtils.delete();
                }
                dishTypeService.delete(id);
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "/type/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult updateType(@RequestBody String json, @PathVariable("id") Integer id) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");

            int retID = dishTypeService.update(id,type);
            return ResponseResult.success(retID);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }
}