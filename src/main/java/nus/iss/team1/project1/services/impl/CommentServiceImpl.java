package nus.iss.team1.project1.services.impl;

import nus.iss.team1.project1.dao.CommentDao;
import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.models.Comment;
import nus.iss.team1.project1.services.CommentService;
//import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private CanteenDao canteenDao;
    @Override
    public int create(String orderID,String userID,String canteenID, float star,String comment){
        int num = commentDao.checkCommentNum(userID, orderID);
        if(num > 0){
            return -1;
       }
        Comment commentItem = new Comment();
        commentItem.setOrder_id(Integer.valueOf(orderID));
        commentItem.setCanteen_id(Integer.valueOf(canteenID));
        commentItem.setUser_id(Integer.valueOf(userID));
        commentItem.setStar(star);
        commentItem.setComment(comment);

        commentDao.create(commentItem);
        canteenDao.updateStar(canteenID);
        return commentItem.getId();
    }

    @Override
    public List<Comment> get(String canteenID, String userID,String orderID) {
        return commentDao.getComment(canteenID, userID,orderID);
    }

    @Override
    public int delete(Integer id) {
        String temp_id = String.valueOf(commentDao.getCanteenIDByID(id));
        commentDao.delete(id);
        canteenDao.updateStar(temp_id);
        return id;
    }
}
