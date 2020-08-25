package com.emstudies.santiago.async;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class AccountCreationListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            String messageBody = new String(message.getBody());
            System.out.println(messageBody);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
