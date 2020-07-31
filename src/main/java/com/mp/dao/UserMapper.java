package com.mp.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mp.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:48 上午
 * <p>
 */
public interface UserMapper extends MyMapper<User> {
    /**
     * 自定义查询，是不会加逻辑删除的限定条件的
     * <p>
     * 有2种方式解决：
     * 1.有Wrapper对象传入，直接在Wrapper类中添加限定条件
     * 2.如果没有Wrapper对象传入，则需要写在下方的sql语句中，要特别注意
     */
    //@SqlParser注解，filter属性设置为true，让多租户配置不应用到这个方法上
    @SqlParser(filter = true)
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> mySelectList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
}