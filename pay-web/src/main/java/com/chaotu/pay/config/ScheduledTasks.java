package com.chaotu.pay.config;

import com.chaotu.pay.po.TOrder;
import com.chaotu.pay.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    OrderService orderService;
    //发货
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateTodayAmount() {
        TOrder order = new TOrder();
        order.setIsHistory(1);
        orderService.updateByIsHistory(order);

    }

}