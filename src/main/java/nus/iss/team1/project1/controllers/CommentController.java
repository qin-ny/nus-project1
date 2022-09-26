package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.models.Comment;
import nus.iss.team1.project1.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemSleepEvent;
import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping(path="/Foodies/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public JSONObject create(@RequestBody String json){
        JSONObject resObject = new JSONObject();
        try{
            json = new String(json.getBytes(), Charset.forName("utf-8"));
            JSONObject jsonobject = JSONObject.parseObject(json);
            String orderID = jsonobject.getString("orderID");
            String userID = jsonobject.getString("userID");
            String canteenID = jsonobject.getString("canteenID");
            float star = jsonobject.getFloat("star");
            String comment = jsonobject.getString("comment");

            int result = commentService.create(orderID, userID, canteenID, star, comment);
            if(result == 1){
                resObject.put("resultCode",1);
                resObject.put("msg","Comment Created");
                resObject.put("content","New comment added");
                System.out.println("Comment Created");
            }
            else if(result == -1){
                resObject.put("resultCode",-1);
                resObject.put("msg","Comment Create Failed");
                resObject.put("content","User commented already");
                System.out.println("Comment Create Failed. User Commented already");
            }
        }
        catch (Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Internal Fail");
            resObject.put("content",e.getMessage());
        }
        return resObject;
    }
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public JSONObject get(@RequestParam(name = "canteen_id", required = false) String canteenID)  {
        JSONObject resObject = new JSONObject();
        try{
            List<Comment> list = commentService.get(canteenID);
            resObject.put("requestCode",1);
            resObject.put("msg","Query Success");
            resObject.put("content",JSON.toJSON(list));
            return resObject;
        }
        catch(Exception e){
            resObject.put("requestCode",-2);
            resObject.put("msg","Query Failed");
            resObject.put("content",e.getMessage());
            System.out.println("Internal Fail," + e.getMessage());
        }
        return resObject;
    }

    @ResponseBody
    @RequestMapping(value = {"{id}"},method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    public JSONObject delete(@PathVariable(value = "id", required = false) int id){
        JSONObject resObject = new JSONObject();
        try{
            commentService.delete(id);
            resObject.put("resultCode",1);
            resObject.put("msg","Delete Success");
            return resObject;
        }
        catch(Exception e){
            resObject.put("resultCode",-2);
            resObject.put("msg","Delete Failed");
            System.out.println("Internal Fail," + e.getMessage());
        }
        return resObject;
    }

}
