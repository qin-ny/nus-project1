package nus.iss.team1.project1.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository
public interface UserDao {
    @Select("select count(*) from tb_user where username = #{userName} and password= #{password} and type = #{type}")
    public Integer validate(@Param("userName")String userName,@Param("password")String password,@Param("type")String type);


    @Select("select count(*) from tb_user where username = #{userName}")
    public Integer checkExist(@Param("userName")String userName);

    @Insert("insert into tb_user (username,password,type) values (#{userName}, #{password}, #{type})")
    void create(@Param("userName")String userName,@Param("password")String password,@Param("type")String type);
}