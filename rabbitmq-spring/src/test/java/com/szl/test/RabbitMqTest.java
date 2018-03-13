package com.szl.test;

import com.szl.rabbit.provider.MqProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @Description :
 * @author ：Andrew.Song
 * @Date : 2018-3-13 16:22
 * @Modified By ：
 */
public class RabbitMqTest {

    private ApplicationContext applicationContext = null;

    @Before
    public void init(){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void mqTest() throws InterruptedException, UnsupportedEncodingException {
        MqProvider mqProvider = (MqProvider) applicationContext.getBean("mqProvider");
        mqProvider.sendMsg();

        // 暂停一下,让消费者去处理
        Thread.sleep(6000);
    }
}
