package com.szl.rabbit.customer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Description : 消息消费者
 * @author ：Andrew.Song
 * @Date : 2018-3-13 15:46
 * @Modified By ：
 */
public class MqCustomer implements MessageListener{


    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
