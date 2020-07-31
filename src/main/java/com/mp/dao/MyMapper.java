package com.mp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 3:49 下午
 * <p>
 * 自己的Mapper基类，提供一些通用方法
 */
public interface MyMapper<T> extends BaseMapper<T> {
    /**
     * 自定义sql注入方法，即使不写sql语句，也能删除所有，返回影响行数
     */
    int deleteAll();

    /**
     * 批量插入
     */
    int insertBatchSomeColumn(List<T> list);

    /**
     * 逻辑删除，并且带自动填充
     */
    int deleteByIdWithFill(T entity);

    /**
     * 根据Id，更新固定几个字段
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}