package com.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:45 上午
 * <p>
 */
@Data
public class User {
    /**
     * 主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 直属上级
     */
    private Long managerId;
    /**
     * 创建时间
     */
    //注解@TableField，fill属性，配置自动填充，在插入时，自动插入创建时间。默认是不处理的
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    //注解@TableField，fill属性，配置自动填充，在更新时，自动更新时间。默认是不处理的
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 版本
     */
    //注解@Version，标识当前字段为乐观锁字段
    @Version
    private Integer version;
    /**
     * 逻辑删除标识（0为未删除，1为已删除）
     */
    //注解@TableLogic，标识该字段为逻辑删除字段
    //你可以使用注解里面的value属性指定未删除的值，delval属性设置已删除的值，一般我们不指定，使用全局配置的，这里配置的是局部配置的
    @TableLogic
    //查询时，不查该字段，因为查询都会过滤这个条件，必须为0未删除时，才查询出来，所以查这个字段，没什么意义
    @TableField(select = false)
    private Integer deleted;
}