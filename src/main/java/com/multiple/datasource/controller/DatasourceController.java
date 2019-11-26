package com.multiple.datasource.controller;

import com.multiple.datasource.annotation.Master;
import com.multiple.datasource.annotation.Slave;
import com.multiple.datasource.service.TidingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhuzhenqi
 * @date 2019/11/26 9:42
 */
@RestController
public class DatasourceController {

    @Autowired
    private TidingsService tidingsService;
    @GetMapping("master")
    public Map<Object, Object> getMasterById(@RequestParam Long id) {
        return tidingsService.getMasterById(id);
    }
    @GetMapping("slave")
    public Map<Object, Object> getSlaveById(@RequestParam Long id) {
        return tidingsService.getSlaveById(id);
    }
}
