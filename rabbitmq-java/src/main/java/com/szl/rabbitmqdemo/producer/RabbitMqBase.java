package com.szl.rabbitmqdemo.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description : rabbitmq基本配置
 * @author ：Andrew.Song
 * @Date : 2018-3-6 15:00
 * @Modified By ：
 */
public abstract class RabbitMqBase {

    private volatile static ConnectionFactory factory = null;

    /**
     * 创建connectionFactory
     * @return
     */
    public static ConnectionFactory getConnectionFactoryInstance(){
        if (null == factory) {
            synchronized (RabbitMqBase.class) {
                if (null == factory) {
                    factory = new ConnectionFactory();
                }
            }
        }
        return factory;
    }

    /**
     * 创建Connection
     * @param factory
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection(ConnectionFactory factory) throws IOException, TimeoutException {
        return factory.newConnection();
    }

    /**
     * 创建 channel
     * @param connection
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }

    public static void close(Connection connection,Channel channel) throws IOException, TimeoutException {
        if (channel != null) channel.close();
        if (connection != null) connection.close();
    }

}
