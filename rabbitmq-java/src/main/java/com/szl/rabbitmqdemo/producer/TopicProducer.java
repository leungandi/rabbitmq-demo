package com.szl.rabbitmqdemo.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description : rabbitmq (topic)消息生产者
 * @author ：Andrew.Song
 * @Date : 2018-2-28 11:51
 * @Modified By ：
 */
public class TopicProducer {

    public static final String TOPIC_QUEUE_NAME = "com.szl.topic.que";
    public static final String TOPIC_EXCHANGE_NAME = "com.szl.topic.exchange";
    public static final String TOPIC_ROUTING_KEY = "com.szl.topic.que.routing";

    public static void main(String[] args){

        // 创建连接工厂
        ConnectionFactory factory = RabbitMqBase.getConnectionFactoryInstance();
        Connection connection = null;
        Channel channel = null;

        // 设置rabbitmq
        factory.setHost("192.168.56.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        try {
            // 创建一个连接
            connection= RabbitMqBase.getConnection(factory);

            // 创建一个通道
            channel = RabbitMqBase.getChannel(connection);

            // 声明一个交换机,这里使用topic
            channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true);

            // 声明一个队列
            channel.queueDeclare(TOPIC_QUEUE_NAME, true, false, false, null);

            String[] routingKeys = new String[]{"www.loveyy.net", "www.ddyc.vip"};

            // 发送消息到队列
            System.out.println("-----写入消息队列开始------");
            for (int i = 0; i < routingKeys.length; i++) {
                String message = "Hello rabbitmq(" + routingKeys[i] + ")";
                channel.basicPublish(TOPIC_EXCHANGE_NAME,routingKeys[i],null,message.getBytes());
            }
            System.out.println("-----写入消息队列完成------");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        finally {
            try {
                RabbitMqBase.close(connection,channel);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

    }
}
