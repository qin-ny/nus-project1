package nus.iss.team1.project1.controllers;

import com.alibaba.fastjson.JSONObject;
import nus.iss.team1.project1.dao.CustomerDao;
import nus.iss.team1.project1.services.CustomerService;
import nus.iss.team1.project1.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/Foodies/user/customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public ResponseResult update(@RequestBody String json) {
        try{

            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer userID = jsonObject.getInteger("user_id");

            Integer isMember = null;
            if (jsonObject.containsKey("availability")) {
                boolean availability = jsonObject.getBooleanValue("availability");
                isMember = availability ? 1:0;
            }

//            QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", username).or()
//                    .eq("email", email).or()
//                    .eq("phone_num", phone);
            customerDao.update(userID, null, isMember);
        }
        catch (Exception e) {
            return ResponseResult.internalError(e);
        }
        return ResponseResult.success();
    }
}
