package nus.iss.team1.project1.services;

import nus.iss.team1.project1.models.Comment;

import java.util.List;
public interface CommentService {
    public int create(String orderID, String userID, String canteenID, float star, String comment);
    public List<Comment> get(String canteenID, String userID,String orderID);
    public int delete(Integer id);
}
