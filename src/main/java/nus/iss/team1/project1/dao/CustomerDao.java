package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao {

    @Insert(" <script>"  +
            " insert into Customer (user_id)" +
            " values (#{user_id})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(Customer customer);

    @Update(
          " <script>"  +
          " update Customer set user_id=#{user_id}" +
          " <when test='rewardPoint!=null'>" +
          " ,reward_points = (select reward_points from (" +
          " select reward_points "+
          " from Customer where user_id =  #{user_id} ) x) + #{rewardPoint} " +
          " </when>" +
          " <when test='is_member!=null'>" +
          " ,is_member = #{is_member}" +
          " </when>" +
          " where user_id = #{user_id}" +
          " </script>"
    )
    public void update(@Param("user_id") Integer user_id, @Param("rewardPoint") Integer rewardPoint, @Param("is_member") Integer is_member);

    @Select(
            " <script>"  +
            " select id,reward_points,is_member,user_id from Customer where user_id = #{user_id} " +
            " </script>"
    )
    public Customer getCustomer(@Param("user_id") Integer user_id);
}