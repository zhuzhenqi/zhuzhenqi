package com.multiple.datasource.service.impl;

import com.multiple.datasource.annotation.Master;
import com.multiple.datasource.annotation.Slave;
import com.multiple.datasource.dao.TidingsDao;
import com.multiple.datasource.service.TidingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhuzhenqi
 * @date 2019/11/26 9:19
 */
@Service
public class TidingsServiceImpl implements TidingsService {

    @Autowired
    private TidingsDao tidingsDao;
    @Override
    @Master
    public Map<Object, Object> getMasterById(Long id) {
        return tidingsDao.getById(id);
    }
    @Override
    @Slave
    public Map<Object, Object> getSlaveById(Long id) {
        return tidingsDao.getById(id);
    }
}
