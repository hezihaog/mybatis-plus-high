package com.mp.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:55 上午
 * <p>
 */
@Configuration
public class MyBatisConfiguration {
    public static ThreadLocal<String> myTableName = new ThreadLocal<>();

    /**
     * 逻辑删除注入器（3.1.2版本的MyBatis-Plus，默认已经配置了，所以可以不用配置）
     * 注意，如果配置了这句，自定义Sql注入器就会找到2个ISqlInjector的实现类，会导致注入失败而抛异常，所以版本符合的情况下，就不要加了
     */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * SQL性能分析插件，一般测试环境开启，生产环境不开启
     */
    @Bean
    //@Profile注解，配置开发环境、测试环境下开启
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        //设置是否格式化，默认为false
        interceptor.setFormat(true);
        //设置执行超过指定的毫秒数，就停止操作（SQL执行过慢）
        //interceptor.setMaxTime(5L);
        return interceptor;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        ArrayList<ISqlParser> sqlParserList = new ArrayList<>();

        //多租户解析器
//        TenantSqlParser tenantSqlParser = new TenantSqlParser();
//        tenantSqlParser.setTenantHandler(new TenantHandler() {
//            @Override
//            public String getTenantIdColumn() {
//                //返回多租户的字段名，注意是数据库字段名，而不是实体的变量名
//                return "manager_id";
//            }
//
//            @Override
//            public Expression getTenantId() {
//                //租户信息Id，一般情况下，可能会从session中取、配置文件中取、或者从静态变量等取出
//                //这里不做演示，先写死
//                return new LongValue(1088248166370832385L);
//            }
//
//            @Override
//            public boolean doTableFilter(String tableName) {
//                //可以指定哪些加上租户信息，哪些不加上
//                //返回true，代表过滤，就不加
//                //返回false，代表不过滤，就是加
//                if (tableName.equals("role")) {
//                    //如果是角色表，就不加租户条件
//                    return true;
//                }
//                return false;
//            }
//        });
//        //将多租户解析器添加到解析器列表中
//        sqlParserList.add(tenantSqlParser);

        //动态表名解析器，可用于一定规则的分表，例如表数据量很大，表名按一定前缀+日期+表名，来分开不同的表来查询
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        tableNameHandlerMap.put("user", new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                //如果返回为null，则不进行替换
                return myTableName.get();
            }
        });
        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        sqlParserList.add(dynamicTableNameParser);

        //配置解析器列表到插件中
        interceptor.setSqlParserList(sqlParserList);
        //添加过滤器，可以让指定方法不添加多租户条件
        interceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement statement = SqlParserHelper.getMappedStatement(metaObject);
                //过滤UserMapper的selectById()方法，不添加租户条件
                //多个Mapper的方法，都在这里配置，会很多，一般使用@SqlParser注解，配置到Mapper的方法上来进行！例如UserMapper就配置了
                if ("com.mp.dao.UserMapper.selectById".equals(statement.getId())) {
                    return true;
                }
                return false;
            }
        });
        return interceptor;
    }
}