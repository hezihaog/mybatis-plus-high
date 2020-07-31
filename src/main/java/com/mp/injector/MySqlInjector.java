package com.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.injector.methods.additional.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.additional.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.additional.LogicDeleteByIdWithFill;
import com.mp.method.DeleteAllMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 3:44 下午
 * <p>
 * 自定义Sql注入器
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //加入自定义的删除所有的方法
        methodList.add(new DeleteAllMethod());
        //1.官方选装件，批量插入，支持排除某些字段不进行批量插入的字段中
        methodList.add(new InsertBatchSomeColumn(new Predicate<TableFieldInfo>() {
            @Override
            public boolean test(TableFieldInfo info) {
                //返回true，代表加入插入字段中，false则为不加入
                //除了逻辑删除的字段，其他都加入
                return !info.isLogicDelete();
                //如果还想排除其他字段，使用getColumn()方法获取列名
                //return !info.isLogicDelete() && !info.getColumn().equals("age");
            }
        }));
        //2.官方选装件，逻辑删除时，自动填充其他字段（例如逻辑删除时，自动填充删除人是谁，什么时候删除的）
        methodList.add(new LogicDeleteByIdWithFill());
        //3.官方选装件，根据Id更新固定几个字段
        methodList.add(new AlwaysUpdateSomeColumnById(new Predicate<TableFieldInfo>() {
            @Override
            public boolean test(TableFieldInfo info) {
                //当更新时，忽略name字段，其他非逻辑删除字段会进行更新
                return !info.getColumn().equals("name");
            }
        }));
        return methodList;
    }
}