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
            " SELECT a.user_id, a.star, b.name, a.comment FROM comment as a, canteen as b where a.canteen_id = b.id and a.canteen_id = #{canteenID}" +
            " </script>")
    public List<Comment> getComment(@Param("canteenID") String canteenID);

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
