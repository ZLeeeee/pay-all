package com.chaotu.pay.mq;

import com.chaotu.pay.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_A)
    public void processMessage(String content) {
        log.info(content);
    }
}
