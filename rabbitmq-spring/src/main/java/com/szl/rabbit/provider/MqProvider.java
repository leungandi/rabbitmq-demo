package com.szl.rabbit.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @Description : 消息提供者
 * @author ：Andrew.Song
 * @Date : 2018-3-13 15:43
 * @Modified By ：
 */
@Service("mqProvider")
public class MqProvider {

    @Resource(name="amqpTemplate")
    private AmqpTemplate amqpTemplate;


    public void sendMsg() throws UnsupportedEncodingException {
        System.out.println("spring:开始写入消息队列");
        for (int i = 0; i < 10 ; i++) {
            String msg = "Hello Spring-RabbitMq("+i+")";
            Message message = MessageBuilder.withBody(msg.getBytes("utf-8"))
                    .setMessageId(System.currentTimeMillis() + "")
                    .build();
            amqpTemplate.send(message);
        }
        System.out.println("spring:写入消息队列完成");
    }

}
