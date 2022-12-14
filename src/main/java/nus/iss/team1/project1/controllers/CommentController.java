package nus.iss.team1.project1.controllers;

//import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.annotation.token.Token;
import nus.iss.team1.project1.models.Comment;
import nus.iss.team1.project1.services.CommentService;
import nus.iss.team1.project1.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping(path="/Foodies/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Token
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResponseResult create(@RequestBody String json){
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
            if(result > 0){
                return ResponseResult.success(result);
            }
            else {
                return ResponseResult.error("User Commented already", null);
            }
        }
        catch (Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResponseResult get(@RequestParam(name = "canteen_id", required = false) String canteenID,
                              @RequestParam(name = "user_id", required = false) String userID,
                              @RequestParam(name = "order_id", required = false) String orderID) {
        try{
            List<Comment> list = commentService.get(canteenID, userID,orderID);
            return ResponseResult.success(list);
        }
        catch(Exception e){
            return ResponseResult.internalError(e);
        }
    }

    @Token
    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer id){
        //JSONObject resObject = new JSONObject();
        try{
            commentService.delete(id);
            return ResponseResult.success();
        }
        catch(Exception e){
            return ResponseResult.internalError(e);
        }
    }

}
