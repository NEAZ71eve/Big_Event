package com.demo.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CategoryMapper {
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time"+
            "values(#{categoryName},#{categroyAlias},#{createUser},#{createTime},#{updateTime}")
    void add(@Param("categoryName") String categoryName,@Param("categoryAlias")String categoryAlias, @Param("createUser") Integer createUser);
}
