package com.emstudies.santiago.async;

import com.emstudies.santiago.infra.async.rabbitmq.AsyncConnectionQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.emstudies.santiago.infra.async.rabbitmq.AsyncConnectionQueue.*;
import static com.emstudies.santiago.infra.async.rabbitmq.RabbitMqExchanges.*;

@Configuration
public class AccountCreationListenerConfig {

    Logger logger = LoggerFactory.getLogger(AccountCreationListenerConfig.class);
    private TopicExchange exchange = new TopicExchange(CHILE.getExchange());
    private static final AsyncConnectionQueue QUEUE = ACCOUNT_CREATION;
    private static final AsyncConnectionQueue QUEUE_PARKING_LOT = ACCOUNT_CREATION_PARKING_LOT;
    private static final AsyncConnectionQueue QUEUE_RETRY = ACCOUNT_CREATION_RETRY;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public SimpleMessageListenerContainer accountCreationContainer(){
        SimpleMessageListenerContainer container = null;
        try{
            Queue queue = buildQueue();
            Queue parkingLotQueue = buildParkingLotQueue();
            Queue retryQueue = buildRetryQueue();

            container = createContainer(new Queue[]{queue, parkingLotQueue, retryQueue});

            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareQueue(parkingLotQueue);
            amqpAdmin.declareQueue(retryQueue);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return container;
    }

    @Bean
    public MessageListener accountCreationListener(){
        return new AccountCreationListener();
    }

    private Queue buildQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", CHILE.getExchange());
        args.put("x-dead-letter-routing-key", QUEUE_PARKING_LOT.getRoutingKey());

        return new Queue(QUEUE.getQueue(), true, false,false, args);
    }

    private Queue buildParkingLotQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", CHILE.getExchange());
        args.put("x-dead-letter-routing-key", QUEUE_RETRY.getRoutingKey());
        args.put("x-message-ttl", 18000);

        return new Queue(QUEUE_PARKING_LOT.getQueue(), true, false,false, args);
    }

    private Queue buildRetryQueue() {
        return new Queue(QUEUE_RETRY.getQueue(), true, false,false);
    }

    private SimpleMessageListenerContainer createContainer(Queue[] queues) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setAutoStartup(true);
        container.setQueues(queues);
        container.setMessageListener(accountCreationListener());
        return container;
    }
}
