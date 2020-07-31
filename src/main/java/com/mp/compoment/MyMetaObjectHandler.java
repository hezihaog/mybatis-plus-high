package com.mp.compoment;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 10:24 上午
 * <p>
 * 元数据对象处理类
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String fieldName = "createTime";
        //有创建时间字段时，才进行填充，否则不处理
        boolean hasCreateTimeField = metaObject.hasSetter(fieldName);
        if (hasCreateTimeField) {
            System.out.println("============== insert 触发自动填充... ==============");
            //新增时，插入创建时间（注意传入的字段名是属性中的变量名，不是数据库中的字段名）
            setInsertFieldValByName(fieldName, LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //存在更新时间字段时，再进行自动填充，否则不处理
        String fieldName = "updateTime";
        boolean hasUpdateTime = metaObject.hasSetter(fieldName);
        //获取实体中，是否手动设置了值，如果设置了值，则不进行自动填充
        Object val = getFieldValByName(fieldName, metaObject);
        if (val == null && hasUpdateTime) {
            System.out.println("============== update 触发自动填充... ==============");
            //更新时，更新时间（注意传入的字段名是属性中的变量名，不是数据库中的字段名）
            setUpdateFieldValByName(fieldName, LocalDateTime.now(), metaObject);
        }
    }
}