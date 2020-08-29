package com.emstudies.santiago.infra.async.rabbitmq;

public enum AsyncConnectionQueue {

    ACCOUNT_CREATION("santiago.account.creation", "santiago.account.creation"),
    ACCOUNT_CREATION_PARKING_LOT("santiago.account.creation.parking", "santiago.account.creation.parking"),
    ACCOUNT_CREATION_RETRY("santiago.account.creation.retry", "santiago.account.creation.retry");

    private String queue;
    private String routingKey;

    AsyncConnectionQueue(String queue, String routingKey){
        this.queue = queue;
        this.routingKey = routingKey;
    }

    public String getQueue() {
        return queue;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    @Override
    public String toString() {
        return "AsyncConnectionsQueues{" +
                "queue='" + queue + '\'' +
                ", routingKey='" + routingKey + '\'' +
                '}';
    }
}
