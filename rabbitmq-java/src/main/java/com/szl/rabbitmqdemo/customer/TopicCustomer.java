package com.szl.rabbitmqdemo.customer;

import com.rabbitmq.client.*;
import com.szl.rabbitmqdemo.producer.RabbitMqBase;
import com.szl.rabbitmqdemo.producer.TopicProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.szl.rabbitmqdemo.producer.DirectProducer.QUEUE_NAME;

/**
 * @Description :rabbitmq(topic)消息消费者
 * @author ：Andrew.Song
 * @Date : 2018-2-28 13:40
 * @Modified By ：
 */
public class TopicCustomer extends CustomerBase{

    public static void main(String[] args){
        // 创建连接工厂
        ConnectionFactory factory = RabbitMqBase.getConnectionFactoryInstance();

        // 设置rabbitmq
        factory.setHost("192.168.56.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 创建一个连接
        Connection connection = null;
        try {
            connection = RabbitMqBase.getConnection(factory);

            // 创建一个通道
            Channel channel = RabbitMqBase.getChannel(connection);

            // 声明一个交换机
            channel.exchangeDeclare(TopicProducer.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true);

            // 路由关键字
            String[] routingKeys = new String[]{"#"};

            // 队列绑定
            for (int i = 0; i <routingKeys.length ; i++) {
                channel.queueBind(TopicProducer.TOPIC_QUEUE_NAME,TopicProducer.TOPIC_EXCHANGE_NAME,routingKeys[i]);
            }

            // 监听一个通道
            Consumer consumer = defaultConsumer(channel);

            // 自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(TopicProducer.TOPIC_QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
