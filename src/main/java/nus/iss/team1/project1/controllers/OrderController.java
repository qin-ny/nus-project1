package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.annotation.token.Token;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.Order;
import nus.iss.team1.project1.models.OrderItem;
import nus.iss.team1.project1.services.CustomerService;
import nus.iss.team1.project1.services.DishService;
import nus.iss.team1.project1.services.OrderItemService;
import nus.iss.team1.project1.services.OrderService;
import nus.iss.team1.project1.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/Foodies/order") // This means URL's start with /Foodies/user
public class OrderController {

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CustomerService customerService;
    @Token
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult create(@RequestBody String json) {
        Order order = null;
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderTime = jsonObject.getString("orderTime");
            double totalFee = jsonObject.getDouble("totalFee");
            Integer canteenID = jsonObject.getInteger("canteenID");
            Integer userID = jsonObject.getInteger("userID");
            Integer status = jsonObject.getInteger("status");
            String strOrderItems = jsonObject.getString("orderItems");
            List<OrderItem> orderItems = JSON.parseArray(strOrderItems, OrderItem.class);

            order = orderService.create(orderTime,totalFee,status,canteenID,userID);

            try {
                for (OrderItem orderItem: orderItems) {
                    OrderItem savedOrderItem = orderItemService.create(orderItem.getName(), orderItem.getNumber(), orderItem.getFee(),
                            order.getId(), orderItem.getDish_id());
                    Dish dish = dishService.getDishByID(orderItem.getDish_id());
                    dishService.update(orderItem.getDish_id(), null, null, null,
                            null, dish.getSales_num_thirty()+orderItem.getNumber(), null, null);
                }
            } catch (Exception e) {
                if (order != null) {
                    orderService.delete(order.getId());
                }
                return ResponseResult.error(555, "invalid order item", null);
            }

            int resultID = customerService.update(order.getUser_id(),(int)order.getTotal_fee()*100, null);
            return ResponseResult.success(order.getId());
        }
        catch (Exception e){
            if (order != null) {
                orderService.delete(order.getId());
            }
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "canteen_id", required = false) String canteenID,
                               @RequestParam(name = "user_id", required = false) String userID,
                               @RequestParam(name = "status", required = false) String status,
                               @RequestParam(name = "order_type", required = false) String orderType) {
        try{
            List<Order> list = orderService.get(canteenID,userID,status,orderType);
            return ResponseResult.success(list);
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
//    public JSONObject deleteOrder(@PathVariable Integer id) {
//        JSONObject resObject = new JSONObject();
//        try{
//            List<Order> list = orderService.getOrder(canteenID,userID,status,orderType);
//            resObject.put("resultCode",1);
//            resObject.put("msg","Query Success");
//            resObject.put("content",JSON.toJSON(list));
//            return resObject;
//        }
//        catch (Exception e){
//            resObject.put("resultCode",-2);
//            resObject.put("msg","Internal Fail");
//            resObject.put("content",e.getMessage());
//            System.out.println("Internal Fail,"+e.getMessage());
//        }
//        return resObject;
//    }

    @Token
    @ResponseBody
    @RequestMapping(value = "/status",method = RequestMethod.PUT,produces = "application/json; charset=utf-8")
    public ResponseResult updateStatus(@RequestBody String json) {
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderID = jsonObject.getString("orderID");
            String status = jsonObject.getString("status");

            int result = orderService.updateStatus(orderID,status);
            return ResponseResult.success();
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }
}