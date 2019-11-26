package com.multiple.datasource.config;

import com.multiple.datasource.datasource.RoutingDataSource;
import com.multiple.datasource.enumeration.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuzhenqi
 * @date 2019/11/25 15:43
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.master.type}")
    private Class<? extends DataSource> masterType;

    @Value("${spring.datasource.slave1.type}")
    private Class<? extends DataSource> slave1Type;

    @Value("${spring.datasource.slave2.type}")
    private Class<? extends DataSource> slave2Type;

    @Bean(name = "masterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master() {
        DataSource master = DataSourceBuilder.create().type(masterType).build();
        return master;
    }

    @Bean(name = "slave1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1() {
        DataSource slave1 = DataSourceBuilder.create().type(slave1Type).build();
        return slave1;
    }

    @Bean(name = "slave2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource slave2() {
        DataSource slave2 = DataSourceBuilder.create().type(slave2Type).build();
        return slave2;
    }

    @Primary
    //该注解用于声明当前bean依赖于另外一个bean。所依赖的bean会被容器确保在当前bean实例化之前被实例化.
    @DependsOn({ "masterDatasource", "slave1Datasource","slave2Datasource"})
    @Bean(name = "datasource")
    public RoutingDataSource dataSource(@Qualifier("masterDatasource") DataSource masterDatasource,
                                        @Qualifier("slave1Datasource") DataSource slave1Datasource,
                                        @Qualifier("slave2Datasource") DataSource slave2Datasource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDatasource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1Datasource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2Datasource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(masterDatasource);
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

}
