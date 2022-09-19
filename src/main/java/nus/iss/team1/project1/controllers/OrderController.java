package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Order;
import nus.iss.team1.project1.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/order") // This means URL's start with /Foodies/user
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject createOrder(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderTime = jsonObject.getString("orderTime");
            double totalFee = jsonObject.getDouble("totalFee");
            Integer canteenID = jsonObject.getInteger("canteenID");
            Integer userID = jsonObject.getInteger("userID");
            Integer status = jsonObject.getInteger("status");

            int result = orderService.create(orderTime,totalFee,status,canteenID,userID);
            if(result == 1) {
                resObject.put("resultCode",1);
                resObject.put("msg","Create Order Success");
                resObject.put("content","Create Order Success");
                System.out.println("Create Order Success");
            }
            else {
                resObject.put("resultCode",-1);
                resObject.put("msg","Create Order Fail");
                resObject.put("content","Create Order Error");
                System.out.println("Create Order Fail");
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
    public JSONObject getOrder(@RequestParam(name = "canteen-id", required = false) String canteenID,
                               @RequestParam(name = "user-id", required = false) String userID,
                               @RequestParam(name = "status", required = false) String status,
                               @RequestParam(name = "order-type", required = false) String orderType) {
        JSONObject resObject = new JSONObject();
        try{
//            String canteenID = jsonObject.getString("canteenID");
//            String userID = jsonObject.getString("userID");
//            Integer status = jsonObject.getInteger("status");
//            String orderType = jsonObject.getString("orderType");

            List<Order> list = orderService.getOrder(canteenID,userID,status,orderType);
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
    @RequestMapping(value = "/status",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public JSONObject updateStatus(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderID = jsonObject.getString("orderID");
            String status = jsonObject.getString("status");

            int result = orderService.updateStatus(orderID,status);
            resObject.put("resultCode",1);
            resObject.put("msg","Order Status Modified");
            resObject.put("content","Order Status Modified");
            System.out.println("Order Status Modified");
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