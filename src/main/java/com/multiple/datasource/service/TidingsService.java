package com.multiple.datasource.service;

import java.util.Map;

/**
 * @author zhuzhenqi
 * @date 2019/11/26 9:18
 */
public interface TidingsService {

    /**因为是测试不想写实体类就用map了
     * @param id
     * @return
     */
    Map<Object,Object> getMasterById(Long id);
    /**因为是测试不想写实体类就用map了
     * @param id
     * @return
     */
    Map<Object,Object> getSlaveById(Long id);

}
