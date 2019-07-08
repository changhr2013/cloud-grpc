package com.changhr.cloud.security.oauth2.mapper;

import com.changhr.cloud.security.oauth2.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author changhr
 */
@Mapper
public interface RoleMapper {

    @Select("SELECT A.id,A.name FROM role A LEFT JOIN user_role B ON A.id=B.role_id WHERE B.user_id=${userId}")
    List<Role> getRolesByUserId(@Param("userId") Long userId);

}