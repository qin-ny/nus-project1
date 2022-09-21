package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao {

    @Insert(" <script>"  +
            "insert into OrderItem (number,fee,order_id,dish_id)" +
            " values (#{number},#{fee},#{order_id},#{dish_id})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(OrderItem orderItem);
//    public void create(@Param("number") Integer number, @Param("fee") double fee,
//                       @Param("orderID") Integer orderID,
//                       @Param("dishID") Integer dishID);

    @Select(" <script>"  +
            " select id,number,fee,order_id,dish_id from OrderItem where 1=1" +
            " <when test='orderID!=null'>" +
            " and order_id = #{orderID}" +
            " </when>" +
            " <when test='dishID!=null'>" +
            " and dish_id = #{dishID}" +
            " </when>" +
            " </script>")
    public List<OrderItem> get(@Param("orderID") Integer orderID, @Param("dishID") Integer dishID);

}
