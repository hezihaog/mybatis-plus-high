package com.mp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

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
public class FillTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void inset() {
        User user = new User();
        user.setName("李玉");
        user.setAge(31);
        user.setEmail("lmc@baomidou.com");
        user.setManagerId(1088248166370832385L);
        int rows = userMapper.insert(user);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(27);
        //手动设置更新时间，自动填充则不进行填充
        user.setUpdateTime(LocalDateTime.now());
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }
}