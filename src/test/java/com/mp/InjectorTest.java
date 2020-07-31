package com.mp;

import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 3:46 下午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InjectorTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteAll() {
        int rows = userMapper.deleteAll();
        System.out.println("影响行数：" + rows);
    }

    /**
     * 官方选装件，批量插入
     */
    @Test
    public void insertBatchSomeColumn() {
        User user1 = new User();
        user1.setName("李兴华");
        user1.setAge(34);
        user1.setManagerId(1088248166370832385L);

        User user2 = new User();
        user2.setName("杨红");
        user2.setAge(29);
        user2.setManagerId(1088248166370832385L);

        List<User> list = Arrays.asList(user1, user2);
        int rows = userMapper.insertBatchSomeColumn(list);
        System.out.println("影响行数：" + rows);
    }

    /**
     * 逻辑删除，并带自动填充
     */
    @Test
    public void deleteByIdWithFill() {
        User user = new User();
        user.setId(1289111378211680257L);
        //逻辑删除后，更新年龄为35，注意实体属性age要加上@TableField(fill = FieldFill.UPDATE)注解
        user.setAge(35);
        int rows = userMapper.deleteByIdWithFill(user);
        System.out.println("影响行数：" + rows);
    }

    /**
     * 根据Id，更新固定几个字段（一般是排除固定的一些字段）
     */
    @Test
    public void alwaysUpdateSomeColumnById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setName("王第风");
        int rows = userMapper.alwaysUpdateSomeColumnById(user);
        System.out.println("影响行数：" + rows);
    }
}