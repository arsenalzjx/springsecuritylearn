package com.zjx.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>@ClassName: MockQueue </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/30 11:05</p>
 */
@Component()
public class MockQueue {
    //下单消息
    private String placeOrder;

    //订单完成消息
    private String completeOrder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws Exception {
        //创建一个新的线程模拟消息队列接受消息处理业务并存入处理结果消息
        new Thread(() -> {
            logger.info("接到下单请求:"+placeOrder);
            try {
                logger.info("处理下单请求:"+placeOrder);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("下单请求处理完毕:"+placeOrder);
            logger.info("处理完成消息到队列:"+placeOrder);
            this.completeOrder = placeOrder;
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
