package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    @Insert(" <script>" +
            " INSERT INTO Project1.Comment (order_id,user_id,canteen_id,star,comment)" +
            " VALUES (#{order_id},#{user_id},#{canteen_id},#{star},#{comment})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(Comment comment);

    @Select(" <script>" +
//            " SELECT a.user_id, c.name, a.star, b.name, a.comment FROM Comment as a, Canteen as b, User as c WHERE a.canteen_id = b.id and a.user_id = c.id" +
            " SELECT * from Comment as a, Canteen as b, User as c WHERE a.canteen_id = b.id and a.user_id = c.id" +
            " <when test='canteenID!=null'>" +
            " and a.canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='userID!=null'>" +
            " and a.user_id = #{userID}" +
            " </when>" +
            " </script>")
    public List<Comment> getComment(@Param("canteenID") String canteenID,@Param("userID") String userID);

    @Select(" <script>" +
            " SELECT count(*) from Project1.Comment where user_id = #{userID} and order_id = #{orderID}" +
            " </script>")
    public Integer checkCommentNum(@Param("userID") String userID,@Param("orderID") String orderID);

    @Select(" <script>" +
            " SELECT canteen_id FROM Project1.Comment WHERE id = #{commentID}" +
            " </script>")
    public Integer getCanteenIDByID(@Param("commentID") Integer commentID);

    @Delete(" <script>" +
            " DELETE from Project1.Comment where id in #{comment_id}" +
            " </script>")
    public int delete(@Param("commentID") Integer commentID);
}
