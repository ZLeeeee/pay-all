package com.chaotu.pay;

import com.chaotu.pay.common.channel.Channel;
import com.chaotu.pay.common.channel.ChannelFactory;
import com.chaotu.pay.common.channel.PddChannel;
import com.chaotu.pay.po.TChannelAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayWebApplicationTests {
@Autowired
    ChannelFactory factory;
    @Test
    public void contextLoads() {


    }

}
