package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Canteen;
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
            " values (#{order_time},#{total_fee},#{status},#{canteen.id},#{user_id})" +
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
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "create_time",property = "create_time"),
            @Result(column = "order_time",property = "order_time"),
            @Result(column = "total_fee",property = "total_fee"),
            @Result(column = "status",property = "status"),
            @Result(column = "user_id",property = "user_id"),
            @Result(
                    property = "canteen",
                    column = "canteen_id",
                    javaType = Canteen.class,
                    one = @One(select = "nus.iss.team1.project1.dao.CanteenDao.getByID")
            ),
            @Result(
                    property = "orderItems",
                    column = "{orderID=id}",
                    javaType = List.class,
                    many = @Many(select = "nus.iss.team1.project1.dao.OrderItemDao.get")
            )
    })
    public List<Order> get(@Param("canteenID") String canteenID, @Param("userID") String userID,
                                @Param("status") String status, @Param("order") String order);

    @Update(" <script>"  +
            " update project1.Order set status = #{status} where id = #{orderID}" +
            " </script>" )
    public void updateStatus(@Param("orderID") String orderID, @Param("status")String status);

    @Select
    ( " <script>"  +
            " select count(*) cnt from project1.Order where canteen_id = #{canteen_id}" +
            " and DATE_SUB(CURDATE(), INTERVAL 30 DAY) &lt;= date(create_time)" +
            " </script>" )
    public Integer getOrderNumByCanteenID(@Param("canteen_id") String canteen_id);

    @Delete(" <script>"  +
            " delete from project1.Order where id in #{id}" +
            " </script>" )
    public void delete(@Param("id") Integer id);

}