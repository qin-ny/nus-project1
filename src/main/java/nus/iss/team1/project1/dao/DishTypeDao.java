package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.Dish;
import nus.iss.team1.project1.models.DishType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishTypeDao {


    @Select("select count(*) from CanteenType where type = #{type}")
    public Integer checkDishTypeExist(@Param("type")String type);

    @Insert(" <script>"  +
            "insert into DishType (type,canteen_id)" +
            " values (#{type},#{canteen_id})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(DishType dishType);

    @Update(" <script>"  +
            " update DishType set id=#{id} " +
            " <when test='type!=null'>" +
            " ,type = #{type}" +
            " </when>" +
            " where id = #{id}" +
            " </script>" )
    public void update(@Param("id") Integer id, @Param("type")String type);

    @Select(" <script>"  +
            " select * from DishType where 1=1" +
            " <when test='id>0'>" +
            " and id = #{id}" +
            " </when>" +
            " <when test='canteen_id!=null'>" +
            " and canteen_id = #{canteen_id}" +
            " </when>" +
            " <when test='type!=null'>" +
            " and type = #{type}" +
            " </when>" +
            " </script>" )
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "type",property = "type"),
            @Result(column = "canteen_id",property = "canteen_id")
    })
    public List<DishType> get(@Param("id") Integer id, @Param("canteen_id") String canteenID, @Param("type") String type);

    @Delete(" <script>"  +
            " delete from DishType where id = #{id}" +
            " </script>")
    public void delete(@Param("id") Integer id);
}
