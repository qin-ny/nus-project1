package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Dish;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishDao {

    @Select("select count(*) from Dish where name = #{name} and canteen_id = #{canteenID}")
    public Integer checkDishExist(@Param("name")String name,@Param("canteenID") Integer canteenID);


    @Insert(" <script>"  +
            "insert into Dish (name,price,description,dish_type_id,canteen_id,stock)" +
            " values (#{name},#{price},#{description},#{dish_type_id},#{canteen_id},#{stock})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(Dish dish);

    @Update(" <script>"  +
            " update Dish set id=#{id} " +
            " <when test='name!=null'>" +
            " ,name = #{name}" +
            " </when>" +
            " <when test='price!=-1'>" +
            " ,price = #{price}" +
            " </when>" +
            " <when test='description!=null'>" +
            " ,description = #{description}" +
            " </when>" +
            " <when test='dish_type_id!=null'>" +
            " ,dish_type_id = #{dish_type_id}" +
            " </when>" +
            " <when test='sales_num_thirty!=null'>" +
            " ,sales_num_thirty = #{sales_num_thirty}" +
            " </when>" +
            " <when test='stock!=null'>" +
            " ,stock = #{stock}" +
            " </when>" +
            " where id = #{id}" +
            " </script>" )
    public void update(@Param("id") Integer id, @Param("name")String name,
                       @Param("price") double price, @Param("description") String description,
                       @Param("dish_type_id") String type_id, @Param("sales_num_thirty") Integer sales_num_thirty,
                       @Param("stock") String stock);


    @Select(" <script>"  +
            " select * from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type_id!=null'>" +
            " and dish_type_id = #{type_id}" +
            " </when>" +
            " </script>" )
    public List<Dish> getDish(@Param("canteenID") String canteenID,@Param("type_id") String type_id);

    @Select(" <script>"  +
            " select * from Dish where " +
            " id = #{id}" +
            " </script>" )
    public Dish getDishByID(@Param("id") Integer id);

    @Select(" <script>"  +
            " select * from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type_id!=null'>" +
            " and dish_type_id = #{type_id}" +
            " </when>" +
            " order by price ${order}" +
            " </script>" )
    public List<Dish> getDishOrderByPrice(@Param("canteenID") String canteenID,@Param("type_id") String type_id,@Param("order") String order);

    @Select(" <script>"  +
            " select * from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type_id!=null'>" +
            " and dish_type_id = #{type_id}" +
            " </when>" +
            " order by sales_num_thirty desc" +
            " </script>")
    public List<Dish> getDishOrderBySales(@Param("canteenID") String canteenID,@Param("type_id") String type_id);

    @Delete(" <script>"  +
            " delete from Dish where id = #{id}" +
            " </script>")
    public void delete(@Param("id") Integer id);

}