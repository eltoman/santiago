package com.emstudies.santiago.infra.async.rabbitmq;

public enum RabbitMqExchanges {

    CHILE("ChileExchange");

    String exchange;

    RabbitMqExchanges(String exchange){
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    @Override
    public String toString() {
        return "RabbitMqExchanges{" +
                "exchange='" + exchange + '\'' +
                '}';
    }
}
