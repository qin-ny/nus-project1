package nus.iss.team1.project1.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import nus.iss.team1.project1.models.User;
import java.util.List;

@Repository
public interface UserDao {
    @Select("select id,name,NRIC_FIN,phone_number,gender,email,username,password,type from " +
            "User where (username = #{userName} or email = #{userName}) and password= #{password} and type = #{type}")
    public User validate(@Param("userName")String userName,@Param("password")String password,@Param("type")String type);


    @Select("select count(*) from User where username = #{userName}")
    public Integer checkUserNameExist(@Param("userName")String userName);


    @Select("select count(*) from User where phone_number = #{phone} and type = #{type}")
    public Integer checkPhoneExist(@Param("phone")String phone,@Param("type")String type);

    @Select("select count(*) from User where email = #{email}  and type = #{type}")
    public Integer checkEmailExist(@Param("email")String email,@Param("type")String type);


    @Select("select count(*) from User where NRIC_FIN = #{NRIC}  and type = #{type}")
    public Integer checkNRICExist(@Param("NRIC")String NRIC,@Param("type")String type);


    @Insert(" <script>"  +
            "insert into User (username,password,name," +
            " <when test='gender!=null'>" +
            " gender," +
            " </when>" +
            " <when test='phone_number!=null'>" +
            " phone_number," +
            " </when>" +
            " <when test='email!=null'>" +
            " email," +
            " </when>" +
            " NRIC_FIN,type)" +
            " values (#{username}, #{password},#{name}, " +
            " <when test='gender!=null'>" +
            " #{gender}," +
            " </when>" +
            " <when test='phone_number!=null'>" +
            " #{phone_number}," +
            " </when>" +
            " <when test='email!=null'>" +
            " #{email}," +
            " </when>" +
            " #{NRIC_FIN},#{type})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(User user);

    @Select(" <script>"  +
            "select id,name,NRIC_FIN,phone_number,gender,email,username,password,type from User where 1=1" +
            " <when test='userName!=null'>" +
            " and username = #{userName}" +
            " </when>" +
            " <when test='name!=null'>" +
            " and name = #{name}" +
            " </when>" +
            " <when test='gender!=null'>" +
            " and gender = #{gender}" +
            " </when>" +
            " <when test='type!=null'>" +
            " and type = #{type}" +
            " </when>"+
            " </script>")
    public List<User> get(@Param("userName")String userName,@Param("name")String name,@Param("gender")String gender,@Param("type")String type);

    @Update(" <script>"  +
            " update User set " +
            " <when test='userName!=null'>" +
            " username = #{userName}, " +
            " </when>" +
            " <when test='gender!=null'>" +
            " gender = #{gender}, " +
            " </when>" +
            " <when test='phone!=null'>" +
            " phone_number = #{phone}, "+
            " </when>" +
            " <when test='email!=null'>" +
            " email = #{email} "+
            " </when>" +
            " where NRIC_FIN = #{NRIC} " +
            " </script>")
    public void modify(@Param("userName")String userName,
                @Param("gender")String gender,@Param("phone")String phone,@Param("email")String email,@Param("NRIC")String NRIC,
                @Param("type")String type);

    @Select("select count(*) from User where username = #{userName} and NRIC_FIN != #{NRIC}")
    public Integer checkUserNameExistModify(@Param("userName")String userName,@Param("NRIC")String NRIC);


    @Select("select count(*) from User where phone_number = #{phone} and type = #{type} and NRIC_FIN != #{NRIC}")
    public Integer checkPhoneExistModify(@Param("phone")String phone,@Param("type")String type,@Param("NRIC")String NRIC);

    @Select("select count(*) from User where email = #{email}  and type = #{type} and NRIC_FIN != #{NRIC}")
    public Integer checkEmailExistModify(@Param("email")String email,@Param("type")String type,@Param("NRIC")String NRIC);

    @Update(" <script>"  +
            " update User set " +
            " password = #{password} " +
            " where username = #{userName} " +
            " </script>")
    public void modifyPassword(@Param("userName")String userName,
                       @Param("password")String password);

}