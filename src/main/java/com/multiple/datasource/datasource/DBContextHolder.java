package com.multiple.datasource.datasource;

import com.multiple.datasource.enumeration.DBTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhuzhenqi
 * @date 2019/11/25 17:05
 */
public class DBContextHolder {
    private static Logger log = LoggerFactory.getLogger(DBContextHolder.class);
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        if (dbType == null) {
            log.info("数据源初始化为空,赋值为master");
            contextHolder.set(DBTypeEnum.MASTER);
        }
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        if (contextHolder.get() != null) {
            log.info("=====Threadlocal数据源的类型是：======"+contextHolder.get().name());
        }
        else {
            log.info("=====Threadlocal数据源的类型是：空======数据源初始化为空,赋值为master");
            return DBTypeEnum.MASTER;
        }

        return contextHolder.get() == null ? DBTypeEnum.MASTER : contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.info("切换到master");
    }

    public static void slave() {
        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            set(DBTypeEnum.SLAVE1);
            log.info("切换到slave1");
        }else {
            set(DBTypeEnum.SLAVE2);
            log.info("切换到slave2");
        }
    }
    public static void clearDB() {
        contextHolder.remove();
    }
}
