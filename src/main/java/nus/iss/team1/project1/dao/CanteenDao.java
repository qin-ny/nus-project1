package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Canteen;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanteenDao {

    @Select("select count(*) from Canteen where name = #{name}")
    public Integer checkCanteenExist(@Param("name")String name);

    @Insert(" <script>"  +
            "insert into Canteen (name,description,user_id)" +
            " values (#{name}, #{description},#{user_id})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(Canteen canteen);


    @Select(" <script>"  +
            " select id,name,star,description,user_id from Canteen " +
            "where id=#{id}" +
            " </script>" )
    public Canteen getByID(@Param("id") Integer id);

    @Select(" <script>"  +
            " select * from Canteen where 1=1" +
            " <when test='userID!=null'>" +
            " and user_id = #{userID}" +
            " </when>" +
            " <when test='keyword!=null'>" +
            " and name like #{keyword} " +
            " </when>" +
            " <when test='orderType!=null'>" +
            " order by star desc " +
            " </when>" +
            " </script>" )
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "star",property = "star"),
            @Result(column = "description",property = "description"),
            @Result(column = "user_id",property = "user_id"),
            @Result(
                    property = "canteenTypes",
                    column = "id",
                    javaType = List.class,
                    many = @Many(select = "nus.iss.team1.project1.dao.CanteenTypeDao.getByCanteenID")
            ),
            @Result(
                    property = "orderNums",
                    column = "id",
                    javaType = Integer.class,
                    many = @Many(select = "nus.iss.team1.project1.dao.OrderDao.getOrderNumByCanteenID")
            )
    })
    public List<Canteen> get(@Param("userID") String userID,@Param("orderType") String orderType,@Param("keyword") String keyword);

    @Update(" <script>"  +
            " update Canteen set id=#{id} " +
            " <when test='name!=null'>" +
            " ,name = #{name}" +
            " </when>" +
            " <when test='description!=null'>" +
            " ,description = #{description}" +
            " </when>" +
            " where id = #{id}" +
            " </script>" )
    public void update(@Param("id") Integer id, @Param("name")String name, @Param("description") String description);
    @Update(" <script>" +
            " update Canteen set star =" +
            " (select avg(star) from comment where canteen_id = #{canteenID})" +
            " where id = #{canteenID}" +
            " </script>")
    public void updateStar(@Param("canteenID") String canteenID);
    @Delete(" <script>"  +
            " delete from Canteen where id = #{id}" +
            " </script>")
    public void delete(@Param("id") Integer id);
}