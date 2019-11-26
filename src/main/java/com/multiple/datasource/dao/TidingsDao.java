package com.multiple.datasource.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhuzhenqi
 * @date 2019/11/26 9:17
 */
@Repository
public interface TidingsDao {
    @Select("select * from platform_tidings where id = #{id, jdbcType=BIGINT}")
    Map<Object,Object> getById(Long id);
}
