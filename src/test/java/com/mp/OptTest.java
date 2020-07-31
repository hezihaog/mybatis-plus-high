package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:59 上午
 * <p>
 * 自动填充测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OptTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void update() {
        int version = 2;

        User user = new User();
        user.setEmail("ly3@baomidou.com");
        user.setVersion(version);
        QueryWrapper<User> query = Wrappers.query();
        query.eq("name", "李玉");
        int rows = userMapper.update(user, query);
        System.out.println("影响行数：" + rows);

        //复用会有问题！！！version字段会and多次！！
        int version2 = 3;
        User user2 = new User();
        user2.setEmail("ly4@baomidou.com");
        user2.setVersion(version2);
        query.eq("age", 25);
        int rows2 = userMapper.update(user2, query);
        System.out.println("影响行数：" + rows2);
    }

    @Test
    public void updateById() {
        //模拟查询出来版本
        int version = 1;

        User user = new User();
        user.setId(1289027494564343810L);
        user.setEmail("ly2@baomidou.com");
        //设置版本号，Mybatis-Plus会将版本号+1进行设置
        user.setVersion(version);
        //手动设置更新时间，自动填充则不进行填充
        user.setUpdateTime(LocalDateTime.now());
        //注意，乐观锁能生效的只有updateById()和update()方法，Wrapper的方式不能服用Wrapper，否则会有问题！！version字段会and多次！！
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }
}