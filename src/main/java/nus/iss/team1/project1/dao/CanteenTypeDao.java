package nus.iss.team1.project1.dao;

import nus.iss.team1.project1.models.CanteenType;
import nus.iss.team1.project1.models.CanteenTypeCanteen;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanteenTypeDao {

    @Select("select count(*) from CanteenType where type = #{type}")
    public Integer checkCanteenTypeExist(@Param("type")String type);

    @Insert(" <script>"  +
            "insert into CanteenType (type)" +
            " values (#{type})" +
            " </script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void create(CanteenType canteenType);

    @Insert(" <script>"  +
            "insert into Canteen_CanteenType (canteen_id, canteen_type_id)" +
            " values (#{canteen_id}, #{canteen_type_id})" +
            " </script>")
    public void createCanteenCanteenType(CanteenTypeCanteen canteenTypeCanteen);

    @Select(" <script>"  +
            " select id,type from CanteenType" +
            " </script>" )
    public List<CanteenType> get();

    @Select(" <script>"  +
            " select id,type from CanteenType" +
            "where type = #{type}" +
            " </script>" )
    public CanteenType getByType(@Param("type") String type);

    @Select("select * from Canteen_CanteenType, CanteenType " +
            "where Canteen_CanteenType.canteen_type_id=CanteenType.id and Canteen_CanteenType.canteen_id=#{canteenID}")
    public List<CanteenType> getByCanteenID(int canteenID);

    @Update(" <script>"  +
            " update CanteenType set id=#{id} " +
            " <when test='type!=null'>" +
            " ,type = #{type}" +
            " </when>" +
            " where id = #{id}" +
            " </script>" )
    public void update(@Param("id") Integer id, @Param("type")String type);

    @Delete(" <script>"  +
            " delete from Canteen where id = #{id}" +
            " </script>")
    public void delete(@Param("id") Integer id);

    @Delete(" <script>"  +
            " delete from Canteen_CanteenType where canteen_id = #{canteen_id} and canteen_type_id = #{canteen_type_id}" +
            " </script>")
    public void deleteCanteenTypeCanteen(Integer canteen_id, Integer canteen_type_id);
}
