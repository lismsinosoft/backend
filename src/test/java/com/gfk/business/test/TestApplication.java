package com.gfk.business.test;

import com.gfk.business.common.file.model.FileInfo;
import com.gfk.common.util.DateParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王哲霖
 * @version 1.0 2021-06-17 17:53
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAsync
@Slf4j
public class TestApplication {

//    @Resource
//    private UserMapper userMapper;


    @Test
    public void initUserPW() {
        /*List<User> users = userMapper.selectList(Wrappers.emptyWrapper());
        for (User user : users) {
            //设置密码
            String salt = String.valueOf(System.currentTimeMillis());
            user.setSalt(salt);
            // 密码加密（与shiro密码校验时的解密机制一致）
            String password = EncryptUtils.encryptPassword(user.getId(), "Admin888!", user.getSalt());
            user.setPassword(password);
            user.setCreatedTime(TimeUtils.nowDate());
            user.setUpdatedTime(user.getCreatedTime());
            userMapper.updateById(user);
        }*/
    }


    @Test
    public void getAllFields() {
        FileInfo clazz = new FileInfo();
        //HomeQueryForm clazz = new HomeQueryForm();
        Field[] declaredFields = clazz.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            //System.out.println(declaredField.getName());
            //System.out.println();
        }
        List<String> names = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList()).stream().map(str -> {
            char[] cs = str.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
            //return str;
        }).collect(Collectors.toList());
        //List<String> result = names.stream().map(name -> "result" + ".set" + name + "(" + "tmsHomeDataDTO" + ".get" + name + "()" + ".add(result.get" + name + "()" + "));").collect(Collectors.toList());
        //List<String> result = names.stream().map(name -> "buPerson.set" + name + "(buPerson.get" + name + "().add(person.get" + name + "()));").collect(Collectors.toList());
        //List<String> result = names.stream().map(name -> "this." + name + "= form.get" + name + "().add(person.get" + name + "()));").collect(Collectors.toList());
        List<String> result = names.stream().map(name -> "form.set" + name + "(\"\");").collect(Collectors.toList());
        result.forEach(System.out::println);
    }

    @Test
    public void testDateParser() {
        System.out.println(DateParser.quarterDate("2021-01-06"));
        System.out.println(DateParser.quarterDate("2021-03-06"));
        System.out.println(DateParser.quarterDate("2021-04-06"));
        System.out.println(DateParser.yearDate("2021-01-06"));
        System.out.println(DateParser.yearDate("2021-07-06"));
    }


}
