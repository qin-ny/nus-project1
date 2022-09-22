package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Canteen;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanteenDao {

    @Select("select count(*) from Canteen where name = #{name}")
    public Integer checkCanteenExist(@Param("name")String name);

    @Insert(" <script>"  +
            "insert into Canteen (name,description,user_id)" +
            " values (#{name}, #{description},#{userID})" +
            " </script>")
    public void create(@Param("name") String name, @Param("description") String description, @Param("userID") String userID);


    @Select(" <script>"  +
            " select id,name,star,description,user_id from Canteen where 1=1" +
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
}