package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

//    @Select("select count(*) from Order where username = #{userName}")
//    public Integer checkUserNameExist(@Param("userName")String userName);

    @Insert(" <script>"  +
            "insert into project1.Order (order_time,total_fee,status,canteen_id,user_id)" +
            " values (#{order_time},#{total_fee},#{status},#{canteen_id},#{user_id})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(Order order);

    @Select(" <script>"  +
            " select id,create_time,order_time,total_fee,status,user_id,canteen_id from project1.Order where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='userID!=null'>" +
            " and user_id = #{userID}" +
            " </when>" +
            " <when test='status!=null'>" +
            " and status = #{status}" +
            " </when>" +
            " order by order_time ${order}" +
            " </script>")
    public List<Order> get(@Param("canteenID") String canteenID, @Param("userID") String userID,
                                @Param("status") String status, @Param("order") String order);

    @Update(" <script>"  +
            " update project1.Order set status = #{status} where id = #{orderID}" +
            " </script>" )
    public void updateStatus(@Param("orderID") String orderID, @Param("status")String status);

//    @Delete(" <script>"  +
//            " delete from project1.Order where id in #{orderID}" +
//            " </script>" )
//    public void delete(@Param("orderID") String orderID);

}