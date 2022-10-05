package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Canteen;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.OrderItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao {

    @Insert(" <script>"  +
            "insert into OrderItem (name,number,fee,order_id,dish_id)" +
            " values (#{name},#{number},#{fee},#{order_id},#{dish_id})" +
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
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "number",property = "number"),
            @Result(column = "fee",property = "fee"),
            @Result(column = "order_id",property = "order_id"),
            @Result(column = "dish_id",property = "dish_id"),
            @Result(
                    property = "dish",
                    column = "dish_id",
                    javaType = Dish.class,
                    one = @One(select = "nus.iss.team1.project1.dao.DishDao.getDishByID")
            )
    })
    public List<OrderItem> get(@Param("orderID") Integer orderID, @Param("dishID") Integer dishID);

}
