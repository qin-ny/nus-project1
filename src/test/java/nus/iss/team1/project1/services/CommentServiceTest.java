package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.CanteenDao;
import nus.iss.team1.project1.dao.CommentDao;
import nus.iss.team1.project1.models.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @MockBean(name="commentDao")
    private CommentDao commentMockDao;

    @Test
    public void get_CanteenComment() throws Exception{
        Comment comment = new Comment();
        comment.setCanteen_id(1);
        comment.setComment("Very Good!");
        comment.setId(1);
        comment.setOrder_id(1);
        comment.setStar(4);
        comment.setUser_id(1);
        Comment comment1 = new Comment();
        comment1.setCanteen_id(1);
        comment1.setComment("Excellent!");
        comment1.setId(2);
        comment1.setOrder_id(2);
        comment1.setStar(5);
        comment1.setUser_id(2);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment1);
        Mockito.when(commentMockDao.getComment("1",null,null)).thenReturn(comments);
        List<Comment> c = commentService.get("1",null,null);
        Assertions.assertEquals(2,c.size());
        Assertions.assertEquals("Very Good!",c.get(0).getComment());
        Assertions.assertEquals(4,c.get(0).getStar());
        Assertions.assertEquals("Excellent!",c.get(1).getComment());
        Assertions.assertEquals(5,c.get(1).getStar());
    }

    @Test
    public void get_SingleComment() throws Exception{
        Comment comment = new Comment();
        comment.setCanteen_id(1);
        comment.setComment("Very Good!");
        comment.setId(1);
        comment.setOrder_id(1);
        comment.setStar(4);
        comment.setUser_id(1);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Mockito.when(commentMockDao.getComment(null,null,"1")).thenReturn(comments);
        List<Comment> c = commentService.get(null,null,"1");
        Assertions.assertEquals(1,c.size());
        Assertions.assertEquals("Very Good!",c.get(0).getComment());
        Assertions.assertEquals(4,c.get(0).getStar());
    }

    @Test
    public void get_UserComment() throws Exception{
        Comment comment = new Comment();
        comment.setCanteen_id(1);
        comment.setComment("Very Good!");
        comment.setId(1);
        comment.setOrder_id(1);
        comment.setStar(4);
        comment.setUser_id(1);
        Comment comment1 = new Comment();
        comment1.setCanteen_id(1);
        comment1.setComment("Excellent!");
        comment1.setId(2);
        comment1.setOrder_id(2);
        comment1.setStar(5);
        comment1.setUser_id(1);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment1);
        Mockito.when(commentMockDao.getComment(null,"1",null)).thenReturn(comments);
        List<Comment> c = commentService.get(null,"1",null);
        Assertions.assertEquals(2,c.size());
        Assertions.assertEquals("Very Good!",c.get(0).getComment());
        Assertions.assertEquals(4,c.get(0).getStar());
        Assertions.assertEquals("Excellent!",c.get(1).getComment());
        Assertions.assertEquals(5,c.get(1).getStar());
    }

    @Test
    public void delete() throws Exception{
        Comment comment = new Comment();
        comment.setCanteen_id(1);
        comment.setComment("Very Good!");
        comment.setId(1);
        comment.setOrder_id(1);
        comment.setStar(4);
        comment.setUser_id(1);
        Mockito.doNothing().when(commentMockDao).delete(1);
        int result = commentService.delete(1);
        Assertions.assertEquals(1,result);
    }
}