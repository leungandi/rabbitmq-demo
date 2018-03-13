package com.szl.rabbitmqdemo.customer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Description : 消费者基类
 * @author ：Andrew.Song
 * @Date : 2018-3-6 17:17
 * @Modified By ：
 */
public class CustomerBase{

    protected static Consumer defaultConsumer(final Channel channel) throws UnsupportedEncodingException {
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("接收到消息-->'" + message + "'");
                System.out.println("exchange-->" +envelope.getExchange());
                System.out.println("routingKey-->" +envelope.getRoutingKey());
            }
        };
        return consumer;
    }
}
