package com.emstudies.santiago.infra.async.rabbitmq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.emstudies.santiago.infra.async.rabbitmq.RabbitMqExchanges.*;

@Configuration
public class RabbitMqExchangeConfiguration {

    @Bean
    Exchange chileExchange(){
        return ExchangeBuilder.topicExchange(CHILE.getExchange())
                .durable(true)
                .build();
    }
}
