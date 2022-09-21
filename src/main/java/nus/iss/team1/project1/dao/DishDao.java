package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishDao {

    @Select("select count(*) from Dish where name = #{name} and canteen_id = #{canteenID}")
    public Integer checkDishExist(@Param("name")String name,@Param("canteenID") String canteenID);


    @Insert(" <script>"  +
            "insert into Dish (name,price,description,type,canteen_id)" +
            " values (#{name},#{price},#{description},#{type},#{canteenID})" +
            " </script>")
    public void create(@Param("name") String name, @Param("price") double price,
                       @Param("description") String description, @Param("type") String type,
                       @Param("canteenID") String canteenID);

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
            " <when test='type!=null'>" +
            " ,type = #{type}" +
            " </when>" +
            " where id = #{id}" +
            " </script>" )
    public void update(@Param("id") Integer id, @Param("name")String name,
                       @Param("price") double price, @Param("description") String description,
                       @Param("type") String type);


    @Select(" <script>"  +
            " select id,name,price,stock,description,availability,type,sales_num_thirty,canteen_id from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type!=null'>" +
            " and type = #{type}" +
            " </when>" +
            " </script>" )
    public List<Dish> getDish(@Param("canteenID") String canteenID,@Param("type") String type);

    @Select(" <script>"  +
            " select id,name,price,stock,description,availability,type,sales_num_thirty,canteen_id from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type!=null'>" +
            " and type = #{type}" +
            " </when>" +
            " order by price ${order}" +
            " </script>" )
    public List<Dish> getDishOrderByPrice(@Param("canteenID") String canteenID,@Param("type") String type,@Param("order") String order);

    @Select(" <script>"  +
            " select id,name,price,stock,description,availability,type,sales_num_thirty,canteen_id from Dish where 1=1" +
            " <when test='canteenID!=null'>" +
            " and canteen_id = #{canteenID}" +
            " </when>" +
            " <when test='type!=null'>" +
            " and type = #{type}" +
            " </when>" +
            " order by sales_num_thirty desc" +
            " </script>" )
    public List<Dish> getDishOrderBySales(@Param("canteenID") String canteenID,@Param("type") String type);

}