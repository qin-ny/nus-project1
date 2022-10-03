package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.OrderItem;
import nus.iss.team1.project1.services.CanteenService;
import nus.iss.team1.project1.services.CanteenTypeService;
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
    public JSONObject create(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String userID = jsonObject.getString("userID");
            JSONArray canteenTypes = jsonObject.getJSONArray("canteenTypes");

            int id = canteenService.create(name,description,userID,canteenTypes);
            if(id > 0) {
                resObject.put("resultCode",1);
                resObject.put("msg","Canteen Created");
                resObject.put("content","Canteen Created");
                System.out.println("Canteen Created");
            }
            else if(id == -1) {
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
    public JSONObject get(@RequestParam(name = "user_id", required = false) String userID,
                          @RequestParam(name = "order_type", required = false) String orderType,
                          @RequestParam(name = "keyword", required = false) String keyword) {
        JSONObject resObject = new JSONObject();
        try{
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

    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject update(@PathVariable(value = "id") List<String> idList, @RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            JSONArray canteenTypes = jsonObject.getJSONArray("canteenTypes");

            for (String id: idList) {
                int ret = canteenService.update(Integer.parseInt(id), name, description, canteenTypes);
            }
            resObject.put("resultCode",1);
            resObject.put("msg","Update Success");
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
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public JSONObject delete(@PathVariable(value = "id") List<String> idList) {
        JSONObject resObject = new JSONObject();
        try{
            for (String id: idList) {
                int ret = canteenService.delete(Integer.parseInt(id));
            }
            resObject.put("resultCode",1);
            resObject.put("msg","Delete Success");
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

            int id = canteenTypeService.create(type);
            if(id > 0) {
                resObject.put("resultCode",1);
                resObject.put("id",id);
                resObject.put("msg","Canteen Created");
                resObject.put("content","Canteen Created");
                System.out.println("Canteen Created");
            }
            else {
                resObject.put("resultCode",-1);
                resObject.put("msg","CanteenType Create Fail");
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
    @RequestMapping(value = "/type",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject getType() {
        JSONObject resObject = new JSONObject();
        try{
            List<CanteenType> list = canteenTypeService.get();
            resObject.put("resultCode",1);
            resObject.put("msg","Query Success");
            resObject.put("content",JSON.toJSON(list));
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
    @RequestMapping(value = "/type/{id}",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject updateType(@PathVariable(value = "id") List<String> idList, @RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String type = jsonObject.getString("type");

            for (String id: idList) {
                int ret = canteenTypeService.update(Integer.parseInt(id), type);
            }
            resObject.put("resultCode",1);
            resObject.put("msg","Update Success");
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
    @RequestMapping(value = "/type/{id}",method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public JSONObject deleteType(@PathVariable(value = "id") List<String> idList) {
        JSONObject resObject = new JSONObject();
        try {
            for (String id : idList) {
                int ret = canteenTypeService.delete(Integer.parseInt(id));
            }
            resObject.put("resultCode", 1);
            resObject.put("msg", "Delete Success");
        } catch (Exception e) {
            resObject.put("resultCode", -2);
            resObject.put("msg", "Internal Fail");
            resObject.put("content", e.getMessage());
            System.out.println("Internal Fail," + e.getMessage());
        }
        return resObject;
    }
}