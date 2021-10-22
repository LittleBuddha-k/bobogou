package com.littlebuddha.bobogou.modules.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * User: LittleBuddha-k
 * Date: 2021-10-22
 */
@Configuration                //第一个包扫描
@MapperScan(basePackages = {"com.littlebuddha.bobogou.modules.mapper"}, sqlSessionFactoryRef = "oneSqlSessionFactory")
public class OneDataSourceConfig {
        @Value("${spring.datasource.one.driver-class-name}")
        String driverClass;
        @Value("${spring.datasource.one.jdbc-url}")
        String url;
        @Value("${spring.datasource.one.username}")
        String userName;
        @Value("${spring.datasource.one.password}")
        String passWord;

        @Primary
        @Bean(name = "oneDataSource")
        @ConfigurationProperties("spring.datasource.one")
        public DataSource oneDataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(passWord);
            return dataSource;
        }
        @Bean(name = "oneSqlSessionFactory")
        @Primary
        public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);
            sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:mybatis/*/*Mapper.xml"));//第一个mapper.xml

            //配置多数据源需要设置驼峰规则，否则不生效
            org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            sessionFactoryBean.setConfiguration(configuration);
            return sessionFactoryBean.getObject();
        }
        @Bean(name = "oneSqlSessionTemplate")
        @Primary
        public SqlSessionTemplate oneSqlSessionFactoryTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
}