package com.emstudies.santiago.configuration.async;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emstudies.santiago.async.AccountCreationListener;

import java.util.HashMap;
import java.util.Map;

import static com.emstudies.santiago.configuration.async.AsyncConnectionQueue.*;

@Configuration
public class AccountCreationListenerConfig {


    private static final String EXCHANGE = "CHILE";
    private static final AsyncConnectionQueue QUEUE = ACCOUNT_CREATION;
    private static final AsyncConnectionQueue QUEUE_PARKING_LOT = ACCOUNT_CREATION_PARKING_LOT;
    private static final AsyncConnectionQueue QUEUE_RETRY = ACCOUNT_CREATION_RETRY;

    @Value("MESSAGE_TTL_DEFAULT")
    private String MESSAGE_TTL_DEFAULT;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public MessageListener accountCreationListener(){
        return new AccountCreationListener();
    }

    @Bean
    public SimpleMessageListenerContainer accountCreationContainer(){
        SimpleMessageListenerContainer container = null;
        try{
            Queue queue = buildQueue();
            Queue parkingLotQueue = buildParkingLotQueue();
            Queue retryQueue = buildRetryQueue();

            container = createContainer(new Queue[]{queue, parkingLotQueue, retryQueue});

        }catch (Exception e){
            e.printStackTrace();
        }

        return container;
    }

    private Queue buildQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", EXCHANGE);
        args.put("x-dead-letter-routing-key", QUEUE_PARKING_LOT.getRoutingKey());

        return new Queue(QUEUE.getQueue(), true, false,false, args);
    }

    private Queue buildParkingLotQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", EXCHANGE);
        args.put("x-dead-letter-routing-key", QUEUE_RETRY.getRoutingKey());
        args.put("x-message-ttl", MESSAGE_TTL_DEFAULT);

        return new Queue(QUEUE_PARKING_LOT.getQueue(), true, false,false, args);
    }

    private Queue buildRetryQueue() {
        return new Queue(QUEUE_RETRY.getQueue(), true, false,false);
    }

    private SimpleMessageListenerContainer createContainer(Queue[] queues) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queues);
        container.setMessageListener(accountCreationListener());
        container.setMaxConcurrentConsumers(2);

        return container;
    }
}
