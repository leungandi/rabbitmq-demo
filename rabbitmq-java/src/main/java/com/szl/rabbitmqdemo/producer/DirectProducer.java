package com.szl.rabbitmqdemo.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description : rabbitmq (direct)消息生产者
 * @author ：Andrew.Song
 * @Date : 2018-2-28 11:51
 * @Modified By ：
 */
public class DirectProducer {

    public static final String QUEUE_NAME = "com.szl.direct.que";
    public static final String DIRECT_EXCHANGE_NAME = "com.szl.direct.exchange";
    public static final String ROUTING_KEY = "com.szl.direct.que.routing";

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

            // 声明一个交换机,这里使用DIRECT
            channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true);

            // 声明一个队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // 队列绑定到交换机
            channel.queueBind(QUEUE_NAME,DIRECT_EXCHANGE_NAME,ROUTING_KEY);

            // 发送消息到队列
            System.out.println("-----写入消息队列开始------");
            for (int i = 1; i < 11; i++) {
                String message = "Hello rabbitmq(" + i + ")";
                channel.basicPublish(DIRECT_EXCHANGE_NAME,ROUTING_KEY,null,message.getBytes());
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
