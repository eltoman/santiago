package com.emstudies.santiago.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class AccountCreationListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(AccountCreationListener.class);

    @Override
    public void onMessage(Message message) {

        try {
            String messageBody = new String(message.getBody());
            logger.info("Receive message: ", messageBody);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
