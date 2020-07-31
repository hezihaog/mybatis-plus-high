package com.mp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:59 上午
 * <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 逻辑删除，由于我们实体的逻辑删除字段配置了注解@TableLogic，所以使用的是逻辑删除，而不是真正的删除
     */
    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void select() {
        //这里测试一下，例如我们让表名是按年份规则进行区分
        //MyBatisConfiguration.myTableName.set("user_2019");

        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void mySelect() {
        List<User> userList = userMapper.mySelectList(
                Wrappers.<User>lambdaQuery()
                        .gt(User::getAge, 25)
                        //自定义sql查询，不会加逻辑删除的限定条件，所以需要自己加
                        .eq(User::getDeleted, 0)
        );
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(26);
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
    }
}