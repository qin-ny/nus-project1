package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.OrderItem;
import nus.iss.team1.project1.services.CanteenService;
import nus.iss.team1.project1.services.CanteenTypeService;
import nus.iss.team1.project1.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/canteen") // This means URL's start with /Foodies/user
public class CanteenController {
    @Autowired
    private CanteenService canteenService;

    @Autowired
    private CanteenTypeService canteenTypeService;

    //canteen create
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult create(@RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String userID = jsonObject.getString("userID");
            JSONArray canteenTypes = jsonObject.getJSONArray("canteenTypes");

            int id = canteenService.create(name,description,userID,canteenTypes);
            if(id > 0) {

                return ResponseResult.success();
            }
            else {
                return ResponseResult.error("Name Already Exist", null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "user_id", required = false) String userID,
                          @RequestParam(name = "order_type", required = false) String orderType,
                          @RequestParam(name = "keyword", required = false) String keyword) {
        try{
            List<Canteen> list = canteenService.get(userID,orderType,keyword);
            return ResponseResult.success(list);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult update(@PathVariable(value = "id") List<String> idList, @RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            JSONArray canteenTypes = jsonObject.getJSONArray("canteenTypes");

            for (String id: idList) {
                int ret = canteenService.update(Integer.parseInt(id), name, description, canteenTypes);
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult delete(@PathVariable(value = "id") List<String> idList) {
        try{
            for (String id: idList) {
                int ret = canteenService.delete(Integer.parseInt(id));
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult createType(@RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");

            int id = canteenTypeService.create(type);
            if(id > 0) {
                return ResponseResult.success(id);
            }
            else {
                return ResponseResult.error("Name Already Exist", null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult getType() {
        try{
            List<CanteenType> list = canteenTypeService.get();
            return ResponseResult.success(list);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult updateType(@PathVariable(value = "id") List<String> idList, @RequestBody String json) {
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");

            for (String id: idList) {
                int ret = canteenTypeService.update(Integer.parseInt(id), type);
            }
            return ResponseResult.success();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Internal Fail,"+e.getMessage());
            return ResponseResult.internalError();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/type/{id}",method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public ResponseResult deleteType(@PathVariable(value = "id") List<String> idList) {
        try {
            for (String id : idList) {
                int ret = canteenTypeService.delete(Integer.parseInt(id));
            }
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Internal Fail," + e.getMessage());
            return ResponseResult.internalError();
        }
    }
}